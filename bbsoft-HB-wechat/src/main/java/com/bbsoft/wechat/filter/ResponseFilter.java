package com.bbsoft.wechat.filter;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbsoft.common.util.ResultUtil;


/**
 * 参数返回过滤器
 * @author Chris.Zhang
 * @date 2017年3月7日
 */
public class ResponseFilter implements Filter {
	
	private Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

	public void destroy() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("rawtypes")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		//请求参数
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		String requestUri = servletRequest.getRequestURI();
		String contextPath = servletRequest.getContextPath();
		String url = requestUri.substring(contextPath.length());
		Map<String, String[]>  requestParams = request.getParameterMap();
		Map<String, String> params = new HashMap<String, String>();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			 valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		logger.debug("入参({})：{}",url,ResultUtil.returnByObj(params));
		
		WrapperResponse wrapperResponse = new WrapperResponse((HttpServletResponse) response);
        filterChain.doFilter(request, wrapperResponse);
        //输出参数
        byte[] content = wrapperResponse.getContent();
        if (logger.isDebugEnabled() && content != null && content.length > 0) {
        	if(!url.contains("cap")){
        		logger.debug("出参({})：{}",url, new String(content,"utf-8"));
        	}
            ServletOutputStream out = response.getOutputStream();
            out.write(content);
            out.flush();
        }
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
