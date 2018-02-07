package com.bbsoft.common.util;

import java.net.URL;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LocationUtil {

	/**
	 * 根据经纬度获取地理位置信息
	 * @param lng 经度
	 * @param lat 纬度
	 * @return
	 */
	public static String getAdd(String lng, String lat ){  
		String locResult = convertGPS(lng, lat);
		if(!StringUtil.isEmpty(locResult)){
			JSONObject object = JSONObject.fromObject(locResult);
			int error = object.getInt("error");
			if(error == 0){
				JSONObject location = object.getJSONObject("google");
				lng = location.getString("lng");
				lat = location.getString("lat");
			}
		}
        //lat 小  log  大  
        //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)  
        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="+lat+","+lng+"&type=111";  
        String res = "";     
        try {     
            URL url = new URL(urlString);    
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();    
            conn.setDoOutput(true);    
            conn.setRequestMethod("POST");    
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));    
            String line;    
           while ((line = in.readLine()) != null) {    
               res += line+"\n";    
         }    
            in.close();    
        } catch (Exception e) {    
            System.out.println("error in wapaction,and e is " + e.getMessage());    
        }   
        System.out.println(res);  
        return res;    
    }  
	
	/**
	 * 转换GPS为地图经纬度
	 * @param lng 经度
	 * @param lat 纬度
	 * @return
	 */
	public static String convertGPS(String lng, String lat){
        String urlString = "http://map.yanue.net/gpsapi.php?lat=" + lat + "&lng=" + lng;
        String res = "";     
        try {     
            URL url = new URL(urlString);    
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();    
            conn.setDoOutput(true);    
            conn.setRequestMethod("POST");    
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));    
            String line;    
           while ((line = in.readLine()) != null) {    
               res += line+"\n";    
         }    
            in.close();    
        } catch (Exception e) {    
            System.out.println("error in wapaction,and e is " + e.getMessage());    
        }   
        System.out.println(res);  
        return res;    
	}
	
	/**
	 * 其他地图坐标转换成腾讯坐标经纬度，一天最多一万次
	 * @param lng 经度
	 * @param lat 纬度
	 * @param type 转换类型，1 GPS坐标 2 sogou经纬度 3 baidu经纬度 4 mapbar经纬度 5 [默认]腾讯、google、高德坐标 6 sogou墨卡托
	 * @return
	 */
	public static JSONObject convertCoordinate(String lng, String lat, String type){
		if(StringUtil.isEmpty(lng) || StringUtil.isEmpty(lat) || StringUtil.isEmpty(type)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		//腾讯API调用KEY
		String key = "ACFBZ-WEBKD-TSW4A-P24LF-E2SW2-YDBMF";
		String requestUrl = "https://apis.map.qq.com/ws/coord/v1/translate?locations=" + lat + "," + lng + "$type=" + type + "$key=" + key;
		JSONObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, "GET", null);
		if(jsonObject.getInt("status") != 0){
			return null;
		}
		return jsonObject;
	}
	
	public static void main(String[] args) {
		//23.03575,113.1502
        String add = getAdd("113.1502", "23.03575");  
        JSONObject jsonObject = JSONObject.fromObject(add);  
        JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("addrList"));  
        JSONObject j_2 = JSONObject.fromObject(jsonArray.get(0));  
        String allAdd = j_2.getString("admName");  
        String arr[] = allAdd.split(",");  
        System.out.println("省："+arr[0]+"\n市："+arr[1]+"\n区："+arr[2]);  
	}
}
