/**
 * Copyright 2010-2015 epweike.com.
 * @Description: 
 * @author 吴小平
 * @date Sep 16, 2015 8:41:18 AM 
 * 
 */
package com.epweike.util;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wuxp
 * @param <T>
 *
 */
public class QueryUtils<T> {

	private static Logger logger = LoggerFactory
			.getLogger(QueryUtils.class);

	private Class<?> clazz;
    public QueryUtils(Class<?> clazz) {
    	this.clazz=clazz;
    }

	/**
	 * 查询对象
	 * 
	 * @param sql
	 *            添加的sql语句
	 * @param params
	 *            数据的参数
	 * @param name
	 *            连接池的名字
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T get(String sql, Object[] params, String name)
			throws SQLException {
		log(sql, params, name);
		// 创建SQL执行工具
		QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource(name));
		return (T) qr.query(sql, new BeanHandler(clazz),params);
	}

	/**
	 * 查询集合
	 * 
	 * @param sql
	 *            添加的sql语句
	 * @param params
	 *            数据的参数
	 * @param name
	 *            连接池的名字
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> select(String sql, Object[] params, String dataSourceName)
			throws SQLException {
		log(sql, params, dataSourceName);
		// 创建SQL执行工具
		QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource(dataSourceName));
		// 执行SQL查询，并获取结果
		return (List<T>) qr.query(sql, new BeanListHandler((Class) clazz), params);
	}

	private void log(String sql, Object[] params, String name) {
		logger.info("SQL语句：" + sql + " 检索参数： " + Arrays.toString(params)
				+ " 连接池名字： " + name);
	}
}
