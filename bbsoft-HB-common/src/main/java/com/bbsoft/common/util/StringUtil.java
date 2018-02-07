package com.bbsoft.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;

/**
 * 字符串工具类 ClassName: StringUtil
 * 
 * @Description: 字符串工具类
 */

public class StringUtil {
	public static final String ZERO = "0";
	public static final String spot = ".";
	public static final String RESULT = "result";

	/**
	 * 新增操作
	 */
	public static final String ADD = "ADD";

	/**
	 * 删除操作
	 */
	public static final String DELETE = "DELETE";

	/**
	 * 修改操作
	 */
	public static final String UPDATE = "UPDATE";

	/**
	 * 删除状态
	 */
	public static final String STATUS_DELETE = "DELETE";

	/**
	 * 使用状态
	 */
	public static final String STATUS_ACTIVE = "ACTIVE";

	/** 电话格式验证 **/
	@SuppressWarnings("unused")
	private static final String PHONE_CALL_PATTERN = "^(\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}(-\\d{1,4})?$";

	/**
	 * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700
	 **/
	private static final String CHINA_TELECOM_PATTERN = "(^1(33|53|77|8[019])\\d{8}$)|(^1700\\d{7}$)";

	/**
	 * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1709
	 **/
	private static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";

	/**
	 * 中国移动号码格式验证
	 * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184
	 * ,187,188,147,178,1705
	 **/
	private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";

	/**
	 * 判断一个字符是否是中文 @Description: @param: @param c
	 * 需要校验的字节码 @param: @return @return: boolean @throws
	 */
	public static boolean isChinese(char c) {
		return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
	}

	/**
	 * 判断一个字符串是否含有中文 @Description: @param: @param str
	 * 需要校验的字符串 @param: @return @return: boolean @throws
	 */
	public static boolean isChinese(String str) {
		if (str == null)
			return false;
		for (char c : str.toCharArray()) {
			if (isChinese(c))
				return true;// 有一个中文字符就返回
		}
		return false;
	}

	/**
	 * 校验格式是否为邮箱
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkMail(String str) {
		if (str != null && str.indexOf(".") > 0 && str.indexOf("@") > 0 && str.length() - str.indexOf(".") > 1
				&& str.indexOf(".") - str.indexOf("@") > 1) {
			return true;
		}
		return false;
	}

	/**
	 * 校验格式是否为手机号
	 * @param str
	 * @return
	 */
	public static boolean checkPhone(String str){
		boolean result = false;
		if(str != null && str.length() == 11){
			boolean result1 = str == null || str.trim().equals("") ? false : match(CHINA_MOBILE_PATTERN, str);  
			boolean result2 = str == null || str.trim().equals("") ? false : match(CHINA_UNICOM_PATTERN, str);
			boolean result3 = str == null || str.trim().equals("") ? false : match(CHINA_TELECOM_PATTERN, str);
			if(result1 || result2 || result3){
				result = true;
			}
		}
		return result;
	}

	/** 
	* 执行正则表达式 
	* @param pat 表达式 
	* @param str 待验证字符串 
	* @return 返回true,否则为false 
	*/  
	private static boolean match(String pat, String str) {
		Pattern pattern = Pattern.compile(pat);
		Matcher match = pattern.matcher(str);
		return match.find();
	}
	
	/**
	 * 校验身份证
	 * @param cardNo 身份证号码 
	 * @return
	 */
	public static boolean isCard(String cardNo){
		if(StringUtil.isEmpty(cardNo)){
			return false;
		}
		Pattern p15 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
		Pattern p18 = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
		Matcher m15 = p15.matcher(cardNo);
		Matcher m18 = p18.matcher(cardNo);
		return m15.matches() || m18.matches();
	}

	/**
	 * 是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否不为空
	 * 
	 * @param str
	 * @return true：不为空 false：为空
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 是否位数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		if (str != null) {
			Pattern p = Pattern.compile("^\\d+$");
			Matcher m = p.matcher(str);
			return m.matches();
		}
		return false;
	}

	/**
	 * 高级字符串替换
	 * 
	 * @param str
	 * @param map
	 * @return
	 */
	public static String replace(String str, Map<Object, Object> map) {
		for (Object key : map.keySet()) {
			str = str.replace(key.toString(), map.get(key).toString());
		}
		return str;
	}

	/**
	 * 将字符串转字符串数组
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @param splitChar
	 *            分割符
	 * @return
	 */
	public static String[] strConvertArray(String str, String splitChar) {
		if (!StringUtil.isEmpty(str)) {
			if (!isEmpty(splitChar) && str.indexOf(splitChar) != -1) {
				return str.split(splitChar);
			} else {
				return new String[] { str };
			}
		}
		return new String[] {};
	}

	/**
	 * 将String转换成list<Integer>
	 * 
	 * @param str
	 *            字符串
	 * @param splitChar
	 *            分隔符
	 * @return
	 */
	public static List<Integer> strConvertList(String str, String splitChar) {
		String strNub[] = strConvertArray(str, splitChar);
		List<Integer> listIds = new ArrayList<Integer>();
		if (strNub != null && strNub.length > 0) {
			for (int i = 0; i < strNub.length; i++) {
				listIds.add(Integer.valueOf(strNub[i].trim()));
			}
		}
		return listIds;
	}

