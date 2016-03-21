/**
 * Copyright 2010-2015 epweike.com.
 * @Description: TODO
 * @author 吴小平
 * @date Dec 14, 2015 3:29:21 PM 
 * 
 */
package com.epweike.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * Hive的JavaApi
 * 
 * 启动hive的远程服务接口命令行执行：hive --service hiveserver2 >/dev/null 2>/dev/null &
 * 
 * @author wuxp
 * 
 */
public class HiveJdbcCliTest {

	private static String driverName = "org.apache.hive.jdbc.HiveDriver";
	private static String url = "jdbc:hive2://10.0.100.100:10000/default";
	private static String user = "hadoop";
	private static String password = "";
	private static String sql = "";
	private static ResultSet res;
	private static final Logger log = Logger.getLogger(HiveJdbcCliTest.class);

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConn();
			stmt = conn.createStatement();

			String tableName = "logs";

			showTables(stmt, tableName);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error(driverName + " not found!", e);
			System.exit(1);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("Connection error!", e);
			System.exit(1);
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void countData(Statement stmt, String tableName)
			throws SQLException {
		sql = "select count(1) from " + tableName;
		System.out.println("Running:" + sql);
		res = stmt.executeQuery(sql);
		System.out.println("执行“regular hive query”运行结果:");
		while (res.next()) {
			System.out.println("count ------>" + res.getString(1));
		}
	}

	private static void selectData(Statement stmt, String tableName)
			throws SQLException {
		sql = "select * from " + tableName;
		System.out.println("Running:" + sql);
		res = stmt.executeQuery(sql);
		System.out.println("执行 select * query 运行结果:");
		while (res.next()) {
			System.out.println(res.getInt(1) + "\t" + res.getString(2));
		}
	}

	private static void loadData(Statement stmt, String tableName)
			throws SQLException {
		String filepath = "/user/hive/warehouse";
		sql = "load data local inpath '" + filepath + "' into table "
				+ tableName;
		System.out.println("Running:" + sql);
		res = stmt.executeQuery(sql);
	}

	private static void describeTables(Statement stmt, String tableName)
			throws SQLException {
		sql = "describe " + tableName;
		System.out.println("Running:" + sql);
		res = stmt.executeQuery(sql);
		System.out.println("执行 describe table 运行结果:");
		while (res.next()) {
			System.out.println(res.getString(1) + "\t" + res.getString(2));
		}
	}

	private static void showTables(Statement stmt, String tableName)
			throws SQLException {
		sql = "show tables '" + tableName + "'";
		System.out.println("Running:" + sql);
		res = stmt.executeQuery(sql);
		System.out.println("执行 show tables 运行结果:");
		if (res.next()) {
			System.out.println(res.getString(1));
		}
	}

	private static Connection getConn() throws ClassNotFoundException,
			SQLException {
		Class.forName(driverName);
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

}
