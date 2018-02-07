package com.bbsoft.common.wechat.messge;

import java.util.Map;

/**
 * 模板消息
 * @author Chris.Zhang
 * @date 2017-6-20 11:47:09
 *
 */
public class TemplateMessage {

	private String touser;		//接收者openid
	private String template_id;	//模板ID
	private String url;			//模板跳转链接
	private Map<String, Object> data;	//模板数据
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public TemplateMessage(String touser, String template_id, String url, Map<String, Object> data) {
		super();
		this.touser = touser;
		this.template_id = template_id;
		this.url = url;
		this.data = data;
	}
	public TemplateMessage() {
		super();
	}
}
