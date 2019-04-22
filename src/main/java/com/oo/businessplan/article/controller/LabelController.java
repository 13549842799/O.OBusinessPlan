package com.oo.businessplan.article.controller;

import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
import java.util.Date;
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
import com.oo.businessplan.common.enumeration.StatusFlag;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.article.service.LabelService;
import com.oo.businessplan.article.pojo.entity.Label;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-04-19 15:55:26
 */
@RestController
@RequestMapping(value = "/api/article/label")
public class LabelController extends BaseController{

    @Autowired
    LabelService labelService;
    
    @IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<List<Label>> list(
    		HttpServletRequest request,
    		@RequestParam(value="type") Byte type,
    		@RequestParam(value="status") Byte status) {
        ResponseResult<List<Label>> response = new ResponseResult<>();
        Label label = new Label(null, DeleteFlag.VALID.getCode(), status, currentAdminId(request), type);
        List<Label> labels = labelService.getList(label);
        System.out.println(labels);
        return response.success(labels);
    }
    
    @IgnoreSecurity
    @PostMapping(value = "/add.do")
    public ResponseResult<Label> add(HttpServletRequest request,
    		@RequestBody Label label
    		) {
        ResponseResult<Label> response = new ResponseResult<>();
        Integer adminId = currentAdminId(request);
        label.setAdminId(adminId);
        label.setCreator(adminId);
        label.setCreateTime(new Timestamp(new Date().getTime()));
        label.setDelflag(DeleteFlag.VALID.getCode());
        label.setState(StatusFlag.ENABLE.getCode());
        label.setType(Label.USER);
        
        labelService.add(label, Integer.class);

        return response.success(label);
    }
}