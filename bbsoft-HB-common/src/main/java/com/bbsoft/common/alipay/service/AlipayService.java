/**  
 * @Title: AlipayService.java
 * @Package: com.bbsoft.alipay
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-2-27
 */
package com.bbsoft.common.alipay.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.bbsoft.common.alipay.config.AlipayConfig;
import com.bbsoft.common.alipay.util.AlipayClientFactory;
import com.bbsoft.common.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * ClassName: AlipayService 
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-2-27
 */
public class AlipayService {
	
private static Logger logger = LoggerFactory.getLogger(AlipayService.class);
	
	/**
	 * @Description: app创建支付宝订单
	 * @param: @param order
	 * @param: @return   
	 * @return: String  
	 * @throws
	 * @author: VULCAN
	 * @date: 2017-3-1
	 */
	public static String buildAppPayService(Map<String, String> params){
		  AlipayClient alipayClient = AlipayClientFactory.getAlipayClient();
		  //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		  AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest(); 
		  AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		  //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式
		  //(model和biz_content同时存在的情况下取biz_content)
		  model.setBody(params.get("body"));
		  model.setSubject(params.get("subject"));//    订单信息
		  model.setOutTradeNo(params.get("orderNo"));//订单号
		  //超时时间默认为30m
		  if(StringUtil.isEmpty(params.get("timeoutExpress"))){
			  model.setTimeoutExpress("30m");
		  }else{
			  model.setTimeoutExpress(params.get("timeoutExpress"));
		  }
		  model.setTotalAmount(params.get("totalAmount"));//平台定金
		  model.setProductCode("QUICK_MSECURITY_PAY");
		  alipayRequest.setBizModel(model);
		  alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		  String form = "";
		  try {
	          //这里和普通的接口调用不同，使用的是sdkExecute
	          AlipayTradeAppPayResponse alipayResponse = alipayClient.sdkExecute(alipayRequest);
	          form = alipayResponse.getBody();
	          //就是orderString 可以直接给客户端请求，无需再做处理。
	          logger.debug("buildAppPayService return 【{}】", form);
	      	} catch (AlipayApiException e) {
		          e.printStackTrace();
		  }
	    return form;
	}
	
	/**
	 * @Description: 退款
	 * @param: @return   
	 * @return: String  
	 * @throws
	 * @author: VULCAN
	 * @date: 2017-3-1
	 */
	public static Boolean buildTradeRefundService(Map<String, String> params){
		AlipayClient alipayClient = AlipayClientFactory.getAlipayClient();
		boolean flag = false;
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("out_trade_no", params.get("outTradeNo"));		//本地订单号
		map.put("trade_no", params.get("TradeNo"));				//退款订单号
		map.put("refund_amount", params.get("refundAmount"));	//退款金额
		map.put("refund_reason", "【云助手365】服务人员取消订单");			//退款理由
		JSONObject jsonObj = JSONObject.fromObject(map);
		request.setBizContent(jsonObj.toString());
		
		AlipayTradeRefundResponse response;
		try {
			response = alipayClient.execute(request);
			if(response.isSuccess()){
				flag = true;
			} else {
				System.out.println("调用失败");
				flag = false;
			}
			logger.debug("buildTradeRefundService request {}, return result is {}", (response.isSuccess() ? "success" : "fail"), response.isSuccess());
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public static void buildImgCodePayService(){
		AlipayClient alipayClient = AlipayClientFactory.getAlipayClient();; //获得初始化的AlipayClient
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();//创建API对应的request类
		request.setBizContent("{" +
				"    \"out_trade_no\":\"20150320010101002\"," +
				"    \"total_amount\":\"88.88\"," +
				"    \"subject\":\"Iphone6 16G\"," +
				"    \"store_id\":\"NJ_001\"," +
				"    \"timeout_express\":\"90m\"}");//设置业务参数
		AlipayTradePrecreateResponse response = null;
		try {
			response = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		
		System.out.print(response.getBody());
		//根据response中的结果继续业务逻辑处理
	}
	
	public static void main(String[] args) {
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("body", "云助手365");
//		map.put("subject", "云助手365");
//		map.put("orderNo", "QC-" + System.currentTimeMillis());
//		map.put("totalAmount", "0.01");
//		String result = buildAppPayService(map);
//		System.out.println(result);
		
		buildImgCodePayService();
	}
	
	
}
