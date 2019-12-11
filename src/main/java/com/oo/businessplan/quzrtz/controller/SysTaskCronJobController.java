package com.oo.businessplan.quzrtz.controller;

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
import com.oo.businessplan.quzrtz.service.SysTaskCronJobService;
import com.oo.businessplan.quzrtz.pojo.SysTaskCronJob;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-12-11 17:49:54
 */
@RestController
@RequestMapping(value = "/api/quzrtz/sysTaskCronJob")
public class SysTaskCronJobController extends BaseController{

    @Autowired
    SysTaskCronJobService sysTaskCronJobService;
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<List<SysTaskCronJob>> list(HttpServletRequest request) {
        ResponseResult<List<SysTaskCronJob>> response = new ResponseResult<>();

        return response.success();
    }
}