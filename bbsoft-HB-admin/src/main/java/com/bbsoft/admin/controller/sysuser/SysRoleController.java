package com.bbsoft.admin.controller.sysuser;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.admin.controller.BaseController;
import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.BeanToMapUtil;
import com.bbsoft.common.util.PageUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.SnowflakeIdUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.sysuser.domain.SysRole;
import com.bbsoft.core.sysuser.service.SysRoleService;

/**
 * 角色信息接口（后台管理）
 * @author Chris.Zhang
 * @date 2017年3月6日
 */
@Controller
public class SysRoleController extends BaseController {

	@Autowired
	private SysRoleService sysRoleService;
	
	/**
	 * 新增角色
	 * @param request
	 * @param roleName 角色名称
	 * @param description 描述
	 * @param menu 权限菜单ID，多个用英文逗号符号分隔
	 * @return
	 */
	@RequestMapping(value = "role200000")
	@ResponseBody
	public Json addRole(HttpServletRequest request, String roleName, String description, String menu){
		if(StringUtil.isEmpty(roleName)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode(), MsgeData.SYSTEM_10104.getMsg()
					+ "roleName : " + roleName);
		}
		SysRole role = new SysRole();
		role.setId(SnowflakeIdUtil.getGeneratedKey());
		role.setRoleName(roleName);
		role.setDescription(description);
		role.setSortNum(0);
		List<String> menus = null;
		if(!StringUtil.isEmpty(menu)){
			menus = StringUtil.strConvertListString(menu, ",");
		}
		sysRoleService.addRole(role, menus);
		return getSuccessObj();
	}
	
	/**
	 * 修改角色
	 * @param request
	 * @param id 角色ID
	 * @param roleName 角色名称
	 * @param description 描述
	 * @param menu 权限菜单ID，多个用英文逗号符号分隔
	 * @return
	 */
	@RequestMapping(value = "role200001")
	@ResponseBody
	public Json updateRole(HttpServletRequest request, String id, String roleName, String description, String menu){
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10103.getCode());
		}
		SysRole role = sysRoleService.getRoleById(id);
		if(role==null){
			throw new ServiceException(MsgeData.SYSTEM_10105.getCode());
		}
		role.setId(id);
		role.setRoleName(roleName);
		role.setDescription(description);
		role.setSortNum(0);
		List<String> menus = null;
		if(!StringUtil.isEmpty(menu)){
			menus = StringUtil.strConvertListString(menu, ",");
		}
		sysRoleService.updateRole(role, menus);
		return getSuccessObj();
	}
	
	/**
	 * 分页查询角色信息
	 * @param request
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @param search
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "role200002")
	@ResponseBody
	public Json getRoleByPage(HttpServletRequest request, Integer pageNum, Integer pageSize, String search){
		if(StringUtil.isEmpty(search)){
			search = null;
		}
		int total = sysRoleService.getRoleCount(search);
		PageUtil<Map> page = new PageUtil<Map>(pageNum, pageSize, total);
		List<SysRole> roles = sysRoleService.getRoleByPage(page.getStartItem(), pageSize, search);
		page.setItems(BeanToMapUtil.convertList(roles, false));
		return getSuccessObj(ResultUtil.returnByObj(page));
	}
	
	/**
	 * 获取指定角色
	 * @param request
	 * @param id 角色ID
	 * @return
	 */
	@RequestMapping(value = "role200003")
	@ResponseBody
	public Json getRoleById(HttpServletRequest request, String id){
		return getSuccessObj(ResultUtil.returnByObj(sysRoleService.getRoleById(id)));
	}
	
	/**
	 * 查询角色列表
	 * @param request
	 * @param search 搜索关键字
	 * @return
	 */
	@RequestMapping(value = "role200004")
	@ResponseBody
	public Json getRoles(HttpServletRequest request, String search){
		if(StringUtil.isEmpty(search)){
			search = null;
		}
		List<SysRole> roles = sysRoleService.getRoleByPage(0, 9999, search);
		return getSuccessObj(ResultUtil.returnByList(roles));
	}
	
	/**
	 * 删除角色信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/role200005")
	@ResponseBody
	public Json deleteRole(String id){
		sysRoleService.deleteRole(id);
		return getSuccessObj();
	}
}
