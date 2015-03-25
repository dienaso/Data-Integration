package com.epweike.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**  
* @Description:控制器通用方法类
*  
* @author  吴小平
* @version 创建时间：2014年12月10日 下午2:36:07
*/  
public class BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	//页码
	public static int pageNum = 1;
	//每页显示条数
	public static int pageSize = 30;
	
	public static HttpSession getSession() {
		HttpSession session = null;
		try {
		    session = getRequest().getSession();
		}catch (Exception e) {
			logger.error("获取session异常！详细错误："+e);
		}
		return session;
	}

	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();    
	}

}
