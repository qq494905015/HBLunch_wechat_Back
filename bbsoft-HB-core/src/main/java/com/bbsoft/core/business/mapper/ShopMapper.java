package com.bbsoft.core.business.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.business.domain.Shop;

/**
 * 店铺数据库访问接口
 * @author Chris.Zhang
 * @date 2017-5-11 11:50:47
 */
public interface ShopMapper {

	/**
	 * 新增店铺信息
	 * @param shop 店铺
	 * @return
	 */
	public int insertShop(Shop shop);
	
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
	public int deleteShop(@Param("id") String id);
	
	/**
	 * 查询指定ID的店铺
	 * @param id 店铺主键ID
	 * @return
	 */
	public Shop selectShopById(@Param("id") String id);
	
	/**
	 * 根据条件查询店铺记录数
	 * @param map 查询条件
	 * @return
	 */
	public int selectShopCount(Map<String, Object> map);
	
	/**
	 * 分页查询店铺记录
	 * @param map 查询条件
	 * @return
	 */
	public List<Shop> selectShopByPage(Map<String, Object> map);
	
	/**
	 * 根据名称查询店铺记录数
	 * @param name 店铺名称
	 * @return
	 */
	public int selectCountByName(@Param("name") String name);
	
	/**
	 * 查询当前用户的附近门店信息列表
	 * @param map 查询条件 lng(经度）lat(纬度)
	 * @return
	 */
	public List<Map<String, Object>> selectNearShops(Map<String, Object> map);
	
	/**
	 * 查询所有店铺信息
	 * @param search 搜索关键字
	 * @return
	 */
	public List<Shop> selectShops(@Param("search") String search);
}
