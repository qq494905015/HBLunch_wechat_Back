package com.bbsoft.core.interceptor;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;

import net.sf.json.JSONObject;

/**
 * 自定义异常拦截器
 * @author Chris
 * @date 2017-2-21 19:33:18
 *
 */
public class ServiceExceptionInterceptor implements HandlerExceptionResolver{
	
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exception) {
		JSONObject json = new JSONObject();
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		json.put("success", false);
		json.put("results", new HashMap<String,Object>());
		//处理自定异常
		if(exception.getClass().equals(ServiceException.class)){
			ServiceException serviceException = (ServiceException) exception;
			json.put("errorCode", serviceException.getCode());
			json.put("errorMsg", serviceException.getMessage());
		}else if(exception.getClass().equals(RuntimeException.class)){//处理运行时异常
			json.put("errorCode", MsgeData.SYSTEM_10100.getCode());
			json.put("errorMsg", MsgeData.SYSTEM_10100.getMsg());
			 
		}else if(exception.getClass().equals(Exception.class)){//处理异常
			json.put("errorCode", MsgeData.SYSTEM_10100.getCode());
			json.put("errorMsg", MsgeData.SYSTEM_10100.getMsg());
		}else{  
			json.put("errorCode", MsgeData.SYSTEM_10100.getCode());
			json.put("errorMsg", MsgeData.SYSTEM_10100.getMsg());
		}
		//写到前台
		try {
			response.getWriter().write(json.toString());
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView();
		}
	}
		
