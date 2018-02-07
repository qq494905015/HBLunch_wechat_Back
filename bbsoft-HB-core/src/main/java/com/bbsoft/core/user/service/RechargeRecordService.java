package com.bbsoft.core.user.service;

import com.bbsoft.core.user.domain.RechargeRecord;

/**
 * 充值记录业务接口
 * @author Chris.Zhang
 * @date 2017-5-16 18:24:27
 *
 */
public interface RechargeRecordService {
	
	/**
	 * 新增充值记录
	 * @param rechargeRecord 充值记录
	 * @return
	 */
	public int addRechargeRecord(RechargeRecord rechargeRecord);
	
	/**
	 * 修改充值记录
	 * @param rechargeRecord 充值记录
	 * @return
	 */
	public int updateRechargeRecord(RechargeRecord rechargeRecord);
	
	/**
	 * 获取指定订单的充值记录
	 * @param orderId 订单号
	 * @return
	 */
	public RechargeRecord getRecordByOrder(String orderId);
	
	/**
	 * 获取指定ID的充值记录
	 * @param id 充值记录ID
	 * @return
	 */
	public RechargeRecord getRecordById(Long id);
	
	/**
	 * 获取指定ID的充值记录
	 * @param id 充值记录ID
	 * @param isException 是否抛出业务异常 true|false
	 * @return
	 */
	public RechargeRecord getRecordById(Long id, boolean isException);
	
}
