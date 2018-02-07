package com.bbsoft.wechat.controller.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.common.constant.GlobalMail;
import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ConsumeRecordExcel;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.BeanToMapUtil;
import com.bbsoft.common.util.DateUtil;
import com.bbsoft.common.util.ExcelUtil;
import com.bbsoft.common.util.LocationUtil;
import com.bbsoft.common.util.PageUtil;
import com.bbsoft.common.util.PriceUtil;
import com.bbsoft.common.util.ResourceUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.business.domain.Card;
import com.bbsoft.core.business.domain.Shop;
import com.bbsoft.core.business.domain.ShopTotal;
import com.bbsoft.core.business.service.CardService;
import com.bbsoft.core.business.service.ShopService;
import com.bbsoft.core.business.service.ShopTotalService;
import com.bbsoft.core.user.domain.BaseUser;
import com.bbsoft.core.user.domain.ConsumeRecord;
import com.bbsoft.core.user.service.BaseUserService;
import com.bbsoft.core.user.service.ConsumeRecordService;
import com.bbsoft.wechat.controller.BaseController;

import net.sf.json.JSONObject;

/**
 * 店铺接口
 * @author Chris.Zhang
 * @date 2017-5-24 10:58:23
 *
 */
@Controller
public class ShopController extends BaseController{
	
	@Autowired
	private ShopService shopService;
	@Autowired
	private ConsumeRecordService consumeRecordService;
	@Autowired
	private BaseUserService baseUserService;
	@Autowired
	private CardService cardService;
	@Autowired
	private ShopTotalService shopTotalService;

	/**
	 * 获取附近门店信息
	 * @param request
	 * @param lng 经度
	 * @param lat 纬度
	 * @return
	 */
	@RequestMapping("shop100000")
	@ResponseBody
	public Json getNearShops(HttpServletRequest request, String lng, String lat){
		Map<String, Object> map = new HashMap<String, Object>();
		String locResult = LocationUtil.convertGPS(lng, lat);
		if(!StringUtil.isEmpty(locResult)){
			JSONObject object = JSONObject.fromObject(locResult);
			int error = object.getInt("error");
			if(error == 0){
				JSONObject location = object.getJSONObject("google");
				lng = location.getString("lng");
				lat = location.getString("lat");
			}
		}
		map.put("lng", StringUtil.isEmpty(lng) ? null : lng);
		map.put("lat", StringUtil.isEmpty(lat) ? null : lat);
		map.put("openId", getOpenId(request));
		List<Map<String, Object>> shops = shopService.getNearShops(map);
		return getSuccessObj(ResultUtil.returnByList(shops));
	}
	
