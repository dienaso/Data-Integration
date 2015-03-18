package com.epweike.controller;

import com.epweike.model.Users;
import com.epweike.service.UsersService;
import com.epweike.util.DateUtils;
import com.epweike.util.MD5Utils;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

/**
 * @author wuxp
 */
@Controller
public class UsersController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersService usersService;
    
    public List<Users> usersList;
    
    @RequestMapping(value="/users/oper", method=RequestMethod.POST)  
    public String oper(@Valid Users users,
    		@RequestParam(value = "oper", required = true) String oper,
    		BindingResult binding){  
        
    	logger.info("本次操作为：" +oper+ "," +users.toString() +"！！！");
    	
        if(oper.equals("add")) {
        	
        	if(binding.hasErrors()){ 
        		logger.info("验证不通过！！！" + binding.getAllErrors().toString());
                return "users/list";  
            } else {
            	users.setOnTime(DateUtils.formatDateTime(new Date()));
            	//密码MD5加密
            	String md5 = MD5Utils.getMD5(users.getPassword(), users.getUserName());
            	users.setPassword(md5);
            	this.usersService.insert(users);
            }
        	
        } else if (oper.equals("del")) {
        	
        	this.usersService.delete(users.getId());
        	
        } else if (oper.equals("edit")) {
        	
        	if(binding.hasErrors()){ 
        		logger.info("验证不通过！！！" + binding.getAllErrors().toString());
                return "users/list";  
            } else {
            	this.usersService.update(users);
            }
        	
        }
        
        return "/users/list";  
    }
    
    @RequestMapping(value = {"/users/list"})
    public String list(Model model) {
    	
    	Users users = new Users();
    	usersList = this.usersService.select(users);
    	JSONArray json = JSONArray.fromObject(usersList); 
    	model.addAttribute("users", json.toString());
    	logger.info("获取用户列表！！！"+json.toString());
    	
        return "users/list";
    }
    
}
