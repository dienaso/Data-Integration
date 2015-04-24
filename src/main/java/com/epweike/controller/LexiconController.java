package com.epweike.controller;

import com.epweike.model.Lexicon;
import com.epweike.service.LexiconService;

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
public class LexiconController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(LexiconController.class);

    @Autowired
    private LexiconService lexiconService;
    
    public List<Lexicon> lexiconList;
    
    @RequestMapping(value="/lexicon/oper", method=RequestMethod.POST)  
    public String oper(@Valid Lexicon lexicon,
    		@RequestParam(value = "oper", required = true) String oper,
    		BindingResult binding){  
        
    	logger.info("本次操作为：" +oper+ "," +lexicon.toString() +"！！！");
    	
        if(oper.equals("add")) {
        	
        	if(binding.hasErrors()){ 
        		logger.info("验证不通过！！！" + binding.getAllErrors().toString());
                return "lexicon/list";  
            } else {
            }
        	
        } else if (oper.equals("del")) {
        	
        	this.lexiconService.delete(lexicon.getId());
        	
        } else if (oper.equals("edit")) {
        	
        	if(binding.hasErrors()){ 
        		logger.info("验证不通过！！！" + binding.getAllErrors().toString());
                return "lexicon/list";  
            } else {
            	this.lexiconService.update(lexicon);
            }
        	
        }
        
        return "/lexicon/list";  
    }
    
    @RequestMapping(value = {"/lexicon/list"})
    public String list(Model model) {
    	
    	lexiconList = this.lexiconService.selectPage(pageNum, pageSize);
    	model.addAttribute("lexicon", lexiconList);
    	logger.info("获取词语列表！！！"+lexiconList.toString());
    	
        return "lexicon/list";
    }
    
}
