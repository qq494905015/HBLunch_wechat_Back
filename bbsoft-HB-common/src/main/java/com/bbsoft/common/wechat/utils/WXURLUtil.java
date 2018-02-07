package com.bbsoft.common.wechat.utils;

/**
 * 微信URL常量
 * @author Chris.Zhang
 * @date 2017-5-4 12:11:13
 */
public class WXURLUtil {
	
	public static String TOKEN_KEY = "ACCESS_TOKEN";
	public static String OPEN_ID = "OPENID";
	public static String NEXT_OPEN_ID = "NEXT_OPENID";
	 // 百度地图搜索网址
	public static String BAIDUMAP_URL = "http://api.map.baidu.com/place/v2/search";
	 //	获得微信jssdk 票据jsapi_ticket
	public static String JSAPIURL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";	
	// 凭证获取accessToken（GET）
	public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//获取用户基本信息
	public final static String GET_BASE_USERINFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN ";
	//获取用户列表
	public final static String GET_USER_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	
	/**************以下AccessTokens是网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同*****************/
	//授权2.o路径
	public final static String AUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	//检验授权凭证是否有效
	public final static String CHECK_AUTH_URL = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID ";
	//获取网页授权用户信息
	public final static String AUTH_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	
	/*****************微信卡券接口******************/
	//获取微信卡券接口签名的api_ticket
	public final static String CARD_API_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=wx_card";
	//上传图片
	public final static String UPDATELOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN"; 
	//创建会员卡
	public final static String CARD_CREATE = "https://api.weixin.qq.com/card/create?access_token=ACCESS_TOKEN";
	//修改会员卡
	public final static String CARD_UPDATE = "https://api.weixin.qq.com/card/update?access_token=TOKEN";
	//激活会员卡
	public final static String CARD_ACTIVATE = "https://api.weixin.qq.com/card/membercard/activate?access_token=ACCESS_TOKEN";
	//设置测试白名单
	public final static String CARD_TEST_WHITE_LIST= "https://api.weixin.qq.com/card/testwhitelist/set?access_token=ACCESS_TOKEN";
	//修改用户会员卡信息
	public final static String CARD_USER_UPDATE = "https://api.weixin.qq.com/card/membercard/updateuser?access_token=TOKEN";
	//激活会员卡信息
	public final static String CARD_USER_ACTIVATE = "https://api.weixin.qq.com/card/membercard/activate?access_token=TOKEN";
	//支付后投放，支付及会员
	public final static String PAYGIFT_CARD = "https://api.weixin.qq.com/card/paygiftcard/add?access_token=TOKEN";
	
	/*****************微信菜单接口******************/
	//创建菜单
	public final static String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	//删除菜单
	public final static String MENU_REMOVE = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	
	
	/*****************微信消息管理接口******************/
	//发送客服消息
	public final static String CUS_SEND_MSG = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	//发送模板消息
	public final static String CUS_SEND_TEMP_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	/*****************微信用户管理接口******************/
	//获取用户基本信息
	public final static String WEUSER_BASIC = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	//获取用户列表
	public final static String WEUSER_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	
		
}
