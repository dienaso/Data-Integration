package com.epweike.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.epweike.model.PageModel;
import com.epweike.util.SolrUtils;
import com.epweike.util.StatUtils;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
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
public class SearchHistoryController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(SearchHistoryController.class);

	/**
	 * @Description:通用搜索历史记录,根据时间分组
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月17日 上午8:47:31
	 */
	@RequestMapping(value = "/search/history/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSearchHistory(HttpServletRequest request)
			throws Exception {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info(aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);

		// 开始时间
		String startString = getParamFromAodata(aoData, "start");
		// Date start = DateUtils.parseDate(startString);
		// 结束时间
		String endString = getParamFromAodata(aoData, "end");
		// Date end = DateUtils.parseDateTime(endString + " 23:59:59");
		// 搜索类型
		String searchType = getParamFromAodata(aoData, "searchType");

		SolrQuery params = new SolrQuery("*:*")
				.addFilterQuery(
						"on_time:[" + startString + "T00:00:00Z TO "
								+ endString + "T23:59:59Z]").setFacet(true)
				.addFacetField("keyword").setFacetMinCount(1)
				.setFacetLimit(Integer.MAX_VALUE);
		if (!searchType.equals("all"))// 搜索类型过滤
			params.addFilterQuery("search_type:" + searchType);

		QueryResponse response = SolrUtils.getSolrServer("search_history").query(
				params);
		// 获取统计列表
		List<FacetField> facetFields = response.getFacetFields();

		List<Map<String, Object>> list = StatUtils
				.getFacetList(facetFields, "");

		// 搜索结果数
		pageModel.setiTotalDisplayRecords(list.size());
		pageModel.setiTotalRecords(list.size());
		pageModel.setAaData(list);
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取搜索历史统计列表！！！" + json);

		return json.toString();
	}

	@RequestMapping(value = { "/stat/search_history" })
	public ModelAndView searchHistoryStat() throws SolrServerException, IOException {
		// 返回视图
		ModelAndView mv = new ModelAndView("stat/search/search_history");
		logger.info("进入搜索历史统计！！！");
		return mv;
	}

}
