package com.epweike;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epweike.model.Lexicons;
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
