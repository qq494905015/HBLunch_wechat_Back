package com.bbsoft.admin.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.admin.controller.BaseController;
import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.system.domain.WeMenu;
import com.bbsoft.core.system.service.WeMenuService;

import net.sf.json.JSONArray;

/**
 * 微信菜单配置
 * @author Chris.Zhang
 * @date 2017-7-24 17:28:46
 *
 */
@Controller
public class WeMenuController extends BaseController{

	@Autowired
	private WeMenuService weMenuService;
	
	/**
	 * 新增微菜单信息
	 * @param name 微菜单名称
	 * @param url 微菜单路径
	 * @param parentId 菜单父级ID
	 * @param isAuth 是否需要授权，0：否，1：是
	 * @return
	 */
	@RequestMapping("weMenu200000")
	@ResponseBody
	public Json addMenu(String name, String url, Long parentId, String isAuth){
		if(StringUtil.isEmpty(name) || StringUtil.isEmpty(isAuth)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if(parentId == null){
			parentId = 0L;
		}
		if(parentId != 0 && StringUtil.isEmpty(url)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		WeMenu menu = new WeMenu(null, name, url, parentId, isAuth);
		weMenuService.addWeMenu(menu);
		return getSuccessObj();
	}
	
	/**
	 * 修改微信菜单信息
	 * @param id 微菜单ID
	 * @param name 微菜单名称
	 * @param url 微菜单路径
	 * @param parentId 菜单父级ID
	 * @param isAuth 是否需要授权，0：否，1：是
	 * @return
	 */
	@RequestMapping("weMenu200001")
	@ResponseBody
	public Json updateMenu(Long id, String name, String url, Long parentId, String isAuth){
		if(id == null || id == 0){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		WeMenu menu = new WeMenu(id, name, url, parentId, isAuth);
		weMenuService.updateWeMenu(menu);
		return getSuccessObj();
	}
	
	/**
	 * 获取所有菜单信息
	 * @return
	 */
	@RequestMapping("weMenu200002")
	@ResponseBody
	public Json getMenus(){
		List<WeMenu> menus = weMenuService.getAll();
		return getSuccessObj(ResultUtil.returnByList(menus));
	}
	
	/**
	 * 更新菜单到微信
	 * @return
	 */
	@RequestMapping("weMenu200003")
	@ResponseBody
	public Json updateToWechat(){
		weMenuService.updateToWechat();
		return getSuccessObj();
	}
	
	/**
	 * 批量修改微信菜单
	 * @param reqMenu 请求菜单数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("weMenu200004")
	@ResponseBody
	public Json updateMenus(String reqMenu){
		if(StringUtil.isEmpty(reqMenu)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		JSONArray array = JSONArray.fromObject(reqMenu);
		JSONArray.toArray(array, WeMenu.class);
		List<WeMenu> menus = (List<WeMenu>) JSONArray.toCollection(array, WeMenu.class);
		System.out.println(menus);
		weMenuService.updateMenus(menus);
		return getSuccessObj();
	}
}
