package com.bbsoft.core.user.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员银行卡信息
 * @author Chris.Zhang
 * @date 2017-5-16 17:14:25
 *
 */
public class UserBank implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;		//主键ID
	private String userId;	//会员ID（外键）
	private String bankNo;	//银行卡号
	private String realName;//真实姓名
	private String bank;	//所属银行
	private String phone;	//手机号
	private Date createTime;//添加时间
	private Date updateTime;//更新时间
	private String isDelete;	//是否删除（0：否，1：是）
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
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public UserBank() {
		super();
	}
	public UserBank(String userId, String bankNo, String realName, String bank, String phone, Date createTime,
			String isDelete) {
		super();
		this.userId = userId;
		this.bankNo = bankNo;
		this.realName = realName;
		this.bank = bank;
		this.phone = phone;
		this.createTime = createTime;
		this.isDelete = isDelete;
	}
	public UserBank(Long id, String userId, String bankNo, String realName, String bank, String phone, Date updateTime,
			String isDelete) {
		super();
		this.id = id;
		this.userId = userId;
		this.bankNo = bankNo;
		this.realName = realName;
		this.bank = bank;
		this.phone = phone;
		this.updateTime = updateTime;
		this.isDelete = isDelete;
	}
	
}
