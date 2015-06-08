package com.epweike.controller;

import com.epweike.model.PageModel;
import com.epweike.model.Users;
import com.epweike.service.UsersService;

import net.sf.json.JSONObject;

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

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxp
 */
@Controller
public class UsersController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

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
}
