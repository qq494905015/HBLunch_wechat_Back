/**  
 * @Title: ResourceUtil.java
 * @Package: com.bbsoft.common
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-2-9
 */
package com.bbsoft.common.util;

import java.util.ResourceBundle;

/**
 * ClassName: ResourceUtil 
 * @Description: 项目参数工具类
 * @author: VULCAN
 * @date: 2017-2-9
 */
public class ResourceUtil {

	public static final ResourceBundle bundle = ResourceBundle.getBundle("sysConfig");
	/**
	 * @Description: 通过key值取数据
	 * @param: @param key
	 * @param: @return   
	 * @return: String  
	 * @throws
	 * @author: VULCAN
	 * @date: 2017-2-9
	 */
	public static String getKey(String key) {
		return bundle.getString(key);
	}
	
	/**
	 * @Description: 获取上传文件服务器路径
	 * @param: @return  http://bbsoft.cloudtool360.com:8080/sysfile 
	 * @return: String  
	 * @throws
	 * @author: VULCAN
	 * @date: 2017-2-9
	 */
	public static String getFileServerPath(){
		return bundle.getString("fileServerPath");
	}
	
	/**
	 * @Description: 获取上传文件路径
	 * @param: @return  /home/sysfile 
	 * @return: String  
	 * @throws
	 * @author: VULCAN
	 * @date: 2017-2-9
	 */
	public static String getUploadImgPath(){
		return bundle.getString("uploadImgPath");
	}
	
	/**
	 * @Description: 获取验证码超时时间
	 * @param: @return   
	 * @return: Long  
	 * @throws
	 * @author: VULCAN
	 * @date: 2017-2-10
	 */
	public static Long getOverTime(){
		String overTime = bundle.getString("OVERTIME");
		return Long.parseLong(overTime);
	}
	/**
	 * @Description: 获取订单超时时间
	 * @param: @return   
	 * @return: Long  
	 * @throws
	 * @author: VULCAN
	 * @date: 2017-2-10
	 */
	public static Long getOrderOverTime(){
		String overTime = bundle.getString("ORDEROVERTIME");
		return Long.parseLong(overTime);
	}
	/**
	 * @Description: 获取设置自动评价订单时间
	 * @param: @return   
	 * @return: Long  
	 * @throws
	 * @author: VULCAN
	 * @date: 2017-2-10
	 */
	public static Long getOrderOverComfirmTime(){
		String overTime = bundle.getString("ORDERCOMFIRMTIME");
		return Long.parseLong(overTime);
	}
	
	/**
	 * @Description: 获取随机数的个数
	 * @param: @return   
	 * @return: Integer  
	 * @throws
	 * @author: VULCAN
	 * @date: 2017-2-10
	 */
	public static Integer getRandCodeLength(){
		String randCodeLength = bundle.getString("randCodeLength");
		return Integer.parseInt(randCodeLength);
	}
	
	
    /**
     * @Description: 拼接url成为完整路径
     * @param: @param url
     * @param: @return   
     * @return: String  
     * @throws
     * @author: VULCAN
     * @date: 2017-2-15
     */
	public static String covertFullImg(String url){
		if(StringUtil.isEmpty(url)) return "";
		if(url.indexOf("http://") != -1 || url.indexOf("https://") != -1){
			return url;
		}
		return ResourceUtil.getFileServerPath() + url;
	}
	
	/**
	 * @Description: 截取url成为相对路径存数据库
	 * @param: @param url
	 * @param: @return   
	 * @return: String  
	 * @throws
	 * @author: VULCAN
	 * @date: 2017-2-15
	 */
	public static String covertImgDomain(String url){
		if(!StringUtil.isEmpty(url) && url.indexOf(ResourceUtil.getFileServerPath()) != -1){
			url = url.substring(ResourceUtil.getFileServerPath().length(), url.length());
		}
		return url;
	}
	
	public static void main(String[] args) throws Exception {
	 System.out.println(getKey("OVERTIME"));
	}
	
}
