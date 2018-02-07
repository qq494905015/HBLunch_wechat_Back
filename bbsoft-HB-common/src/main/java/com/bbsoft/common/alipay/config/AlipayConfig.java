package com.bbsoft.common.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
		// 调用地址沙箱模式和正式上线模式： https://openapi.alipaydev.com/gateway.do
		public static String URL = "https://openapi.alipay.com/gateway.do";

		// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
		public static String partner = "2088311771079114";
		
		//应用的 APPID: 2017022705933121
		public static String APP_ID = "2017022705933121";
		
		// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
		public static String seller_id = partner;

		//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
		public static String private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCbSn2aWbWE2dh3H4hmk60ASHL3fVWv/4Fph1gyXbiagix0hgDb7IKihw7yHmNtAjfWcaTOFYldxGbJ/efGRcRy1nwl3UKUcfKQxN+zeOGpzQFfSL/cOP5t6H8PQWYTtmmykHyGIDWdIR2M1Omw+Pjwki8RLmH3q+7qsDkybuHU41lYBePSJRG+ou9uaiad43pwzydoGk+xhweEq+gitHYy10S/iZmv839szH5D/jDVxFr8zdaiCwQCpGJyBH47E/pzQYm7+OR9TpFl6DE06beVNSsIa4jZBx1s4uq1a6elUzJq5as7+pMb7xGBgi5GwRZNkkEGVn3wrq4CdtR52mxZAgMBAAECggEAdU4l50zQ49Qzwnidbu8rBkW0IuQYsGRP+8lzhEhrxLuEnVkEmRzl6aQgVCFEdfnw+BrMpah7JcvQ4dDdHldheGYxTeEABGa3F0jAuzAbN0tXOAUaeV7MQ3YXVQzP8SAgY/TlO+Rk25dCXToQddD9K60hxNbGxnKofbw02LYN/eO6EnEBSOrVOq8tjrs84PuRh/RJFLrYJ+t70gn/4Zn/Tfp56A8OLr03HrEd6MgxIoEZ9Q1krDfj3dSdNM1WXFdap34moDozT2iv0ubLm9nhdiwfXi3pS1K7ooDPKmf9Tx2S8mNT0LyujgLctnkwN/ZZ3iiIA8mQs1AEsSkiw11mAQKBgQDOsoI7ehK/k6e0ff+1pbv8TpuieNnFCm7E5iuQhmbKQjmndyRQ8fRkg05zaN2YTSkHDn2+Wk4G+F3OPWs9ywvl9RgTyGk8V+4IloI4Z7JausOTzs+ma/XpySYRIgA29TejPcGKoOnTFbbKmybuDkRh33YBFbz6+t+FMoqNPwiSoQKBgQDAVPliUUlXMlptwtVsPejzbT68NVIeZawH2NUI5fYG/hpfacEpr4fph1VftChPwIk6BeAu+7sLLemQ+1IiAzkTft8URH6qwRbHJ6pnSKcftcXB/Qc1QAwro4ID0+WvrSp47jUg/PD2Pqj+HXB9eLA9ox9PZlZThB0o/gTaBiq2uQKBgDf7VRZyy50jiqpx7c3u42lEiRaKEMEzQCkIBgiQvkpNgXv/4tAhmAWZIqhTYKEdVN1aaWgi4Ts2DYIMVH1Z/xD7Ptgs0YPMdOXSjdZlD4NPTiU8QDVczC/TkQHW6aP46mbGFSNMzcej++wH9ZVIO/EeKsHEd3iqdduHgssapWHBAoGBAIsZabunmmlL64bSkFRWejID4olv7tI5KjAKrBhhaIwqHpy++YrSfnNxA1fSDg2P7C9grICOYvp03CWU/hPHTPJCocgguZN5TqX+lAlazDPs3PetrhbfGW4+NPvbtpRVoxAPB7Gx/fu52bLfS8oTDTOm1DAyjWCdfhF1lcCxERdRAoGAP8k+gt4z25de9X+3faKGOXk+g+G23eFprpM5YoweDcM6YtHWgCO+ZXe/XfAcBXSHeL4CsS2H/tu7SUpTBvinaf9/wUj0RVs0Ihp7l6SE3mkWTbcXlQTii+0B835xvhnwCs3Jr40rFhs99tXKuHeF1be/PTirL+2uAqHCR3G74VU=";
		
		// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm    MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuUfSg/1Q9Zow8EDHFCHnDuro/ZKtLFDF91BFgHKirXvKR5cELwoZa+Sia2oOjvNECYHWKT9cCZGw9+jl5pa6jpvBiHu5gRI6rIjqGI8U30IlVWx0YOJZQEzKlRak4LDDhrtjtU4/iBLWJX9A+7XrCA5M3ncs1Ep6ESFCGwIL8gsXtFBNswm2mDJkB1iYlZHed2Uiv6gIKVOgKhfaVfQavlYxC9T/i32FR6l3guC21Bdx2V5K0RbwupuWaFdKTMHRw3sgSA8HwAsIA1+aZsft6NC8EU1ybCeyTdAsW4+BY1AULgrFTBmx7Wj2kXXOI52SksyLlmqhyUoX8mjA88RDowIDAQAB
		public static String alipay_public_key  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlx10tXUNGnalr5b4/w7wBP4xVjvMBE1zi67EErkvLoxOaPXr4zrnvPiGHAAh8d0hs7ujqxSdQSV3Lmo4hZipXQX3rqFPM4QNmn1lMeb4aaeWB1yjySBbLU1gOIxD1SM6Eon6tMNAKgzEG88UeGfX8R+J3ZE4d8NQtZ7j06P78nSg45e6PiDUmFSYj30gxg8SYccIn4R0dcxmncF9Do3CO6/aK34M2EcHGO0q6J+uW6d/dSgv2rnJZJYoJUlwfMlSPVJDrzLN3LxfjZ1+8uganiMSInW7hAVnZMQmEncUIuzmeXkjjfJUGejO01vu7599Gq4lwdNoqNzU+bysruOO1QIDAQAB";
		
		//服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String notify_url = "https://apim.cloudtool365.com/qcloud365-api/callBackAliPay";

		// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String return_url = "http://bbsoft.com/return_url.jsp";

		// 签名方式
		public static String sign_type = "RSA2";
		
		// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
		public static String log_path = "C:\\";
			
		// 字符编码格式 目前支持utf-8
		public static String input_charset = "utf-8";
			
		// 支付类型 ，无需修改
		public static String payment_type = "1";
		
			
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

}

