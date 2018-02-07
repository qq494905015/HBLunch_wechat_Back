package com.bbsoft.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信接口调用凭证，可以适用于基础接口和网页授权接口（必须申请两个不同的实例）
 * @author Chris.Zhang
 * @date 2017-6-17 13:41:51
 *
 */
public class AccessToken implements Serializable{
	
	private static final long serialVersionUID = 1L;
	// 获取到的凭证  
    private String token;  
    // 凭证有效时间，单位：秒  
    private int expiresIn;
    //凭证生成时间
    private Date createTime;
    
    public String getToken() {  
        return token;  
    }  
  
    public void setToken(String token) {  
        this.token = token;  
    }  
  
    public int getExpiresIn() {  
        return expiresIn;  
    }  
  
    public void setExpiresIn(int expiresIn) {  
        this.expiresIn = expiresIn;  
    }  
    
    public Date getCreateTime() {
		return createTime;
	}
    
    public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
