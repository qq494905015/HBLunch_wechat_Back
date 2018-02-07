package com.bbsoft.core.system.service;

import com.bbsoft.core.system.domain.HelpInfo;

public interface HelpInfoService {

	/**
	 * 新增官网帮助中心信息
	 * @param helpInfo 官网帮助中心信息
	 * @return
	 */
	public int addHelpInfo(HelpInfo helpInfo);
	
	/**
	 * 修改官网帮助中心信息
	 * @param helpInfo 官网帮助中心信息
	 * @return
	 */
	public int updateHelpInfo(HelpInfo helpInfo);
	
	/**
	 * 获取指定状态的官网帮助中心信息
	 * @param status 状态，0：使用中，1：禁用
	 * @return
	 */
	public HelpInfo getShowHelp(String status);
	
	/**
	 * 获取指定官网帮助中心信息
	 * @param id 主键ID
	 * @return
	 */
	public HelpInfo getById(Long id);
}
