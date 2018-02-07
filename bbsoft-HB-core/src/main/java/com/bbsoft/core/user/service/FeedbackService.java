package com.bbsoft.core.user.service;

import java.util.List;
import java.util.Map;

import com.bbsoft.core.user.domain.Feedback;

/**
 * 用户反馈业务接口
 * @author Chris.Zhang
 * @date 2017-5-16 18:23:42
 *
 */
public interface FeedbackService {

	/**
	 * 新增用户反馈
	 * @param feedback 用户反馈
	 * @return
	 */
	public int addFeedback(Feedback feedback);
	
	/**
	 * 修改用户反馈
	 * @param feedback 用户反馈
	 * @return
	 */
	public int updateFeedback(Feedback feedback);
	
	/**
	 * 删除用户反馈
	 * @param id 反馈ID
	 * @return
	 */
	public int deleteFeedback(Long id);
	
	/**
	 * 多条件获取用户反馈记录数
	 * @param map 查询条件
	 * @return
	 */
	public int getFeedbackCount(Map<String, Object> map);
	
	/**
	 * 多条件分页获取用户反馈记录
	 * @param map 查询条件
	 * @return
	 */
	public List<Feedback> getFeedbackByPage(Map<String, Object> map);
	
	/**
	 * 获取指定用户反馈记录
	 * @param id 反馈记录ID
	 * @return
	 */
	public Feedback getFeedbackById(Long id);
	
	/**
	 * 获取指定用户反馈记录
	 * @param id 反馈记录ID
	 * @param isException 是否抛出业务异常 true|false
	 * @return
	 */
	public Feedback getFeedbackById(Long id, boolean isException);
}
