package com.bbsoft.core.user.service;

import java.util.List;
import java.util.Map;

import com.bbsoft.core.user.domain.CashRecord;

/**
 * 会员提现业务接口
 * @author Chris.Zhang
 * @date 2017-5-16 18:21:54
 *
 */
public interface CashRecordService {
	
	/**
	 * 新增会员提现记录
	 * @param cashRecord 会员提现记录
	 * @return
	 */
	public int addCashRecord(CashRecord cashRecord);
	
	/**
	 * 修改会员提现记录
	 * @param cashRecord 会员提现记录
	 * @return
	 */
	public int updateCashRecord(CashRecord cashRecord);
	
	/**
	 * 删除会员提现记录
	 * @param id 会员提现记录ID
	 * @return
	 */
	public int deleteCashRecord(Long id);
	
	/**
	 * 多条件获取会员提现记录数
	 * @param map 查询条件
	 * @return
	 */
	public int getCashRecordCount(Map<String, Object> map);
	
	/**
	 * 多条件分页获取会员提现记录
	 * @param map 查询条件
	 * @return
	 */
	public List<CashRecord> getCashRecordByPage(Map<String, Object> map);
	
	/**
	 * 获取指定ID的会员提现记录
	 * @param id 会员提现记ID
	 * @return
	 */
	public CashRecord getCashRecordById(Long id);
	
	/**
	 * 获取指定ID的会员提现记录
	 * @param id 会员提现记录ID
	 * @param isException 是否抛出业务异常 true|false
	 * @return
	 */
	public CashRecord getCashRecordById(Long id, boolean isException);

}
