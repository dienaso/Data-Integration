package com.epweike.controller;

import com.epweike.model.Lexicons;
import com.epweike.model.PageModel;
import com.epweike.service.LexiconsService;
import com.github.pagehelper.Page;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxp
 */
@Controller
public class LexiconsController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(LexiconsController.class);

    @Autowired
    private LexiconsService lexiconService;
    
    public List<Lexicons> lexiconList;
    
    @RequestMapping(value = {"/lexicon/list"})
    public String list(Model model) {
        return "lexicon/list";
    }
    
    @RequestMapping(value = "/lexicon/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody String paginationDataTables(HttpServletRequest  request) throws IOException {
    	
    	//获取查询关键参数
    	String aoData = request.getParameter("aoData"); 
    	//解析查询关键参数
    	PageModel<Lexicons> pageModel = parsePageParamFromJson(aoData);
    	//搜索条件
    	//String sSearch = pageModel.getsSearch();
    	//总条数
    	int total = this.lexiconService.count(new Lexicons());
    	
    	//搜索结果集
    	lexiconList = this.lexiconService.selectAll(pageModel);
    	//搜索结果数
    	long totalDisplay = ((Page<Lexicons>) lexiconList).getTotal();
    	pageModel.setiTotalDisplayRecords(totalDisplay);
    	pageModel.setiTotalRecords(total);
    	pageModel.setAaData(lexiconList);
		
    	JSONObject json = JSONObject.fromObject(pageModel);
    	logger.info("获取词语列表！！！"+json);
	
		return json.toString();
    }
    
    @RequestMapping(value = "/lexicon/del", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody int del(HttpServletRequest  request) throws IOException {
    	
    	//获取删除主键
    	String id = request.getParameter("id"); 
    	
    	int result = this.lexiconService.delete(id);
	
		return result;
    }
    
}
