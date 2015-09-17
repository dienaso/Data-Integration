/**
 * Copyright 2010-2015 epweike.com.
 * @Description: TODO
 * @author 吴小平
 * @date Sep 16, 2015 3:17:32 PM 
 * 
 */
package com.epweike.quartz.job;

import java.util.HashMap;
import java.util.Map;

import com.epweike.controller.IndexController;
import com.epweike.util.FreeMarkertUtils;

/**
 * @author wuxp
 *
 */
public class CreateHomeHtmlJob extends IndexController {

	/**
	 * Job，Job需要一个公有的构造函数，否则Factory无法构建
	 * 
	 * @return
	 */
	public CreateHomeHtmlJob() {
	}

	/**
	 * 实现execute方法
	 */
	public void execute() {
		Map<String, Object> dataModel = new HashMap<String, Object>();
		// 获取统计数据
		try {
			dataModel = getIndexDataModel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			FreeMarkertUtils.processTemplate("home.ftl", dataModel, "home.html");
			System.out.println("home.html生成成功!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		CreateHomeHtmlJob chJob = new CreateHomeHtmlJob();
		chJob.execute();
		
	}

}
