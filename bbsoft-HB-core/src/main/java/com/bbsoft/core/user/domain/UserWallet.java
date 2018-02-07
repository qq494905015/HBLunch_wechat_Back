package com.bbsoft.core.user.domain;

import java.io.Serializable;

/**
 * 用户钱包
 * @author Chris.Zhang
 * @date 2017-5-23 18:30:51
 *
 */
public class UserWallet implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;//钱包ID
	private String userId;//用户ID
	private Long frozenMoney;//冻结资金
	private Long commision;//佣金
	private Long balance;//余额
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
	public Long getFrozenMoney() {
		return frozenMoney;
	}
	public void setFrozenMoney(Long frozenMoney) {
		this.frozenMoney = frozenMoney;
	}
	public Long getCommision() {
		return commision;
	}
	public void setCommision(Long commision) {
		this.commision = commision;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public UserWallet() {
		super();
	}
	public UserWallet(String userId, Long frozenMoney, Long commision, Long balance) {
		super();
		this.userId = userId;
		this.frozenMoney = frozenMoney;
		this.commision = commision;
		this.balance = balance;
	}
	public UserWallet(Long id, String userId, Long frozenMoney, Long commision, Long balance) {
		super();
		this.id = id;
		this.userId = userId;
		this.frozenMoney = frozenMoney;
		this.commision = commision;
		this.balance = balance;
	}
	
}
