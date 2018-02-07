package com.bbsoft.wechat.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.AlidayuMsgUtil;
import com.bbsoft.common.util.PageUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.business.domain.Order;
import com.bbsoft.core.business.service.OrderService;
import com.bbsoft.core.user.domain.CashRecord;
import com.bbsoft.core.user.domain.UserBank;
import com.bbsoft.core.user.domain.WechatUser;
import com.bbsoft.core.user.service.CashRecordService;
import com.bbsoft.core.user.service.ConsumeRecordService;
import com.bbsoft.core.user.service.UserBankService;
import com.bbsoft.core.user.service.WechatUserService;
import com.bbsoft.wechat.controller.BaseController;

import net.sf.json.JSONObject;

/**
 * 用户资金业务接口
 * @author Chris.Zhang
 * @date 2017-5-27 20:04:26
 *
 */
@Controller
public class UserBusinessController extends BaseController{

	@Autowired
	private CashRecordService cashRecordService;
	@Autowired
	private UserBankService userBankService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private WechatUserService wechatUserService;
	@Autowired
	private ConsumeRecordService consumeRecordService;
	
	private Logger logger = LoggerFactory.getLogger(getClass()); 
	
	
	/**
	 * 提现
	 * @param request
	 * @param bankId 银行卡ID
	 * @param money 提现金额
	 * @param phone 手机号
	 * @param code 验证码
	 * @return
	 */
	@RequestMapping("userbusiness100000")
	@ResponseBody
	public Json cash(HttpServletRequest request, Long bankId, Long money, String phone, String code){
		if(bankId == null || money == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		String userId = getUserId(request);
		//验证验证码
		AlidayuMsgUtil.checkCode(phone, code, "4");
		UserBank bank = userBankService.getUserBankById(bankId, true);
		CashRecord cashRecord = new CashRecord(userId, bank.getBankNo(), bank.getBank(), money, "0", new Date(), "0");
		cashRecordService.addCashRecord(cashRecord);
		return getSuccessObj();
	}
	
	/**
	 * 充值
	 * @param request
	 * @param money 充值金额
	 * @return
	 */
	@RequestMapping("userbusiness100001")
	@ResponseBody
	public Json recharge(HttpServletRequest request, Long money){
		String userId = null;
		String openId = getOpenId(request);
		if(StringUtil.isEmpty(openId)){
			userId = getUserId(request);
			WechatUser wechatUser = wechatUserService.getByUserId(userId);
			openId = wechatUser.getOpenId();
		}else{
			WechatUser wechatUser = wechatUserService.getByOpenId(openId);
			if(wechatUser == null){
				throw new ServiceException(MsgeData.USER_1000020015.getCode());
			}
			userId = wechatUser.getUserId();
		}
		if(money == null || money <= 0){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		logger.debug("userId======================" + userId);
		logger.debug("openId======================" + openId);
		Order order = new Order();
		order.setOrderType("2");
		order.setUserId(userId);
		order.setOpenId(openId);
		order.setPrice(money);
		order.setPayType("2");
		JSONObject resultObj = orderService.addToPayOrder(order, request);
		return getSuccessObj(resultObj);
	}
	
	/**
	 * 获取当前登录用户的消费记录
	 * @param request
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @return
	 */
	@RequestMapping("userbusiness100002")
	@ResponseBody
	public Json getConsumeRecord(HttpServletRequest request, Integer pageNum, Integer pageSize){
		String userId = getUserId(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		Map<String, Object> countMap = consumeRecordService.getCountConsumeMap(map);
		Integer total = Integer.parseInt(countMap.get("count").toString());
		PageUtil<Map<String, Object>> page = new PageUtil<Map<String, Object>>(pageNum, pageSize, total);
		map.put("startItem", page.getStartItem());
		map.put("pageSize", page.getPageSize());
		List<Map<String, Object>> records = consumeRecordService.getConsumeRecordMap(map);
		if(records != null && records.size() > 0){
			for(Map<String, Object> recordMap : records){
				String createTime = recordMap.get("createTime").toString();
				createTime = createTime.substring(0, createTime.lastIndexOf("."));
				recordMap.put("createTime", createTime);
			}
		}
		page.setItems(records);
		return getSuccessObj(page);
	}
}
