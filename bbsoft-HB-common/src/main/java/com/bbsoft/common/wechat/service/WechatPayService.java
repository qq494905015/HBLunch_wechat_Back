package com.bbsoft.common.wechat.service;

import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbsoft.common.util.StringUtil;
import com.bbsoft.common.wechat.WeChatConfig;
import com.bbsoft.common.wechat.utils.WechatPayUtil;

/**
 * 微信支付业务
 * @author Chris.Zhang
 * @date 2017年3月8日
 */
public class WechatPayService {
	
	private static Logger logger = LoggerFactory.getLogger(WechatPayService.class);
	static String timeMillis = String.valueOf(System.currentTimeMillis() / 1000);
	static String randomString = WechatPayUtil.getRandomString(32);
	
	/**
	 * 微信预支付订单，统一下单接口
	 * @param tradeNo 平台订单号
	 * @param totalAmount 支付的金额
	 * @param description 商品简单描述
	 * @param tradeType 取值如下：JSAPI，NATIVE，APP
	 * @param notifyUrl 支付成功通知路径
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> weixinPrePay(String tradeNo, Long totalAmount, String description, String tradeType, String notifyUrl, String openId, HttpServletRequest request){
		SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();  
		parameterMap.put("appid", WeChatConfig.APP_ID);  	//微信开放平台审核通过的应用APPID
		parameterMap.put("mch_id", WeChatConfig.MCH_ID);  	//微信支付分配的商户号
		parameterMap.put("nonce_str", randomString);  		//随机字符串，不长于32位
		parameterMap.put("body", description);				
		parameterMap.put("out_trade_no", tradeNo + "_" + StringUtil.getValidCode(6));			//商户订单号
		parameterMap.put("fee_type", "CNY");  				//符合ISO 4217标准的三位字母代码，默认人民币：CNY
		parameterMap.put("total_fee", totalAmount); 		//订单总金额，单位为分
		parameterMap.put("spbill_create_ip", request.getRemoteAddr()); //用户端实际ip
		parameterMap.put("notify_url", notifyUrl);//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
		parameterMap.put("trade_type", tradeType);				//支付类型
		
		if("JSAPI".equals(tradeType)){
			parameterMap.put("openid", openId);				//trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识
			
		}
		String sign = WechatPayUtil.createSign("UTF-8", parameterMap); 
		parameterMap.put("sign", sign);  
		String requestXML = WechatPayUtil.getRequestXml(parameterMap);  
		logger.debug("=======requestXML======={}", requestXML);
		String result = WechatPayUtil.httpsRequest(  
				WeChatConfig.PREPAY_URL, "POST",  
				requestXML);  
		logger.debug("wechat prepay result is 【{}】", result);  
		Map<String, String> map = null;  
		try {  
			//处理已xml格式返回的结果
			map = WechatPayUtil.doXMLParse(result);  
		} catch (JDOMException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		SortedMap<String, Object> returnParams = new TreeMap<String, Object>(); 
		returnParams.put("appId", WeChatConfig.APP_ID);
		returnParams.put("nonceStr", randomString);
		returnParams.put("timeStamp", System.currentTimeMillis()/1000);
		returnParams.put("package", "prepay_id=" + map.get("prepay_id"));
		returnParams.put("signType", "MD5");
//		returnParams.put("partnerid", WeChatConfig.MCH_ID);//老版本，可能APP和扫码支付需要
//		returnParams.put("prepayid", map.get("prepay_id"));//老版本，可能APP和扫码支付需要
		map.put("sign", WechatPayUtil.createSign("UTF-8", returnParams));
		map.put("timestamp", returnParams.get("timeStamp").toString());
		map.put("nonce_str", returnParams.get("nonceStr").toString());
		map.put("package", "Sign=WXPay");
		return map;                
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();  
		parameterMap.put("appid", WeChatConfig.APP_ID);  	//微信开放平台审核通过的应用APPID
		parameterMap.put("mch_id", WeChatConfig.MCH_ID);  	//微信支付分配的商户号
		parameterMap.put("nonce_str", randomString);  		//随机字符串，不长于32位
		parameterMap.put("body", "万味网");				
		parameterMap.put("out_trade_no", "294434960591290361");			//商户订单号
		parameterMap.put("fee_type", "CNY");  				//符合ISO 4217标准的三位字母代码，默认人民币：CNY
		parameterMap.put("total_fee", "11"); 		//订单总金额，单位为分
		parameterMap.put("spbill_create_ip", "127.0.0.1"); //用户端实际ip
		parameterMap.put("notify_url", WeChatConfig.NOTIFY_URL);//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
		parameterMap.put("trade_type", "JSAPI");				//支付类型
		parameterMap.put("openid", "ogMZuv0L3g_O-uC2DAPHU_3m6ZYg");				//支付类型
		String sign = WechatPayUtil.createSign("UTF-8", parameterMap); 
		System.out.println(randomString);
		System.out.println(sign);
		parameterMap.put("sign", sign);  
		String requestXML = WechatPayUtil.getRequestXml(parameterMap);  
		Map<String, String> map = null;  
		try {  
			//处理已xml格式返回的结果
			String result = WechatPayUtil.httpsRequest(  
					WeChatConfig.PREPAY_URL, "POST",  
					requestXML);  
			map = WechatPayUtil.doXMLParse(result);  
		} catch (JDOMException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		System.out.println(map.toString());
	}

}
