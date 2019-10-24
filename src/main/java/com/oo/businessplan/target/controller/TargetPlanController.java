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
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.target.service.TargetPlanService;
import com.oo.businessplan.target.pojo.entity.TargetPlan;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-10-24 16:38:41
 */
@RestController
@RequestMapping(value = "/api/target/targetPlan")
public class TargetPlanController extends BaseController{

    @Autowired
    TargetPlanService targetPlanService;
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<List<TargetPlan>> list(HttpServletRequest request) {
        ResponseResult<List<TargetPlan>> response = new ResponseResult<>();

        return response.success();
    }
}