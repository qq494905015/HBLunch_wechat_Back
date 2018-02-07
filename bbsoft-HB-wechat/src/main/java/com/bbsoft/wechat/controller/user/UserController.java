package com.bbsoft.wechat.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.PhoneMessage;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.AlidayuMsgUtil;
import com.bbsoft.common.util.BeanToMapUtil;
import com.bbsoft.common.util.EncryUtil;
import com.bbsoft.common.util.PageUtil;
import com.bbsoft.common.util.RequestUtil;
import com.bbsoft.common.util.ResourceUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.SnowflakeIdUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.business.domain.Card;
import com.bbsoft.core.business.service.CardService;
import com.bbsoft.core.user.domain.BaseUser;
import com.bbsoft.core.user.domain.UserWallet;
import com.bbsoft.core.user.service.BaseUserService;
import com.bbsoft.core.user.service.UserWalletService;
import com.bbsoft.wechat.controller.BaseController;

/**
 * 用户接口
 * @author Chris.Zhang
 * @date 2017-5-24 10:45:07
 *
 */
@Controller
public class UserController extends BaseController{
	
	@Autowired
	private BaseUserService baseUserService;
	@Autowired
	private UserWalletService userWalletService;
	@Autowired
	private CardService cardService;
	/**
	 * 获取短信验证码
	 * @param phone 手机号
	 * @param time 限制时间
	 * @param type 类型 1 登录, 2 注册, 3 修改密码, 4 验证ID
	 * @return
	 */
	@RequestMapping(value = "/user100000")
	@ResponseBody
	public Json getPhoneCode(String phone, Integer time, String type){
		BaseUser user = baseUserService.getByPhone(phone); 
		if(("1".equals(type) || "3".equals(type) || "4".equals(type)) && user == null){
			throw new ServiceException(MsgeData.USER_1000020003.getCode());
		}
		if("2".equals(type) && user != null){
			throw new ServiceException(MsgeData.USER_1000020002.getCode());
		}
		//调用发送短信
		PhoneMessage phoneMessage = null; 
		if("1".equals(type)){
			phoneMessage = AlidayuMsgUtil.sendLoginMsg(phone);
		}
		if("2".equals(type)){
			phoneMessage = AlidayuMsgUtil.sendRegisterMsg(phone);
		}
		if("3".equals(type)){
			phoneMessage = AlidayuMsgUtil.sendModfiyMsg(phone);
		}
		if("4".equals(type)){
			phoneMessage = AlidayuMsgUtil.sendIdCheckMsg(phone);
		}
		
		return getSuccessObj(ResultUtil.returnByObj(phoneMessage));
	}
	
	/**
	 * 注册用户
	 * @param phone 手机号
	 * @param pwd 密码
	 * @param code 验证码
	 * @return
	 */
	@RequestMapping(value = "/user100001")
	@ResponseBody
	public Json register(HttpServletRequest request, String phone, String pwd, String code){
		//参数校验
		if(StringUtil.isEmpty(pwd)){
			throw new ServiceException(MsgeData.PUBLIC_1000010011.getCode());
		}
		if(pwd.length() < 6){
			throw new ServiceException(MsgeData.PUBLIC_1000010012.getCode());
		}
		//校验验证码
		AlidayuMsgUtil.checkCode(phone, code, "2");
		BaseUser user = new BaseUser();
		String id = SnowflakeIdUtil.getGeneratedKey();
		user.setId(id);
		user.setPhone(phone);
		user.setPassWord(pwd);
		user.setNickName(phone);
		user.setUserName(phone);
		user.setRealName(phone);
		user.setRegisterFrom("微信");
		user.setPreLoginTime(user.getLastloginTime() == null ? new Date() : user.getLastloginTime());
		user.setLastloginTime(new Date());
		user.setLoginNum(1);
		user.setPreLoginIp("");
		user.setWechatNo(getOpenId(request));
		user.setCurrLoginIp(RequestUtil.getIpAddr(request));
		baseUserService.addUser(user);
		//获取用户存入到session中
		BaseUser userInfo = baseUserService.getUserById(user.getId());
		//存入Session
		pushSession(request, userInfo);
		
		userInfo.setHeadImg(ResourceUtil.covertFullImg(userInfo.getHeadImg()));
		return getSuccessObj(ResultUtil.returnByObj(userInfo));
	}
	
