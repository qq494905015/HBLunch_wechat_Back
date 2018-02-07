package com.bbsoft.core.business.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品信息
 * @author Chris.Zhang
 * @date 2017-6-7 15:33:07
 *
 */
public class Product implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;			//商品主键ID
	private String cardId;		//微信会员卡ID
	private String name;		//商品名称
	private Long price;			//售价
	private String mainImg;		//商品主图
	private String minImg;		//商品缩略图
	private String description;	//商品详情
	private String isDelete;	//是否删除，0：否，1：是
	private Date createTime;	//创建时间
	private Date updateTime;	//更新时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
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
	public String getMainImg() {
		return mainImg;
	}
	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}
	public String getMinImg() {
		return minImg;
	}
	public void setMinImg(String minImg) {
		this.minImg = minImg;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Product(Long id, String name, Long price, String mainImg, String minImg, String description, String isDelete,
			Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.mainImg = mainImg;
		this.minImg = minImg;
		this.description = description;
		this.isDelete = isDelete;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	public Product(String name, Long price, String mainImg, String minImg, String description, String isDelete,
			Date createTime, Date updateTime) {
		super();
		this.name = name;
		this.price = price;
		this.mainImg = mainImg;
		this.minImg = minImg;
		this.description = description;
		this.isDelete = isDelete;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	public Product() {
		super();
	}
}
