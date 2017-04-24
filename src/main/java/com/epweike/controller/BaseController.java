package com.epweike.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;

import com.epweike.model.PageModel;
import com.epweike.util.DateUtils;
import com.epweike.util.SolrUtils;
import com.epweike.util.StatUtils;

/**  
 * @Description:控制器通用方法类
 *  
 * @author  吴小平
 * @version 创建时间：2014年12月10日 下午2:36:07
 */
/**
 * @author wuxp
 *
 */
public class BaseController {

	/**
	 * @Description:从json数据中解析关键分页参数
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年5月15日 下午2:08:44
	 */
	public <T> PageModel<T> parsePageParamFromJson(String aoData) {

		JSONArray ja = (JSONArray) JSONArray.fromObject(aoData);
		PageModel<T> pageModel = new PageModel<T>();

		for (int i = 0, len = ja.size(); i < len; i++) {
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
		for (int i = 0, len = ja.size(); i < len; i++) {
			JSONObject obj = (JSONObject) ja.get(i);
			if (obj != null && obj.get("name").equals(name) && obj.get("value") != null)
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
		// 来源(web、iphone、Android等)
		String source = getParamFromAodata(aoData, "source");
		// 是否已托管
		String cash_status = getParamFromAodata(aoData, "cash_status");
		// 任务状态
		String task_status = getParamFromAodata(aoData, "task_status");
		// 任务金额
		String task_cash = getParamFromAodata(aoData, "task_cash");

		SolrQuery parameters = new SolrQuery("*:*").setFacet(true)
				.addDateRangeFacet(field, start, end, statType)
				.setFacetLimit(1000);
		if (!task_cash.equals("全部"))
			parameters.addFilterQuery(task_cash);
		if (!source.equals("全部"))
			parameters.addFilterQuery("source:" + source);
		if (!"全部".equals(task_status))
			parameters.addFilterQuery("task_status:" + task_status);
		if (!cash_status.equals("全部")) {
			if (cash_status.equals("未托管")) {
				parameters.addFilterQuery("cash_status:0");
			} else {
				parameters.addFilterQuery("cash_status:{0 TO *}");
			}
		}
		// 过滤任务类型
		String filter = "";
		if (!"".equals(taskType)) {
			String[] types = taskType.split(",");
			for (int i = 0; i < types.length; i++) {
				// 过滤任务类型
				switch (types[i]) {
				case "单赏":
					filter += "(model_id:1) OR ";
					break;
				case "多赏":
					filter += "(model_id:2) OR ";
					break;
				case "计件":
					filter += "(model_id:3) OR ";
					break;
				case "招标":
					filter += "(model_id:4 AND task_type:{* TO 2}) OR ";
					break;
				case "雇佣":
					filter += "(model_id:4 AND task_type:{* TO 2} AND task_cash_coverage:0) OR ";
					break;
				case "服务":
					filter += "(model_id:4 AND task_type:2) OR ";
					break;
				case "直接雇佣":
					filter += "(model_id:4 AND task_type:3) OR ";
				default:
					break;
				}
			}
			filter = filter.substring(0, filter.length() - 3);
		}
		parameters.addFilterQuery(filter);

		// 日期根据统计类型截取
		int endIndex = 10;
		if (statType.contains("YEAR")) {
			endIndex = 4;
		} else if (statType.contains("MONTH")) {
			endIndex = 7;
		}

		QueryResponse response = SolrUtils.getSolrServer(core)
				.query(parameters);
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

	/**
	 * @Description:获取店铺等级中文名称
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午6:34:18
	 */
	public static String getShopLevelName(String shop_level) {

		String name = "";

		switch (shop_level) {
		case "1":
			name = "基础版";
			break;
		case "2":
			name = "VIP拓展";
			break;
		case "3":
			name = "VIP旗舰";
			break;
		case "4":
			name = "VIP白金";
			break;
		case "5":
			name = "VIP钻石";
			break;
		case "6":
			name = "VIP皇冠";
			break;
		case "7":
			name = "金尊皇冠";
			break;
		case "8":
			name = "至尊皇冠";
			break;
		}

		return name;
	}

	/**
	 * @Description:获取威客品级中文名称
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午6:34:18
	 */
	public static String getWLevelName(String w_level) {

		String name = "";

		switch (w_level) {
		case "1":
			name = "九品";
			break;
		case "2":
			name = "八品";
			break;
		case "3":
			name = "七品";
			break;
		case "4":
			name = "六品";
			break;
		case "5":
			name = "五品";
			break;
		case "6":
			name = "四品";
			break;
		case "7":
			name = "三品";
			break;
		case "8":
			name = "二品";
			break;
		case "9":
			name = "一品";
			break;
		}

		return name;
	}

	public List<Map<String, Object>> getFacetList(String core, String facetField, int minCount) {
		SolrQuery params = new SolrQuery("*:*")
		.addFacetField(facetField).setFacetMinCount(minCount);
		
		QueryResponse response = null;
		try {
			response = SolrUtils.getSolrServer(core).query(
					params);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获取统计列表
		List<FacetField> facetFields = response.getFacetFields();
		
		List<Map<String, Object>> list = StatUtils
				.getFacetList(facetFields, "");
		
		return list;
	}
}
