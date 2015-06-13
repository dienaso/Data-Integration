package com.epweike.controller;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epweike.util.StatUtils;

/**
 * @author wuxp
 */
@Controller
public class LoginController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value = {"/login"})
    public String login(Model model) {
		logger.info("进入登录页面！！！");
        return "login";
    }
	
	/**  
	* @Description:一品用户登陆类型统计
	*  
	* @author  吴小平
	* @version 创建时间：2015年6月10日 下午3:13:55
	*/  
	@RequestMapping(value = {"/stat/users/loginType"})
    public ModelAndView loginTypeStat() throws SolrServerException, IOException {
		
		SolrQuery parameters = new SolrQuery("*:*").setFacet(true).addFacetField("login_type");
		QueryResponse response = getSolrServer("login").query(parameters);
		SolrDocumentList results = response.getResults();
		
		//登录类型统计
		List<FacetField> facetFields = response.getFacetFields(); 
		
		//返回视图
		ModelAndView mv = new ModelAndView("stat/users/loginType");
		//总数
		mv.addObject("total", results.getNumFound());
		//柱状图数据
		mv.addObject("barData", StatUtils.barJson(facetFields));
		//饼状图数据
		mv.addObject("pieData", StatUtils.pieJson(facetFields));
		logger.info("进入用户注册统计！！！");
        return mv;
    }
}
