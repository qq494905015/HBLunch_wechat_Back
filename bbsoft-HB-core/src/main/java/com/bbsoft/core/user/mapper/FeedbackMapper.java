package com.bbsoft.core.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.user.domain.Feedback;

/**
 * 用户反馈数据库访问接口
 * @author Chris.Zhang
 * @date 2017-5-16 18:06:25
 *
 */
public interface FeedbackMapper {

	/**
	 * 查询指定ID的用户反馈记录信息
	 * @param id 用户反馈记录ID
	 * @return
	 */
	public Feedback selectById(@Param("id") Long id);
	
	/**
	 * 多条件查询用户反馈记录数
	 * @param map 查询条件
	 * @return
	 */
	public int selectFeedbackCount(Map<String, Object> map);
	
	/**
	 * 多条件分页查询用户反馈记录
	 * @param map 查询条件
	 * @return
	 */
	public List<Feedback> selectFeedbackByPage(Map<String, Object> map);
	
	/**
	 * 新增用户反馈记录
	 * @param feedback 用户反馈
	 * @return
	 */
	public int insertFeedback(Feedback feedback);
	
	/**
	 * 修改用户反馈记录
	 * @param feedback 用户反馈
	 * @return
	 */
	public int updateFeedback(Feedback feedback);
	
	/**
	 * 上线指定用户反馈记录
	 * @param id 反馈记录ID
	 * @return
	 */
	public int deleteFeedback(@Param("id") Long id);
	
	/**
	 * 查询当他用户发表反馈的记录数
	 * @param userId 用户ID
	 * @return
	 */
	public int selectCurrCountByUser(@Param("userId") String userId);
}
