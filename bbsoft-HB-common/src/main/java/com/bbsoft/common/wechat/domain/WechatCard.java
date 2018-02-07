package com.bbsoft.common.wechat.domain;

/**
 * 微信卡券信息
 * @author Chris.Zhang
 * @date 2017-6-20 13:02:54
 *
 */
public class WechatCard {
	
	private String card_id;	//非必填，是由微信创建会员卡后返回的ID
	private String background_pic_url;//非必填，商家自定义会员卡背景图，须先调用上传图片接口将背景图上传至CDN，否则报错，卡面设计请遵循微信会员卡自定义背景设计规范  ,像素大小控制在1000像素*600像素以下
	private Boolean supply_bonus;//非必填，显示积分，填写true或false，如填写true，积分相关字段均为必填。
	private Boolean supply_balance;//非必填，是否支持储值，填写true或false。如填写true，储值相关字段均为必填。
	private Boolean balance_rules;//非必填，储值说明。
	private String prerogative;//必填，会员卡特权说明。
	private Boolean auto_activate;//非必填，设置为true时用户领取会员卡后系统自动将其激活，无需调用激活接口，详情见自动激活。
	private String activate_url;//必填，激活会员卡的url。
	private Integer discount;//非必填，折扣，该会员卡享受的折扣优惠,填10就是九折。
	private CardBaseInfo base_info;//必填，基本的卡券数据，见下表，所有卡券类型通用
	
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getBackground_pic_url() {
		return background_pic_url;
	}
	public void setBackground_pic_url(String background_pic_url) {
		this.background_pic_url = background_pic_url;
	}
	public Boolean getSupply_bonus() {
		return supply_bonus;
	}
	public void setSupply_bonus(Boolean supply_bonus) {
		this.supply_bonus = supply_bonus;
	}
	public Boolean getSupply_balance() {
		return supply_balance;
	}
	public void setSupply_balance(Boolean supply_balance) {
		this.supply_balance = supply_balance;
	}
	public Boolean getBalance_rules() {
		return balance_rules;
	}
	public void setBalance_rules(Boolean balance_rules) {
		this.balance_rules = balance_rules;
	}
	public String getPrerogative() {
		return prerogative;
	}
	public void setPrerogative(String prerogative) {
		this.prerogative = prerogative;
	}
	public Boolean getAuto_activate() {
		return auto_activate;
	}
	public void setAuto_activate(Boolean auto_activate) {
		this.auto_activate = auto_activate;
	}
	public String getActivate_url() {
		return activate_url;
	}
	public void setActivate_url(String activate_url) {
		this.activate_url = activate_url;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public CardBaseInfo getBase_info() {
		return base_info;
	}
	public void setBase_info(CardBaseInfo base_info) {
		this.base_info = base_info;
	}
	public WechatCard(String background_pic_url, Boolean supply_bonus, Boolean supply_balance, Boolean balance_rules,
			String prerogative, Boolean auto_activate, String activate_url, Integer discount, CardBaseInfo base_info) {
		super();
		this.background_pic_url = background_pic_url;
		this.supply_bonus = supply_bonus;
		this.supply_balance = supply_balance;
		this.balance_rules = balance_rules;
		this.prerogative = prerogative;
		this.auto_activate = auto_activate;
		this.activate_url = activate_url;
		this.discount = discount;
		this.base_info = base_info;
	}
	public WechatCard() {
		super();
	}
}
