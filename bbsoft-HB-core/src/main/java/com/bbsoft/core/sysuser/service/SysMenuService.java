package com.bbsoft.core.sysuser.service;

import java.util.List;

import com.bbsoft.core.sysuser.domain.SysMenu;


/**
 * 权限菜单业务
 * @author Chris.Zhang
 * @date 2017年3月6日
 */
public interface SysMenuService {
	
	
	/**
	 * 获取指定的角色ed菜单
	 * @param roleId 角色ID
	 * @return
	 */
	public List<SysMenu> getMenuByRole(String roleId);
	
	/**
	 * 查询所有菜单信息
	 * @return
	 */
	public List<SysMenu> getMenus();


}
