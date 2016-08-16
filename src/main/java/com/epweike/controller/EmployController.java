package com.epweike.controller;

import com.epweike.model.Employ;
import com.epweike.model.PageModel;
import com.epweike.model.RetModel;
import com.epweike.service.EmployService;

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
@RequestMapping("/employ")
public class EmployController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(EmployController.class);

	@Autowired
	private EmployService employService;

	public List<Employ> employList;
	
	public void insert(Employ employ){
		this.employService.insert(employ);
	}

	@RequestMapping(value = { "list" })
	public String list(Model model) {
		return "employ/list";
	}
	
	@RequestMapping(value = { "list2" })
	public String list2(Model model) {
		return "employ/list2";
	}


	@RequestMapping(value = "del", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel RetModel(HttpServletRequest request)
			throws IOException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取主键
		int id = Integer.parseInt(request.getParameter("id"));

		try {
			this.employService.deleteByPrimaryKey(id);
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

	@RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String paginationDataTables(HttpServletRequest request)
			throws IOException {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		// 解析查询关键参数
		PageModel<Employ> pageModel = parsePageParamFromJson(aoData);
		// 总条数(无过滤)
		int total = this.employService.count(new Employ(0));

		// 搜索结果集
		employList = this.employService.selectPage(new Employ(0),
				pageModel);
		// 搜索结果数
		pageModel.setiTotalDisplayRecords(total);
		pageModel.setiTotalRecords(total);
		pageModel.setAaData(employList);
		
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取招聘列表！！！" + json);

		return json.toString();
	}
	
	@RequestMapping(value = "getEmployee", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getEmployee(HttpServletRequest request)
			throws IOException {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		// 解析查询关键参数
		PageModel<Employ> pageModel = parsePageParamFromJson(aoData);
		// 总条数(无过滤)
		int total = this.employService.count(new Employ(1));

		// 搜索结果集
		employList = this.employService.selectPage(new Employ(1),
				pageModel);
		// 搜索结果数
		pageModel.setiTotalDisplayRecords(total);
		pageModel.setiTotalRecords(total);
		pageModel.setAaData(employList);
		
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取求职列表！！！" + json);

		return json.toString();
	}

}
