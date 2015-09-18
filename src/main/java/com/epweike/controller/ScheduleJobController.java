package com.epweike.controller;

import com.epweike.model.ScheduleJob;
import com.epweike.model.PageModel;
import com.epweike.service.ScheduleJobService;

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
public class ScheduleJobController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleJobController.class);

    @Autowired
    private ScheduleJobService scheduleJobService;
    
    public List<ScheduleJob> scheduleJobList;
    
    @RequestMapping(value = {"/schedulejob/list"})
    public String list(Model model) {
        return "schedulejob/list";
    }
    
    @RequestMapping(value = "/schedulejob/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody int update(HttpServletRequest  request) throws IOException {
    	
    	//获取主键
    	String id = request.getParameter("pk"); 
    	
    	System.out.println("--------------------"+id);
    	
    	//int result = this.scheduleJobService.update(new ScheduleJob());
    	int result = 0;
		return result;
    }
    
	@RequestMapping(value = "/schedulejob/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String paginationDataTables(HttpServletRequest request)
			throws IOException {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		// 解析查询关键参数
		PageModel<ScheduleJob> pageModel = parsePageParamFromJson(aoData);
		// 搜索条件
		String sSearch = pageModel.getsSearch();
		// 总条数(无过滤)
		int total = this.scheduleJobService.count(new ScheduleJob());

		// 搜索结果集
		scheduleJobList = this.scheduleJobService.selectPage(new ScheduleJob(sSearch),
				pageModel);
		// 搜索结果数
		pageModel.setiTotalDisplayRecords(total);
		pageModel.setiTotalRecords(total);
		pageModel.setAaData(scheduleJobList);

		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取词语列表！！！" + json);

		return json.toString();
	}
    
    @RequestMapping(value = "/schedulejob/del", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody int del(HttpServletRequest  request) throws IOException {
    	
    	//获取删除主键
    	String id = request.getParameter("id"); 
    	
    	int result = this.scheduleJobService.delete(id);
	
		return result;
    }
    
}
