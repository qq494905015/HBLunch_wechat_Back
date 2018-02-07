package com.bbsoft.wechat.interceptor;



import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.constant.SessionSecurityConstants;
import com.bbsoft.common.domain.ServiceException;

/**
 * Controller方法拦截器：处理后台session
 * @author jorge.zheng
 * @date 2017-2-21
 *
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
	
	private List<String> excludeUrls;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 完成页面的render后调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
	    logger.debug("afterCompletion");
	    super.afterCompletion(request, response, object, exception);
	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
        logger.debug("postHandle");
        super.postHandle(request, response, object, modelAndView);
	}

	/**
	 * 在调用controller具体方法前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
//		HttpSession httpSession = request.getSession();//7B86A1B6DC2B364DF4B0D5AD88A43357   
		String method = request.getMethod();
		if ("OPTIONS".equals(method)) {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, PUT, OPTIONS");
		}
		if (excludeUrls.contains(url)) {
			return true;
		} else {
			if(isLogin(request)){//判断登录，并跳转
				return true;
			}else{
				forward(request, response);
				return false;
			}
		}
	}
	
	/**
	 * 判断是否登录
	 * @param httpServletRequest
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	protected boolean isLogin(HttpServletRequest httpServletRequest) throws ServletException, IOException {
		HttpSession httpSession = httpServletRequest.getSession();
		String userId = (String) httpSession.getAttribute(SessionSecurityConstants.KEY_USER_ID);
		if (userId == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 重定向到首页
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws Exception 
	 */
//	private void redirectToIndex(HttpServletRequest request,HttpServletResponse response) throws IOException, Exception {
//		response.sendRedirect(INDEXPAGE);
//		request.getRequestDispatcher(INDEXPAGE).forward(request,response);
//	}
	
	/**
	 * 重定向到登录页
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws Exception 
	 */
	private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("forward");
		throw new ServiceException(MsgeData.SYSTEM_10102.getCode());
//		response.sendRedirect("/#/login");
//		request.getRequestDispatcher("/timeout.jsp").forward(request, response);
	}

}
