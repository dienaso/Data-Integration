package com.epweike.controller.solr;

import com.epweike.controller.BaseController;
import com.epweike.model.PageModel;
import com.epweike.model.RetModel;
import com.epweike.model.solr.Service;
import com.epweike.util.QueryUtils;
import com.epweike.util.SolrUtils;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	public Service service;

	@RequestMapping(value = { "list" })
	public String list(Model model) {

		return "solr/service/list";
	}
	
	/**
	 * @Description:查询服务对象
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年9月29日 下午3:28:27
	 */
	public Service getById(int service_id) throws IOException {

		String sql = "SELECT a.service_id,model_id,service_type,indus_id,indus_pid,title,price,is_stop,sale_num,focus_num,mark_num,leave_num,views,"
				+ "total_sale,service_status,is_top,a.listorder,b.listorder AS recommend_listorder,catid,TYPE FROM keke_witkey_service a "
				+ "LEFT JOIN keke_witkey_talent_recommend b ON a.service_id=b.service_id"
				+ " WHERE a.service_id=?";
		QueryUtils<Service> queryRunnerUtils = new QueryUtils<Service>(
				Service.class);

		Object params[] = { service_id };
		// 搜索结果集
		try {
			service = queryRunnerUtils.get(sql, params, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("获取服务对象！！！" + service);

		return service;
	}

	/**
	 * @Description:ajax获取服务列表
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
			params.addFilterQuery("username:" + "\"*" + username + "*\"");

		QueryResponse response = SolrUtils.query(params, "service");
		// 获取服务列表
		SolrDocumentList list = response.getResults();
		// 总条数
		long total = response.getResults().getNumFound();

		// 搜索结果数
		pageModel.setiTotalDisplayRecords(total);
		pageModel.setiTotalRecords(total);
		pageModel.setAaData(list);
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取服务列表！！！" + json);

		return json.toString();
	}

	@RequestMapping(value = "update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel update(HttpServletRequest request)
			throws IOException, SQLException, IllegalArgumentException,
			IllegalAccessException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取主键
		int service_id = Integer.parseInt(request.getParameter("service_id"));
		// 获取服务对象
		Service service = null;
		try {
			service = getById(service_id);
		} catch (Exception e) {
			retModel.setFlag(false);
			retModel.setObj(e);
			retModel.setMsg("查询失败，暂时无法更新！");
			e.printStackTrace();
			return retModel;
		}
		
		if(service == null) {
			retModel.setFlag(false);
			retModel.setMsg("未找到该记录，暂时无法更新！");
			return retModel;
		}
		
		Class<? extends Service> clazz = service.getClass();
		Field[] fields = clazz.getDeclaredFields();
		// 拼装索引对象
		SolrInputDocument doc = new SolrInputDocument();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			f.setAccessible(true);
			System.out.println("属性名:" + f.getName() + " 属性值:" + f.get(service));
			if (!"service_id".equals(f.getName())
					&& !"serialVersionUID".equals(f.getName())) {
				Map<String, Object> oper = new HashMap<String, Object>();
				oper.put("set", f.get(service));
				doc.addField(f.getName(), oper);
			} else if ("service_id".equals(f.getName())) {
				doc.addField(f.getName(), f.get(service));
			}
		}
		System.out.println(doc.toString());
		try {
			SolrUtils.update(doc, "service");
			retModel.setMsg("更新成功！");
		} catch (Exception e) {
			retModel.setFlag(false);
			retModel.setObj(e);
			retModel.setMsg("更新失败！");
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
			retModel.setObj(e);
			retModel.setMsg("删除失败！");
			e.printStackTrace();
		}

		return retModel;
	}

}