	/**
	 * 更改密码（找回密码）
	 * @param phone手机号
	 * @param pwd 登录密码
	 * @param 驗證碼
	 * @return
	 */
	@RequestMapping(value = "/user100002")
	@ResponseBody
	public Json updateFindPwd(String phone, String pwd, String code){
		//参数校验
		if(StringUtil.isEmpty(pwd)){
			throw new ServiceException(MsgeData.PUBLIC_1000010011.getCode());
		}
		if(pwd.length() < 6){
			throw new ServiceException(MsgeData.PUBLIC_1000010012.getCode());
		}
		if(AlidayuMsgUtil.checkCode(phone, code, "3")){
			baseUserService.updatePwd(phone, EncryUtil.md5s(pwd));
		}
		return getSuccessObj();
	}
	
	/**
	 * 登录
	 * @param phone 手机号
	 * @param userName 用户账号
	 * @param pwd 密码
	 * @return
	 */
	@RequestMapping(value = "/user100003")
	@ResponseBody
	public Json login(HttpServletRequest request, HttpServletResponse response, String phone, String userName, String pwd){
		//参数校验
		if((StringUtil.isEmpty(phone) && StringUtil.isEmpty(userName)) || StringUtil.isEmpty(pwd)){
			throw new ServiceException(MsgeData.PUBLIC_1000010010.getCode());
		}
		
		if(!StringUtil.isEmpty(phone) && !StringUtil.checkPhone(phone)){
			throw new ServiceException(MsgeData.PUBLIC_1000010001.getCode());
		}
		
		if(pwd.length() < 6){
			throw new ServiceException(MsgeData.PUBLIC_1000010012.getCode());
		}
		BaseUser user = baseUserService.getByLogin(phone, userName);
		if(user != null){
			if(!EncryUtil.md5s(pwd).equals(user.getPassWord())){
				throw new ServiceException(MsgeData.PUBLIC_1000010009.getCode());
			}
			user.setPreLoginTime(user.getLastloginTime() == null ? new Date() : user.getLastloginTime());
			user.setLastloginTime(new Date());
			user.setLoginNum(user.getLoginNum() + 1);
			user.setPreLoginIp(user.getCurrLoginIp() == null ? "" : user.getCurrLoginIp());
			user.setCurrLoginIp(RequestUtil.getIpAddr(request));
			user.setPhone(null);
			user.setPassWord(null);
			baseUserService.updateUser(user);	//更新登录用户基本信息
			
			//获取用户存入到session中
			BaseUser userInfo = baseUserService.getUserById(user.getId());
			//存入Session
			pushSession(request, userInfo);
			if(!StringUtil.isEmpty(user.getWechatNo()) && StringUtil.isEmpty(getOpenId(request))){
				  //获取微信用户openid存储在cookie中的信息
	            Cookie userCookie = new Cookie("openId", user.getWechatNo());
	            userCookie.setMaxAge(-1);
	            userCookie.setPath("/");
	            response.addCookie(userCookie);
			}
			
			userInfo.setHeadImg(ResourceUtil.covertFullImg(userInfo.getHeadImg()));
			return getSuccessObj(ResultUtil.returnByObj(userInfo));
		}else{
			throw new ServiceException(MsgeData.USER_1000020003.getCode());
		}
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "user100004")
	@ResponseBody
	public Json logout(HttpServletRequest request){
		clearSession(request);
		return getSuccessObj();
	}
	
