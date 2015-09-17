package com.epweike.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.epweike.model.ScheduleJob;
import com.epweike.util.TaskUtils;

/**
 * 
 * @Description: 计划任务执行处 无状态
 * @author wuxp
 * 
 */
public class QuartzJobFactory implements Job {
	public final Logger log = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		System.out.println(scheduleJob.toString());
		TaskUtils.invokMethod(scheduleJob);
	}
}