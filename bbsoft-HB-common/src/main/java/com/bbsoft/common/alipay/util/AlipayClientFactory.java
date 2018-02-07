/**  
 * @Title: AlipayClientFactory.java
 * @Package: com.bbsoft.common.alipay.util
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-3-2
 */
package com.bbsoft.common.alipay.util;

import java.util.Map;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.bbsoft.common.alipay.config.AlipayConfig;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;

/**
 * ClassName: AlipayClientFactory 
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-3-2
 */
public class AlipayClientFactory {
	
	/**
	 * 初始化alipay客户端
	 */
	public static AlipayClient alipayClient = null;
	
	public static AlipayClient getAlipayClient() {
		return alipayClient;
	}

	public static void setAlipayClient(AlipayClient alipayClient) {
		AlipayClientFactory.alipayClient = alipayClient;
	}

	//实例化客户端
	static {
		if(alipayClient==null){
			alipayClient = new DefaultAlipayClient(
			AlipayConfig.URL,// "https://openapi.alipaydev.com/gateway.do"
			AlipayConfig.APP_ID,// APP_ID,
			AlipayConfig.private_key, // APP_PRIVATE_KEY,
			"json", // 数据格式,
			AlipayConfig.input_charset,// CHARSET,
			AlipayConfig.alipay_public_key,// ALIPAY_PUBLIC_KEY,
			AlipayConfig.sign_type); // "RSA2"
			
		}
	}
	
	/**
	 * @Description: 校验支付宝返回值是否正确
	 * @param: @param params
	 * @param: @return   
	 * @return: boolean  
	 * @throws
	 * @author: VULCAN
	 * @date: 2017-3-2
	 */
	public static boolean rsaCheckV1(Map<String, String> params){
		boolean flagNew = false;
		try {
			flagNew = AlipaySignature.rsaCheckV1(
					params,//返回参数
					AlipayConfig.alipay_public_key, //支付宝公钥
					AlipayConfig.input_charset, //字符串编码
					"RSA2");
		} catch (AlipayApiException e) {
			e.printStackTrace();
			throw new ServiceException(MsgeData.ALIPAY_00004.getCode(), MsgeData.ALIPAY_00004.getMsg());
		}
		return flagNew;
	}

}
