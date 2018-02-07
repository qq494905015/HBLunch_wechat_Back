package com.bbsoft.core.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.bbsoft.core.system.domain.WeMenu;

/**
 * 微信菜单数据访问接口
 * @author Chris.Zhang
 * @date 2017-7-24 18:32:46
 *
 */
public interface WeMenuMapper {

	/**
	 * 新增微信菜单信息
	 * @param weMenu 微菜单
	 * @return
	 */
	public int insertWeMenu(WeMenu weMenu);
	
	/**
	 * 修改微信菜单信息
	 * @param weMenu 微菜单
	 * @return
	 */
	public int updateWeMenu(WeMenu weMenu);
	
	/**
	 * 删除微信菜单信息
	 * @param id 菜单ID
	 * @return
	 */
	public int deleteWeMenu(@Param("id") Long id);
	
	/**
	 * 删除微信所有菜单信息
	 * @return
	 */
	public int deleteAll();
	
	/**
	 * 查询所有菜单信息
	 * @return
	 */
	public List<WeMenu> selectWeMenus();
	
	/**
	 * 获取指定父级的菜单信息
	 * @param parentId
	 * @return
	 */
	public List<WeMenu> selectWeMenuByParent(@Param("parentId") Long parentId);
	
	/**
	 * 获取指定父级的菜单记录数
	 * @param parentId
	 * @return
	 */
	public int selectCountByParent(@Param("parentId") Long parentId);
}
