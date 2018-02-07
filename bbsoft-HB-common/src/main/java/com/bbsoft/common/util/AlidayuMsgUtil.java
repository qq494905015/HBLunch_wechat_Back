package com.bbsoft.common.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.PhoneMessage;
import com.bbsoft.common.domain.ServiceException;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import net.sf.json.JSONObject;

/**
 * 阿里大于短信工具类
 * @author Chris.Zhang
 * @date 2017-7-7 13:55:33
 *
 */
public class AlidayuMsgUtil {
	
	private static Logger logger = LoggerFactory.getLogger(AlidayuMsgUtil.class);
	
	//TOP分配给应用的AppKey
	private final static String APP_KEY = "23741048";
	//TOP分配给应用的密钥
	private final static String APP_SECRET = "70dde2c05903fd60faa9f80a6c6afc0c";
	//官网的URL，http请求， 正式环境
	private final static String URL = "http://qw.api.taobao.com/router/rest";
	//用于临时存储手机短信信息的集合，用完即删除指定手机号信息
	public static Map<String, PhoneMessage> phoneMaps = new HashMap<String, PhoneMessage>();	
	public static Long limitTime = 1000 * 60 * 1L;	//发送限制重复发送时间，毫秒
	public static Long sendTime = 0L;				//短信发送时间，毫秒
	public static String product = "【万味网】"; 
	/**
	 * 发送短信
	 * @param phone 接收短信手机号
	 * @param signName 短信签名
	 * @param tempCode 短信模板ID
	 * @param params 短信模板变量
	 * @param time 限制重复发送时间，单位：分钟
	 * @return
	 */
	public static PhoneMessage sendMsg(String phone, String signName, String tempCode, JSONObject params, Integer time){
		
		//默认一分钟
		if(time == null){
			time = 1;
		}
		//当前时间，毫秒
		Long currTime = System.currentTimeMillis();
		logger.info("向手机号为【{}】的发送短信时间{}， ", phone, currTime);
		
		//先从内存中读取，看是否存在已经发送的短信信息，不存在直接发送，如果存在则判断时间是否过期
		PhoneMessage phoneMessage = phoneMaps.get(phone + "_" + tempCode);
		if(phoneMessage == null 
				|| (phoneMessage != null && (currTime - phoneMessage.getSendTime()) > limitTime * time)){
			//获取随机数
			String validateCode = StringUtil.getValidCode(4);
			TaobaoClient client = new DefaultTaobaoClient(URL, APP_KEY, APP_SECRET);
			//实例化请求
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			//公共回传参数，在”消息返回“中会透回该参数
			req.setExtend("OK");
			//(必写)短信类型，传入值请写normal
			req.setSmsType("normal");
			//(必写)短信签名，传入的短信签名是在阿里大于“管理中心-短信签名管理”中的可用签名
			req.setSmsFreeSignName(signName);
			//短信模板变量，传参规则{"key" : "value"}
			params.put("code", validateCode);
			req.setSmsParamString(params.toString());
			//(必写)短信接收号码，支持单个或多个手机号码，多个号码间用英文逗号相隔
			req.setRecNum(phone);
			//短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板
			req.setSmsTemplateCode(tempCode);
			//实例化响应包
			AlibabaAliqinFcSmsNumSendResponse response;
			try {
				response = client.execute(req);
				String result = response.getBody();
				logger.debug("短信发送返回结果：" + result);
				//处理返回结果
				if(result != null && result.length() > 0){
					JSONObject resultJson = JSONObject.fromObject(result);
					JSONObject aliResultJson = resultJson.getJSONObject("alibaba_aliqin_fc_sms_num_send_response").getJSONObject("result");
					if("0".equals(aliResultJson.getString("err_code").toString()) && aliResultJson.getBoolean("success")){
						PhoneMessage rtPhoneMessage = new PhoneMessage(phone, currTime, validateCode, signName);
						phoneMaps.put(phone + "_" + tempCode, rtPhoneMessage);
						return rtPhoneMessage;
					}
				}
			} catch (ApiException e) {
				e.printStackTrace();
			}
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
	public static Boolean checkCode(String phone, String code, String type){
		String tempCode = "";
		if("1".equals(type)){
			tempCode = "SMS_59990689";
		}
		if("2".equals(type)){
			tempCode = "SMS_59990687";
		}
		if("3".equals(type)){
			tempCode = "SMS_59990685";
		}
		if("4".equals(type)){
			tempCode = "SMS_59990691";
		}
		//参数校验
		check(phone);
		PhoneMessage phoneMessage = phoneMaps.get(phone + "_" + tempCode);
		if(phoneMessage != null && !StringUtil.isEmpty(code) && code.equals(phoneMessage.getCode())){
			phoneMaps.remove(phone + "_" + tempCode);
			return true;
		}else{
			throw new ServiceException(MsgeData.PUBLIC_1000010005.getCode());
		}
		
	}
	
	/**
	 * 
	 * @param phone 手机号
	 * @return 
	 */
	public static void check(String phone){
		if(phone == null || (!StringUtil.isEmpty(phone) && !StringUtil.checkPhone(phone))){
			throw new ServiceException(MsgeData.PUBLIC_1000010001.getCode());
		}
	}
	
	/**
	 * 发送登录验证码
	 * @param phone 接收验证码手机号
	 * @return
	 */
	public static PhoneMessage sendLoginMsg(String phone){
		PhoneMessage phoneMessage = sendMsg(phone, "登录验证", "SMS_59990689", setInitParams(), null);
		return phoneMessage;
	}
	
	/**
	 * 发送注册验证码
	 * @param phone 接收验证码手机号
	 * @return
	 */
	public static PhoneMessage sendRegisterMsg(String phone){
		PhoneMessage phoneMessage = sendMsg(phone, "注册验证", "SMS_59990687", setInitParams(), null);
		return phoneMessage;
	}
	
	/**
	 * 发送修改密码验证码
	 * @param phone 接收验证码手机号
	 * @return
	 */
	public static PhoneMessage sendModfiyMsg(String phone){
		PhoneMessage phoneMessage = sendMsg(phone, "变更验证", "SMS_59990685", setInitParams(), null);
		return phoneMessage;
	}
	
	/**
	 * 发送修改密码验证码
	 * @param phone 接收验证码手机号
	 * @return
	 */
	public static PhoneMessage sendIdCheckMsg(String phone){
		PhoneMessage phoneMessage = sendMsg(phone, "身份验证", "SMS_59990691", setInitParams(), null);
		return phoneMessage;
	}
	
	private static JSONObject setInitParams(){
		JSONObject params = new JSONObject();
		params.put("product", product);
		return params;
	}
	
	public static void main(String[] args) {
		PhoneMessage phoneMessage = sendIdCheckMsg("15813878509");
		System.out.println(checkCode("15813878509", phoneMessage.getCode(), "SMS_59990691"));
	}
}
