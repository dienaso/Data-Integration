package com.epweike.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wuxp
 */
@Controller
public class LoginController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value = {"/login"})
    public String login(Model model) {
		logger.info("进入登录页面！！！");
        return "login";
    }
	
}
