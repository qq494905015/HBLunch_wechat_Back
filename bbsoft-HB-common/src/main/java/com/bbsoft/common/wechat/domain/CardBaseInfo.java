package com.bbsoft.common.wechat.domain;

import java.util.Map;

/**
 * 卡券基本信息
 * @author Chris.Zhang
 * @date 2017-6-20 13:06:15
 *
 */
public class CardBaseInfo {
	
	private String logo_url; 	//必填，卡券的商户logo，建议像素为300*300
	private String brand_name;	//必填，商户名字,字数上限为12个汉字。
	private String code_type;	//必填，Code展示类型， "CODE_TYPE_TEXT" 文本 "CODE_TYPE_BARCODE" 一维码 "CODE_TYPE_QRCODE" 二维码 "CODE_TYPE_ONLY_QRCODE" 仅显示二维码 "CODE_TYPE_ONLY_BARCODE" 仅显示一维码 "CODE_TYPE_NONE" 不显示任何码型
	private String title;		//必填，卡券名，字数上限为9个汉字 (建议涵盖卡券属性、服务及金额)。
	private String color;		//必填，券颜色。按色彩规范标注填写Color010-Color100 
	private String notice;		//必填，卡券使用提醒，字数上限为16个汉字
	private String service_phone;//非必填，客服电话  
	private String description;	 //必填，卡券使用说明，字数上限为1024个汉字
	private Map<String, Object> date_info;//使用日期,JSON结构有效期的信息
	private Map<String, Object> sku;	//商品信息,JSON结构;quantity,卡券库存的数量，不支持填写0，上限为100000000
	private Integer get_limit;	//非必填，每人可领券的数量限制，建议会员卡每人限领一张 
	private Boolean use_custom_code;//非必填，通常自有优惠码系统的开发者选择自定义Code码
	private Boolean can_give_friend;//非必填，卡券是否可转赠,默认为true  
	private String custom_url_name;	//非必填，自定义跳转外链的入口名字
	private String custom_url;		//非必填，自定义跳转的URL
	private String custom_url_sub_title;//非必填，显示在入口右侧的提示语
	private String promotion_url_name;//非必填，营销场景的自定义入口名称
	private String promotion_url;//非必填，入口跳转外链的地址链接
	private Boolean need_push_on_view;//非必填，填写true为用户点击进入会员卡时推送事件，默认为false
	
	public String getLogo_url() {
		return logo_url;
	}
	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getCode_type() {
		return code_type;
	}
	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getService_phone() {
		return service_phone;
	}
	public void setService_phone(String service_phone) {
		this.service_phone = service_phone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Map<String, Object> getDate_info() {
		return date_info;
	}
	public void setDate_info(Map<String, Object> date_info) {
		this.date_info = date_info;
	}
	public Map<String, Object> getSku() {
		return sku;
	}
	public void setSku(Map<String, Object> sku) {
		this.sku = sku;
	}
	public Integer getGet_limit() {
		return get_limit;
	}
	public void setGet_limit(Integer get_limit) {
		this.get_limit = get_limit;
	}
	public Boolean getUse_custom_code() {
		return use_custom_code;
	}
	public void setUse_custom_code(Boolean use_custom_code) {
		this.use_custom_code = use_custom_code;
	}
	public Boolean getCan_give_friend() {
		return can_give_friend;
	}
	public void setCan_give_friend(Boolean can_give_friend) {
		this.can_give_friend = can_give_friend;
	}
	public String getCustom_url_name() {
		return custom_url_name;
	}
	public void setCustom_url_name(String custom_url_name) {
		this.custom_url_name = custom_url_name;
	}
	public String getCustom_url() {
		return custom_url;
	}
	public void setCustom_url(String custom_url) {
		this.custom_url = custom_url;
	}
	public String getCustom_url_sub_title() {
		return custom_url_sub_title;
	}
	public void setCustom_url_sub_title(String custom_url_sub_title) {
		this.custom_url_sub_title = custom_url_sub_title;
	}
	public String getPromotion_url_name() {
		return promotion_url_name;
	}
	public void setPromotion_url_name(String promotion_url_name) {
		this.promotion_url_name = promotion_url_name;
	}
	public String getPromotion_url() {
		return promotion_url;
	}
	public void setPromotion_url(String promotion_url) {
		this.promotion_url = promotion_url;
	}
	public Boolean getNeed_push_on_view() {
		return need_push_on_view;
	}
	public void setNeed_push_on_view(Boolean need_push_on_view) {
		this.need_push_on_view = need_push_on_view;
	}
	public CardBaseInfo(String logo_url, String brand_name, String code_type, String title, String color, String notice,
			String service_phone, String description, Map<String, Object> date_info, Map<String, Object> sku,
			Integer get_limit, Boolean use_custom_code, Boolean can_give_friend, String custom_url_name,
			String custom_url, String custom_url_sub_title, String promotion_url_name, String promotion_url,
			Boolean need_push_on_view) {
		super();
		this.logo_url = logo_url;
		this.brand_name = brand_name;
		this.code_type = code_type;
		this.title = title;
		this.color = color;
		this.notice = notice;
		this.service_phone = service_phone;
		this.description = description;
		this.date_info = date_info;
		this.sku = sku;
		this.get_limit = get_limit;
		this.use_custom_code = use_custom_code;
		this.can_give_friend = can_give_friend;
		this.custom_url_name = custom_url_name;
		this.custom_url = custom_url;
		this.custom_url_sub_title = custom_url_sub_title;
		this.promotion_url_name = promotion_url_name;
		this.promotion_url = promotion_url;
		this.need_push_on_view = need_push_on_view;
	}
	public CardBaseInfo() {
		super();
	}
}
