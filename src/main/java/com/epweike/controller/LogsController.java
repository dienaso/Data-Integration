package com.epweike.controller;

import com.epweike.model.LogsStat;
import com.epweike.model.PageModel;
import com.epweike.service.LogsStatService;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/logs")
public class LogsController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(LogsController.class);

	@Autowired
	private LogsStatService logsStatService;

	public List<LogsStat> logsStatList;

	@RequestMapping(value = { "flow" })
	public String flow(Model model) {
		return "logs/flow";
	}
	
	@RequestMapping(value = { "userAgent" })
	public String userAgent(Model model) {
		return "logs/userAgent";
	}

	@RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String paginationDataTables(HttpServletRequest request)
			throws IOException {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		// 解析查询关键参数
		PageModel<LogsStat> pageModel = parsePageParamFromJson(aoData);
		// 总条数(无过滤)
		int total = this.logsStatService.count(new LogsStat());

		// 搜索结果集
		logsStatList = this.logsStatService.selectPage(new LogsStat(),
				pageModel);
		// 搜索结果数
		pageModel.setiTotalDisplayRecords(total);
		pageModel.setiTotalRecords(total);
		pageModel.setAaData(logsStatList);

		JSONObject json = JSONObject.fromObject(pageModel);

		logger.info(json.toString());
		
		return json.toString();
	}

}
