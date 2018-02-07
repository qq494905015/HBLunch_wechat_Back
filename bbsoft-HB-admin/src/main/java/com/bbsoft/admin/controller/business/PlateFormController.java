package com.bbsoft.admin.controller.business;

import java.util.HashMap;
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
import com.bbsoft.core.user.service.ConsumeRecordService;

/**
 * 平台功能接口
 * @author Chris.Zhang
 * @date 2017-6-14 11:01:48
 *
 */
@Controller
public class PlateFormController extends BaseController{

	@Autowired
	private ConsumeRecordService consumeRecordService;
	
	/**
	 * 分页查询平台收入统计
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @param startTime 开始时间
	 * @param endTime结束时间
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("plateform200000")
	@ResponseBody
	public Json getIncomeTotalByPage(Integer pageNum, Integer pageSize, String startTime, String endTime){
		if(!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)){
			if(DateUtil.formatStringToDate(startTime, DateUtil.GLOBAL_DATE_PATTERN).getTime() > DateUtil.formatStringToDate(endTime, DateUtil.GLOBAL_DATE_PATTERN).getTime()){
				throw new ServiceException(MsgeData.PUBLIC_1000010017.getCode());
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", StringUtil.isEmpty(startTime) ? null : startTime);
		map.put("endTime", StringUtil.isEmpty(endTime) ? null : endTime);
		Map<String, Object> countMap = consumeRecordService.getPlateCount(map);
		int count = 0;
		if(countMap != null){
			count = Integer.parseInt(countMap.get("count").toString());
		}
		PageUtil<Map<String, Object>> page = new PageUtil<Map<String, Object>>(pageNum, pageSize, count);
		map.put("startItem", page.getStartItem());
		map.put("pageSize", page.getPageSize());
		page.setItems(consumeRecordService.getPlateTotalMap(map));
		Map<String, Object> resultMap = BeanToMapUtil.convertBean(page);
		resultMap.put("totalMoney", countMap.get("total"));
		return getSuccessObj(ResultUtil.returnByObj(resultMap));
	}
}
