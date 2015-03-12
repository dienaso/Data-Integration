package com.epweike.controller;

import com.epweike.model.Menu;
import com.epweike.service.MenuService;
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

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

/**
 * @author wuxp
 */
@Controller
public class MenuController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;
    
    public List<Menu> menuList;
    
    @RequestMapping(value="/menu/oper", method=RequestMethod.POST)  
    public String oper(@Valid Menu menu,
    		@RequestParam(value = "oper", required = true) String oper,
    		BindingResult binding){  
        
    	logger.info("本次操作为：" +oper+ "," +menu.toString() +"！！！");
    	
        if(oper.equals("add")) {
        	
        	if(binding.hasErrors()){ 
        		logger.info("验证不通过！！！" + binding.getAllErrors().toString());
                return "menu/list";  
            } else {
            	menu.setOnTime(DateUtils.formatDateTime(new Date()));
            	this.menuService.insert(menu);
            }
        	
        } else if (oper.equals("del")) {
        	
        	this.menuService.delete(menu.getId());
        	
        } else if (oper.equals("edit")) {
        	
        	if(binding.hasErrors()){ 
        		logger.info("验证不通过！！！" + binding.getAllErrors().toString());
                return "menu/list";  
            } else {
            	menu.setOnTime(DateUtils.formatDateTime(new Date()));
            	this.menuService.update(menu);
            }
        	
        }
        return "redirect:/menu/list";  
    }
    
    @RequestMapping(value = {"/menu/list"})
    public String list(Model model) {
    	
    	Menu menu = new Menu();
    	menuList = this.menuService.select(menu);
    	JSONArray json = JSONArray.fromObject(menuList); 
    	model.addAttribute("menu", json.toString());
    	
    	logger.info("获取菜单列表！！！"+json.toString());
    	
        return "menu/list";
    }
    
}
