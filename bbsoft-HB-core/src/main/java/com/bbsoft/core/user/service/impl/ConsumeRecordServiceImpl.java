package com.bbsoft.core.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.DateUtil;
import com.bbsoft.common.util.PriceUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.common.wechat.messge.TemplateMessage;
import com.bbsoft.common.wechat.service.MessageService;
import com.bbsoft.core.business.domain.Card;
import com.bbsoft.core.business.domain.ShopTotal;
import com.bbsoft.core.business.service.CardService;
import com.bbsoft.core.business.service.ProductService;
import com.bbsoft.core.business.service.ShopTotalService;
import com.bbsoft.core.user.domain.ConsumeRecord;
import com.bbsoft.core.user.domain.UserWallet;
import com.bbsoft.core.user.domain.WechatUser;
import com.bbsoft.core.user.mapper.ConsumeRecordMapper;
import com.bbsoft.core.user.service.ConsumeRecordService;
import com.bbsoft.core.user.service.UserWalletService;
import com.bbsoft.core.user.service.WechatUserService;

@Service
public class ConsumeRecordServiceImpl implements ConsumeRecordService {
	
	private Logger logger = LoggerFactory.getLogger(ConsumeRecordServiceImpl.class);
	
	@Autowired
	private ConsumeRecordMapper consumeRecordMapper;
	@Autowired
	private ShopTotalService shopTotalService;
	@Autowired
	private WechatUserService wechatUserService;
	@Autowired
	private CardService cardService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserWalletService userWalletService;

