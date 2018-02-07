package com.bbsoft.common.constant;

import java.io.Serializable;

/**
 * 
 * ClassName: Json 
 * @Description: TODO
 * @author: VULCAN
 * @date: 2016-12-29
 */
public class Json implements Serializable {
	
	private static final long serialVersionUID = 8153877542999778919L;
	
	private String errorMsg;
	private String errorCode;
	private Boolean success;
	private Object results;
	
	public Object getResults() {
		return results;
	}
	
	public void setResults(Object results) {
		this.results = results;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
 }
