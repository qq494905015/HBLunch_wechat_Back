/**  
 * @Title: SysMenu.java
 * @Package: com.happygou.bysystem.domain
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-1-5
 */
package com.bbsoft.core.sysuser.domain;

import java.io.Serializable;

/**
 * ClassName: SysMenu 
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-1-5
 */
public class SysMenu implements Serializable{

	/**
	 * @Fields: serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;//主键
	private String name;//权限名称
	private String parentId;//父级id
	private String groupName;//模块名称
	private String sn;//权限名的简称
	private String url;//链接地址
	private String type;//模块类型（F: 功能 O：操作）
	private Integer sortNum;//排序
	private String iconClass;//图标class
	private String createBy;//创建人
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public String getIconClass() {
		return iconClass;
	}
	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
}
