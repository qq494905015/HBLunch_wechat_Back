package com.bbsoft.core.user.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户消费记录
 * @author Chris.Zhang
 * @date 2017-5-16 17:04:11
 *
 */
public class ConsumeRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;		//主键ID
	private String userId;	//会员ID（外键）
	private String shopId;	//消费的门店ID（外键）
	private String cardId;	//消费的卡号ID（外键）
	private Long shopTotalId;//门店日统计ID（外键）
	private Long money;		//消费金额
	private String status;	//状态：（0：成功，1：失败）
	private Date createTime;//消费时间
	private String isDelete;//是否删除（0：否，1：是）
	private String fromType;//消费来源（1：会员卡，2：充值，3：消费）
	private String fromId;	//消费来源ID，根据来源类型而定
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
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public Long getShopTotalId() {
		return shopTotalId;
	}
	public void setShopTotalId(Long shopTotalId) {
		this.shopTotalId = shopTotalId;
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
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getFromType() {
		return fromType;
	}
	public void setFromType(String fromType) {
		this.fromType = fromType;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public ConsumeRecord() {
		super();
	}
	public ConsumeRecord(String userId, String shopId, String cardId, Long money) {
		super();
		this.userId = userId;
		this.shopId = shopId;
		this.cardId = cardId;
		this.money = money;
	}
}
