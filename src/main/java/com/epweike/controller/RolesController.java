package com.epweike.controller;

import com.epweike.model.Roles;
import com.epweike.model.RetModel;
import com.epweike.model.PageModel;
import com.epweike.service.RolesService;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxp
 */
@Controller
@RequestMapping("/roles")
public class RolesController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(RolesController.class);

	@Autowired
	private RolesService rolesService;

	public List<Roles> rolesList;

	@RequestMapping(value = { "list" })
	public String list(Model model) {
		return "roles/list";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel add(HttpServletRequest request)
			throws IOException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取请求参数
		String roleName = request.getParameter("roleName");

		// 数据校验
		if ("".equals(roleName) || roleName == null) {
			retModel.setFlag(false);
			retModel.setMsg("角色名不能为空！");
			return retModel;
		} else {// 校验参数名是否存在
			Roles roles = new Roles(roleName);
			roles = rolesService.selectOne(roles);
			if (roles != null) {
				retModel.setFlag(false);
				retModel.setMsg("角色名:'" + roleName + "'已存在！");
				return retModel;
			}
		}

		Roles roles = new Roles();
		roles.setRoleName(roleName);

		try {
			// 添加到数据库
			rolesService.insert(roles);
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

		return retModel;

	}

	@RequestMapping(value = "del", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel del(HttpServletRequest request)
			throws IOException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取主键
		int id = Integer.parseInt(request.getParameter("id"));

		try {
			rolesService.deleteByPrimaryKey(id);
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

	@RequestMapping(value = "update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel update(HttpServletRequest request)
			throws IOException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取请求参数
		int id = Integer.parseInt(request.getParameter("id"));
		String roleName = request.getParameter("roleName");

		// 数据校验
		if ("".equals(roleName) || roleName == null) {
			retModel.setFlag(false);
			retModel.setMsg("角色名不能为空！");
			return retModel;
		} else {// 校验参数名是否存在
			Roles roles = new Roles(roleName);
			roles = rolesService.selectOne(roles);
			if (roles != null) {
				retModel.setFlag(false);
				retModel.setMsg("角色名:'" + roleName + "'已存在！");
				return retModel;
			}
		}

		Roles roles = new Roles();
		roles.setId(id);
		roles.setRoleName(roleName);

		try {
			// 更新数据
			rolesService.update(roles);
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

		return retModel;
	}

	@RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String get(HttpServletRequest request)
			throws IOException {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		// 解析查询关键参数
		PageModel<Roles> pageModel = parsePageParamFromJson(aoData);
		// 搜索条件
		String sSearch = pageModel.getsSearch();

		// 搜索结果集
		rolesList = rolesService.selectPage(new Roles(sSearch),
				pageModel);
		// 搜索结果数
		pageModel.setiTotalDisplayRecords(rolesList.size());
		pageModel.setiTotalRecords(rolesList.size());
		pageModel.setAaData(rolesList);

		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取角色列表！！！" + json);

		return json.toString();
	}

}