	public int addConsumeRecord(ConsumeRecord consumeRecord) {
		if(consumeRecord == null
			|| StringUtil.isEmpty(consumeRecord.getUserId())
			|| StringUtil.isEmpty(consumeRecord.getShopId())
			|| StringUtil.isEmpty(consumeRecord.getCardId())
			|| StringUtil.isEmpty(consumeRecord.getFromType())
			|| consumeRecord.getMoney() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if(consumeRecord.getCreateTime() == null){
			consumeRecord.setCreateTime(new Date());
		}
		if(StringUtil.isEmpty(consumeRecord.getIsDelete())){
			consumeRecord.setIsDelete("0");
		}
		if(StringUtil.isEmpty(consumeRecord.getStatus())){
			consumeRecord.setStatus("0");
		}
		int result = 0;
		Card card = cardService.getCardById(consumeRecord.getCardId(), true);
		Card editCard = new Card();
		editCard.setId(card.getId());
		//用户钱包信息
		UserWallet editWallet = new UserWallet();
		UserWallet dbWallet = userWalletService.getWalletByUser(consumeRecord.getUserId());
		if(dbWallet != null){
			editWallet.setUserId(dbWallet.getUserId());
		}
		//fromType 消费来源（1：会员卡，2：充值，3：消费）
		if("1".equals(consumeRecord.getFromType())){
			//
			consumeRecord.setShopTotalId(0L);
		}
		if("2".equals(consumeRecord.getFromType())){
			editCard.setBalance(card.getBalance() + consumeRecord.getMoney());
			editWallet.setBalance(dbWallet.getBalance() + consumeRecord.getMoney());
			consumeRecord.setShopTotalId(0L);
		}
		if("3".equals(consumeRecord.getFromType())){
			//新增门店月统计记录
			ShopTotal shopTotal = new ShopTotal(consumeRecord.getShopId(), consumeRecord.getMoney(), new Date(), DateUtil.formatDateToString(new Date(), DateUtil.GLOBAL_DATE_PATTERN), "0");
			Long curShopTotalId = shopTotalService.addShopTotal(shopTotal);
			logger.debug("addShoptTotal return curShopTotalId ============================" + curShopTotalId);
			if(card.getBalance() >= consumeRecord.getMoney()){
				editCard.setBalance(card.getBalance() - consumeRecord.getMoney());
				editWallet.setBalance(dbWallet.getBalance() - consumeRecord.getMoney());
			}else{
				throw new ServiceException(MsgeData.CARD_1000030013.getCode());
			}
			if(curShopTotalId == null){
				throw new ServiceException(MsgeData.SHOP_1000040005.getCode());
			}
			consumeRecord.setShopTotalId(curShopTotalId);
			consumeRecord.setFromId("0");
		}
		//新增消费记录
		result = consumeRecordMapper.insertConsumeRecord(consumeRecord);
		if("2".equals(consumeRecord.getFromType()) || "3".equals(consumeRecord.getFromType())){
			//修改会员卡信息
			result = cardService.updateCard(editCard);
		}
		if(!StringUtil.isEmpty(consumeRecord.getUserId()) && !"0".equals(consumeRecord.getUserId())){
			//同步用户钱包信息
			result = userWalletService.updateWallet(editWallet);
		}
		
		//新增消费后需要推送消费消息
		if("3".equals(consumeRecord.getFromType())){
			
			sendMessage(consumeRecord, card);
		}
		//同步修改微信会员卡信息
//		JSONObject cardJson = new JSONObject();
//		if("2".equals(consumeRecord.getFromType()) || "3".equals(consumeRecord.getFromType())){
//			Product product = productService.getProductById(card.getProductId(), true);
//			cardJson.put("code", card.getCardNo());
//			cardJson.put("card_id", product.getCardId());
//			//转换变动金额为“元”
//			String consumePrice = PriceUtil.formatPriceToString(consumeRecord.getMoney() * 0.01);
//			if("2".equals(consumeRecord.getFromType())){
//				cardJson.put("add_balance", consumeRecord.getMoney());
//				cardJson.put("record_balance", "充值金额" + consumePrice + "元");
//			}else{
//				cardJson.put("add_balance", consumeRecord.getMoney() * -1);
//				cardJson.put("record_balance", "消费金额" + consumePrice + "元");
//			}
//			//同步修改微信会员卡
//			WechatCardService.updateUserCard(cardJson);
//		}
		return result;
	}

	public int updateConsumeRecord(ConsumeRecord consumeRecord) {
		if(consumeRecord == null
			|| consumeRecord.getId() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return consumeRecordMapper.updateConsumeRecord(consumeRecord);
	}

	public int deleteConsumeRecord(Long id) {
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return consumeRecordMapper.deleteConsumeRecord(id);
	}
	
	public Map<String, Object> getCountConsumeMap(Map<String, Object> map) {
		return consumeRecordMapper.selectCountConsumeMap(map);
	}
	
	public Map<String, Object> getConsumeRecord(Map<String, Object> map) {
		return consumeRecordMapper.selectCountConsumeRecord(map);
	}

	public List<ConsumeRecord> getConsumeRecordByPage(Map<String, Object> map) {
		checkSearch(map);
		return consumeRecordMapper.selectConsumeRecordByPage(map);
	}

	public List<Map<String, Object>> getConsumeRecordMap(Map<String, Object> map) {
		checkSearch(map);
		return consumeRecordMapper.selectConsumeRecordMap(map);
	}
	
	public Map<String, Object> getPlateCount(Map<String, Object> map) {
		return consumeRecordMapper.selectPlateCount(map);
	}

	public List<Map<String, Object>> getPlateTotalMap(Map<String, Object> map) {
		checkSearch(map);
		return consumeRecordMapper.selectPlateTotalMap(map);
	}
	
	public ConsumeRecord getRecordByFrom(String fromId, String fromType) {
		if(StringUtil.isEmpty(fromId) || StringUtil.isEmpty(fromType)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return consumeRecordMapper.selectRecordByFrom(fromId, fromType);
	}
	
	private void checkSearch(Map<String, Object> map){
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		map.put("startItem", map.get("startItem") == null ? 0 : map.get("startItem"));
		map.put("pageSize", map.get("pageSize") == null ? 0 : map.get("pageSize"));
	}
	
	private void sendMessage(ConsumeRecord consumeRecord, Card card){
		WechatUser wechatUser = wechatUserService.getByUserId(consumeRecord.getUserId());
		Map<String, Object> data = new HashMap<String, Object>();
		
		Map<String, Object> productType = new HashMap<String, Object>();
		productType.put("color", "#173177");
		productType.put("value", "会员卡号");
		data.put("productType", productType);
		
		Map<String, Object> name = new HashMap<String, Object>();
		name.put("color", "#173177");
		name.put("value", card.getCardNo());
		data.put("name", name);
		
		Map<String, Object> accountType = new HashMap<String, Object>();
		accountType.put("color", "#173177");
		accountType.put("value", "金额");
		data.put("accountType", accountType);
		
		Map<String, Object> account = new HashMap<String, Object>();
		account.put("color", "#173177");
		account.put("value", PriceUtil.formatPriceToString(consumeRecord.getMoney() * 0.01));
		data.put("account", account);
		
		Map<String, Object> time = new HashMap<String, Object>();
		time.put("color", "#173177");
		time.put("value", DateUtil.formatDateToString(consumeRecord.getCreateTime(), DateUtil.CN_TIME_PATTERN));
		data.put("time", time);
		
		Map<String, Object> remark = new HashMap<String, Object>();
		remark.put("color", "#173177");
		remark.put("value", "如非本人消费或者消费有误，请联系客服。");
		data.put("remark", remark);
		TemplateMessage message = new TemplateMessage(wechatUser.getOpenId(), "ixRy5zscxNy7C2FdkFop4HrfNZSU9tX5YFRNtcKIcmM", "http://www.wan-we.com/", data);
		MessageService.sendTemplateMsg(message);
	}
}
