package com.bbsoft.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;

/**
 * 对象转换器
 * ClassName: BeanToMapUtil 
 * @Description: 将对象转换成Map，或者将Map转换成Bean
 */
public class BeanToMapUtil {
	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * 
	 * @param type
	 *            要转化的类型
	 * @param map
	 *            包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InstantiationException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings("rawtypes")
	public static Object convertMap(Class type, Map map)
			throws IntrospectionException, IllegalAccessException,
			InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象
		try {
			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				
				if (map.containsKey(propertyName)) {
					// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
					Object value = map.get(propertyName);
					
					Object[] args = new Object[1];
					args[0] = value;
					if(descriptor.getReadMethod() != null && descriptor.getReadMethod().getGenericReturnType().equals(Date.class)){
						descriptor.getWriteMethod().invoke(obj, DateUtil.formatStringToDate(args[0].toString()));
					}else{
						if(descriptor.getWriteMethod() != null)
							descriptor.getWriteMethod().invoke(obj, args);
					}
				}
			}
		} catch (Exception e) {
			throw new ServiceException(MsgeData.SYSTEM_10107.getCode());
		}
		return obj;
	}
	
	@SuppressWarnings({"rawtypes" })
	public static Map convertBean(Object bean,Boolean passEmpty) {
		return convertBean(bean,passEmpty,null);
	}
	
	@SuppressWarnings({"rawtypes" })
	public static Map convertBean(Object bean){
		return convertBean(bean, false);
	}
	

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean 要转化的JavaBean 对象
	 * @param passEmpty 是否去空格
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings({"rawtypes" })
	public static Map convertBean(Object bean,Boolean passEmpty,List<String> coverList) {
		Map returnMap = new HashMap();
		if(bean == null) return returnMap;
		Class type = bean.getClass();
		if(type == Map.class || type == HashMap.class || type == Hashtable.class){
			Map map = (Map) bean;
			for(Object obj : map.keySet()){
				returnMap = BeanToMapUtil.setMapValue(obj, map.get(obj), returnMap, passEmpty,coverList);
			}
		}else{
			try {
				BeanInfo beanInfo = Introspector.getBeanInfo(type);
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				for (int i = 0; i < propertyDescriptors.length; i++) {
					PropertyDescriptor descriptor = propertyDescriptors[i];
					String propertyName = descriptor.getName();
					if (!propertyName.equals("class")) {
						Method readMethod = descriptor.getReadMethod();
						if(readMethod != null){
							Object result = readMethod.invoke(bean, new Object[0]);
							returnMap = BeanToMapUtil.setMapValue(propertyName, result, returnMap, passEmpty,coverList);
						}
					}
				}
			} catch (Exception e) {
				throw new ServiceException(MsgeData.SYSTEM_10107.getCode());
			}
		}
		return returnMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map setMapValue(Object key, Object value, Map map, Boolean passEmpty,List<String> coverList){
		if(key != null && map != null){
			if(value != null){
				if(value instanceof Date){
					map.put(key, DateUtil.formatDateToString((Date)value, DateUtil.GLOBAL_TIME_PATTERN));
				}else if(value instanceof Double){
					map.put(key, PriceUtil.formatPriceToString(new Double(value.toString())));
				}else if(value instanceof String && coverList != null && coverList.contains(key)){
//					map.put(key,StringUtil.covertFullImg(value.toString()));
				}else{
					map.put(key, value);
				}
			} else if(!passEmpty){
				map.put(key, "");
			}
		}else{
			map = new HashMap();
		}
		return map;
	}
	
	
	/**
	 * 转换list集合的JavaBean转换成Map
	 * @Description: 转换list集合的JavaBean转换成Map
	 * @param: @param list 需要转换的对象
	 * @param: @param passEmpty 是否去空格
	 * @param: @return
	 * @param: @throws IntrospectionException
	 * @param: @throws IllegalAccessException
	 * @param: @throws InvocationTargetException   
	 * @return: List<Map> 转换的list集合
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> convertList(List list, Boolean passEmpty) {
		List<Map> returnList = new ArrayList<Map>();
		if(list != null && list.size() > 0){
			try {
				for(Object bean : list){
					returnList.add(BeanToMapUtil.convertBean(bean, passEmpty,null));
				}
			} catch (Exception e) {
				throw new ServiceException(MsgeData.SYSTEM_10107.getCode());
			}
		}
		return returnList;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> convertList(List list) {
		List<Map> returnList = new ArrayList<Map>();
		if(list != null && list.size() > 0){
			try {
				for(Object bean : list){
					returnList.add(BeanToMapUtil.convertBean(bean, false,null));
				}
			} catch (Exception e) {
				throw new ServiceException(MsgeData.SYSTEM_10107.getCode());
			}
		}
		return returnList;
	}
	
	
	/**
	 * 转换list集合的JavaBean转换成Map
	 * @Description: 转换list集合的JavaBean转换成Map
	 * @param: @param list 需要转换的对象
	 * @param: @param passEmpty 是否去空格
	 * @param: @param coverSet 需要处理的封面图集合
	 * @param: @return 
	 * @param: @throws IntrospectionException
	 * @param: @throws IllegalAccessException
	 * @param: @throws InvocationTargetException   
	 * @return: List<Map> 转换的list集合
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> convertList(List list, Boolean passEmpty,List<String> coverList) {
		List<Map> returnList = new ArrayList<Map>();
		if(list != null && list.size() > 0){
			try {
				for(Object bean : list){
					returnList.add(BeanToMapUtil.convertBean(bean, passEmpty,coverList));
				}
			} catch (Exception e) {
				throw new ServiceException(MsgeData.SYSTEM_10107.getCode());
			}
		}
		return returnList;
	}
	
	
}
