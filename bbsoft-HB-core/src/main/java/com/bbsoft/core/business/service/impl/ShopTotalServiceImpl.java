package com.bbsoft.core.business.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.business.domain.ShopTotal;
import com.bbsoft.core.business.mapper.ShopTotalMapper;
import com.bbsoft.core.business.service.ShopTotalService;

@Service
public class ShopTotalServiceImpl implements ShopTotalService {
	
	private Logger logger = LoggerFactory.getLogger(ShopTotalServiceImpl.class);
	
	@Autowired
	private ShopTotalMapper shopTotalMapper;

	public Long addShopTotal(ShopTotal shopTotal) {
		if(shopTotal == null
			|| StringUtil.isEmpty(shopTotal.getTotalTime())
			|| StringUtil.isEmpty(shopTotal.getShopId())){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		ShopTotal dbShopTotal = shopTotalMapper.selectTotalByTime(shopTotal.getTotalTime(), shopTotal.getShopId());
		ShopTotal editShopTotal = new ShopTotal();
//		int result = 0;
		if(dbShopTotal != null){
			editShopTotal.setId(dbShopTotal.getId());
			editShopTotal.setTotal(dbShopTotal.getTotal() + shopTotal.getTotal());
			shopTotal.setId(dbShopTotal.getId());
			logger.debug("update shopTotal id =====================================" + shopTotal.getId());
			logger.debug("dbShopTotal id =====================================" + dbShopTotal.getId());
			shopTotalMapper.updateShopTotal(editShopTotal);
			return dbShopTotal.getId();
		}else{
			if(shopTotal.getCreateTime() == null){
				shopTotal.setCreateTime(new Date());
			}
			if(shopTotal.getTotal() == null){
				shopTotal.setTotal(0L);
			}
			if(StringUtil.isEmpty(shopTotal.getStatus())){
				shopTotal.setStatus("0");
			}
			shopTotalMapper.insertShopTotal(shopTotal);
			logger.debug("insert shopTotal id =====================================" + shopTotal.getId());
			return shopTotal.getId();
		}
	}
	
	public int updateShopTotal(ShopTotal shopTotal) {
		if(shopTotal == null
			|| shopTotal.getId() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return shopTotalMapper.updateShopTotal(shopTotal);
	}
	
	public synchronized int updateShopTotals(List<ShopTotal> totals){
		int result = 0;
		if(totals != null && totals.size() > 0){
			for(ShopTotal shopTotal : totals){
				ShopTotal editShopTotal = new ShopTotal();
				editShopTotal.setId(shopTotal.getId());
				editShopTotal.setStatus("1");
				result = this.updateShopTotal(editShopTotal);
			}
		}
		return result;
	}

	public int getTotalCount(Map<String, Object> map) {
		checkSearch(map);
		return shopTotalMapper.selectTotalCount(map);
	}

	public List<ShopTotal> getTotalByPage(Map<String, Object> map) {
		checkSearch(map);
		map.put("startItem", map.get("startItem") == null ? 0 : map.get("startItem"));
		map.put("pageSize", map.get("pageSize") == null ? 0 : map.get("pageSize"));
		return shopTotalMapper.selectTotalByPage(map);
	}
	
	public ShopTotal getShopTotalById(Long id) {
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return shopTotalMapper.selectById(id);
	}
	
	private Map<String, Object> checkSearch(Map<String, Object> map){
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return map;
	}


}
