package com.bbsoft.admin.controller.business;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.bbsoft.core.business.domain.Card;
import com.bbsoft.core.business.domain.Order;
import com.bbsoft.core.business.service.CardService;
import com.bbsoft.core.business.service.OrderService;
import com.bbsoft.core.user.domain.BaseUser;
import com.bbsoft.core.user.service.BaseUserService;

/**
 * 订单接口
 * @author Chris.Zhang
 * @date 2017-5-23 15:56:27
 *
 */
@Controller
public class OrderController extends BaseController{
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private BaseUserService baseUserService;
	@Autowired
	private CardService cardService;

	/**
	 * 分页获取订单列表信息
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @param search 搜索关键字
	 * @param orderType 订单类型，1：会员卡，2：充值
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param status 订单状态（1：待支付，2：支付中，3：支付成功，4：订单完成，5：订单失效）
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("order200000")
	@ResponseBody
	public Json getOrderByPage(
			Integer pageNum, Integer pageSize, String search, 
			String orderType, String startTime, String endTime,
			String status){
		
		if(!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)){
			Date start = DateUtil.formatStringToDate(startTime);
			Date end = DateUtil.formatStringToDate(endTime);
			if(start.getTime() > end.getTime()){
				throw new ServiceException(MsgeData.PUBLIC_1000010017.getCode());
			}
		}
		//查询条件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", StringUtil.isEmpty(search) ? null : search);
		map.put("orderType", StringUtil.isEmpty(orderType) ? null : orderType);
		map.put("startTime", StringUtil.isEmpty(startTime) ? null : startTime);
		map.put("endTime", StringUtil.isEmpty(endTime) ? null : endTime);
		map.put("status", StringUtil.isEmpty(status) ? null : status);
		
		//查询总订单数
		int total = orderService.getOrderCount(map);
		PageUtil<Map> page = new PageUtil<Map>(pageNum, pageSize, total);
		map.put("startItem", page.getStartItem());
		map.put("pageSize", page.getPageSize());
		
		//分页额度订单记录
		List<Order> orders = orderService.getOrderByPage(map);
		if(orders != null && orders.size() > 0){
			for(Order order : orders){
				if("2".equals(order.getOrderType())){
					BaseUser baseUser = baseUserService.getUserById(order.getUserId());
					if(baseUser != null){
						order.setRealName(StringUtil.isEmpty(baseUser.getRealName()) ? baseUser.getUserName() : baseUser.getRealName());
					}
				}
				if("1".equals(order.getOrderType())){
					Card card = cardService.getCardById(order.getCardId(), true);
					order.setCardId(card.getCardNo());
				}else{
					order.setCardId("");
				}
			}
		}
		page.setItems(BeanToMapUtil.convertList(orders));
		
		return getSuccessObj(ResultUtil.returnByObj(page));
	}
	
	/**
	 * 获取指定的订单信息
	 * @param id 订单ID，订单号
	 * @return
	 */
	@RequestMapping("order200001")
	@ResponseBody
	public Json getOrderById(String id){
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Order order = orderService.getOrderById(id);
		return getSuccessObj(ResultUtil.returnByObj(order));
	}
	
	
	@RequestMapping("order200002")
	@ResponseBody
	public Json updateOrder(
			String id, String status){
		
		return getSuccessObj();
	}
	
	/**
	 * 导出获取订单列表信息
	 * @param search 搜索关键字
	 * @param orderType 订单类型，1：会员卡，2：充值
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param status 订单状态（1：待支付，2：支付中，3：支付成功，4：订单完成，5：订单失效）
	 * @return
	 */
	@RequestMapping("order200003")
	@ResponseBody
	public Json exportOrder( String search, String orderType, String startTime, String endTime,
			String status,HttpServletResponse respons){
		
		if(!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)){
			Date start = DateUtil.formatStringToDate(startTime);
			Date end = DateUtil.formatStringToDate(endTime);
			if(start.getTime() > end.getTime()){
				throw new ServiceException(MsgeData.PUBLIC_1000010017.getCode());
			}
		}
		//查询条件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", StringUtil.isEmpty(search) ? null : search);
		map.put("orderType", StringUtil.isEmpty(orderType) ? null : orderType);
		map.put("startTime", StringUtil.isEmpty(startTime) ? null : startTime);
		map.put("endTime", StringUtil.isEmpty(endTime) ? null : endTime);
		map.put("status", StringUtil.isEmpty(status) ? null : status);
		//到处excel
		orderService.exprotOrder(map,respons);
		return getSuccessObj();
	}
}
