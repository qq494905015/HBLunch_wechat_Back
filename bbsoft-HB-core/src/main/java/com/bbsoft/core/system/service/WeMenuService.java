package com.bbsoft.core.system.service;

import java.util.List;

import com.bbsoft.core.system.domain.WeMenu;

import net.sf.json.JSONObject;

/**
 * 微菜单业务接口
 * @author Chris.Zhang
 * @date 2017-7-25 10:37:56
 *
 */
public interface WeMenuService {

	/**
	 * 新增微信菜单
	 * @param weMenu 微信菜单
	 * @return
	 */
	public int addWeMenu(WeMenu weMenu);
	
	/**
	 * 修改微信菜单
	 * @param weMenu 微信菜单
	 * @return
	 */
	public int updateWeMenu(WeMenu weMenu);
	
	/**
	 * 获取所有菜单
	 * @return
	 */
	public List<WeMenu> getAll();
	
	/**
	 * 删除所有菜单
	 * @return
	 */
	public int deleteAll();
	
	/**
	 * 删除指定菜单
	 * @param id 菜单ID
	 * @return
	 */
	public int deleteWeMenu(Long id);
	
	/**
	 * 更新菜单到微信
	 * @return
	 */
	public JSONObject updateToWechat();
	
	/**
	 * 批量修改菜单信息
	 * @param menus
	 * @return
	 */
	public int updateMenus(List<WeMenu> menus);
}
