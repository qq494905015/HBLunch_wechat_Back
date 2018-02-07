package com.bbsoft.core.business.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 * @author Chris.Zhang
 * @date 2017-5-16 17:36:40
 *
 */
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;		//订单主键ID
	private String userId;	//用户ID（外键）
	private String openId;	//微信用户唯一标识
	private String cardId;	//会员卡ID（外键）
	private String surname;	//姓氏，英文大写
	private String name;	//名，英文大写
	private Long price;		//订单价格
	private Long payPrice;	//支付价格
	private String payType;	//支付方式， 1：支付宝，2：微信
	private String payNo;	//支付订单号
	private Date payTime;	//支付时间
	private Date createTime;//下单时间
	private Date updateTime;//更新时间
	private String status;	//订单状态（1：待支付，2：支付中，3：支付成功，4：订单完成，5：订单失效）
	private String realName;//收货人姓名
	private String phone;	//收货人联系电话
	private String recommendPhone;//推荐人联系电话
	private String address;	//收货地址
	private String isDelete;//是否删除（0：否，1：是）
	private String orderType;//订单类型，1：会员卡，2：充值
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Long getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(Long payPrice) {
		this.payPrice = payPrice;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getRecommendPhone() {
		return recommendPhone;
	}
	public void setRecommendPhone(String recommendPhone) {
		this.recommendPhone = recommendPhone;
	}
}
