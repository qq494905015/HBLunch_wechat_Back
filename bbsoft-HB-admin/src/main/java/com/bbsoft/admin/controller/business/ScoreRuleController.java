package com.bbsoft.admin.controller.business;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.admin.controller.BaseController;
import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.business.domain.ScoreRule;
import com.bbsoft.core.business.service.ScoreRuleService;

/**
 * 积分规则接口
 * @author Chris.Zhang
 * @date 2017-6-7 16:07:06
 *
 */
@Controller
public class ScoreRuleController extends BaseController{

	@Autowired
	private ScoreRuleService scoreRuleService;
	
	/**
	 * 新增积分规则
	 * @param consumeAmount 消费金额
	 * @param token 积分规则标识
	 * @param score 获得积分
	 * @param description 规则描述
	 * @param status 状态，（0：启用，1：禁用）
	 * @return
	 */
	@RequestMapping("scoreruel200000")
	@ResponseBody
	public Json addScoreRule(
			Long consumeAmount, String token, Long score, 
			String description, String status){
		if((consumeAmount == null && score == null)
			|| StringUtil.isEmpty(token)
			|| StringUtil.isEmpty(description)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		ScoreRule scoreRule = new ScoreRule(consumeAmount, token, score, description, new Date(), status, "0");
		scoreRuleService.addScoreRule(scoreRule);
		return getSuccessObj();
	}
	
	/**
	 * 修改积分规则
	 * @param id 积分规则ID
	 * @param consumeAmount 消费金额
	 * @param token 积分规则标识
	 * @param score 获得积分
	 * @param description 规则描述
	 * @param status 状态，（0：启用，1：禁用）
	 * @return
	 */
	@RequestMapping("scoreruel200001")
	@ResponseBody
	public Json updateScoreRule(
			Long id, Long consumeAmount, String token, 
			Long score, String description, String status){
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		ScoreRule scoreRule = new ScoreRule(id, consumeAmount, token, score, description, null, status, "0");
		scoreRuleService.updateScoreRule(scoreRule);
		return getSuccessObj();
	}
	
	/**
	 * 获取指定积分规则
	 * @param id 积分规则ID
	 * @return
	 */
	@RequestMapping("scoreruel200002")
	@ResponseBody
	public Json getRuleById(Long id){
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		ScoreRule scoreRule = scoreRuleService.getRuleById(id, true);
		return getSuccessObj(ResultUtil.returnByObj(scoreRule));
	}
	
	/**
	 * 获取积分规则列表
	 * @return
	 */
	@RequestMapping("scoreruel200003")
	@ResponseBody
	public Json getRuleList(){
		List<ScoreRule> rules = scoreRuleService.getScoreRules();
		return getSuccessObj(ResultUtil.returnByList(rules));
	}
}
