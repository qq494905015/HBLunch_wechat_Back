package com.bbsoft.core.business.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONObject;

import org.apache.log4j.chainsaw.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.alipaypc.config.AlipayConfigPc;
import com.bbsoft.common.alipaypc.util.AlipaySubmit;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.DateUtil;
import com.bbsoft.common.util.PriceUtil;
import com.bbsoft.common.util.SnowflakeIdUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.common.wechat.WeChatConfig;
import com.bbsoft.common.wechat.messge.TemplateMessage;
import com.bbsoft.common.wechat.service.MessageService;
import com.bbsoft.common.wechat.service.WechatPayService;
import com.bbsoft.core.business.domain.Card;
import com.bbsoft.core.business.domain.Order;
import com.bbsoft.core.business.mapper.OrderMapper;
import com.bbsoft.core.business.service.CardService;
import com.bbsoft.core.business.service.OrderService;
import com.bbsoft.core.user.domain.BaseUser;
import com.bbsoft.core.user.domain.ConsumeRecord;
import com.bbsoft.core.user.domain.RechargeRecord;
import com.bbsoft.core.user.domain.WechatUser;
import com.bbsoft.core.user.service.BaseUserService;
import com.bbsoft.core.user.service.ConsumeRecordService;
import com.bbsoft.core.user.service.RechargeRecordService;
import com.bbsoft.core.user.service.WechatUserService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private CardService cardService;
	@Autowired
	private RechargeRecordService rechargeRecordService;
	@Autowired
	private ConsumeRecordService consumeRecordService;
	@Autowired
	private WechatUserService wechatUserService;
	@Autowired
	private BaseUserService baseUserService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	public synchronized int addOrder(Order order) {
		if(order == null 
			|| StringUtil.isEmpty(order.getOpenId())
			|| StringUtil.isEmpty(order.getOrderType())){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if(StringUtil.isEmpty(order.getId())){
			order.setId(SnowflakeIdUtil.getGeneratedKey());
		}
		//会员卡订单
		if("1".equals(order.getOrderType())){
			Card dbCard = cardService.getCardByOpenId(order.getOpenId(), "3"); 
			Card dbCard1 = cardService.getCardByOpenId(order.getOpenId(), "4"); 
			if(dbCard != null || dbCard1 != null){
				throw new ServiceException(MsgeData.CARD_1000030018.getCode());
			}
			if(StringUtil.isEmpty(order.getRealName())
				|| StringUtil.isEmpty(order.getPhone())
				|| StringUtil.isEmpty(order.getAddress())){
				throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
			}
			Card card = cardService.getCardById(order.getCardId(), true);
			if(card.getPrice() <= 0){
				throw new ServiceException(MsgeData.PUBLIC_1000010018.getCode());
			}
			if(!"0".equals(card.getStatus())){
				throw new ServiceException(MsgeData.CARD_1000030024.getCode());
			}
			order.setPrice(card.getPrice());
			//修改会员卡的信息，未完成支付之前占用此卡,此时状态为【未支付禁用】
//			card.setUserId(order.getUserId());
//			card.setOpenId(order.getOpenId());
//			cardService.updateCard(card);
//			}
		}
		//充值订单
		if("2".equals(order.getOrderType())){
			Card card = null;
			if(StringUtil.isEmpty(order.getCardId())){
				card = cardService.getCardByUser(order.getUserId());
			}
			//未购买
			if(card == null){
				throw new ServiceException(MsgeData.CARD_1000030002.getCode());
			}
			//需要先绑定
			if(!"4".equals(card.getStatus())){
				throw new ServiceException(MsgeData.CARD_1000030010.getCode());
			}
			order.setCardId(card.getId());
			RechargeRecord rechargeRecord = 
					new RechargeRecord(order.getUserId(), "微信", card.getCardNo(), order.getPrice(), "0", new Date(), "0");
			rechargeRecord.setOrderId(order.getId());
			//新增充值记录
			rechargeRecordService.addRechargeRecord(rechargeRecord);
		}
		order.setCreateTime(new Date());
		order.setIsDelete("0");
		if(StringUtil.isEmpty(order.getStatus())){
			order.setStatus("1");
		}
		return orderMapper.insertOrder(order);
	}

	public int updateOrder(Order order) {
		if(order == null
			|| StringUtil.isEmpty(order.getId())){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		this.getOrderById(order.getId(), true);
		return orderMapper.updateOrder(order);
	}
	
	public int updatePaySuccOrder(Order order){
		if(order == null
			|| StringUtil.isEmpty(order.getId())
			|| StringUtil.isEmpty(order.getPayNo())
			|| order.getPayPrice() == null
			|| order.getPayPrice() == 0
			|| order.getPayTime() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		logger.debug("updatePaySuccOrder==============================支付成功回调service start");
		Order dbOrder = this.getOrderById(order.getId(), true);
		Card card = cardService.getCardById(dbOrder.getCardId(), true);
		Card editCard = new Card();
		editCard.setId(card.getId());
		if(StringUtil.isEmpty(order.getUserId())){
			order.setUserId("0");
		}
		//消费记录
		ConsumeRecord consumeRecord = new ConsumeRecord(order.getUserId(), "0", card.getId(), order.getPayPrice()); 
		consumeRecord.setFromId(order.getId());
		
		//会员卡订单
		if("1".equals(dbOrder.getOrderType())){
			logger.debug("updatePaySuccOrder 会员卡订单==============================start");
			editCard.setStatus("3");
			editCard.setSurname(dbOrder.getSurname());
			editCard.setName(order.getName());
			editCard.setUpdateTime(new Date());
			editCard.setOpenId(order.getOpenId());
			card.setOpenId(order.getOpenId());
			consumeRecord.setFromType("1");
			sendBuyCardSuccMsg(order, card);
			logger.debug("updatePaySuccOrder 会员卡订单==============================end");
		}
		//充值订单
		if("2".equals(dbOrder.getOrderType())){
			logger.debug("updatePaySuccOrder 充值订单==============================start");
			//更新卡余额，更新充值记录
//			editCard.setBalance(card.getBalance() + order.getPayPrice());
			RechargeRecord rechargeRecord = rechargeRecordService.getRecordByOrder(dbOrder.getId());
			rechargeRecord.setUpdateTime(new Date());
			rechargeRecord.setStatus("1");
			consumeRecord.setFromType("2");
			rechargeRecordService.updateRechargeRecord(rechargeRecord);
			sendRechargeSuccMsg(order, card);
			logger.debug("updatePaySuccOrder 充值订单==============================end");
		}
		int result = orderMapper.updateOrder(order);
		result = cardService.updateCard(editCard);
		result = consumeRecordService.addConsumeRecord(consumeRecord);
		logger.debug("updatePaySuccOrder==============================支付成功回调service end");
		return result;
	}

	public JSONObject addToPayOrder(Order order, HttpServletRequest request) {
		if(StringUtil.isEmpty(order.getId())){
			this.addOrder(order);
		}
		JSONObject returnJson = new JSONObject();
		String desc = "1".equals(order.getOrderType()) ? "万味黑卡" : "万味黑卡充值";
		String result = "";
		//支付宝
		if("1".equals(order.getPayType())){
			Map<String, String> params = AlipayConfigPc.getParaTemp();
			params.put("body", desc);
			params.put("subject", desc);
			params.put("out_trade_no", order.getId());
			params.put("total_fee", PriceUtil.formatPriceToString(order.getPrice() * 0.01));
			//状态接收返回请求
			params.put("notify_url", AlipayConfigPc.notify_url);
			params.put("return_url", AlipayConfigPc.return_url);
			JSONObject resultJson = new JSONObject();
			String htmlForm = AlipaySubmit.buildRequest(params,"get","确认");
			resultJson.put("htmlForm", htmlForm);
			result = resultJson.toString();
		}//微信
		else if("2".equals(order.getPayType())){
			Map<String, String> map = WechatPayService.weixinPrePay(order.getId(), order.getPrice(), desc, "JSAPI", WeChatConfig.NOTIFY_URL, order.getOpenId(), request);
			result = JSONObject.fromObject(map).toString();
		}else{
			throw new ServiceException(MsgeData.PUBLIC_1000010016.getCode());
		}
		returnJson.put("payInfo", result);
		return returnJson;
	}

	public Order getOrderById(String id) {
		return this.getOrderById(id, false);
	}

	public Order getOrderById(String id, boolean isException) {
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Order order = orderMapper.selectOrderById(id);
		if(order == null && isException){
			throw new ServiceException(MsgeData.ORDER_1000030004.getCode());
		}
		return order;
	}

	public int getOrderCount(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return orderMapper.selectOrderCount(map);
	}

	public List<Order> getOrderByPage(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		map.put("startItem", map.get("startItem") == null ? 0 : map.get("startItem"));
		map.put("pageSize", map.get("pageSize") == null ? 0 : map.get("pageSize"));
		return orderMapper.selectOrderByPage(map);
	}
	
 
	public void exprotOrder(Map<String, Object> map,HttpServletResponse response) {
		// 准备excel的标题
		String[] titles = { "订单号", "订单类型", "订单价格", "支付价格","订单状态", "支付时间", "下单时间", "支付订单号", "收货人姓名", "联系电话", "收货地址", "姓氏", "名", "推荐人手机号", "卡号" };
		OutputStream outPut = null;
		try {
			String fileName = "订单列表.xls";
			fileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/msexcel;charset=UTF-8");
			outPut = response.getOutputStream();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new ServiceException(MsgeData.SYSTEM_10112.getCode());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException(MsgeData.SYSTEM_10112.getCode());
		}
		// 创建excel工作簿
		try {
			WritableWorkbook workBook;
			// 创建jxl文件并指定第一个sheet
			workBook = Workbook.createWorkbook(outPut);
			WritableSheet sheef = workBook.createSheet("订单列表", 0);
			Label label;
			for (int i = 0; i < titles.length; i++) {
				label = new Label(i, 0, titles[i]);
				try {
					sheef.addCell(label);
				} catch (RowsExceededException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}
			// 从数据库取数据
			List<Order> mapList = orderMapper.selectExportOrder(map);
			for (int i = 0; i < mapList.size(); i++) {
				try {
					if("2".equals(mapList.get(i).getOrderType())){
						BaseUser baseUser = baseUserService.getUserById(mapList.get(i).getUserId());
						if(baseUser != null){
							mapList.get(i).setRealName(StringUtil.isEmpty(baseUser.getRealName()) ? baseUser.getUserName() : baseUser.getRealName());
						}
					}
					// 订单号
					label = new Label(0, i + 1, mapList.get(i).getId());
					sheef.addCell(label);
					// 订单类型
					label = new Label(1, i + 1, getOrderTypeName(mapList.get(i).getOrderType()));
					sheef.addCell(label);
					// 订单价格
					Long price = mapList.get(i).getPrice();
					label = new Label(2, i + 1, price==null?"0.00":PriceUtil.formatPriceToString(price*0.01, "0.00"));
					sheef.addCell(label);
					// 支付价格
					Long payPrice = mapList.get(i).getPayPrice();
					label = new Label(3, i + 1,payPrice ==null?"0.00":PriceUtil.formatPriceToString(payPrice*0.01,"0.00"));
					sheef.addCell(label);
					// 订单状态 
					label = new Label(4, i + 1, getOrderStatusName(mapList.get(i).getStatus()));
					sheef.addCell(label);
					// 支付时间
					label = new Label(5, i + 1, DateUtil.formatDateToString(mapList.get(i).getPayTime(),"yyyy-MM-dd HH:mm:ss"));
					sheef.addCell(label);
					// 下单时间
					label = new Label(6, i + 1, DateUtil.formatDateToString(mapList.get(i).getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
					sheef.addCell(label);
					// 支付订单号
					label = new Label(7, i + 1, mapList.get(i).getPayNo());
					sheef.addCell(label);
					// 收货人姓名
					label = new Label(8, i + 1,mapList.get(i).getRealName());
					sheef.addCell(label);
					// 联系电话
					label = new Label(9, i + 1, mapList.get(i).getPhone());
					sheef.addCell(label);
					// 收货地址
					label = new Label(10, i + 1, mapList.get(i).getAddress());
					sheef.addCell(label);
					// 姓氏
					label = new Label(11, i + 1, mapList.get(i).getSurname());
					sheef.addCell(label);
					// 名
					label = new Label(12, i + 1, mapList.get(i).getName());
					sheef.addCell(label);
					// 推荐人手机号
					label = new Label(13, i + 1, mapList.get(i).getRecommendPhone());
					sheef.addCell(label);
					//卡号
					if("1".equals(mapList.get(i).getOrderType())){
						Card card = cardService.getCardById(mapList.get(i).getCardId(), true);
						label = new Label(14, i + 1, card.getCardNo());
						sheef.addCell(label);
					}else{
						label = new Label(14, i + 1, "");
						sheef.addCell(label);
					}
					
				} catch (RowsExceededException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}
			// 写入数据
			workBook.write();
			// 关闭文件
			workBook.close();
			outPut.close();
		} catch (WriteException e) {
			e.printStackTrace();
			System.out.println("excel工作簿未关闭");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("文件路径不存在");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("io读取错误");
		}
	}
	
	public String getOrderStatusName(String status){
		String statusName = "";
		if(status==null){
			statusName = "未知状态";
		}else{
			if("1".equals(status)){
				statusName = "待支付";
			}else if("2".equals(status)){
				statusName = "支付中";
			}else if("3".equals(status)){
				statusName = "支付成功";
			}else if("4".equals(status)){
				statusName = "订单完成";
			}else if("5".equals(status)){
				statusName = "订单失效";
			}else{
				statusName = "未知状态";
			}
		}
		return statusName;
	}
	
	public String getOrderTypeName(String orderType){
		String orderTypeName = "";
		if(orderType==null){
			orderTypeName = "其它";
		}else{
			if("1".equals(orderType)){
				orderTypeName = "会员卡";
			}else if("2".equals(orderType)){
				orderTypeName = "充值";
			}else{
				orderTypeName = "其它";
			}
		}
		return orderTypeName;
	}
	
	public Order getOrderByOpenId(String openId, String orderType, String status) {
		return orderMapper.selectOrderByOpenId(openId, orderType, status);
	}

	public int updateFailOrders(String orderId, String status, String updateStatus, String openId) {
		if(StringUtil.isEmpty(orderId) || StringUtil.isEmpty(status) || StringUtil.isEmpty(updateStatus) || StringUtil.isEmpty(openId)){
			return 0;
		}
		return orderMapper.updateFailOrders(orderId, status, updateStatus, openId);
	}
	
	/**
	 * 发送购卡成功
	 */
	private void sendBuyCardSuccMsg(Order order, Card card){
		WechatUser wechatUser = wechatUserService.getByOpenId(card.getOpenId());
		Map<String, Object> data = new HashMap<String, Object>();
		
		Map<String, Object> first = new HashMap<String, Object>();
		first.put("color", "#173177");
		first.put("value", "你好，你有一张会员卡可以绑定为微信会员卡。");
		data.put("first", first);
		
		Map<String, Object> keynote1 = new HashMap<String, Object>();
		keynote1.put("color", "#173177");
		keynote1.put("value", card.getCardNo());
		data.put("keynote1", keynote1);
		
		Map<String, Object> keynote2 = new HashMap<String, Object>();
		keynote2.put("color", "#173177");
		keynote2.put("value", DateUtil.formatDateToString(order.getCreateTime(), DateUtil.CN_DATE_PATTERN));
		data.put("keynote2", keynote2);
		
		Map<String, Object> remark = new HashMap<String, Object>();
		remark.put("color", "#173177");
		remark.put("value", "点击详情，快速绑定微信会员卡");
		data.put("remark", remark);
		String domain = "http://wx.wan-we.com/HeBenWechat#/";
		String redirectUri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChatConfig.APP_ID 
			+ "&redirect_uri=CUS_URL&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
		TemplateMessage message = new TemplateMessage(wechatUser.getOpenId(), "xj7eIuXw8fxZD0C6GGPZf6hoXITzLKNxFdlggDDSN2U", redirectUri.replace("CUS_URL", URLEncoder.encode(domain + "bind")), data);
		MessageService.sendTemplateMsg(message);
	}
	
	private void sendRechargeSuccMsg(Order order, Card card){
		WechatUser wechatUser = wechatUserService.getByOpenId(card.getOpenId());
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> first = new HashMap<String, Object>();
		first.put("color", "#173177");
		first.put("value", "您好，您已成功进行会员卡充值。");
		data.put("first", first);
		
		Map<String, Object> accountType = new HashMap<String, Object>();
		accountType.put("color", "#173177");
		accountType.put("value", "会员卡号");
		data.put("accountType", accountType);
		
		Map<String, Object> account = new HashMap<String, Object>();
		account.put("color", "#173177");
		account.put("value", card.getCardNo());
		data.put("account", account);
		
		Map<String, Object> amount = new HashMap<String, Object>();
		amount.put("color", "#173177");
		amount.put("value", PriceUtil.formatPriceToString(order.getPayPrice() * 0.01));
		data.put("amount", amount);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("color", "#173177");
		result.put("value", "充值成功");
		data.put("result", result);
		
		Map<String, Object> remark = new HashMap<String, Object>();
		remark.put("color", "#173177");
		remark.put("value", "备注：如有疑问，请联系客服，谢谢。");
		data.put("remark", remark);
		String domain = "http://wx.wan-we.com/HeBenWechat#/";
		String redirectUri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChatConfig.APP_ID 
			+ "&redirect_uri=CUS_URL&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
		
		TemplateMessage message = new TemplateMessage(wechatUser.getOpenId(), "WTilDP1Lu6Q9PgNKgSfl4YrgAtnhp3LLbV_67aw-yq4", redirectUri.replace("CUS_URL", URLEncoder.encode(domain + "mine")), data);
		MessageService.sendTemplateMsg(message);
	}
	
	public static void main(String[] args) {
		System.out.println(URLEncoder.encode("http://wx.wan-we.com/HeBenWechat#/bind"));
	}
}
