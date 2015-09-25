package com.epweike.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epweike.util.DateUtils;
import com.epweike.util.SolrUtils;
import com.epweike.util.StatUtils;

import net.sf.json.JSONArray;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * @author wuxp
 */
@Controller
public class IndexController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(IndexController.class);	

	public Map<String, Object> getIndexDataModel() throws Exception {       

		Date end = new Date();// 结束时间
		Date start = DateUtils.addDays(end, -31);// 开始时间(31天前)
		// 统计类型(日、月、年)
		String statType = "+1DAY";
		/*
		 * --------------------------------------------------------------
		 * 用户注册渠道统计
		 * --------------------------------------------------------------
		 * */
		SolrServer solr1 = SolrUtils.getSolrServer("talent");
		
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
		response1 = solr1.query(regParams);
		List<Map<String, Object>> regList = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 4, 10);
		response1 = solr1.query(regParams1);
		List<Map<String, Object>> regList1 = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 4, 10);
		response1 = solr1.query(regParams2);
		List<Map<String, Object>> regList2 = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 4, 10);
		response1 = solr1.query(regParams3);
		List<Map<String, Object>> regList3 = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 4, 10);
		response1 = solr1.query(regParams4);
		List<Map<String, Object>> regList4 = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 4, 10);
		response1 = solr1.query(regParams5);
		List<Map<String, Object>> regList5 = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 4, 10);
		response1 = solr1.query(regParams6);
		List<Map<String, Object>> regList6 = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 4, 10);
		response1 = solr1.query(regParams7);
		List<Map<String, Object>> regList7 = StatUtils.getFacetRangeList(
				response1.getFacetRanges(), 4, 10);

		/*
		 * --------------------------------------------------------------
		 * 用户登陆渠道统计
		 * --------------------------------------------------------------
		 * */
		SolrServer solr2 = SolrUtils.getSolrServer("login");
		
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
				response2.getFacetRanges(), 4, 10);
		response2 = solr2.query(loginParams1);
		List<Map<String, Object>> loginList1 = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 4, 10);
		response2 = solr2.query(loginParams2);
		List<Map<String, Object>> loginList2 = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 4, 10);
		response2 = solr2.query(loginParams3);
		List<Map<String, Object>> loginList3 = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 4, 10);
		response2 = solr2.query(loginParams4);
		List<Map<String, Object>> loginList4 = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 4, 10);
		response2 = solr2.query(loginParams5);
		List<Map<String, Object>> loginList5 = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 4, 10);
		response2 = solr2.query(loginParams6);
		List<Map<String, Object>> loginList6 = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 4, 10);
		response2 = solr2.query(loginParams7);
		List<Map<String, Object>> loginList7 = StatUtils.getFacetRangeList(
				response2.getFacetRanges(), 4, 10);
		
		/*
		 * --------------------------------------------------------------
		 * 任务发布数统计
		 * --------------------------------------------------------------
		 * */
		SolrServer solr3 = SolrUtils.getSolrServer("task");
		
		// TOTAL
		SolrQuery taskPubParams = new SolrQuery("*:*").setFacet(true)
				.addDateRangeFacet("pub_time_date", start, end, statType);
		// 单赏
		SolrQuery taskPubParams1 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("model_id:1")
				.addDateRangeFacet("pub_time_date", start, end, statType);
		// 多赏
		SolrQuery taskPubParams2 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("model_id:2")
				.addDateRangeFacet("pub_time_date", start, end, statType);
		// 计件
		SolrQuery taskPubParams3 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("model_id:3")
				.addDateRangeFacet("pub_time_date", start, end, statType);
		// 招标
		SolrQuery taskPubParams4 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("model_id:4").addFilterQuery(
						"task_type:{* TO 2}")
				.addDateRangeFacet("pub_time_date", start, end, statType);
		// 雇佣
		SolrQuery taskPubParams5 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("model_id:4")
				.addFilterQuery("task_type:{* TO 2}")
				.addFilterQuery("task_cash_coverage:0")
				.addDateRangeFacet("pub_time_date", start, end, statType);
		// 服务
		SolrQuery taskPubParams6 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("model_id:4").addFilterQuery(
						"task_type:2")
				.addDateRangeFacet("pub_time_date", start, end, statType);
		// 直接雇佣
		SolrQuery taskPubParams7 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("model_id:4").addFilterQuery(
						"task_type:3")
				.addDateRangeFacet("pub_time_date", start, end, statType);

		QueryResponse response3 = null;
		// 获取各个任务类型统计列表
		response3 = solr3.query(taskPubParams);
		List<Map<String, Object>> taskPubList = StatUtils.getFacetRangeList(
				response3.getFacetRanges(), 0, 10);
		response3 = solr3.query(taskPubParams1);
		List<Map<String, Object>> taskPubList1 = StatUtils.getFacetRangeList(
				response3.getFacetRanges(), 0, 10);
		response3 = solr3.query(taskPubParams2);
		List<Map<String, Object>> taskPubList2 = StatUtils.getFacetRangeList(
				response3.getFacetRanges(), 0, 10);
		response3 = solr3.query(taskPubParams3);
		List<Map<String, Object>> taskPubList3 = StatUtils.getFacetRangeList(
				response3.getFacetRanges(), 0, 10);
		response3 = solr3.query(taskPubParams4);
		List<Map<String, Object>> taskPubList4 = StatUtils.getFacetRangeList(
				response3.getFacetRanges(), 0, 10);
		response3 = solr3.query(taskPubParams5);
		List<Map<String, Object>> taskPubList5 = StatUtils.getFacetRangeList(
				response3.getFacetRanges(), 0, 10);
		response3 = solr3.query(taskPubParams6);
		List<Map<String, Object>> taskPubList6 = StatUtils.getFacetRangeList(
				response3.getFacetRanges(), 0, 10);
		response3 = solr3.query(taskPubParams7);
		List<Map<String, Object>> taskPubList7 = StatUtils.getFacetRangeList(
				response3.getFacetRanges(), 0, 10);
		
		/*
		 * --------------------------------------------------------------
		 * 任务已圆满完成统计
		 * --------------------------------------------------------------
		 * */
		SolrServer solr4 = SolrUtils.getSolrServer("task");
		
		// TOTAL
		SolrQuery taskDoneParams = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("task_status:8")
				.addDateRangeFacet("pub_time_date", start, end, statType);
		// 单赏
		SolrQuery taskDoneParams1 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("task_status:8")
				.addFilterQuery("model_id:1")
				.addDateRangeFacet("pub_time_date", start, end, statType);
		// 多赏
		SolrQuery taskDoneParams2 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("task_status:8")
				.addFilterQuery("model_id:2")
				.addDateRangeFacet("pub_time_date", start, end, statType);
		// 计件
		SolrQuery taskDoneParams3 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("task_status:8")
				.addFilterQuery("model_id:3")
				.addDateRangeFacet("pub_time_date", start, end, statType);
		// 招标
		SolrQuery taskDoneParams4 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("task_status:8")
				.addFilterQuery("model_id:4").addFilterQuery(
						"task_type:{* TO 2}")
				.addDateRangeFacet("pub_time_date", start, end, statType);
		// 雇佣
		SolrQuery taskDoneParams5 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("task_status:8")
				.addFilterQuery("model_id:4")
				.addFilterQuery("task_type:{* TO 2}")
				.addFilterQuery("task_cash_coverage:0")
				.addDateRangeFacet("pub_time_date", start, end, statType);
		// 服务
		SolrQuery taskDoneParams6 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("task_status:8")
				.addFilterQuery("model_id:4").addFilterQuery(
						"task_type:2")
				.addDateRangeFacet("pub_time_date", start, end, statType);
		// 直接雇佣
		SolrQuery taskDoneParams7 = new SolrQuery("*:*").setFacet(true)
				.addFilterQuery("task_status:8")
				.addFilterQuery("model_id:4").addFilterQuery(
						"task_type:3")
				.addDateRangeFacet("pub_time_date", start, end, statType);

		QueryResponse response4 = null;
		// 获取各个任务类型 统计列表
		response4 = solr4.query(taskDoneParams);
		List<Map<String, Object>> taskDoneList = StatUtils.getFacetRangeList(
				response4.getFacetRanges(), 0, 10);
		response4 = solr4.query(taskDoneParams1);
		List<Map<String, Object>> taskDoneList1 = StatUtils.getFacetRangeList(
				response4.getFacetRanges(), 0, 10);
		response4 = solr4.query(taskDoneParams2);
		List<Map<String, Object>> taskDoneList2 = StatUtils.getFacetRangeList(
				response4.getFacetRanges(), 0, 10);
		response4 = solr4.query(taskDoneParams3);
		List<Map<String, Object>> taskDoneList3 = StatUtils.getFacetRangeList(
				response4.getFacetRanges(), 0, 10);
		response4 = solr4.query(taskDoneParams4);
		List<Map<String, Object>> taskDoneList4 = StatUtils.getFacetRangeList(
				response4.getFacetRanges(), 0, 10);
		response4 = solr4.query(taskDoneParams5);
		List<Map<String, Object>> taskDoneList5 = StatUtils.getFacetRangeList(
				response4.getFacetRanges(), 0, 10);
		response4 = solr4.query(taskDoneParams6);
		List<Map<String, Object>> taskDoneList6 = StatUtils.getFacetRangeList(
				response4.getFacetRanges(), 0, 10);
		response4 = solr4.query(taskDoneParams7);
		List<Map<String, Object>> taskDoneList7 = StatUtils.getFacetRangeList(
				response4.getFacetRanges(), 0, 10);
		/*
		 * --------------------------------------------------------------
		 * 用户能力品级统计
		 * --------------------------------------------------------------
		 * */
		SolrQuery wParams = new SolrQuery("*:*").setFacet(true).addFacetField("w_level");
		QueryResponse wResponse = SolrUtils.getSolrServer("talent").query(wParams);
		List<FacetField> wFacetFields = wResponse.getFacetFields(); 
		/*
		 * --------------------------------------------------------------
		 * 用户商铺等级统计
		 * --------------------------------------------------------------
		 * */
		SolrQuery sParams = new SolrQuery("*:*").setFacet(true).addFacetField("shop_level");
		QueryResponse sResponse = SolrUtils.getSolrServer("talent").query(sParams);
		List<FacetField> sFacetFields = sResponse.getFacetFields(); 

		// 返回结果
		Map<String, Object> map = new HashMap<String, Object>();
		//用户注册渠道统计
		map.put("totalReg", JSONArray.fromObject(regList).toString());
		map.put("webReg", JSONArray.fromObject(regList1).toString());
		map.put("cpmReg", JSONArray.fromObject(regList2).toString());
		map.put("appReg", JSONArray.fromObject(regList3).toString());
		map.put("wapReg", JSONArray.fromObject(regList4).toString());
		map.put("mallReg", JSONArray.fromObject(regList5).toString());
		map.put("backgroundReg", JSONArray.fromObject(regList6).toString());
		map.put("yunReg", JSONArray.fromObject(regList7).toString());
		//用户登陆渠道统计
		map.put("totalLogin", JSONArray.fromObject(loginList).toString());
		map.put("webLogin", JSONArray.fromObject(loginList1).toString());
		map.put("appLogin", JSONArray.fromObject(loginList2).toString());
		map.put("wapLogin", JSONArray.fromObject(loginList3).toString());
		map.put("webqqLogin", JSONArray.fromObject(loginList4).toString());
		map.put("appqzoneLogin", JSONArray.fromObject(loginList5).toString());
		map.put("websinaLogin", JSONArray.fromObject(loginList6).toString());
		map.put("otherLogin", JSONArray.fromObject(loginList7).toString());
		//任务发布统计
		map.put("totaltaskPub", JSONArray.fromObject(taskPubList).toString());
		map.put("danshangtaskPub", JSONArray.fromObject(taskPubList1).toString());
		map.put("duoshangtaskPub", JSONArray.fromObject(taskPubList2).toString());
		map.put("jijiantaskPub", JSONArray.fromObject(taskPubList3).toString());
		map.put("zhaobiaotaskPub", JSONArray.fromObject(taskPubList4).toString());
		map.put("guyongtaskPub", JSONArray.fromObject(taskPubList5).toString());
		map.put("fuwutaskPub", JSONArray.fromObject(taskPubList6).toString());
		map.put("zhijieguyongtaskPub", JSONArray.fromObject(taskPubList7).toString());
		//任务托管赏金统计
		map.put("totaltaskDone", JSONArray.fromObject(taskDoneList).toString());
		map.put("danshangtaskDone", JSONArray.fromObject(taskDoneList1).toString());
		map.put("duoshangtaskDone", JSONArray.fromObject(taskDoneList2).toString());
		map.put("jijiantaskDone", JSONArray.fromObject(taskDoneList3).toString());
		map.put("zhaobiaotaskDone", JSONArray.fromObject(taskDoneList4).toString());
		map.put("guyongtaskDone", JSONArray.fromObject(taskDoneList5).toString());
		map.put("fuwutaskDone", JSONArray.fromObject(taskDoneList6).toString());
		map.put("zhijieguyongtaskDone", JSONArray.fromObject(taskDoneList7).toString());
		//用户能力品级柱形图数据
		map.put("wlevel", StatUtils.barJson(wFacetFields));
		//用户商铺等级柱形图数据
		map.put("slevel", StatUtils.barJson(sFacetFields));

		logger.info("getIndexDataModel="+map);
		return map;
	}
	
//	@RequestMapping(value = { "/" })
//	public ModelAndView index() throws Exception {       
//
//		// 返回视图
//		ModelAndView mv = new ModelAndView("/html/home.html");
////		Map<String, Object> map = getIndexDataModel();
////		for (String key : map.keySet()) {
////			System.out.println("key= "+ key + " and value= " + map.get(key));
////			mv.addObject(key, map.get(key));
////	    }
//
//		logger.info("进入主页！！！");
//		return mv;
//	}

}
