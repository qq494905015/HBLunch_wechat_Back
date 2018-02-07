package com.bbsoft.common.constant;


import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * ClassName: Menu 
 * @Description: 树结构
 * @author: VULCAN
 * @date: 2017-4-26
 */
public class Menu implements Serializable {

	private static final long serialVersionUID = 5468336530515560024L;

	private String id;
	private String text;
	private String iconCls;
	private String state;
	private Map<String, String> attributes;
	private List<Menu> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

}
