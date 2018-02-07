package com.bbsoft.core.business.domain;

import java.util.Date;

/**
 * 店铺
 * @author Chris.Zhang
 * @date 2017-5-11 11:18:44
 */
public class Shop {

	private String id;			//店铺主键ID
	private String name;		//店铺名称
	private String shortName;	//店铺简称
	private String shopSn;		//店铺编码
	private String logo;		//店铺logo
	private String linkName;	//店铺负责人
	private String phone;		//负责人手机
	private String email;		//负责人邮箱
	private Date createTime;	//创建时间
	private Date updateTime;	//更新时间
	private String isOnline;	//是否上线，0：否，1：是
	private String isDelete;	//是否删除，0：否，1：是
	private String longitude;	//经度
	private String latitude;	//纬度
	private String address;		//地址
	private String startTime;	//门店营业开始时间
	private String endTime;		//门店营业结束时间
	private String description;	//门店介绍
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getShopSn() {
		return shopSn;
	}
	public void setShopSn(String shopSn) {
		this.shopSn = shopSn;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Shop() {
		super();
	}
	public Shop(String id, String name, String shortName, String shopSn, String logo, String linkName, String phone,
			String email, Date updateTime, String isOnline, String isDelete, String longitude,
			String latitude, String address, String startTime, String endTime, String description) {
		super();
		this.id = id;
		this.name = name;
		this.shortName = shortName;
		this.shopSn = shopSn;
		this.logo = logo;
		this.linkName = linkName;
		this.phone = phone;
		this.email = email;
		this.updateTime = updateTime;
		this.isOnline = isOnline;
		this.isDelete = isDelete;
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
	}
	public Shop(String name, String shortName, String shopSn, String logo, String linkName, String phone, String email,
			Date createTime, String isOnline, String isDelete, String longitude, String latitude, String address,
			String startTime, String endTime, String description) {
		super();
		this.name = name;
		this.shortName = shortName;
		this.shopSn = shopSn;
		this.logo = logo;
		this.linkName = linkName;
		this.phone = phone;
		this.email = email;
		this.createTime = createTime;
		this.isOnline = isOnline;
		this.isDelete = isDelete;
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
	}
	
}
