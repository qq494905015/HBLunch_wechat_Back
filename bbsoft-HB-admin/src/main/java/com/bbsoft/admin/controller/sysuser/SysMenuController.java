package com.bbsoft.admin.controller.sysuser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.admin.controller.BaseController;
import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.Menu;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.BeanToMapUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.core.sysuser.domain.SysMenu;
import com.bbsoft.core.sysuser.domain.SysUser;
import com.bbsoft.core.sysuser.service.SysMenuService;
import com.bbsoft.core.sysuser.service.SysRoleService;
import com.bbsoft.core.sysuser.service.SysUserService;

import net.sf.json.JSONObject;

@Controller
public class SysMenuController extends BaseController{

	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 获取指定角色的权限菜单信息
	 * @param request 
	 * @param roleId 角色ID
	 * @return
	 */
	@RequestMapping(value = "menu200000")
	@ResponseBody
	public Json getMenuByRole(HttpServletRequest request, String roleId){
		if(roleId == null){
			throw new ServiceException(MsgeData.SYSTEM_10103.getCode(), 
					MsgeData.SYSTEM_10103.getMsg() + "【roleId : " + roleId + "】");
		}
		sysRoleService.checkIsExist(roleId);
		List<SysMenu> menus = sysMenuService.getMenuByRole(roleId);
		List<JSONObject> list = packageMenu(menus);
		return getSuccessObj(list);
	}
	
	/**
	 * 查询当前登录的用户权限
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "menu200001")
	@ResponseBody
	public Json getCurrUserMenu(HttpServletRequest request){
		String userId = getSessionId(request);
		SysUser sysUser = sysUserService.getUserById(userId,false);
		sysRoleService.checkIsExist(sysUser.getRoleId());
		List<SysMenu> menusList = sysMenuService.getMenuByRole(sysUser.getRoleId());
		return getSuccessObj(packageMenu(menusList));
	}
	
	public List<Menu> buildMenu(List<SysMenu> menusList, String parentId) {
		List<Menu> menuList = new ArrayList<Menu>();
		for (SysMenu module : menusList) {
			Menu menu = new Menu();
			menu.setId(module.getId());
			menu.setText(module.getName());
			menu.setIconCls(module.getIconClass());
//			menu.setState("close");
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("href", module.getUrl());
			menu.setAttributes(attributes);
			if (StringUtils.equals(parentId, module.getParentId())) {
				menu.setChildren(buildMenu(menusList, module.getId()));
				menuList.add(menu);
			}
		}
		return menuList;
	}

	
	/**
	 * 查询所有权限目录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/menu200002")
	@ResponseBody
	public Json getMenus(HttpServletRequest request){
		List<SysMenu> menus = sysMenuService.getMenus();
		List<JSONObject> list = packageMenu(menus);
		return getSuccessObj(list);
	}
	
	/**
	 * 获取指定角色的权限菜单信息
	 * @param request 
	 * @param roleId 角色ID
	 * @return
	 */
	@RequestMapping(value = "menu200003")
	@ResponseBody
	public Json getMenuByRole1(HttpServletRequest request, String roleId){
		if(roleId == null){
			throw new ServiceException(MsgeData.SYSTEM_10103.getCode(), 
					MsgeData.SYSTEM_10103.getMsg() + "【roleId : " + roleId + "】");
		}
		sysRoleService.checkIsExist(roleId);
		List<SysMenu> menus = sysMenuService.getMenuByRole(roleId);
		return getSuccessObj(ResultUtil.returnByList(menus));
	}
	
	/**
	 * 打包菜单
	 * @param menus
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> packageMenu(List<SysMenu> menus){
		List<JSONObject> list = new ArrayList<JSONObject>();
		//包装菜单信息，以子父级结构返回
		if(menus != null && menus.size() > 0){
			for(SysMenu menu : menus){
				if(menu.getParentId().equals("0")){
					Map<String, Object> menuMap = BeanToMapUtil.convertBean(menu, false);
					List<JSONObject> childMenus = new ArrayList<JSONObject>();
					for(SysMenu childMenu : menus){
						if(childMenu.getParentId().equals(menu.getId())){
							Map<String, Object> childMenuMap = BeanToMapUtil.convertBean(childMenu, false);
							List<JSONObject> secondMenus = new ArrayList<JSONObject>();
							for(SysMenu secondMenu : menus){
								if(secondMenu.getParentId().equals(childMenu.getId())){
									secondMenus.add(JSONObject.fromObject(BeanToMapUtil.convertBean(secondMenu, false)));
								}
							}
							childMenuMap.put("childMenu", secondMenus);
							childMenus.add(JSONObject.fromObject(childMenuMap));
						}
					}
					menuMap.put("childMenu", childMenus);
					list.add(JSONObject.fromObject(menuMap));
				}
			}
		}
		
		return list;
	}
}
