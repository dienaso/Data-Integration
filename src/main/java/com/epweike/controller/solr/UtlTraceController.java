package com.epweike.controller.solr;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.epweike.controller.BaseController;
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
@RequestMapping("/urlTrace")
public class UtlTraceController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(UtlTraceController.class);

	/**
	 * @Description:用户访问链接统计
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月9日 下午5:29:08
	 */
	@RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSearchHistory(HttpServletRequest request)
			throws Exception {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info(aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);

		// 开始时间
		String startString = getParamFromAodata(aoData, "start");
		// 结束时间
		String endString = getParamFromAodata(aoData, "end");

		SolrQuery params = new SolrQuery("*:*")
				.addFilterQuery(
						"add_time:[" + startString + "T00:00:00Z TO "
								+ endString + "T23:59:59Z]").setFacet(true)
				.addFacetField("source").setFacetMinCount(1)
				.setFacetLimit(100);

		QueryResponse response = SolrUtils.getSolrServer("urltrace").query(
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
		logger.info("获取访问统计列表！！！" + json);

		return json.toString();
	}

	@RequestMapping(value = { "list" })
	public ModelAndView searchHistoryStat() throws SolrServerException, IOException {
		// 返回视图
		ModelAndView mv = new ModelAndView("solr/urltrace/list");
		logger.info("进入访问统计！！！");
		return mv;
	}

}
