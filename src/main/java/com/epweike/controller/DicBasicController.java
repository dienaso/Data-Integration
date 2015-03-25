package com.epweike.controller;

import com.epweike.model.DicBasic;
import com.epweike.service.DicBasicService;
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

import java.util.List;

import javax.validation.Valid;

/**
 * @author wuxp
 */
@Controller
public class DicBasicController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(DicBasicController.class);

    @Autowired
    private DicBasicService dicBasicService;
    
    public List<DicBasic> dicBasicList;
    
    @RequestMapping(value="/dicBasic/oper", method=RequestMethod.POST)  
    public String oper(@Valid DicBasic dicBasic,
    		@RequestParam(value = "oper", required = true) String oper,
    		BindingResult binding){  
        
    	logger.info("本次操作为：" +oper+ "," +dicBasic.toString() +"！！！");
    	
        if(oper.equals("add")) {
        	
        	if(binding.hasErrors()){ 
        		logger.info("验证不通过！！！" + binding.getAllErrors().toString());
                return "dicBasic/list";  
            } else {
            }
        	
        } else if (oper.equals("del")) {
        	
        	this.dicBasicService.delete(dicBasic.getId());
        	
        } else if (oper.equals("edit")) {
        	
        	if(binding.hasErrors()){ 
        		logger.info("验证不通过！！！" + binding.getAllErrors().toString());
                return "dicBasic/list";  
            } else {
            	this.dicBasicService.update(dicBasic);
            }
        	
        }
        
        return "/dicBasic/list";  
    }
    
    @RequestMapping(value = {"/dicBasic/list"})
    public String list(Model model) {
    	
    	dicBasicList = this.dicBasicService.selectPage(pageNum, pageSize);
    	JSONArray json = JSONArray.fromObject(dicBasicList); 
    	model.addAttribute("dicBasic", json.toString());
    	logger.info("获取词语列表！！！"+json.toString());
    	
        return "dicBasic/list";
    }
    
}
