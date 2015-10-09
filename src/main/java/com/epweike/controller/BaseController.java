package com.epweike.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;

import com.epweike.model.PageModel;
import com.epweike.util.DateUtils;
import com.epweike.util.SolrUtils;

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
		// 来源(web、iphone、Android等)
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
			name = "基础版本";
			break;
		case "2":
			name = "扩展版";
			break;
		case "3":
			name = "旗舰版";
			break;
		case "4":
			name = "白金版";
			break;
		case "5":
			name = "钻石版";
			break;
		case "6":
			name = "皇冠版";
			break;
		case "7":
			name = "战略合作版";
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

}
