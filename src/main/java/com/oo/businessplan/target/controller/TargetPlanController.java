package com.oo.businessplan.target.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.oo.businessplan.target.service.TargetPlanService;
import com.oo.businessplan.target.service.TargetService;
import com.oo.businessplan.target.pojo.entity.Target;
import com.oo.businessplan.target.pojo.entity.TargetPlan;


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
    
    @IgnoreSecurity
    @GetMapping(value = "/save.do")
    public ResponseResult<TargetPlan> saveOrUpdate(HttpServletRequest request,
    		TargetPlan plan) {
        ResponseResult<TargetPlan> response = new ResponseResult<>();
        
        plan = plan == null ? new TargetPlan(DeleteFlag.VALID.getCode()) : plan;
        
        plan.setCreator(currentAdminId(request));
        
        targetPlanService.add(plan, Integer.class);
        
        return response.success(plan);
    }
    
    @IgnoreSecurity
    @GetMapping(value = "/s/{id}/del.do")
    public ResponseResult<TargetPlan> giveUpPlan(HttpServletRequest request,
    		@PathVariable(name="id")Integer id) {
        ResponseResult<TargetPlan> response = new ResponseResult<>();
        
        TargetPlan plan = new TargetPlan();
        plan.setId(id);
        plan.setCreator(currentAdminId(request));
        
        return response.deleteResult(targetPlanService.delete(plan));
    }
}