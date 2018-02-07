package com.bbsoft.core.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.user.domain.BaseUser;

/**
 * 用户访问数据库接口
 * @author Chris
 * @date 2017-2-23 15:45:48
 */
public interface BaseUserMapper {

	/**
	 * 新增用户信息
	 * @param user 新增的用户
	 * @return
	 */
	public int insertUser(BaseUser user);
	
	/**
	 * 更改用户登录密码
	 * @param id 用户ID
	 * @param oldPwd 用户登录密码
	 * @param newPwd 用户登录密码
	 * @return
	 */
	public int updateUserPwd(@Param("id") String id, @Param("newPwd") String newPwd);
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public int updateUser(BaseUser user);
	
	/**
	 * 查询指定手机号的用户信息
	 * @param phone 手机号
	 * @return
	 */
	public BaseUser selectByPhone(@Param("phone") String phone);
	
	/**
	 * 根据登录的账户号和密码查询用户信息
	 * @param userName 账户号
	 * @param pwd 登录密码
	 * @return
	 */
	public BaseUser selectByUserName(@Param("userName") String userName);
	
	/**
	 * 根据手机号查询用户记录数
	 * @param phone 手机号
	 * @return
	 */
	public int selectCountByPhone(@Param("phone") String phone);
	
	/**
	 * 根据用户账号查询用户记录数
	 * @param userName 用户账号
	 * @return
	 */
	public int selectCountByName(@Param("userName") String userName);
	
	/**
	 * 查询指定用户信息
	 * @param id 用户ID
	 * @return
	 */
	public BaseUser selectById(@Param("id") String id);
	
	/**
	 * 查询用户记录数
	 * @param map 搜索条件
	 * @return
	 */
	public Integer selectUserCount(Map<String, Object> map);
	
	/**
	 * 分页查询用户记录信息
	 * @param map 搜索条件
	 * @return
	 */
	public List<BaseUser> selectUserByPage(Map<String, Object> map);
	
	/**
	 * 查询指定Token的用户信息
	 * @param token
	 * @return
	 */
	public BaseUser selectByToken(@Param("token") String token);
	
	/**
	 * 分页查询用户信息，查询自定义Map集合
	 * @param startItem 开始行数
	 * @param pageSize 页大小
	 * @param phone 搜索手机号
	 * @return
	 */
	public List<Map<String, Object>> selectUserMaps(Map<String, Object> map);
	
	/**
	 * 查询用户数
	 * @param map
	 * @return
	 */
	public Integer selectUserMapCount(Map<String, Object> map);
	
	/**
	 * 查询指定门店的用户信息
	 * @param shopId 门店iD
	 * @return
	 */
	public List<BaseUser> selectUserByShop(@Param("shopId") String shopId);
}
