package com.oo.businessplan.quartz.service.impl;

import javax.annotation.PostConstruct;

import static org.quartz.CronExpression.isValidExpression;

import java.util.List;
import java.util.UUID;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.exception.AddErrorException;
import com.oo.businessplan.common.util.StringUtil;
import com.oo.businessplan.quartz.mapper.SysTaskCronJobMapper;
import com.oo.businessplan.quartz.pojo.SysTaskCronJob;
import com.oo.businessplan.quartz.service.SysTaskCronJobService;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-12-11 17:49:54
 */
@Service("sysTaskCronJobService")
public class SysTaskCronJobServiceImpl extends BaseServiceImpl<SysTaskCronJob> implements SysTaskCronJobService, ApplicationContextAware {
	
	/**
	* 基于cron调度的Job的默认组名
	*/
	public static final String CRON_JOB_GROUP_NAME = "cron_task_group";

    @Autowired
    SysTaskCronJobMapper sysTaskCronJobMapper;
    
    private ApplicationContext context;
    
    private volatile Scheduler scheduler ;
    
    @PostConstruct
    public void init() {
    	try {
			scheduler = new StdSchedulerFactory().getScheduler();			
		} catch (SchedulerException e) {
			System.out.println("初始化失败");
			e.printStackTrace();			
		}
    	if (scheduler == null) {
    		System.out.println("初始化失败");
    		return;
    	}
    	
    	// 初始化基于cron时间配置的任务列表
    	this.initCronJobs(scheduler);
    	
    	try {
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }
    
    
    /**
	 * 初始化任务（基于cron触发器）
	 * 遍历数据库中存在的时钟任务，然后根据数据库配置决定是否启动它们。
	 * @param scheduler
	 */
	private void initCronJobs(Scheduler scheduler) {
		
		SysTaskCronJob job = new SysTaskCronJob();
		job.setDelflag(DeleteFlag.VALID.getCode());
		Iterable<SysTaskCronJob> its = this.getList(job);	
		
		if (its == null) {
			return;
		}	
		for (SysTaskCronJob sysTaskCronJob : its) {
			this.scheduleCronJob(sysTaskCronJob, scheduler);
		}
	}
	
	/**
	 * 校验时钟属性是否合法，如果时钟在数据库中的状态为运行，则启动时钟
	 * @param job
	 * @param scheduler
	 */
	private void scheduleCronJob(SysTaskCronJob job, Scheduler scheduler) {
		if (job == null || StringUtil.isEmpty(job.getJobName()) ||  StringUtil.isEmpty(job.getJobClassName()) 
				||  StringUtil.isEmpty(job.getCron()) || scheduler == null) {
			System.out.println("必要参数存在空值");
			return;
		}
		if (!job.getEnabled()) {
			System.out.println(job.getJobName() + "状态为停止");
			return;
		}
		JobKey jobKey = this.genCronJobKey(job);
		try {
			if (!scheduler.checkExists(jobKey)) {
				//This job doesn't exist, then add it to scheduler.
				//log.info("Add new cron job to scheduler, jobName = " + job.getJobName());
				this.newJobAndNewCronTrigger(job, scheduler, jobKey);
			} else {
				// totally execute the job once.
				this.updateRelateCronTriggerOfJob(job, scheduler, jobKey);
			}
		} catch (SchedulerException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 新建job和trigger到scheduler(基于cron触发器)
	 * @param job
	 * @param scheduler
	 * @param jobKey 时钟在调度器中的标志，由SysTaskCronJob的id加上固定字段组成
	 * @throws ClassNotFoundException 
	 * @throws SchedulerException
	 */
	
	@SuppressWarnings({"unchecked" })
	private void newJobAndNewCronTrigger(SysTaskCronJob job, Scheduler scheduler, JobKey jobKey) 
			throws ClassNotFoundException, SchedulerException {
		TriggerKey triggerKey = this.genCronTriggerKey(job);
		
		String cronExpr = job.getCron();
		if (!isValidExpression(cronExpr)) {
			System.out.println("表达式不正确");
			return;
		}
		Class<?> jobClass = Class.forName(job.getJobClassName().trim());
		JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>)jobClass).withIdentity(jobKey)
				.withDescription(job.getJobDescription()).usingJobData(acceptParams(job.getJobParams())).build();
		//新建触发器
		CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpr).withMisfireHandlingInstructionDoNothing()).build();
	    System.out.println("创建<" + job.getJobName() + ">定时任务");
		scheduler.scheduleJob(jobDetail, cronTrigger);
	 
	}
	
	/**
	 * 更新job的trigger(基于cron触发器)
	 * @param job
	 * @param scheduler
	 * @param jobKey
	 * @throws SchedulerException
	 */
	private void updateRelateCronTriggerOfJob(SysTaskCronJob job, Scheduler scheduler, JobKey jobKey) throws SchedulerException {
		TriggerKey triggerKey = this.genCronTriggerKey(job);
		String cronExpr = job.getCron().trim();
 
		List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
 
		for (int i = 0; triggers != null && i < triggers.size(); i++) {
			Trigger trigger = triggers.get(i);
			TriggerKey curTriggerKey = trigger.getKey();
 
			if (this.isTriggerKeyEqual(triggerKey, curTriggerKey)) {
				if (trigger instanceof CronTrigger
						&& cronExpr.equalsIgnoreCase(((CronTrigger) trigger).getCronExpression())) {
					// Don't need to do anything.
				} else {
					updateCronTriggerOfJob(job, scheduler, jobKey, curTriggerKey);
				}
			} else {
				// different trigger key ,The trigger key is illegal, unschedule
				// this trigger
				scheduler.unscheduleJob(curTriggerKey);
			}
 
		}
	}
	
	/**
	 * 更新调度器中的时钟任务信息。
	 * @param job
	 * @param scheduler
	 * @param jobKey 
	 * @param triggerKey
	 * @throws SchedulerException
	 */
	private void updateCronTriggerOfJob(SysTaskCronJob job, Scheduler scheduler, JobKey jobKey, TriggerKey triggerKey) throws SchedulerException {
		if (!isValidExpression(job.getCron())) {
			return;
		}	
		// Cron expression is valid, build a new trigger and
		// replace the old one.
		CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
				.withSchedule(CronScheduleBuilder.cronSchedule(job.getCron().trim())
						.withMisfireHandlingInstructionDoNothing())
				.build();
		scheduler.rescheduleJob(triggerKey, newTrigger);	
	}
	
	/**
     * 重置时钟信息
     * @param newJob
     * @param oldJob
     * @throws SchedulerException 
     * @throws ClassNotFoundException 
     */
	public void reSetScheduler(SysTaskCronJob newJob, SysTaskCronJob oldJob) throws SchedulerException, ClassNotFoundException {
        //如果在状态没有修改的情况下，如果当前状态为停止或者表达式没有修改，则不更新或者创建时钟任务
        if (newJob.getEnabled().equals(oldJob.getEnabled()) 
        		&& (!newJob.getEnabled() || newJob.getCron().trim().equals(oldJob.getCron().trim())) && newJob.getDelflag() == DeleteFlag.DELETE.getCode() ) {
        	return;
        }
        
        TriggerKey triggerKey = this.genCronTriggerKey(oldJob);
        JobKey jobKey = this.genCronJobKey(oldJob);
        
        if (newJob.getDelflag() == DeleteFlag.DELETE.getCode()) {
        	scheduler.deleteJob(jobKey);
        	return;
        }
        
        //如果状态修改为0，则停止任务
        if (!newJob.getEnabled()) {
        	//scheduler.unscheduleJob(triggerKey);
            scheduler.pauseJob(jobKey);
            //scheduler.deleteJob(jobKey);
            return;
        }       
        
        Trigger trigger = scheduler.getTrigger(triggerKey);//获取对应触发器trigger

        // trigger如果为null则说明scheduler中并没有创建该任务
        if (trigger == null) {
        	this.newJobAndNewCronTrigger(newJob, scheduler, jobKey);
        } else { // 不为null则说明scheduler中有创建该任务,更新即可
        	if (!newJob.getJobParams().equals(oldJob.getJobParams())) {
        		scheduler.getJobDetail(jobKey).getJobDataMap().clear();
        		scheduler.getJobDetail(jobKey).getJobDataMap().putAll(acceptParams(newJob.getJobParams()));
        	}
        	updateCronTriggerOfJob(newJob, scheduler, jobKey, triggerKey);
        }
    }
	
	
	// 在对任务进行保存时需同步更新调度器中的定时任务配置
	/**
	 * 根据修改的信息更新系统调度器中的时钟任务信息，然后更新数据库信息
	 * @param taskCronJob 时钟执行逻辑类
	 */
	@Override
	public void add(SysTaskCronJob taskCronJob) throws AddErrorException {
		
		taskCronJob.setJobKey(UUID.randomUUID().toString());
		
		SysTaskCronJob old = new SysTaskCronJob();
		old.setJobKey(taskCronJob.getJobKey());
		
		try {
			this.reSetScheduler(taskCronJob, old);
		} catch (ClassNotFoundException | SchedulerException e) {
			e.printStackTrace();
			return;
		}
		super.add(taskCronJob);
	}

	

	@Override
	public int update(SysTaskCronJob taskCronJob) {
		
		taskCronJob.setDelflag(DeleteFlag.VALID.getCode());
		SysTaskCronJob old = getById(taskCronJob);
		try {
			this.reSetScheduler(taskCronJob, old);
		} catch (ClassNotFoundException | SchedulerException e) {
			e.printStackTrace();
			return -1;
		}
		return super.update(taskCronJob);
	}
	

	@Override
	public boolean delete(SysTaskCronJob taskCronJob) {
		SysTaskCronJob old = getById(taskCronJob);
		try {
			this.reSetScheduler(taskCronJob, old);
		} catch (ClassNotFoundException | SchedulerException e) {
			e.printStackTrace();
			return false;
		}
		return super.delete(taskCronJob);
	}


	/**
     * 产生JobKey
     * 
     * @param job
     * @return
     */
    public JobKey genCronJobKey(SysTaskCronJob job)
    {
        return new JobKey(job.getJobKey(), CRON_JOB_GROUP_NAME);
    }
    
    /**
     * 产生TriggerKey
     * 
     * @param job
     * @return
     */
    public TriggerKey genCronTriggerKey(SysTaskCronJob job)
    {
        return new TriggerKey("trigger_" + job.getJobKey().trim(), CRON_JOB_GROUP_NAME);
    }
    
    /**
	 * 获取时钟任务的参数
	 * @param params 参数字符串  type/paramsName/value
	 * @return
	 */
    private JobDataMap acceptParams(String params) {
    	JobDataMap paramsMap = new JobDataMap();
    	if (StringUtil.isEmpty(params)) {
    		return paramsMap;
    	}
    	String[] arr = params.split(",");
    	
    	for(int i = 0; i < arr.length; i++) {
    		String[] p = arr[i].split("/");
    		if (p.length != 2 && p.length != 3) {
    			continue;
    		}
    		switch (p[0]) {
				case "bean":  //获取Spring管理的bean作为参数
					Object bean = getBean(p[1]);
					if (bean == null) {
					    continue;
					}
					paramsMap.put(p[1], bean);
					break;
				default:				
					break;
			}
    	}
    	return paramsMap;
    }
    
    /**
     * 判断是否两个trigger key是否相等
     * 
     * @param tk1
     * @param tk2
     * @return
     */
    public boolean isTriggerKeyEqual(TriggerKey tk1, TriggerKey tk2)
    {
        return tk1.getName().equals(tk2.getName()) && ((tk1.getGroup() == null && tk2.getGroup() == null)
                || (tk1.getGroup() != null && tk1.getGroup().equals(tk2.getGroup())));
    }
    
    public Object getBean(String beanName) {
    	return context.getBean(beanName);
    }


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}