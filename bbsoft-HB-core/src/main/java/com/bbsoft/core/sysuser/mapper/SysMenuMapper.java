package com.bbsoft.core.sysuser.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.sysuser.domain.SysMenu;


/**
 * 系统菜单数据库访问接口
 * @author Chris.Zhang
 * @date 2017年3月6日
 */
public interface SysMenuMapper {

	/**
	 * 给角色分配菜单权限
	 * @param menus 菜单信息
	 * @param id 角色ID
	 * @return
	 */
	public int insertMenuToRole(@Param("listMap") List<Map<String,Object>> listMap);
	
	/**
	 * 删除指定角色的菜单权限
	 * @param roleId 角色ID
	 * @return
	 */
	public int deleteMenuByRole(@Param("roleId") String roleId);
	
	/**
	 * 查询指定角色的菜单信息
	 * @param roleId 角色ID
	 * @return
	 */
	public List<SysMenu> selectMenuByRole(@Param("roleId") String roleId);
	
	/**
	 * 查询所有的菜单信息
	 * @return
	 */
	public List<SysMenu> selectMenus();
}
