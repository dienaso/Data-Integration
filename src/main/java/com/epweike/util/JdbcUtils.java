/**
 * Copyright 2010-2015 epweike.com.
 * @Description: 
 * @author 吴小平
 * @date Sep 16, 2015 8:41:18 AM 
 * 
 */
package com.epweike.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * @author wuxp
 * @param <T>
 *
 */
public class JdbcUtils<T> {
	
	private static DataSource dataSource = null;
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(SysconfigUtils.getVarValue("driverClass"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(SysconfigUtils.getVarValue("url"), SysconfigUtils.getVarValue("user"),
					SysconfigUtils.getVarValue("password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	   * 获取连接池
	   * @param name 连接池的名字
	 * @throws Exception 
	   * */
	public static DataSource getDataSource(String dataSourceName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, SysconfigUtils.getVarValue("driverClass"));
		map.put(DruidDataSourceFactory.PROP_URL, SysconfigUtils.getVarValue("url"));
		map.put(DruidDataSourceFactory.PROP_USERNAME, SysconfigUtils.getVarValue("user"));
		map.put(DruidDataSourceFactory.PROP_PASSWORD, SysconfigUtils.getVarValue("password"));
		try {
			dataSource = DruidDataSourceFactory.createDataSource(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(dataSource.toString());
		return dataSource;
	}

}
