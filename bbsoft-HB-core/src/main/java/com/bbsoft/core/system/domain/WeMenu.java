package com.bbsoft.core.system.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信菜单
 * @author Chris.Zhang
 * @date 2017-7-24 17:31:21
 *
 */
public class WeMenu implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;		//菜单ID
	private String name;	//菜单名称
	private String url;		//菜单路径
	private Long parentId;	//菜单父级ID
	private String isAuth;	//是否需要授权，0：否，1：是
	private String type;	//类型
	private Date createTime;//创建时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getIsAuth() {
		return isAuth;
	}
	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public WeMenu(Long id, String name, String url, Long parentId, String isAuth) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.parentId = parentId;
		this.isAuth = isAuth;
	}
	public WeMenu() {
		super();
	}
}
