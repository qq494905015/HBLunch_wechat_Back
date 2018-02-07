/**  
 * @Title: Tuser.java
 * @Package: com.happygou.entity
 * @Description: TODO
 * @author: VULCAN
 * @date: 2016-12-26
 */
package com.bbsoft.core.sysuser.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: SysUser系统用户
 * 
 * @Description: TODO
 * @author: VULCAN
 * @date: 2016-12-26
 */
public class SysUser implements Serializable {

	/**
	 * @Fields: serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private String id;// 主键
	private String userName;// 用户名
	private String nickName;// 昵称
	private String password;// 密码
	private String email;// 邮箱
	private String phone;// 联系电话
	private String lastLoginIp;// 最后登录ip
	private Date lastLoginTime;// 最后登录时间
	private Date createTime;// 创建时间
	private String isDelete;// 是否删除（N：否，Y:是）
	private String parentId;// 父级id
	private String tag;// 用户的分类（0.会员用户1.后台用户）
	private String status;// 状态  是否禁用(1:禁用 0：启用)
	private String createBy;// 创建人
	private String token;//唯一标识
	private String roleId;//角色ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public SysUser() {
		super();
	}

	public SysUser(String userName, String nickName, String password, String email, String phone, String status,
			String roleId) {
		super();
		this.userName = userName;
		this.nickName = nickName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.status = status;
		this.roleId = roleId;
	}
	
	public SysUser(String id, String userName, String nickName, String password, String email, String phone, String status,
			String roleId) {
		super();
		this.id = id;
		this.userName = userName;
		this.nickName = nickName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.status = status;
		this.roleId = roleId;
	}
	
}
