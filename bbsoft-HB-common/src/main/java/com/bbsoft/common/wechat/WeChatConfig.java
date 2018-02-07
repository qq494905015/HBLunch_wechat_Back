package com.bbsoft.common.wechat;

/**
 * 微信接口配置信息
 * @author Chri.Zhang
 * @date 2017年3月7日
 */
public class WeChatConfig {

	/**
	 * 预支付请求地址
	 */
	public static final String PREPAY_URL  = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	/**
    * 查询订单地址
    */
   public static final String  ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

   /**
    * 关闭订单地址
    */
   public static final String  CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";

   /**
    * 申请退款地址
    */
   public static final String  REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

   /**
    * 查询退款地址
    */
   public static final String  REFUND_QUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";

   /**
    * 下载账单地址
    */
   public static final String  DOWN_LOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
   
   /**
    * 支付成功回调地址，不能有参数，支持GET,POST
    */
   public static final String NOTIFY_URL = "http://wx.wan-we.com/wxPaySuccess";
   
   /**
    * 商户APPID
    */
   public static final String  APP_ID = "wxc149f0db5026b4ec"; 	//万味网
   
   
   /**
    * 商户APPID 商户秘钥  32位，在微信商户平台中设置
    */
   public static final String  API_KEY = "hebenbiandanghongmihehulianwang1";	//万味网

   /**
    * 商户账户 获取支付能力后，从邮件中得到
    */
   public static final String  MCH_ID = "1432276502";
   
	public static final int AGENTID = 75;
	public static final String TOKEN = "Ueb2VZnQHxqZRfe3fNDQk2ed8OVrMqbm";//万味网
	/**
	 * AppId应用ID
	 */
	public static final String CORPID = "wxc149f0db5026b4ec";	//万味网
	public static final String SECRET = "8b5360f32e78e4bb9c2a6a52757a114a";	// 正式,万味网
	
	public static final String encodingAESKey = "y9fx5rCcbovFc0aMvABkCIFbxKluzG2vGjh0fPngJC0";//万味网
}
