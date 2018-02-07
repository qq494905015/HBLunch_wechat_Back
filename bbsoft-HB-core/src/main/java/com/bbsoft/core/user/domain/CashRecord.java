package com.bbsoft.core.user.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员提现记录
 * @author Chris.Zhang
 * @date 2017-5-16 17:01:33
 *
 */
public class CashRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;		//主键ID
	private String userId;	//会员ID（外键）
	private String bankNo;	//提现银行卡号
	private String bank;	//提现银行
	private Long money;		//提现金额
	private String status;	//记录状态（0：提现中，提现成功）
	private Date createTime;//提现时间
	private Date updateTime;//更新时间
	private String isDelete;//是否删除（0：否，1：是）
	private String realName;//用户姓名
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
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
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
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public CashRecord() {
		super();
	}
	public CashRecord(String userId, String bankNo, String bank, Long money, String status, Date createTime,
			String isDelete) {
		super();
		this.userId = userId;
		this.bankNo = bankNo;
		this.bank = bank;
		this.money = money;
		this.status = status;
		this.createTime = createTime;
		this.isDelete = isDelete;
	}
	public CashRecord(Long id, String userId, String bankNo, String bank, Long money, String status, Date updateTime,
			String isDelete) {
		super();
		this.id = id;
		this.userId = userId;
		this.bankNo = bankNo;
		this.bank = bank;
		this.money = money;
		this.status = status;
		this.updateTime = updateTime;
		this.isDelete = isDelete;
	}
	
}
