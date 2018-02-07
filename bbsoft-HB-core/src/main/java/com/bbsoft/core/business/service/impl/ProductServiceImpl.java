package com.bbsoft.core.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.business.domain.Product;
import com.bbsoft.core.business.mapper.ProductMapper;
import com.bbsoft.core.business.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductMapper productMapper;

	public int addProduct(Product product) {
		if(product == null
			|| StringUtil.isEmpty(product.getName())
			|| product.getPrice() == null
			|| StringUtil.isEmpty(product.getMainImg())
//			|| StringUtil.isEmpty(product.getMinImg())
			|| StringUtil.isEmpty(product.getDescription())){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if(productMapper.selectCountByName(product.getName()) > 0){
			throw new ServiceException(MsgeData.CARD_1000030011.getCode());
		}
		return productMapper.insertProduct(product);
	}

	public int updateProduct(Product product) {
		if(product == null || product.getId() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Product dbProduct = this.getProductById(product.getId(), true);
		if(!StringUtil.isEmpty(product.getName()) && !dbProduct.getName().equals(product.getName())){
			if(productMapper.selectCountByName(product.getName()) > 0){
				throw new ServiceException(MsgeData.CARD_1000030011.getCode());
			}
		}
		return productMapper.updateProduct(product);
	}

	public int deleteProduct(Long id) {
		this.getProductById(id, true);
		Product product = new Product();
		product.setId(id);
		product.setIsDelete("1");
		return productMapper.updateProduct(product);
	}

	public Product getProductById(Long id) {
		return this.getProductById(id, false);
	}
	
	public Product getProductById(Long id, boolean isException){
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Product product = productMapper.selectProductById(id);
		if(product == null && isException){
			throw new ServiceException(MsgeData.CARD_1000030012.getCode());
		}
		return product;
	}

	public int getProductCount(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return productMapper.selectProductCount(map);
	}

	public List<Product> getProductByPage(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		map.put("startItem", map.get("startItem") == null ? 0 : map.get("startItem"));
		map.put("pageSize", map.get("pageSize") == null ? 10 : map.get("pageSize"));
		return productMapper.selectProductByPage(map);
	}

	public List<Product> getProducts() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startItem", 0);
		map.put("pageSize", 999999);
		return productMapper.selectProductByPage(map);
	}

}
