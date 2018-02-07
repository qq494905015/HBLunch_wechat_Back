package com.bbsoft.core.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.user.domain.UserBank;
import com.bbsoft.core.user.mapper.UserBankMapper;
import com.bbsoft.core.user.service.UserBankService;

@Service
public class UserBankServiceImpl implements UserBankService {
	
	@Autowired
	private UserBankMapper userBankMapper;

	public int addUserBank(UserBank userBank) {
		if(userBank == null
			|| StringUtil.isEmpty(userBank.getUserId())
			|| StringUtil.isEmpty(userBank.getBankNo())
			|| StringUtil.isEmpty(userBank.getRealName())
			|| StringUtil.isEmpty(userBank.getPhone())
			|| StringUtil.isEmpty(userBank.getBank())){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		checkIsExist(userBank);
		userBank.setCreateTime(new Date());
		userBank.setIsDelete("0");
		return userBankMapper.insertUserBank(userBank);
	}

	public int updateUserBank(UserBank userBank) {
		if(userBank == null
			|| userBank.getId() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		UserBank dbBank = this.getUserBankById(userBank.getId(), true);
		if(!StringUtil.isEmpty(userBank.getBankNo()) && !userBank.getBankNo().equals(dbBank.getBankNo())){
			checkIsExist(userBank);
		}
		return userBankMapper.updateUserBank(userBank);
	}

	public int deleteUserBank(Long id) {
		this.getUserBankById(id, true);
		return userBankMapper.deleteUserBank(id);
	}

	public int getUserBankCount(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return userBankMapper.selectCountUserBank(map);
	}

	public List<UserBank> getUserBankByPage(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		map.put("startItem", map.get("startItem") == null ? 0 : map.get("startItem"));
		map.put("pageSize", map.get("pageSize") == null ? 0 : map.get("pageSize"));
		return userBankMapper.selectUserBankByPage(map);
	}

	public List<UserBank> getListByUser(String userId) {
		if(StringUtil.isEmpty(userId)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return userBankMapper.selectByUser(userId);
	}

	public UserBank getUserBankById(Long id) {
		return this.getUserBankById(id, false);
	}

	public UserBank getUserBankById(Long id, boolean isException) {
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		UserBank userBank = userBankMapper.selectById(id);
		if(userBank == null && isException){
			throw new ServiceException(MsgeData.USER_1000020011.getCode());
		}
		return userBank;
	}

	/**
	 * 校验银行卡信息是否存在
	 * @param userBank
	 */
	private void checkIsExist(UserBank userBank){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userBank.getUserId());
		map.put("bankNo", userBank.getBankNo());
		if(userBankMapper.selectCountUserBank(map) > 0){
			throw new ServiceException(MsgeData.USER_1000020010.getCode());
		}
	}
}
