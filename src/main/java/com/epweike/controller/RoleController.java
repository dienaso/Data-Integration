package com.epweike.controller;

import com.epweike.model.Role;
import com.epweike.service.RoleService;
import com.epweike.util.DateUtils;

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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

/**
 * @author wuxp
 */
@Controller
public class RoleController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    
    public List<Role> roleList;
    
    @RequestMapping(value="/role/oper", method=RequestMethod.POST)  
    public String oper(@Valid Role role,
    		@RequestParam(value = "oper", required = true) String oper,
    		BindingResult binding){  
        
    	logger.info("本次操作为：" +oper+ "," +role.toString() +"！！！");
    	
        if(oper.equals("add")) {
        	
        	if(binding.hasErrors()){ 
        		logger.info("验证不通过！！！" + binding.getAllErrors().toString());
                return "role/list";  
            } else {
            	
            }
        	
        } else if (oper.equals("del")) {
        	
        	
        	
        } else if (oper.equals("edit")) {
        	
        	if(binding.hasErrors()){ 
        		logger.info("验证不通过！！！" + binding.getAllErrors().toString());
                return "role/list";  
            } else {
            	
            }
        	
        }
        
        return "redirect:/role/list";  
    }
    
    @RequestMapping(value = {"/role/list"})
    public String list(Model model) {
    	
    	Role role = new Role();
    	roleList = this.roleService.select(role);
    	JSONArray json = JSONArray.fromObject(roleList); 
    	model.addAttribute("role", json.toString());
    	
    	logger.info("获取角色列表！！！"+json.toString());
    	
        return "role/list";
    }
    
}
