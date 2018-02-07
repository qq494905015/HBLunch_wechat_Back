package com.bbsoft.core.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.user.domain.CashRecord;
import com.bbsoft.core.user.domain.UserWallet;
import com.bbsoft.core.user.mapper.CashRecordMapper;
import com.bbsoft.core.user.service.CashRecordService;
import com.bbsoft.core.user.service.UserWalletService;

@Service
public class CashRecordServiceImpl implements CashRecordService {
	
	@Autowired
	private CashRecordMapper cashRecordMapper;
	@Autowired
	private UserWalletService userWalletService;
	

	public int addCashRecord(CashRecord cashRecord) {
		if(cashRecord == null 
			|| StringUtil.isEmpty(cashRecord.getUserId())
			|| StringUtil.isEmpty(cashRecord.getBankNo())
			|| StringUtil.isEmpty(cashRecord.getBank())
			|| cashRecord.getMoney() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if(StringUtil.isEmpty(cashRecord.getStatus())){
			cashRecord.setStatus("0");
		}
		if(cashRecord.getCreateTime() == null){
			cashRecord.setCreateTime(new Date());
		}
		if(StringUtil.isEmpty(cashRecord.getIsDelete())){
			cashRecord.setIsDelete("0");
		}
		//提现规格校验，不能超过可提现额度
		UserWallet userWallet = userWalletService.getWalletByUser(cashRecord.getUserId());
		//提现中的中金额
		Long currTotal = cashRecordMapper.selectMoneyByUser(cashRecord.getUserId(), "0"); 
		if(currTotal == null){
			currTotal = 0L;
		}
		if((userWallet.getCommision() - currTotal) < cashRecord.getMoney()){
			throw new ServiceException(MsgeData.CARD_1000030007.getCode());
		}
		return cashRecordMapper.insertCashRecord(cashRecord);
		
	}

	public int updateCashRecord(CashRecord cashRecord) {
		if(cashRecord == null
			|| cashRecord.getId() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		CashRecord dbRecord = this.getCashRecordById(cashRecord.getId(), true);
		if(cashRecord.getUpdateTime() == null){
			cashRecord.setUpdateTime(new Date());
		}
		//当时更新记录为提现成功，则需要扣除用户佣金
		if("1".equals(cashRecord.getStatus()) && "0".equals(dbRecord.getStatus())){
			UserWallet dbWallet = userWalletService.getWalletByUser(dbRecord.getUserId());
			if(dbWallet.getCommision() >= dbRecord.getMoney()){
				//更改需要的信息
				UserWallet userWallet = new UserWallet();
				userWallet.setUserId(dbWallet.getUserId());
				userWallet.setCommision(dbWallet.getCommision() - dbRecord.getMoney());
				userWalletService.updateWallet(userWallet);
			}else{
				throw new ServiceException(MsgeData.USER_1000020016.getCode());
			}
		}
		return cashRecordMapper.updateCashRecord(cashRecord);
	}

	public int deleteCashRecord(Long id) {
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		this.getCashRecordById(id, true);
		return cashRecordMapper.deleteCashRecord(id);
	}

	public int getCashRecordCount(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return cashRecordMapper.selectCountCashRecord(map);
	}

	public List<CashRecord> getCashRecordByPage(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		map.put("startItem", map.get("startItem") == null ? 0 : map.get("startItem"));
		map.put("pageSize", map.get("pageSize") == null ? 0 : map.get("pageSize"));
		return cashRecordMapper.selectCashRecordByPage(map);
	}

	public CashRecord getCashRecordById(Long id) {
		return this.getCashRecordById(id, false);
	}

	public CashRecord getCashRecordById(Long id, boolean isException) {
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		CashRecord cashRecord = cashRecordMapper.selectById(id);
		if(cashRecord == null && isException){
			throw new ServiceException(MsgeData.CARD_1000030008.getCode());
		}
		return cashRecord;
	}

}
