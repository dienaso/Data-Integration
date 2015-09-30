package com.epweike.controller.solr;

import com.epweike.controller.BaseController;
import com.epweike.model.PageModel;
import com.epweike.model.RetModel;
import com.epweike.util.SolrUtils;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxp
 */
@Controller
@RequestMapping("/service")
public class ServiceController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(ServiceController.class);

	@RequestMapping(value = { "list" })
	public String list(Model model) {

		return "solr/service/list";
	}

	/**
	 * @Description:ajax获取人才列表
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年9月29日 下午3:28:27
	 * @throws SolrServerException
	 */
	@RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String paginationDataTables(HttpServletRequest request)
			throws IOException, SolrServerException {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info(aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);

		// uid
		String uid = getParamFromAodata(aoData, "uid");
		// 服务标识
		String service_id = getParamFromAodata(aoData, "service_id");
		// 用户名
		String username = getParamFromAodata(aoData, "username");

		SolrQuery params = new SolrQuery("*:*");
		params.addSort(new SortClause("service_id", SolrQuery.ORDER.asc));
		params.setStart(pageModel.getiDisplayStart());
		params.setRows(pageModel.getiDisplayLength());

		if (!uid.equals(""))
			params.addFilterQuery("uid:" + uid);

		if (!service_id.equals(""))
			params.addFilterQuery("service_id:" + service_id);

		if (!username.equals(""))
			params.addFilterQuery("username:" + username);

		QueryResponse response = SolrUtils.query(params, "service");
		// 获取人才列表
		SolrDocumentList list = response.getResults();
		// 总条数
		long total = response.getResults().getNumFound();

		// 搜索结果数
		pageModel.setiTotalDisplayRecords(total);
		pageModel.setiTotalRecords(total);
		pageModel.setAaData(list);
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取人才列表！！！" + json);

		return json.toString();
	}

	@RequestMapping(value = "update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel update(HttpServletRequest request)
			throws IOException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取主键
		//int service_id = Integer.parseInt(request.getParameter("service_id"));

		try {
			retModel.setMsg("更新成功！");
		} catch (Exception e) {
			retModel.setFlag(false);
			retModel.setMsg("更新失败！");
			retModel.setObj(e);
			e.printStackTrace();
		}

		return retModel;
	}

	@RequestMapping(value = "del", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel del(HttpServletRequest request)
			throws IOException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取主键
		String service_id = request.getParameter("service_id");
		List<String> ids = new ArrayList<String>();
		ids.add(service_id);
		try {
			SolrUtils.deleteById(ids, "service");
			retModel.setMsg("删除成功！");
		} catch (Exception e) {
			retModel.setFlag(false);
			retModel.setMsg("删除失败！");
			retModel.setObj(e);
			e.printStackTrace();
		}

		return retModel;
	}

}
