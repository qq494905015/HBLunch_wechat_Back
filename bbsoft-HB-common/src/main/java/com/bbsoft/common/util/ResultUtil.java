package com.bbsoft.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;


/**
 * 返回结果处理 ClassName: ResultUtil
 * 
 * @Description: 针对规范的返回结果的处理
 */
public class ResultUtil {

	// 返回结果的Map
	private static Map<String, Object> resultMap = new HashMap<String, Object>();

	/**
	 * 返回成功的结果，不带参数
	 * 
	 * @Description: 只返回code和msg
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	public static Object returnResult() {
		JSONObject resultStr = JSONObject.fromObject(resultMap);
		resultMap = new HashMap<String, Object>();// 重置
		return resultStr.get(StringUtil.RESULT);
	}

	/**
	 * 返回成功结果，且将对于参数放入key为data的Map中
	 * 
	 * @Description: 返回成功结果，且将对于参数放入key为data的Map中
	 * @param: @param object 存入返回结果的值
	 * @param: @return
	 * @return: Map<String,Object>
	 * @throws
	 */
	public static Object returnByObj(Object object) {
		return returnByObj(object, false);
	}

	/**
	 * 返回自定义参数结果
	 * 
	 * @Description:
	 * @param: @param object 存入返回结果的值
	 * @param: @param isSelfKey
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static Object returnByObj(Object object, Boolean isSelfKey) {
		resultMap = new HashMap<String, Object>();
		if (object != null) {
			if ((object.getClass() == Map.class || object.getClass() == HashMap.class)&& isSelfKey) {
				Map<String, Object> paramMap = (Map<String, Object>) object;
				for (String key : paramMap.keySet()) {
					resultMap.put(key, paramMap.get(key));
				}
				resultMap.remove(StringUtil.RESULT);
			} else {
				resultMap.put(StringUtil.RESULT,BeanToMapUtil.convertBean(object, false));
			}
		}
		return returnResult();
	}

	/**
	 * @Description: 将list转为JSONObject
	 * @param: @param list
	 * @param: @return
	 * @return: JSONObject
	 * @throws
	 * @author: VULCAN
	 * @date: 2016-12-27
	 */
	@SuppressWarnings("rawtypes")
	public static Object returnByList(List list) {
		List<Map> listMap = BeanToMapUtil.convertList(list, false);
		resultMap.put(StringUtil.RESULT, listMap);
		return returnResult();
	}

	/**
	 * @Description: 扩展需要特殊处理的数据
	 * @param: @param list
	 * @param: @param picList
	 * @param: @return
	 * @return: JSONObject
	 * @throws
	 * @author: VULCAN
	 * @date: 2016-12-27
	 */
	@SuppressWarnings("rawtypes")
	public static Object resultByList(List list, List<String> picList) {
		List<Map> listMap = BeanToMapUtil.convertList(list, false, picList);
		resultMap.put(StringUtil.RESULT, listMap);
		return returnResult();
	}
}
