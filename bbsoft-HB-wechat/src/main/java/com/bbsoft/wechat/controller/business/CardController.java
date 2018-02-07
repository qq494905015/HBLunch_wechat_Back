package com.bbsoft.wechat.controller.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.AlidayuMsgUtil;
import com.bbsoft.common.util.BeanToMapUtil;
import com.bbsoft.common.util.PageUtil;
import com.bbsoft.common.util.QrcodeUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.business.domain.Card;
import com.bbsoft.core.business.service.CardService;
import com.bbsoft.core.user.domain.BaseUser;
import com.bbsoft.core.user.service.BaseUserService;
import com.bbsoft.wechat.controller.BaseController;

/**
 * 会员卡接口
 * @author Chris.Zhang
 * @date 2017-5-25 13:44:22
 *
 */
@Controller
public class CardController extends BaseController{

	@Autowired
	private CardService cardService;
	@Autowired
	private BaseUserService baseUserService;
	
	/**
	 * 获取会员卡信息
	 * @param request
	 * @param pageNum 当前页，可用于换一批功能
	 * @param pageSize 页大小
	 * @param isGoods 是否是特殊顶级号码
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("card100000")
	@ResponseBody
	public Json getCards(HttpServletRequest request, Integer pageNum, Integer pageSize, String isGoods, String search, Integer productId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isGoods", isGoods);
		map.put("search", StringUtil.isEmpty(search) ? null : search);
		map.put("productId", productId);
		map.put("status", "0");
		int total = cardService.getCardCount(map);
		PageUtil<Map> page = new PageUtil<Map>(pageNum, pageSize, total);
		if(pageNum > page.getTotal()){
			page.setPageNum(1);
			page.setStartItem(0);
		}
		map.put("startItem", page.getStartItem());
		map.put("pageSize", page.getPageSize());
		map.put("rand", "true");
		List<Card> cards = cardService.getCardByPage(map);
//		if(cards.size() == 0){
//			throw new ServiceException(MsgeData.CARD_1000030015.getCode());
//		}
		page.setItems(BeanToMapUtil.convertList(cards));
		return getSuccessObj(ResultUtil.returnByObj(page));
	}
	
	/**
	 * 随机获取一张会员卡信息
	 * @param request
	 * @return
	 */
	@RequestMapping("card100001")
	@ResponseBody
	public Json getRandomCard(HttpServletRequest request){
		Card card = cardService.getCardByOnce("0");
		return getSuccessObj(ResultUtil.returnByObj(card));
	}
	
	/**
	 * 绑定会员卡信息
	 * @param request
	 * @param cardNo 卡号
	 * @param phone 联系电话
	 * @param code 验证码
	 * @return
	 */
	@RequestMapping("card100002")
	@ResponseBody
	public Json bindCard(
			HttpServletRequest request,
			String cardNo, String phone, String code
			){
		if(StringUtil.isEmpty(cardNo) || StringUtil.isEmpty(phone) || StringUtil.isEmpty(code)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if(AlidayuMsgUtil.checkCode(phone, code, "4")){
			cardService.updateBindCard(cardNo, phone, getUserId(request), getOpenId(request));
		}
		return getSuccessObj();
	}
	
	/**
	 * 获取指定会员卡信息
	 * @param id 会员卡ID
	 * @return
	 */
	@RequestMapping("card100003")
	@ResponseBody
	public Json getCardById(HttpServletRequest request, String id){
		String userId = getUserId(request);
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Card card = cardService.getCardById(id, true);
		if(!userId.equals(card.getUserId())){
			throw new ServiceException(MsgeData.CARD_1000030009.getCode());
		}
		return getSuccessObj(ResultUtil.returnByObj(card));
	}
	
	/**
	 * 根据卡号获取会员卡信息
	 * @param request
	 * @param cardId 会员卡卡号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("card100004")
	@ResponseBody
	public Json getCardByCode(HttpServletRequest request, String code){
		if(StringUtil.isEmpty(code)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Card card = cardService.getCardByCode(code);
		if(card == null){
			throw new ServiceException(MsgeData.CARD_1000030002.getCode());
		}
		BaseUser baseUser = baseUserService.getUserById(card.getUserId(), true);
		Map<String, Object> resultMap = BeanToMapUtil.convertBean(card);
		resultMap.put("phone", baseUser.getPhone());
		resultMap.put("realName", baseUser.getRealName());
		return getSuccessObj(ResultUtil.returnByObj(resultMap));
	}
	
	/**
	 * 修改会员卡信息
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping("card100005")
	@ResponseBody
	public Json updateCard(HttpServletRequest request, String code){
		if(StringUtil.isEmpty(code)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Card card = cardService.getCardByUser(getUserId(request));
		if(card == null){
			throw new ServiceException(MsgeData.CARD_1000030002.getCode());
		}
		if(StringUtil.isEmpty(card.getCode())){
			card.setCode(code);
		}else{
			throw new ServiceException(MsgeData.CARD_1000030014.getCode());
		}
		cardService.updateCard(card);
		return getSuccessObj();
	}
	
	/**
	 * 获取当前微信用户的会员卡信息
	 * @param request
	 * @return
	 */
	@RequestMapping("card100006")
	@ResponseBody
	public Json getCardByOpenId(HttpServletRequest request){
		String openId = getOpenId(request);
		if(StringUtil.isEmpty(openId)){
			throw new ServiceException(MsgeData.SYSTEM_10128.getCode());
		}
		Card card = cardService.getCardByOpenId(openId, "3");
		if(card == null){
			throw new ServiceException(MsgeData.CARD_1000030020.getCode());
		}
		return getSuccessObj(ResultUtil.returnByObj(card));
	}
	
	/**
	 * 获取当前登录用户卡号和二维码信息
	 * @param request
	 * @return
	 */
	@RequestMapping("card100007")
	@ResponseBody
	public Json getCardQrcode(HttpServletRequest request){
		String userId = getUserId(request);
		Card card = cardService.getCardByUser(userId);
		if(card == null){
			throw new ServiceException(MsgeData.CARD_1000030020.getCode());
		}
		//先清空
		if(codeCacheMap.keySet().size() > 0){
			for (Map.Entry<String, Map<String, Object>> entry : codeCacheMap.entrySet()) {
				Map<String, Object> codeMap = entry.getValue();
				if(codeMap != null){
					String cardNo = codeMap.get("cardNo").toString();
					if(card.getCardNo().equals(cardNo)){
						codeCacheMap.remove(entry.getKey());
						break;
					}
				}
			}
		}
		Map<String, Object> codeMap = QrcodeUtil.produceCode(card.getCardNo(), true);
		codeMap.put("cardNo", card.getCardNo());
		codeCacheMap.put(codeMap.get("codeId").toString(), codeMap);
		return getSuccessObj(codeMap);
	}
}
