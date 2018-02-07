package com.bbsoft.core.user.service;

import com.bbsoft.core.user.domain.WechatUser;

/**
 * 微信用户
 * @author Chris.Zhang
 * @date 2017-6-17 18:13:02
 *
 */
public interface WechatUserService {

	/**
	 * 新增微信用户
	 * @param wechatUser 微信用户
	 * @return
	 */
	public int addWechatUser(WechatUser wechatUser);
	
	/**
	 * 修改微信用户
	 * @param wechatUser 微信用户
	 * @return
	 */
	public int updateWechatUser(WechatUser wechatUser);
	
	/**
	 * 根据微信用户唯一标识获取微信用户信息
	 * @param openId 微信用户唯一标识 
	 * @return
	 */
	public WechatUser getByOpenId(String openId);
	
	/**
	 * 根据平台用户ID获取微信用户信息
	 * @param userId 平台用户ID
	 * @return
	 */
	public WechatUser getByUserId(String userId);
	
	/**
	 * 根据主键ID获取微信用户信息
	 * @param id 主键ID
	 * @return
	 */
	public WechatUser getById(Long id);
}
