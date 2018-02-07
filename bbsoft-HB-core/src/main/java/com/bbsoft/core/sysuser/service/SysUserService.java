package com.bbsoft.core.sysuser.service;

import java.util.List;

import com.bbsoft.core.sysuser.domain.SysUser;


public interface SysUserService {
	
	/**
	 * 获取当前登录的用户信息
	 * @param userName 用户账号
	 * @param password 登录的密码 
	 * @return
	 */
	public SysUser getUserByLogin(String userName, String password);
	
	/**
	 * 查询指定用户的信息
	 * @param id 用户主键ID
	 * @return
	 */
	public SysUser getUserById(String id,Boolean flag);
	
	/**
	 * 查询用户记录数
	 * @param id 当前登录的用户ID
	 * @param search 搜索关键字
	 * @return
	 */
	public int getUserCount(String id, String search);
	
	/**
	 * 分页查询用户记录
	 * @param startItem 开始行数
	 * @param pageSize 页大小
	 * @param id 当前登录的用户ID
	 * @param search 搜索关键字
	 * @return
	 */
	public List<SysUser> getUserByPage(Integer startItem, Integer pageSize, String id, String search);
	
	/**
	 * 新增系统管理员
	 * @param sysUser 新增的管理员信息
	 * @return
	 */
	public int addUser(SysUser sysUser);
	
	/**
	 * 更新系统管理员
	 * @param sysUser 更新的管理员信息
	 * @return
	 */
	public int updateUser(SysUser sysUser);
	
	/**
	 * 删除管理员
	 * @param id 要删除的管理员ID
	 * @return
	 */
	public int deleteUser(String id);
	

}
