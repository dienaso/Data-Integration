package com.epweike;


import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epweike.service.LexiconsService;

public class RowBoundsTest {
	
	@Autowired
	private LexiconsService lexiconsService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath:applicationContext.xml" });
		lexiconsService = (LexiconsService) context.getBean("lexiconsService");
	}

}
