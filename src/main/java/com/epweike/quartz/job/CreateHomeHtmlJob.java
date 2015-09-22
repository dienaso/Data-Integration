/**
 * Copyright 2010-2015 epweike.com.
 * @Description: TODO
 * @author 吴小平
 * @date Sep 16, 2015 3:17:32 PM 
 * 
 */
package com.epweike.quartz.job;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.epweike.controller.IndexController;
import com.epweike.util.FreeMarkertUtils;
import com.epweike.util.PathUtils;

/**
 * @author wuxp
 *
 */
public class CreateHomeHtmlJob extends IndexController {

	/**
	 * 实现execute方法
	 */
	public void execute() {

		long t1 = System.currentTimeMillis();// 计时开始

		Map<String, Object> dataModel = new HashMap<String, Object>();
		// 获取统计数据
		try {
			dataModel = getIndexDataModel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// 输出路径
			String outPath = PathUtils.getWebRootPath() + File.separator
					+ "index.html";
			Writer out = new OutputStreamWriter(new FileOutputStream(outPath),
					"UTF-8");
			FreeMarkertUtils.processTemplate("home.ftl", dataModel, out);
			System.out.println("home.html生成成功!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long t2 = System.currentTimeMillis(); // 计时结束
		// 计时结束
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t2 - t1);
		System.out.println("耗时: " + c.get(Calendar.MINUTE) + "分 "
				+ c.get(Calendar.SECOND) + "秒 " + c.get(Calendar.MILLISECOND)
				+ " 毫秒");
	}

	public static void main(String[] args) {

		CreateHomeHtmlJob chJob = new CreateHomeHtmlJob();
		chJob.execute();

	}

}
