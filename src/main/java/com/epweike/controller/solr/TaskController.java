package com.epweike.controller.solr;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
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
import com.epweike.model.RetModel;
import com.epweike.model.solr.Task;
import com.epweike.util.QueryUtils;
import com.epweike.util.SolrUtils;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.StatsParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wuxp
 */
@Controller
public class TaskController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	private static DecimalFormat df = new DecimalFormat("######0.000");// 保留3位

	public Task task;

	/**
	 * @Description:查询任务对象
	 * @author 吴小平
	 * @version 创建时间：2016年12月21日 下午3:28:27
	 */
	public Task getByTaskId(int task_id) throws IOException {

		String sql = "SELECT is_top, work_count, wiki_num, a.task_id AS task_id, start_time, IF(e.on_time > start_time,e.on_time,start_time) AS sort_time,"
				+ "  model_id, task_status, task_title, task_desc, task_akey_desc, a.uid AS uid, task_cash, "
				+ "  a.indus_id AS indus_id, view_num, work_num, pay_item, task_type, pub_time, task_cash_coverage, "
				+ "  cash_status, is_delay, no_show, CASE   POINT    WHEN '' "
				+ "    THEN NULL    WHEN ','    THEN NULL    ELSE CONCAT(     CONCAT("
				+ "        SUBSTRING(         SUBSTRING_INDEX(POINT, ',', - 1),         1,"
				+ "          INSTR(           SUBSTRING_INDEX(POINT, ',', - 1),           '.'"
				+ "          ) + 1       ),       ROUND(ROUND(RAND(), 10) * 10000000000)"
				+ "      ),     ',',     CONCAT(" + "        SUBSTRING(POINT, 1, INSTR(POINT, '.') + 1),"
				+ "        ROUND(ROUND(RAND(), 10) * 10000000000)     )   )  END AS POINT,"
				+ "  city, focus_num, logo_type, logo_tc, w_uid,"
				+ "  task_pay_time, w_username, w_bid_time, payitem_time,"
				+ "  b.g_id AS g_id,b.indus_name AS indus_name, c.service_id AS service_id,"
				+ "  d.w_integrity AS w_integrity " + "FROM"
				+ "  keke_witkey_task a LEFT JOIN keke_witkey_industry b ON a.indus_id=b.indus_id"
				+ "  LEFT JOIN keke_witkey_task_service_refer c ON a.task_id=c.task_id"
				+ "  LEFT JOIN keke_witkey_task_extra d ON a.task_id=d.task_id"
				+ "  LEFT JOIN keke_witkey_task_delay e ON a.task_id=e.task_id WHERE a.task_id=?";
		QueryUtils<Task> queryRunnerUtils = new QueryUtils<Task>(Task.class);

		Object params[] = { task_id };
		// 搜索结果集
		try {
			task = queryRunnerUtils.get(sql, params, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("获取任务对象！！！" + task);

		return task;
	}

	@RequestMapping(value = "/task/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel update(HttpServletRequest request)
			throws IOException, SQLException, IllegalArgumentException, IllegalAccessException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取主键
		int task_id = Integer.parseInt(request.getParameter("task_id"));
		// 获取任务对象
		Task task = null;
		try {
			task = getByTaskId(task_id);
		} catch (Exception e) {
			retModel.setFlag(false);
			retModel.setObj(e);
			retModel.setMsg("查询失败，暂时无法更新！");
			e.printStackTrace();
			return retModel;
		}

		if (task == null) {
			retModel.setFlag(false);
			retModel.setMsg("未找到该记录，暂时无法更新！");
			return retModel;
		}

		Class<? extends Task> clazz = task.getClass();
		Field[] fields = clazz.getDeclaredFields();
		// 拼装索引对象
		SolrInputDocument doc = new SolrInputDocument();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			f.setAccessible(true);
			System.out.println("属性名:" + f.getName() + " 属性值:" + f.get(task));

			if (!"task_id".equals(f.getName()) && !"serialVersionUID".equals(f.getName())) {
				Map<String, Object> oper = new HashMap<String, Object>();
				oper.put("set", f.get(task));
				doc.addField(f.getName(), oper);
			} else if ("task_id".equals(f.getName())) {
				doc.addField(f.getName(), f.get(task));
			}
		}
		System.out.println(doc.toString());
		try {
			SolrUtils.update(doc, "task");
			retModel.setMsg("更新成功！");
		} catch (Exception e) {
			retModel.setFlag(false);
			retModel.setObj(e);
			retModel.setMsg("更新失败！");
			e.printStackTrace();
		}

		return retModel;
	}

	@RequestMapping(value = { "/task/list" })
	public String list(Model model) {

		return "solr/task/list";
	}

	/**
	 * @Description:ajax获取任务列表
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年9月29日 下午3:28:27
	 * @throws SolrServerException
	 */
	@RequestMapping(value = "/task/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String paginationDataTables(HttpServletRequest request)
			throws IOException, SolrServerException {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info(aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);

		// task_id
		String task_id = getParamFromAodata(aoData, "task_id");
		// uid
		String uid = getParamFromAodata(aoData, "uid");
		// 任务标题
		String task_title = getParamFromAodata(aoData, "task_title");
		// 任务状态
		String task_status = getParamFromAodata(aoData, "task_status");

		SolrQuery params = new SolrQuery("*:*");
		params.addSort(new SortClause("pub_time", SolrQuery.ORDER.desc));
		params.setStart(pageModel.getiDisplayStart());
		params.setRows(pageModel.getiDisplayLength());

		if (!uid.equals(""))
			params.addFilterQuery("uid:" + uid);

		if (!task_id.equals(""))
			params.addFilterQuery("task_id:" + task_id);

		if (!task_title.equals(""))
			params.addFilterQuery("task_title:" + task_title);

		if (!"全部".equals(task_status))
			params.addFilterQuery("task_status:" + task_status);

		QueryResponse response = SolrUtils.query(params, "task");
		// 获取服务列表
		SolrDocumentList list = response.getResults();
		// 总条数
		long total = response.getResults().getNumFound();

		// 搜索结果数
		pageModel.setiTotalDisplayRecords(total);
		pageModel.setiTotalRecords(total);
		pageModel.setAaData(list);
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取任务列表！！！" + json);

		return json.toString();
	}

	/**
	 * @Description:任务来源统计列表（按时间）
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月9日 下午5:29:08
	 */
	@RequestMapping(value = "/task/date/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getTaskFacetByDate(HttpServletRequest request) throws Exception {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info(aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);
		// facet统计列表
		List<Map<String, Object>> list = getTaskFacetListByTime(aoData, "pub_time_date", "task");
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
		// 过滤掉计件任务
		// params.addFilterQuery("NOT model_id:3");
		// 过滤任务类型
		String filter = "";
		if (!"".equals(taskType)) {
			String[] types = taskType.split(",");
			for (int i = 0; i < types.length; i++) {
				System.out.println(types[i]);
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
		
		params.addFilterQuery("pub_time_date:[" + pub_start + " TO " + pub_end + "]");
		if (!cash_start.equals("*") && !cash_end.equals("*"))
			params.addFilterQuery("cash_time_date:[" + cash_start + " TO " + cash_end + "]");
		if (!"".equals(indus_name))
			params.addFilterQuery("indus_name:" + indus_name);

		params.setGetFieldStatistics(true);
		params.setParam(StatsParams.STATS_FIELD, "task_cash");
		params.setParam(StatsParams.STATS_FACET, "indus_name");

		if (!"全部".equals(task_status))
			params.addFilterQuery("task_status:" + task_status);
		
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
			params.addFilterQuery("work_num:[" +work_num+ " TO *}");
		
		if (!source.equals("全部"))
			params.addFilterQuery("source:" + source);

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
		logger.info("获取任务统计(按分类)列表！！！" + json);

		return json.toString();
	}

	/**
	 * @Description:任务统计列表（按雇主）
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年9月9日 下午10:29:08
	 */
	@RequestMapping(value = "/task/user/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getTaskFacetListByUser(HttpServletRequest request) throws Exception {

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
		// 任务状态
		String task_status = getParamFromAodata(aoData, "task_status");

		// 用户名
		String username = getParamFromAodata(aoData, "username");
		// 是否已托管
		String cash_status = getParamFromAodata(aoData, "cash_status");

		SolrQuery params = new SolrQuery("*:*");
		// 过滤掉计件任务
		// params.addFilterQuery("NOT model_id:3");
		// 过滤任务类型
		String filter = "";
		if (!"".equals(taskType)) {
			String[] types = taskType.split(",");
			for (int i = 0; i < types.length; i++) {
				System.out.println(types[i]);
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
		params.addFilterQuery("pub_time_date:[" + pub_start + " TO " + pub_end + "]");
		if (!cash_start.equals("*") && !cash_end.equals("*"))
			params.addFilterQuery("cash_time_date:[" + cash_start + " TO " + cash_end + "]");
		if (!"".equals(username))
			params.addFilterQuery("username:" + username);
		params.setGetFieldStatistics(true);
		params.setParam(StatsParams.STATS_FIELD, "task_cash");
		params.setParam(StatsParams.STATS_FACET, "username");
		if (!"全部".equals(task_status))
			params.addFilterQuery("task_status:" + task_status);

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
					for (int i = 0, len = statisList.size(); i < len; i++) {
						// count大于0才统计
						if (statisList.get(i).getCount() > 0) {
							Map<String, Object> tmp2 = new HashMap<String, Object>();
							tmp2.put("name", statisList.get(i).getName());
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
		logger.info("获取任务统计(按雇主)列表！！！" + json);

		return json.toString();
	}

	@RequestMapping(value = { "/stat/task/user" })
	public ModelAndView taskStatByUser() throws SolrServerException, IOException {
		// 返回视图
		ModelAndView mv = new ModelAndView("stat/task/task_user");
		mv.addObject("sourceList", getFacetList("task", "source", 10));
		logger.info("进入任务统计(按雇主)！！！");
		return mv;
	}

	@RequestMapping(value = { "/stat/task/date" })
	public ModelAndView taskStatByDate() throws Exception {
		// 返回视图
		ModelAndView mv = new ModelAndView("stat/task/task_date");
		mv.addObject("sourceList", getFacetList("task", "source", 10));
		logger.info("进入任务统计(按日期)！！！");
		return mv;
	}

	@RequestMapping(value = { "/stat/task/indus" })
	public ModelAndView taskStatByIndus() throws Exception {
		// 返回视图
		ModelAndView mv = new ModelAndView("stat/task/task_indus");
		mv.addObject("sourceList", getFacetList("task", "source", 10));
		logger.info("进入任务统计(按分类)！！！");
		return mv;
	}

	@RequestMapping(value = "/task/del", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel del(HttpServletRequest request) throws IOException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取主键
		String task_id = request.getParameter("task_id");
		List<String> ids = new ArrayList<String>();
		ids.add(task_id);
		try {
			SolrUtils.deleteById(ids, "task");
			retModel.setMsg("删除成功！");
		} catch (Exception e) {
			retModel.setFlag(false);
			retModel.setObj(e);
			retModel.setMsg("删除失败！");
			e.printStackTrace();
		}

		return retModel;
	}

}
