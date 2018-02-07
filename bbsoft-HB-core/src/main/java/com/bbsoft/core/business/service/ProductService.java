package com.bbsoft.core.business.service;

import java.util.List;
import java.util.Map;

import com.bbsoft.core.business.domain.Product;

/**
 * 商品信息业务接口
 * @author Chris.Zhang
 * @date 2017-6-7 21:20:42
 *
 */
public interface ProductService {

	/**
	 * 新增商品信息
	 * @param product 商品信息
	 * @return
	 */
	public int addProduct(Product product);
	
	/**
	 * 修改商品信息
	 * @param product 商品信息
	 * @return
	 */
	public int updateProduct(Product product);
	
	/**
	 * 删除商品
	 * @param id 商品ID
	 * @return
	 */
	public int deleteProduct(Long id);
	
	/**
	 * 获取指定商品
	 * @param id 商品ID 
	 * @return
	 */
	public Product getProductById(Long id);
	
	/**
	 * 获取指定商品
	 * @param id 商品ID
	 * @param isException
	 * @return
	 */
	public Product getProductById(Long id, boolean isException);
	
	/**
	 * 获取商品记录数
	 * @param map 查询条件
	 * @return
	 */
	public int getProductCount(Map<String, Object> map);
	
	/**
	 * 分页获取商品记录
	 * @param map 查询条件
	 * @return
	 */
	public List<Product> getProductByPage(Map<String, Object> map);
	
	/**
	 * 获取商品列表
	 * @return
	 */
	public List<Product> getProducts();
}
