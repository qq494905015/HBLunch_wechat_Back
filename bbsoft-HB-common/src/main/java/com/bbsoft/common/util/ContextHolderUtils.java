package com.bbsoft.common.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
* @ClassName: ContextHolderUtils 
* @Description: TODO(上下文工具类) 
 * @author: VULCAN
 * @date: 2017-2-9
*
 */
public class ContextHolderUtils {
	/**
	 * SpringMvc下获取request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;

	}
	/**
	 * SpringMvc下获取session
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		session.setMaxInactiveInterval(60*60);
		return session;

	}
	
	
	public static String getContextPath()
	{
		
		HttpServletRequest request =getRequest();
		String contextPath =request.getSession().getServletContext().getRealPath("");
		return contextPath;
	}

}

