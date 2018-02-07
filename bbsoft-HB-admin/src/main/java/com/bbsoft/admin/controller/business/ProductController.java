package com.bbsoft.admin.controller.business;

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
import com.bbsoft.common.util.PageUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.business.domain.Product;
import com.bbsoft.core.business.service.ProductService;

/**
 * 商品信息接口
 * @author Chris.Zhang
 * @date 2017-6-7 15:38:03
 *
 */
@Controller
public class ProductController extends BaseController{

	@Autowired
	private ProductService productService;
	
	/**
	 * 新增商品信息
	 * @param name 商品名称
	 * @param price 商品售价
	 * @param mainImg 商品主图
	 * @param minImg 商品缩略图
	 * @param description 商品描述
	 * @return
	 */
	@RequestMapping("product200000")
	@ResponseBody
	public Json addProduct(
			String name, Long price, String mainImg, 
			String minImg, String description){
		if(StringUtil.isEmpty(name)
			|| price == null
			|| StringUtil.isEmpty(mainImg)
//			|| StringUtil.isEmpty(minImg)
			|| StringUtil.isEmpty(description)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Product product = new Product(name, price, mainImg, minImg, description, "0", new Date(), null);
		productService.addProduct(product);
		return getSuccessObj();
	}
	
	/**
	 * 修改商品信息
	 * @param id 商品ID
	 * @param name 商品名称
	 * @param price 商品售价
	 * @param mainImg 商品主图
	 * @param minImg 商品缩略图
	 * @param description 商品描述
	 * @return
	 */
	@RequestMapping("product200001")
	@ResponseBody
	public Json updateProduct(
			Long id, String name, Long price, 
			String mainImg, String minImg, String description){
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Product product = new Product(id, name, price, mainImg, minImg, description, null, null, new Date());
		productService.updateProduct(product);
		return getSuccessObj();
	}
	
	/**
	 * 删除商品信息
	 * @param id 商品ID
	 * @return
	 */
	@RequestMapping("product200002")
	@ResponseBody
	public Json deleteProduct(Long id){
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		productService.deleteProduct(id);
		return getSuccessObj();
	}
	
	/**
	 * 分页查询商品信息
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @param search 搜索关键字
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("product200003")
	@ResponseBody
	public Json getProductByPage(Integer pageNum, Integer pageSize, String search){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", StringUtil.isEmpty(search) ? null : search);
		int total = productService.getProductCount(map);
		PageUtil<Map> page = new PageUtil<>(pageNum, pageSize, total);
		List<Product> products = productService.getProductByPage(map);
		page.setItems(BeanToMapUtil.convertList(products));
		return getSuccessObj(ResultUtil.returnByObj(page));
	}
	
	/**
	 * 获取商品列表
	 * @return
	 */
	@RequestMapping("product200004")
	@ResponseBody
	public Json getProducts(){
		List<Product> products = productService.getProducts();
		return getSuccessObj(ResultUtil.returnByList(products));
	}
}
