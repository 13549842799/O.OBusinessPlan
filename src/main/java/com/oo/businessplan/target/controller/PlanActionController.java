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

import com.github.pagehelper.PageInfo;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.target.service.PlanActionService;
import com.oo.businessplan.target.pojo.entity.PlanAction;
import com.oo.businessplan.target.pojo.entity.TargetPlan;
import com.oo.businessplan.target.pojo.form.PlanActionForm;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-12-04 10:49:25
 */
@RestController
@RequestMapping(value = "/api/target/planAction")
public class PlanActionController extends BaseController{

    @Autowired
    PlanActionService planActionService;
    
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<List<PlanAction>> list(HttpServletRequest request) {
        ResponseResult<List<PlanAction>> response = new ResponseResult<>();

        return response.success();
    }
    
    @IgnoreSecurity
    @GetMapping(value = "/page.re")
    public ResponseResult<PageInfo<PlanActionForm>> page(HttpServletRequest request,
    		PlanAction action,
    		@RequestParam(name="pageNum", defaultValue ="1")int pageNum,
    		@RequestParam(name="pageSize", defaultValue ="10")int pageSize,
    		@RequestParam(name="planId", required = true)int planId) {
        ResponseResult<PageInfo<PlanActionForm>> response = new ResponseResult<>();
        
        action.setTargetPlanId(planId);
        PageInfo<PlanActionForm> page = planActionService.getPage(action, pageNum, pageSize);
        if (page != null && page.getList() != null && page.getList().size() > 1) {
        	List<PlanActionForm> list = page.getList();
    		for (int i = 1; i < list.size(); i++) {
    			list.get(i).setEtype(list.get(i).getDateStr().equals(list.get(i-1).getDateStr()) ? 1 : 0);
    		}
        }
        
        return response.success(page);
    }
    
    /**
     * 更改目标计划动作的状态
     * @param request
     * @return
     */
    @IgnoreSecurity
    @PostMapping(value = "/alter.do")
    public ResponseResult<String> alterState(HttpServletRequest request,
    		@RequestBody(required=true) PlanAction action) {
        ResponseResult<String> response = new ResponseResult<>();
        
        if (action.getId() == null) {
        	return response.fail("请选择计划");
        }
        if (action.getResult() == null) {
        	return response.fail("参数异常");
        }
        PlanAction pl = new PlanAction();
        pl.setId(action.getId());
        pl.setResult(action.getResult());
        switch (pl.getResult()) {
        case PlanAction.OVERCOMPLETE: //超时状态需要记录结束时间和超时理由
			pl.setEndTime(action.getEndTime());
		case PlanAction.GIVEUP: //放弃需要记录理由
			pl.setReason(action.getReason());
			break;
		case PlanAction.ACTIONING:
			pl.setStartTime(action.getStartTime());
			break;
		case PlanAction.COMPLETE:
			pl.setEndTime(action.getEndTime());
			break;
		default:
			break;
		}
        return response.updateResult(planActionService.update(pl));
    }
}