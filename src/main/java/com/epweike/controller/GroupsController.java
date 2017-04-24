package com.epweike.controller;

import com.epweike.model.GroupAuthorities;
import com.epweike.model.Groups;
import com.epweike.model.RetModel;
import com.epweike.model.PageModel;
import com.epweike.model.Roles;
import com.epweike.service.GroupAuthoritiesService;
import com.epweike.service.GroupsService;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxp
 */
@Controller
@RequestMapping("/groups")
public class GroupsController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(GroupsController.class);

	@Autowired
	private GroupsService groupsService;
	
	@Autowired
	private GroupAuthoritiesService groupAuthoritiesService;
	
	@Autowired
	private RolesService rolesService;
	
	public List<Groups> groupsList;

	@RequestMapping(value = { "list" })
	public String list(Model model) {
		//获取所有角色
		model.addAttribute("roleList", getGroupAuthorities(0));
		return "groups/list";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel add(HttpServletRequest request)
			throws IOException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取请求参数
		String groupName = request.getParameter("groupName");

		// 数据校验
		if ("".equals(groupName) || groupName == null) {
			retModel.setFlag(false);
			retModel.setMsg("用户组名不能为空！");
			return retModel;
		} else {// 校验参数名是否存在
			Groups groups = new Groups(groupName);
			groups = groupsService.selectOne(groups);
			if (groups != null) {
				retModel.setFlag(false);
				retModel.setMsg("用户组名:'" + groupName + "'已存在！");
				return retModel;
			}
		}

		Groups groups = new Groups();
		groups.setGroupName(groupName);

		try {
			// 添加到数据库
			groupsService.insert(groups);
			retModel.setInsertSucceed();
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
			groupsService.deleteByPrimaryKey(id);
			retModel.setDelSucceed();
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
		long id = Long.parseLong(request.getParameter("id"));
		String groupName = request.getParameter("groupName");

		// 数据校验
		if ("".equals(groupName) || groupName == null) {
			retModel.setFlag(false);
			retModel.setMsg("用户组名不能为空！");
			return retModel;
		} else {// 校验参数名是否存在
			Groups groups = new Groups(groupName);
			groups = groupsService.selectOne(groups);
			if (groups != null) {
				retModel.setFlag(false);
				retModel.setMsg("用户组名:'" + groupName + "'已存在！");
				return retModel;
			}
		}

		Groups groups = new Groups();
		groups.setId(id);
		groups.setGroupName(groupName);

		try {
			// 更新数据
			groupsService.update(groups);
			retModel.setUpdateSucceed();
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
		PageModel<Groups> pageModel = parsePageParamFromJson(aoData);
		// 搜索条件
		String sSearch = pageModel.getsSearch();

		// 搜索结果集
		groupsList = groupsService.selectPage(new Groups(sSearch),
				pageModel);
		// 搜索结果数
		pageModel.setiTotalDisplayRecords(groupsList.size());
		pageModel.setiTotalRecords(groupsList.size());
		pageModel.setAaData(groupsList);

		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取用户组列表！！！" + json);

		return json.toString();
	}

	/**  
	* @Description:获取用户组权限
	* 
	* @author  吴小平
	* @version 创建时间：Nov 26, 2015 10:26:50 AM
	*/  
	public List<Roles> getGroupAuthorities(long groupId) {
		
		List<Roles> roleList = new ArrayList<Roles>();
		
		if(groupId == 0){//获取所有角色
			roleList = this.rolesService.selectAll();
		} else{
			List<GroupAuthorities> groupAuthorities = this.groupAuthoritiesService.select(new GroupAuthorities(groupId));
			Iterator<GroupAuthorities> it = groupAuthorities.iterator();
			while(it.hasNext()){
				GroupAuthorities ga = it.next();
				Roles roles = new Roles(ga.getAuthority());
				roleList.add(roles);
			}
		}
		
		System.out.println("rolelist:"+roleList.toString());
		return roleList;
	}
}
