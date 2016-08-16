package com.epweike.controller.solr;

import javax.servlet.http.HttpServletRequest;

import com.epweike.controller.BaseController;
import com.epweike.util.SolrUtils;

import net.sf.json.JSONArray;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wuxp
 */
@Controller
@RequestMapping("/industry")
public class IndustryController extends BaseController {

	/**
	 * @Description:获取下级分类
	 * 
	 * @author 吴小平
	 * @version 创建时间：2016年6月21日 下午5:29:08
	 */
	@RequestMapping(value = "getChild", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSearchHistory(HttpServletRequest request) throws Exception {

		// 一级分类
		String g_id = request.getParameter("g_id");
		// 二级分类
		String indus_pid = request.getParameter("indus_pid");

		SolrQuery params = new SolrQuery("*:*").addFacetQuery("indus_type:1").setFields("indus_id,indus_name")
				.setSort("listorder", SolrQuery.ORDER.asc);
		if (g_id != null && !"".equals(g_id)) {
			params.addFilterQuery("g_id:" + g_id).addFilterQuery("indus_pid:0");
		} else if (indus_pid != null && !"".equals(indus_pid)) {
			params.addFilterQuery("indus_pid:" + indus_pid);
		}

		QueryResponse response = SolrUtils.getSolrServer("industry").query(params);

		SolrDocumentList results = new SolrDocumentList();
		results = response.getResults();

		JSONArray json = JSONArray.fromObject(results);

		return json.toString();
	}

}
