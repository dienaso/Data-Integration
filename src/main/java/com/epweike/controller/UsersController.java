package com.epweike.controller;

import com.epweike.model.PageModel;
import com.epweike.model.Users;
import com.epweike.service.UsersService;
import com.epweike.util.StatUtils;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxp
 */
@Controller
public class UsersController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
	
	public static final String urlCoreString = SOLR_URL + "talent";

    @Autowired
    private UsersService usersService;
    
    public List<Users> usersList;
    
    public User user;
    
    @RequestMapping(value = {"/users/list"})
    public String list(Model model) {
    	user = (User) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    	
        return "users/list";
    }
    
    /**  
	* @Description:ajax获取系统用户列表
	*  
	* @author  吴小平
	* @version 创建时间：2015年6月10日 下午3:28:27
	*/
    @RequestMapping(value = "/users/get", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String paginationDataTables(HttpServletRequest  request) throws IOException {
    	
    	//获取查询关键参数
    	String aoData = request.getParameter("aoData"); 
    	//解析查询关键参数
    	PageModel<Users> pageModel = parsePageParamFromJson(aoData);
    	//搜索条件
    	String sSearch = pageModel.getsSearch();
    	//总条数
    	int total = this.usersService.count(new Users());
    	//搜索结果集
    	usersList = this.usersService.selectPage(new Users(sSearch), pageModel);
    	pageModel.setiTotalDisplayRecords(total);
    	pageModel.setiTotalRecords(total);
    	pageModel.setAaData(usersList);
		
    	JSONObject json = JSONObject.fromObject(pageModel);
    	logger.info("获取用户列表！！！"+json);
	
		return json.toString();
    }
    
    @RequestMapping(value = "/users/del", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody int del(HttpServletRequest  request) throws IOException {
    	
    	//获取删除主键
    	String userName = request.getParameter("id"); 
    	
    	System.out.println("--------------------"+userName);
    	
    	int result = this.usersService.delete(userName);
	
		return result;
    }
    
    /**  
	* @Description:一品用户省份分布统计
	*  
	* @author  吴小平
	* @version 创建时间：2015年6月10日 下午3:28:27
	*/
    @RequestMapping(value = {"/stat/users/province"})
    public ModelAndView provinceStat() throws SolrServerException, IOException {
		
		SolrQuery parameters = new SolrQuery("*:*").setFacet(true).addFacetField("province");
		QueryResponse response = getSolrServer("talent").query(parameters);
		SolrDocumentList results = response.getResults();
		
		//地区分布统计
		List<FacetField> facetFields = response.getFacetFields(); 
		
		//返回视图
		ModelAndView mv = new ModelAndView("stat/users/province");
		//总数
		mv.addObject("total", results.getNumFound());
		//饼状图数据
		//mv.addObject("pieData", ChartUtils.pieJson(facetFields));
		//柱状图数据
		mv.addObject("barData", StatUtils.barJson(facetFields));
		logger.info("进入用户分布统计！！！");
        return mv;
    }
	
	/**  
	* @Description:一品用户注册统计
	*  
	* @author  吴小平
	* @version 创建时间：2015年6月10日 下午3:28:27
	*/  
	@RequestMapping(value = {"/stat/users/register"})
    public ModelAndView registerStat() throws SolrServerException, IOException {
		
		SolrQuery parameters = new SolrQuery("*:*").setFacet(true).addFacetField("province");
		QueryResponse response = getSolrServer("talent").query(parameters);
		SolrDocumentList results = response.getResults();
		
		//地区分布统计
		List<FacetField> facetFields = response.getFacetFields(); 
		
		//返回视图
		ModelAndView mv = new ModelAndView("stat/users/register");
		//总数
		mv.addObject("total", results.getNumFound());
		//柱状图数据
		mv.addObject("barData", StatUtils.barJson(facetFields));
		logger.info("进入用户注册统计！！！");
        return mv;
    }
}
