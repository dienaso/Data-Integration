package com.epweike.controller;

import java.io.IOException;
import java.util.List;

import com.epweike.service.UsersService;
import com.epweike.util.ChartUtils;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wuxp
 */
@Controller
public class ChartsController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ChartsController.class);
	
	@Autowired
	private UsersService sysuserService;

	@RequestMapping(value = {"/charts/users/province"})
    public ModelAndView provinceStat() throws SolrServerException, IOException {
		
		String urlString = "http://solr.api.epweike.com/talent";
		SolrClient solr = new HttpSolrClient(urlString);
		SolrQuery parameters = new SolrQuery("*:*").setFacet(true).addFacetField("province");
		QueryResponse response = solr.query(parameters);
		SolrDocumentList results = response.getResults();
		
		//地区分布统计
		List<FacetField> facetFields = response.getFacetFields(); 
		
		solr.close();
		
		//返回视图
		ModelAndView mv = new ModelAndView("charts/users/province");
		//总数
		mv.addObject("total", results.getNumFound());
		//饼状图数据
		//mv.addObject("pieData", ChartUtils.pieJson(facetFields));
		//柱状图数据
		mv.addObject("barData", ChartUtils.barJson(facetFields));
		logger.info("进入用户分布统计！！！");
        return mv;
    }
	
	@RequestMapping(value = {"/charts/users/register"})
    public ModelAndView registerStat() throws SolrServerException, IOException {
		
		String urlString = "http://solr.api.epweike.com/talent";
		SolrClient solr = new HttpSolrClient(urlString);
		SolrQuery parameters = new SolrQuery("*:*").setFacet(true).addFacetField("province");
		QueryResponse response = solr.query(parameters);
		SolrDocumentList results = response.getResults();
		
		//地区分布统计
		List<FacetField> facetFields = response.getFacetFields(); 
		
		solr.close();
		
		//返回视图
		ModelAndView mv = new ModelAndView("charts/users/register");
		//总数
		mv.addObject("total", results.getNumFound());
		//柱状图数据
		mv.addObject("barData", ChartUtils.barJson(facetFields));
		logger.info("进入用户注册统计！！！");
        return mv;
    }
	
	@RequestMapping(value = {"/charts/users/loginType"})
    public ModelAndView loginTypeStat() throws SolrServerException, IOException {
		
		String urlString = "http://solr.api.epweike.com/login";
		SolrClient solr = new HttpSolrClient(urlString);
		SolrQuery parameters = new SolrQuery("*:*").setFacet(true).addFacetField("login_type");
		QueryResponse response = solr.query(parameters);
		SolrDocumentList results = response.getResults();
		
		//登录类型统计
		List<FacetField> facetFields = response.getFacetFields(); 
		
		solr.close();
		
		//返回视图
		ModelAndView mv = new ModelAndView("charts/users/loginType");
		//总数
		mv.addObject("total", results.getNumFound());
		//柱状图数据
		mv.addObject("barData", ChartUtils.barJson(facetFields));
		//饼状图数据
		mv.addObject("pieData", ChartUtils.pieJson(facetFields));
		logger.info("进入用户注册统计！！！");
        return mv;
    }
	
}
