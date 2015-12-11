/**
 * Copyright 2010-2015 epweike.com.
 * @Description: TODO
 * @author 吴小平
 * @date Sep 17, 2015 3:08:42 PM 
 * 
 */
package com.epweike.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

/**
 * @author wuxp
 *
 */
public class ApplicationListener extends ContextLoaderListener {

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		String webAppRootKey = sce.getServletContext().getRealPath("/");
		System.setProperty("webapp.root", webAppRootKey);
	}

}
