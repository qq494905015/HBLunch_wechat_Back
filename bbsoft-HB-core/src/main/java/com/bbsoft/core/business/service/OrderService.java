package com.bbsoft.core.business.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bbsoft.core.business.domain.Order;

import net.sf.json.JSONObject;

/**
 * 订单业务接口
 * @author Chris.Zhang
 * @date 2017-5-16 18:20:53
 *
 */
public interface OrderService {

	/**
	 * 新增订单
	 * @param order 订单 
	 * @return
	 */
	public int addOrder(Order order);
	
	/**
	 * 修改订单
	 * @param order 订单
	 * @return
	 */
	public int updateOrder(Order order);
	
	/**
	 * 修改支付成功订单信息
	 * @param order 订单
	 * @return
	 */
	public int updatePaySuccOrder(Order order);
	
	/**
	 * 新增预支付订单
	 * @param order 订单
	 * @param request
	 */
	public JSONObject addToPayOrder(Order order, HttpServletRequest request);
	
	/**
	 * 获取指定的订单信息
	 * @param id 订单ID，订单号
	 * @return
	 */
	public Order getOrderById(String id);
	
	/**
	 * 获取指定的订单信息
	 * @param id 订单ID，订单号
	 * @param isException 是否抛出业务异常 true|false
	 * @return
	 */
	public Order getOrderById(String id, boolean isException);
	
	/**
	 * 多条件获取订单记录数
	 * @param map 查询条件
	 * @return
	 */
	public int getOrderCount(Map<String, Object> map);
	
	/**
	 * 多条分页获取订单记录
	 * @param map 查询条件
	 * @return
	 */
	public List<Order> getOrderByPage(Map<String, Object> map);
	
	/**
	 * 多条获取订单记录
	 * @param map 查询条件
	 * @return
	 */
	public void exprotOrder(Map<String, Object> map,HttpServletResponse respons);
	
	/**
	 * 根据微信用户唯一标识获取订单信息
	 * @param openId 微信用户唯一标识
	 * @param orderType 订单类型，1：会员卡，2：充值
	 * @param status 订单状态（1：待支付，2：支付中，3：支付成功，4：订单完成，5：订单失效）
	 * @return
	 */
	public Order getOrderByOpenId(String openId, String orderType, String status);
	
	/**
	 * 更新订单为已失效
	 * @param orderId 订单ID，除某此订单之外的不更新
	 * @param status 订单状态（1：待支付，2：支付中，3：支付成功，4：订单完成，5：订单失效）
	 * @param updateStatus 订单状态（1：待支付，2：支付中，3：支付成功，4：订单完成，5：订单失效）
	 * @param openId 订单所属微信用户ID
	 * @return
	 */
	public int updateFailOrders(String orderId, String status,String updateStatus, String openId);
}
