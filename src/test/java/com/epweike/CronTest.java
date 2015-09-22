/**
 * Copyright 2010-2015 epweike.com.
 * @Description: TODO
 * @author 吴小平
 * @date Sep 22, 2015 10:20:27 AM 
 * 
 */
package com.epweike;

import org.quartz.CronScheduleBuilder;

/**
 * @author Administrator
 *
 */
public class CronTest {

	/**  
	 * @Description:
	 *  
	 * @author  吴小平
	 * @version 创建时间：Sep 22, 2015 10:20:27 AM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CronScheduleBuilder.cronSchedule("0 77 * * * ?");
	}

}
