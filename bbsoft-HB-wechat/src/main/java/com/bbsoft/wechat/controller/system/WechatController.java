package com.bbsoft.wechat.controller.system;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.common.aes.WXBizMsgCrypt;
import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.AccessToken;
import com.bbsoft.common.domain.SNSUserInfo;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.domain.WXjsTicket;
import com.bbsoft.common.domain.WechatAuth2Token;
import com.bbsoft.common.util.BeanToMapUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.common.wechat.WeChatConfig;
import com.bbsoft.common.wechat.utils.AccessTokenUtil;
import com.bbsoft.common.wechat.utils.WeMenuUtil;
import com.bbsoft.common.wechat.utils.WeixinJSSDKSignUtil;
import com.bbsoft.core.user.domain.WechatUser;
import com.bbsoft.core.user.service.WechatUserService;
import com.bbsoft.wechat.WechatCoreService;
import com.bbsoft.wechat.controller.BaseController;

/**
 * 微信基本接口
 * @author Chris.Zhang
 * @date 2017-5-24 11:34:22
 *
 */
@Controller
public class WechatController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private WechatUserService wechatUserService;
	
	@RequestMapping(value = "wechatApi", method = RequestMethod.GET)
	public void wechatApiGet(HttpServletRequest request, HttpServletResponse response){
		// 微信加密签名
		String msg_signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		
		logger.debug("request=" + request.getRequestURL());
		try {
			PrintWriter out = response.getWriter();
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			String result = null;
			try {
				WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(WeChatConfig.TOKEN, WeChatConfig.encodingAESKey,
						WeChatConfig.CORPID);
				result = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException(MsgeData.SYSTEM_10100.getCode());
			}
			if (result == null) {
				result = WeChatConfig.TOKEN;
			}
			out.print(result);
			out.close();
			out = null;
		} catch (Exception e) {
			throw new ServiceException(MsgeData.SYSTEM_10100.getCode());
		}
	}
	
	@RequestMapping(value = "wechatApi", method = RequestMethod.POST)
	public void wechatApiPost(HttpServletRequest request, HttpServletResponse response){
		logger.info("微信请求开始");
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        try {
			request.setCharacterEncoding("UTF-8");
			//微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
			response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
			//初始化配置文件
			WechatCoreService wechatCoreService = new WechatCoreService();
			String respMessage = wechatCoreService.processRequest(request);//调用CoreService类的processRequest方法接收、处理消息，并得到处理结果；
			logger.info("respMessage:[{0}]", respMessage);
			// 响应消息  
			//调用response.getWriter().write()方法将消息的处理结果返回给用户
			PrintWriter printWriter = response.getWriter();
			if(printWriter != null && StringUtil.isNotEmpty(respMessage)){
				logger.info("printWriter:[{0}]", printWriter);
				printWriter.write(respMessage);
				printWriter.flush();
				printWriter.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("微信请求结束");
	}
	
	/**
	 * 获取微信TOKEN
	 * @param response
	 * @return
	 */
	@RequestMapping("getWechatToken")
	@ResponseBody
	public Json getWechatTokenK(HttpServletRequest request){
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		return getSuccessObj(ResultUtil.returnByObj(accessToken));
	}
	
	/**
	 * 初始化菜单
	 * @param request
	 * @return
	 */
	@RequestMapping("initMenu")
	@ResponseBody
	public Json initMenu(HttpServletRequest request){
		WeMenuUtil.init();
		return getSuccessObj();
	}
	
	/**
	 * 微信网页授权后回调处理
	 * @param request
	 * @param code 
	 * @return
	 */
	@RequestMapping("wechatAuth")
	@ResponseBody
	public Json wechatAuth(HttpServletRequest request, HttpServletResponse response, String code, String state){
		if(StringUtil.isEmpty(code)){
			throw new ServiceException(MsgeData.SYSTEM_10125.getCode());
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//用户同意授权
		if(!"authdeny".equals(code)){
			logger.debug("==================================用户同意授权==================================");
			String openId = getOpenId(request);
			if(StringUtil.isEmpty(openId)){
				// 获取网页授权access_token
				WechatAuth2Token auth2Token = AccessTokenUtil.getOauth2AccessToken(code);
				// 网页授权接口访问凭证
				String accessToken = auth2Token.getAccessToken();
				// 用户标识
	            openId = auth2Token.getOpenId();
            //获取微信用户openid存储在cookie中的信息
            Cookie userCookie = new Cookie("openId", openId);
            userCookie.setMaxAge(-1);
            userCookie.setPath("/");
            response.addCookie(userCookie);
            // 获取用户信息
            SNSUserInfo snsUserInfo = AccessTokenUtil.getSNSUserInfo(accessToken, openId);
            
            //同步微信用户到平台
            WechatUser wechatUser = 
            		new WechatUser(openId, snsUserInfo.getNickname(), snsUserInfo.getSex() + "", snsUserInfo.getProvince(), snsUserInfo.getCity(), snsUserInfo.getCountry(), snsUserInfo.getHeadImgUrl());
            wechatUserService.addWechatUser(wechatUser);
            
            resultMap.put("snsUserInfo", BeanToMapUtil.convertBean(snsUserInfo));
			}else{
				WechatUser wechatUser = wechatUserService.getByOpenId(openId);
				if(wechatUser == null){
					WechatUser editWechatUser = new WechatUser();
					editWechatUser.setOpenId(openId);
					wechatUserService.addWechatUser(editWechatUser);
					wechatUser = editWechatUser;
				}
	            resultMap.put("snsUserInfo", BeanToMapUtil.convertBean(wechatUser));
			}
			resultMap.put("state", state);
		}
		return getSuccessObj(ResultUtil.returnByObj(resultMap));
	}
	

	/**
	 * 获取当前的微信用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping("getWechatUser")
	@ResponseBody
	public Json getWechatUser(HttpServletRequest request){
		String openId = getOpenId(request);
		WechatUser wechatUser = wechatUserService.getByOpenId(openId);
		return getSuccessObj(ResultUtil.returnByObj(wechatUser));
	}
	
	/**
	 * 获取微信JSSDK签权信息
	 * @param response
	 * @return
	 */
	@RequestMapping("getWechatJSSDK")
	@ResponseBody
	public Json getWechatJSSDK(HttpServletRequest request){
		String queryString = request.getQueryString();
		String url2 = "http://wx.wan-we.com" // 服务器地址
				+ request.getContextPath() // 项目名称
				+ request.getServletPath(); // 请求页面或其他地址
		if (queryString != null) {
			url2 += "?" + queryString;
		}
		url2 = request.getParameter("signUrl");
		if(StringUtil.isEmpty(url2)){
			url2 = "http://wx.wan-we.com/HeBenWechat/";
		}
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		WXjsTicket wXjsTicket = AccessTokenUtil.getWXjsTicket(accessToken.getToken());
		Map<String, String> sign = WeixinJSSDKSignUtil.sign(wXjsTicket.getJsTicket(), url2);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("appId", WeChatConfig.CORPID);
		retMap.put("nonceStr", sign.get("nonceStr"));
		retMap.put("timestamp", sign.get("timestamp"));
		retMap.put("signature", sign.get("signature"));
		return getSuccessObj(ResultUtil.returnByObj(retMap));
	}
	
	/**
	 * 获取微信卡券api_ticket
	 * @param request
	 * @return
	 */
	@RequestMapping("getCardApiTicket")
	@ResponseBody
	public Json getCardApiTicket(HttpServletRequest request){
		String queryString = request.getQueryString();
		String url2 = "http://" + request.getServerName() // 服务器地址
				+ request.getContextPath() // 项目名称
				+ request.getServletPath(); // 请求页面或其他地址
		if (queryString != null) {
			url2 += "?" + queryString;
		}
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		WXjsTicket cardTicket = AccessTokenUtil.getCardTicket(accessToken.getToken());
		Map<String, String> sign = WeixinJSSDKSignUtil.sign(cardTicket.getJsTicket(), url2);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("nonceStr", sign.get("nonceStr"));
		retMap.put("timestamp", sign.get("timestamp"));
		retMap.put("cardSign", sign.get("signature"));
		return getSuccessObj(ResultUtil.returnByObj(retMap));
	}
	
}
