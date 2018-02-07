package com.bbsoft.core.user.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.EmojiFilterUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.user.domain.WechatUser;
import com.bbsoft.core.user.mapper.WechatUserMapper;
import com.bbsoft.core.user.service.WechatUserService;

@Service
public class WechatUserServiceImpl implements WechatUserService {

	@Autowired
	private WechatUserMapper wechatUserMapper;
	
	public int addWechatUser(WechatUser wechatUser) {
		if(wechatUser == null || StringUtil.isEmpty(wechatUser.getOpenId())){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if(!StringUtil.isEmpty(wechatUser.getNickname())){
			wechatUser.setNickname(EmojiFilterUtil.filterEmoji(wechatUser.getNickname()));
			if(wechatUser.getNickname() == null){
				wechatUser.setNickname("");
			}
		}
		WechatUser dbWechatUser = wechatUserMapper.selectByOpenId(wechatUser.getOpenId());
		if(dbWechatUser != null){
			wechatUser.setId(dbWechatUser.getId());
			wechatUser.setUpdateTime(new Date());
			wechatUser.setCreateTime(null);
			return wechatUserMapper.updateWechatUser(wechatUser);
		}
		if(wechatUser.getCreateTime() == null){
			wechatUser.setCreateTime(new Date());
		}
		return wechatUserMapper.insertWechatUser(wechatUser);
	}

	public int updateWechatUser(WechatUser wechatUser) {
		if(wechatUser == null || wechatUser.getId() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return wechatUserMapper.updateWechatUser(wechatUser);
	}

	public WechatUser getByOpenId(String openId) {
		if(StringUtil.isEmpty(openId)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return wechatUserMapper.selectByOpenId(openId);
	}

	public WechatUser getByUserId(String userId) {
		if(StringUtil.isEmpty(userId)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return wechatUserMapper.selectByUserId(userId);
	}

	public WechatUser getById(Long id) {
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return wechatUserMapper.selectById(id);
	}

}
