package com.bbsoft.core.sysuser.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.sysuser.domain.SysRole;


/**
 * 角色信息数据库访问接口
 * @author Chris.Zhang
 * @date 2017年3月6日
 */
public interface SysRoleMapper {
	
	/**
	 * 新增角色信息
	 * @param role 角色
	 * @return
	 */
	public int intsertRole(SysRole role);
	
	/**
	 * 更新角色信息
	 * @param role 角色
	 * @return
	 */
	public int updateRole(SysRole role);
	
	/**
	 * 删除角色
	 * @param id 角色ID
	 * @return
	 */
	public int deleteRole(@Param("id") String id);
	
	/**
	 * 新增角色给用户
	 * @param userId 用户ID
	 * @param roleId 角色ID
	 * @return
	 */
	public int insertRoleToUser(@Param("id") String id, @Param("userId") String userId, @Param("roleId") String roleId);
	
	/**
	 * 修改用户角色
	 * @param userId 用户ID
	 * @param roleId 角色ID
	 * @return
	 */
	public int updateRoleAndUser(@Param("userId") String userId, @Param("roleId") String roleId);
	
	
	
	/**
	 * 分页查询角色信息
	 * @param startItem 开始行数
	 * @param pageSize 分页大小
	 * @param search 搜索关键字
	 * @return
	 */
	public List<SysRole> selectRoleByPage(@Param("startItem") Integer startItem, @Param("pageSize") Integer pageSize, @Param("search") String search);
	
	/**
	 * 查询角色记录数
	 * @param search 搜索关键字
	 * @return
	 */
	public int selectRoleCount(@Param("search") String search);
	
	/**
	 * 根据角色名称查询角色记录数
	 * @param roleName 角色名称
	 * @return
	 */
	public int selectCountByName(@Param("roleName") String roleName);
	
	/**
	 * 根据ID查询角色信息
	 * @param id 角色ID
	 * @return
	 */
	public SysRole selectById(@Param("id") String id);

}
