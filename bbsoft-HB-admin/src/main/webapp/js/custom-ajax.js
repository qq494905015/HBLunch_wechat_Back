/*
 * AJAX请求封装类
 * version    : "1.0.0",
 * createTime : "2017-6-2 18:44:55"
 * author : "Chris"
 */
/************ztd ajax请求后台访问统一路径**************/

//var HOST_URL = "http://localhost:8000";	//本地 
//var HOST_URL = "http://119.29.139.49:9999/bbsoft-HB-admin" //蓝书试生产
var HOST_URL = "http://admin.wan-we.com" //和本生产


var AJAX_URL = {
	
	/***********系统设置管理请求路径*************/
	"sysset.helpInfo200000" 	: "/helpInfo200000",
	"sysset.helpInfo200001" 	: "/helpInfo200001",
	"sysset.helpInfo200002" 	: "/helpInfo200002",
		
	/***********系统用户管理请求路径*************/
	"sysmgr.sysUser200000"		: "/sysUser200000",
	"sysmgr.sysUser200001"		: "/sysUser200001",
	"sysmgr.sysUser200002"		: "/sysUser200002",
	"sysmgr.sysUser200003"		: "/sysUser200003",
	"sysmgr.sysUser200004"		: "/sysUser200004",
	"sysmgr.sysUser200005"		: "/sysUser200005",
	"sysmgr.sysUser200006"		: "/sysUser200006",
	"sysmgr.sysUser200007"		: "/sysUser200007",
	"sysmgr.sysUser200008"   	: "/sysUser200008",
	
	/***********系统菜单管理请求路径*************/
	"sysmgr.menu200000"   	: "/menu200000",
	"sysmgr.menu200001"   	: "/menu200001",
	"sysmgr.menu200002"   	: "/menu200002",
	"sysmgr.menu200003"   	: "/menu200003",
	
	/***********系统菜单角色请求路径*************/
	"sysmgr.role200000"   	: "/role200000",
	"sysmgr.role200001"   	: "/role200001",
	"sysmgr.role200002"   	: "/role200002",
	"sysmgr.role200003"   	: "/role200003",
	"sysmgr.role200004"   	: "/role200004",
	"sysmgr.role200005"   	: "/role200005",
	
	
	/***********会员用户请求路径*************/
	"usermgr.user200000"   	: "/user200000",
	"usermgr.user200001"   	: "/user200001",
	"usermgr.user200002"   	: "/user200002",
	"usermgr.user200003"   	: "/user200003",
	"usermgr.user200004"   	: "/user200004",
	"usermgr.user200005"   	: "/user200005",
	
	"usermgr.cash200000"   	: "/cash200000",
	"usermgr.cash200001"   	: "/cash200001",
	
	
	/***********积分规则请求路径*************/
	"rulemgr.scoreruel200000"   	: "/scoreruel200000",
	"rulemgr.scoreruel200001"   	: "/scoreruel200001",
	"rulemgr.scoreruel200002"   	: "/scoreruel200002",
	"rulemgr.scoreruel200003"   	: "/scoreruel200003",
	
	/***********商品管理请求路径*************/
	"productmgr.product200000"   	: "/product200000",
	"productmgr.product200001"   	: "/product200001",
	"productmgr.product200002"   	: "/product200002",
	"productmgr.product200003"   	: "/product200003",
	"productmgr.product200004"   	: "/product200004",
	
	/***********订单管理请求路径*************/
	"ordermgr.order200000"   	: "/order200000",
	"ordermgr.order200001"   	: "/order200001",
	"ordermgr.order200002"   	: "/order200002",
	"ordermgr.order200003"   	: "/order200003",
	
	/***********店铺管理请求路径*************/
	"shopmgr.shop200000"   	: "/shop200000",
	"shopmgr.shop200001"   	: "/shop200001",
	"shopmgr.shop200002"   	: "/shop200002",
	"shopmgr.shop200003"   	: "/shop200003",
	"shopmgr.shop200004"   	: "/shop200004",
	"shopmgr.shop200005"   	: "/shop200005",
	
	/***********会员卡管理请求路径*************/
	"cardmgr.card200000"   	: "/card200000",
	"cardmgr.card200001"   	: "/card200001",
	"cardmgr.card200002"   	: "/card200002",
	"cardmgr.card200003"   	: "/card200003",
	"cardmgr.card200004"   	: "/card200004",
	"cardmgr.card200006"   	: "/card200006",
	
	/***********店铺结算统计请求路径*************/
	"settlementmgr.shoptotal200000"   	: "/shoptotal200000",
	"settlementmgr.shoptotal200001"   	: "/shoptotal200001",
	"settlementmgr.shoptotal200002"   	: "/shoptotal200002",
	
	/***********平台管理请求路径*************/
	"platemgr.plateform200000"   	: "/plateform200000",
	
	/***********微菜单管理请求路径*************/
	"wemenumgr.weMenu200000"   	: "/weMenu200000",
	"wemenumgr.weMenu200001"   	: "/weMenu200001",
	"wemenumgr.weMenu200002"   	: "/weMenu200002",
	"wemenumgr.weMenu200003"   	: "/weMenu200003",
	"wemenumgr.weMenu200004"   	: "/weMenu200004",
	
	/***********留言反馈管理请求路径*************/
	"feedbackmgr.feedback200000"   	: "/feedback200000",
	"feedbackmgr.feedback200001"   	: "/feedback200001",
	"feedbackmgr.feedback200002"   	: "/feedback200002",
}

