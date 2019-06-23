package com.oo.businessplan.system.controller;

import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.StringUtil;
import com.oo.businessplan.common.util.UpLoadUtil;
import com.oo.businessplan.system.service.AppVersionService;
import com.oo.businessplan.system.pojo.AppVersion;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-06-22 20:57:31
 */
@RestController
@RequestMapping(value = "/api/system/appVersion")
public class AppVersionController extends BaseController{

    @Autowired
    AppVersionService appVersionService;
    
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<List<AppVersion>> list(HttpServletRequest request) {
        ResponseResult<List<AppVersion>> response = new ResponseResult<>();
        
        return response.success();
    }
    
    /**
     * 获取最新的一个app版本的版本号
     * @param request
     * @return
     */
    @GetMapping(value = "/version.re")
    public ResponseResult<AppVersion> checkVersion(HttpServletRequest request) {
        ResponseResult<AppVersion> response = new ResponseResult<>();
        
        return response.success(appVersionService.getAppVersionInfo());
    }

}