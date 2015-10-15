package com.epweike.controller.solr;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.epweike.controller.BaseController;
import com.epweike.model.PageModel;
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
	@RequestMapping(value = {"stat/loginType"})
    public ModelAndView loginTypeStat() throws SolrServerException, IOException {
		
		SolrQuery params = new SolrQuery("*:*").setFacet(true).addFacetField("login_type");
		QueryResponse response = SolrUtils.getSolrServer("login").query(params);
		SolrDocumentList results = response.getResults();
		
		//登录类型统计
		List<FacetField> facetFields = response.getFacetFields(); 
		
		//返回视图
		ModelAndView mv = new ModelAndView("solr/loginInfo/loginType");
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
	@RequestMapping(value = "loginDetail/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
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
	@RequestMapping(value = { "stat/loginDetail" })
	public ModelAndView loginDetail() throws SolrServerException, IOException {
		// 返回视图
		ModelAndView mv = new ModelAndView("solr/loginInfo/loginDetail");
		logger.info("进入用户登陆明细列表！！！");
		return mv;
	}
}
