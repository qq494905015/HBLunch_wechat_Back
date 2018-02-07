package com.bbsoft.admin.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.admin.controller.BaseController;
import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.BeanToMapUtil;
import com.bbsoft.common.util.DateUtil;
import com.bbsoft.common.util.PageUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.user.domain.CashRecord;
import com.bbsoft.core.user.service.CashRecordService;

/**
 * 用户提现记录接口
 * @author Chris.Zhang
 * @date 2017-8-7 14:17:24
 *
 */
@Controller
public class CashRecordController extends BaseController{

	@Autowired
	private CashRecordService cashRecordService;
	
	/**
	 * 分页获取用户提现记录
	 * @param userId 用户ID
	 * @param bankNo 银行卡号
	 * @param status 状态 （0：提现中，提现成功）
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("cash200000")
	@ResponseBody
	public Json getRecordByPage(
			String userId, String bankNo, String status, 
			String startTime, String endTime, Integer pageNum, 
			Integer pageSize){
		if(!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)){
			Date start = DateUtil.formatStringToDate(startTime);
			Date end = DateUtil.formatStringToDate(endTime);
			if(start.getTime() > end.getTime()){
				throw new ServiceException(MsgeData.PUBLIC_1000010017.getCode());
			}
		}
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("userId", StringUtil.isEmpty(userId) ? null : userId);
		searchMap.put("bankNo", StringUtil.isEmpty(bankNo) ? null : bankNo);
		searchMap.put("status", StringUtil.isEmpty(status) ? null : status);
		searchMap.put("startTime", StringUtil.isEmpty(startTime) ? null : startTime);
		searchMap.put("endTime", StringUtil.isEmpty(endTime) ? null : endTime);
		
		int total = cashRecordService.getCashRecordCount(searchMap);
		PageUtil<Map> page = new PageUtil<Map>(pageNum, pageSize, total);
		searchMap.put("startItem", page.getStartItem());
		searchMap.put("pageSize", page.getPageSize());
		
		List<CashRecord> records = cashRecordService.getCashRecordByPage(searchMap);
		page.setItems(BeanToMapUtil.convertList(records));
		return getSuccessObj(ResultUtil.returnByObj(page));
	}
	
	/**
	 * 确认提现
	 * @param id 提现记录ID
	 * @return
	 */
	@RequestMapping("cash200001")
	@ResponseBody
	public Json commitCash(Long id){
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		CashRecord cashRecord = new CashRecord();
		cashRecord.setId(id);
		cashRecord.setStatus("1");
		cashRecordService.updateCashRecord(cashRecord);
		return getSuccessObj();
	}
}
