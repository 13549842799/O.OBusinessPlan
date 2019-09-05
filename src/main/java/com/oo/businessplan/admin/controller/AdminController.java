package com.oo.businessplan.admin.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;
import java.util.concurrent.TimeUnit;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.oo.businessplan.additional.pojo.Msg;
import com.oo.businessplan.additional.pojo.WebMessage;
import com.oo.businessplan.additional.service.WebMessageService;
import com.oo.businessplan.admin.pojo.entity.Admin;
import com.oo.businessplan.admin.pojo.form.AdminForm;
import com.oo.businessplan.admin.pojo.form.LoginForm;
import com.oo.businessplan.admin.pojo.page.Padmin;
import com.oo.businessplan.admin.service.AdminService;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.basic.service.MsgService;
import com.oo.businessplan.basic.service.support.RedisCacheSupport;
import com.oo.businessplan.common.constant.EntityConstants;
import com.oo.businessplan.common.constant.ResultConstant;

import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.enumeration.StatusFlag;
import com.oo.businessplan.common.exception.AuthorityNotEnoughException;
import com.oo.businessplan.common.exception.CheckObjectExistException;
import com.oo.businessplan.common.exception.NullUserException;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.net.SessionInfo;
import com.oo.businessplan.common.pageModel.MethodResult;
import com.oo.businessplan.common.pageModel.PageParams;
import com.oo.businessplan.common.pageModel.ResponseResult;

import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.security.SessionManager;
import com.oo.businessplan.common.util.HttpUtil;
import com.oo.businessplan.common.util.IPAdressUtil;
import com.oo.businessplan.common.util.StringUtil;
import com.oo.businessplan.common.util.UpLoadUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 登陆接口
 * 获取当前登陆账号信息
 * 获取对应账号信息
 * 账号列表接口
 * 
 * @author admin
 *
 */
@RestController
@RequestMapping("/api/admin")
@Api("职员账号接口模块")
public class AdminController extends BaseController{
	
	   @Autowired
	   private AdminService adminService;
	   
	   @Autowired
	   private SessionManager sessionManager;
	   
	   @Autowired
	   private WebMessageService wmService;	  
	   
	   @Autowired
	   private UpLoadUtil upLoadUtil;
	   
	   @Autowired
	   private MsgService msgService;
	   
	   public static final long expired = 20;
	   
	   public static final TimeUnit timeUnit = TimeUnit.DAYS;
	   
