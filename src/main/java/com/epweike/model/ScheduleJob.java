package com.epweike.model;

import java.util.Date;

import javax.persistence.*;

@Table(name = "schedule_job")
public class ScheduleJob extends BaseModel<ScheduleJob> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String STATUS_RUNNING = "1";
	public static final String STATUS_NOT_RUNNING = "0";
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";
	
    @Id
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "job_group")
    private String jobGroup;

    @Column(name = "job_status")
    private String jobStatus;

    @Column(name = "cron_expression")
    private String cronExpression;

    private String description;

    @Column(name = "bean_class")
    private String beanClass;

    /**
     * 1
     */
    @Column(name = "is_concurrent")
    private String isConcurrent;

    @Column(name = "spring_id")
    private String springId;

    @Column(name = "method_name")
    private String methodName;

    /**
     * @return job_id
     */
    public Long getJobId() {
        return jobId;
    }

    /**
     * @param jobId
     */
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return job_name
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * @param jobName
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * @return job_group
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * @param jobGroup
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    /**
     * @return job_status
     */
    public String getJobStatus() {
        return jobStatus;
    }

    /**
     * @param jobStatus
     */
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * @return cron_expression
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * @param cronExpression
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return bean_class
     */
    public String getBeanClass() {
        return beanClass;
    }

    /**
     * @param beanClass
     */
    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * 获取是否允许并行
     *
     * @return is_concurrent
     */
    public String getIsConcurrent() {
        return isConcurrent;
    }

    /**
     * 设置获取是否允许并行 1:允许
     *
     * @param isConcurrent
     */
    public void setIsConcurrent(String isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    /**
     * @return spring_id
     */
    public String getSpringId() {
        return springId;
    }

    /**
     * @param springId
     */
    public void setSpringId(String springId) {
        this.springId = springId;
    }

    /**
     * @return method_name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @param methodName
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}