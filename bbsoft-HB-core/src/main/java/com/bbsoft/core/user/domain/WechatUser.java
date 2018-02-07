package com.bbsoft.core.user.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信用户信息
 * @author Chris.Zhang
 * @date 2017-6-17 17:30:16
 *
 */
public class WechatUser implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;				//主键ID
	private String openId;			//加密的微信账号
	private String userId;			//平台ID
	private String nickname;		//微信昵称
	private String sex;				//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String province;		//用户个人资料填写的省份
	private String city;			//用户个人资料填写的城市
	private String country;			//国家，如中国为CN
	private String headImgUrl;		//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效
	private String privilege;		//用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	private String unionid;			//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	private String lng;				//经度
	private String lat;				//纬度
	private Date attentionTime;		//关注时间
	private Date updateTime;		//更新时间
	private Date createTime;		//创建时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public Date getAttentionTime() {
		return attentionTime;
	}
	public void setAttentionTime(Date attentionTime) {
		this.attentionTime = attentionTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public WechatUser() {
		super();
	}
	public WechatUser(String openId, String userId, String nickname, String sex, String province, String city,
			String country, String headImgUrl, String privilege, String unionid, String lng, String lat,
			Date attentionTime, Date updateTime, Date createTime) {
		super();
		this.openId = openId;
		this.userId = userId;
		this.nickname = nickname;
		this.sex = sex;
		this.province = province;
		this.city = city;
		this.country = country;
		this.headImgUrl = headImgUrl;
		this.privilege = privilege;
		this.unionid = unionid;
		this.lng = lng;
		this.lat = lat;
		this.attentionTime = attentionTime;
		this.updateTime = updateTime;
		this.createTime = createTime;
	}
	public WechatUser(String openId, String nickname, String sex, String province, String city, String country,
			String headImgUrl) {
		super();
		this.openId = openId;
		this.nickname = nickname;
		this.sex = sex;
		this.province = province;
		this.city = city;
		this.country = country;
		this.headImgUrl = headImgUrl;
	}
}
