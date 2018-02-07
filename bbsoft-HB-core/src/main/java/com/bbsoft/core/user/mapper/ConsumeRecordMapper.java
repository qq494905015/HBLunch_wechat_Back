package com.bbsoft.core.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.user.domain.ConsumeRecord;

/**
 * 用户消费记录数据库访问接口
 * @author Chris.Zhang
 * @date 2017-5-16 18:02:09
 *
 */
public interface ConsumeRecordMapper {

	/**
	 * 查询指定ID的消费记录
	 * @param id 消费记录ID
	 * @return
	 */
	public ConsumeRecord selectById(@Param("id") String id);
	
	/**
	 * 多条件查询消费记录数和消费总金额
	 * @param map 查询条件
	 * @return
	 */
	public Map<String, Object> selectCountConsumeRecord(Map<String, Object> map);
	
	/**
	 * 多条件查询消费记录数和消费总金额，同返回Map集合一起使用
	 * @param map 查询条件
	 * @return
	 */
	public Map<String, Object> selectCountConsumeMap(Map<String, Object> map);
	
	
	/**
	 * 多条件分页查询消费记录数
	 * @param map 查询条件
	 * @return
	 */
	public List<ConsumeRecord> selectConsumeRecordByPage(Map<String, Object> map);
	
	/**
	 * 多条件分页查询消费记录数，Map结果
	 * @param map 查询条件
	 * @return
	 */
	public List<Map<String, Object>> selectConsumeRecordMap(Map<String, Object> map);
	
	/**
	 * 查询所有根据天分组的消费记录数
	 * @param map 查询条件
	 * @return
	 */
	public Map<String, Object> selectPlateCount(Map<String, Object> map);
	
	/**
	 * 分页查询根据天分组的消费记录
	 * @param map 查询条件
	 * @return
	 */
	public List<Map<String, Object>> selectPlateTotalMap(Map<String, Object> map);
	
	/**
	 * 新增消费记录
	 * @param consumeRecord 消费记录
	 * @return
	 */
	public int insertConsumeRecord(ConsumeRecord consumeRecord);
	
	/**
	 * 修改消费记录
	 * @param consumeRecord 消费记录
	 * @return
	 */
	public int updateConsumeRecord(ConsumeRecord consumeRecord);
	
	/**
	 * 删除消费记录
	 * @param id 消费记录ID
	 * @return
	 */
	public int deleteConsumeRecord(@Param("id") Long id);
	
	/**
	 * 根据来源获取消费记录
	 * @param fromId 消费来源ID
	 * @param fromType 消费来源（1：会员卡，2：充值，3：消费）
	 * @return
	 */
	public ConsumeRecord selectRecordByFrom(@Param("fromId") String fromId, @Param("fromType") String fromType);
}
