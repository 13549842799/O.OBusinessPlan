package com.oo.businessplan.quartz.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.quartz.pojo.SysTaskCronJob;
import com.oo.businessplan.quartz.service.SysTaskCronJobService;


/**
 * 时钟任务
 * @author cyz
 * @version 创建时间：2019-12-11 17:49:54
 */
@RestController
@RequestMapping(value = "/api/quartz/sysTaskCronJob")
public class SysTaskCronJobController extends BaseController{

    @Autowired
    SysTaskCronJobService sysTaskCronJobService;
    
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<List<SysTaskCronJob>> list(HttpServletRequest request,
    		SysTaskCronJob job) {
        ResponseResult<List<SysTaskCronJob>> response = new ResponseResult<>();

        return response.success(sysTaskCronJobService.getList(job));
    }
    
    @IgnoreSecurity
    @PostMapping(value = "/save.do")
    public ResponseResult<SysTaskCronJob> save(HttpServletRequest request,
    		@RequestBody SysTaskCronJob job) {
        ResponseResult<SysTaskCronJob> response = new ResponseResult<>();
        
        if (job.getId() == null) {
        	 sysTaskCronJobService.add(job, Integer.class);
        	 return response.success(job);
        } else {
        	return response.updateResult(sysTaskCronJobService.update(job));
        }   
    }
    
    @IgnoreSecurity
    @DeleteMapping(value = "/s/{id}/delete.do")
    public ResponseResult<String> save(HttpServletRequest request,
    		@PathVariable("id")Integer id) {
        ResponseResult<String> response = new ResponseResult<>();
        SysTaskCronJob job  = new SysTaskCronJob();
        job.setId(id);
        job.setDelflag(DeleteFlag.DELETE.getCode());
        return response.deleteResult(sysTaskCronJobService.delete(job));
    }
    
}