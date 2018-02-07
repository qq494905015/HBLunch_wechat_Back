package com.bbsoft.core.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.user.domain.RechargeRecord;

/**
 * 会员充值记录数据库访问接口
 * @author Chris.Zhang
 * @date 2017-5-16 18:06:59
 *
 */
public interface RechargeRecordMapper {

	/**
	 * 查询指定ID的会员充值记录
	 * @param id 充值记录ID
	 * @return
	 */
	public RechargeRecord selectById(@Param("id") Long id);
	
	/**
	 * 多条件查询会员充值记录数
	 * @param map 查询条件
	 * @return
	 */
	public int selectCountRechargeRecord(Map<String, Object> map);
	
	/**
	 * 多条件分页查询会员充值记录
	 * @param map 查询条件
	 * @return
	 */
	public List<RechargeRecord> selectRechargeRecordByPage(Map<String, Object> map);
	
	/**
	 * 新增会员充值记录
	 * @param rechargeRecord 记录
	 * @return
	 */
	public int insertRechargeRecord(RechargeRecord rechargeRecord);
	
	/**
	 * 修改会员充值记录
	 * @param rechargeRecord 记录
	 * @return
	 */
	public int updateRechargeRecord(RechargeRecord rechargeRecord);
	
	/**
	 * 删除充值记录信息
	 * @param id 记录ID
	 * @return
	 */
	public int deleteRechargeRecord(@Param("id") Long id);
	
	/**
	 * 根据订单号查询充值记录
	 * @param orderId 订单号
	 * @return
	 */
	public RechargeRecord selectRecordByOrder(@Param("orderId") String orderId);
}