	   /**
	    * 职员登陆的接口
	    * 算法：首先获取登陆的网站的对象，获取目标的账号信息：
	    * 1.如果有账号，则判断是否具有登陆此网站的权限，没有权限则失败
	    * 1.1.成功则判断密码是否正确，不正确则返回失败信息。
	    * 1.2.成功则创建token，然后保存sessioninfo（登陆信息）然后返回ok
	    * 2.如果没有账号，则判断是否拥有编号与登陆用户名相同的职员记录，没有则返回失败
	    * 2.1。有则判断此职员是否拥有账号，有则失败
	    * 2.2.没有则创建一个admin账号记录，然后同上
	    * @param request
	    * @param session
	    * @param code
	    * @param userName
	    * @param password
	    * @param mac
	    * @return
	    */
	   @ApiOperation(value = "职员登陆")
	   @RequestMapping(value="/loginAsyn.do",method=RequestMethod.POST)
	   public ResponseResult<Object> loginAsyn(HttpServletRequest request,HttpSession session,
			    @RequestBody LoginForm form){
			ResponseResult<Object>  response = new ResponseResult<>();	
		    String target = null;
			WebMessage webMessage = wmService.selectWeb(form.getCode());	
			if (webMessage==null) {
			   return response.fail("此网站不存在");
			}	
			Admin admin = null;
			try {
				admin = adminService.getAdminByAccount(form.getUserName(), webMessage);
			    if (!adminService.checkPasswordValid(admin.getPassword(), form.getPassword())) {
					return response.fail("密码错误");
				}
			} catch (NullUserException e) {
				e.printStackTrace();
				try {
					admin = adminService.generalByECode(form.getUserName(), webMessage);
				} catch (NoSuchAlgorithmException | UnsupportedEncodingException e1) {
					e1.printStackTrace();
					return response.error("发生异常，请再次尝试或联系管理员");
				} 
			} catch (AuthorityNotEnoughException e) {
				e.printStackTrace();
				return response.fail(e.getMessage());
			}			
			SessionInfo sessionInfo = new SessionInfo();
			Object lastPageObj = session.getAttribute("lastPage");
			target = lastPageObj==null?webMessage.getHomepage():lastPageObj.toString();
			sessionInfo.setId(admin.getId());
			sessionInfo.setName(form.getUserName());
			String ip = IPAdressUtil.getIpAddress(request);
			sessionInfo.setIp(ip);
			sessionInfo.getResourceList().put(webMessage.getCode(), webMessage.getSignoutAddress());//signoutIp为注销的请求路径
			//tokenManager.createToken(sessionInfo) ;
			sessionManager.saveSeesion(sessionInfo, admin.getAccountname() + EntityConstants.REDIS_SESSION_NAME, RedisCacheSupport.EXPIRED, RedisCacheSupport.TIMEUNIT);
			System.out.println("成功登录");
			Map<String,Object> result = new HashMap<>();
			result.put("session", sessionInfo);
			result.put("target", target);
			return response.success("ok",result);
		}
	   
	   
	   @ApiOperation(value = "职员通过手机登陆")
	   @RequestMapping(value="/loginAsynFromPhone.do",method=RequestMethod.POST)
	   public ResponseResult<Object> loginAsynFromPhone(HttpServletRequest request,HttpSession session,
			    @RequestBody LoginForm form){
			ResponseResult<Object>  response = new ResponseResult<>();	
			if (HttpUtil.getInstance().checkHttpOrigin(request) != 0) {
		    	return response.fail("错误");
		    }
			System.out.println("进入手机登录接口");
			Admin admin = null;
			try {
				admin = adminService.getAdminByName(form.getUserName());
				System.out.println("admin:" + admin.getPassword());
			    if (!adminService.checkPasswordValid(admin.getPassword(), form.getPassword())) {
					return response.fail("密码错误");
				}
			    System.out.println("admin结束");
			} catch (NullUserException e) {
				e.printStackTrace();
				return response.fail("没有这个用户");
			} catch (AuthorityNotEnoughException e) {
				e.printStackTrace();
				return response.fail(e.getMessage());
			}			
			SessionInfo sessionInfo = new SessionInfo();
			sessionInfo.setId(admin.getId());
			sessionInfo.setName(form.getUserName());
			String ip = IPAdressUtil.getIpAddress(request);
			sessionInfo.setIp(ip);
			sessionManager.saveSeesion(sessionInfo, admin.getAccountname() + EntityConstants.REDIS_PHONE_SESSION_NAME, expired, timeUnit);
			Map<String,Object> result = new HashMap<>();
			result.put("session", sessionInfo);
			//清空敏感信息
			admin.setPassword(null);
			result.put("admin", admin);
			System.out.println("登录结束");
			return response.success("ok",result);
		}
	   
	   @ApiOperation(value = "刷新token")
	   @RequestMapping(value="/reflush.do",method=RequestMethod.GET)
	   public ResponseResult<Object> reflush(HttpServletRequest request,
			   @ApiParam(value = "账号用户名", required = true)  @RequestParam(required=true,value="accountName")String accountname,
			   @ApiParam(value = "token", required = true)  @RequestParam(required=true,value="token")String token){
		    System.out.println("刷新token");
		    ResponseResult<Object> response = new ResponseResult<>(); 
		    String key = accountname + EntityConstants.REDIS_PHONE_SESSION_NAME;
		    SessionInfo sessionInfo = sessionManager.getSessionInfo(key);
		    if (sessionInfo == null) {
		    	return response.fail("当前用户没有登录信息");
		    }
		   
		    if (!token.equals(sessionInfo.getToken())) {
		    	return response.fail("用户不正确");
		    }
		    sessionManager.saveSeesion(sessionInfo, key,  expired, timeUnit);
		    
		    return response.success(sessionInfo);
		   
	   }
	   
	   
	   @ApiOperation(value = "获取账号信息")
	   @RequestMapping(value="/admin_main.re",method=RequestMethod.GET)
	   @IgnoreSecurity(authority = false)
	   public ResponseResult<Admin> searchAdminMain(HttpServletRequest request,
			   @ApiParam(value = "账号用户名", required = true)  @RequestParam(required=true,value="accountname")String accountname){
		    
		    ResponseResult<Admin> response = new ResponseResult<>();
		    
		    return response.success(adminService.getAdminByAccountName( accountname));
		   
	   }
	   
	   @ApiOperation(value = "账号信息修改-判断账号昵称否重复")
	   @RequestMapping(value="/check_nikename_exist.do",method=RequestMethod.GET)
	   @IgnoreSecurity(val=false)
	   public ResponseResult<String> checkAccountRepeat(HttpServletRequest request,
			   @ApiParam(value = "账号昵称", required = true)  @RequestParam(required=true,value="nikename")String nikename){
		    
		    ResponseResult<String> response = new ResponseResult<>();
		    
		    boolean exist = adminService.checkNikenameExist(nikename);
		    if (exist) {
				return response.fail("此昵称已存在");
			}		    
		    return response.success(null);
		   
	   }
	   
