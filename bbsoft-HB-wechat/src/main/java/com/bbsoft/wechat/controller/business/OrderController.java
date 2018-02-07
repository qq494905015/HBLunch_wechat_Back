package com.bbsoft.wechat.controller.business;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.BeanToMapUtil;
import com.bbsoft.common.util.DateUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.common.wechat.utils.WechatPayUtil;
import com.bbsoft.core.business.domain.Card;
import com.bbsoft.core.business.domain.Order;
import com.bbsoft.core.business.service.CardService;
import com.bbsoft.core.business.service.OrderService;
import com.bbsoft.wechat.controller.BaseController;

import net.sf.json.JSONObject;

/**
 * 订单接口
 * @author Chris.Zhang
 * @date 2017-5-24 10:49:41
 *
 */
@Controller
public class OrderController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private OrderService orderService;
	@Autowired
	private CardService cardService;
	
	/**
	 * 申请办卡
	 * @param request
	 * @param cardId 会员卡ID
	 * @param surname 姓氏，英文大写
	 * @param name 名，英文大写
	 * @param realName 收货人姓名
	 * @param phone 收货人联系电话
	 * @param address 收货地址
	 * @param recommendPhone 推荐人联系电话
	 * @return
	 */
	@RequestMapping("order100000")
	@ResponseBody
	public Json applyCard(
			HttpServletRequest request, 
			String cardId, String surname, String name, 
			String realName, String phone, String address,
			String recommendPhone){
		
		if(StringUtil.isEmpty(cardId)
			|| StringUtil.isEmpty(realName)
			|| StringUtil.isEmpty(phone)
			|| StringUtil.isEmpty(address)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
			
		Order order = new Order();
		order.setCardId(cardId);
		order.setSurname(surname);
		order.setName(name);
		order.setRealName(realName);
		order.setPhone(phone);
		order.setRecommendPhone(recommendPhone);
		order.setAddress(address);
		order.setOrderType("1");
		order.setPayType("2");
		order.setOpenId(getOpenId(request));
		JSONObject resultObj = orderService.addToPayOrder(order, request);
		//更新其他订单失效
		orderService.updateFailOrders(order.getId(), "1", "5", getOpenId(request));
		return getSuccessObj(resultObj);
	}
	
	/**
	 * 获取指定订单卡信息
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("order100001")
	@ResponseBody
	public Json getOrderById(HttpServletRequest request, String id){
		Order order = orderService.getOrderById(id, true);
		return getSuccessObj(ResultUtil.returnByObj(order));
	}
	
	/**
	 * 获取当前微信用户的订单信息
	 * @param request
	 * @return
	 */
	@RequestMapping("order100002")
	@ResponseBody
	public Json getOrderByOpen(HttpServletRequest request){
		String openId = getOpenId(request);
		Card dbCard = cardService.getCardByOpenId(openId, "3"); 
		Card dbCard1 = cardService.getCardByOpenId(openId, "4"); 
		if(dbCard != null || dbCard1 != null){
			throw new ServiceException(MsgeData.CARD_1000030018.getCode());
		}
		Order order = orderService.getOrderByOpenId(openId, "1", "1");
		if(order != null){
			Card card = cardService.getCardById(order.getCardId());
			if(card != null && "0".equals(card.getStatus())){
				return getSuccessObj(BeanToMapUtil.convertBean(order));
			}
		}
		return getSuccessObj();
	}
	
	/**
	 * 继续支付
	 * @param request 
	 * @param orderId 支付订单号
	 * @return
	 */
	@RequestMapping("order100003")
	@ResponseBody
	public Json againPay(HttpServletRequest request, String orderId){
		Order order = orderService.getOrderById(orderId, true);
		JSONObject resultObj = orderService.addToPayOrder(order, request);
		return getSuccessObj(resultObj);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("wxPaySuccess")
	@ResponseBody
	public void wxPaySuccess(HttpServletRequest request, HttpServletResponse response){
		logger.debug("=====================支付回调=====================");
		try {
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			String resultxml = new String(outSteam.toByteArray(), "utf-8");
			Map<String, String> params = WechatPayUtil.doXMLParse(resultxml);
			logger.debug("微信支付回调的请求参数信息=======" + params.toString());
			outSteam.close();
			inStream.close();
			if (!WechatPayUtil.isTenpaySign(params)) {
				logger.debug("=====================付款失败======================");
				response.getWriter().write("FAIL");
				response.getWriter().close();
			}else{
				logger.debug("=====================付款成功======================");
				// ------------------------------
				// 处理业务开始
				// ------------------------------
				// 此处处理订单状态，结合自己的订单数据完成订单状态的更新
				// ------------------------------
//				Long totalFee = Long.parseLong(params.get("total_fee"));
				String tradeNo = params.get("out_trade_no");//商户系统的订单号，与请求一致
				if(tradeNo.indexOf("_") != -1){
					tradeNo = tradeNo.substring(0, tradeNo.indexOf("_"));
				}
//				String resultCode = params.get("result_code");//支付返回的code
				String transactionId = params.get("transaction_id");//微信支付订单号
				String payTime = params.get("time_end");//支付时间
				String payPrice = params.get("cash_fee");
				Order order = orderService.getOrderById(tradeNo);
				if(order != null){
					if("1".equals(order.getStatus())){
						order.setPayNo(transactionId);
						order.setPayTime(DateUtil.formatStringToDate(payTime, "yyyyMMddHHmmss"));
						order.setPayPrice(Long.parseLong(payPrice));
						order.setStatus("3");
						orderService.updatePaySuccOrder(order);
						response.getWriter().write("SUCCESS");
						response.getWriter().close();
					}else{
						response.getWriter().write("FAIL");
						response.getWriter().close();
					}
				}else{
					throw new ServiceException(MsgeData.SYSTEM_10105.getCode());
				}
			}
		} catch (Exception e) {
			throw new ServiceException(MsgeData.SYSTEM_10106.getCode());
		}
	}
	
}
