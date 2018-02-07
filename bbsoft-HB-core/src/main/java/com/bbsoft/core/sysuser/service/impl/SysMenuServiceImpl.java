package com.bbsoft.core.sysuser.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.core.sysuser.domain.SysMenu;
import com.bbsoft.core.sysuser.mapper.SysMenuMapper;
import com.bbsoft.core.sysuser.service.SysMenuService;


@Service
public class SysMenuServiceImpl implements SysMenuService {
	
	@Autowired
	private SysMenuMapper sysMenuMapper;

	public List<SysMenu> getMenuByRole(String roleId) {
		if(roleId == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return sysMenuMapper.selectMenuByRole(roleId);
	}

	public List<SysMenu> getMenus() {
		return sysMenuMapper.selectMenus();
	}

}