	   @ApiOperation(value = "注销账号")
	   @RequestMapping(value="/{accountName}/signout.do",method=RequestMethod.GET)
	   @IgnoreSecurity(authority = false)
	   public ResponseResult<String> cancelLogined(HttpServletRequest request,
			   @ApiParam(value = "账号名", required = true)  @PathVariable(required=true,value="accountName")String accountName){
		    
		    ResponseResult<String> response = new ResponseResult<>();
		    //1.获取保存在sessionInfo中的当前保存的注销路径集合
		    //2.遍历集合，发送注销请求
		    //3.注销总服务器的登陆
		    if ( adminService.removeAdmin(accountName, HttpUtil.getInstance().isPhoneLogin(request)) ) {
				return response.success("已注销成功");
			}
		    return response.fail("注销失败，请再次尝试");	   
	   }
	   
	   @ApiOperation(value = "后台注册账号-必须存在职员,默认普通职员权限")
	   @RequestMapping(value="/createAdmin.do",method=RequestMethod.POST)
	   @IgnoreSecurity(val=false)
	   public ResponseResult<String> registAdmin(HttpServletRequest request,
			   @ApiParam(value = "职员编号", required = true)  @RequestParam(required=true,value="employeeCode")String employeeCode){
		    
		    ResponseResult<String> response = new ResponseResult<>();
		    	    
		    try {
		    	SessionInfo info = matchSessionInfo(request);		    	
				Admin admin = adminService.registAdmin(employeeCode,(int)info.getId());
				return response.success("创建成功");
			} catch (CheckObjectExistException e) {
				e.printStackTrace();
				return response.fail(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				return response.fail(ResultConstant.ACCOUNTING_RESPONSE);
			}
		   
	   }
	   
	   @ApiOperation(value = "后台更改账号状态")
	   @RequestMapping(value="/changeState.do",method=RequestMethod.PATCH)
	   @IgnoreSecurity(val=false)
	   public ResponseResult<String> changeState(HttpServletRequest request,
			   @ApiParam(value = "账号id", required = true)  @RequestParam(required=true,value="adminId")int adminId,
			   @ApiParam(value = "状态", required = true)  @RequestParam(required=true,value="state")byte state){
		    
		    ResponseResult<String> response = new ResponseResult<>();

		    	try {
					SessionInfo info = matchSessionInfo(request);		    	
					adminService.changeAdminState(adminId,state,info.getId());
					return response.success("创建成功");
				} catch (ObjectNotExistException e) {
					e.printStackTrace();
					return response.fail(e.getMessage());
				} catch (Exception e) {
					return response.fail(ResultConstant.ACCOUNTING_RESPONSE);
				}
					   
	   }
	   
	   @ApiOperation(value = "账号信息修改-修改密码")
	   @RequestMapping(value="/alter_password.do",method=RequestMethod.PATCH)
	   @IgnoreSecurity(val=false)
	   public ResponseResult<String> alterPassword(HttpServletRequest request,
			   @ApiParam(value = "旧密码", required = false)  @RequestParam(required=false,value="oldPass")String oldPass,
			   @ApiParam(value = "新密码", required = false)  @RequestParam(required=false,value="newPass")String newPass,
			   @ApiParam(value = "验证码", required = false)  @RequestParam(required=false,value="verificationCode")String verificationCode,
			   @ApiParam(value = "手机号", required = false)  @RequestParam(required=false,value="phoneNo")String phoneNo){
		    
		    ResponseResult<String> response = new ResponseResult<>();
		    
		    if (StringUtil.isEmpty(oldPass)) {
				return response.fail("请输入旧密码");
			}
		    if (StringUtil.isEmpty(newPass)) {
				return response.fail("请输入新密码");
			}
		    //验证码检验
		    if(!msgService.validMsg(phoneNo, verificationCode, Msg.PASSWORD)) {
		    	return response.fail("验证码错误");
		    }
		    switch (adminService.alterPassword(oldPass,newPass)) {
				case 1:
					return response.success("密码修改成功");
				case 0:
					return response.fail("密码错误");
				default:
					return response.fail("两次密码不相同");
			}		    		   
	   }
	   
	   @PostMapping("/bindPhone.do")
	   @IgnoreSecurity
	   public ResponseResult<String> bindPhone(HttpServletRequest request,
			   @RequestParam(required=false,value="password")String password,
			   @RequestParam(required=false,value="newPhone")String newPhone,
			   @RequestParam(required=false,value="verificationCode")String verificationCode
			   ) {
		   ResponseResult<String> response = new ResponseResult<>();
		   
		   Admin admin = adminService.getAdminByAccountName(getAccountName(request));
		   if (StringUtil.isEmpty(admin.getBindPhone())) {
			   
		   }
		   admin.setBindPhone(newPhone);
		   adminService.update(admin);
		   
		   return response.success();
	   }
	   
	   @ApiOperation(value = "账号信息修改-修改昵称")
	   @PostMapping("/alterNikeName.do")
	   @IgnoreSecurity(val=false)
	   public ResponseResult<Object> alterNikeName(
			   HttpServletRequest request,
			   @RequestBody Admin admin){
		    
		    ResponseResult<Object> response = new ResponseResult<>();
            if (admin == null  || StringUtil.isEmpty(admin.getNikename())) {
				return response.error(ResultConstant.PARAMETER_ERROR);
			}
            String accountName = getAccountName(request);
            if (adminService.checkNikenameExist(admin.getNikename())) {
				return response.fail("此昵称已存在");
			} 
            Admin redisAdmin = adminService.getAdminByAccountName(accountName);
            redisAdmin.setNikename(admin.getNikename());
            if (adminService.update(redisAdmin) == 1) {              
				return response.success();
			} 
		    return response.error("未知错误");
		   
	   }
	   
	   @ApiOperation(value = "账号信息修改-修改头像")
	   @PostMapping(value="/alterAvatar.do")
	   @IgnoreSecurity(val=false)
	   public ResponseResult<String> alterAvatar(
			   HttpServletRequest request){
		    
		    ResponseResult<String> response = new ResponseResult<>();
		    
		    String accountName = getAccountName(request);
		    
		    Map<String, Map<String, String>> params = new HashMap<>();
		    
		    Map<String, String> sonConfig = new HashMap<>();
		    sonConfig.put("targetPath", File.separator + "avatar");
		    sonConfig.put("check", "true");
		    sonConfig.put("size", "120");
		    sonConfig.put("newName", accountName + String.valueOf(new Date().getTime()));
		    params.put("img", sonConfig);
		    MethodResult<Map<String, String>> result = upLoadUtil.uploadFile(request, params);
		    System.out.println(result);
		    String newPath =  null;
		    if (result.fail()) {
		    	return response.fail(result.getErrorMessage());
		    }
		    if ((newPath = result.getData().get("img")) != null) {
		    	Admin redisAdmin = adminService.getAdminByAccountName(accountName);
		    	//删除久头像图片
		    	upLoadUtil.deleteFile(UpLoadUtil.LOCALPREFIX + redisAdmin.getAvatar());
		    	redisAdmin.setAvatar(newPath);
		    	adminService.update(redisAdmin);
		    	return response.success(newPath);
		    }
            
		    return response.error("未知错误");
		   
	   }

	   @ApiOperation(value = "账号列表")
	   @RequestMapping(value="/sys/admins.re", method=RequestMethod.GET)
	   @IgnoreSecurity()
	   public ResponseResult<PageInfo<Padmin>> adminList(HttpServletRequest request,
			   @ApiParam(value = "用户名", required = false)  @RequestParam(required=false,value="accountName")String accountName,
			   @ApiParam(value = "昵称", required = false)  @RequestParam(required=false,value="nikename")String nikename,
			   @ApiParam(value = "状态  0-禁用 1-正常", required = false)  @RequestParam(required=false,value="state")Byte state,
			   @ApiParam(value = "员工名称", required = false)  @RequestParam(required=false,value="relatedName")String relatedName,
			   @ApiParam(value = "员工编号", required = false)  @RequestParam(required=false,value="relatedCode")String relatedCode,
			   @ApiParam(value = "页数", required = false)  @RequestParam(required=false,value="pageNum")Integer pageNo,
			   @ApiParam(value = "页容量", required = false)  @RequestParam(required=false,value="pageSize")Integer pageSize){
		    
		    ResponseResult<PageInfo<Padmin>> response = new ResponseResult<>();
		    
		    AdminForm adminForm = new AdminForm();
		    adminForm.setAccountname(accountName);
		    adminForm.setNikename(nikename);
		    adminForm.setState(state);
		    adminForm.setDelflag(DeleteFlag.VALID.getCode());
		    adminForm.setRelatedName(relatedName);
		    adminForm.setRelatedCode(relatedCode);
		    adminForm.setRoleState(StatusFlag.ENABLE.getCode());
		    adminForm.setRoleDelflag(DeleteFlag.VALID.getCode());
		    PageParams<AdminForm> params = new PageParams<>();
		    params.setParams(adminForm);
		    params.setPageNum(pageNo);
		    params.setPageSize(pageSize);
		    PageInfo<Padmin> page = adminService.getAdminList(params);

		    return response.success(page);
		   
	   }
	   
	   
	   
	   
	
	  

}
