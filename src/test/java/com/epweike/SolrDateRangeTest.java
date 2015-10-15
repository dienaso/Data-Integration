/**
 * Copyright 2010-2015 epweike.com.
 * @Description: TODO
 * @author 吴小平
 * @date Oct 12, 2015 5:25:13 PM 
 * 
 */
package com.epweike;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.epweike.util.DateUtils;
import com.epweike.util.StatUtils;

/**
 * @author Administrator
 *
 */
public class SolrDateRangeTest {

	/**  
	 * @Description:
	 *  
	 * @author  吴小平
	 * @version 创建时间：Oct 12, 2015 5:25:13 PM
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Date end = new Date();// 结束时间
		Date start = DateUtils.addDays(end, -31);// 开始时间(31天前)
		// 统计类型(日、月、年)
		String statType = "+1DAY";
		/*
		 * --------------------------------------------------------------
		 * 用户注册渠道统计
		 * --------------------------------------------------------------
		 */
		SolrServer solr1 = new HttpSolrServer("http://solr2.api.epweike.com/talent");;

		// TOTAL
		SolrQuery regParams = new SolrQuery("*:*").setFacet(true)
				.addDateRangeFacet("reg_time_date", start, end, statType);
		//regParams.setParam(FacetParams.FACET_DATE_INCLUDE, "lower,upper,edge");
		QueryResponse response1 = null;
		response1 = solr1.query(regParams);
		List<Map<String, Object>> regList = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 4, 10);
		System.out.println(regList.toString());

	}

}
