package com.epweike.controller.solr;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.epweike.controller.BaseController;
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
public class TaskWorkController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(TaskWorkController.class);

	private static DecimalFormat df = new DecimalFormat("######0");// 保留3位

	/**
	 * @Description:稿件来源统计列表
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月9日 下午5:29:08
	 */
	@RequestMapping(value = "/stat/task_work/date/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getTaskWork(HttpServletRequest request) throws Exception {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info(aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);
		// facet统计列表
		List<Map<String, Object>> list = getTaskFacetListByTime(aoData, "work_time", "task_work");

		// 搜索结果数
		pageModel.setiTotalDisplayRecords(list.size());
		pageModel.setiTotalRecords(list.size());
		pageModel.setAaData(list);
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取稿件统计列表！！！" + json);

		return json.toString();
	}

	@RequestMapping(value = { "/stat/task_work/date" })
	public ModelAndView taskWorkStat() throws SolrServerException, IOException {
		// 返回视图
		ModelAndView mv = new ModelAndView("solr/task_work/date");
		logger.info("进入稿件统计！！！");
		return mv;
	}

	/**
	 * @Description:任务来源统计列表（按分类）
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月9日 下午5:29:08
	 */
	@RequestMapping(value = "/task_work/indus/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getTaskFacetByIndus(HttpServletRequest request) throws Exception {

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
		// 任务状态
		String task_status = getParamFromAodata(aoData, "task_status");

		// 分类名称
		String indus_name = getParamFromAodata(aoData, "indus_name");

		// 最低投稿数
		String work_num = getParamFromAodata(aoData, "work_num");

		SolrQuery params = new SolrQuery("*:*");

		if (!"全部".equals(task_status))
			params.addFilterQuery("task_status:" + task_status);
		// 过滤掉计件任务
		// params.addFilterQuery("NOT model_id:3");

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
		params.addFilterQuery(filter);
		System.out.println("aaafilter:"+filter);

		params.addFilterQuery("pub_time_date:[" + pub_start + " TO " + pub_end + "]");
		if (!cash_start.equals("*") && !cash_end.equals("*"))
			params.addFilterQuery("cash_time_date:[" + cash_start + " TO " + cash_end + "]");
		if (!"".equals(indus_name))
			params.addFilterQuery("indus_name:" + indus_name);

		params.setGetFieldStatistics(true);
		params.setParam(StatsParams.STATS_FIELD, "work_num");
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
		if (!"".equals(work_num))
			params.addFilterQuery("work_num:[" + work_num + " TO *}");

		if (!source.equals("全部"))
			params.addFilterQuery("source:" + source);

		// 查询统计任务报表
		QueryResponse response = SolrUtils.getSolrServer("task").query(params);

		Map<String, Object> tmp1 = new HashMap<String, Object>();
		Map<String, FieldStatsInfo> stats = response.getFieldStatsInfo();
		FieldStatsInfo statsInfo = stats.get("work_num");// 任务总金额
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
					for (int i = 0, len = statisList.size(); i < len; i++) {
						// count大于0才统计
						if (statisList.get(i).getCount() > 0) {
							Map<String, Object> tmp2 = new HashMap<String, Object>();
							if ((statisList.get(i).getName() == null)) {
								tmp2.put("name", "未知分类");
							} else {
								tmp2.put("name", statisList.get(i).getName());
							}
							tmp2.put("min", df.format(statisList.get(i).getMin()));
							tmp2.put("max", df.format(statisList.get(i).getMax()));
							tmp2.put("sum", df.format(statisList.get(i).getSum()));
							tmp2.put("count", statisList.get(i).getCount());
							tmp2.put("missing", statisList.get(i).getMissing());
							tmp2.put("mean", df.format(statisList.get(i).getMean()));
							tmp2.put("stddev", df.format(statisList.get(i).getStddev()));
							list.add(tmp2);
						}
					}
				}
			}
			// 排序(按任务总额降序)
			Collections.sort(list, new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
					return -(Double.valueOf(arg0.get("count").toString())
							.compareTo(Double.valueOf(arg1.get("count").toString())));
				}
			});
			System.out.println("resultList:" + list.toString());

			// 搜索结果数
			pageModel.setiTotalDisplayRecords(list.size());
			pageModel.setiTotalRecords(list.size());
			pageModel.setAaData(list);
		}
		JSONObject json = JSONObject.fromObject(pageModel);

		return json.toString();
	}

	@RequestMapping(value = { "/stat/task_work/indus" })
	public ModelAndView taskWorkStatByIndus() throws SolrServerException, IOException {
		// 返回视图
		ModelAndView mv = new ModelAndView("solr/task_work/indus");
		mv.addObject("sourceList", getFacetList("task", "source", 10));
		return mv;
	}

}
