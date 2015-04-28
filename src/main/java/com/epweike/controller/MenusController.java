package com.epweike.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.epweike.model.Menus;
import com.epweike.model.PageModel;
import com.epweike.service.MenusService;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wuxp
 */
@Controller
public class MenusController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(MenusController.class);

    @Autowired
    private MenusService menuService;
    
    public List<Menus> menuList;
    
    @RequestMapping(value = {"/menu/list"})
    public String list(Model model) {
        return "menu/list";
    }
    
    @RequestMapping(value = "/menu/get", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String paginationDataTables(HttpServletRequest  request) throws IOException {
    	
    	//获取查询关键参数
    	String aoData = request.getParameter("aoData"); 
    	//解析查询关键参数
    	PageModel<Menus> pageModel = parsePageParamFromJson(aoData);
    	//搜索条件
    	String sSearch = pageModel.getsSearch();
    	//总条数
    	int total = this.menuService.count(new Menus());
    	//搜索结果数
    	int totalDisplay = this.menuService.count(new Menus(sSearch));
    	//搜索结果集
    	menuList = this.menuService.selectPage(new Menus(sSearch), pageModel);
		//Set Total display record
    	pageModel.setiTotalDisplayRecords(totalDisplay);
		//Set Total record
    	pageModel.setiTotalRecords(total);
    	pageModel.setAaData(menuList);
		
    	JSONObject json = JSONObject.fromObject(pageModel);
    	logger.info("获取菜单列表！！！"+json);
	
		return json.toString();
    }
    
    @RequestMapping(value = "/menu/del", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody int del(HttpServletRequest  request) throws IOException {
    	
    	//获取删除主键
    	int id = Integer.parseInt( request.getParameter("id") ); 
    	
    	System.out.println("--------------------"+id);
    	
    	int result = this.menuService.delete(id);
	
		return result;
    }
}
