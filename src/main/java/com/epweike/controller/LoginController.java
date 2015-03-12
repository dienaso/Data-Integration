package com.epweike.controller;

import com.epweike.common.Constants;
import com.epweike.model.Sysuser;
import com.epweike.service.SysuserService;
import com.epweike.util.MD5Utils;
import com.epweike.util.ValidateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author wuxp
 */
@Controller
public class LoginController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	@Autowired
	private SysuserService sysuserService;

	@RequestMapping(value = { "/login", "login.html" })
	protected ModelAndView handle(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// 保存相应的参数，通过ModelAndView返回
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ValidateUtils.isRequired(username)
				&& !ValidateUtils.isRequired(password)) {
			// 密码MD5加密
			password = MD5Utils.getMD5(password);
			Sysuser user = checkUser(username, password);

			if (user != null) {
				model.put("user", user);
				logger.info("登陆成功！！！");
				return new ModelAndView("redirect:/index", model);
			} else {
				model.put("error", "用户名或密码输入错误！！！");
				logger.info("用户名或密码输入错误！！！");
				return new ModelAndView("/login", model);
			}
		} else {
			model.put("error", "用户名或密码不能为空！！！");
			logger.info("用户名或密码不能为空！！！");
			return new ModelAndView("/login", model);
		}

	}

	// 验证用户
	public Sysuser checkUser(String username, String password) {
		List<Sysuser> list = new ArrayList<Sysuser>();
		list = sysuserService.select(new Sysuser(username, password));
		if (list != null && list.size() > 0) {
			Sysuser sysuser = (Sysuser) list.get(0);

			// 设置session参数
			HttpSession session = getSession();
			session.setAttribute(Constants.USER_ID, sysuser.getId());
			session.setAttribute(Constants.USER_NAME, sysuser.getUserName());

			return sysuser;
		} else {
			return null;
		}
	}
}
