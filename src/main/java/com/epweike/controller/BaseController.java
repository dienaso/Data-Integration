package com.epweike.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.epweike.model.PageModel;
import com.epweike.util.DateUtils;

/**  
 * @Description:控制器通用方法类
 *  
 * @author  吴小平
 * @version 创建时间：2014年12月10日 下午2:36:07
 */
/**
 * @author Administrator
 *
 */
public class BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(BaseController.class);

	/*
	 * 从配置文件中读取SOLR配置
	 */
	@Value("#{configProperties['solr1.url']}")
	private String solr1_url;
	@Value("#{configProperties['solr2.url']}")
	private String solr2_url;
	@Value("#{configProperties['solr1.cores']}")
	private String solr1_cores;
	@Value("#{configProperties['solr2.cores']}")
	private String solr2_cores;

	// 最终路由地址
	private String solr_url;

	/**
	 * @Description:连接solr服务器
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月11日 上午9:32:13
	 */
	public SolrServer getSolrServer(String core) {
		logger.info("读取配置文件属性：solr1.url=" + solr1_url);
		logger.info("读取配置文件属性：solr2.url=" + solr1_url);
		logger.info("读取配置文件属性：solr1.cores=" + solr1_cores);
		logger.info("读取配置文件属性：solr2.cores=" + solr2_cores);

		if (solr1_url == null && solr2_url == null) {
			logger.error("solr1.url=" + solr1_url + ";solr2.url=" + solr2_url
					+ "，将使用使用默认值:'http://solr.api.epweike.net/'！！！");
			solr_url = "http://solr.api.epweike.net/";
		} else {
			// core路由
			if (solr1_cores.contains(core)) {
				solr_url = solr1_url;
			} else {
				solr_url = solr2_url;
			}
		}

		SolrServer solr = new HttpSolrServer(solr_url + core);
		logger.info("SOLR URL IS:" + solr_url + core);
		return solr;
	}

	/**
	 * @Description:从json数据中解析关键分页参数
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年5月15日 下午2:08:44
	 */
	public <T> PageModel<T> parsePageParamFromJson(String aoData) {

		JSONArray ja = (JSONArray) JSONArray.fromObject(aoData);
		PageModel<T> pageModel = new PageModel<T>();

		for (int i = 0; i < ja.size(); i++) {
			JSONObject obj = (JSONObject) ja.get(i);
			if (obj.get("name").equals("sEcho"))
				pageModel.setsEcho(obj.get("value").toString());
			if (obj.get("name").equals("iDisplayStart"))
				pageModel.setiDisplayStart(Integer.parseInt(obj.get("value")
						.toString()));
			if (obj.get("name").equals("iDisplayLength"))
				pageModel.setiDisplayLength(Integer.parseInt(obj.get("value")
						.toString()));
			if (obj.get("name").equals("sSearch"))
				pageModel.setsSearch(obj.get("value").toString());
			if (obj.get("name").equals("sColumns"))
				pageModel.setsColumns(obj.get("value").toString());
		}
		return pageModel;
	}
	
	/**
	 * @Description:获取前台传递的自定义参数
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午6:34:18
	 */
	public String getParamFromAodata(String aoData, String name) {

		JSONArray ja = (JSONArray) JSONArray.fromObject(aoData);
		String param = "";
		for (int i = 0; i < ja.size(); i++) {
			JSONObject obj = (JSONObject) ja.get(i);
			if (obj.get("name").equals(name))
				param = obj.get("value").toString();
		}
		return param;
	}
	
	/**
	 * @Description:通用获取任务、稿件统计列表,根据时间分组
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月12日 上午8:47:31
	 */
	public List<Map<String, Object>> getTaskFacetListByTime(String aoData,
			String field, String core) throws Exception {

		// 开始时间
		String startString = getParamFromAodata(aoData, "start");
		Date start = DateUtils.parseDate(startString);
		// 结束时间
		String endString = getParamFromAodata(aoData, "end");
		Date end = DateUtils.parseDateTime(endString + " 23:59:59");
		// 任务类型
		String taskType = getParamFromAodata(aoData, "taskType");
		// 统计类型(日、月、年)
		String statType = getParamFromAodata(aoData, "statType");
		// 来源(web、iphoe、Android等)
		String source = getParamFromAodata(aoData, "source");

		SolrQuery parameters = new SolrQuery("*:*").setFacet(true)
				.addDateRangeFacet(field, start, end, statType)
				.setFacetLimit(1000);
		if (!source.equals("全部"))
			parameters.addFilterQuery("source:" + source);
		// 过滤任务类型
		switch (taskType) {
		case "单赏":
			parameters.addFilterQuery("model_id:1");
			break;
		case "多赏":
			parameters.addFilterQuery("model_id:2");
			break;
		case "计件":
			parameters.addFilterQuery("model_id:3");
			break;
		case "招标":
			parameters.addFilterQuery("model_id:4").addFilterQuery(
					"task_type:{* TO 2}");
			break;
		case "雇佣":
			parameters.addFilterQuery("model_id:4")
					.addFilterQuery("task_type:{* TO 2}")
					.addFilterQuery("task_cash_coverage:0");
			break;
		case "服务":
			parameters.addFilterQuery("model_id:4").addFilterQuery(
					"task_type:2");
			break;
		case "直接雇佣":
			parameters.addFilterQuery("model_id:4").addFilterQuery(
					"task_type:3");
		default:
			break;
		}

		// 日期根据统计类型截取
		int endIndex = 10;
		if (statType.contains("YEAR")) {
			endIndex = 4;
		} else if (statType.contains("MONTH")) {
			endIndex = 7;
		}

		QueryResponse response = getSolrServer(core).query(parameters);
		// 获取区间统计列表
		@SuppressWarnings("rawtypes")
		List<RangeFacet> listFacet = response.getFacetRanges();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (RangeFacet<?, ?> rf : listFacet) {
			List<RangeFacet.Count> listCounts = rf.getCounts();
			for (RangeFacet.Count count : listCounts) {
				System.out.println("RangeFacet:" + count.getValue() + ":"
						+ count.getCount());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("date", count.getValue().substring(0, endIndex));// 日期截取只保留年月日形式
				map.put("count", count.getCount());
				list.add(map);
			}
		}
		return list;
	}

}
