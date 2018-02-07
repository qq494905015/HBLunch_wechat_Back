package com.bbsoft.core.user.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.user.domain.RechargeRecord;
import com.bbsoft.core.user.mapper.RechargeRecordMapper;
import com.bbsoft.core.user.service.RechargeRecordService;

@Service
public class RechargeRecordServiceImpl implements RechargeRecordService {
	
	@Autowired
	private RechargeRecordMapper rechargeRecordMapper;

	public int addRechargeRecord(RechargeRecord rechargeRecord) {
		if(rechargeRecord == null
			|| StringUtil.isEmpty(rechargeRecord.getUserId())
			|| StringUtil.isEmpty(rechargeRecord.getOrderId())
			|| StringUtil.isEmpty(rechargeRecord.getCardNo())
			|| rechargeRecord.getMoney() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if(StringUtil.isEmpty(rechargeRecord.getStatus())){
			rechargeRecord.setStatus("0");
		}
		if(rechargeRecord.getCreateTime() == null){
			rechargeRecord.setCreateTime(new Date());
		}
		if(StringUtil.isEmpty(rechargeRecord.getIsDelete())){
			rechargeRecord.setIsDelete("0");
		}
		return rechargeRecordMapper.insertRechargeRecord(rechargeRecord);
	}

	public int updateRechargeRecord(RechargeRecord rechargeRecord) {
		if(rechargeRecord == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		rechargeRecord.setUpdateTime(new Date());
		return rechargeRecordMapper.updateRechargeRecord(rechargeRecord);
	}

	public RechargeRecord getRecordByOrder(String orderId) {
		if(StringUtil.isEmpty(orderId)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return rechargeRecordMapper.selectRecordByOrder(orderId);
	}

	public RechargeRecord getRecordById(Long id) {
		return this.getRecordById(id, false);
	}

	public RechargeRecord getRecordById(Long id, boolean isException) {
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		RechargeRecord rechargeRecord = rechargeRecordMapper.selectById(id);
		if(rechargeRecord == null && isException){
			throw new ServiceException(MsgeData.CARD_1000030006.getCode());
		}
		return rechargeRecord;
	}

}
