package com.epweike.controller;

import com.epweike.model.Sysuser;
import com.epweike.service.SysuserService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

/**
 * @author wuxp
 */
@Controller
public class SysuserController {
	private static final Logger logger = LoggerFactory.getLogger(SysuserController.class);

    @Autowired
    private SysuserService sysuserService;
    
    public List<Sysuser> sysuserList;
    
    @RequestMapping(value="/sysuser/oper", method=RequestMethod.POST)  
    public String oper(@Valid Sysuser sysuser,
    		@RequestParam(value = "oper", required = true) String oper,
    		BindingResult binding){  
        
    	logger.info("本次操作为：" +oper+ "," +sysuser.toString() +"！！！");
    	
        if(oper.equals("add")) {
        	
        	if(binding.hasErrors()){ 
        		logger.info("验证不通过！！！" + binding.getAllErrors().toString());
                return "sysuser/list";  
            } else {
            	sysuser.setOnTime(DateUtils.formatDateTime(new Date()));
            	//密码MD5加密
            	String md5 = MD5Utils.getMD5(sysuser.getPassword());
            	sysuser.setPassword(md5);
            	this.sysuserService.insert(sysuser);
            }
        	
        } else if (oper.equals("del")) {
        	
        	this.sysuserService.delete(sysuser.getId());
        	
        } else if (oper.equals("edit")) {
        	
        	if(binding.hasErrors()){ 
        		logger.info("验证不通过！！！" + binding.getAllErrors().toString());
                return "sysuser/list";  
            } else {
            	this.sysuserService.update(sysuser);
            }
        	
        }
        
        return "redirect:/sysuser/list";  
    }
    
    @RequestMapping(value = {"/sysuser/list"})
    public String list(Model model) {
    	
    	Sysuser sysuser = new Sysuser();
    	sysuserList = this.sysuserService.select(sysuser);
    	JSONArray json = JSONArray.fromObject(sysuserList); 
    	model.addAttribute("sysuser", json.toString());
    	
    	logger.info("获取用户列表！！！"+json.toString());
    	
        return "sysuser/list";
    }
    
    @ResponseBody
    @RequestMapping("test3")
    public List<Sysuser> requestTest7(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        sysuserService.test();
        sysuserService.testEntityMapper();
        return sysuserService.selectPage(pageNum, pageSize);
    }
}