/*
 * 封装异步AJAX
 */
var AJAX = (function() {
	//系统设置
	var sysset = {};
	//系统管理
	var sysmgr = {};
	//会员中心
	var usermgr = {};
	//积分规则 
	rulemgr = {};
	//商品管理
	var productmgr = {};
	//商品管理
	var ordermgr = {};
	//店铺管理
	var shopmgr = {};
	//会员卡管理
	var cardmgr = {};
	//结算管理
	var settlementmgr = {};
	//平台管理
	var platemgr = {};
	//微信管理
	var wemenumgr = {};
	//留言反馈
	var feedbackmgr = {};
	
	/***********系统设置管理*************/
	/**
	 * 新增官网帮助中心信息
	 */
	sysset.helpInfo200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysset.helpInfo200000');
	};
	/**
	 * 修改官网帮助中心信息
	 */
	sysset.helpInfo200001 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysset.helpInfo200001');
	};
	/**
	 * 获取当前配置的官网帮助中心信息
	 */
	sysset.helpInfo200002 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysset.helpInfo200002');
	};
	
	/***********系统用户管理*************/
	/**
	 * 新增用户信息
	 */
	sysmgr.sysUser200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.sysUser200000');
	};
	/**
	 * 系统用户登录
	 */
	sysmgr.sysUser200001 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.sysUser200001');
	};
	/**
	 * 分页查询系统管理员信息
	 */
	sysmgr.sysUser200002 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.sysUser200002');
	};
	/**
	 * 获取指定系统管理员信息
	 */
	sysmgr.sysUser200003 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.sysUser200003');
	};
	/**
	 * 更新用户信息
	 */
	sysmgr.sysUser200004 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.sysUser200004');
	};
	/**
	 * 删除用户信息
	 */
	sysmgr.sysUser200005 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.sysUser200005');
	};
	
	/**
	 * 修改当前登录用户的密码
	 */
	sysmgr.sysUser200006 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.sysUser200006');
	};
	/**
	 * 获取当前登录的用户信息
	 */
	sysmgr.sysUser200007 = function(_param, _callback, _callback2) {
		asyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.sysUser200007');
	};
	
	/**
	 * 系统用户退出登录
	 */
	sysmgr.sysUser200008 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.sysUser200008');
	};
	
	/***********系统菜单管理*************/
	/**
	 * 获取指定角色的权限菜单信息
	 */
	sysmgr.menu200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.menu200000');
	};
	/**
	 * 查询当前登录的用户权限
	 */
	sysmgr.menu200001 = function(_param, _callback, _callback2) {
		asyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.menu200001');
	};
	/**
	 * 查询所有权限目录
	 */
	sysmgr.menu200002 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.menu200002');
	};
	/**
	 * 获取指定角色的权限菜单信息
	 */
	sysmgr.menu200003 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.menu200003');
	};
	
	/***********系统菜单管理*************/
	/**
	 * 新增角色
	 */
	sysmgr.role200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.role200000');
	};
	/**
	 * 修改角色
	 */
	sysmgr.role200001 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.role200001');
	};
	/**
	 * 分页查询角色信息
	 */
	sysmgr.role200002 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.role200002');
	};
	/**
	 * 获取指定角色
	 */
	sysmgr.role200003 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.role200003');
	};
	/**
	 * 查询角色列表
	 */
	sysmgr.role200004 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.role200004');
	};
	/**
	 * 删除角色信息
	 */
	sysmgr.role200005 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'sysmgr.role200005');
	};
	
	/***********会员中心管理*************/
	/**
	 * 分页获取会员列表信息
	 */
	usermgr.user200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'usermgr.user200000');
	};
	/**
	 * 查询指定会员信息
	 */
	usermgr.user200001 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'usermgr.user200001');
	};
	/**
	 * 新增会员信息
	 */
	usermgr.user200002 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'usermgr.user200002');
	};
	/**
	 * 修改会员信息
	 */
	usermgr.user200003 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'usermgr.user200003');
	};
	/**
	 * 编辑用户余额
	 */
	usermgr.user200004 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'usermgr.user200004');
	};
	/**
	 * 编辑用户佣金
	 */
	usermgr.user200005 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'usermgr.user200005');
	};
	/**
	 * 分页获取用户提现记录
	 */
	usermgr.cash200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'usermgr.cash200000');
	};
	/**
	 * 确认提现
	 */
	usermgr.cash200001 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'usermgr.cash200001');
	};
	
	/***********积分规则管理*************/
	/**
	 * 新增积分规则
	 */
	rulemgr.scoreruel200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'rulemgr.scoreruel200000');
	};
	/**
	 * 修改积分规则
	 */
	rulemgr.scoreruel200001 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'rulemgr.scoreruel200001');
	};
	/**
	 * 获取指定积分规则
	 */
	rulemgr.scoreruel200002 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'rulemgr.scoreruel200002');
	};
	/**
	 * 获取积分规则列表
	 */
	rulemgr.scoreruel200003 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'rulemgr.scoreruel200003');
	};
	
	/***********商品管理*************/
	/**
	 * 新增商品信息
	 */
	productmgr.product200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'productmgr.product200000');
	};
	/**
	 * 修改商品信息
	 */
	productmgr.product200001 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'productmgr.product200001');
	};
	/**
	 * 删除商品信息
	 */
	productmgr.product200002 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'productmgr.product200002');
	};
	/**
	 * 分页查询商品信息
	 */
	productmgr.product200003 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'productmgr.product200003');
	};
	/**
	 * 获取商品列表
	 */
	productmgr.product200004 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'productmgr.product200004');
	};
	
	/***********订单管理请求路径*************/
	/**
	 * 分页获取订单列表信息
	 */
	ordermgr.order200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'ordermgr.order200000');
	};
	/**
	 * 获取指定的订单信息
	 */
	ordermgr.order200001 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'ordermgr.order200001');
	};
	/**
	 * 
	 */
	ordermgr.order200002 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'ordermgr.order200002');
	};
	/**
	 * 
	 */
	ordermgr.order200003 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'ordermgr.order200003');
	};
	
	/***********店铺管理请求路径*************/
	/**
	 * 新增店铺
	 */
	shopmgr.shop200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'shopmgr.shop200000');
	};
	/**
	 * 修改店铺
	 */
	shopmgr.shop200001 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'shopmgr.shop200001');
	};
	/**
	 * 删除店铺
	 */
	shopmgr.shop200002 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'shopmgr.shop200002');
	};
	/**
	 * 分页获取店铺列表
	 */
	shopmgr.shop200003 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'shopmgr.shop200003');
	};
	/**
	 * 获取指定ID的店铺信息
	 */
	shopmgr.shop200004 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'shopmgr.shop200004');
	};
	/**
	 * 获取所有门店列表
	 */
	shopmgr.shop200005 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'shopmgr.shop200005');
	};
	
	/***********会员卡管理请求路径*************/
	/**
	 * 新增会员卡信息
	 */
	cardmgr.card200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'cardmgr.card200000');
	};
	/**
	 * 修改会员卡信息
	 */
	cardmgr.card200001 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'cardmgr.card200001');
	};
	/**
	 * 获取指定会员卡信息
	 */
	cardmgr.card200002 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'cardmgr.card200002');
	};
	/**
	 * 分页获取会员卡信息列表
	 */
	cardmgr.card200003 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'cardmgr.card200003');
	};
	/**
	 * 删除会员卡信息
	 */
	cardmgr.card200004 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'cardmgr.card200004');
	};
	
	/**
	 * 后台一键绑定会员卡，用于解决部分会员线下付款的情况
	 */
	cardmgr.card200006 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'cardmgr.card200006');
	};
	
	/***********结算管理请求路径*************/
	/**
	 * 分页查询店铺日统计记录
	 */
	settlementmgr.shoptotal200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'settlementmgr.shoptotal200000');
	};
	/**
	 * 修改店铺日统计记录
	 */
	settlementmgr.shoptotal200001 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'settlementmgr.shoptotal200001');
	};
	/**
	 * 分页查询消费记录
	 */
	settlementmgr.shoptotal200002 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'settlementmgr.shoptotal200002');
	};
	
	/***********平台管理请求路径*************/
	/**
	 * 分页查询平台收入统计
	 */
	platemgr.plateform200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'platemgr.plateform200000');
	};
	
	/***********微信菜单配置请求路径*************/
	/**
	 * 新增微菜单信息
	 */
	wemenumgr.weMenu200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'wemenumgr.weMenu200000');
	};
	/**
	 * 修改微信菜单信息
	 */
	wemenumgr.weMenu200001 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'wemenumgr.weMenu200001');
	};
	/**
	 * 获取所有菜单信息
	 */
	wemenumgr.weMenu200002 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'wemenumgr.weMenu200002');
	};
	/**
	 * 更新菜单到微信
	 */
	wemenumgr.weMenu200003 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'wemenumgr.weMenu200003');
	};
	/**
	 * 批量更新菜单信息
	 */
	wemenumgr.weMenu200004 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'wemenumgr.weMenu200004');
	};
	
	/***********留言反馈配置请求路径*************/
	/**
	 * 更新用户反馈
	 */
	feedbackmgr.feedback200000 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'feedbackmgr.feedback200000');
	};
	/**
	 * 分页获取用户反馈信息列表
	 */
	feedbackmgr.feedback200001 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'feedbackmgr.feedback200001');
	};
	/**
	 * 获取指定的反馈信息
	 */
	feedbackmgr.feedback200002 = function(_param, _callback, _callback2) {
		notAsnyncAjax(_param, _callback, _callback2, 'post', 'feedbackmgr.feedback200002');
	};
	
	return {
		sysset : sysset,
		sysmgr : sysmgr,
		usermgr : usermgr,
		rulemgr : rulemgr,
		productmgr : productmgr,
		ordermgr : ordermgr,
		shopmgr : shopmgr,
		cardmgr : cardmgr,
		settlementmgr : settlementmgr,
		platemgr : platemgr,
		wemenumgr : wemenumgr,
		feedbackmgr : feedbackmgr
	};
}());





