package com.bbsoft.core.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.user.domain.CashRecord;

/**
 * 会员提现记录数据库访问接口
 * @author Chris.Zhang
 * @date 2017-5-16 17:47:50
 *
 */
public interface CashRecordMapper {

	/**
	 * 查询指定ID的会员提现记录
	 * @param id 提现记录ID
	 * @return
	 */
	public CashRecord selectById(@Param("id") Long id);
	
	/**
	 * 多条件查询会员提现记录数
	 * @param map 查询条件
	 * @return
	 */
	public int selectCountCashRecord(Map<String, Object> map);
	
	/**
	 * 多条件分页查询会员提现记录
	 * @param map 查询条件
	 * @return
	 */
	public List<CashRecord> selectCashRecordByPage(Map<String, Object> map);
	
	/**
	 * 新增会员提现记录
	 * @param cashRecord 记录
	 * @return
	 */
	public int insertCashRecord(CashRecord cashRecord);
	
	/**
	 * 修改会员提现记录
	 * @param cashRecord 记录
	 * @return
	 */
	public int updateCashRecord(CashRecord cashRecord);
	
	/**
	 * 删除提现记录信息
	 * @param id 记录ID
	 * @return
	 */
	public int deleteCashRecord(@Param("id") Long id);
	
	/**
	 * 查询当前提现中(未提现结束的)总金额
	 * @param userId
	 * @param status
	 * @return
	 */
	public Long selectMoneyByUser(@Param("userId") String userId, @Param("status") String status);
}
