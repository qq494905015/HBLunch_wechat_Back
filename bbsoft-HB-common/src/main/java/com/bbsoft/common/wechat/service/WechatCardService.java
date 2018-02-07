package com.bbsoft.common.wechat.service;

import java.util.Map;

import com.bbsoft.common.constant.EnumMethod;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.AccessToken;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.HttpRequestUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.common.wechat.utils.WXURLUtil;
import com.bbsoft.common.wechat.WeChatConfig;
import com.bbsoft.common.wechat.domain.WechatCard;
import com.bbsoft.common.wechat.utils.AccessTokenUtil;

import net.sf.json.JSONObject;

/**
 * 微信卡券接口
 * @author Chris.Zhang
 * @date 2017-6-16 10:31:33
 *
 */
public class WechatCardService {

	/**
	 * 创建会员卡信息
	 * @param card 需要创建的会员卡信息
	 * @return
	 */
	public static JSONObject createCard(WechatCard card){
		JSONObject jsonObject = new JSONObject();
		JSONObject memberCardJson = new JSONObject();
		memberCardJson.put("card_type", "MEMBER_CARD");
		memberCardJson.put("member_card", JSONObject.fromObject(card));
		jsonObject.put("card", memberCardJson);
		//调用微信基础接口唯一标识
		AccessToken token = AccessTokenUtil.getAccessToken();
		//请求接口地址
		String requestUrl = WXURLUtil.CARD_CREATE.replace(WXURLUtil.TOKEN_KEY, token.getToken());
		JSONObject retJson = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.POST.name(), jsonObject.toString());
		return dealResult(retJson);
	}
	
	/**
	 * 更改会员卡信息
	 * @param card 需要修改的会员卡信息
	 * @return
	 */
	public static JSONObject updateCard(WechatCard card){
		JSONObject jsonObject = new JSONObject();
		JSONObject memberCardJson = new JSONObject();
		memberCardJson.put("card_id", card.getCard_id());
		memberCardJson.put("member_card", JSONObject.fromObject(card));
		jsonObject.put("card", memberCardJson);
		//调用微信基础接口唯一标识
		AccessToken token = AccessTokenUtil.getAccessToken();
		//请求接口地址
		String requestUrl = WXURLUtil.CARD_UPDATE.replace(WXURLUtil.TOKEN_KEY, token.getToken());
		JSONObject retJson = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.POST.name(), jsonObject.toString());
		return dealResult(retJson);
	}
	
	/**
	 * 设置支付后投放卡券接口
	 * @param cardJson 会员卡信息
	 * @return
	 */
	public static JSONObject updateUserCard(JSONObject cardJson){
		if(StringUtil.isEmpty(cardJson.getString("code")) || StringUtil.isEmpty(cardJson.getString("card_id"))){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		//调用微信基础接口唯一标识
		AccessToken token = AccessTokenUtil.getAccessToken();
		String requestUrl = WXURLUtil.CARD_USER_UPDATE.replace(WXURLUtil.TOKEN_KEY, token.getToken());
		JSONObject retJson = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.POST.name(), cardJson.toString());
		return dealResult(retJson);
	}
	
	/**
	 * 激活会员卡信息
	 * @param activateInfo 激活信息
	 * {
		    "init_bonus": 100, 初始积分，不填为0。    
		    "init_bonus_record":"旧积分同步", 积分同步说明。
		    "init_balance": 200, 初始余额，不填为0。
		    "membership_number": "AAA00000001", 会员卡编号，由开发者填入，作为序列号显示在用户的卡包里。可与Code码保持等值
		    "code": "12312313", 领取会员卡用户获得的code  
		    "card_id": "xxxx_card_id", 卡券ID,自定义code卡券必填
		    "background_pic_url": "https://mmbiz.qlogo.cn/mmbiz/0?wx_fmt=jpeg", 商家自定义会员卡背景图，须先调用上传图片接口将背景图上传至CDN，否则报错，卡面设计请遵循微信会员卡自定义背景设计规范      
		    "init_custom_field_value1": "xxxxx" 创建时字段custom_field1定义类型的初始值，限制为4个汉字，12字节
		}
	 * @return
	 */
	public static JSONObject activateCard(JSONObject activateInfo){
		if(StringUtil.isEmpty(activateInfo.getString("code"))
			|| StringUtil.isEmpty(activateInfo.getString("card_id"))
			|| StringUtil.isEmpty(activateInfo.getString("membership_number"))){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		//调用微信基础接口唯一标识
		AccessToken token = AccessTokenUtil.getAccessToken();
		String requestUrl = WXURLUtil.CARD_USER_ACTIVATE.replace(WXURLUtil.TOKEN_KEY, token.getToken());
		JSONObject retJson = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.POST.name(), activateInfo.toString());
		return dealResult(retJson);
	}
	
	/**
	 * 支付后推送模板消息给用户，用户点击直接领取会员卡
	 * @param reqJson 请求信息
	 * @return
	 */
	public static JSONObject payGiftCard(Map<String, Object> map){
		JSONObject reqJson = new JSONObject();
		JSONObject ruleInfo = new JSONObject();
		ruleInfo.put("type", "RULE_TYPE_PAY_MEMBER_CARD");
		
		JSONObject baseInfo = new JSONObject();
		baseInfo.put("mchid_list", new String[]{WeChatConfig.MCH_ID});
		baseInfo.put("begin_time", map.get("beginTime"));
		baseInfo.put("end_time", map.get("endTime"));
		ruleInfo.put("base_info", baseInfo);
		
		JSONObject memberRule = new JSONObject();
		memberRule.put("card_id", map.get("cardId"));		//要赠送的会员卡card_id
		memberRule.put("least_cost", map.get("leastCost"));	//单次消费送会员卡的金额下限，以分为单位
		memberRule.put("max_cost", map.get("maxCost"));		//单次消费送会员卡的金额上限，以分为单位
		if(map.get("jumpUrl") != null){
			memberRule.put("jump_url", map.get("jumpUrl"));	//后点击支付即会员消息会跳转至商户网页领卡
		}
		ruleInfo.put("member_rule", memberRule);
		
		reqJson.put("rule_info", ruleInfo);
		//调用微信基础接口唯一标识
		AccessToken token = AccessTokenUtil.getAccessToken();
		String requestUrl = WXURLUtil.PAYGIFT_CARD.replace(WXURLUtil.TOKEN_KEY, token.getToken());
		JSONObject retJson = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.POST.name(), reqJson.toString());
		return dealResult(retJson);
	}
	
	/**
	 * 设置测试白名单
	 * @param openIds 测试的微信用户唯一标识数组列表
	 * @param usernames 测试的微信号数组列表
	 * @return
	 */
	public static JSONObject setTestWhiteList(String[] openIds, String[] usernames){
		JSONObject reqJson = new JSONObject();
		if(openIds != null && openIds.length > 0){
			reqJson.put("openid", openIds);
		}
		if(usernames != null && usernames.length > 0){
			reqJson.put("username", usernames);
		}
		//调用微信基础接口唯一标识
		AccessToken token = AccessTokenUtil.getAccessToken();
		//请求接口地址
		String requestUrl = WXURLUtil.CARD_TEST_WHITE_LIST.replace(WXURLUtil.TOKEN_KEY, token.getToken());
		JSONObject retJson = HttpRequestUtil.httpRequest(requestUrl, EnumMethod.POST.name(), reqJson.toString());
		return dealResult(retJson);
	}
	
	/**
	 * 处理调用接口返回信息
	 * @param resultObj 返回结果信息
	 * @return
	 */
	private static JSONObject dealResult(JSONObject resultObj){
		if(resultObj != null){
			int errCode = resultObj.getInt("errcode");
			if(errCode == 0){
				return resultObj;
			}else{
				throw new ServiceException(MsgeData.SYSTEM_10127.getCode());
			}
			
		}else{
			throw new ServiceException(MsgeData.SYSTEM_10127.getCode());
		}
	}
	
	public static void main(String[] args) {
		String[] openids = new String[]{"oN69auIlrHzt8W4OYjmQwmLuUKLw"};
		System.out.println(WechatCardService.setTestWhiteList(openids, new String[]{}));
	}
}
