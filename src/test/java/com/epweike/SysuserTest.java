package com.epweike;

import org.exolab.castor.types.Date;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epweike.model.Users;
import com.epweike.service.UsersService;
import com.epweike.util.MD5Utils;

public class SysuserTest {
	private static final Logger logger = LoggerFactory.getLogger(SysuserTest.class);
	
	@Autowired
	private UsersService sysuserService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath:applicationContext.xml" });
		sysuserService = (UsersService) context.getBean("sysuserService");
	}

//	@Test 
//	public void countTest() {
//		Sysuser user = new Sysuser("wxp", "wxp");
//		Sysuser user2 = (Sysuser) sysuserService.select(user).get(0);
//		System.out.println(user2.toString() + "---COUNT");
//	}
	
	@Test
	public void selectOneTest() {
		Users sysuser = this.sysuserService.selectOne(new Users("ping6"));
		System.out.println("打印对象：" + sysuser.toString());
	}

}
