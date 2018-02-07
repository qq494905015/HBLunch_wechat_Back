package com.bbsoft.core.business.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.business.domain.Order;

/**
 * 订单数据库访问接口
 * @author Chris.Zhang
 * @date 2017-5-16 18:09:38
 *
 */
public interface OrderMapper {
	
	/**
	 * 查询指定ID的订单信息
	 * @param id 主键ID，订单号
	 * @return
	 */
	public Order selectOrderById(@Param("id") String id);
	
	/**
	 * 多条件查询订单记录数
	 * @param map 查询条件
	 * @return
	 */
	public int selectOrderCount(Map<String, Object> map);
	
	/**
	 * 多条件分页查询订单记录
	 * @param map 查询条件
	 * @return
	 */
	public List<Order> selectOrderByPage(Map<String, Object> map);
	
	/**
	 * 导出多条件查询订单记录
	 * @param map 查询条件
	 * @return
	 */
	public List<Order> selectExportOrder(Map<String, Object> map);
	
	/**
	 * 新增订单信息
	 * @param order 订单
	 * @return
	 */
	public int insertOrder(Order order);
	
	/**
	 * 修改订单信息
	 * @param order 订单
	 * @return
	 */
	public int updateOrder(Order order);
	
	/**
	 * 删除订单信息
	 * @param id 订单ID，订单号
	 * @return
	 */
	public int deleteOrder(@Param("id") String id);
	
	/**
	 * 获取微信用户的订单信息
	 * @param openId 微信用户唯一标识
	 * @param orderType 订单类型，1：会员卡，2：充值
	 * @param status 订单状态（1：待支付，2：支付中，3：支付成功，4：订单完成，5：订单失效）
	 * @return
	 */
	public Order selectOrderByOpenId(@Param("openId") String openId, @Param("orderType") String orderType, @Param("status") String status);
	
	/**
	 * 更新订单为已失效
	 * @param orderId 订单ID，除某此订单之外的不更新
	 * @param status 订单状态（1：待支付，2：支付中，3：支付成功，4：订单完成，5：订单失效）
	 * @param updateStatus 订单状态（1：待支付，2：支付中，3：支付成功，4：订单完成，5：订单失效）
	 * @param openId 订单所属微信用户ID
	 * @return
	 */
	public int updateFailOrders(@Param("orderId") String orderId, @Param("status") String status, @Param("updateStatus") String updateStatus, @Param("openId") String openId);
}
