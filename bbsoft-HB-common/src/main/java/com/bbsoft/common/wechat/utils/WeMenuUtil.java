package com.bbsoft.common.wechat.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbsoft.common.constant.EnumMethod;
import com.bbsoft.common.domain.AccessToken;
import com.bbsoft.common.util.HttpRequestUtil;
import com.bbsoft.common.wechat.WeChatConfig;

import net.sf.json.JSONObject;

/**
 * 微信菜单工具类
 * @author yonking
 * 2016年3月13日00:44:45
 */
public class WeMenuUtil {
	private static Logger logger = LoggerFactory.getLogger(WeMenuUtil.class);
	
	
	/**
	 * 自定义菜单事件推送
	 * 描述：@param eventKey
	 * 描述：@param fromUserName
	 * 描述：@param toUserName
	 * 描述：@return 接受用户点击事件，通过微信推送给用户消息，跳转页面，发送消息等
	 * 
	 */
	public static String getClickResponse(String eventKey, String fromUserName,
			String toUserName) {
		// TODO 判断evetKey事件处理
		if(eventKey.equals("test"))
		{
			
		}
		return null;
	}
	/**
	 * 创建菜单
	 * @param menu
	 * @return
	 */
	public static JSONObject create(Map<String, Object> menu){
		logger.info("开始创建菜单");
		JSONObject jsonObject = null;
		if(menu != null){
			AccessToken accessToken = AccessTokenUtil.getAccessToken();
			String requestUrl = WXURLUtil.MENU_CREATE.replace(WXURLUtil.TOKEN_KEY, accessToken.getToken());
			jsonObject = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.POST.name(), JSONObject.fromObject(menu).toString());
			if(jsonObject != null){
				logger.info("添加菜单成功！");
			}
		}
		return jsonObject;
	}
	
	/**
	 * 删除菜单
	 * @return
	 */
	public static JSONObject remove(){
		logger.info("开始删除菜单");
		JSONObject jsonObject = null;
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		String requestUrl = WXURLUtil.MENU_REMOVE.replace(WXURLUtil.TOKEN_KEY, accessToken.getToken());
		jsonObject = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.GET.name(), null);
		if(jsonObject != null){
			logger.info("删除菜单成功！");
		}
		return jsonObject;
	}
	
	public static void main(String[] args) {
		init();
	}
	
	public static void init() {
		logger.info("初始化菜单开始");
		Map<String, Object> menu = new HashMap<String, Object>();
		List<Map<String, Object>> button = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> item1 = new HashMap<String, Object>();
		Map<String, Object> item2 = new HashMap<String, Object>();
		Map<String, Object> item3 = new HashMap<String, Object>();
		
		item1.put("name", "万味黑卡");
		item2.put("name", "如何使用");
		item3.put("name", "个人中心");
		Map<String, Object> item1_button1 = new HashMap<String,Object>();//申请办卡
		Map<String, Object> item1_button2 = new HashMap<String,Object>();//持卡人绑定
		Map<String, Object> item1_button3 = new HashMap<String,Object>();//附近商家
//		Map<String, Object> item1_button4 = new HashMap<String,Object>();//疯狂沙龙报名表

		Map<String, Object> item2_button1 = new HashMap<String,Object>();//官方网站
		Map<String, Object> item2_button2 = new HashMap<String,Object>();//会员须知
		Map<String, Object> item2_button3 = new HashMap<String,Object>();//常见问题
		Map<String, Object> item2_button4 = new HashMap<String,Object>();//关于我们
		
		Map<String, Object> item3_button1 = new HashMap<String,Object>();//登录/注册
		Map<String, Object> item3_button2 = new HashMap<String,Object>();//个人信息
		Map<String, Object> item3_button3 = new HashMap<String,Object>();//充值
		Map<String, Object> item3_button4 = new HashMap<String,Object>();//售后
		Map<String, Object> item3_button5 = new HashMap<String,Object>();//商户入口
		
		List<Map<String, Object>> item1_btns = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> item2_btns = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> item3_btns = new ArrayList<Map<String,Object>>();
		
//		Map<String, Object> item1_btn1_view = new HashMap<String, Object>();//申请办卡
//		Map<String, Object> item1_btn2_view = new HashMap<String, Object>();//持卡人绑定
//		Map<String, Object> item1_btn3_view = new HashMap<String, Object>();//附近商家
//		
//		Map<String, Object> item2_btn1_view = new HashMap<String, Object>();//官方网站
//		Map<String, Object> item2_btn2_view = new HashMap<String, Object>();//会员须知
//		Map<String, Object> item2_btn3_view = new HashMap<String, Object>();//常见问题
//		Map<String, Object> item2_btn4_view = new HashMap<String, Object>();//关于我们
//		
//		Map<String, Object> item3_btn1_view = new HashMap<String, Object>();//登录/注册
//		Map<String, Object> item3_btn2_view = new HashMap<String, Object>();//个人信息
//		Map<String, Object> item3_btn3_view = new HashMap<String, Object>();//充值
//		Map<String, Object> item3_btn4_view = new HashMap<String, Object>();//售后
//		Map<String, Object> item3_btn5_view = new HashMap<String, Object>();//商户入口
		
		String domain = "http://wx.wan-we.com/HeBenWechat#/";
		String redirectUri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChatConfig.APP_ID 
			+ "&redirect_uri=CUS_URL&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
		item1_button1.put("type", "view");
		item1_button1.put("name", "申请办卡");
		item1_button1.put("url", redirectUri.replace("CUS_URL", domain + "apply"));
		
		item1_button2.put("type", "view");
		item1_button2.put("name", "持卡人绑定");
		item1_button2.put("url", redirectUri.replace("CUS_URL", domain + "bind"));
		
		item1_button3.put("type", "view");
		item1_button3.put("name", "附近商家");
		item1_button3.put("url", domain + "list");
		
//		item1_button4.put("type", "view");
//		item1_button4.put("name", "会员报名");
//		item1_button4.put("url", "https://m.zhundao.net/event/24604");
		
//		item1_button1.put("type", "view");
//		item1_button1.put("name", "申请办卡");
//		item1_button1.put("url", redirectUri.replace("CUS_URL", domain + "apply"));
		
//		item1_button2.put("type", "view");
//		item1_button2.put("name", "在线办理");
//		item1_button2.put("url", "http://shop.wan-we.com/mobile/goods.php?id=1");
//		
//		item1_button3.put("type", "view");
//		item1_button3.put("name", "会员报名");
//		item1_button3.put("url", "https://m.zhundao.net/event/24604");
//		
//		item1_button4.put("type", "view");
//		item1_button4.put("name", "餐厅报名");
//		item1_button4.put("url", "http://cn.mikecrm.com/4ubakuB");
		
		
		
		item2_button1.put("type", "view");
		item2_button1.put("name", "官方网站");
		item2_button1.put("url", "http://i.wan-we.com");
		
		item2_button2.put("type", "view");
		item2_button2.put("name", "会员须知");
		item2_button2.put("url", domain + "notice?type=memberNotes");
		
		item2_button3.put("type", "view");
		item2_button3.put("name", "常见问题");
		item2_button3.put("url", domain + "notice?type=commonProblem");
		
		item2_button4.put("type", "view");
		item2_button4.put("name", "关于我们");
		item2_button4.put("url", domain + "notice?type=aboutUs");
		
		item3_button1.put("type", "view");
		item3_button1.put("name", "登录/注册");
		item3_button1.put("url", redirectUri.replace("CUS_URL", domain + "sign-in"));
		
		item3_button2.put("type", "view");
		item3_button2.put("name", "个人信息");
		item3_button2.put("url", redirectUri.replace("CUS_URL", domain + "mine"));
		
		item3_button3.put("type", "view");
		item3_button3.put("name", "充值");
		item3_button3.put("url", redirectUri.replace("CUS_URL", domain + "recharge"));
		
		item3_button4.put("type", "view");
		item3_button4.put("name", "售后");
		item3_button4.put("url", domain + "after-sale");
		
		item3_button5.put("type", "view");
		item3_button5.put("name", "商户入口");
		item3_button5.put("url", redirectUri.replace("CUS_URL", domain + "index"));
		
		
		item1_btns.add(item1_button1);
		item1_btns.add(item1_button2);
		item1_btns.add(item1_button3);
//		item1_btns.add(item1_button4);
		
		item2_btns.add(item2_button1);
		item2_btns.add(item2_button2);
		item2_btns.add(item2_button3);
		item2_btns.add(item2_button4);
		
		item3_btns.add(item3_button1);
		item3_btns.add(item3_button2);
		item3_btns.add(item3_button3);
		item3_btns.add(item3_button4);
		item3_btns.add(item3_button5);
		
		item1.put("sub_button", item1_btns);
		item2.put("sub_button", item2_btns);
		item3.put("sub_button", item3_btns);
		
//		item1.put("sub_button", item1_button1);
//		item1.put("sub_button", item1_button2);
//		item1.put("sub_button", item1_button3);
//		
//		item2.put("sub_button", item2_button1);
//		item2.put("sub_button", item2_button2);
//		item2.put("sub_button", item2_button3);
//		item2.put("sub_button", item2_button4);
//		
//		item3.put("sub_button", item3_button1);
//		item3.put("sub_button", item3_button2);
//		item3.put("sub_button", item3_button3);
//		item3.put("sub_button", item3_button4);
//		item3.put("sub_button", item3_button5);

		
		button.add(item1);
		button.add(item2);
		button.add(item3);
		menu.put("button", button);
		logger.debug("参数：{}", menu);
		remove();
		create(menu);
	}
}
