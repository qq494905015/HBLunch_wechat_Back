package com.bbsoft.admin.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.admin.controller.BaseController;
import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.BeanToMapUtil;
import com.bbsoft.common.util.DateUtil;
import com.bbsoft.common.util.PageUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.business.domain.Card;
import com.bbsoft.core.business.domain.Shop;
import com.bbsoft.core.business.service.CardService;
import com.bbsoft.core.business.service.ShopService;
import com.bbsoft.core.user.domain.BaseUser;
import com.bbsoft.core.user.domain.UserWallet;
import com.bbsoft.core.user.service.BaseUserService;
import com.bbsoft.core.user.service.UserWalletService;

/**
 * 会员接口
 * @author Chris.Zhang
 * @date 2017-5-15 13:19:23
 *
 */
@Controller
public class UserController extends BaseController{
	
	@Autowired
	private BaseUserService baseUserService;
	@Autowired
	private CardService cardService;
	@Autowired
	private UserWalletService userWalletService;
	@Autowired
	private ShopService shopService;
	/**
	 * 分页获取会员列表信息
	 * @param search 搜索关键字
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @param startTime 开始注册时间
	 * @param endTime 结束注册时间
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("user200000")
	@ResponseBody
	public Json getUserByPage(String search, Integer pageNum, Integer pageSize, String startTime, String endTime){
		if(!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)){
			if(DateUtil.formatStringToDate(startTime, DateUtil.GLOBAL_TIME_PATTERN).getTime() > DateUtil.formatStringToDate(endTime, DateUtil.GLOBAL_TIME_PATTERN).getTime()){
				throw new ServiceException(MsgeData.PUBLIC_1000010017.getCode());
			}
		}
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("search", StringUtil.isEmpty(search) ? null : search);
		paramsMap.put("startTime", StringUtil.isEmpty(startTime) ? null : startTime);
		paramsMap.put("endTime", StringUtil.isEmpty(endTime) ? null : endTime);
		int total = baseUserService.getUserCount(paramsMap);
		PageUtil<Map> page = new PageUtil<Map>(pageNum, pageSize, total);
		paramsMap.put("startItem", page.getStartItem());
		paramsMap.put("pageSize", page.getPageSize());
		List<BaseUser> users = baseUserService.getUserByPage(paramsMap);
		List<Map> mapList = BeanToMapUtil.convertList(users);
		if(mapList != null && mapList.size() > 0){
			for(Map map : mapList){
				String userId = map.get("id").toString();
				Card card = cardService.getCardByUser(userId);
				if(card == null){
					map.put("balance", "0.00");
					map.put("cardNo", "未绑定");
				}else{
					map.put("balance", card.getBalance());
					map.put("cardNo", card.getCardNo());
				}
				UserWallet userWallet = userWalletService.getWalletByUser(userId);
				if(userWallet != null && userWallet.getCommision() != null){
					map.put("commision", userWallet.getCommision());
				}else{
					map.put("commision", "0.00");
				}
				String shopId = map.get("shopId").toString();
				if(StringUtil.isEmpty(shopId)){
					shopId = "0";
				}
				Shop shop = shopService.getShopById(shopId);
				if(shop != null){
					map.put("shopName", shop.getName());
				}else{
					map.put("shopName", "");
				}
			}
		}
		page.setItems(mapList);
		return getSuccessObj(ResultUtil.returnByObj(page));
	}
	
	/**
	 * 查询指定会员信息
	 * @param id 会员ID
	 * @return
	 */
	@RequestMapping("user200001")
	@ResponseBody
	public Json getUserById(String id){
		return getSuccessObj(ResultUtil.returnByObj(baseUserService.getUserById(id)));
	}
	
	/**
	 * 新增会员信息
	 * @param userName 会员账号
	 * @param nickName 会员昵称
	 * @param realName 会员真实姓名
	 * @param phone 会员手机号
	 * @param password 会员登录密码
	 * @param sex 会员性别
	 * @param birthday 出生日期
	 * @param idCard 身份证号码
	 * @return
	 */
	@RequestMapping("user200002")
	@ResponseBody
	public Json addUser(
			String userName, String nickName, String realName,
			String phone, String password, String sex, 
			String birthday, String idCard){
		if(StringUtil.isEmpty(phone) || StringUtil.isEmpty(password)){
			throw new ServiceException(MsgeData.PUBLIC_1000010010.getCode());
		}
		if(password.length() < 6){
			throw new ServiceException(MsgeData.PUBLIC_1000010012.getCode());
		}
		BaseUser baseUser = new BaseUser();
		baseUser.setPhone(phone);
		baseUser.setPassWord(password);
		baseUser.setSex(sex);
		baseUser.setBirthday(birthday);
		baseUser.setUserName(userName);
		baseUser.setNickName(nickName);
		baseUser.setRealName(realName);
		baseUser.setIdCard(idCard);
		if(StringUtil.isEmpty(userName)){
			baseUser.setUserName(phone);
		}
		if(StringUtil.isEmpty(nickName)){
			baseUser.setNickName(phone);
		}
		baseUserService.addUser(baseUser);
		return getSuccessObj();
	}
	
	/**
	 * 修改会员信息
	 * @param id 会员ID
	 * @param nickName 会员昵称
	 * @param realName 会员真实姓名
	 * @param phone 会员手机号
	 * @param password 会员登录密码
	 * @param sex 会员性别
	 * @param birthday 会员出生日期
	 * @param idCard 会员身份证号码
	 * @param shopId 店铺ID
	 * @return
	 */
	@RequestMapping("user200003")
	@ResponseBody
	public Json updateUser(
			String id, String nickName, String realName, 
			String phone, String password, String sex, 
			String birthday, String idCard, String shopId){
		
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if(!StringUtil.isEmpty(password) && password.length() < 6){
			throw new ServiceException(MsgeData.PUBLIC_1000010012.getCode());
		}
		BaseUser baseUser = new BaseUser();
		baseUser.setId(id);
		baseUser.setNickName(nickName);
		baseUser.setRealName(realName);
		baseUser.setPhone(phone);
		baseUser.setPassWord(password);
		baseUser.setSex(sex);
		baseUser.setBirthday(birthday);
		baseUser.setIdCard(idCard);
		baseUser.setShopId(shopId);
		baseUserService.updateUser(baseUser);
		return getSuccessObj();
	}
	
	/**
	 * 修改指定用户的余额信息
	 * @param id 会员用户ID
	 * @param balance
	 * @return
	 */
	@RequestMapping("user200004")
	@ResponseBody
	public Json updateUserBalance(String id, Long balance){
		if(id == null
				|| balance == null || balance < 0){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		baseUserService.updateUserBalance(id, balance);
		return getSuccessObj();
	}
	
	/**
	 * 修改用户佣金信息
	 * @param userId 用户ID
	 * @param commision 用户佣金
	 * @return
	 */
	@RequestMapping("user200005")
	@ResponseBody
	public Json updateUserCommision(String id, Long commision){
		if(id == null
				|| commision == null || commision < 0){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		baseUserService.updateUserCommision(id, commision);
		return getSuccessObj();
	}
}
