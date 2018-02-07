/**  
 * @Title: MysqlConnectUtil.java
 * @Package: com.haier.diy.common.utils
 * @Description: TODO
 * @author: VULCAN
 * @date: 2016-11-23
 */
package com.bbsoft.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ClassName: MysqlConnectUtil 
 * @Description: TODO
 * @author: VULCAN
 * @date: 2016-11-23
 */
public class MysqlConnectUtil {
	
	private static Log log = LogFactory.getLog(MysqlConnectUtil.class);
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载mysql驱动程序
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行数据库更新操作
	 * @param sql 需要执行的SQL
	 * @return
	 */
	public static int executeUpdateSQL(String sql){
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		int result = 0;// 返回受影响行数
		try {
			System.out.println("开始尝试连接数据库！");
			con = DriverManager.getConnection(
					ResourceUtil.getKey("DATABASE_URL"),
					ResourceUtil.getKey("DATABASE_USERNAME"),
					ResourceUtil.getKey("DATABASE_PASSWORD"));// 获取连接
			System.out.println("连接成功！");
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeUpdate();// 执行更新，注意括号中不需要再加参数

		} catch (Exception e) {
			log.debug("MySqlUtil executeSQL 异常:"+e.getMessage(),e.fillInStackTrace());
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源, 注意关闭的顺序，最后使用的最先关闭
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				System.out.println("数据库连接已关闭！");
			} catch (Exception e) {
				log.debug("MySqlUtil 关闭连接 异常:"+e.getMessage(),e.fillInStackTrace());
			}
		}
		return result;
	}
		
	/**
	 * 一个非常标准的连接Oracle数据库的示例代码
	 */
	public static List<Map<String,Object>> executeSQL(String sql) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();;
		try {
			System.out.println("开始尝试连接数据库！");
			con = DriverManager.getConnection(
					ResourceUtil.getKey("DATABASE_URL"),
					ResourceUtil.getKey("DATABASE_USERNAME"),
					ResourceUtil.getKey("DATABASE_PASSWORD"));// 获取连接
			System.out.println("连接成功！");
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			listMap = resultSetToList(result);

		} catch (Exception e) {
			log.debug("MySqlUtil executeSQL 异常:"+e.getMessage(),e.fillInStackTrace());
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源, 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				System.out.println("数据库连接已关闭！");
			} catch (Exception e) {
				log.debug("MySqlUtil 关闭连接 异常:"+e.getMessage(),e.fillInStackTrace());
			}
		}
		return listMap;
	}
	
	public static List<Map<String,Object>> resultSetToList(ResultSet rs) throws java.sql.SQLException {   
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();   
		Map<String,Object> rowData = new HashMap<String,Object>();   
       if(rs!=null){
    	   ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等   
    	   int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数
           while (rs.next()){ 
        		rowData = new HashMap<String,Object>(columnCount);   
        		for (int i = 1; i <= columnCount; i++) {   
        			rowData.put(md.getColumnLabel(i), rs.getObject(i));   
        		}   
        		listMap.add(rowData);   
           }
       }
        return listMap;   
	}  
	
	public static void main(String[] args) {
//		String sql ="INSERT INTO pre_common_member(email, username, password, regdate) VALUES('',15813878509,'b3dcbdcb06b44e50a82e688e66d13c48',1491372392); INSERT INTO pre_ucenter_members(username, password, email, regdate, salt) VALUES(15813878509,'b3dcbdcb06b44e50a82e688e66d13c48','',1491372392,560804);";
//		int result = MysqlConnectUtil.executeUpdateSQL(sql);
//		System.out.println(" 执行更新操作所受影响的行数：" + result);
	}
}
