package com.bbsoft.core.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.user.domain.UserBank;

/**
 * 会员银行卡信息数据库访问接口
 * @author Chris.Zhang
 * @date 2017-5-16 18:07:49
 *
 */
public interface UserBankMapper {

	/**
	 * 查询指定ID的银行卡记录
	 * @param id 银行卡记录ID
	 * @return
	 */
	public UserBank selectById(@Param("id") Long id);
	
	/**
	 * 多条件查询会员银行卡记录数
	 * @param map 查询条件
	 * @return
	 */
	public int selectCountUserBank(Map<String, Object> map);
	
	/**
	 * 多条件分页查询会员银行卡记录
	 * @param map 查询条件
	 * @return
	 */
	public List<UserBank> selectUserBankByPage(Map<String, Object> map);
	
	/**
	 * 查询指定用户的银行记录信息
	 * @param userId 用户ID
	 * @return
	 */
	public List<UserBank> selectByUser(@Param("userId") String userId);
	
	/**
	 * 新增会员银行卡记录
	 * @param userBank 银行卡信息
	 * @return
	 */
	public int insertUserBank(UserBank userBank);
	
	/**
	 * 修改会员银行卡记录
	 * @param userBank 银行卡信息
	 * @return
	 */
	public int updateUserBank(UserBank userBank);
	
	/**
	 * 删除银行卡信息
	 * @param id 银行卡信息ID
	 * @return
	 */
	public int deleteUserBank(@Param("id") Long id);
}
