package com.bbsoft.core.user.mapper;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.user.domain.WechatUser;

/**
 * 微信用户数据库访问接口
 * @author Chris.Zhang
 * @date 2017-6-17 17:35:46
 *
 */
public interface WechatUserMapper {

	/**
	 * 新增微信用户信息
	 * @param wechatUser 微信用户
	 * @return
	 */
	public int insertWechatUser(WechatUser wechatUser);
	
	/**
	 * 修改微信用户信息
	 * @param wechatUser 微信用户
	 * @return
	 */
	public int updateWechatUser(WechatUser wechatUser);
	
	/**
	 * 根据微信唯一标识获取微信用户信息
	 * @param openId 微信唯一标识 
	 * @return
	 */
	public WechatUser selectByOpenId(@Param("openId") String openId);
	
	/**
	 * 根据平台用户ID获取微信用户信息
	 * @param userId 平台用户ID
	 * @return
	 */
	public WechatUser selectByUserId(@Param("userId") String userId);
	
	/**
	 * 根据主键ID获取微信用户信息
	 * @param id 主键ID
	 * @return
	 */
	public WechatUser selectById(@Param("id") Long id);
}
