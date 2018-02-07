package com.bbsoft.admin.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.admin.controller.BaseController;
import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.BeanToMapUtil;
import com.bbsoft.common.util.PageUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.user.domain.Feedback;
import com.bbsoft.core.user.service.FeedbackService;

/**
 * 用户反馈接口
 * @author Chris.Zhang
 * @date 2017-5-23 18:33:11
 *
 */
@Controller
public class FeedbackController extends BaseController{

	@Autowired
	private FeedbackService feedbackService;
	
	/**
	 * 更新用户反馈
	 * @param id 反馈ID
	 * @param result 反馈结果
	 * @return
	 */
	@RequestMapping("feedback200000")
	@ResponseBody
	public Json updateFeedback(Long id, String result){
		if(id == null || StringUtil.isEmpty(result)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Feedback feedback = new Feedback();
		feedback.setId(id);
		feedback.setResult(result);
		feedback.setRetTime(new Date());
		feedbackService.updateFeedback(feedback);
		return getSuccessObj();
	}
	
	/**
	 * 分页获取用户反馈信息列表
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @param search 搜索关键字
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("feedback200001")
	@ResponseBody
	public Json getFeedbackByPage(Integer pageNum, Integer pageSize, String search){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", StringUtil.isEmpty(search) ? null : search);
		int total = feedbackService.getFeedbackCount(map);
		PageUtil<Map> page = new PageUtil<Map>(pageNum, pageSize, total);
		map.put("startItem", page.getStartItem());
		map.put("pageSize", page.getPageSize());
		List<Feedback> feedbacks = feedbackService.getFeedbackByPage(map);
		page.setItems(BeanToMapUtil.convertList(feedbacks));
		return getSuccessObj(ResultUtil.returnByObj(page));
	}
	
	/**
	 * 获取指定的反馈信息
	 * @param id 反馈ID
	 * @return
	 */
	@RequestMapping("feedback200002")
	@ResponseBody
	public Json getFeedbackById(Long id){
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Feedback feedback = feedbackService.getFeedbackById(id, true);
		return getSuccessObj(ResultUtil.returnByObj(feedback));
	}
}
