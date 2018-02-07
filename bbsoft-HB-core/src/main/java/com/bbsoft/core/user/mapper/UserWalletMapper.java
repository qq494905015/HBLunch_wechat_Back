package com.bbsoft.core.user.mapper;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.user.domain.UserWallet;

/**
 * 用户钱包数据库访问接口
 * @author Chris.Zhang
 * @date 2017-5-22 17:27:36
 *
 */
public interface UserWalletMapper {

	/**
	 * 新增用户钱包
	 * @param userWallet 用户钱包
	 * @return
	 */
	public int insertWallet(UserWallet userWallet);
	
	/**
	 * 修改用户钱包信息
	 * @param userWallet 用户钱包
	 * @return
	 */
	public int updateWallet(UserWallet userWallet);
	
	/**
	 * 查询指定用户的钱包信息
	 * @param userId 用户ID
	 * @return
	 */
	public UserWallet selectWalletByUser(@Param("userId") String userId);
	
}
