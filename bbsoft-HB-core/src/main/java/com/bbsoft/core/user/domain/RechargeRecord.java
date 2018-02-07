package com.bbsoft.core.user.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员充值记录
 * @author Chris.Zhang
 * @date 2017-5-16 17:10:00
 *
 */
public class RechargeRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;		//主键ID
	private String userId;	//会员ID（外键）
	private String orderId;	//订单ID
	private String type;	//充值方式
	private String cardNo;	//充值卡号
	private Long money;		//充值金额
	private String status;	//记录状态（0：充值中，充值成功）
	private Date createTime;//充值时间
	private Date updateTime;//更新时间
	private String isDelete;//是否删除（0：否，1：是）
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Long getMoney() {
		return money;
	}
	public void setMoney(Long money) {
		this.money = money;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public RechargeRecord() {
		super();
	}
	public RechargeRecord(String userId, String type, String cardNo, Long money, String status, Date createTime,
			String isDelete) {
		super();
		this.userId = userId;
		this.type = type;
		this.cardNo = cardNo;
		this.money = money;
		this.status = status;
		this.createTime = createTime;
		this.isDelete = isDelete;
	}
}
