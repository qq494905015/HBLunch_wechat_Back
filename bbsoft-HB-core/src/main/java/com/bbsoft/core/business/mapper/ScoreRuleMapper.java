package com.bbsoft.core.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.business.domain.ScoreRule;

/**
 * 积分规则数据库访问接口
 * @author Chris.Zhang
 * @date 2017-6-7 17:28:48
 *
 */
public interface ScoreRuleMapper {

	/**
	 * 新增积分规则
	 * @param scoreRule 积分规则
	 * @return
	 */
	public int insertScoreRule(ScoreRule scoreRule);
	
	/**
	 * 修改积分规则
	 * @param scoreRule 积分规则
	 * @return
	 */
	public int updateScoreRule(ScoreRule scoreRule);
	
	/**
	 * 查询指定积分规则
	 * @param id 积分规则ID
	 * @return
	 */
	public ScoreRule selectById(@Param("id") Long id);
	
	/**
	 * 查询积分规则列表
	 * @return
	 */
	public List<ScoreRule> selectRules();
	
	/**
	 * 根据token查询积分规则
	 * @param token
	 * @return
	 */
	public ScoreRule selectRuleByToken(@Param("token") String token);
}
