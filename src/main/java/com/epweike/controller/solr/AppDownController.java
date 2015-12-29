package com.epweike.controller.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.epweike.controller.BaseController;
import com.epweike.model.PageModel;
import com.epweike.util.DateUtils;
import com.epweike.util.SolrUtils;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.common.params.GroupParams;
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
@RequestMapping("/appDown")
public class AppDownController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(AppDownController.class);

	/**
	 * @Description:app下载统计列表
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月9日 下午5:29:08
	 */
	@RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getTaskWork(HttpServletRequest request)
			throws Exception {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);
		// 开始时间
		String startString = getParamFromAodata(aoData, "start");
		Date start = DateUtils.parseDate(startString);
		// 结束时间
		String endString = getParamFromAodata(aoData, "end");
		Date end = DateUtils.parseDateTime(endString + " 23:59:59");
		// 应用类型
		String app_name = getParamFromAodata(aoData, "app_name");
		// 是否去重
		String distinct = getParamFromAodata(aoData, "distinct");
		// 统计类型(日、月、年)
		String statType = getParamFromAodata(aoData, "statType");
		// 来源(web、iphone、Android等)
		String source = getParamFromAodata(aoData, "source");

		SolrQuery parameters = new SolrQuery("*:*").setFacet(true)
				.addDateRangeFacet("down_time_date", start, end, statType)
				.setFacetLimit(1000);
		if (!source.equals("全部"))
			parameters.addFilterQuery("source:" + source);
		if (!app_name.equals("全部"))
			parameters.addFilterQuery("app_name:" + app_name);
		
		//去重
		if("1".equals(distinct)){
			parameters.setParam(GroupParams.GROUP_FACET, true);
			parameters.setParam(GroupParams.GROUP_FIELD, "down_ip");
		}

		// 日期根据统计类型截取
		int endIndex = 10;
		if (statType.contains("YEAR")) {
			endIndex = 4;
		} else if (statType.contains("MONTH")) {
			endIndex = 7;
		}

		QueryResponse response = SolrUtils.getSolrServer("app_down")
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

		// 搜索结果数
		pageModel.setiTotalDisplayRecords(list.size());
		pageModel.setiTotalRecords(list.size());
		pageModel.setAaData(list);
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取app下载统计列表！！！" + json);

		return json.toString();
	}

	@RequestMapping(value = { "/list" })
	public ModelAndView taskWorkStat() throws SolrServerException, IOException {
		// 返回视图
		ModelAndView mv = new ModelAndView("/solr/appdown/list");
		logger.info("进入app下载统计！！！");
		return mv;
	}

}
