package com.bbsoft.core.business.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.business.domain.ShopTotal;

/**
 * 门店日统计数据库访问接口
 * @author Chris.Zhang
 * @date 2017-6-13 15:32:18
 *
 */
public interface ShopTotalMapper {

	/**
	 * 新增统计记录
	 * @param shopTotal 记录
	 * @return
	 */
	public int insertShopTotal(ShopTotal shopTotal);
	
	/**
	 * 修改统计记录
	 * @param shopTotal 记录
	 * @return
	 */
	public int updateShopTotal(ShopTotal shopTotal);
	
	/**
	 * 查询指定
	 * @param id 记录ID
	 * @return
	 */
	public ShopTotal selectById(@Param("id") Long id);
	
	/**
	 * 查询指定统计日期和门店的记录
	 * @param totalTime 统计时间
	 * @param shopId 门店ID
	 * @return
	 */
	public ShopTotal selectTotalByTime(@Param("totalTime") String totalTime, @Param("shopId") String shopId);
	
	/**
	 * 查询统计记录数
	 * @param map 查询条件
	 * @return
	 */
	public int selectTotalCount(Map<String, Object> map);
	
	/**
	 * 分页查询统计记录
	 * @param map 查询条件
	 * @return
	 */
	public List<ShopTotal> selectTotalByPage(Map<String, Object> map);
	
}
