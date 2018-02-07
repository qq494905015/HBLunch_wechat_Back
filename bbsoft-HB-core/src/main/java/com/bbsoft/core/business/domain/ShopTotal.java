package com.bbsoft.core.business.domain;

import java.io.Serializable;
import java.util.Date;

public class ShopTotal implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;		//门店日统计主键ID
	private String shopId;	//门店ID（外键）
	private String shopName;//门店名称
	private String phone;	//负责人电话
	private Long total;		//统计金额
	private Date createTime;//创建时间
	private String totalTime;	//统计的时间（每一天）
	private String status;	//结算状态（0：未结算，1：待结算，2：已结算）
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ShopTotal(Long id, String shopId, Long total, Date createTime, String totalTime, String status) {
		super();
		this.id = id;
		this.shopId = shopId;
		this.total = total;
		this.createTime = createTime;
		this.totalTime = totalTime;
		this.status = status;
	}
	public ShopTotal(String shopId, Long total, Date createTime, String totalTime, String status) {
		super();
		this.shopId = shopId;
		this.total = total;
		this.createTime = createTime;
		this.totalTime = totalTime;
		this.status = status;
	}
	public ShopTotal() {
		super();
	}
}
