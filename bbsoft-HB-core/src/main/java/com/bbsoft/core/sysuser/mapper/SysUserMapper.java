package com.bbsoft.core.sysuser.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.sysuser.domain.SysUser;


/**
 * 系统管理员数据库接口访问类
 * @author Chris.Zhang
 * @date 2017年2月28日
 */
public interface SysUserMapper {

	/**
	 * 根据用户账号和密码查询用户信息
	 * @param userName 用户账号
	 * @param password 用户密码
	 * @return
	 */
	public SysUser selectByLogin(@Param("userName") String userName, @Param("password") String password);
	
	/**
	 * 根据用户账号查询用户记录数
	 * @param userName 用户账号
	 * @return
	 */
	public int selectCountByName(@Param("userName") String userName);
	
	/**
	 * 查询指定ID的用户信息
	 * @param id 用户的主键ID
	 * @return
	 */
	public SysUser selectById(@Param("id") String id);
	
	/**
	 * 查询指定token的用户信息
	 * @param token 用户的token
	 * @return
	 */
	public SysUser selectByToken(@Param("token") String token);
	
	/**
	 * 根据条件查询用户记录数
	 * @param search 搜索关键字
	 * @return
	 */
	public int selectUserCount(@Param("search") String search);
	
	/**
	 * 分页多条件查询用户信息
	 * @param startItem 开始行数
	 * @param pageSize 页大小
	 * @param search 搜索关键字
	 * @return
	 */
	public List<SysUser> selectUserByPage(@Param("startItem") Integer startItem, @Param("pageSize") Integer pageSize, @Param("search") String search);
	
	/**
	 * 新增管理员用户
	 * @param sysUser 需要新增的管理员
	 * @return
	 */
	public int insertSysUser(SysUser sysUser);
	
	/**
	 * 更新管理员用户
	 * @param sysUser 需要更新的管理员
	 * @return
	 */
	public int updateSysUser(SysUser sysUser);
}
