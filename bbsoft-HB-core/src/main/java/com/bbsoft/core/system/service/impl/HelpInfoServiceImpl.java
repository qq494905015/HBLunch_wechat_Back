package com.bbsoft.core.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.system.domain.HelpInfo;
import com.bbsoft.core.system.mapper.HelpInfoMapper;
import com.bbsoft.core.system.service.HelpInfoService;

@Service
public class HelpInfoServiceImpl implements HelpInfoService {
	
	@Autowired
	private HelpInfoMapper helpInfoMapper;

	public int addHelpInfo(HelpInfo helpInfo) {
		if(helpInfo == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if(helpInfoMapper.selectHelpCount() > 0){
			return 0;
		}else{
			return helpInfoMapper.insertHelp(helpInfo);
		}
	}

	public int updateHelpInfo(HelpInfo helpInfo) {
		if(helpInfo == null || helpInfo.getId() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return helpInfoMapper.updateHelp(helpInfo);
	}

	public HelpInfo getShowHelp(String status) {
		return helpInfoMapper.selectShowHelp(status);
	}
	
	public HelpInfo getById(Long id) {
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return helpInfoMapper.selectHelpById(id);
	}
}
