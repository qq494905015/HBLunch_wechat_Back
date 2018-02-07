package com.bbsoft.core.business.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 积分规则
 * @author Chris.Zhang
 * @date 2017-6-7 15:52:10
 *
 */
public class ScoreRule implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;			//积分规则主键ID
	private Long consumeAmount;	//消费金额
	private String token;		//规则标识，根据此标识获取指定规则
	private Long score;		//积分
	private String description;	//积分规则描述
	private Date createTime;	//创建时间
	private String status;		//状态，（0：启用，1：禁用）
	private String isDelete;	//是否删除，0：否，1：是
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getConsumeAmount() {
		return consumeAmount;
	}
	public void setConsumeAmount(Long consumeAmount) {
		this.consumeAmount = consumeAmount;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public ScoreRule(Long id, Long consumeAmount, String token, Long score, String description, Date createTime,
			String status, String isDelete) {
		super();
		this.id = id;
		this.consumeAmount = consumeAmount;
		this.token = token;
		this.score = score;
		this.description = description;
		this.createTime = createTime;
		this.status = status;
		this.isDelete = isDelete;
	}
	public ScoreRule(Long consumeAmount, String token, Long score, String description, Date createTime,
			String status, String isDelete) {
		super();
		this.consumeAmount = consumeAmount;
		this.token = token;
		this.score = score;
		this.description = description;
		this.createTime = createTime;
		this.status = status;
		this.isDelete = isDelete;
	}
	public ScoreRule() {
		super();
	}
	
	
}
