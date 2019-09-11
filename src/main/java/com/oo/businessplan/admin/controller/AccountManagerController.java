package com.oo.businessplan.admin.controller;

import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.basic.service.PageService;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.pageModel.PageParams;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.StringUtil;
import com.oo.businessplan.admin.service.AccountManagerService;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.admin.pojo.entity.AccountManager;
import com.oo.businessplan.admin.pojo.entity.Key;


/**
 * 
 * @author cyz
 * @version 创建时间：2019-08-07 17:28:11
 */
@RestController
@RequestMapping(value = "/api/admin/accountManager")
public class AccountManagerController extends BaseController{

    @Autowired
    AccountManagerService accountManagerService;
    
    @Resource(name="accountManagerService")
    PageService<AccountManager> pageService;
    
    @IgnoreSecurity
    @PostMapping(value = "/checkKey.re")
    public ResponseResult<String> checkKey(HttpServletRequest request) {
        ResponseResult<String> response = new ResponseResult<>();
        
        boolean result = accountManagerService.haskey(currentAdminId(request));
        return response.success(result ? "1" : "0");
    }
    
    @IgnoreSecurity
    @PostMapping(value = "/saveKey.do")
    public ResponseResult<AccountManager> saveKey(HttpServletRequest request,
    		Key key) {
        ResponseResult<AccountManager> response = new ResponseResult<>();
        key.setAccountId(currentAdminId(request));
        accountManagerService.saveKey(key);
        
        return response.success();
    }
    
	@IgnoreSecurity
    @GetMapping(value = "/list.re")
    public ResponseResult<PageInfo<AccountManager>> list(HttpServletRequest request,
    		AccountManager manager,
    		PageParams<AccountManager> params) {
        ResponseResult<PageInfo<AccountManager>> response = new ResponseResult<>();
        manager.setDelflag(DeleteFlag.VALID.getCode());
        manager.setCreator(currentAdminId(request));
        params.setParams(manager);
        return response.success(pageService.getPage(params));
    }
    
    @IgnoreSecurity
    @GetMapping(value = "/s/{id}/read.re")
    public ResponseResult<AccountManager> getOne(HttpServletRequest request,
    		@PathVariable(name="id")Integer id) {
        ResponseResult<AccountManager> response = new ResponseResult<>();
        AccountManager am = new AccountManager();
        am.setId(id);
        am.setCreator(currentAdminId(request));
        am.setDelflag(DeleteFlag.VALID.getCode());
        return response.success(accountManagerService.getById(am));
    }
    
    @IgnoreSecurity
    @PostMapping(value = "/add.do")
    public ResponseResult<AccountManager> addAccount(HttpServletRequest request,
    		@RequestBody AccountManager account) {
        ResponseResult<AccountManager> response = new ResponseResult<>();
        Integer user = currentAdminId(request);
        account.setPassword(accountManagerService.encryptPassword(account.getPassword(), user));
        account.setCreator(user);
        if (account.getId() == null) {
        	if (StringUtil.isEmpty(account.getPassword())) {
            	return response.fail("必须填写密码");
            }
        	account.setCreateTime(new Timestamp(new Date().getTime()));
        	accountManagerService.add(account, Integer.class);
        } else {
        	accountManagerService.update(account);
        }
 
        return response.success(account);
    }
    
    @IgnoreSecurity
    @PostMapping(value = "/update.do")
    public ResponseResult<AccountManager> updateAccount(HttpServletRequest request,
    		AccountManager account) {
        ResponseResult<AccountManager> response = new ResponseResult<>();
        int user = currentAdminId(request);
        if (account != null && StringUtil.isNotEmpty(account.getPassword())) {
        	account.setPassword(accountManagerService.encryptPassword(account.getPassword(), user));
        }
        accountManagerService.update(account);
        
        return response.success(account);
    }
  
    @IgnoreSecurity
    @DeleteMapping(value = "/s/{id}/delete.do")
    public ResponseResult<String> deleteAccount(HttpServletRequest request,
    		@PathVariable(name="id")Integer id) {
        ResponseResult<String> response = new ResponseResult<>();
        int user = currentAdminId(request);
        AccountManager am = new AccountManager();
        am.setId(id);
        am.setCreator(user);
        accountManagerService.delete(am);
        
        return response.success();
    }
    
}