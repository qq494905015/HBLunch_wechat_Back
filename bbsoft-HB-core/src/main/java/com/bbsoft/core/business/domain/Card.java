package com.bbsoft.core.business.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员卡信息
 * @author Chris.Zhang
 * @date 2017-5-16 16:55:20
 *
 */
public class Card implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;			//会员卡ID
	private String userId;		//会员ID（外键）
	private String openId;		//微信用户唯一标识（外键）
	private Long productId;		//商品信息ID（外键）
	private String cardNo;		//卡号
	private String code;		//微信会员卡卡号
	private String cardName;	//卡名称
	private String surname;		//姓氏，英文大写
	private String name;		//名，英文大写
	private Long balance;		//卡余额
	private Long price;			//卡片费用
	private String type;		//卡类型
	private Date bindTime;		//绑定时间
	private Date startTime;		//有效开始时间
	private Date endTime;		//有效结束时间
	private String status;		//状态（0：启用，1：禁用， 2：未支付禁用, 3：已付未激活, 4:已付已激活）
	private String isGoods;		//是否是靓号（0：否，1：是）
	private String isDelete;	//是否删除（0：否，1：是）
	private Date createTime;	//创建时间
	private Date updateTime;	//更新时间
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
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
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
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getBindTime() {
		return bindTime;
	}
	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsGoods() {
		return isGoods;
	}
	public void setIsGoods(String isGoods) {
		this.isGoods = isGoods;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
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
	public Card() {
		super();
	}
	public Card(String id, String userId, Long productId, String cardNo, String cardName, String surname, String name, Long balance,
			Long price, String type, Date bindTime, Date startTime, Date endTime, String status, String isGoods,
			String isDelete, Date updateTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.cardNo = cardNo;
		this.cardName = cardName;
		this.surname = surname;
		this.name = name;
		this.balance = balance;
		this.price = price;
		this.type = type;
		this.bindTime = bindTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.isGoods = isGoods;
		this.isDelete = isDelete;
		this.updateTime = updateTime;
	}
	public Card(String userId, Long productId, String cardNo, String cardName, String surname, String name, Long balance, Long price,
			String type, Date bindTime, Date startTime, Date endTime, String status, String isGoods, Date createTime) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.cardNo = cardNo;
		this.cardName = cardName;
		this.surname = surname;
		this.name = name;
		this.balance = balance;
		this.price = price;
		this.type = type;
		this.bindTime = bindTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.isGoods = isGoods;
		this.createTime = createTime;
	}
	
}
