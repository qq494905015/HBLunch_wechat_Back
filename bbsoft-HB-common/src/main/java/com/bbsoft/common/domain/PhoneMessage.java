package com.bbsoft.common.domain;

/**
 * 手机短信
 * @author Chris
 * @date 2017年2月22日
 */
public class PhoneMessage {

	private String phone;		//手机号
	private Long sendTime;		//发送时间
	private String code;		//发送的验证码
	private String content;		//发送的内容
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getSendTime() {
		return sendTime;
	}
	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public PhoneMessage() {
	}
	
	public PhoneMessage(String phone, Long sendTime, String code, String content) {
		super();
		this.phone = phone;
		this.sendTime = sendTime;
		this.code = code;
		this.content = content;
	}
	
	
}
