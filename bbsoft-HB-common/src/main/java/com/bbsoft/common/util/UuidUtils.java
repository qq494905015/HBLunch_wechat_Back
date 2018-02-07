package com.bbsoft.common.util;

import java.util.UUID;


public class UuidUtils {


	/**
	 * 
	 * @Description: 生成主键id
	 * @param: @return   
	 * @return: String  
	 * @throws
	 * @author: VULCAN
	 * @date: 2016-12-30
	 */
	public static String getUUid() {
		String uuid = null;
		try {
			uuid = UUID.randomUUID().toString().replaceAll("-", "");
		} catch (Exception ex) {

		}
		return uuid;
	}
	
	public static void main(String[] args) {
		System.out.println(getUUid());//07084816965547a4a3fb79c7d50fd111
	}
 

}
