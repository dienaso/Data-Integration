package com.epweike.controller;

import org.quartz.CronScheduleBuilder;
import org.apache.commons.lang.StringUtils;

import com.epweike.model.RetModel;
import com.epweike.model.ScheduleJob;
import com.epweike.model.PageModel;
import com.epweike.service.QuartzService;
import com.epweike.service.ScheduleJobService;
import com.epweike.util.SpringUtils;

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
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxp
 */
@Controller
@RequestMapping("/schedulejob")
public class ScheduleJobController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleJobController.class);
	
    @Autowired
    private ScheduleJobService scheduleJobService;
    
    @Autowired
    private QuartzService quartzService;
    
    public List<ScheduleJob> scheduleJobList;
    
    @RequestMapping(value = {"list"})
    public String list(Model model) {
        return "schedulejob/list";
    }
    
    
    /**  
    * @Description:校验计划任务合法性
    *  
    * @author  吴小平
    * @version 创建时间：Sep 22, 2015 10:49:07 AM
    */  
    private RetModel checkJob(ScheduleJob job, RetModel retModel) {
    	//返回响应结果
    	retModel.setFlag(false);
		try {
			CronScheduleBuilder.cronSchedule(job.getCronExpression());
		} catch (Exception e) {
			retModel.setMsg("cron表达式有误，不能被解析！");
			return retModel;
		}
		Object obj = null;
		try {
			if (StringUtils.isNotBlank(job.getSpringId())) {
				obj = SpringUtils.getBean(job.getSpringId());
				System.out.println("---------------------spring---------------------");
			} else {
				Class<?> clazz = Class.forName(job.getBeanClass());
				obj = clazz.newInstance();
			}
		} catch (Exception e) {
			// do nothing.........
		}
		if (obj == null) {
			retModel.setMsg("未找到执行类！");
			return retModel;
		} else {
			Class<? extends Object> clazz = obj.getClass();
			Method method = null;
			try {
				method = clazz.getMethod(job.getMethodName(), new Class[0]);
			} catch (Exception e) {
				// do nothing.....
			}
			if (method == null) {
				retModel.setMsg("未找到执行方法！");
				return retModel;
			}
		}
		retModel.setFlag(true);
    	return retModel;
    }
    
    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody RetModel add(HttpServletRequest  request) throws IOException {
    	//返回结果对象
        RetModel retModel = new RetModel();
    	//获取请求参数
    	String jobName = request.getParameter("jobName"); 
    	String jobGroup = request.getParameter("jobGroup"); 
    	String jobStatus = request.getParameter("jobStatus"); 
    	String cronExpression = request.getParameter("cronExpression"); 
    	String springId = request.getParameter("springId"); 
    	String beanClass = request.getParameter("beanClass"); 
    	String methodName = request.getParameter("methodName"); 
    	String isConcurrent = request.getParameter("isConcurrent"); 
    	String description = request.getParameter("description"); 
		ScheduleJob job = new ScheduleJob();
    	job.setJobName(jobName);
    	job.setJobGroup(jobGroup);
    	job.setJobStatus(jobStatus);
    	job.setCronExpression(cronExpression);
    	job.setSpringId(springId);
    	job.setBeanClass(beanClass);
    	job.setMethodName(methodName);
    	job.setIsConcurrent(isConcurrent);
    	job.setDescription(description);
    	job.setOnTime(new Date());
		job.setUpdateTime(new Date());
    	//校验计划任务
    	retModel = checkJob(job, retModel);
    	
    	if(retModel.isFlag()){
    		try {
    			//新增到数据库和计划任务
    			int result = quartzService.addTaskJob(job);
    			if(result > 0){
					retModel.setMsg("新增成功！");
				}else {
					retModel.setFlag(false);
					retModel.setMsg("新增失败！");
					return retModel;
				}
    		} catch (Exception e) {
    			e.printStackTrace();
    			retModel.setFlag(false);
    			retModel.setMsg("新增失败，检查 name group 组合是否有重复！");
    			return retModel;
    		}
    	}

		return retModel;
    	
    }
    
    @RequestMapping(value = "del", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody RetModel del(HttpServletRequest  request) throws IOException {
    	//返回结果对象
        RetModel retModel = new RetModel();
    	//获取主键
    	int id = Integer.parseInt(request.getParameter("id")); 
    	
		try {
			quartzService.deleteTaskJob(id);
			retModel.setMsg("删除成功！");
		} catch (Exception e) {
			retModel.setFlag(false);
			retModel.setMsg("删除失败！");
			retModel.setObj(e);
			e.printStackTrace();
		}
	
		return retModel;
    }
    
    @RequestMapping(value = "update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody RetModel update(HttpServletRequest  request) throws IOException {
    	//返回结果对象
        RetModel retModel = new RetModel();
    	//获取请求参数
    	String id = request.getParameter("id"); 
    	String jobName = request.getParameter("jobName"); 
    	String jobGroup = request.getParameter("jobGroup"); 
    	String jobStatus = request.getParameter("jobStatus"); 
    	String cronExpression = request.getParameter("cronExpression"); 
    	String springId = request.getParameter("springId"); 
    	String beanClass = request.getParameter("beanClass"); 
    	String methodName = request.getParameter("methodName"); 
    	String isConcurrent = request.getParameter("isConcurrent"); 
    	String description = request.getParameter("description"); 
		ScheduleJob job = new ScheduleJob();
    	job.setId(Integer.parseInt(id));
    	job.setJobName(jobName);
    	job.setJobGroup(jobGroup);
    	job.setJobStatus(jobStatus);
    	job.setCronExpression(cronExpression);
    	job.setSpringId(springId);
    	job.setBeanClass(beanClass);
    	job.setMethodName(methodName);
    	job.setIsConcurrent(isConcurrent);
    	job.setDescription(description);
    	job.setUpdateTime(new Date());
    	
    	//校验计划任务
    	retModel = checkJob(job, retModel);
    	
    	if(retModel.isFlag()){
			try {
				//更新数据库和计划任务
				int result = quartzService.updateTaskJob(job);
				if(result > 0){
					retModel.setMsg("修改成功！");
				}else {
					retModel.setFlag(false);
					retModel.setMsg("修改失败！");
					return retModel;
				}
			} catch (Exception e) {
				e.printStackTrace();
				retModel.setFlag(false);
				retModel.setMsg("保存失败，检查 name group 组合是否有重复！");
				return retModel;
			}
    	}

    	return retModel;
    	
    }
    
    @RequestMapping(value = "run", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody RetModel run(HttpServletRequest  request) throws IOException {
    	//返回结果对象
        RetModel retModel = new RetModel();
    	//获取主键
    	int id = Integer.parseInt(request.getParameter("id")); 
    	ScheduleJob scheduleJob = this.scheduleJobService.get(id);
		try {
			quartzService.runAJobNow(scheduleJob);
			retModel.setMsg("执行成功！");
		} catch (Exception e) {
			retModel.setFlag(false);
			retModel.setMsg("执行失败,或许该任务被禁用！");
			retModel.setObj(e);
			e.printStackTrace();
		}
	
		return retModel;
    }
    
	@RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String get(HttpServletRequest request)
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
		logger.info("获取计划任务列表！！！" + json);

		return json.toString();
	}
    
}
