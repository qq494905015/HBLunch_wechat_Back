package com.bbsoft.core.user.service;

import com.bbsoft.core.user.domain.UserWallet;

/**
 * 用户钱包业务接口
 * @author Chris.Zhang
 * @date 2017-5-22 17:57:40
 *
 */
public interface UserWalletService {

	/**
	 * 新增用户钱包信息
	 * @param userWallet 用户钱包
	 * @return
	 */
	public int addWallet(UserWallet userWallet);
	
	/**
	 * 修改用户钱包信息
	 * @param userWallet 用户钱包
	 * @return
	 */
	public int updateWallet(UserWallet userWallet);
	
	/**
	 * 根据用户获取钱包信息
	 * @param userId 用户ID
	 * @return
	 */
	public UserWallet getWalletByUser(String userId);
}
