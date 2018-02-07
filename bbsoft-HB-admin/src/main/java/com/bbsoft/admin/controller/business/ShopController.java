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
import com.bbsoft.core.business.domain.Shop;
import com.bbsoft.core.business.service.ShopService;

/**
 * 店铺接口
 * @author Chris.Zhang
 * @date 2017-5-23 14:13:05
 *
 */
@Controller
public class ShopController extends BaseController{

	@Autowired
	private ShopService shopService;
	
	/**
	 * 新增店铺
	 * @param name 店铺名称
	 * @param shortName 店铺简称
	 * @param shopSn 店铺编码
	 * @param logo 店铺LOGO
	 * @param description 店铺介绍
	 * @param isOnline 是否上线，0：否，1：是
	 * @param phone 联系人电话
	 * @param email 联系人邮箱
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param address 店铺地址
	 * @param linkName 门店负责人名字
	 * @param startTime 店铺营业开始时间
	 * @param endTime 店铺营业结束时间
	 * @return
	 */
	@RequestMapping("shop200000")
	@ResponseBody
	public Json addShop(
			String name, String shortName, String shopSn,
			String logo, String description, String isOnline, 
			String phone, String email, String longitude,
			String latitude, String address, String linkName,
			String startTime, String endTime){
		if(StringUtil.isEmpty(name) 
			|| StringUtil.isEmpty(shortName)
			|| StringUtil.isEmpty(description)
			|| StringUtil.isEmpty(phone)
			|| StringUtil.isEmpty(address)
			|| StringUtil.isEmpty(linkName)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Shop shop = new Shop(name, shortName, shopSn, logo, linkName, phone, email, new Date(), isOnline, "0", longitude, latitude, address, startTime, endTime, description);
		shopService.addShop(shop);
		return getSuccessObj();
	}
	
	/**
	 * 修改店铺
	 * @param id 店铺ID
	 * @param name 店铺名称
	 * @param shortName 店铺简称
	 * @param shopSn 店铺编码
	 * @param logo 店铺LOGO
	 * @param description 店铺介绍
	 * @param isOnline 是否上线，0：否，1：是
	 * @param phone 联系人电话
	 * @param email 联系人邮箱
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param address 店铺地址
	 * @param linkName 门店负责人名字
	 * @param startTime 店铺营业开始时间
	 * @param endTime 店铺营业结束时间
	 * @return
	 */
	@RequestMapping("shop200001")
	@ResponseBody
	public Json updateShop(
			String id, String name, String shortName, 
			String shopSn, String logo, String description, 
			String isOnline, String phone, String email, 
			String longitude, String latitude, String address, 
			String linkName, String startTime, String endTime){
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Shop shop = new Shop(id, name, shortName, shopSn, logo, linkName, phone, email, new Date(), isOnline, null, longitude, latitude, address, startTime, endTime, description);
		shopService.updateShop(shop);
		return getSuccessObj();
	}
	
	/**
	 * 删除店铺
	 * @param id 店铺ID
	 * @return
	 */
	@RequestMapping("shop200002")
	@ResponseBody
	public Json deleteShop(String id){
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		shopService.deleteShop(id);
		return getSuccessObj();
	}
	
	/**
	 * 分页获取店铺列表
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @param search 搜索关键字
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("shop200003")
	@ResponseBody
	public Json getShopByPage(Integer pageNum, Integer pageSize, String search){
		if(pageNum == null || pageSize == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", StringUtil.isEmpty(search) ? null : search);
		int total = shopService.getShopCount(map);
		PageUtil<Map> page = new PageUtil<Map>(pageNum, pageSize, total);
		map.put("startItem", page.getStartItem());
		map.put("pageSize", page.getPageSize());
		List<Shop> shops = shopService.getShopByPage(map);
		List<Map> list = BeanToMapUtil.convertList(shops);
		page.setItems(list);
		return getSuccessObj(ResultUtil.returnByObj(page));
	}
	
	/**
	 * 获取指定ID的店铺信息
	 * @param id 店铺ID
	 * @return
	 */
	@RequestMapping("shop200004")
	@ResponseBody
	public Json getShopById(String id){
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return getSuccessObj(ResultUtil.returnByObj(shopService.getShopById(id, true)));
	}
	
	/**
	 * 获取所有门店列表
	 * @param search 搜索关键字
	 * @return
	 */
	@RequestMapping("shop200005")
	@ResponseBody
	public Json getShops(String search){
		if(StringUtil.isEmpty(search)){
			search = null;
		}
		List<Shop> shops = shopService.getShops(search);
		return getSuccessObj(ResultUtil.returnByList(shops));
	}
}
