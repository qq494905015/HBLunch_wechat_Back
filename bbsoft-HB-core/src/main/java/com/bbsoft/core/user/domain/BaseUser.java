package com.bbsoft.core.user.domain;

import java.io.Serializable;
import java.util.Date;

public class BaseUser implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;//主键
	private String shopId;//店铺ID
	private String userName;//用户名
	private String nickName;//昵称
	private String ownerUin;//用户账号，QQ
	private String wechatNo;//微信账号
	private String passWord;//密码
	private String realName;//姓名
	private String idCard;//身份证号
	private String email;//邮箱
	private String phone;//联系电话
	private String headImg;//头像
	private String info;//自我介绍
	private String sex;//性别
	private Integer height;//身高
	private Double weight;//体重
	private String bwh;//三围
	private Integer age;//年龄
	private String birthday;//出生日期
	private String provinceId;//省级id
	private String cityId;//市级id
	private String areaId;//区级id
	private String addressDetail;//详细地址
	private String address;//完整地址
	private String profession;//职业 
	private String latitude;//经度
	private String longitude;//维度
	private String token;//唯一标识
	private String recommendCode;//推荐码
	private String recommendId;//推荐人id
	private String status;//用户状态(0:启用 1：禁用)
	private String isMessage;//是否接收推送消息(0:是 1：否)
	private Date lastloginTime;//最后登录时间
	private Date createTime;//注册时间
	private Integer loginNum;//登录次数
	private Date preLoginTime;//上次登录时间
	private String preLoginIp;//上次登录IP
	private String currLoginIp;//当前登录IP
	private String registerFrom;//注册来源
	private String isDelete;//是否删除(0 : 是 1：否)
	
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getWechatNo() {
		return wechatNo;
	}
	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRecommendId() {
		return recommendId;
	}
	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}
	public String getRegisterFrom() {
		return registerFrom;
	}
	public void setRegisterFrom(String registerFrom) {
		this.registerFrom = registerFrom;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getBwh() {
		return bwh;
	}
	public void setBwh(String bwh) {
		this.bwh = bwh;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRecommendCode() {
		return recommendCode;
	}
	public void setRecommendCode(String recommendCode) {
		this.recommendCode = recommendCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsMessage() {
		return isMessage;
	}
	public void setIsMessage(String isMessage) {
		this.isMessage = isMessage;
	}
	public Date getLastloginTime() {
		return lastloginTime;
	}
	public void setLastloginTime(Date lastloginTime) {
		this.lastloginTime = lastloginTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getLoginNum() {
		return loginNum;
	}
	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}
	public Date getPreLoginTime() {
		return preLoginTime;
	}
	public void setPreLoginTime(Date preLoginTime) {
		this.preLoginTime = preLoginTime;
	}
	public String getPreLoginIp() {
		return preLoginIp;
	}
	public void setPreLoginIp(String preLoginIp) {
		this.preLoginIp = preLoginIp;
	}
	public String getCurrLoginIp() {
		return currLoginIp;
	}
	public void setCurrLoginIp(String currLoginIp) {
		this.currLoginIp = currLoginIp;
	}
	public String getOwnerUin() {
		return ownerUin;
	}
	public void setOwnerUin(String ownerUin) {
		this.ownerUin = ownerUin;
	}
}
