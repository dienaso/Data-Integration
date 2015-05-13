package com.epweike;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epweike.model.Menus;
import com.epweike.service.MenusService;

public class MenusTest {
	
	@Autowired
	private MenusService menusService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath:applicationContext.xml" });
		menusService = (MenusService) context.getBean("menusService");
	}

	@Test
	public void selectTest() {
		List<Menus> menus = this.menusService.select(new Menus());
		System.out.println("打印对象：" + menus.toString());
	}

}
