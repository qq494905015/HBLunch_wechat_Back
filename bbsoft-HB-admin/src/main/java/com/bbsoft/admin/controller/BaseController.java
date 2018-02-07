package com.bbsoft.admin.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.constant.SessionSecurityConstants;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.core.sysuser.domain.SysUser;

/**
 * 
 * ClassName: BaseApi 
 * @Description: TODO
 * @author: VULCAN
 * @date: 2016-12-29
 */
public class BaseController {
	
	public final static String SYSUSER_ID = "SYSUSER_ID";
	
	public static void pushSession(HttpServletRequest request, SysUser sysUser){
		HttpSession session = request.getSession();
		session.setAttribute(SessionSecurityConstants.KEY_USER_ID, sysUser.getId());
		session.setAttribute(SessionSecurityConstants.KEY_USER_NAME, sysUser.getUserName());
		session.setAttribute(SessionSecurityConstants.KEY_USER_NICK_NAME, sysUser.getNickName());
	}
	
	public static void clearSession(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
	public static String getSessionId(HttpServletRequest request){
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute(SessionSecurityConstants.KEY_USER_ID);
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10102.getCode());
		}
		return id;
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
