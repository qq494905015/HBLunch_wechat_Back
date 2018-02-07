package com.bbsoft.core.sysuser.service;

import java.util.List;

import com.bbsoft.core.sysuser.domain.SysRole;


/**
 * 系统角色业务
 * @author Chris.Zhang
 * @date 2017年3月6日
 */
public interface SysRoleService {

	/**
	 * 新增角色
	 * @param role 角色信息
	 * @param menus 菜单权限
	 * @return
	 */
	public int addRole(SysRole role, List<String> menus);
	
	/**
	 * 修改角色
	 * @param role 角色信息
	 * @param menus 菜单权限
	 * @return
	 */
	public int updateRole(SysRole role, List<String> menus);
	
	/**
	 * 删除角色
	 * @param roleId  角色ID
	 * @return
	 */
	public int deleteRole(String id);
	
	/**
	 * 分页查询角色信息
	 * @param startItem 查询开始行数
	 * @param pageSize 查询的一页数
	 * @param search 搜索关键字，角色名称
	 * @return
	 */
	public List<SysRole> getRoleByPage(Integer startItem, Integer pageSize, String search);
	
	/**
	 * 查询角色信息记录数
	 * @param search 搜索关键字，角色名称
	 * @return
	 */
	public int getRoleCount(String search);
	
	/**
	 * 给用户添加角色
	 * @param userId 用户ID
	 * @param roleId 角色ID
	 * @return
	 */
	public int addRoleToUser(String userId, String roleId);
	
	/**
	 * 修改用户的角色信息
	 * @param userId 用户ID
	 * @param roleId 角色ID
	 * @return
	 */
	public int updateRoleAndUser(String userId, String roleId);
	
	/**
	 * 获取指定角色信息
	 * @param id 角色ID
	 * @return
	 */
	public SysRole getRoleById(String id);
	
	/**
	 * 校验角色
	 * @param id 角色ID
	 * @return
	 */
	public SysRole checkIsExist(String roleId);
	
}
