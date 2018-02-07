package com.bbsoft.core.system.domain;

import java.io.Serializable;

/**
 * 官网帮助中心信息
 * @author Chris.Zhang
 * @date 2017-6-6 16:03:59
 *
 */
public class HelpInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;				//主键ID
	private String memberNotes;		//会员须知
	private String memberNotesUrl;	//会员须知链接
	private String commonProblem;	//常见问题
	private String commonProblemUrl;//常见问题链接
	private String aboutUs;			//关于我们
	private String aboutUsUrl;		//关于我们链接
	private String status;			//状态，0：使用中，1：禁用
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMemberNotes() {
		return memberNotes;
	}
	public void setMemberNotes(String memberNotes) {
		this.memberNotes = memberNotes;
	}
	public String getMemberNotesUrl() {
		return memberNotesUrl;
	}
	public void setMemberNotesUrl(String memberNotesUrl) {
		this.memberNotesUrl = memberNotesUrl;
	}
	public String getCommonProblem() {
		return commonProblem;
	}
	public void setCommonProblem(String commonProblem) {
		this.commonProblem = commonProblem;
	}
	public String getCommonProblemUrl() {
		return commonProblemUrl;
	}
	public void setCommonProblemUrl(String commonProblemUrl) {
		this.commonProblemUrl = commonProblemUrl;
	}
	public String getAboutUs() {
		return aboutUs;
	}
	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
	}
	public String getAboutUsUrl() {
		return aboutUsUrl;
	}
	public void setAboutUsUrl(String aboutUsUrl) {
		this.aboutUsUrl = aboutUsUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public HelpInfo(Long id, String memberNotes, String memberNotesUrl, String commonProblem, String commonProblemUrl,
			String aboutUs, String aboutUsUrl) {
		super();
		this.id = id;
		this.memberNotes = memberNotes;
		this.memberNotesUrl = memberNotesUrl;
		this.commonProblem = commonProblem;
		this.commonProblemUrl = commonProblemUrl;
		this.aboutUs = aboutUs;
		this.aboutUsUrl = aboutUsUrl;
	}
	public HelpInfo(String memberNotes, String memberNotesUrl, String commonProblem, String commonProblemUrl,
			String aboutUs, String aboutUsUrl) {
		super();
		this.memberNotes = memberNotes;
		this.memberNotesUrl = memberNotesUrl;
		this.commonProblem = commonProblem;
		this.commonProblemUrl = commonProblemUrl;
		this.aboutUs = aboutUs;
		this.aboutUsUrl = aboutUsUrl;
	}
	public HelpInfo() {
		super();
	}
	
}
