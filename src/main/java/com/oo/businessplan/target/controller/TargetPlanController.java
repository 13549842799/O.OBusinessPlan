package com.oo.businessplan.target.controller;

import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.inject.internal.cglib.core.Local;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.StringUtil;
import com.oo.businessplan.common.valid.ValidService;
import com.oo.businessplan.target.service.PlanActionService;
import com.oo.businessplan.target.service.TargetPlanService;
import com.oo.businessplan.target.service.TargetService;
import com.oo.businessplan.target.pojo.entity.PlanAction;
import com.oo.businessplan.target.pojo.entity.Target;
import com.oo.businessplan.target.pojo.entity.TargetPlan;
import com.oo.businessplan.target.pojo.entity.TargetPlanAlterRecord;


/**
 * 目标计划接口
 * @author cyz
 * @version 创建时间：2019-10-24 16:38:41
 */
@RestController
@RequestMapping(value = "/api/target/targetPlan")
public class TargetPlanController extends BaseController{
	
	@Autowired
	TargetService targetService;

    @Autowired
    TargetPlanService targetPlanService;
    
    @Autowired
    private PlanActionService planActionService;
    
    @Autowired
    ValidService validUtil;
    
    /**
     * 计划列表
     * @param request
     * @return
     */
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<List<TargetPlan>> list(HttpServletRequest request,
    		TargetPlan plan) {
        ResponseResult<List<TargetPlan>> response = new ResponseResult<>();
        
        plan = plan == null ? new TargetPlan(DeleteFlag.VALID.getCode()) : plan;
        
        plan.setCreator(currentAdminId(request));
        
        List<TargetPlan> plans = targetPlanService.getList(plan);
        
        return response.success(plans);
    }
    
    /**
     * 倒计时计划列表，获取用户当天将要执行的计划的列表
     * @param request
     * @param plan
     * @return
     */
    @IgnoreSecurity
    @GetMapping(value = "/wlist.re")
    public ResponseResult<List<TargetPlan>> willList(HttpServletRequest request,
    		TargetPlan plan) {
        ResponseResult<List<TargetPlan>> response = new ResponseResult<>();
        
        
        List<TargetPlan> plans = targetPlanService.getWillExecutePlanListInDay(currentAdminId(request));
        
        return response.success(plans);
    }
    
    /**
     * 计划执行当日未完成列表
     * @param request
     * @return
     */
    @IgnoreSecurity
    @GetMapping(value = "/un-complete.re")
    public ResponseResult<List<TargetPlan>> unCmpleteList(HttpServletRequest request) {
        ResponseResult<List<TargetPlan>> response = new ResponseResult<>();
       
        List<TargetPlan> plans = targetPlanService.unCompleteList(currentAdminId(request));
        
        return response.success(plans);
    }
    
    /**
     * 计划修改记录列表
     * @param request
     * @param id
     * @return
     */
    @IgnoreSecurity
    @GetMapping(value = "/s/{id}/records.re")
    public ResponseResult<List<TargetPlanAlterRecord>> recordsList(HttpServletRequest request,
    		@PathVariable("id")int id) {
        ResponseResult<List<TargetPlanAlterRecord>> response = new ResponseResult<>();
        
        
        List<TargetPlanAlterRecord> records = targetPlanService.recordsList(id, currentAdminId(request));
        
        return response.success(records);
    }
    
    /**
     * 通过id获取单个计划实体
     */
    @IgnoreSecurity
    @GetMapping(value = "/s/{id}/read.re")
    public ResponseResult<TargetPlan> readOne(HttpServletRequest request,
    		@PathVariable("id")Integer id) {
        ResponseResult<TargetPlan> response = new ResponseResult<>();
        
        TargetPlan plan = new TargetPlan(DeleteFlag.VALID.getCode());
        plan.setId(id);
        plan.setCreator(currentAdminId(request));
        plan = targetPlanService.getById(plan);
        return response.success(plan);
    }
    
