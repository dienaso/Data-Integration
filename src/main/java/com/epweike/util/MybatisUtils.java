/**
 * 
 */
package com.epweike.util;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtils {
	private static SqlSessionFactory factory = null;

	private MybatisUtils() {
	}

	static {
		try {
			factory = new SqlSessionFactoryBuilder().build(Resources
					.getResourceAsStream("applicationContext.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return factory;
	}

	public static SqlSession getSqlSession() {
		// 其中通过Try 解决读取出问题 却不抛出的问题
		SqlSession session = null;
		try {
			session = factory.openSession();
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e.getMessage());
		}
		return session;
	}

	public static void close(SqlSession session) {
		if (session != null) {
			session.close();
		}
	}
}
