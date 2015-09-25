package com.epweike.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.epweike.model.PageModel;
import com.epweike.util.SolrUtils;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.StatsParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wuxp
 */
@Controller
public class TaskController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(TaskController.class);

	private static DecimalFormat df = new DecimalFormat("######0.000");// 保留3位

	/**
	 * @Description:任务来源统计列表（按时间）
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月9日 下午5:29:08
	 */
	@RequestMapping(value = "/task/date/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getTaskFacetByDate(HttpServletRequest request)
			throws Exception {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info(aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);
		// facet统计列表
		List<Map<String, Object>> list = getTaskFacetListByTime(aoData,
				"pub_time_date", "task");
		// 搜索结果数
		pageModel.setiTotalDisplayRecords(list.size());
		pageModel.setiTotalRecords(list.size());
		pageModel.setAaData(list);
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取任务统计列表！！！" + json);

		return json.toString();
	}

	/**
	 * @Description:任务来源统计列表（按分类）
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月9日 下午5:29:08
	 */
	@RequestMapping(value = "/task/indus/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getTaskFacetByIndus(HttpServletRequest request)
			throws Exception {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info("aoData:" + aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);

		// 发布时间
		String pub_start = getParamFromAodata(aoData, "pub_start");
		pub_start = (!"".equals(pub_start)) ? pub_start + "T00:00:00Z" : "*";
		String pub_end = getParamFromAodata(aoData, "pub_end");
		pub_end = (!"".equals(pub_end)) ? pub_end + "T23:59:59Z" : "*";
		// 托管时间
		String cash_start = getParamFromAodata(aoData, "cash_start");
		cash_start = (!"".equals(cash_start)) ? cash_start + "T00:00:00Z" : "*";
		String cash_end = getParamFromAodata(aoData, "cash_end");
		cash_end = (!"".equals(cash_end)) ? cash_end + "T23:59:59Z" : "*";
		// 任务类型
		String taskType = getParamFromAodata(aoData, "taskType");
		// 来源(web、iphone、Android等)
		String source = getParamFromAodata(aoData, "source");
		// 是否已托管
		String cash_status = getParamFromAodata(aoData, "cash_status");
		// 分类名称
		String indus_name = getParamFromAodata(aoData, "indus_name");

		SolrQuery params = new SolrQuery("*:*");
		// 过滤掉计件任务
		// params.addFilterQuery("NOT model_id:3");
		// 过滤任务类型
		switch (taskType) {
		case "单赏":
			params.addFilterQuery("model_id:1");
			break;
		case "多赏":
			params.addFilterQuery("model_id:2");
			break;
		case "计件":
			params.addFilterQuery("model_id:3");
			break;
		case "招标":
			params.addFilterQuery("model_id:4").addFilterQuery(
					"task_type:{* TO 2}");
			break;
		case "雇佣":
			params.addFilterQuery("model_id:4")
					.addFilterQuery("task_type:{* TO 2}")
					.addFilterQuery("task_cash_coverage:0");
			break;
		case "服务":
			params.addFilterQuery("model_id:4").addFilterQuery(
					"task_type:2");
			break;
		case "直接雇佣":
			params.addFilterQuery("model_id:4").addFilterQuery(
					"task_type:3");
		default:
			break;
		}
		params.addFilterQuery("pub_time_date:[" + pub_start + " TO "
				+ pub_end + "]");
		params.addFilterQuery("cash_time_date:[" + cash_start + " TO "
				+ cash_end + "]");
		if (!"".equals(indus_name))
			params.addFilterQuery("indus_name:" + indus_name);
		params.setGetFieldStatistics(true);
		params.setParam(StatsParams.STATS_FIELD, "task_cash");
		params.setParam(StatsParams.STATS_FACET, "indus_name");

		if (!source.equals("全部"))
			params.addFilterQuery("source:" + source);

		if (!cash_status.equals("全部")) {
			if (cash_status.equals("未托管")) {
				params.addFilterQuery("cash_status:0");
			} else {
				params.addFilterQuery("cash_status:{0 TO *}");
			}
		}

		// 查询统计任务报表
		QueryResponse response = SolrUtils.getSolrServer("task").query(params);

		Map<String, Object> tmp1 = new HashMap<String, Object>();
		Map<String, FieldStatsInfo> stats = response.getFieldStatsInfo();
		FieldStatsInfo statsInfo = stats.get("task_cash");// 任务总金额
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// 返回结果集
		if (stats != null && stats.size() >= 0) {
			// total
			if (statsInfo != null) {
				tmp1.put("name", "汇总");
				tmp1.put("min", df.format(statsInfo.getMin()));
				tmp1.put("max", df.format(statsInfo.getMax()));
				tmp1.put("sum", df.format(statsInfo.getSum()));
				tmp1.put("count", statsInfo.getCount());
				tmp1.put("missing", statsInfo.getMissing());
				tmp1.put("mean", df.format(statsInfo.getMean()));
				tmp1.put("stddev", df.format(statsInfo.getStddev()));
				list.add(tmp1);
				System.out.println("tmp:" + tmp1.toString());
				// facets
				Map<String, List<FieldStatsInfo>> map = statsInfo.getFacets();
				List<FieldStatsInfo> statisList = map.get("indus_name");
				if (statisList != null && statisList.size() > 0) {
					for (int i = 0; i < statisList.size(); i++) {
						// count大于0才统计
						if (statisList.get(i).getCount() > 0) {
							Map<String, Object> tmp2 = new HashMap<String, Object>();
							if ((statisList.get(i).getName() == null)) {
								tmp2.put("name", "未知分类");
							} else {
								tmp2.put("name", statisList.get(i).getName());
							}
							tmp2.put("min",
									df.format(statisList.get(i).getMin()));
							tmp2.put("max",
									df.format(statisList.get(i).getMax()));
							tmp2.put("sum",
									df.format(statisList.get(i).getSum()));
							tmp2.put("count", statisList.get(i).getCount());
							tmp2.put("missing", statisList.get(i).getMissing());
							tmp2.put("mean",
									df.format(statisList.get(i).getMean()));
							tmp2.put("stddev",
									df.format(statisList.get(i).getStddev()));
							list.add(tmp2);
						}
					}
				}
			}
			// 排序(按任务总额降序)
			Collections.sort(list, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> arg0,
						Map<String, Object> arg1) {
					return -(Double.valueOf((String) arg0.get("sum"))
							.compareTo(Double.valueOf(arg1.get("sum")
									.toString())));
				}
			});
			System.out.println("resultList:" + list.toString());

			// 搜索结果数
			pageModel.setiTotalDisplayRecords(list.size());
			pageModel.setiTotalRecords(list.size());
			pageModel.setAaData(list);
		}
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取任务统计(按分类)列表！！！" + json);

		return json.toString();
	}

	/**
	 * @Description:接单统计列表（按用户）
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年9月9日 下午10:29:08
	 */
	@RequestMapping(value = "/task/user/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getTaskFacetListByUser(
			HttpServletRequest request) throws Exception {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info("aoData:" + aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);

		// 发布时间
		String pub_start = getParamFromAodata(aoData, "pub_start");
		pub_start = (!"".equals(pub_start)) ? pub_start + "T00:00:00Z" : "*";
		String pub_end = getParamFromAodata(aoData, "pub_end");
		pub_end = (!"".equals(pub_end)) ? pub_end + "T23:59:59Z" : "*";
		// 托管时间
		String cash_start = getParamFromAodata(aoData, "cash_start");
		cash_start = (!"".equals(cash_start)) ? cash_start + "T00:00:00Z" : "*";
		String cash_end = getParamFromAodata(aoData, "cash_end");
		cash_end = (!"".equals(cash_end)) ? cash_end + "T23:59:59Z" : "*";
		// 任务类型
		String taskType = getParamFromAodata(aoData, "taskType");
		// 来源(web、iphone、Android等)
		String source = getParamFromAodata(aoData, "source");
		// 用户名
		String username = getParamFromAodata(aoData, "username");
		// 是否已托管
		String cash_status = getParamFromAodata(aoData, "cash_status");

		SolrQuery params = new SolrQuery("*:*");
		// 过滤掉计件任务
		// params.addFilterQuery("NOT model_id:3");
		// 过滤任务类型
		switch (taskType) {
		case "单赏":
			params.addFilterQuery("model_id:1");
			break;
		case "多赏":
			params.addFilterQuery("model_id:2");
			break;
		case "计件":
			params.addFilterQuery("model_id:3");
			break;
		case "招标":
			params.addFilterQuery("model_id:4").addFilterQuery(
					"task_type:{* TO 2}");
			break;
		case "雇佣":
			params.addFilterQuery("model_id:4")
					.addFilterQuery("task_type:{* TO 2}")
					.addFilterQuery("task_cash_coverage:0");
			break;
		case "服务":
			params.addFilterQuery("model_id:4").addFilterQuery(
					"task_type:2");
			break;
		case "直接雇佣":
			params.addFilterQuery("model_id:4").addFilterQuery(
					"task_type:3");
		default:
			break;
		}
		params.addFilterQuery("pub_time_date:[" + pub_start
				+ " TO " + pub_end + "]");
		params.addFilterQuery("cash_time_date:[" + cash_start
				+ " TO " + cash_end + "]");
		if (!"".equals(username))
			params.addFilterQuery("username:" + username);
		params.setGetFieldStatistics(true);
		params.setParam(StatsParams.STATS_FIELD, "task_cash");
		params.setParam(StatsParams.STATS_FACET, "username");

		if (!source.equals("全部"))
			params.addFilterQuery("source:" + source);

		if (!cash_status.equals("全部")) {
			if (cash_status.equals("未托管")) {
				params.addFilterQuery("cash_status:0");
			} else {
				params.addFilterQuery("cash_status:{0 TO *}");
			}
		}

		// 查询统计任务报表
		QueryResponse response = SolrUtils.getSolrServer("task").query(params);

		Map<String, Object> tmp1 = new HashMap<String, Object>();
		Map<String, FieldStatsInfo> stats = response.getFieldStatsInfo();
		FieldStatsInfo statsInfo = stats.get("task_cash");// 任务总金额
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// 返回结果集
		if (stats != null && stats.size() >= 0) {
			// total
			if (statsInfo != null && statsInfo.getCount() > 0) {
				tmp1.put("name", "汇总");
				tmp1.put("min", df.format(statsInfo.getMin()));
				tmp1.put("max", df.format(statsInfo.getMax()));
				tmp1.put("sum", df.format(statsInfo.getSum()));
				tmp1.put("count", statsInfo.getCount());
				tmp1.put("missing", statsInfo.getMissing());
				tmp1.put("mean", df.format(statsInfo.getMean()));
				tmp1.put("stddev", df.format(statsInfo.getStddev()));
				list.add(tmp1);
				System.out.println("tmp:" + tmp1.toString());
				// facets
				Map<String, List<FieldStatsInfo>> map = statsInfo.getFacets();
				List<FieldStatsInfo> statisList = map.get("username");
				if (statisList != null && statisList.size() > 0) {
					for (int i = 0; i < statisList.size(); i++) {
						// count大于0才统计
						if (statisList.get(i).getCount() > 0) {
							Map<String, Object> tmp2 = new HashMap<String, Object>();
							tmp2.put("name", statisList.get(i).getName());
							tmp2.put("min",
									df.format(statisList.get(i).getMin()));
							tmp2.put("max",
									df.format(statisList.get(i).getMax()));
							tmp2.put("sum",
									df.format(statisList.get(i).getSum()));
							tmp2.put("count", statisList.get(i).getCount());
							tmp2.put("missing", statisList.get(i).getMissing());
							tmp2.put("mean",
									df.format(statisList.get(i).getMean()));
							tmp2.put("stddev",
									df.format(statisList.get(i).getStddev()));
							list.add(tmp2);
						}
					}
				}
			}
			// 排序(按任务总额降序)
			Collections.sort(list, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> arg0,
						Map<String, Object> arg1) {
					return -(Double.valueOf((String) arg0.get("sum"))
							.compareTo(Double.valueOf(arg1.get("sum")
									.toString())));
				}
			});
			System.out.println("resultList:" + list.toString());

			// 搜索结果数
			pageModel.setiTotalDisplayRecords(list.size());
			pageModel.setiTotalRecords(list.size());
			pageModel.setAaData(list);
		}
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取任务统计(按雇主)列表！！！" + json);

		return json.toString();
	}

	@RequestMapping(value = { "/stat/task/user" })
	public ModelAndView taskStatByUser() throws SolrServerException,
			IOException {
		// 返回视图
		ModelAndView mv = new ModelAndView("stat/task/task_user");
		logger.info("进入任务统计(按雇主)！！！");
		return mv;
	}

	@RequestMapping(value = { "/stat/task/date" })
	public ModelAndView taskStatByDate() throws Exception {
		// 返回视图
		ModelAndView mv = new ModelAndView("stat/task/task_date");
		logger.info("进入任务统计(按日期)！！！");
		return mv;
	}

	@RequestMapping(value = { "/stat/task/indus" })
	public ModelAndView taskStatByIndus() throws Exception {
		// 返回视图
		ModelAndView mv = new ModelAndView("stat/task/task_indus");
		logger.info("进入任务统计(按分类)！！！");
		return mv;
	}

}
