package com.oo.businessplan.quzrtz.pojo;

import com.oo.businessplan.basic.entity.DeleteAbleEntity;

/**
 * 时钟实体类
 * @author cyz
 *
 */
public class SysTaskCronJob extends DeleteAbleEntity<Integer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 341645549334498059L;

	// Job名称
	private String jobName;
	
	private String jobKey;
	
	// cron表达式
	private String cron;
 
	// Job相关的类全名
	private String jobClassName;
	
	//Job相关类的执行方法
	private String jobMethodName;
	
	/**
	 * alter table sys_task_cron_job add job_params varcher(50);
	 */
	//job参数
	private String jobParams;
 
	// Job描述
	private String jobDescription;
 
	// Job编号
	private String jobNumber;
 
	// Job是否启用
	private Boolean enabled;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobKey() {
		return jobKey;
	}

	public void setJobKey(String jobKey) {
		this.jobKey = jobKey;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

	public String getJobMethodName() {
		return jobMethodName;
	}

	public void setJobMethodName(String jobMethodName) {
		this.jobMethodName = jobMethodName;
	}

	public String getJobParams() {
		return jobParams;
	}

	public void setJobParams(String jobParams) {
		this.jobParams = jobParams;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
    
}
