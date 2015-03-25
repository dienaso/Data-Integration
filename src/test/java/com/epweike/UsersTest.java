package com.epweike;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epweike.model.Users;
import com.epweike.service.UsersService;

public class UsersTest {
	
	@Autowired
	private UsersService usersService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath:applicationContext.xml" });
		usersService = (UsersService) context.getBean("usersService");
	}

	@Test
	public void selectOneTest() {
		Users users = this.usersService.selectOne(new Users("ping6dfgdfg"));
		System.out.println("打印对象：" + users.toString());
	}

}
