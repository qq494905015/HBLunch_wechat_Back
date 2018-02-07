package com.bbsoft.admin.controller.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.admin.controller.BaseController;
import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.BeanToMapUtil;
import com.bbsoft.common.util.DateUtil;
import com.bbsoft.common.util.PageUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.common.wechat.WeChatConfig;
import com.bbsoft.common.wechat.messge.TemplateMessage;
import com.bbsoft.common.wechat.service.MessageService;
import com.bbsoft.core.business.domain.ShopTotal;
import com.bbsoft.core.business.service.ShopTotalService;
import com.bbsoft.core.user.domain.BaseUser;
import com.bbsoft.core.user.service.BaseUserService;
import com.bbsoft.core.user.service.ConsumeRecordService;

/**
 * 门店统计接口
 * @author Chris.Zhang
 * @date 2017-6-13 15:28:00
 *
 */
@Controller
public class ShopTotalController extends BaseController{

	@Autowired
	private ShopTotalService shopTotalService;
	@Autowired
	private ConsumeRecordService consumeRecordService; 
	@Autowired
	private BaseUserService baseUserService;
	
	/**
	 * 分页查询店铺日统计记录
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @param totalTime 统计时间
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param status 状态，0:未结算，1：已结算
	 * @param phone 负责人手机号
	 * @param shopName 店铺名称
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("shoptotal200000")
	@ResponseBody
	public Json getTotalByPage(
			Integer pageNum, Integer pageSize, String totalTime,
			String startTime, String endTime, String status, 
			String phone, String shopName
			){
		if(!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)){
			if(DateUtil.formatStringToDate(startTime, DateUtil.GLOBAL_DATE_PATTERN).getTime() > DateUtil.formatStringToDate(endTime, DateUtil.GLOBAL_DATE_PATTERN).getTime()){
				throw new ServiceException(MsgeData.PUBLIC_1000010017.getCode());
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalTime", StringUtil.isEmpty(totalTime) ? null : totalTime);
		map.put("startTime", StringUtil.isEmpty(startTime) ? null : startTime);
		map.put("endTime", StringUtil.isEmpty(endTime) ? null : endTime);
		map.put("status", StringUtil.isEmpty(status) ? null : status);
		map.put("phone", StringUtil.isEmpty(phone) ? null : phone);
		map.put("shopName", StringUtil.isEmpty(shopName) ? null : shopName);
		
		int total = shopTotalService.getTotalCount(map);
		PageUtil<Map> page = new PageUtil<Map>(pageNum, pageSize, total);
		map.put("startItem", page.getStartItem());
		map.put("pageSize", page.getPageSize());
		List<ShopTotal> totals = shopTotalService.getTotalByPage(map);
		page.setItems(BeanToMapUtil.convertList(totals));
		return getSuccessObj(ResultUtil.returnByObj(page));
	}
	
	/**
	 * 修改店铺日统计记录
	 * @param id 店铺日统计记录ID 
	 * @param status 状态，0：未结算，1：已结算
	 * @return
	 */
	@RequestMapping("shoptotal200001")
	@ResponseBody
	public Json updateTotal(Long id, String status){
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		ShopTotal shopTotal = new ShopTotal();
		shopTotal.setId(id);
		shopTotal.setStatus(status);
		ShopTotal dbShopTotal = shopTotalService.getShopTotalById(id);
		if(dbShopTotal == null){
			throw new ServiceException(MsgeData.SYSTEM_10105.getCode());
		}
		if("0".equals(dbShopTotal.getStatus())){
			throw new ServiceException(MsgeData.SHOP_1000040004.getCode());
		}
		if("2".equals(dbShopTotal.getStatus())){
			throw new ServiceException(MsgeData.SHOP_1000040003.getCode());
		}
		shopTotalService.updateShopTotal(shopTotal);
		//发送模板消息给商户用户
		if("2".equals(status)){
			sendSettlementMsg(dbShopTotal);
		}
		return getSuccessObj();
	}

	/**
	 * 分页查询消费记录
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @param shopTotalId 指定日统计记录ID
	 * @param phone 手机号码
	 * @return
	 */
	@RequestMapping("shoptotal200002")
	@ResponseBody
	public Json getConsumeByPage(Integer pageNum, Integer pageSize, Long shopTotalId, String phone){
		if(shopTotalId == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopTotalId", shopTotalId);
		map.put("phone", StringUtil.isEmpty(phone) ? null : phone);
		Map<String, Object> countMap = consumeRecordService.getCountConsumeMap(map);
		int total = 0;
		if(countMap != null){
			total = Integer.parseInt(countMap.get("count").toString());
		}
		PageUtil<Map<String, Object>> page = new PageUtil<Map<String, Object>>(pageNum, pageSize, total);
		map.put("startItem", page.getStartItem());
		map.put("pageSize", page.getPageSize());
		page.setItems(consumeRecordService.getConsumeRecordMap(map));
		return getSuccessObj(ResultUtil.returnByObj(page));
	}
	
	/**
	 * 发送结算对账信息给商户
	 * @param shopTotal 对账记录信息
	 */
	private void sendSettlementMsg(ShopTotal shopTotal){
		List<BaseUser> users = baseUserService.getUserByShop(shopTotal.getShopId());
		if(users != null && users.size() > 0){
			Map<String, Object> data = new HashMap<String, Object>();
			Map<String, Object> first = new HashMap<String, Object>();
			first.put("color", "#173177");
			first.put("value", "您好，您于" + shopTotal.getTotalTime() + "的结算已审核通过。");
			data.put("first", first);
			
			Map<String, Object> keyword1 = new HashMap<String, Object>();
			keyword1.put("color", "#173177");
			keyword1.put("value", "财务对账");
			data.put("keyword1", keyword1);
			
			Map<String, Object> keyword2 = new HashMap<String, Object>();
			keyword2.put("color", "#173177");
			keyword2.put("value", "对账审核通过");
			data.put("keyword2", keyword2);
			
			Map<String, Object> keyword3 = new HashMap<String, Object>();
			keyword3.put("color", "#173177");
			keyword3.put("value", shopTotal.getTotalTime());
			data.put("keyword3", keyword3);
			
			Map<String, Object> remark = new HashMap<String, Object>();
			remark.put("color", "#173177");
			remark.put("value", "备注：如有疑问，请联系客服，谢谢。");
			data.put("remark", remark);
			String domain = "http://wx.wan-we.com/HeBenWechat#/";
			String redirectUri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChatConfig.APP_ID 
				+ "&redirect_uri=CUS_URL&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
			for(BaseUser user : users){
				TemplateMessage message = new TemplateMessage(user.getWechatNo(), "3ly-t8QmJ-zU3avSTI4KS0-s6y6jFDDJVImpzdFA3WM", redirectUri.replace("CUS_URL", domain + "index"), data);
				MessageService.sendTemplateMsg(message);
			}
		}
	}
}
