package com.bbsoft.core.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.user.domain.Feedback;
import com.bbsoft.core.user.mapper.FeedbackMapper;
import com.bbsoft.core.user.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	
	@Autowired
	private FeedbackMapper feedbackMapper;

	public int addFeedback(Feedback feedback) {
		if(feedback == null
			|| StringUtil.isEmpty(feedback.getUserId())
			|| StringUtil.isEmpty(feedback.getUserName())
			|| StringUtil.isEmpty(feedback.getPhone())
			|| StringUtil.isEmpty(feedback.getDescription())){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		feedback.setCreateTime(new Date());
		feedback.setIsDelete("0");
		if(feedbackMapper.selectCurrCountByUser(feedback.getUserId()) > 10){
			throw new ServiceException(MsgeData.USER_1000020008.getCode());
		}
		return feedbackMapper.insertFeedback(feedback);
	}

	public int updateFeedback(Feedback feedback) {
		if(feedback == null
			|| feedback.getId() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return feedbackMapper.updateFeedback(feedback);
	}

	public int deleteFeedback(Long id) {
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		this.getFeedbackById(id, true);
		return feedbackMapper.deleteFeedback(id);
	}

	public int getFeedbackCount(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return feedbackMapper.selectFeedbackCount(map);
	}

	public List<Feedback> getFeedbackByPage(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		map.put("startItem", map.get("startItem") == null ? 0 : map.get("startItem"));
		map.put("pageSize", map.get("pageSize") == null ? 0 : map.get("pageSize"));
		return feedbackMapper.selectFeedbackByPage(map);
	}

	public Feedback getFeedbackById(Long id) {
		return this.getFeedbackById(id, false);
	}

	public Feedback getFeedbackById(Long id, boolean isException) {
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Feedback feedback = feedbackMapper.selectById(id);
		if(feedback == null && isException){
			throw new ServiceException(MsgeData.USER_1000020009.getCode());
		}
		return feedback;
	}

}
