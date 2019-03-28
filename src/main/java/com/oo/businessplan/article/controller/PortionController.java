package com.oo.businessplan.article.controller;

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
import com.oo.businessplan.article.service.PortionService;
import com.oo.businessplan.article.pojo.entity.Portion;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-03-28 15:24:19
 */
@RestController
@RequestMapping(value = "/api/article/portion")
public class PortionController extends BaseController{

    @Autowired
    PortionService portionService;
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<List<Portion>> list(HttpServletRequest request) {
        ResponseResult<List<Portion>> response = new ResponseResult<>();

        return response.success();
    }
}