package com.bbsoft.common.util;

import java.text.DecimalFormat;

/**
 * 金额计算工具类
 * ClassName: PriceUtil 
 * @Description: 
 */
public class PriceUtil {

	public static DecimalFormat format = new DecimalFormat("0.00");
	public static Integer defaultMultiple = 100;
	public final static String DEFAULT_PRICE = "0.00"; 
	
	/**
	 * 将金额转换成字符串，切保留格式的两位小数，默认格式为 0.00
	 * @Description: 
	 * @param: @param price 需要转换的金额
	 * @param: @param pattern 格式
	 * @param: @return   
	 * @return: String  
	 * @throws
	 */
	public static String formatPriceToString(Double price, String pattern){
		if(price == null) return DEFAULT_PRICE;
		if(!StringUtil.isEmpty(pattern)){
			DecimalFormat format = new DecimalFormat(pattern);
			return format.format(price);
		}
		return format.format(price);
	}
	
	/**
	 * 将金额转换成字符串，切保留格式的两位小数，默认格式为 0.00
	 * @Description: 
	 * @param: @param price 需要转换的金额
	 * @param: @return   
	 * @return: String  
	 * @throws
	 */
	public static String formatPriceToString(Double price){
		return PriceUtil.formatPriceToString(price, null);
	}
	
	
	/**
	 * 将double类型转成Long--针对金额元转成指定倍数，默认为100
	 * @Description: 
	 * @param: @param price 需要转换的金额
	 * @param: @param defaultMultiple 默认转换的倍数，默认为100
	 * @param: @return   
	 * @return: Long  
	 * @throws
	 */
	public static Long formatPriceToLong(Double price, Integer defaultMultiple){
		if(price == null) return new Long(0);
		DecimalFormat format = new DecimalFormat("0");
		if(defaultMultiple == null) {
			
			return new Long(format.format(price * PriceUtil.defaultMultiple.intValue()));
		
		}else {
			return new Long(format.format(price * defaultMultiple.intValue()));
		}
	}
	
	/**
	 * 将double类型转成Long--针对金额元转成指定倍数，默认为100
	 * @Description: 
	 * @param: @param price 需要转换的金额
	 * @param: @return   
	 * @return: Long  
	 * @throws
	 */
	public static Long formatPriceToLong(Double price){
		return formatPriceToLong(price, null);
	}
}
