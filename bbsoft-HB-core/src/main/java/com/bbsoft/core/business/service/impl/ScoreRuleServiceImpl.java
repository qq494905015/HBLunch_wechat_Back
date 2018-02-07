package com.bbsoft.core.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.business.domain.ScoreRule;
import com.bbsoft.core.business.mapper.ScoreRuleMapper;
import com.bbsoft.core.business.service.ScoreRuleService;

@Service
public class ScoreRuleServiceImpl implements ScoreRuleService {
	
	@Autowired
	private ScoreRuleMapper scoreRuleMapper;

	public int addScoreRule(ScoreRule scoreRule) {
		if(scoreRule == null 
				|| (scoreRule.getConsumeAmount() == null && scoreRule.getScore() == null)
				|| StringUtil.isEmpty(scoreRule.getToken())){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return scoreRuleMapper.insertScoreRule(scoreRule);
	}

	public int updateScoreRule(ScoreRule scoreRule) {
		if(scoreRule == null
			|| scoreRule.getId() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return scoreRuleMapper.updateScoreRule(scoreRule);
	}

	public ScoreRule getRuleById(Long id) {
		return this.getRuleById(id, false);
	}
	
	public ScoreRule getRuleById(Long id, boolean isException){
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		ScoreRule rule = scoreRuleMapper.selectById(id);
		if(rule == null && isException){
			throw new ServiceException(MsgeData.SYSTEM_10105.getCode());
		}
		return rule;
	}

	public ScoreRule getRuleByToken(String token) {
		if(StringUtil.isEmpty(token)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return scoreRuleMapper.selectRuleByToken(token);
	}

	public List<ScoreRule> getScoreRules() {
		return scoreRuleMapper.selectRules();
	}

}
