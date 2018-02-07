package com.bbsoft.core.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.system.domain.HelpInfo;

/**
 * 官网帮助中心信息数据库访问接口
 * @author Chris.Zhang
 * @date 2017-6-6 16:33:33
 *
 */
public interface HelpInfoMapper {

	/**
	 * 新增官网帮助中心信息
	 * @param helpInfo 官网帮助中心信息
	 * @return
	 */
	public int insertHelp(HelpInfo helpInfo);
	
	/**
	 * 修改官网帮助中心信息
	 * @param helpInfo 官网帮助中心信息
	 * @return
	 */
	public int updateHelp(HelpInfo helpInfo);
	
	/**
	 * 获取指定官网帮助中心信息
	 * @param id 主键ID
	 * @return
	 */
	public HelpInfo selectHelpById(@Param("id") Long id);
	
	/**
	 * 查询指定状态的一条官网帮助中心信息
	 * @param status 状态，0：使用中，1：禁用
	 * @return
	 */
	public HelpInfo selectShowHelp(@Param("status") String status);
	
	/**
	 * 查询当前配置的官网帮助中心信息记录数
	 * @return
	 */
	public int selectHelpCount();
}
