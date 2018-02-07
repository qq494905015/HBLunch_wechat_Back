package com.bbsoft.common.wechat.service;

import com.bbsoft.common.constant.EnumMethod;
import com.bbsoft.common.domain.AccessToken;
import com.bbsoft.common.util.HttpRequestUtil;
import com.bbsoft.common.wechat.utils.AccessTokenUtil;
import com.bbsoft.common.wechat.utils.WXURLUtil;

import net.sf.json.JSONObject;

/**
 * 微信用户管理接口
 * @author Chris.Zhang
 *
 */
public class WeUserService {

	/**
	 * 获取指定微信用户基本信息
	 * @param openId 微信用户ID
	 * @return
	 */
	public static JSONObject getBasicUser(String openId){
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		String requestUrl = WXURLUtil.WEUSER_BASIC.replace(WXURLUtil.TOKEN_KEY, accessToken.getToken()).replace(WXURLUtil.OPEN_ID, openId);
		JSONObject resultJson = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);
		return resultJson;
	}
	
	/**
	 * 获取微信用户列表
	 * @param nexOpenId 从第几个开始，不传从0开始
	 * @return
	 */
	public static JSONObject getUserList(String nexOpenId){
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		String requestUrl = WXURLUtil.WEUSER_LIST.replace(WXURLUtil.TOKEN_KEY, accessToken.getToken()).replace(WXURLUtil.NEXT_OPEN_ID, nexOpenId);
		JSONObject resultJson = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);
		return resultJson;
	}
	
	/**
	 * 获取二维码信息
	 * @param code 卡号
	 * @return
	 */
	public static JSONObject getQrcode(String code){
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN".replace(WXURLUtil.TOKEN_KEY, accessToken.getToken());
		JSONObject reqJson = new JSONObject();
		reqJson.put("expire_seconds", "120");
		reqJson.put("action_name", "QR_SCENE");
		JSONObject actionInfo = new JSONObject();
		JSONObject scene = new JSONObject();
		scene.put("scene_id", code);
		actionInfo.put("scene", scene);
		reqJson.put("action_info", actionInfo);
		JSONObject resultJson = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.POST.name(), reqJson.toString());
		return resultJson;
	}
	
	public static void main(String[] args) {
//		System.out.println(getUserList(""));
		System.out.println(getBasicUser("ogMZuv1fZSF-BWYl9x0dT-6oYNUg"));
	}
}
