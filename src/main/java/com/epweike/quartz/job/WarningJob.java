/**
 * Copyright 2010-2015 epweike.com.
 * @Description: TODO
 * @author 吴小平
 * @date Sep 16, 2015 3:17:32 PM 
 * 
 */
package com.epweike.quartz.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.epweike.controller.BaseController;
import com.epweike.util.DateUtils;
import com.epweike.util.StatUtils;

/**
 * @author wuxp
 *
 */
public class WarningJob extends BaseController {
	
	/**
	 * 实现execute方法
	 * @throws Exception 
	 */
	public void execute() throws Exception {

		long t1 = System.currentTimeMillis();// 计时开始

		Date end = new Date();// 结束时间
		Date start = DateUtils.addDays(end, -2);// 开始时间(2天前)
		// 统计类型(日、月、年)
		String statType = "+1DAY";
		/*
		 * --------------------------------------------------------------
		 * 用户注册统计
		 * --------------------------------------------------------------
		 */
		String urlString = "http://solr2.api.epweike.com:8080/talent";
		SolrServer solr1 = new HttpSolrServer(urlString);

		// TOTAL
		SolrQuery regParams = new SolrQuery("*:*").setFacet(true)
				.addDateRangeFacet("reg_time_date", start, end, statType);
		QueryResponse response = null;
		response = solr1.query(regParams);
		List<Map<String, Object>> regList = StatUtils.getFacetRangeList(
				response.getFacetRanges(), 4, 10);
		
		System.out.println(regList.toString());
		

		long t2 = System.currentTimeMillis(); // 计时结束
		// 计时结束
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t2 - t1);
		System.out.println("耗时: " + c.get(Calendar.MINUTE) + "分 "
				+ c.get(Calendar.SECOND) + "秒 " + c.get(Calendar.MILLISECOND)
				+ " 毫秒");
	}

	public static void main(String[] args) throws Exception {

		WarningJob chJob = new WarningJob();
		chJob.execute();

	}

}
