package com.bbsoft.core.user.service;

import java.util.List;
import java.util.Map;

import com.bbsoft.core.user.domain.UserBank;

/**
 * 用户银行卡业务接口
 * @author Chris.Zhang
 * @date 2017-5-16 18:25:22
 *
 */
public interface UserBankService {

	/**
	 * 新增银行卡
	 * @param userBank 银行卡信息
	 * @return
	 */
	public int addUserBank(UserBank userBank);
	
	/**
	 * 修改银行卡信息
	 * @param userBank 银行卡信息
	 * @return
	 */
	public int updateUserBank(UserBank userBank);
	
	/**
	 * 删除银行卡信息
	 * @param id 银行卡记录ID
	 * @return
	 */
	public int deleteUserBank(Long id);
	
	/**
	 * 多条件获取银行记录数
	 * @param map 查询条件
	 * @return
	 */
	public int getUserBankCount(Map<String, Object> map);
	
	/**
	 * 多条件分页获取银行记录
	 * @param map 查询条件
	 * @return
	 */
	public List<UserBank> getUserBankByPage(Map<String, Object> map);
	
	/**
	 * 获取指定用户的银行卡记录
	 * @param userId 用户ID
	 * @return
	 */
	public List<UserBank> getListByUser(String userId);
	
	/**
	 * 获取指定银行卡记录
	 * @param id 银行卡记录ID
	 * @return
	 */
	public UserBank getUserBankById(Long id);
	
	/**
	 * 获取指定银行卡记录
	 * @param id 银行卡记录ID
	 * @param isException 是否抛出业务异常 true|false
	 * @return
	 */
	public UserBank getUserBankById(Long id, boolean isException);
}
