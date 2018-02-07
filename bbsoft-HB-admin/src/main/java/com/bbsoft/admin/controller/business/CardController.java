package com.bbsoft.admin.controller.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
import com.bbsoft.core.business.domain.Product;
import com.bbsoft.core.business.service.CardService;
import com.bbsoft.core.business.service.ProductService;

/**
 * 会员卡接口
 * @author Chris.Zhang
 * @date 2017-5-23 17:23:10
 *
 */
@Controller
public class CardController extends BaseController{

	@Autowired
	private CardService cardService;
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 新增会员卡信息
	 * @param userId 用户ID
	 * @param productId 商品信息ID
	 * @param cardNo 卡号
	 * @param cardName 卡名称
	 * @param surname 姓氏，英文大写
	 * @param name 名， 英文大写
	 * @param balance 余额
	 * @param price 会员卡售卖价格
	 * @param type 卡类型
	 * @param bindTime 绑定时间
	 * @param startTime 有效开始时间
	 * @param endTime 有效结束时间
	 * @param status 状态（0：启用，1：禁用，2：未支付禁用）
	 * @param isGoods 是否是靓号（0：否，1：是）
	 * @return
	 */
	@RequestMapping("card200000")
	@ResponseBody
	public Json addCard(
			String userId, String cardNo, String cardName, 
			String surname, String name, Long balance, 
			Long price, String type, String bindTime, 
			String startTime, String endTime, String status, 
			String isGoods, Long productId
			){
		if(StringUtil.isEmpty(cardNo) 
			|| StringUtil.isEmpty(cardName)
			|| StringUtil.isEmpty(isGoods)
			|| productId == null
			|| price == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Date bind = null;
		Date start = null;
		Date end = null;
		if(StringUtil.isEmpty(bindTime)){
			bind = DateUtil.formatStringToDate(bindTime);
		}
		if(StringUtil.isEmpty(startTime)){
			start = DateUtil.formatStringToDate(startTime);
		}
		if(StringUtil.isEmpty(endTime)){
			end = DateUtil.formatStringToDate(endTime);
		}
		if(start != null && end != null && start.getTime() > end.getTime()){
			throw new ServiceException(MsgeData.PUBLIC_1000010017.getCode());
		}
		Card card = new Card(userId, productId, cardNo, cardName, surname, name, balance, price, type, bind, start, end, status, isGoods, new Date());
		cardService.addCard(card);
		return getSuccessObj();
	}
	
	/**
	 * 修改会员卡信息
	 * @param id 会员卡ID
	 * @param userId 用户ID
	 * @param productId 商品信息ID
	 * @param cardNo 卡号
	 * @param cardName 卡名称
	 * @param surname 姓氏，英文大写
	 * @param name 名， 英文大写
	 * @param balance 余额
	 * @param price 会员卡售卖价格
	 * @param type 卡类型
	 * @param bindTime 绑定时间
	 * @param startTime 有效开始时间
	 * @param endTime 有效结束时间
	 * @param status 状态（0：启用，1：禁用，2：未支付禁用）
	 * @param isGoods 是否是靓号（0：否，1：是）
	 * @return
	 */
	@RequestMapping("card200001")
	@ResponseBody
	public Json updateCard(
			String id, String userId, Long productId, String cardNo, 
			String cardName, String surname, String name, 
			Long balance, Long price, String type, 
			String bindTime, String startTime, String endTime, 
			String status, String isGoods){
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Date bind = null;
		Date start = null;
		Date end = null;
		if(StringUtil.isEmpty(bindTime)){
			bind = DateUtil.formatStringToDate(bindTime);
		}
		if(StringUtil.isEmpty(startTime)){
			start = DateUtil.formatStringToDate(startTime);
		}
		if(StringUtil.isEmpty(endTime)){
			end = DateUtil.formatStringToDate(endTime);
		}
		if(start != null && end != null && start.getTime() > end.getTime()){
			throw new ServiceException(MsgeData.PUBLIC_1000010017.getCode());
		}
		Card card = new Card(id, userId, productId, cardNo, cardName, surname, name, balance, price, type, bind, start, end, status, isGoods, null, new Date());
		cardService.updateCard(card);
		return getSuccessObj();
	}
	
	/**
	 * 获取指定会员卡信息
	 * @param id 会员卡ID
	 * @return
	 */
	@RequestMapping("card200002")
	@ResponseBody
	public Json getCardById(String id){
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Card card = cardService.getCardById(id, true);
		return getSuccessObj(ResultUtil.returnByObj(card));
	}
	
	/**
	 * 分页获取会员卡信息列表
	 * @param pageNum 当前页 
	 * @param pageSize 页大小
	 * @param search 搜索关键字
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("card200003")
	@ResponseBody
	public Json getCardByPage(Integer pageNum, Integer pageSize, String search){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", StringUtil.isEmpty(search) ? null : search);
		int total = cardService.getCardCount(map);
		PageUtil<Map> page = new PageUtil<>(pageNum, pageSize, total);
		map.put("startItem", page.getStartItem());
		map.put("pageSize", page.getPageSize());
		List<Card> cards = cardService.getCardByPage(map);
		page.setItems(BeanToMapUtil.convertList(cards));
		return getSuccessObj(ResultUtil.returnByObj(page));
	}
	
	/**
	 * 删除会员卡信息
	 * @param id 会员卡ID
	 * @return
	 */
	@RequestMapping("card200004")
	@ResponseBody
	public Json deleteCard(String id){
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		cardService.deleteCard(id);
		return getSuccessObj();
	}
	
	/**
	 * 导入会员卡信息
	 * @return
	 */
	@RequestMapping("card200005")
	@ResponseBody
	public Json importCard(@RequestParam("file") CommonsMultipartFile file){
		String errorStr = "";
	    Workbook workbook;
		try {
			workbook = new XSSFWorkbook(file.getInputStream());//2007 
		    //3.得到Excel工作表对象  
		    Sheet sheet = workbook.getSheetAt(0); 
	        //总行数  
	        int trLength = sheet.getLastRowNum();  
	        //4.得到Excel工作表的行  
	        Row row = sheet.getRow(0); 
	        //总列数  
	        int tdLength = row.getLastCellNum();
	        if(tdLength==5){
	        	List<Card> listCard = new ArrayList<Card>();
	        	 for(int i=1;i<=trLength;i++){  
	 	            //得到Excel工作表的行  
	 	            Row row1 = sheet.getRow(i); 
	 	            Cell cardNameCell = row1.getCell(0);  //卡名
	 	            Cell cardNoCell = row1.getCell(1);  //卡号
	 	            Cell productIdCell = row1.getCell(2);  //所属商品id
	 	            Cell isGoodsCell = row1.getCell(3);  //是否特殊
	 	            Cell priceCell = row1.getCell(4);  //售价
	 	            
	 	            int cardNameInt = cardNameCell==null?3:cardNameCell.getCellType();
	 	            int cardNoInt = cardNoCell==null?3:cardNoCell.getCellType();
	 	            int productIdInt = productIdCell==null?3:productIdCell.getCellType();
	 	            int isGoodsInt = isGoodsCell==null?3:isGoodsCell.getCellType();
	 	            int priceInt = priceCell==null?3:priceCell.getCellType();
	 	            
	 	            //数据全部为空，跳出当前循环，直接进行下一步
	 	            if(cardNameInt==3&&cardNoInt==3&&productIdInt==3&&isGoodsInt==3&&priceInt==3){
	 	            	break;
	 	            }
	 	            if(cardNameCell==null||cardNoCell==null||productIdCell==null||isGoodsCell==null||priceCell==null){
	 	            	errorStr = "第"+i+"行有数据为空，请检查后上传";
	 	            	throw new ServiceException(MsgeData.SYSTEM_10104.getCode(),errorStr);
	 	            }
	 	            /** 
	 	             * 为了处理：Excel异常Cannot get a text value from a numeric cell 
	 	             * 将所有列中的内容都设置成String类型格式 
	 	             */  
 	            	cardNameCell.setCellType(Cell.CELL_TYPE_STRING);  
 	            	cardNoCell.setCellType(Cell.CELL_TYPE_STRING);  
 	            	productIdCell.setCellType(Cell.CELL_TYPE_STRING);  
 	            	isGoodsCell.setCellType(Cell.CELL_TYPE_STRING);  
 	            	priceCell.setCellType(Cell.CELL_TYPE_STRING); 
 	            	
 	            	String cardName = cardNameCell.toString().trim();
 	            	String cardNo = cardNoCell.toString().trim();
 	            	String productIdStr = productIdCell.toString().trim();
 	            	String isGoods = isGoodsCell.toString().trim();
 	            	String priceStr = priceCell.toString().trim();
 	            	if(StringUtil.isEmpty(cardNo)|| StringUtil.isEmpty(cardName)|| StringUtil.isEmpty(isGoods)|| StringUtil.isEmpty(productIdStr)|| StringUtil.isEmpty(priceStr)){
 	            		errorStr = "第"+i+"行有数据为空，请检查数据后上传";
	 	            	throw new ServiceException(MsgeData.SYSTEM_10104.getCode(),errorStr);
 	            	}
 	            	//String 转Long
 	            	if(!isNumeric(productIdStr)){
 	            		errorStr = "第"+i+"行《所属商品的id》数据格式错误，请检查数据后上传";
	 	            	throw new ServiceException(MsgeData.SYSTEM_10104.getCode(),errorStr);
 	            	}
 	            	if(!isNumeric(priceStr)){
 	            		errorStr = "第"+i+"行《价格》数据格式错误，请检查数据后上传";
 	            		throw new ServiceException(MsgeData.SYSTEM_10104.getCode(),errorStr);
 	            	}
 	            	if(!isNumeric(isGoods)){
 	            		errorStr = "第"+i+"行《卡号是否特殊》数据格式错误，请检查数据后上传";
 	            		throw new ServiceException(MsgeData.SYSTEM_10104.getCode(),errorStr);
 	            	}
 	            	
            		Long productId = Long.parseLong(productIdStr);
            		Long price = Long.parseLong(priceStr);
 	            	//判断该卡号是否已经添加
            		Card cardOld = cardService.getCardByCode(cardNo);
            		if(cardOld!=null){
            			errorStr = "第"+i+"行《卡号》已经存在，请检查数据后上传";
            			throw new ServiceException(MsgeData.SYSTEM_10104.getCode(),errorStr);
            		}
            		//校验商品
            		Product product = productService.getProductById(productId, false);
            		if(product == null){
            			errorStr = "第"+i+"行所属商品不存在，请检查数据后上传";
            			throw new ServiceException(MsgeData.SYSTEM_10104.getCode(),errorStr);
            		}
            		
 	            	Card card = new Card(null, productId, cardNo, cardName, null, null, 0l, price*100, "普通卡", null, null, null, "0", isGoods, new Date());
 	            	listCard.add(card);
 	        	}  
	        	 cardService.addCardList(listCard);
	        }else{
	        	throw new ServiceException(MsgeData.SYSTEM_10110.getCode());
	        }
		} catch (IOException e) {
			throw new ServiceException(MsgeData.SYSTEM_10110.getCode());
		}
		return  getSuccessObj();
	}
	
	public static boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("[0-9]*"); 
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
	}
	
