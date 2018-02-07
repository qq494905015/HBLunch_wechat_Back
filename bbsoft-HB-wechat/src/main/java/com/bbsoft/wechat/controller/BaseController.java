package com.bbsoft.wechat.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.constant.SessionSecurityConstants;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.core.user.domain.BaseUser;

/**
 * 
 * ClassName: BaseApi 
 * @Description: TODO
 * @author: VULCAN
 * @date: 2016-12-29
 */
public class BaseController {
	
	public final static String SYSUSER_ID = "SYSUSER_ID";
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);
	//二维码缓存信息
	public static Map<String, Map<String, Object>> codeCacheMap = new HashMap<String, Map<String, Object>>();
	
	public static void pushSession(HttpServletRequest request, BaseUser userInfo){
		HttpSession session = request.getSession();
		session.setAttribute(SessionSecurityConstants.KEY_USER_ID, userInfo.getId());
		session.setAttribute(SessionSecurityConstants.KEY_USER_NAME, userInfo.getUserName());
		session.setAttribute(SessionSecurityConstants.KEY_USER_NICK_NAME, userInfo.getNickName());
	}
	
	public static void clearSession(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
	/**
	 * 获取当前的登录的用户ID
	 * @param request
	 * @return
	 */
	public static String getUserId(HttpServletRequest request){
		HttpSession session = request.getSession();
		Object obj = session.getAttribute(SessionSecurityConstants.KEY_USER_ID);
		String id = obj==null?"":obj.toString();
		if(StringUtils.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10102.getCode());
		}
		logger.debug("当前的登录的用户userId==========================" + id);
		return id;
	}
	
	/**
	 * 获取微信唯一标识
	 * @param request
	 * @return
	 */
	public static String getOpenId(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		String openId = null;
		if(cookies != null && cookies.length > 0){
			for(Cookie cookie : cookies){
				if("openId".equals(cookie.getName())){
					openId = cookie.getValue();
					break;
				}
			}
		}
		logger.debug("当前的OpenId==========================" + openId);
		return openId;
	}
	
	protected Json getSuccessObj() {
		Json json = new Json();
		json.setErrorMsg("操作成功");
		json.setErrorCode("0");
		json.setSuccess(true);
		json.setResults(new HashMap<String, Object>());
		return json;
	}
	
	protected Json getSuccessObj(Object object) {
		Json json = new Json();
		json.setErrorMsg("操作成功");
		json.setErrorCode("0");
		json.setSuccess(true);
		json.setResults(object);
		return json;
	}

	protected Json getSuccessObj(String msg) {
		Json json = new Json();
		json.setErrorMsg(msg);
		json.setErrorCode("0");
		json.setSuccess(true);
		json.setResults(new HashMap<String, Object>());
		return json;
	}

	protected Json getSuccessObj(String code, String msg) {
		Json json = new Json();
		json.setErrorMsg(msg);
		json.setErrorCode(code);
		json.setSuccess(true);
		json.setResults(new HashMap<String, Object>());
		return json;
	}

	protected Json getFailedObj() {
		Json json = new Json();
		json.setErrorMsg("操作失败");
		json.setErrorCode("-1");
		json.setSuccess(false);
		json.setResults(new HashMap<String, Object>());
		return json;
	}

	protected Json getFailedObj(String msg) {
		Json json = new Json();
		json.setErrorMsg(msg);
		json.setErrorCode("-1");
		json.setSuccess(false);
		json.setResults(new HashMap<String, Object>());
		return json;
	}

	protected Json getFailedObj(String code, String msg) {
		Json json = new Json();
		json.setErrorMsg(msg);
		json.setErrorCode(code);
		json.setSuccess(false);
		json.setResults(new HashMap<String, Object>());
		return json;
	}
}