	/**
	 * 获取指定门店信息 
	 * @param request
	 * @param id 门店ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("shop100001")
	@ResponseBody
	public Json getShopById(HttpServletRequest request, String id){
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		Shop shop = shopService.getShopById(id, true);
		Map<String, Object> resultMap = BeanToMapUtil.convertBean(shop);
		if(resultMap.get("description") != null){
			String description = resultMap.get("description").toString();
			if(!StringUtil.isEmpty(description) && description.length() > 0){
				resultMap.put("imgList", description.split(","));
			}
		}
		return getSuccessObj(ResultUtil.returnByObj(resultMap));
	}
	
	/**
	 * 获取当前登录用户的门店财务报表
	 * @param request
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @param startTime 搜索开始时间
	 * @param endTime 搜索结束时间
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("shop100002")
	@ResponseBody
	public Json getShopConsumeTotal(HttpServletRequest request, 
			Integer pageNum, Integer pageSize, String startTime, String endTime){

		if(!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)){
			Date start = DateUtil.formatStringToDate(startTime, DateUtil.GLOBAL_DATE_PATTERN);
			Date end = DateUtil.formatStringToDate(endTime, DateUtil.GLOBAL_DATE_PATTERN);
			if(start.getTime() > end.getTime()){
				throw new ServiceException(MsgeData.PUBLIC_1000010017.getCode());
			}
		}
		BaseUser baseUser = baseUserService.getUserById(getUserId(request), true);
		//校验当前用户是否店铺管理员
		checkIsShop(baseUser);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", StringUtil.isEmpty(startTime) ? null : startTime + " 00:00:00");
		map.put("endTime", StringUtil.isEmpty(endTime) ? null : endTime + " 23:59:59");
		map.put("shopId", baseUser.getShopId());
		//获取消费总记录数和消费总金额
		Map<String, Object> totalMap = consumeRecordService.getConsumeRecord(map);
		PageUtil<Map> page = new PageUtil<Map>(pageNum, pageSize, Integer.parseInt(totalMap.get("count").toString()));
		map.put("startItem", page.getStartItem());
		map.put("pageSize", page.getPageSize());
		List<Map<String, Object>> consumeRecords = consumeRecordService.getConsumeRecordMap(map);
		Map<String, Object> resultMap = BeanToMapUtil.convertBean(page);
		resultMap.put("items", consumeRecords);
		resultMap.put("consumeTotal", totalMap.get("total"));
		return getSuccessObj(ResultUtil.returnByObj(resultMap));
	}
	
	/**
	 * 商户收费
	 * @param request
	 * @param money 收费金额
	 * @param codeId 缓存中的二维码唯一标识，有效时间2分钟
	 * @return
	 */
	@RequestMapping("shop100003")
	@ResponseBody
	public Json charge(HttpServletRequest request, Long money, String codeId){
		if(money == null || money <= 0 || StringUtil.isEmpty(codeId)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		String userId = getUserId(request);
		BaseUser baseUser = baseUserService.getUserById(userId, true);
		Map<String, Object> codeMap = codeCacheMap.get(codeId);
		Long createTime = Long.parseLong(codeMap.get("createTime").toString());
		if(System.currentTimeMillis() - createTime > (2 * 60 * 1000)){
			throw new ServiceException(MsgeData.CARD_1000030021.getCode());
		}
		Card card = cardService.getCardByCode(codeMap.get("cardNo").toString());
		if(!"4".equals(card.getStatus())){
			throw new ServiceException(MsgeData.CARD_1000030016.getCode());
		}
		//校验当前用户是否店铺管理员
		checkIsShop(baseUser);
		//新增消费记录
		ConsumeRecord consumeRecord = new ConsumeRecord(card.getUserId(), baseUser.getShopId(), card.getId(), money);
		consumeRecord.setFromType("3");//消费来源（1：会员卡，2：充值，3：消费）
		consumeRecordService.addConsumeRecord(consumeRecord);
		return getSuccessObj();
	}
	
	/**
	 * 导出报表并以邮件发送
	 * @param request
	 * @param mail 邮件地址
	 * @param searchTime 查询时间
	 * @return
	 */
	@RequestMapping("shop100004")
	@ResponseBody
	public Json exportIncome(HttpServletRequest request, String mail, String startTime, String endTime){
		if(StringUtil.isEmpty(mail) || !StringUtil.checkMail(mail)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		if(!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)){
			if(DateUtil.formatStringToDate(startTime, DateUtil.GLOBAL_DATE_PATTERN).getTime() > DateUtil.formatStringToDate(endTime, DateUtil.GLOBAL_DATE_PATTERN).getTime()){
				throw new ServiceException(MsgeData.PUBLIC_1000010017.getCode());
			}
		}
		BaseUser baseUser = baseUserService.getUserById(getUserId(request), true);
		//校验当前用户是否店铺管理员
		Shop shop = checkIsShop(baseUser);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", StringUtil.isEmpty(startTime) ? null : startTime + " 00:00:00");
		map.put("endTime", StringUtil.isEmpty(endTime) ? null : endTime + " 23:59:59");
		map.put("shopId", baseUser.getShopId());
		map.put("startItem", 0);
		map.put("pageSize", 999999999);
		List<Map<String, Object>> consumeRecords = consumeRecordService.getConsumeRecordMap(map);
		if(consumeRecords != null && consumeRecords.size() > 0){
			List<ConsumeRecordExcel> list = new ArrayList<ConsumeRecordExcel>();
			for(Map<String, Object> consumeRecord : consumeRecords){
				ConsumeRecordExcel recordExcel = new ConsumeRecordExcel();
				Object money = consumeRecord.get("money");
				Object phone = consumeRecord.get("phone");
				Object realName = consumeRecord.get("realName");
				if(money != null){
					consumeRecord.put("money", PriceUtil.formatPriceToString(Long.parseLong(money.toString()) * 0.01));
				}
				if(phone == null){
					consumeRecord.put("phone", "未填写");
				}
				if(realName == null){
					consumeRecord.put("realName", "未填写");
				}
				recordExcel.setMoney(consumeRecord.get("money").toString());
				recordExcel.setRealName(consumeRecord.get("realName").toString());
				recordExcel.setPhone(consumeRecord.get("phone").toString());
				list.add(recordExcel);
			}
			OutputStream out;
			String timeStr = "";
			try {
				if(!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)){
					timeStr = startTime + "-" + endTime;
				}
				String filePath = ResourceUtil.getUploadImgPath()+ "/upload/excel/" ;
				String fileName = shop.getName() + timeStr + "_income.xls";
				File outFile = new File(filePath);
				if (!outFile.exists()) {
					outFile.mkdirs();
				}
				out = new FileOutputStream(filePath + fileName);
				LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
				fieldMap.put("realName", "姓名");
				fieldMap.put("phone", "手机号");
				fieldMap.put("money", "消费金额");
				ExcelUtil.listToExcel(list, fieldMap, "报表", out);
				GlobalMail globalMail = new GlobalMail();
				globalMail.init("smtp.163.com", "18002203173@163.com", "18002203173@163.com", "wanwe123");
				globalMail.sendMail(new String[]{mail}, shop.getName() + timeStr + "-财务报表", "附件是门店：" + shop.getName() + timeStr + "的财务报表，请注意查收！", new File(filePath + fileName));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			//修改商户提交的账单信息
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("startTime", StringUtil.isEmpty(startTime) ? null : startTime);
			searchMap.put("endTime", StringUtil.isEmpty(endTime) ? null : endTime);
			List<ShopTotal> totals = shopTotalService.getTotalByPage(map);
			shopTotalService.updateShopTotals(totals);
		}
		return getSuccessObj();
	}
	
	public static void main(String[] args) {
		OutputStream out;
		try {
			String filePath = "D:\\income.xls" ;
			String fileName = "_income.xls";
			File outFile = new File(filePath);
			if (!outFile.exists()) {
				outFile.mkdirs();
			}
			out = new FileOutputStream(filePath + "\\" + fileName);
			LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
			fieldMap.put("realName", "姓名");
			fieldMap.put("phone", "手机号");
			fieldMap.put("money", "消费金额");
			List<ConsumeRecordExcel> consumeRecords = new ArrayList<ConsumeRecordExcel>();
			ConsumeRecordExcel consumeRecordExcel = new ConsumeRecordExcel();
			consumeRecordExcel.setPhone("15813878509");
			consumeRecordExcel.setRealName("张三");
			consumeRecordExcel.setMoney("1.00");
			consumeRecords.add(consumeRecordExcel);
			ExcelUtil.listToExcel(consumeRecords, fieldMap, "报表", out);
			GlobalMail globalMail = new GlobalMail();
			globalMail.init("smtp.163.com", "18002203173@163.com", "18002203173@163.com", "wanwe123");
			globalMail.sendMail(new String[]{"296108631@qq.com"}, "财务报表", "附件是门店：的财务报表，请注意查收！", new File(filePath + "\\" + fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取当前登录的店铺管理员信息
	 * @param request 
	 * @return
	 */
	@RequestMapping("shop100005")
	@ResponseBody
	public Json getShopUser(HttpServletRequest request){
		String userId = getUserId(request);
		BaseUser baseUser = baseUserService.getUserById(userId, true);
		checkIsShop(baseUser);
		return getSuccessObj(ResultUtil.returnByObj(baseUser));
	}
	
	/**
	 * 根据经纬度获取地理位置信息
	 * @param request
	 * @param lng 经度
	 * @param lat 纬度
	 * @return
	 */
	@RequestMapping("shop100006")
	@ResponseBody
	public Json getLocationAdd(HttpServletRequest request, String lng, String lat){
		if(StringUtil.isEmpty(lng) || StringUtil.isEmpty(lat)){
			throw new ServiceException(MsgeData.SYSTEM_10129.getCode());
		}
		String add = LocationUtil.getAdd(lng, lat);
		return getSuccessObj(JSONObject.fromObject(add));
	}
	
	/**
	 * 校验是否是店铺管理员
	 * @param userId 用户ID
	 */
	private Shop checkIsShop(BaseUser baseUser){
		if(StringUtil.isEmpty(baseUser.getShopId()) || "0".equals(baseUser.getShopId())){
			throw new ServiceException(MsgeData.USER_1000020013.getCode());
		}
		Shop shop = shopService.getShopById(baseUser.getShopId(), true);
		if("0".equals(shop.getIsOnline())){
			throw new ServiceException(MsgeData.USER_1000020014.getCode());
		}
		return shop;
	}
}