/*
 * 平台封装同步AJAX
 */
var AsyncAjax = (function() {
	var usermgr = {
		manage: {}
	}; //用户模块

	/*usermgr.add = function(_param, _callback, _callback2) {
		asyncAjax(_param, _callback, _callback2, 'post', 'usermgr.add');
	};*/
	return {
		usermgr: usermgr
	};
}());

//异步请求
var notAsnyncAjax = function(_param, _callback, _callback2, _type, _url, _dataType) {
	ajax(_param, _callback, _callback2, _type, _url, _dataType);
}

//同步请求
var asyncAjax = function(_param, _callback, _callback2, _type, _url, _dataType) {
	ajax(_param, _callback, _callback2, _type, _url, _dataType, false);
}


//ajax请求
var ajax = function(_param, _callback, _callback2, _type, _url, _dataType, _async) {
	if (typeof _param === 'function') {
		_callback2 = _callback;
		_callback = _param;
		_param = '';
	}
	if (typeof _dataType === 'undefined') {
		_dataType = 'json';
	}
	if (typeof _async === "undefined") {
		_async = true;
	}
	if(_type == "post")layer.load(2,{time: 0.2*1000});
	$.ajax({
		type: _type,
		url: HOST_URL+ AJAX_URL[_url] + ".do?curtime=" + Date.parse(new Date()),
		data: _param,
		Async: _async,
		dataType: _dataType,
		success: function(data, status, xhr) {
			if(_type == "post")
			if(typeof data !== "undefined" && data != null){
				if ((typeof data.errorCode === 'undefined') || data.errorCode == 0 || data.errorCode == "0") {
					_callback(data, status, xhr);
				} else {
					if (typeof _callback2 === 'function') {
						_callback2(data, status, xhr);
					}else{
						if(data.errorCode == '-10102'){
							window.location.href = "login.html";
						}else if(data.errorCode){
							console.info(data);
							layer.alert(data.errorMsg);
						}
					}
				}
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			if(_type == "post")layer.close(layer.index);
			if (XMLHttpRequest.status == 404) {
				layer.alert('访问的路径不存在');
				// location.href = '';
			} else {
				layer.alert('数据加载失败: ' + textStatus);
			}
		}
	});
};