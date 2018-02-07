package com.bbsoft.core.business.service;

import java.util.List;
import java.util.Map;

import com.bbsoft.core.business.domain.ShopTotal;

/**
 * 门店日统计业务接口
 * @author Chris.Zhang
 * @date 2017-6-13 15:56:06
 *
 */
public interface ShopTotalService {

	/**
	 * 新增门店日统计记录
	 * @param shopTotal 记录
	 * @return
	 */
	public Long addShopTotal(ShopTotal shopTotal);
	
	/**
	 * 修改门店日统计记录
	 * @param totals 需要修改的记录信息
	 * @return
	 */
	public int updateShopTotal(ShopTotal totals);
	
	/**
	 * 批量修改门店日统计记录
	 * @param shopTotal 记录
	 * @return
	 */
	public int updateShopTotals(List<ShopTotal> shopTotals);
	
	/**
	 * 查询门店日统计记录数
	 * @param map 查询条件
	 * @return
	 */
	public int getTotalCount(Map<String, Object> map);
	
	/**
	 * 分页查询门店日统计记录
	 * @param map 查询条件
	 * @return
	 */
	public List<ShopTotal> getTotalByPage(Map<String, Object> map);
	
	/**
	 * 查询指定门店日统计记录
	 * @param id 记录ID
	 * @return
	 */
	public ShopTotal getShopTotalById(Long id);
}
