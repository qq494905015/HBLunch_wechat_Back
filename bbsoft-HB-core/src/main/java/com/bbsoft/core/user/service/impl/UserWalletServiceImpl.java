package com.bbsoft.core.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.user.domain.UserWallet;
import com.bbsoft.core.user.mapper.UserWalletMapper;
import com.bbsoft.core.user.service.UserWalletService;

@Service
public class UserWalletServiceImpl implements UserWalletService {
	
	@Autowired
	private UserWalletMapper userWalletMapper;

	public int addWallet(UserWallet userWallet) {
		if(userWallet == null 
			|| userWallet.getUserId() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if(userWallet.getFrozenMoney() == null){
			userWallet.setFrozenMoney(0L);
		}
		if(userWallet.getCommision() == null){
			userWallet.setCommision(0L);
		}
		if(userWallet.getBalance() == null){
			userWallet.setBalance(0L);
		}
		if(userWalletMapper.selectWalletByUser(userWallet.getUserId()) != null){
			return 0;
		}
		return userWalletMapper.insertWallet(userWallet);
	}

	public int updateWallet(UserWallet userWallet) {
		if(userWallet == null ||
			StringUtil.isEmpty(userWallet.getUserId())){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return userWalletMapper.updateWallet(userWallet);
	}

	public UserWallet getWalletByUser(String userId) {
		if(StringUtil.isEmpty(userId)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return userWalletMapper.selectWalletByUser(userId);
	}

}