	/**
	 * 将String转换成list<long>
	 * 
	 * @param str
	 *            字符串
	 * @param splitChar
	 *            分隔符
	 * @return
	 */
	public static List<Long> strCntList(String str, String splitChar) {
		String strNub[] = strConvertArray(str, splitChar);
		List<Long> listIds = new ArrayList<Long>();
		if (strNub != null && strNub.length > 0) {
			for (int i = 0; i < strNub.length; i++) {
				listIds.add(Long.valueOf(strNub[i].trim()));
			}
		}
		return listIds;
	}
	
	/**
	 * 将String转换成list<String>
	 * 
	 * @param str
	 *            字符串
	 * @param splitChar
	 *            分隔符
	 * @return
	 */
	public static List<String> strConvertListString(String str, String splitChar) {
		String strNub[] = strConvertArray(str, splitChar);
		List<String> listIds = new ArrayList<String>();
		if (strNub != null && strNub.length > 0) {
			for (int i = 0; i < strNub.length; i++) {
				listIds.add(strNub[i].trim());
			}
		}
		return listIds;
	}

	/**
	 * 将字符串转成list<Object>集合
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @param object
	 *            需要转换的list集合类型
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public static List<Object> StrConvertList(String str, Class clazz) {
		if (str != null) {
			JSONArray jsonArray = JSONArray.fromObject(str);
			List<Object> list = JSONArray.toList(jsonArray, clazz);
			return list;
		}
		return new ArrayList<Object>();
	}

	/**
	 * 字符串按指定长度截取并替换 @Description: 字符串按指定长度截取并替换 @param str 原字符串 @param length
	 * 截取长度 @param param 被截取部分替换的字符串 @return @return String @throws
	 */
	public static String strSplitReplace(String str, Integer length, String param) {
		if (isEmpty(str) || length == null || length > str.length())
			return str;

		str = str.substring(0, length);
		if (isNotEmpty(param)) {
			str = str + param;
		}

		return str;
	}

	/**
	 * @Description: 截取后缀名
	 * @param: @param
	 *             file
	 * @param: @return
	 * @return: String
	 * @throws @author:
	 *             VULCAN
	 * @date: 2017-2-8
	 */
	public static String getFormatForFile(MultipartFile file) {
		String imageName = file.getOriginalFilename();
		int lastIndex = imageName.lastIndexOf(".");
		return imageName.substring(lastIndex + 1, imageName.length());
	}

	/**
	 * @Description: 生成Token
	 * @param: @return
	 * @return: String
	 * @throws @author:
	 *             VULCAN
	 * @date: 2016-12-30
	 */
	public static String getToken() {
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(30);
		for (int i = 0; i < 30; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	/**
	 * 
	 * @Description: 生成N位随机数
	 * @param: @return
	 * @return: String
	 * @throws @author:
	 *             VULCAN
	 * @date: 2017-2-10
	 */
	public static String getValidCode(Integer codeLength) {
		String validateCode = "";
		if (codeLength == null) {
			return "随机位数没传";
		}
		for (int i = 0; i < codeLength; i++) {
			BigDecimal num = new BigDecimal(Math.random() * 9).setScale(0, BigDecimal.ROUND_HALF_UP);
			validateCode += num;
		}
		return validateCode;
	}

	/**
	 * @Description: 生成订单号
	 * @param: @return
	 * @return: String
	 * @throws @author:
	 *             VULCAN
	 * @date: 2017-3-2
	 */
	public static String getOrderNum() {
		long str = System.currentTimeMillis();
		String strnew = "by-" + String.valueOf(str) + getValidCode(3);
		return strnew;
	}

	/**
	 * 校验字符串是否为Json
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @param isException
	 *            是否报异常错误
	 * @return
	 */
	public static JSONObject isJson(String jsonStr, boolean isException) {
		if (StringUtil.isEmpty(jsonStr)) {
			if (isException) {
				throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
			} else {
				return null;
			}
		}
		try {
			return JSONObject.fromObject(jsonStr);
		} catch (Exception e) {
			if (isException) {
				throw new ServiceException(MsgeData.SYSTEM_10109.getCode(),
						MsgeData.SYSTEM_10109.getMsg() + "【" + jsonStr + "】");
			}
			return null;
		}
	}

	/**
	 * 校验字符串是否为Json
	 * 
	 * @param jsonArrayStr
	 *            json对象数组字符串
	 * @param isException
	 *            是否报异常错误
	 * @return
	 */
	public static JSONArray isJsonArray(String jsonArrayStr, boolean isException) {
		if (StringUtil.isEmpty(jsonArrayStr)) {
			if (isException) {
				throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
			} else {
				return null;
			}
		}
		try {
			return JSONArray.fromObject(jsonArrayStr);
		} catch (Exception e) {
			if (isException) {
				throw new ServiceException(MsgeData.SYSTEM_10109.getCode(),
						MsgeData.SYSTEM_10109.getMsg() + "【" + jsonArrayStr + "】");
			}
			return null;
		}
	}

	public static void main(String[] args) {
//		if((!StringUtil.isEmpty(recommendPhone) && !StringUtil.checkPhone(recommendPhone)) 
//				|| (!StringUtil.isEmpty(phone) && !StringUtil.checkPhone(phone))){
//			throw new ServiceException(MsgeData.PUBLIC_1000010001.getCode());
//		}
		System.out.println(checkPhone("153600032641"));

	}

}
