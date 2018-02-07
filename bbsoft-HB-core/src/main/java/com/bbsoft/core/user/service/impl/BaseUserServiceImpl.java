package com.bbsoft.core.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.EncryUtil;
import com.bbsoft.common.util.SnowflakeIdUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.business.domain.Card;
import com.bbsoft.core.business.service.CardService;
import com.bbsoft.core.user.domain.BaseUser;
import com.bbsoft.core.user.domain.UserWallet;
import com.bbsoft.core.user.domain.WechatUser;
import com.bbsoft.core.user.mapper.BaseUserMapper;
import com.bbsoft.core.user.service.BaseUserService;
import com.bbsoft.core.user.service.UserWalletService;
import com.bbsoft.core.user.service.WechatUserService;

@Service
public class BaseUserServiceImpl implements BaseUserService {
	
	@Autowired
	private BaseUserMapper baseUserMapper;
	@Autowired
	private UserWalletService userWalletService;
	@Autowired
	private WechatUserService wechatUserService;
	@Autowired
	private CardService cardService;

	public int addUser(BaseUser user) {
		if(user != null){
			user.setStatus("0");
			user.setCreateTime(new Date());
			if(!StringUtil.isEmpty(user.getPhone()) && baseUserMapper.selectCountByPhone(user.getPhone()) > 0){
				throw new ServiceException(MsgeData.USER_1000020002.getCode());
			}
			if(!StringUtil.isEmpty(user.getUserName()) && baseUserMapper.selectCountByName(user.getUserName()) > 0){
				throw new ServiceException(MsgeData.USER_1000020001.getCode());
			}
			if(StringUtil.isEmpty(user.getId())){
				user.setId(SnowflakeIdUtil.getGeneratedKey());
			}
			user.setPassWord(EncryUtil.md5s(user.getPassWord()));
		}else{
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		
		//更新微信用户信息
		if(!StringUtil.isEmpty(user.getWechatNo())){
			WechatUser wechatUser = new WechatUser();
			WechatUser dbWeUser = wechatUserService.getByOpenId(user.getWechatNo());
			if(dbWeUser != null){
				wechatUser.setId(dbWeUser.getId());
				wechatUser.setUserId(user.getId());
				wechatUserService.updateWechatUser(wechatUser);
			}
			if(!StringUtil.isEmpty(dbWeUser.getNickname())){
				user.setNickName(dbWeUser.getNickname());
			}
		}
		
		//新增平台用户
		int result = baseUserMapper.insertUser(user);
		
		//新增用户钱包
		UserWallet userWallet = new UserWallet(user.getId(), 0L, 0L, 0L);
		userWalletService.addWallet(userWallet);
		return result;
	}

	public BaseUser getByPhone(String phone) {
		return baseUserMapper.selectByPhone(phone);
	}

	public BaseUser getByLogin(String phone, String userName) {
		if(StringUtil.isEmpty(phone) && !StringUtil.isEmpty(userName)){
			return baseUserMapper.selectByUserName(userName);
		}else if(!StringUtil.isEmpty(phone) && StringUtil.isEmpty(userName)){
			return baseUserMapper.selectByPhone(phone);
		}else{
			return null;
		}
	}

	public int updatePwd(String phone, String pwd) {
		BaseUser user = baseUserMapper.selectByPhone(phone);
		if(user != null){
			return baseUserMapper.updateUserPwd(user.getId(), pwd);
		}
		throw new ServiceException(MsgeData.USER_1000020003.getCode());
	}

	public int updateUser(BaseUser user) {
		if(user == null){
			throw new ServiceException(MsgeData.SYSTEM_10105.getCode(), MsgeData.SYSTEM_10105.getMsg() + ",user:" + user);
		}
		if(user.getId() == null){
			throw new ServiceException(MsgeData.SYSTEM_10103.getCode());
		}
		BaseUser dbUser = baseUserMapper.selectById(user.getId());
		if(dbUser == null){
			throw new ServiceException(MsgeData.USER_1000020003.getCode());
		}
		//!StringUtil.isEmpty(user.getUserName()) || 
//		if(!StringUtil.isEmpty(user.getPhone())){
//			throw new ServiceException(MsgeData.USER_1000020004.getCode());
//		}
		if(!StringUtil.isEmpty(user.getPassWord())){
			user.setPassWord(EncryUtil.md5s(user.getPassWord()));
		}
		return baseUserMapper.updateUser(user);
	}

	public int getUserCount(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return baseUserMapper.selectUserCount(map);
	}

	public List<BaseUser> getUserByPage(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return baseUserMapper.selectUserByPage(map);
	}

	public BaseUser getUserById(String id) {
		return this.getUserById(id, false);
	}

	public BaseUser getUserByToken(String token) {
		BaseUser user = baseUserMapper.selectByToken(token);
		if(user == null){
			throw new ServiceException(MsgeData.USER_1000020003.getCode());
		}
		return user;
	}

	public BaseUser getUserById(String id, boolean isException) {
		BaseUser user = baseUserMapper.selectById(id);
		if(user == null && isException){
			throw new ServiceException(MsgeData.USER_1000020003.getCode());
		}
		return user;
	}

	public List<Map<String, Object>> getUserMaps(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		map.put("startItem", map.get("startItem") == null ? 0 : map.get("startItem"));
		map.put("pageSize", map.get("pageSize") == null ? 10 : map.get("pageSize"));
		return baseUserMapper.selectUserMaps(map);
	}

	public Integer getUserMapCount(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return baseUserMapper.selectUserMapCount(map);
	}

	public int updateUserBalance(String userId, Long balance) {
		if(balance == null || balance < 0){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		getUserById(userId, true);
		Card card = cardService.getCardByUser(userId);
		UserWallet userWallet = userWalletService.getWalletByUser(userId);
		if(card == null){
			throw new ServiceException(MsgeData.CARD_1000030002.getCode());
		}
		Card editCard = new Card();
		editCard.setId(card.getId());
		editCard.setBalance(balance);
		int result = cardService.updateCard(editCard);
		if(userWallet != null){
			userWallet.setBalance(balance);
			result = userWalletService.updateWallet(userWallet);
		}
		return result;
	}

	public List<BaseUser> getUserByShop(String shopId) {
		return baseUserMapper.selectUserByShop(shopId);
	}

	public int updateUserCommision(String userId, Long commision) {
		if(commision == null || commision < 0){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		getUserById(userId, true);
		UserWallet userWallet = userWalletService.getWalletByUser(userId);
		int result = 0;
		if(userWallet != null){
			userWallet.setCommision(commision);
			result = userWalletService.updateWallet(userWallet);
		}
		return result;
	}
}
