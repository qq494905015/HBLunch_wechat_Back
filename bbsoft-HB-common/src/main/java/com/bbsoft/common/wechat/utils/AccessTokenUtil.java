package com.bbsoft.common.wechat.utils;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbsoft.common.constant.EnumMethod;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.AccessToken;
import com.bbsoft.common.domain.SNSUserInfo;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.domain.WXjsTicket;
import com.bbsoft.common.domain.WechatAuth2Token;
import com.bbsoft.common.util.HttpRequestUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.common.wechat.WeChatConfig;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 微信凭证
 * @author Chris.Zhang
 * @date 2017-5-25 13:38:13
 *
 */
public class AccessTokenUtil {
	
	private static Logger log = LoggerFactory.getLogger(AccessTokenUtil.class);
	public static AccessToken accessToken = new AccessToken();	//基础接口所需要的token标识
	public static WXjsTicket ticket = new WXjsTicket();		//JsSdk权限配置Ticket
	public static WXjsTicket cardTicket = new WXjsTicket();	//卡券ApiTicket
	
	/**
	 * 获取网页授权凭证
	 * @return
	 */
	public static WechatAuth2Token getOauth2AccessToken(String code){
		return getAuth2AccessToken(WeChatConfig.APP_ID, WeChatConfig.SECRET, code);	
	}

	/**
	 * 获取网页授权凭证
	 * @param appId 微信公众号唯一标识
	 * @param appSecret 公众账号的密钥
	 * @param code
	 * @return
	 */
	public static WechatAuth2Token getAuth2AccessToken(String appId, String appSecret, String code){
		WechatAuth2Token auth2Token = null;
		String requestUrl = WXURLUtil.AUTH_URL;
		requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        JSONObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);
        if (jsonObject != null) {
            try {
                auth2Token = new WechatAuth2Token();
                auth2Token.setAccessToken(jsonObject.getString("access_token"));
                auth2Token.setExpiresIn(jsonObject.getInt("expires_in"));
                auth2Token.setRefreshToken(jsonObject.getString("refresh_token"));
                auth2Token.setOpenId(jsonObject.getString("openid"));
                auth2Token.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                auth2Token = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                throw new ServiceException(MsgeData.SYSTEM_10126.getCode());
            }
        }
        return auth2Token;
	}
	
	/**
	 * 检验网页授权Token是否有效
	 * @param accessToken 需要检验的token
	 * @param openId 微信用户唯一标识
	 * @return
	 */
	public static boolean checkAuthToken(String accessToken, String openId){
		if(StringUtil.isEmpty(accessToken) || StringUtil.isEmpty(openId)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		String requestUrl = WXURLUtil.CHECK_AUTH_URL.replace(WXURLUtil.TOKEN_KEY, accessToken).replace(WXURLUtil.OPEN_ID, openId);
		JSONObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);
		if(jsonObject != null){
			int errcode = jsonObject.getInt("errcode");
			if(errcode == 0){
				return true;
			}
		}
		return false;
	}
	
	/**
     * 通过网页授权获取用户信息
     * 
     * @param accessToken 网页授权接口调用凭证
     * @param openId 用户标识
     * @return SNSUserInfo
     */
    @SuppressWarnings( { "deprecation", "unchecked" })
    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
    	SNSUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = WXURLUtil.AUTH_USER_INFO;
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);

        if (jsonObject != null) {
            try {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 用户特权信息
                snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return snsUserInfo;
    }
	
	/**
	 * 获取微信Token凭证
	 * @return
	 */
	public static AccessToken getAccessToken(){
		if(accessToken != null 
			&& !StringUtil.isEmpty(accessToken.getToken())
			&& (new Date().getTime() / 1000) - (accessToken.getCreateTime().getTime() / 1000) < accessToken.getExpiresIn()){
			return accessToken;
		}else{
			return getAccessToken(WeChatConfig.APP_ID, WeChatConfig.SECRET);	
		}
	}
	
	/**
	 * 获取微信Token凭证
	 * @param appid AppId应用ID
	 * @param appsecret 应用密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		String requestUrl = WXURLUtil.TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);
		// 如果请求成功
		if (jsonObject != null) {
			log.debug("accessToken======" + jsonObject.toString());
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in") - 600);
				accessToken.setCreateTime(new Date());
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				throw new ServiceException(MsgeData.SYSTEM_10126.getCode());
			}
		}else{
			throw new ServiceException(MsgeData.SYSTEM_10126.getCode());
		}
		
		return accessToken;
	}
	
	/**
	 * 获取微信JsSDK权限Ticket
	 * @param accessToken  基础接口Token
	 * @return
	 */
	public static WXjsTicket getWXjsTicket(String accessToken) {
		WXjsTicket wXjsTicket = null;
		if(ticket != null
			&& !StringUtil.isEmpty(ticket.getJsTicket())
			&& (new Date().getTime() / 1000) - (ticket.getCreateTime().getTime() / 1000) < ticket.getJsTicketExpiresIn()
			){
			return ticket;
		}
		String requestUrl= WXURLUtil.JSAPIURL.replace("ACCESS_TOKEN", accessToken);
		// 发起GET请求获取凭证
		JSONObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);
		System.out.println("WechatAccessToken.java 调用了一次getWXjsTicket接口");
		if (null != jsonObject) {
			try {
				wXjsTicket = new WXjsTicket();
				wXjsTicket.setJsTicket(jsonObject.getString("ticket"));
				wXjsTicket.setJsTicketExpiresIn(jsonObject.getInt("expires_in") - 600);
				wXjsTicket.setCreateTime(new Date((System.currentTimeMillis() / 1000)));
				ticket = wXjsTicket;
			} catch (JSONException e) {
				wXjsTicket = null;
				// 获取wXjsTicket失败
				log.error("获取wXjsTicket失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return wXjsTicket;
	}
	
	/**
	 * 获取微信JS卡券Ticket
	 * @param accessToken 基础接口Token
	 * @return
	 */
	public static WXjsTicket getCardTicket(String accessToken){
		WXjsTicket wXjsTicket = null;
		if(cardTicket != null
			&& !StringUtil.isEmpty(cardTicket.getJsTicket())
			&& (new Date().getTime() / 1000) - (cardTicket.getCreateTime().getTime() / 1000) < cardTicket.getJsTicketExpiresIn()
			){
			return ticket;
		}
		String requestUrl= WXURLUtil.CARD_API_TICKET.replace("ACCESS_TOKEN", accessToken);
		// 发起GET请求获取凭证
		JSONObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);
		System.out.println("WechatAccessToken.java 调用了一次getCardTicket接口");
		if (null != jsonObject) {
			try {
				wXjsTicket = new WXjsTicket();
				wXjsTicket.setJsTicket(jsonObject.getString("ticket"));
				wXjsTicket.setJsTicketExpiresIn(jsonObject.getInt("expires_in") - 600);
				wXjsTicket.setCreateTime(new Date((System.currentTimeMillis() / 1000)));
				cardTicket = wXjsTicket;
			} catch (JSONException e) {
				wXjsTicket = null;
				// 获取wXjsTicket失败
				log.error("获取wXjsCardTicket失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return wXjsTicket;
	}

}