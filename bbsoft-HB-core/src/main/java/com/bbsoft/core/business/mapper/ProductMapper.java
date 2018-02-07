package com.bbsoft.core.business.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.business.domain.Product;

/**
 * 商品信息数据库访问接口
 * @author Chris.Zhang
 * @date 2017-6-7 21:36:49
 *
 */
public interface ProductMapper {

	/**
	 * 新增商品信息
	 * @param product 商品信息
	 * @return
	 */
	public int insertProduct(Product product);
	
	/**
	 * 更新商品信息
	 * @param product 商品信息
	 * @return
	 */
	public int updateProduct(Product product);
	
	/**
	 * 查询指定商品
	 * @param id 商品ID
	 * @return
	 */
	public Product selectProductById(Long id);
	
	/**
	 * 查询指定名称的商品数
	 * @param name 商品名称
	 * @return
	 */
	public int selectCountByName(@Param("name") String name);
	
	/**
	 * 查询商品数量
	 * @param map 查询条件
	 * @return
	 */
	public int selectProductCount(Map<String, Object> map);
	
	/**
	 * 分页查询商品信息
	 * @param map 查询条件
	 * @return
	 */
	public List<Product> selectProductByPage(Map<String, Object> map);
}
