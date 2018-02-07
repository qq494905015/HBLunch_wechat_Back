package com.bbsoft.common.wechat.service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.bbsoft.common.constant.EnumMethod;
import com.bbsoft.common.domain.AccessToken;
import com.bbsoft.common.util.HttpRequestUtil;
import com.bbsoft.common.wechat.WeChatConfig;
import com.bbsoft.common.wechat.messge.TemplateMessage;
import com.bbsoft.common.wechat.utils.AccessTokenUtil;
import com.bbsoft.common.wechat.utils.WXURLUtil;

import net.sf.json.JSONObject;

/**
 * 微信消息信息接口
 * @author Chris.Zhang
 * @date 2017-6-20 11:03:30
 *
 */
public class MessageService {

	/**
	 * 发送客户文本消息
	 * @param openId 用户微信唯一标识
	 * @param content 发送消息内容
	 * @return
	 */
	public static JSONObject sendCustomTextMsg(String openId, String content){
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		String requestUrl = WXURLUtil.CUS_SEND_MSG.replace(WXURLUtil.TOKEN_KEY, accessToken.getToken());
		JSONObject reqJson = new JSONObject();
		reqJson.put("touser", openId);
		reqJson.put("msgtype", "text");
		JSONObject contentJson = new JSONObject();
		contentJson.put("content", content);
		reqJson.put("text", contentJson);
		JSONObject resultJson = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.POST.name(), reqJson.toString());
		return resultJson;
	}
	
	/**
	 * 发送模板消息
	 * @param templateMessage 模板消息
	 * @return
	 */
	public static JSONObject sendTemplateMsg(TemplateMessage templateMessage){
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		String requestUrl = WXURLUtil.CUS_SEND_TEMP_MSG.replace(WXURLUtil.TOKEN_KEY, accessToken.getToken());
		JSONObject resultJson = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.POST.name(), JSONObject.fromObject(templateMessage).toString());
		return resultJson;
	}
	
	public static void main(String[] args) {
//		System.out.println(WeUserService.getUserList(""));
//		System.out.println(sendCustomTextMsg("ogppj1cFieHCww-j5cGzLd0dkeN8", "你消费成功测试"));
Map<String, Object> data = new HashMap<String, Object>();
		
		Map<String, Object> first = new HashMap<String, Object>();
		first.put("color", "#173177");
		first.put("value", "你好，你有一张会员卡可以绑定为微信会员卡。");
		data.put("first", first);
		
		Map<String, Object> keynote1 = new HashMap<String, Object>();
		keynote1.put("color", "#173177");
		keynote1.put("value", "a12313123");
		data.put("keynote1", keynote1);
		
		Map<String, Object> keynote2 = new HashMap<String, Object>();
		keynote2.put("color", "#173177");
		keynote2.put("value", "2017-10-31 21:31:57");
		data.put("keynote2", keynote2);
		
		Map<String, Object> remark = new HashMap<String, Object>();
		remark.put("color", "#173177");
		remark.put("value", "点击详情，快速绑定微信会员卡");
		data.put("remark", remark);
		String domain = "http://wx.wan-we.com/HeBenWechat#/";
		String redirectUri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChatConfig.APP_ID 
			+ "&redirect_uri=CUS_URL&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
		TemplateMessage message = new TemplateMessage("ogMZuvwYQiuwK9Uv5tI1Tw0vBlS8", "xj7eIuXw8fxZD0C6GGPZf6hoXITzLKNxFdlggDDSN2U", redirectUri.replace("CUS_URL", URLEncoder.encode(domain + "bind")), data);
		System.out.println(sendTemplateMsg(message));
	}
}
