package com.bbsoft.wechat.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.BeanToMapUtil;
import com.bbsoft.common.util.PageUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.user.domain.Feedback;
import com.bbsoft.core.user.service.FeedbackService;
import com.bbsoft.wechat.controller.BaseController;

/**
 * 用户反馈接口
 * @author Chris.Zhang
 * @date 2017-5-24 10:44:45
 *
 */
@Controller
public class FeedbackController extends BaseController{

	@Autowired
	private FeedbackService feedbackService;
	
	/**
	 * 新增用户反馈
	 * @param request
	 * @param userName 用户姓名
	 * @param phone 用户联系方式
	 * @param description 反馈描述
	 * @return
	 */
	@RequestMapping("feedback100000")
	@ResponseBody
	public Json addFeedback(HttpServletRequest request, 
			String userName, String phone, String description){
		if(StringUtil.isEmpty(userName)
			|| StringUtil.isEmpty(phone)
			|| StringUtil.isEmpty(description)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Feedback feedback = new Feedback(getUserId(request), userName, phone, description, new Date(), "0");
		feedbackService.addFeedback(feedback);
		return getSuccessObj();
	}
	
	/**
	 * 获取当前登录用户的留言反馈
	 * @param request
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("feedback100001")
	@ResponseBody
	public Json getFeedbackByUser(HttpServletRequest request, Integer pageNum, Integer pageSize){
		String userId = getUserId(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		int total = feedbackService.getFeedbackCount(map);
		PageUtil<Map> page = new PageUtil<Map>(pageNum, pageSize, total);
		map.put("pageNum", page.getPageNum());
		map.put("pageSize", page.getPageSize());
		List<Feedback> feedbacks = feedbackService.getFeedbackByPage(map);
		page.setItems(BeanToMapUtil.convertList(feedbacks));
		return getSuccessObj(ResultUtil.returnByObj(page));
	}
	
}
