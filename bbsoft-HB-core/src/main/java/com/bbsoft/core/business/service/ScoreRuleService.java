package com.bbsoft.core.business.service;

import java.util.List;

import com.bbsoft.core.business.domain.ScoreRule;

/**
 * 积分规则业务接口
 * @author Chris.Zhang
 * @date 2017-6-7 16:10:04
 *
 */
public interface ScoreRuleService {

	/**
	 * 新增积分规则
	 * @param scoreRule 积分规则
	 * @return
	 */
	public int addScoreRule(ScoreRule scoreRule);
	
	/**
	 * 修改积分规则
	 * @param scoreRule 积分规则
	 * @return
	 */
	public int updateScoreRule(ScoreRule scoreRule);
	
	/**
	 * 获取指定积分规则
	 * @param id 积分规则ID
	 * @return
	 */
	public ScoreRule getRuleById(Long id);
	
	/**
	 * 获取指定积分规则
	 * @param id 积分规则ID
	 * @param isException 是否抛出异常
	 * @return
	 */
	public ScoreRule getRuleById(Long id, boolean isException);
	
	/**
	 * 根据TOKEN标识获取积分规则
	 * @param token TOKEN标识
	 * @return
	 */
	public ScoreRule getRuleByToken(String token);
	
	/**
	 * 获取积分规则列表
	 * @return
	 */
	public List<ScoreRule> getScoreRules();
}
