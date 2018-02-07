package com.bbsoft.core.user.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员反馈
 * @author Chris.Zhang
 * @date 2017-5-16 17:39:50
 *
 */
public class Feedback implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;			//反馈主键ID
	private String userId;		//会员ID（外键）
	private String userName;	//会员名字
	private String phone;		//电话号码
	private String description;	//反馈描述
	private String result;		//反馈结果
	private Date createTime;	//提交反馈时间
	private Date retTime;		//回复时间
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getRetTime() {
		return retTime;
	}
	public void setRetTime(Date retTime) {
		this.retTime = retTime;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public Feedback() {
		super();
	}
	public Feedback(String userId, String userName, String phone, String description, Date createTime,
			String isDelete) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.phone = phone;
		this.description = description;
		this.createTime = createTime;
		this.isDelete = isDelete;
	}
	public Feedback(Long id, String userId, String userName, String phone, String description, String result,
			Date createTime, Date retTime, String isDelete) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.phone = phone;
		this.description = description;
		this.result = result;
		this.createTime = createTime;
		this.retTime = retTime;
		this.isDelete = isDelete;
	}
	
}
