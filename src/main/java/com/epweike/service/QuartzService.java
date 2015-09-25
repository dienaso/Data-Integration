package com.epweike.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epweike.mapper.ScheduleJobMapper;
import com.epweike.model.ScheduleJob;
import com.epweike.quartz.QuartzJobFactory;
import com.epweike.quartz.QuartzJobFactoryDisallowConcurrentExecution;

/**
 * 
 * @Description: quartz api
 * @author wuxp
 * 
 */
@Service
public class QuartzService extends BaseService<ScheduleJob> {
	public final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private ScheduleJobMapper scheduleJobMapper;

	private Scheduler scheduler = null;

	@PostConstruct
	public void init() throws Exception {
		scheduler = StdSchedulerFactory.getDefaultScheduler();

		if (!scheduler.isStarted()) {
			scheduler.start();
			// 这里获取任务信息数据
			ScheduleJob scheduleJob = new ScheduleJob();
			scheduleJob.setJobStatus(ScheduleJob.STATUS_RUNNING);
			List<ScheduleJob> jobList = scheduleJobMapper.select(scheduleJob);
			for (ScheduleJob job : jobList) {
				addJob(job);
			}
		}
	}

	/**
	 * 从数据库中取 区别于getAllJob
	 * 
	 * @return
	 */
	public List<ScheduleJob> getAllTask() {
		return scheduleJobMapper.select(new ScheduleJob());
	}

	/**
	 * 添加到数据库和计划任务
	 * 
	 * @throws SchedulerException
	 */
	public int addTaskJob(ScheduleJob job) throws SchedulerException {
		// 添加到数据库
		int result = scheduleJobMapper.insertUseGeneratedKeys(job);
		// 添加任务计划
		addJob(job);
		
		return result;
	}

	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void addJob(ScheduleJob job) throws SchedulerException {
		if (job == null
				|| !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			System.out.println("id:" + job.getId() + "----------------"
					+ "jobStatus:" + job.getJobStatus());
			return;
		}

		// Scheduler scheduler = schedulerFactoryBean.getScheduler();
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		log.debug(scheduler
				+ ".......................................................................................add");
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(),
				job.getJobGroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (null == trigger) {
			System.out
					.println("job.getIsConcurrent()=" + job.getIsConcurrent());
			Class<? extends Job> clazz = ScheduleJob.CONCURRENT_IS.equals(job
					.getIsConcurrent()) ? QuartzJobFactory.class
					: QuartzJobFactoryDisallowConcurrentExecution.class;

			JobDetail jobDetail = JobBuilder.newJob(clazz)
					.withIdentity(job.getJobName(), job.getJobGroup()).build();

			jobDetail.getJobDataMap().put("scheduleJob", job);

			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(job.getCronExpression());

			trigger = TriggerBuilder.newTrigger()
					.withIdentity(job.getJobName(), job.getJobGroup())
					.withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(job.getCronExpression());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
					.withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	/**
	 * 获取所有计划中的任务列表
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getAllJob() throws SchedulerException {
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler
					.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJob job = new ScheduleJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler
						.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}

	/**
	 * 所有正在运行的job
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		List<JobExecutionContext> executingJobs = scheduler
				.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(
				executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler
					.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(),
				scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(),
				scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 删除数据库记录和job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteTaskJob(int id) throws SchedulerException {
		ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(id);
		if (scheduleJob != null) {
			// 删除job
			deleteJob(scheduleJob);
			// 删除数据库
			scheduleJobMapper.deleteByPrimaryKey(id);
		}
	}

	/**
	 * 删除一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(),
				scheduleJob.getJobGroup());
		try {
			boolean deleteJob = scheduler.deleteJob(jobKey);
			System.out.println("------------------------------------deleteJob:"
					+ deleteJob);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 更改任务
	 * 
	 * @throws SchedulerException
	 */
	public int updateTaskJob(ScheduleJob job) throws SchedulerException {
		// 删除计划任务
		deleteJob(job);
		// 更新数据库
		int result = scheduleJobMapper.updateByPrimaryKeySelective(job);
		// 添加计划任务
		addJob(job);
		
		return result;
	}

	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(),
				scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

}
