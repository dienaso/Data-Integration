package com.epweike.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epweike.service.UsersService;

import net.sf.json.JSONArray;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.FacetField.Count;
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
public class IndexController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private UsersService sysuserService;
	
	@RequestMapping(value = {"/"})
    public ModelAndView index() throws SolrServerException, IOException {
		
		SolrQuery parameters = new SolrQuery("*:*").setFacet(true).addFacetField("province");
		
		QueryResponse response = getSolrServer("talent").query(parameters);
		
		//地区分布统计
		List<FacetField> facetFields = response.getFacetFields(); 
		List<Map<String, Object>> countList = new ArrayList<Map<String, Object>>();
		for (FacetField ff : facetFields) {
		     System.out.println("--------------------");
		     System.out.println("name=" + ff.getName() + "\tcount=" + ff.getValueCount());
		     System.out.println("--------------------");
		     switch (ff.getName()) {
	         	case "province"://按省份分布统计
			    for (Count count : ff.getValues()) {
			    	System.out.println("name=" + count.getName() + "\tcount=" + count.getCount());
			    	Map<String, Object> map = new HashMap<String, Object>();
			    	map.put("label", count.getName());
			    	map.put("data", count.getCount());
			    	countList.add(map);
		        }
			    break;
		     }
	    }
		//List to json
		String pieData = JSONArray.fromObject(countList).toString();
		System.out.println(pieData);
		
		//返回视图
		ModelAndView mv = new ModelAndView("dashboard");
		mv.addObject("pieData", pieData);
		
		logger.info("进入控制面板！！！");
        return mv;
    }
	
}
