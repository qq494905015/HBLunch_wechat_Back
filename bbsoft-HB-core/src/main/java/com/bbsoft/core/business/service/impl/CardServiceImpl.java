package com.bbsoft.core.business.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.SnowflakeIdUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.business.domain.Card;
import com.bbsoft.core.business.domain.Order;
import com.bbsoft.core.business.mapper.CardMapper;
import com.bbsoft.core.business.service.CardService;
import com.bbsoft.core.business.service.OrderService;
import com.bbsoft.core.business.service.ProductService;
import com.bbsoft.core.user.domain.BaseUser;
import com.bbsoft.core.user.domain.ConsumeRecord;
import com.bbsoft.core.user.service.BaseUserService;
import com.bbsoft.core.user.service.ConsumeRecordService;

@Service
public class CardServiceImpl implements CardService {
	
	@Autowired
	private CardMapper cardMapper;
	@Autowired
	private BaseUserService baseUserService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ConsumeRecordService consumeRecordService;
	
	public int addCard(Card card) {
		if(card == null 
			|| StringUtil.isEmpty(card.getCardNo())
			|| card.getPrice() == null
			|| card.getProductId() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if(cardMapper.selectCountByNo(card.getCardNo()) > 0){
			throw new ServiceException(MsgeData.CARD_1000030001.getCode());
		}
		//校验商品
		productService.getProductById(card.getProductId(), true);
		
		if(StringUtil.isEmpty(card.getId())){
			card.setId(SnowflakeIdUtil.getGeneratedKey());
		}
		if(StringUtil.isEmpty(card.getCardName())){
			card.setCardName("万味网黑卡");
		}
		if(card.getBalance() == null){
			card.setBalance(0L);
		}
		if(StringUtil.isEmpty(card.getType())){
			card.setType("普通卡");
		}
		if(StringUtil.isEmpty(card.getStatus())){
			card.setStatus("0");
		}
		if(StringUtil.isEmpty(card.getIsGoods())){
			card.setIsGoods("0");
		}
		if(!StringUtil.isEmpty(card.getSurname())){
			card.setSurname(card.getSurname().toUpperCase());
		}
		if(!StringUtil.isEmpty(card.getName())){
			card.setName(card.getName().toUpperCase());
		}
		card.setIsDelete("0");
		card.setCreateTime(new Date());
		
		return cardMapper.insertCard(card);
	}
	
	public int addCardList(List<Card> listCard) {
		for (Card card : listCard) {
			if(StringUtil.isEmpty(card.getId())){
				card.setId(SnowflakeIdUtil.getGeneratedKey());
			}
			if(StringUtil.isEmpty(card.getCardName())){
				card.setCardName("万味网黑卡");
			}
			if(card.getBalance() == null){
				card.setBalance(0L);
			}
			if(StringUtil.isEmpty(card.getType())){
				card.setType("普通卡");
			}
			if(StringUtil.isEmpty(card.getStatus())){
				card.setStatus("0");
			}
			if(StringUtil.isEmpty(card.getIsGoods())){
				card.setIsGoods("0");
			}
			if(!StringUtil.isEmpty(card.getSurname())){
				card.setSurname(card.getSurname().toUpperCase());
			}
			if(!StringUtil.isEmpty(card.getName())){
				card.setName(card.getName().toUpperCase());
			}
			card.setIsDelete("0");
			card.setCreateTime(new Date());
			
			cardMapper.insertCard(card);
		}
		return 0;
	}

	public int updateCard(Card card) {
		if(card == null 
			|| StringUtil.isEmpty(card.getId())){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Card dbCard = this.getCardById(card.getId(), true);
		if(!StringUtil.isEmpty(card.getCardNo()) 
			&& !card.getCardNo().equals(dbCard.getCardNo())
			&& cardMapper.selectCountByNo(card.getCardNo()) > 0){
			throw new ServiceException(MsgeData.CARD_1000030001.getCode());
		}
		//校验商品
		if(card.getProductId() != null){
			productService.getProductById(card.getProductId(), true);
		}
		if(!StringUtil.isEmpty(card.getSurname())){
			card.setSurname(card.getSurname().toUpperCase());
		}
		if(!StringUtil.isEmpty(card.getName())){
			card.setName(card.getName().toUpperCase());
		}
		return cardMapper.updateCard(card);
	}

	public int deleteCard(String id) {
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		this.getCardById(id, true);
		return cardMapper.deleteCard(id);
	}

	public Card getCardById(String id) {
		return this.getCardById(id, false);
	}

	public Card getCardById(String id, boolean isException) {
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Card card = cardMapper.selectCardById(id);
		if(card == null && isException){
			throw new ServiceException(MsgeData.CARD_1000030002.getCode());
		}
		return card;
	}
	
	public int getCardCount(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return cardMapper.selectCardCount(map);
	}

	public List<Card> getCardByPage(Map<String, Object> map) {
		if(map == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		map.put("startItem", map.get("startItem") == null ? 0 : map.get("startItem"));
		map.put("pageSize", map.get("pageSize") == null ? 10 : map.get("pageSize"));
		return cardMapper.selectCardByPage(map);
	}

	public Card getCardByOnce(String isGoods) {
		if(StringUtil.isEmpty(isGoods)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return cardMapper.selectCardByOnce(isGoods);
	}

	public int updateBindCard(String cardNo, String phone, String userId, String openId) {
		if(StringUtil.isEmpty(cardNo)
			|| StringUtil.isEmpty(phone)
			|| StringUtil.isEmpty(userId)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Card card = cardMapper.selectCardByNo(cardNo);
		if(card == null){
			throw new ServiceException(MsgeData.CARD_1000030002.getCode());
		}
		if(!"3".equals(card.getStatus())){
			throw new ServiceException(MsgeData.CARD_1000030019.getCode());
		}
		BaseUser baseUser = baseUserService.getUserById(userId, true);
		//与当前登录用户必须为同一个手机号
		if(!phone.equals(baseUser.getPhone())){
			throw new ServiceException(MsgeData.USER_1000020012.getCode());
		}
		if(StringUtil.isEmpty(openId)){
			openId = baseUser.getWechatNo();
		}
		if(!openId.equals(card.getOpenId())){
			throw new ServiceException(MsgeData.CARD_1000030017.getCode());
		}
		Order order = orderService.getOrderByOpenId(card.getOpenId(), "1", "3");
		if(order != null){
			//更新用户的消费记录，因为在购买时，不能关联相应的User
			ConsumeRecord consumeRecord = consumeRecordService.getRecordByFrom(order.getId(), "1");
			ConsumeRecord editRecord = new ConsumeRecord();
			editRecord.setId(consumeRecord.getId());
			editRecord.setUserId(userId);
			consumeRecordService.updateConsumeRecord(editRecord);
			
			BaseUser editUser = new BaseUser();
			editUser.setId(baseUser.getId());
			editUser.setRealName(order.getRealName());
			baseUserService.updateUser(editUser);
			
			Order editOrder = new Order();
			editOrder.setId(order.getId());
			editOrder.setUserId(userId);
			orderService.updateOrder(editOrder);
		}
		card.setUserId(userId);
		card.setStatus("4");
		return cardMapper.updateCard(card);
	}

	public Card getCardByUser(String userId) {
		if(StringUtil.isEmpty(userId)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return cardMapper.selectCardByUser(userId);
	}

	public Card getCardByCode(String code) {
		if(StringUtil.isEmpty(code)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return cardMapper.selectCardByNo(code);
	}

	public Card getCardByOpenId(String openId, String status) {
		if(StringUtil.isEmpty(openId) || StringUtil.isEmpty(status)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return cardMapper.selectCardByOpenId(openId, status);
	}

	public Card updateKeyWordBindCard(Card card, Order order) {
		if(card == null || order == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		BaseUser baseUser = baseUserService.getUserById(card.getUserId(), true);
		Card userCard = getCardByUser(card.getUserId());
		Card openCard = getCardByOpenId(baseUser.getWechatNo(), "3");
		if(userCard != null || openCard != null){
			throw new ServiceException(MsgeData.CARD_1000030023.getCode());
		}
		Card dbCard = this.getCardByCode(card.getCardNo());
		if(dbCard == null){
			throw new ServiceException(MsgeData.CARD_1000030002.getCode());
		}
		if(!"0".equals(dbCard.getStatus())){
			throw new ServiceException(MsgeData.CARD_1000030022.getCode());
		}
		//新增订单
		order.setUserId(card.getUserId());
		order.setOpenId(baseUser.getWechatNo());
		order.setOrderType("1");
		order.setStatus("3");
		order.setCardId(dbCard.getId());
		orderService.addOrder(order);
		
		//新消费流水信息
		ConsumeRecord consumeRecord = new ConsumeRecord(order.getUserId(), "0", dbCard.getId(), order.getPrice()); 
		consumeRecord.setFromId(order.getId());
		consumeRecord.setFromType("1");
		consumeRecordService.addConsumeRecord(consumeRecord);
		
		//更新会员卡
		card.setId(dbCard.getId());
		card.setStatus("4");
		card.setUpdateTime(new Date());
		card.setOpenId(baseUser.getWechatNo());
		cardMapper.updateCard(card);
		return card;
	}

}
