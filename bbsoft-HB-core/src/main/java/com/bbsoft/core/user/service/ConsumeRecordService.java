package com.bbsoft.core.user.service;

import java.util.List;
import java.util.Map;

import com.bbsoft.core.user.domain.ConsumeRecord;

/**
 * 用户消费记录业务接口
 * @author Chris.Zhang
 * @date 2017-5-16 18:22:59
 *
 */
public interface ConsumeRecordService {
	
	/**
	 * 新增用户消费记录
	 * @param consumeRecord 消费记录
	 * @return
	 */
	public int addConsumeRecord(ConsumeRecord consumeRecord);
	
	/**
	 * 修改用户消费记录
	 * @param consumeRecord 消费记录
	 * @return
	 */
	public int updateConsumeRecord(ConsumeRecord consumeRecord);
	
	/**
	 * 删除用户消费记录
	 * @param id 消费记录ID
	 * @return
	 */
	public int deleteConsumeRecord(Long id);
	
	/**
	 * 多条件查询用户消费记录数和总消费金额, 同返回MAP结果集一起使用
	 * @param map 查询条件
	 * @return
	 */
	public Map<String, Object> getCountConsumeMap(Map<String, Object> map);
	
	/**
	 * 多条件查询用户消费记录数和总消费金额
	 * @param map 查询条件
	 * @return
	 */
	public Map<String, Object> getConsumeRecord(Map<String, Object> map);
	
	/**
	 * 多条件分页查询用户消费记录
	 * @param map 查询条件
	 * @return
	 */
	public List<ConsumeRecord> getConsumeRecordByPage(Map<String, Object> map);
	
	/**
	 * 多条件分页查询用户消费记录, Map结果集
	 * @param map 查询条件
	 * @return
	 */
	public List<Map<String, Object>> getConsumeRecordMap(Map<String, Object> map);
	
	/**
	 * 查询平台每一天的收入统计记录数
	 * @param map 查询条件
	 * @return
	 */
	public Map<String, Object> getPlateCount(Map<String, Object> map);
	
	/**
	 * 分页查询平台每一天的收入统计记录
	 * @param map 查询条件
	 * @return
	 */
	public List<Map<String, Object>> getPlateTotalMap(Map<String, Object> map);

	/**
	 * 根据来源获取消费记录
	 * @param fromId 消费来源ID
	 * @param fromType 消费来源（1：会员卡，2：充值，3：消费）
	 * @return
	 */
	public ConsumeRecord getRecordByFrom(String fromId, String fromType);
	
	
}
