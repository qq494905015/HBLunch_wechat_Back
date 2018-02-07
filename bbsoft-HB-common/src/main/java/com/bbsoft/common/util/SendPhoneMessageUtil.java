package com.bbsoft.common.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.PhoneMessage;
import com.bbsoft.common.domain.ServiceException;
import com.qcloud.sms.SmsSingleSender;
import com.qcloud.sms.SmsSingleSenderResult;

/**
 * 发送短信工具类
 * @author Chris
 * @date 2017-2-22 11:07:43
 */
public class SendPhoneMessageUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SendPhoneMessageUtil.class);
	public static final Integer APP_ID = Integer.parseInt(ResourceUtil.getKey("APP_ID"));
	public static final String APP_KEY = ResourceUtil.getKey("APP_KEY");
	public static final Integer TYPE = 0; 			//短信类型，0 为普通短信，1 营销短信
	public static final String NATION_CODE = ResourceUtil.getKey("NATION_CODE");  //国家码，如 86 为中国
	public static Long limitTime = 1000 * 60 * 1L;	//发送限制重复发送时间，毫秒
	public static Long sendTime = 0L;				//短信发送时间，毫秒
	public static String templateLogin = "{1}为您的登录验证码，请于{2}分钟内填写。如非本人操作，请忽略本短信。";
	public static String templateRegister = "{1}为您的注册验证码，请于{2}分钟内填写。如非本人操作，请忽略本短信。";
	public static String templateFindPwd = "{1}为您的密码重置验证码，请于{2}分钟内填写。如非本人操作，请忽略本短信。";
	public static String templateCheck = "{1}为您的验证码，请于{2}分钟内填写。如非本人操作，请忽略本短信。";
	public static Map<String, PhoneMessage> phoneMaps = new HashMap<String, PhoneMessage>();	//用于临时存储手机短信信息的集合，用完即删除指定手机号信息
	
	
	/**
	 * 发送手机短信验证码
	 * @param phone 手机号
	 * @param type 类型 1 登录, 2 注册, 3 修改密码, 4 验证ID
	 * @param time 限制重复发送时间，单位：分钟
	 * @return
	 */
	public static PhoneMessage sendSimpleMessage(String phone, Integer type, Integer time){
		//参数校验
		check(phone, type);
		//默认一分钟
		if(time == null){
			time = 1;
		}
		//当前时间，毫秒
		Long currTime = System.currentTimeMillis();
		logger.info("向手机号为【{}】的发送短信时间{}， ", phone, currTime);
		
		//先从内存中读取，看是否存在已经发送的短信信息，不存在直接发送，如果存在则判断时间是否过期
		PhoneMessage phoneMessage = phoneMaps.get(phone + "_" + type.toString());
		if(phoneMessage == null 
				|| (phoneMessage != null && (currTime - phoneMessage.getSendTime()) > limitTime * time)){
			//获取短信发送对象
			SmsSingleSender singleSender = getSmsSingleSender();
			String message = "";
			//获取随机数
			String validateCode = StringUtil.getValidCode(6);
			switch (type) {
			case 1:
				message = templateLogin;
				break;
			case 2:
				message = templateRegister;
				break;
			case 3:
				message = templateFindPwd;
				break;
			case 4:
				message = templateCheck;
				break;
			default:
				break;
			}
			logger.info("发送短信信息,phone【{}】,sendTime【{}】,validateCode【{}】,message【{}】",phone, currTime, validateCode, message);
			message = message.replace("{1}", validateCode).replace("{2}", time.toString());
			SmsSingleSenderResult result = singleSend(singleSender, phone, message);
			logger.info("调用短息服务返回结果：{}", result.toString());
			//处理返回结果
			if(result != null && result.result == 0){
				PhoneMessage rtPhoneMessage = new PhoneMessage(phone, currTime, validateCode, message);
				phoneMaps.put(phone + "_" + type.toString(), rtPhoneMessage);
				return rtPhoneMessage;
			}
		}else{	//限制时间内不允许重复发送
			throw new ServiceException(MsgeData.PUBLIC_1000010004.getCode());
		}
		return null;
	}
	
	/**
	 * 校验验证码
	 * @param phone 手机号
	 * @param code 验证码
	 * @param type 类型 1 登录, 2 注册, 3 修改密码, 4 验证ID
	 * @return
	 */
	public static Boolean checkCode(String phone, String code, Integer type){
		//参数校验
		check(phone, type);
		PhoneMessage phoneMessage = phoneMaps.get(phone + "_" + type.toString());
		if(phoneMessage != null && !StringUtil.isEmpty(code) && code.equals(phoneMessage.getCode())){
			phoneMaps.remove(phone + "_" + type.toString());
			return true;
		}else{
			throw new ServiceException(MsgeData.PUBLIC_1000010005.getCode());
		}
		
	}
	
	/**
	 * 
	 * @param phone 手机号
	 * @param type 类型 1 登录, 2 注册, 3 修改密码, 4 验证ID
	 * @return 
	 */
	public static void check(String phone, Integer type){
		if(phone == null || (!StringUtil.isEmpty(phone) && !StringUtil.checkPhone(phone))){
			throw new ServiceException(MsgeData.PUBLIC_1000010001.getCode());
		}
		if(type == null || type < 0 || type > 4){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode(), 
					MsgeData.SYSTEM_10104.getMsg() + 
					",type【" + type + "】");
		}
	}
	
	/**
	 * 发送短信获取结果
	 * @param singleSender SMS短信发送对象
	 * @param type 短信类型，0 为普通短信，1 营销短信
	 * @param nationCode 国家码，如 86 为中国
	 * @param phone 不带国家码的手机号
	 * @param message 信息内容，必须与申请的模板格式一致，否则将返回错误
	 * @param extend 扩展码，可填空
	 * @param ext 服务端原样返回的参数，可填空
	 * @return
	 */
	public static SmsSingleSenderResult singleSend(SmsSingleSender singleSender, Integer type, String nationCode, String phone, String message, String extend, String ext){
		SmsSingleSenderResult senderResult = null;
		try {
			senderResult = singleSender.send(type, nationCode, phone, message, extend, ext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return senderResult;
	}
	
	/**
	 * 
	 * @param singleSender SMS短信发送对象
	 * @param phone 不带国家码的手机号
	 * @param message 信息内容，必须与申请的模板格式一致，否则将返回错误
	 * @return
	 */
	public static SmsSingleSenderResult singleSend(SmsSingleSender singleSender, String phone, String message){
		return singleSend(singleSender, TYPE, NATION_CODE, phone, message, "", "");
	}
	
	
	/**
	 * 获取SMS短信发送对象
	 * @param appId 
	 * @param appKey
	 * @return
	 */
	public static SmsSingleSender getSmsSingleSender(Integer appId, String appKey){
		SmsSingleSender singleSender = null;
		try {
			singleSender = new SmsSingleSender(appId, appKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return singleSender;
	}
	
	/**
	 * 获取默认的APP_ID和APP_KEY的SMS短信发送对象
	 * @return
	 */
	public static SmsSingleSender getSmsSingleSender(){
		return getSmsSingleSender(APP_ID, APP_KEY);
	}
	
	public static void main(String[] args) {
		System.out.println(sendSimpleMessage("15813878509", 1, 1));	
	}
}
