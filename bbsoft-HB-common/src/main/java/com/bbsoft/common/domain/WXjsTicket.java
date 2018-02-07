package com.bbsoft.common.domain;

import java.util.Date;

/**
 * 微信JSSDK调用凭证
 * @author Chris.Zhang
 * @date 2017-6-12 18:36:14
 *
 */
public class WXjsTicket {
	// 接口访问凭证  
    private String jsTicket;  
    // 凭证有效期，单位：秒  
    private int jsTicketExpiresIn;  
    // 凭证生成时间
    private Date createTime;
    
    public String getJsTicket() {  
        return jsTicket;  
    }  
    public void setJsTicket(String jsTicket) {  
        this.jsTicket = jsTicket;  
    }  
    public int getJsTicketExpiresIn() {  
        return jsTicketExpiresIn;  
    }  
    public void setJsTicketExpiresIn(int jsTicketExpiresIn) {  
        this.jsTicketExpiresIn = jsTicketExpiresIn;  
    }
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}  
      
}
