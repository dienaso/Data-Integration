package com.epweike.controller.solr;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.GroupParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.epweike.controller.BaseController;
import com.epweike.model.PageModel;
import com.epweike.util.DateUtils;
import com.epweike.util.SolrUtils;
import com.epweike.util.StatUtils;

/**
 * @author wuxp
 */
@Controller
@RequestMapping("/loginInfo")
public class LoginInfoController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(LoginInfoController.class);
	
	/**  
	* @Description:一品用户登陆类型统计
	*  
	* @author  吴小平
	* @version 创建时间：2015年6月10日 下午3:13:55
	*/  
	@RequestMapping(value = {"stat/type"})
    public ModelAndView loginTypeStat() throws SolrServerException, IOException {
		
		SolrQuery params = new SolrQuery("*:*").setFacet(true).addFacetField("login_type");
		QueryResponse response = SolrUtils.getSolrServer("login").query(params);
		SolrDocumentList results = response.getResults();
		
		//登录类型统计
		List<FacetField> facetFields = response.getFacetFields(); 
		
		//返回视图
		ModelAndView mv = new ModelAndView("solr/loginInfo/login_type");
		//总数
		mv.addObject("total", results.getNumFound());
		//柱状图数据
		mv.addObject("barData", StatUtils.barJson(facetFields));
		//饼状图数据
		mv.addObject("pieData", StatUtils.pieJson(facetFields));
		logger.info("进入用户注册统计！！！");
        return mv;
    }
	
	/**
	 * @Description:获取一品用户登陆明细
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午3:28:27
	 */
	@RequestMapping(value = "detail/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getLoginDetail(HttpServletRequest request)
			throws Exception {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info(aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);

		// 开始时间
		String startTime = getParamFromAodata(aoData, "start");
		// 结束时间
		String endTime = getParamFromAodata(aoData, "end");
		// 登陆类型类型
		String loginType = getParamFromAodata(aoData, "loginType");
		// 用户名
		String username = getParamFromAodata(aoData, "username");
		// UID
		String uid = getParamFromAodata(aoData, "uid");
		// 过滤条件
		SolrQuery params = new SolrQuery("*:*");

		params.setFields("uid,country,on_time_str,city,login_type,ip,username");
		if (!loginType.equals("全部"))
			params.addFilterQuery("login_type:" + loginType);
		if (!username.equals(""))
			params.addFilterQuery("username:" + username);

		if (!uid.equals(""))
			params.addFilterQuery("uid:" + uid);
		params.addFilterQuery("on_time:[" + startTime + "T00:00:00Z TO "
				+ endTime + "T23:59:59Z]");
		params.setStart(pageModel.getiDisplayStart());
		params.setRows(pageModel.getiDisplayLength());
		params.setSort("on_time", SolrQuery.ORDER.desc);

		QueryResponse response = SolrUtils.getSolrServer("login").query(params);

		// 获取登陆明细列表
		SolrDocumentList list = response.getResults();

		// 搜索结果数
		pageModel.setiTotalDisplayRecords(list.getNumFound());
		pageModel.setiTotalRecords(list.getNumFound());
		pageModel.setAaData(list);
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取用户登陆明细列表！！！" + json);

		return json.toString();
	}

	/**
	 * @Description:一品用户登陆明细
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午3:28:27
	 */
	@RequestMapping(value = { "stat/detail" })
	public ModelAndView loginDetail() throws SolrServerException, IOException {
		// 返回视图
		ModelAndView mv = new ModelAndView("solr/loginInfo/login_detail");
		logger.info("进入用户登陆明细列表！！！");
		return mv;
	}
	
	/**
	 * @Description:获取一品用户活跃统计
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年12月11日 下午3:28:27
	 */
	@RequestMapping(value = "date/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getLoginDate(HttpServletRequest request)
			throws Exception {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info(aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);
		// facet统计列表
		// 开始时间
		String startString = getParamFromAodata(aoData, "start");
		Date start = DateUtils.parseDate(startString);
		// 结束时间
		String endString = getParamFromAodata(aoData, "end");
		Date end = DateUtils.parseDateTime(endString + " 23:59:59");
		// 是否去重
		String distinct = getParamFromAodata(aoData, "distinct");
		// 统计类型(日、月、年)
		String statType = getParamFromAodata(aoData, "statType");
		// 登陆设备
		String loginType = getParamFromAodata(aoData, "loginType");

		SolrQuery parameters = new SolrQuery("*:*").setFacet(true)
				.addDateRangeFacet("on_time", start, end, statType)
				.setFacetLimit(1000);
		if (!loginType.equals("全部"))
			parameters.addFilterQuery("login_type:" + loginType);
		
		// 日期根据统计类型截取
		int endIndex = 10;
		if (statType.contains("YEAR")) {
			endIndex = 4;
		} else if (statType.contains("MONTH")) {
			endIndex = 7;
		}
		
		//去重
		if("1".equals(distinct)){
			parameters.setParam(GroupParams.GROUP_FACET, true);
			parameters.setParam(GroupParams.GROUP_FIELD, "uid");
		}
		QueryResponse response = SolrUtils.getSolrServer("login")
				.query(parameters);
		
		List<Map<String, Object>> list = StatUtils.getFacetRangeList(
				response.getFacetRanges(), 0, endIndex);
		
		// 搜索结果数
		pageModel.setiTotalDisplayRecords(list.size());
		pageModel.setiTotalRecords(list.size());
		pageModel.setAaData(list);
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取用户活跃统计列表！！！" + json);

		return json.toString();
	}
	
	/**
	 * @Description:一品用户登陆明细
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午3:28:27
	 */
	@RequestMapping(value = { "stat/date" })
	public ModelAndView date() throws SolrServerException, IOException {
		// 返回视图
		ModelAndView mv = new ModelAndView("solr/loginInfo/login_date");
		logger.info("进入用户登陆明细列表！！！");
		return mv;
	}
}
