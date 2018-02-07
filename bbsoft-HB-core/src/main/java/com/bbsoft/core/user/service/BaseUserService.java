package com.bbsoft.core.user.service;


import java.util.List;
import java.util.Map;

import com.bbsoft.core.user.domain.BaseUser;


public interface BaseUserService {

	/**
	 * 新增用户信息
	 * @param phone 手机号
	 * @param password 登录密码
	 * @return
	 */
	public int addUser(BaseUser user);
	
	/**
	 * 获取指定手机号的用户信息
	 * @param phone 手机号
	 * @return
	 */
	public BaseUser getByPhone(String phone);
	
	/**
	 * 根据登录获取指定用户信息
	 * @param phone 手机号
	 * @param userName 用户账号
	 * @param pwd 密码
	 * @return
	 */
	public BaseUser getByLogin(String phone, String userName);
	
	/**
	 * 修改用户密码
	 * @param phone 手机号
	 * @param pwd 密码
	 * @return
	 */
	public int updatePwd(String phone, String pwd);
	
	/**
	 * 修改用户信息
	 * @param user 用户信息
	 * @return
	 */
	public int updateUser(BaseUser user);
	
	/**
	 * 修改用户余额信息
	 * @param userId 用户ID
	 * @param balance 用户余额
	 * @return
	 */
	public int updateUserBalance(String userId, Long balance);
	
	/**
	 * 修改用户佣金信息
	 * @param userId 用户ID
	 * @param commision 用户佣金
	 * @return
	 */
	public int updateUserCommision(String userId, Long commision);
	
	/**
	 * 查询用户记录数
	 * @param map 搜索条件
	 * @return
	 */
	public int getUserCount(Map<String, Object> map);
	
	/**
	 * 分页查询用户信息
	 * @param map 关键字搜索条件
	 * @return
	 */
	public List<BaseUser> getUserByPage(Map<String, Object> map);
	
	/**
	 * 获取指定的用户信息
	 * @param id 用户ID
	 * @return
	 */
	public BaseUser getUserById(String id);
	
	/**
	 * 获取指定用户信息
	 * @param id 用户ID
	 * @param isException 是否抛出业务异常 true | false
	 * @return
	 */
	public BaseUser getUserById(String id, boolean isException);
	
	/**
	 * 根据Token获取用户信息
	 * @param token 
	 * @return
	 */
	public BaseUser getUserByToken(String token);
	
	/**
	 * 分页获取获取用户信息，自定义Map集合
	 * @param map 搜索条件
	 * @return
	 */
	public List<Map<String, Object>> getUserMaps(Map<String, Object> map);
	
	/**
	 * 分页获取获取用户数
	 * @param map 搜索条件
	 * @return
	 */
	public Integer getUserMapCount(Map<String, Object> map);
	
	/**
	 * 查询指定门店的用户信息
	 * @param shopId
	 * @return
	 */
	public List<BaseUser> getUserByShop(String shopId);
	
}
