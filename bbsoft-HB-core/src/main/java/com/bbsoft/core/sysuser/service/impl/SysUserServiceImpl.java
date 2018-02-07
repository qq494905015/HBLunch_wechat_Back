package com.bbsoft.core.sysuser.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.EncryUtil;
import com.bbsoft.common.util.SnowflakeIdUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.sysuser.domain.SysUser;
import com.bbsoft.core.sysuser.mapper.SysUserMapper;
import com.bbsoft.core.sysuser.service.SysRoleService;
import com.bbsoft.core.sysuser.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysRoleService roleService;

	public SysUser getUserByLogin(String userName, String password) {
		SysUser sysUser = sysUserMapper.selectByLogin(userName, password);
		checkUser(sysUser, false);
		return sysUser;
	}

	public SysUser getUserById(String id,Boolean flag) {
		SysUser sysUser = sysUserMapper.selectById(id);
		checkUser(sysUser, flag);
		return sysUser;
	}

	public int getUserCount(String id, String search) {
		return sysUserMapper.selectUserCount(search);
	}

	public List<SysUser> getUserByPage(Integer startItem, Integer pageSize, String id, String search) {
		return sysUserMapper.selectUserByPage(startItem, pageSize, search);
	}

	public int addUser(SysUser sysUser) {
		checkUser(sysUser, true);
		if(StringUtil.isEmpty(sysUser.getUserName()) || StringUtil.isEmpty(sysUser.getPassword())){
			throw new ServiceException(MsgeData.PUBLIC_1000010010.getCode());
		}
		//校验是否存在此用户
		checkExistUser(sysUser);
		if(!StringUtil.isEmpty(sysUser.getPassword())){
			sysUser.setPassword(EncryUtil.md5s(sysUser.getPassword()));
		}else{
			//默认密码888888
			sysUser.setPassword(EncryUtil.md5s("888888"));
		}
		sysUser.setId(SnowflakeIdUtil.getGeneratedKey());
		sysUser.setIsDelete("0");
		sysUser.setCreateTime(new Date());
		int result = sysUserMapper.insertSysUser(sysUser);
		if(!StringUtil.isEmpty(sysUser.getRoleId())){
			result = roleService.addRoleToUser(sysUser.getId(), sysUser.getRoleId());
		}
		return result;
		
	}
	
	public int updateUser(SysUser sysUser) {
		checkUser(sysUser, true);
		SysUser dbUser = sysUserMapper.selectById(sysUser.getId());
		checkUser(dbUser, true);
		if(!StringUtil.isEmpty(sysUser.getPassword())){
			sysUser.setPassword(EncryUtil.md5s(sysUser.getPassword()));
		}
		if(!StringUtil.isEmpty(sysUser.getUserName()) && !sysUser.getUserName().equals(dbUser.getUserName())){
			//校验是否存在此用户
			checkExistUser(sysUser);
		}
		return sysUserMapper.updateSysUser(sysUser);
	}

	public int deleteUser(String id) {
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10103.getCode());
		}
		checkUser(sysUserMapper.selectById(id), true);
		SysUser sysUser = new SysUser();
		sysUser.setId(id);
		sysUser.setIsDelete("1");
		return sysUserMapper.updateSysUser(sysUser);
	}
	
	/**
	 * 校验用户
	 * @param sysUser 需要校验的管理员信息
	 * @param onlyNull 是否值校验空
	 */
	private void checkUser(SysUser sysUser, boolean onlyNull){
		if(sysUser == null){
			throw new ServiceException(MsgeData.USER_1000020003.getCode());
		}
		if(!onlyNull){
			//用户是否禁用
			if("1".equals(sysUser.getStatus())){
				throw new ServiceException(MsgeData.USER_1000020007.getCode());
			}
		}
	}

	/**
	 * 校验是否存在此用户
	 * @param sysUser 系统用户信息
	 */
	private void checkExistUser(SysUser sysUser){
		int existCount = sysUserMapper.selectCountByName(sysUser.getUserName());
		if(existCount > 0){
			throw new ServiceException(MsgeData.USER_1000020001.getCode());
		}
	}

}
