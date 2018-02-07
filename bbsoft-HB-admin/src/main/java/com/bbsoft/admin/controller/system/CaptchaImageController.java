package com.bbsoft.admin.controller.system;


import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bbsoft.admin.controller.BaseController;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * ClassName: CaptchaImageCreateController 
 * @Description: TODO
 * @author: VULCAN
 * @date: 2016-09-29
 */

@Controller
public class CaptchaImageController extends BaseController{
	
	private Producer captchaProducer = null;

	@Autowired
	public void setCaptchaProducer(Producer captchaProducer) {
		this.captchaProducer = captchaProducer;
	}

	@RequestMapping("/cap100000")
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = captchaProducer.createText();
		// store the text in the session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
		
//		String jsessionid = "";
//		Cookie cookie = WebUtils.getCookie(request, "JSESSIONID");
//		if (cookie != null && StringUtils.isNotBlank(cookie.getValue())) {
//			jsessionid = cookie.getValue();
//		}
//		if("".equals(jsessionid)){
//			jsessionid = request.getSession(true).getId();
//		}
//		Cookie cookie = WebUtils.getCookie(request, CAPTCHA);
//		if (cookie != null && StringUtils.isNotBlank(cookie.getValue())) {
//			jsessionid = cookie.getValue();
//		}else{
//			jsessionid = UUID.randomUUID().toString();
//			Cookie newCookie = new Cookie(CAPTCHA, jsessionid);
//			newCookie.setPath("/");
//			response.addCookie(newCookie);
//		}
		//将值存入redis
//		redis.setExForString(jsessionid, 1800, capText);
		
		
		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		// write the data out
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
	}

}