package com.bbsoft.core.business.service;

import java.util.List;
import java.util.Map;

import com.bbsoft.core.business.domain.Shop;

/**
 * 店铺业务接口
 * @author Chris.Zhang
 * @date 2017-5-11 13:23:39
 */
public interface ShopService {
	
	/**
	 * 新增店铺信息
	 * @param shop 店铺
	 * @return
	 */
	public int addShop(Shop shop);

	/**
	 * 修改店铺信息
	 * @param shop 店铺
	 * @return
	 */
	public int updateShop(Shop shop);
	
	/**
	 * 删除店铺
	 * @param id 店铺ID
	 * @return
	 */
	public int deleteShop(String id);
	
	/**
	 * 查询店铺记录数
	 * @param map 查询条件
	 * @return
	 */
	public int getShopCount(Map<String, Object> map);
	
	/**
	 * 分页查询店铺记录
	 * @param map 查询条件
	 * @return
	 */
	public List<Shop> getShopByPage(Map<String, Object> map);

	/**
	 * 获取指定店铺信息
	 * @param id 店铺ID
	 * @return
	 */
	public Shop getShopById(String id);
	
	/**
	 * 获取指定店铺信息
	 * @param id 店铺ID
	 * @param isException 是否抛出异常 true|false
	 * @return
	 */
	public Shop getShopById(String id, boolean isException);
	
	/**
	 * 查询附近门店信息列表
	 * @param map 查询条件
	 * @return
	 */
	public List<Map<String, Object>> getNearShops(Map<String, Object> map);
	
	/**
	 * 获取所有门店信息
	 * @param search 搜索关键字
	 * @return
	 */
	public List<Shop> getShops(String search);
}
