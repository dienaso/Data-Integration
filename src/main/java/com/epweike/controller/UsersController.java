package com.epweike.controller;

import com.epweike.model.PageModel;
import com.epweike.model.Users;
import com.epweike.service.UsersService;
import com.epweike.util.StatUtils;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxp
 */
@Controller
public class UsersController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(UsersController.class);

	@Autowired
	private UsersService usersService;

	public List<Users> usersList;

	public User user;

	@RequestMapping(value = { "/users/list" })
	public String list(Model model) {
		user = (User) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		return "users/list";
	}

	/**
	 * @Description:ajax获取系统用户列表
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午3:28:27
	 */
	@RequestMapping(value = "/users/get", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody String paginationDataTables(HttpServletRequest request)
			throws IOException {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		// 解析查询关键参数
		PageModel<Users> pageModel = parsePageParamFromJson(aoData);
		// 搜索条件
		String sSearch = pageModel.getsSearch();
		// 总条数
		int total = this.usersService.count(new Users());
		// 搜索结果集
		usersList = this.usersService.selectPage(new Users(sSearch), pageModel);
		pageModel.setiTotalDisplayRecords(total);
		pageModel.setiTotalRecords(total);
		pageModel.setAaData(usersList);

		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取用户列表！！！" + json);

		return json.toString();
	}

	@RequestMapping(value = "/users/del", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody int del(HttpServletRequest request) throws IOException {

		// 获取删除主键
		String id = request.getParameter("id");

		System.out.println("--------------------" + id);

		int result = this.usersService.delete(id);

		return result;
	}

	/**
	 * @Description:一品用户省份分布统计
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午3:28:27
	 */
	@RequestMapping(value = { "/stat/users/province" })
	public ModelAndView provinceStat() throws SolrServerException, IOException {

		SolrQuery params = new SolrQuery("*:*").setFacet(true).addFacetField(
				"province");
		QueryResponse response = getSolrServer("talent").query(params);
		SolrDocumentList results = response.getResults();

		// 地区分布统计
		List<FacetField> facetFields = response.getFacetFields();

		// 返回视图
		ModelAndView mv = new ModelAndView("stat/users/province");
		// 总数
		mv.addObject("total", results.getNumFound());
		// 饼状图数据
		// mv.addObject("pieData", ChartUtils.pieJson(facetFields));
		// 柱状图数据
		mv.addObject("barData", StatUtils.barJson(facetFields));
		logger.info("进入用户分布统计！！！");
		return mv;
	}

	/**
	 * @Description:一品用户注册统计
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午3:28:27
	 */
	@RequestMapping(value = { "/stat/users/register" })
	public ModelAndView register() throws SolrServerException, IOException {
		// 返回视图
		ModelAndView mv = new ModelAndView("stat/users/register");
		logger.info("进入用户注册统计！！！");
		return mv;
	}

	// /**
	// * @Description:获取注册用户
	// *
	// * @author 吴小平
	// * @version 创建时间：2015年6月10日 下午3:28:27
	// */
	// @RequestMapping(value = "/users/register/get", method =
	// RequestMethod.GET, produces = "application/json;charset=UTF-8")
	// public @ResponseBody String getRegister(HttpServletRequest request)
	// throws Exception {
	//
	// // 获取查询关键参数
	// String aoData = request.getParameter("aoData");
	// logger.info(aoData);
	// // 解析查询关键参数
	// PageModel<Map<String, Object>> pageModel =
	// parsePageParamFromJson(aoData);
	//
	// // 开始时间
	// String startString = getParamFromAodata(aoData, "start");
	// Date start = DateUtils.parseDate(startString);
	// // 结束时间
	// String endString = getParamFromAodata(aoData, "end");
	// Date end = DateUtils.parseDateTime(endString + " 23:59:59");
	// // 统计类型(日、月、年)
	// String statType = getParamFromAodata(aoData, "statType");
	//
	// SolrQuery params = new SolrQuery("*:*").setFacet(true)
	// .addDateRangeFacet("reg_time_date", start, end, statType)
	// .setFacetLimit(1000);
	//
	// QueryResponse response = getSolrServer("talent").query(params);
	//
	// // 日期根据统计类型截取
	// int endIndex = 10;
	// if (statType.contains("YEAR")) {
	// endIndex = 4;
	// } else if (statType.contains("MONTH")) {
	// endIndex = 7;
	// }
	//
	// // 获取区间统计列表
	// @SuppressWarnings("rawtypes")
	// List<RangeFacet> listFacet = response.getFacetRanges();
	// List<Map<String, Object>> list = StatUtils.getFacetRangeList(listFacet,
	// endIndex);
	//
	// // 搜索结果数
	// pageModel.setiTotalDisplayRecords(list.size());
	// pageModel.setiTotalRecords(list.size());
	// pageModel.setAaData(list);
	// JSONObject json = JSONObject.fromObject(pageModel);
	// logger.info("获取用户注册统计列表！！！" + json);
	//
	// return json.toString();
	// }

	/**
	 * @Description:获取注册用户
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午3:28:27
	 */
	@RequestMapping(value = "/users/register/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getRegister(HttpServletRequest request)
			throws Exception {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info(aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);

		// 注册时间
		String reg_start = getParamFromAodata(aoData, "reg_start");
		reg_start = (!"".equals(reg_start)) ? reg_start + "T00:00:00Z" : "*";
		String reg_end = getParamFromAodata(aoData, "reg_end");
		reg_end = (!"".equals(reg_end)) ? reg_end + "T23:59:59Z" : "*";
		// 注册渠道
		// String come = getParamFromAodata(aoData, "come");

		SolrQuery params = new SolrQuery("*:*");
		params.addFilterQuery("reg_time_date:[" + reg_start + " TO " + reg_end
				+ "]");
		params.setFacet(true);
		params.addFacetPivotField("reg_date,come").setFacetLimit(
				Integer.MAX_VALUE);
		// if (!come.equals("全部"))
		// params.addFilterQuery("come:" + come);
		// params.setParam(FacetParams.FACET_PIVOT_MINCOUNT, "0");

		QueryResponse response = getSolrServer("talent").query(params);
		NamedList<List<PivotField>> namedList = response.getFacetPivot();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;

		if (namedList != null) {
			List<PivotField> pivotList = null;
			for (int i = 0; i < namedList.size(); i++) {
				pivotList = namedList.getVal(i);
				if (pivotList != null) {
					for (PivotField pivot : pivotList) {
						int total = 0;
						map = new HashMap<String, Object>();
						map.put("label", pivot.getValue());
						List<PivotField> fieldList = pivot.getPivot();
						if (fieldList != null) {
							for (PivotField field : fieldList) {
								int count = field.getCount();
								map.put(field.getValue().toString(), count);
								total += count;
							}
						}
						map.put("TOTAL", total);
						// 不存在赋值0
						if (map.get("WEB") == null)
							map.put("WEB", 0);
						if (map.get("cpm") == null)
							map.put("cpm", 0);
						if (map.get("APP") == null)
							map.put("APP", 0);
						if (map.get("WAP") == null)
							map.put("WAP", 0);
						if (map.get("mall") == null)
							map.put("mall", 0);
						if (map.get("background") == null)
							map.put("background", 0);
						if (map.get("yun") == null)
							map.put("yun", 0);

						list.add(map);
					}
				}
			}
		}

		// 排序(按注册日期升序)
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> arg0,
					Map<String, Object> arg1) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				Date date1 = null;
				Date date2 = null;
				try {
					date1 = simpleDateFormat
							.parse(arg0.get("label").toString());
					date2 = simpleDateFormat
							.parse(arg1.get("label").toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Double timeStemp1 = (double) date1.getTime();
				Double timeStemp2 = (double) date2.getTime();
				return -(timeStemp1.compareTo(timeStemp2));
			}
		});

		// 搜索结果数
		pageModel.setiTotalDisplayRecords(list.size());
		pageModel.setiTotalRecords(list.size());
		pageModel.setAaData(list);
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取用户注册统计列表！！！" + json);

		return json.toString();
	}

}
