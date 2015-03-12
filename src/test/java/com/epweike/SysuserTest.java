package com.epweike;

import org.exolab.castor.types.Date;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epweike.model.Sysuser;
import com.epweike.service.SysuserService;
import com.epweike.util.MD5Utils;

public class SysuserTest {
	private static final Logger logger = LoggerFactory.getLogger(SysuserTest.class);
	
	@Autowired
	private SysuserService sysuserService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath:applicationContext.xml" });
		sysuserService = (SysuserService) context.getBean("sysuserService");
	}

//	@Test 
//	public void countTest() {
//		Sysuser user = new Sysuser("wxp", "wxp");
//		Sysuser user2 = (Sysuser) sysuserService.select(user).get(0);
//		System.out.println(user2.toString() + "---COUNT");
//	}
	
	@Test
	public void addTest() {
		for(int i = 0; i < 100; i++){
			Sysuser user = new Sysuser();
			user.setEmail("98375938@qq.com");
			user.setNickName("昵称"+i);
			user.setOnTime(new Date().toString());
			user.setPassword(MD5Utils.getMD5("wxp"));
			user.setUserName("ping"+i);
			user.setStatus("0");
			sysuserService.insert(user);
		}
		
	}

}
