package com.epweike.controller;

import com.epweike.model.Company;
import com.epweike.model.PageModel;
import com.epweike.model.RetModel;
import com.epweike.service.CompanyService;

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
@RequestMapping("/company")
public class CompanyController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(CompanyController.class);

	@Autowired
	private CompanyService companyService;

	public List<Company> companyList;
	
	public void insert(Company company){
		this.companyService.insert(company);
	}

	@RequestMapping(value = { "list" })
	public String list(Model model) {
		return "company/list";
	}


	@RequestMapping(value = "del", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel RetModel(HttpServletRequest request)
			throws IOException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取主键
		int id = Integer.parseInt(request.getParameter("id"));

		try {
			this.companyService.deleteByPrimaryKey(id);
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

	@RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String paginationDataTables(HttpServletRequest request)
			throws IOException {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		// 解析查询关键参数
		PageModel<Company> pageModel = parsePageParamFromJson(aoData);
		// 搜索条件
		String sSearch = pageModel.getsSearch();
		// 总条数(无过滤)
		int total = this.companyService.count(new Company());

		// 搜索结果集
		companyList = this.companyService.selectPage(new Company(sSearch),
				pageModel);
		// 搜索结果数
		pageModel.setiTotalDisplayRecords(total);
		pageModel.setiTotalRecords(total);
		pageModel.setAaData(companyList);
		
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取企业列表！！！" + json);

		return json.toString();
	}

}
