package com.bbsoft.core.sysuser.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.SnowflakeIdUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.sysuser.domain.SysRole;
import com.bbsoft.core.sysuser.mapper.SysMenuMapper;
import com.bbsoft.core.sysuser.mapper.SysRoleMapper;
import com.bbsoft.core.sysuser.mapper.SysUserMapper;
import com.bbsoft.core.sysuser.service.SysRoleService;


@Service
public class SysRoleServiceImpl implements SysRoleService{

	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	
	public int addRole(SysRole role, List<String> menus) {
		if(role == null || StringUtil.isEmpty(role.getRoleName())){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		int count = sysRoleMapper.selectCountByName(role.getRoleName());
		if(count > 0){
			throw new ServiceException(MsgeData.USER_1000020005.getCode());
		}
		int result = sysRoleMapper.intsertRole(role);
		if(role.getId() != null && menus != null && menus.size() > 0){
			List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
			for (int i = 0; i < menus.size(); i++) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", SnowflakeIdUtil.getGeneratedKey());
				map.put("roleId", role.getId());
				map.put("menuId", menus.get(i));
				map.put("createTime", new Date());
				listMap.add(map);
			}
			result = sysMenuMapper.insertMenuToRole(listMap);
		}
		return result;
	}

	public int updateRole(SysRole role, List<String> menus) {
		if(role == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		SysRole dbRole = checkIsExist(role.getId());
		if(!StringUtil.isEmpty(role.getRoleName()) 
				&& !role.getRoleName().equals(dbRole.getRoleName())
				&& sysRoleMapper.selectCountByName(role.getRoleName()) > 0){
			throw new ServiceException(MsgeData.USER_1000020005.getCode());
		}
		int result = sysRoleMapper.updateRole(role);
		result = sysMenuMapper.deleteMenuByRole(role.getId());
		if(menus != null && menus.size() > 0){
			List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
			for (int i = 0; i < menus.size(); i++) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", SnowflakeIdUtil.getGeneratedKey());
				map.put("roleId", role.getId());
				map.put("menuId", menus.get(i));
				map.put("createTime", new Date());
				listMap.add(map);
			}
			result = sysMenuMapper.insertMenuToRole(listMap);
		}
		return result;
	}

	public int deleteRole(String id) {
		checkIsExist(id);
		return sysRoleMapper.deleteRole(id);
	}

	public List<SysRole> getRoleByPage(Integer startItem, Integer pageSize, String search) {
		if(startItem == null){
			startItem = 0;
		}
		if(pageSize == null){
			pageSize = 10;
		}
		return sysRoleMapper.selectRoleByPage(startItem, pageSize, search);
	}

	public int getRoleCount(String search) {
		return sysRoleMapper.selectRoleCount(search);
	}

	public int addRoleToUser(String userId, String roleId) {
		checkUserAndRole(userId, roleId);
		return sysRoleMapper.insertRoleToUser(SnowflakeIdUtil.getGeneratedKey(), userId, roleId);
	}
	
	public int updateRoleAndUser(String userId, String roleId) {
		checkUserAndRole(userId, roleId);
		if(sysUserMapper.selectById(userId) == null){
			throw new ServiceException(MsgeData.USER_1000020003.getCode());
		}
		return sysRoleMapper.updateRoleAndUser(userId, roleId);
	}
	
	/**
	 * 校验是否存在
	 * @param id 角色ID
	 */
	public SysRole checkIsExist(String roleId){
		if(roleId == null){
			throw new ServiceException(MsgeData.SYSTEM_10103.getCode());
		}
		SysRole dbRole = sysRoleMapper.selectById(roleId);
		if(dbRole == null){
			throw new ServiceException(MsgeData.USER_1000020006.getCode());
		}
		return dbRole;
	}

	public SysRole getRoleById(String id) {
		return checkIsExist(id);
	}
	
	/**
	 * 校验角色和用户
	 * @param userId 用户ID
	 * @param roleId 角色ID
	 */
	private void checkUserAndRole(String userId, String roleId){
		if(userId == null || roleId == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode(), MsgeData.SYSTEM_10104.getMsg()
					+ "【userId : " + userId
					+ "roleId : " + roleId + "】");
		}
		checkIsExist(roleId);
	}
}
