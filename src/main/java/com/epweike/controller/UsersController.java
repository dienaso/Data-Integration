package com.epweike.controller;

import com.epweike.model.PageModel;
import com.epweike.model.RetModel;
import com.epweike.model.Users;
import com.epweike.service.UsersService;
import com.epweike.util.MD5Utils;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxp
 */
@Controller
@RequestMapping("/users")
public class UsersController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(UsersController.class);

	@Autowired
	private UsersService usersService;

	public List<Users> usersList;

	public User user;

	@RequestMapping(value = { "list" })
	public String list(Model model) {
		user = (User) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		return "users/list";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel add(HttpServletRequest request)
			throws IOException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取请求参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String enabled = request.getParameter("enabled");

		// 数据校验
		if ("".equals(username) || username == null) {
			retModel.setFlag(false);
			retModel.setMsg("用户名不能为空！");
			return retModel;
		} else if ("".equals(password) || password == null) {
			retModel.setFlag(false);
			retModel.setMsg("密码不能为空！");
			return retModel;
		} else {// 校验用户名是否存在
			Users users = new Users(username);
			users = usersService.selectOne(users);
			if (users != null) {
				retModel.setFlag(false);
				retModel.setMsg("用户:'" + username + "'已存在！");
				return retModel;
			}
		}

		// md5加密
		password = MD5Utils.getMD5(password, username);

		Users users = new Users();
		users.setUserName(username);
		users.setPassword(password);
		;
		users.setEmail(email);
		users.setTel(tel);
		users.setEnabled(Integer.parseInt(enabled));
		users.setOnTime(new Date());

		if (retModel.isFlag()) {
			try {
				// 新增到数据库和计划任务
				usersService.insert(users);
				retModel.setInsertFucceed();
			} catch (AccessDeniedException e) {
				e.printStackTrace();
				retModel.setAccessDenied(e);
				return retModel;
			} catch (Exception e) {
				e.printStackTrace();
				retModel.setInsertFail(e);
				return retModel;
			}
		}

		return retModel;

	}

	@RequestMapping(value = "del", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel del(HttpServletRequest request)
			throws IOException {

		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取主键
		int id = Integer.parseInt(request.getParameter("id"));

		try {
			this.usersService.delete(id);
			retModel.setDelFucceed();
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			retModel.setAccessDenied(e);
			return retModel;
		} catch (Exception e) {
			retModel.setDelFail(e);
			e.printStackTrace();
		}

		return retModel;
	}

	@RequestMapping(value = "changePassword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel changePassword(HttpServletRequest request)
			throws IOException {

		RetModel retModel = new RetModel();
		// 获取请求参数
		String userName = request.getParameter("username");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");

		// 判断新密码与确认密码是否一致
		if (!newPassword.equals(confirmPassword)) {
			retModel.setFlag(false);
			retModel.setMsg("新密码与确认密码不一致！");
			return retModel;
		}
		// 判断旧密码是否正确
		Users users = new Users();
		users.setUserName(userName);
		users.setPassword(MD5Utils.getMD5(oldPassword, userName));
		Users existUsers = usersService.selectOne(users);
		if (existUsers == null) {
			retModel.setFlag(false);
			retModel.setMsg("旧密码不正确！");
			return retModel;
		}

		// 修改密码
		if (retModel.isFlag()) {
			try {
				users.setPassword(MD5Utils.getMD5(newPassword, userName));
				int result = usersService.updateBySelective(users);
				if (result > 0) {
					retModel.setMsg("密码修改成功！");
				} else {
					retModel.setFlag(false);
					retModel.setMsg("密码修改失败！");
					return retModel;
				}
			} catch (Exception e) {
				e.printStackTrace();
				retModel.setFlag(false);
				retModel.setMsg("添加失败！");
				return retModel;
			}
		}

		return retModel;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel update(HttpServletRequest request)
			throws IOException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取请求参数
		int id = Integer.parseInt(request.getParameter("id"));
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String enabled = request.getParameter("enabled");

		// 数据校验
		if ("".equals(username) || username == null) {
			retModel.setFlag(false);
			retModel.setMsg("用户名不能为空！");
			return retModel;
		} else if ("".equals(password) || password == null) {
			retModel.setFlag(false);
			retModel.setMsg("密码不能为空！");
			return retModel;
		}

		// md5加密
		password = MD5Utils.getMD5(password, username);

		Users users = new Users();
		users.setId(id);
		users.setUserName(username);
		users.setPassword(password);
		;
		users.setEmail(email);
		users.setTel(tel);
		users.setEnabled(Integer.parseInt(enabled));

		if (retModel.isFlag()) {
			try {
				// 更新数据库和计划任务
				usersService.update(users);
				retModel.setUpdateFucceed();
			} catch (AccessDeniedException e) {
				e.printStackTrace();
				retModel.setAccessDenied(e);
				return retModel;
			} catch (Exception e) {
				e.printStackTrace();
				retModel.setUpdateFail(e);
				return retModel;
			}
		}

		return retModel;
	}

	/**
	 * @Description:ajax获取系统用户列表
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午3:28:27
	 */
	@RequestMapping(value = "get", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody String paginationDataTables(HttpServletRequest request)
			throws IOException {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		// 解析查询关键参数
		PageModel<Users> pageModel = parsePageParamFromJson(aoData);
		// 搜索条件
		String sSearch = pageModel.getsSearch();
		// 总条数
		int total = this.usersService.count(new Users());
		// 搜索结果集
		usersList = this.usersService.selectPage(new Users(sSearch), pageModel);
		pageModel.setiTotalDisplayRecords(total);
		pageModel.setiTotalRecords(total);
		pageModel.setAaData(usersList);

		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取用户列表！！！" + json);

		return json.toString();
	}
}
