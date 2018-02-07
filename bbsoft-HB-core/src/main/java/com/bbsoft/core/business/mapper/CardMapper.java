package com.bbsoft.core.business.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bbsoft.core.business.domain.Card;

/**
 * 会员卡信息数据库访问接口
 * @author Chris.Zhang
 * @date 2017-5-16 18:09:07
 *
 */
public interface CardMapper {

	/**
	 * 查询指定的ID的会员卡信息
	 * @param id 会员主键ID
	 * @return
	 */
	public Card selectCardById(@Param("id") String id);
	
	/**
	 * 查询指定卡号的记录数
	 * @param cardNo 会员卡号
	 * @return
	 */
	public int selectCountByNo(@Param("cardNo") String cardNo);
	
	/**
	 * 多条件查询会员记录数
	 * @param map 查询条件
	 * @return
	 */
	public int selectCardCount(Map<String, Object> map);
	
	/**
	 * 多条件分页查询会员记录
	 * @param map 查询条件
	 * @return
	 */
	public List<Card> selectCardByPage(Map<String, Object> map);
	
	/**
	 * 新增会员卡信息
	 * @param card 会员卡
	 * @return
	 */
	public int insertCard(Card card);
	
	/**
	 * 修改会员卡信息
	 * @param card 会员卡
	 * @return
	 */
	public int updateCard(Card card);
	
	/**
	 * 删除会员卡
	 * @param id 会员卡ID
	 * @return
	 */
	public int deleteCard(@Param("id") String id);
	
	/**
	 * 获取随意一张会员卡信息
	 * @param isGoods 是否特殊
	 * @return
	 */
	public Card selectCardByOnce(@Param("isGoods") String isGoods);
	
	/**
	 * 查询指定卡号的会员卡信息
	 * @param cardNo 卡号
	 * @return
	 */
	public Card selectCardByNo(@Param("cardNo") String cardNo);
	
	/**
	 * 查询指定用户的会员卡信息
	 * @param userId 用户ID
	 * @return
	 */
	public Card selectCardByUser(@Param("userId") String userId);
	
	/**
	 * 根据微信用户唯一标识获取会员卡信息
	 * @param openId 微信用户唯一标识 
	 * @param status 状态（0：启用，1：禁用， 2：未支付禁用, 3：已付未激活, 4:已付已激活） 
	 * @return
	 */
	public Card selectCardByOpenId(@Param("openId") String openId, @Param("status") String status);
}
