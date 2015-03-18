package com.epweike.controller;

import com.epweike.service.UsersService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wuxp
 */
@Controller
public class LoginController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UsersService sysuserService;

	@RequestMapping(value = {"/"})
    public String index(Model model) {
        return "index";
    }
	
	@RequestMapping(value = {"/login"})
    public String login(Model model) {
        return "login";
    }
	
	@RequestMapping(value = {"/admin"})
    public String list(Model model) {
    	logger.info("管理员控制台！！！");
        return "admin";
    }

}
