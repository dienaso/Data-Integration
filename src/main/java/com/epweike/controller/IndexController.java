package com.epweike.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.epweike.service.UsersService;
import com.epweike.util.DateUtils;
import com.epweike.util.StatUtils;

import net.sf.json.JSONArray;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wuxp
 */
@Controller
public class IndexController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(IndexController.class);

	@Autowired
	private UsersService sysuserService;

	@RequestMapping(value = { "/" })
	public ModelAndView index() throws Exception {

		Date end = new Date();// 结束时间
		Date start = DateUtils.addDays(end, -10);// 开始时间(20天前)
		// 统计类型(日、月、年)
		String statType = "+1DAY";
		/*
		 * --------------------------------------------------------------
		 * 用户注册渠道统计
		 * --------------------------------------------------------------
		 * */
		SolrServer solr = getSolrServer("talent");
		
		// TOTAL
		SolrQuery regParams = new SolrQuery("*:*").setFacet(true)
				.addDateRangeFacet("reg_time_date", start, end, statType);
				
		// WEB
		SolrQuery regParams1 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("come:WEB")
				.addDateRangeFacet("reg_time_date", start, end, statType);
		// CPM
		SolrQuery regParams2 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("come:cpm")
				.addDateRangeFacet("reg_time_date", start, end, statType);
		// APP
		SolrQuery regParams3 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("come:APP")
				.addDateRangeFacet("reg_time_date", start, end, statType);
		// WAP
		SolrQuery regParams4 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("come:WAP")
				.addDateRangeFacet("reg_time_date", start, end, statType);
		// MALL
		SolrQuery regParams5 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("come:mall")
				.addDateRangeFacet("reg_time_date", start, end, statType);
		// BACKGROUND
		SolrQuery regParams6 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("come:background")
				.addDateRangeFacet("reg_time_date", start, end, statType);
		// YUN
		SolrQuery regParams7 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("come:yun")
				.addDateRangeFacet("reg_time_date", start, end, statType);

		QueryResponse response1 = null;
		// 获取各个注册来源区间统计列表
		response1 = solr.query(regParams);
		List<Map<String, Object>> regList = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 10);
		response1 = solr.query(regParams1);
		List<Map<String, Object>> regList1 = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 10);
		response1 = solr.query(regParams2);
		List<Map<String, Object>> regList2 = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 10);
		response1 = solr.query(regParams3);
		List<Map<String, Object>> regList3 = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 10);
		response1 = solr.query(regParams4);
		List<Map<String, Object>> regList4 = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 10);
		response1 = solr.query(regParams5);
		List<Map<String, Object>> regList5 = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 10);
		response1 = solr.query(regParams6);
		List<Map<String, Object>> regList6 = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 10);
		response1 = solr.query(regParams7);
		List<Map<String, Object>> regList7 = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 10);

		/*
		 * --------------------------------------------------------------
		 * 用户登陆渠道统计
		 * --------------------------------------------------------------
		 * */
		SolrServer solr2 = getSolrServer("login");
		
		// TOTAL
		SolrQuery loginParams = new SolrQuery("*:*").setFacet(true)
				.addDateRangeFacet("on_time", start, end, statType);
		// WEB
		SolrQuery loginParams1 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("login_type:web")
				.addDateRangeFacet("on_time", start, end, statType);
		// APP
		SolrQuery loginParams2 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("login_type:app")
				.addDateRangeFacet("on_time", start, end, statType);
		// WAP
		SolrQuery loginParams3 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("login_type:wap")
				.addDateRangeFacet("on_time", start, end, statType);
		// WEB_QQ
		SolrQuery loginParams4 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("login_type:web_qq")
				.addDateRangeFacet("on_time", start, end, statType);
		// APP_QZone
		SolrQuery loginParams5 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("login_type:app_QZone")
				.addDateRangeFacet("on_time", start, end, statType);
		// WEB_SINA
		SolrQuery loginParams6 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("login_type:web_sina")
				.addDateRangeFacet("on_time", start, end, statType);
		// OTHER
		SolrQuery loginParams7 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("NOT login_type:web_sina")
				.addFilterQuery("NOT login_type:app_QZone")
				.addFilterQuery("NOT login_type:web_qq")
				.addFilterQuery("NOT login_type:app")
				.addFilterQuery("NOT login_type:wap")
				.addFilterQuery("NOT login_type:web")
				.addDateRangeFacet("on_time", start, end, statType);

		QueryResponse response2 = null;
		// 获取各个注册来源区间统计列表
		response2 = solr2.query(loginParams);
		List<Map<String, Object>> loginList = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 10);
		response2 = solr2.query(loginParams1);
		List<Map<String, Object>> loginList1 = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 10);
		response2 = solr2.query(loginParams2);
		List<Map<String, Object>> loginList2 = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 10);
		response2 = solr2.query(loginParams3);
		List<Map<String, Object>> loginList3 = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 10);
		response2 = solr2.query(loginParams4);
		List<Map<String, Object>> loginList4 = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 10);
		response2 = solr2.query(loginParams5);
		List<Map<String, Object>> loginList5 = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 10);
		response2 = solr2.query(loginParams6);
		List<Map<String, Object>> loginList6 = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 10);
		response2 = solr2.query(loginParams7);
		List<Map<String, Object>> loginList7 = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 10);
		
		/*
		 * --------------------------------------------------------------
		 * 任务发布统计
		 * --------------------------------------------------------------
		 * */
		
		/*
		 * --------------------------------------------------------------
		 * 任务接单统计
		 * --------------------------------------------------------------
		 * */

		// 返回视图
		ModelAndView mv = new ModelAndView("home");
		//用户注册渠道统计
		mv.addObject("totalReg", JSONArray.fromObject(regList).toString());
		mv.addObject("webReg", JSONArray.fromObject(regList1).toString());
		mv.addObject("cpmReg", JSONArray.fromObject(regList2).toString());
		mv.addObject("appReg", JSONArray.fromObject(regList3).toString());
		mv.addObject("wapReg", JSONArray.fromObject(regList4).toString());
		mv.addObject("mallReg", JSONArray.fromObject(regList5).toString());
		mv.addObject("backgroundReg", JSONArray.fromObject(regList6).toString());
		mv.addObject("yunReg", JSONArray.fromObject(regList7).toString());
		//用户登陆渠道统计
		mv.addObject("totalLogin", JSONArray.fromObject(loginList).toString());
		mv.addObject("webLogin", JSONArray.fromObject(loginList1).toString());
		mv.addObject("appLogin", JSONArray.fromObject(loginList2).toString());
		mv.addObject("wapLogin", JSONArray.fromObject(loginList3).toString());
		mv.addObject("webqqLogin", JSONArray.fromObject(loginList4).toString());
		mv.addObject("appqzoneLogin", JSONArray.fromObject(loginList5).toString());
		mv.addObject("websinaLogin", JSONArray.fromObject(loginList6).toString());
		mv.addObject("otherLogin", JSONArray.fromObject(loginList7).toString());

		logger.info("进入主页！！！");
		return mv;
	}

}