	/**
	 * 通过用户id，获取用户详情信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "user100005")
	@ResponseBody
	public Json getUserInfoById(String id){
		BaseUser userInfo = baseUserService.getUserById(id, true);
		Map<String, Object> resultMap = BeanToMapUtil.convertBean(userInfo);
		UserWallet userWallet = userWalletService.getWalletByUser(userInfo.getId());
		resultMap.put("userWallet", userWallet != null ? BeanToMapUtil.convertBean(userWallet) : new HashMap<String, Object>());
		Card card = cardService.getCardByUser(id);
		resultMap.put("cardInfo", BeanToMapUtil.convertBean(card));
		return getSuccessObj(ResultUtil.returnByObj(resultMap));
	}
	
	/**
	 * 校验验证码
	 * @param phone 手机号
	 * @param code 验证码
	 * @param type 类型 1 登录, 2 注册, 3 修改密码, 4 验证ID
	 * @return
	 */
	@RequestMapping(value = "user100006")
	@ResponseBody
	public Json checkCode(String phone, String code, String type){
		AlidayuMsgUtil.checkCode(phone, code, type);
		return getSuccessObj();
	}
	
	/**
	 * 获取当前登录用户的信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("user100007")
	@ResponseBody
	public Json getCurUser(HttpServletRequest request){
		String userId = getUserId(request);
		BaseUser userInfo = baseUserService.getUserById(userId);
		Map<String, Object> resultMap = BeanToMapUtil.convertBean(userInfo);
		UserWallet userWallet = userWalletService.getWalletByUser(userInfo.getId());
		resultMap.put("userWallet", userWallet != null ? BeanToMapUtil.convertBean(userWallet) : new HashMap<String, Object>());
		Card card = cardService.getCardByUser(userId);
		resultMap.put("cardInfo", BeanToMapUtil.convertBean(card));
		return getSuccessObj(ResultUtil.returnByObj(resultMap));
	}
	
	/**
	 * 获取商户平台下所有用户列表
	 * @param request
	 * @param sign 签名字符串，MD5加密
	 * @param search 搜索关键字
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @return
	 */
	@RequestMapping("user100008")
	@ResponseBody
	public Json getUserList(HttpServletRequest request, 
			String search, Integer pageNum, Integer pageSize){
		
		//校验当前用户是否店铺管理员
		BaseUser baseUser = baseUserService.getUserById(getUserId(request), true);
		if(StringUtil.isEmpty(baseUser.getShopId()) || "0".equals(baseUser.getShopId())){
			throw new ServiceException(MsgeData.USER_1000020013.getCode());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", StringUtil.isEmpty(search) ? null : search);
		map.put("shopId", baseUser.getShopId());
		int total = baseUserService.getUserMapCount(map);
		PageUtil<Map<String, Object>> page = new PageUtil<Map<String, Object>>(pageNum, pageSize, total);
		map.put("startItem", page.getStartItem());
		map.put("pageSize", page.getPageSize());
		page.setItems(baseUserService.getUserMaps(map));
		return getSuccessObj(ResultUtil.returnByObj(page));
	}
	
	/**
	 * 修改当前登录的用户信息
	 * @param request
	 * @param realName 真实姓名
	 * @param birthday 生日
	 * @return
	 */
	@RequestMapping("user100009")
	@ResponseBody
	public Json updateUser(HttpServletRequest request,
			String realName, String birthday){
		String userId = getUserId(request);
		BaseUser baseUser = new BaseUser();
		baseUser.setId(userId);
		baseUser.setRealName(realName);
		baseUser.setBirthday(birthday);
		baseUserService.updateUser(baseUser);
		return getSuccessObj();
	}
	
	/**
	 * 生成二维码
	 * @param request
	 * @return
	 */
	@RequestMapping("produceCode")
	@ResponseBody
	public Json produceCode(HttpServletRequest request){
		String userId = getUserId(request);
		Card card = cardService.getCardByUser(userId);
		if(card == null || !"0".equals(card.getStatus())){
			throw new ServiceException(MsgeData.CARD_1000030010.getCode());
		}
		return getSuccessObj();
	}
}
