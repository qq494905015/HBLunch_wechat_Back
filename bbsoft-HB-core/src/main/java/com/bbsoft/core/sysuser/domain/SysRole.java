/**  
 * @Title: SysRole.java
 * @Package: com.happygou.bysystem.domain
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-1-5
 */
package com.bbsoft.core.sysuser.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: SysRole 
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-1-5
 */
public class SysRole implements Serializable{

	/**
	 * @Fields: serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;//主键
	private String roleName;//角色名称
	private String description;//角色描述
	private String tag;//角色标识
	private Integer sortNum;//排序号
	private String isDelete;//是否删除（N：否，Y:是）
	private String createBy;//创建人id
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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
}