    @IgnoreSecurity
    @PostMapping(value = "/save.do")
    public ResponseResult<TargetPlan> save(HttpServletRequest request,
    		@RequestBody TargetPlan plan) {
        ResponseResult<TargetPlan> response = new ResponseResult<>();
        
        String result = validUtil.validReturnFirstError(plan);
        if (StringUtil.isNotEmpty(result)) {
        	return response.fail(result);
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (plan.getStartDate() == null || plan.getStartDate().compareTo(calendar.getTime()) < 0) {
        	return response.fail("请选择合理的开始时间（在当前时间之后）");
        }
        if (plan.getId() == null) {
        	Date now = new Date();
            calendar.setTime(plan.getStartDate());           
            String startStr = sdf.format(plan.getStartDate()), nowStr = sdf.format(now);
        	boolean needADDAction = startStr.equals(nowStr) && plan.getExecutionTime().after(now); //需要创建一个动作
        	plan.setCreator(currentAdminId(request));
            plan.setCreateTime(new Timestamp(new Date().getTime()));
            plan.setCount(needADDAction ? 1 : 0);
            targetPlanService.add(plan, Integer.class);
            
            if (needADDAction) {
            	PlanAction action = new PlanAction();
            	action.setActionDate(now);
            	action.setTargetPlanId(plan.getId());
            	action.setResult(PlanAction.UNSTART);
            	action.setNum(1);
            	planActionService.add(action, Integer.class);
            	
            	TargetPlan p = new TargetPlan();
            	p.setId(plan.getId());
            	p.setCount(1);
            	targetPlanService.update(p);           	
            	plan.setAction(action);
            }
            return response.success(plan);
        }      
       
        return response.updateResult(targetPlanService.update(plan));
    }
    
    /**
     * 
     * @param request
     * @param plan
     * @return
     */
    @IgnoreSecurity
    @PostMapping(value = "/update.do")
    public ResponseResult<TargetPlan> update(HttpServletRequest request,
    		@RequestBody TargetPlan plan) {
        ResponseResult<TargetPlan> response = new ResponseResult<>();
        
        String result = validUtil.validReturnFirstError(plan);
        if (StringUtil.isNotEmpty(result)) {
        	return response.fail(result);
        }
        plan.setDelflag(DeleteFlag.VALID.getCode());
        plan.setCreator(currentAdminId(request));
        
        TargetPlan p = targetPlanService.getById(plan);
        if (p == null) {
        	return response.fail("不存在对应的目标计划");
        }
                
        return response.updateResult(targetPlanService.update(plan));
    }
    
    @IgnoreSecurity
    @GetMapping(value = "/valid.do")
    public ResponseResult<String> alterValidBeforSave(HttpServletRequest request,
    		TargetPlan plan) {
        ResponseResult<String> response = new ResponseResult<>();
        
        plan.setCreator(currentAdminId(request));
        List<TargetPlan> overs = targetPlanService.isOverLappedTime(plan);
        if (overs != null && overs.size() > 0) {
        	return response.success("执行时间与计划:" + overs.get(0).getPlanName() + " 存在重叠部分");
        }
   
        return response.success();
    }
    
    @IgnoreSecurity
    @GetMapping(value = "/s/{id}/del.do")
    public ResponseResult<TargetPlan> giveUpPlan(HttpServletRequest request,
    		@PathVariable(name="id")Integer id,
    		@RequestParam("reason")String reason) {
        ResponseResult<TargetPlan> response = new ResponseResult<>();
        
        TargetPlan plan = new TargetPlan();
        plan.setId(id);
        plan.setCreator(currentAdminId(request));
        plan.setDeleteReason(reason);
        return response.deleteResult(targetPlanService.delete(plan));
    }
    
    @IgnoreSecurity
    @GetMapping(value = "/test.do")
    public ResponseResult<TargetPlan> text(HttpServletRequest request) {
        ResponseResult<TargetPlan> response = new ResponseResult<>();
        
        System.out.println("开始执行计划");
		List<PlanAction> actions = planActionService.getActionLastDay(null, PlanAction.WAITSTART, PlanAction.UNSTART, PlanAction.ACTIONING);
		if (actions != null && actions.size() > 0) {
			for (PlanAction action : actions) {
				if (action.getStartTime() == null) {
					action.setResult(PlanAction.GIVEUP);
				} else if (action.getEndTime() == null) {
					action.setResult(PlanAction.UNCOMPLETE);
				}
			}
			planActionService.batchUpdateResult(actions);
		}
        
        return response.success();
    }
}