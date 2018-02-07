package com.bbsoft.core.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.HttpRequestUtil;
import com.bbsoft.common.util.LocationUtil;
import com.bbsoft.common.util.SnowflakeIdUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.business.domain.Shop;
import com.bbsoft.core.business.mapper.ShopMapper;
import com.bbsoft.core.business.service.ShopService;
import com.bbsoft.core.user.domain.WechatUser;
import com.bbsoft.core.user.service.WechatUserService;

import net.sf.json.JSONObject;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	private ShopMapper shopMapper;
	@Autowired
	private WechatUserService wechatUserService;

	public int addShop(Shop shop) {
		if(shop == null 
			|| StringUtil.isEmpty(shop.getName())
			|| StringUtil.isEmpty(shop.getShortName())
			|| StringUtil.isEmpty(shop.getPhone())
			|| StringUtil.isEmpty(shop.getLinkName())
			|| StringUtil.isEmpty(shop.getAddress())
			){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if(StringUtil.isEmpty(shop.getId())){
			shop.setId(SnowflakeIdUtil.getGeneratedKey());
		}
		if(StringUtil.isEmpty(shop.getShopSn())){
			shop.setShopSn(shop.getId());
		}
		if(shopMapper.selectCountByName(shop.getName()) > 0){
			throw new ServiceException(MsgeData.SHOP_1000040001.getCode());
		}
//		JSONObject resultObj = LocationUtil.convertCoordinate(shop.getLongitude(), shop.getLatitude(), "3");
//		if(resultObj != null){
//			JSONObject coordObject = resultObj.getJSONObject("locations");
//			shop.setLatitude(coordObject.get("lat").toString());
//			shop.setLongitude(coordObject.getString("lng").toString());
//		}
		return shopMapper.insertShop(shop);
	}

	public int updateShop(Shop shop) {
		if(shop == null
			|| StringUtil.isEmpty(shop.getId())
			){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Shop dbShop = this.getShopById(shop.getId(), true);
		if(!dbShop.getName().equals(shop.getName())){
			if(shopMapper.selectCountByName(shop.getName()) > 0){
				throw new ServiceException(MsgeData.SHOP_1000040001.getCode());
			}
		}
//		if(!StringUtil.isEmpty(shop.getLatitude()) || !StringUtil.isEmpty(shop.getLongitude())){
//			JSONObject resultObj = LocationUtil.convertCoordinate(shop.getLongitude(), shop.getLatitude(), "3");
//			if(resultObj != null){
//				JSONObject coordObject = resultObj.getJSONObject("locations");
//				shop.setLatitude(coordObject.get("lat").toString());
//				shop.setLongitude(coordObject.getString("lng").toString());
//			}
//		}
		return shopMapper.updateShop(shop);
	}

	public int deleteShop(String id) {
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		this.getShopById(id, true);
		return shopMapper.deleteShop(id);
	}

	public int getShopCount(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return shopMapper.selectShopCount(map);
	}

	public List<Shop> getShopByPage(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		map.put("startItem", map.get("startItem") == null ? 0 : map.get("startItem"));
		map.put("pageSize", map.get("pageSize") == null ? 10 : map.get("pageSize"));
		return shopMapper.selectShopByPage(map);
	}

	public Shop getShopById(String id) {
		return this.getShopById(id, false);
	}

	public Shop getShopById(String id, boolean isException) {
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Shop shop = shopMapper.selectShopById(id);
		if(shop == null && isException){
			throw new ServiceException(MsgeData.SHOP_1000040002.getCode());
		}
		return shop;
	}

	public List<Map<String, Object>> getNearShops(Map<String, Object> map) {
		if(map == null 
			|| (map.get("openId") == null && map.get("lng") == null && map.get("lat") == null)){
			throw new ServiceException(MsgeData.SYSTEM_10128.getCode());
		}
		if(map.get("lng") == null || map.get("lat") == null){
			WechatUser wechatUser = wechatUserService.getByOpenId(map.get("openId").toString());
			if(StringUtil.isEmpty(wechatUser.getLng()) || StringUtil.isEmpty(wechatUser.getLat())){
				return null;
			}else{
				map.put("lng", wechatUser.getLng());
				map.put("lat", wechatUser.getLat());
			}
		}
		return shopMapper.selectNearShops(map);
	}

	public List<Shop> getShops(String search) {
		return shopMapper.selectShops(search);
	}
}
