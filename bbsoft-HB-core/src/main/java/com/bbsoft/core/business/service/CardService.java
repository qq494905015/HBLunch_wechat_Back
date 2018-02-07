package com.bbsoft.core.business.service;

import java.util.List;
import java.util.Map;

import com.bbsoft.core.business.domain.Card;
import com.bbsoft.core.business.domain.Order;

/**
 * 会员卡业务接口
 * @author Chris.Zhang
 * @date 2017-5-16 18:20:15
 *
 */
public interface CardService {

	/**
	 * 新增会员卡信息
	 * @param card 会员卡
	 * @return
	 */
	public int addCard(Card card);
	
	/**
	 * 批量新增会员卡信息
	 * @param card 会员卡
	 * @return
	 */
	public int addCardList(List<Card> listCard);
	
	/**
	 * 修改会员卡信息
	 * @param card 会员卡
	 * @return
	 */
	public int updateCard(Card card);
	
	/**
	 * 删除会员卡信息
	 * @param id 会员卡ID
	 * @return
	 */
	public int deleteCard(String id);
	
	/**
	 * 查询指定会员卡
	 * @param id 会员卡ID
	 * @return
	 */
	public Card getCardById(String id);
	
	/**
	 * 查询指定会员卡，是否抛出业务异常提示
	 * @param id 会员卡ID
	 * @param isException 是否抛出异常提示 true|false
	 * @return
	 */
	public Card getCardById(String id, boolean isException);
	
	/**
	 * 多条件获取会员卡记录数
	 * @param map 获取条件
	 * @return
	 */
	public int getCardCount(Map<String, Object> map);
	
	/**
	 * 多条件分页获取会员卡记录
	 * @param map 获取条件
	 * @return
	 */
	public List<Card> getCardByPage(Map<String, Object> map);
	
	/**
	 * 获取随意一张会员卡信息
	 * @param isGoods 是否特殊 0：否，1：是
	 * @return
	 */
	public Card getCardByOnce(String isGoods);
	
	/**
	 * 绑定卡
	 * @param cardNo 卡号
	 * @param phone 手机号
	 * @param userId 用户ID
	 * @param openId 微信用户唯一标识
	 * @return
	 */
	public int updateBindCard(String cardNo, String phone, String userId, String openId);
	
	/**
	 * 获取指定用户的会员卡信息
	 * @param userId 用户ID
	 * @return
	 */
	public Card getCardByUser(String userId);
	
	/**
	 * 获取指定卡号的会员卡信息
	 * @param code 卡号
	 * @return
	 */
	public Card getCardByCode(String code);
	
	/**
	 * 根据微信用户唯一标识获取会员卡信息
	 * @param openId 微信用户唯一标识
	 * @param status status 状态（0：启用，1：禁用， 2：未支付禁用, 3：已付未激活, 4:已付已激活）
	 * @return
	 */
	public Card getCardByOpenId(String openId, String status);
	
	/**
	 * 后台一键绑定会员卡，用于解决部分会员线下付款的情况
	 * @param card 会员卡信息
	 * @param order 订单信息
	 * @return
	 */
	public Card updateKeyWordBindCard(Card card, Order order);
}
