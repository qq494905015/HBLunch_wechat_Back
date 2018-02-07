package com.bbsoft.common.alipaypc.config;

import java.util.HashMap;
import java.util.Map;

/* *
 *类名：AlipayConfig
 *功能：pc端支付宝支付基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfigPc {
	
	// 支付宝提供给商户的服务接入网关URL(新)
	public static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
	
    // 支付宝消息验证地址
	public static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "2088311771079114";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJru+FPwIOvmAJADK4GBMG1Puof3d0tdGY4NKeOUn6ynDdwTgZFpPNgijroOa862xUme6O7/HL3oQyLBPPSEJsEmqw+Ebow463OHHwZBEQJQL+t5olNT7snGY+BQUFO7VkqeMEM68q20RiVfaRn1UpQciWzNqfzUbf6q3mIylrf9AgMBAAECgYAxdfy8/znjVUf0sa5f55hILV3FLlFdM9mGqdknmDjpa2NXULraaxhLf6zE5PmxwlN1T0/ZjIPmdJGKNNp6zP15xyERLxtW41W6nUkbovJufV55isH9usZA22+m065ebSE1UJkll5aIQJqN1mWfUHgqQdKOq9Xi4sW5Mkb2lRDkWQJBAOuv27bvMPZC0/wIaFUfCPt/4WlZXhvMtdai9m41rb0qkAmjd2Lw7dbFMfMjpR0NarKE06pLCD/Ba9k2oEn23McCQQCoSWLV4op7mN0s7JAebgdUxhPJz9D3k5t3Jmtrvl0fMBRSJvdxkO+rWd0qmHGpj012GNrXHB0r5xYJeUAcEhkbAkBQq8DovLZllDo66iXL/PN2Ii9lf9FWcptK/DydPIJbXVYv9mPG2fCOsrCGKI07hHwg3pYUk9oe+HWRw8DZkEXBAkAN4F9B+7gnkOdL+QW5PKFmIB8pmPVGNOmw3X4mGEBdff1u+L+WobjhjDtsc72LmcFtZ4dEDZFqOVfb2B+tMODZAkBp+poax+T41F7WC7VTtXo+brSr5lnvGiuqGSYeM03w1gro/eWZOaK2pNgME1yeryC+LSH+JqT3h6de2J5Dm3E3";
	
	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://192.168.2.4:8088/bluebooksoft-qcloud365/notifyAlipay";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://192.168.2.4:8088/bluebooksoft-qcloud365/returnAlipay";

	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "C:\\";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	public static String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static String service = "create_direct_pay_by_user";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
//↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	// 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
	public static String anti_phishing_key = "";
	
	// 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
	public static String exter_invoke_ip = "";
		
//↑↑↑↑↑↑↑↑↑↑请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	//封装正常数据
	public static Map<String,String> getParaTemp(){
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfigPc.service);
		sParaTemp.put("partner", AlipayConfigPc.partner);
		sParaTemp.put("seller_id", AlipayConfigPc.seller_id);
		sParaTemp.put("_input_charset", AlipayConfigPc.input_charset);
		sParaTemp.put("payment_type", AlipayConfigPc.payment_type);
		sParaTemp.put("notify_url", AlipayConfigPc.notify_url);
		sParaTemp.put("return_url", AlipayConfigPc.return_url);
		sParaTemp.put("anti_phishing_key", AlipayConfigPc.anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", AlipayConfigPc.exter_invoke_ip);
		return sParaTemp;
	}
	
}