	/**
	 * 后台一键绑定会员卡，用于解决部分会员线下付款的情况
	 * @param userId 用户会员ID
	 * @param cardNo 会员卡号
	 * @param surname 姓氏
	 * @param name 名
	 * @param price 支付金额
	 * @param recommendPhone 推荐人手机号
	 * @param realName 收件人姓名
	 * @param phone 收件人收货
	 * @param address 收货地址 
	 * @return
	 */
	@RequestMapping("card200006")
	@ResponseBody
	public Json bindCard(
			String userId, String cardNo, String surname, 
			String name, Long price, String recommendPhone,
			String realName, String phone, String address){
		if(StringUtil.isEmpty(userId) 
				|| StringUtil.isEmpty(cardNo) 
				|| price == null 
				|| price <= 0
				|| StringUtil.isEmpty(realName)
				|| StringUtil.isEmpty(phone)
				|| StringUtil.isEmpty(address)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if((!StringUtil.isEmpty(recommendPhone) && !StringUtil.checkPhone(recommendPhone)) 
				|| (!StringUtil.isEmpty(phone) && !StringUtil.checkPhone(phone))){
			throw new ServiceException(MsgeData.PUBLIC_1000010001.getCode());
		}
		//会员卡信息
		Card card = new Card();
		card.setSurname(surname);
		card.setName(name);
		card.setUserId(userId);
		card.setCardNo(cardNo);
		
		//订单信息
		Order order = new Order();
		order.setRealName(realName + "【一键绑卡】");
		order.setPhone(phone);
		order.setAddress(address);
		order.setRecommendPhone(recommendPhone);
		order.setPrice(price);
		order.setSurname(surname);
		order.setName(realName);
		order.setUserId(userId);
		
		return getSuccessObj(cardService.updateKeyWordBindCard(card, order));
	}
 
}
