package com.oo.businessplan.admin.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oo.businessplan.additional.pojo.WebMessage;
import com.oo.businessplan.additional.service.WebMessageService;
import com.oo.businessplan.admin.pojo.entity.Admin;
import com.oo.businessplan.admin.pojo.form.AdminForm;
import com.oo.businessplan.admin.pojo.page.Padmin;
import com.oo.businessplan.admin.service.AdminService;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.constant.ResultConstant;
import com.oo.businessplan.common.constant.SystemKey;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.exception.AuthorityNotEnoughException;
import com.oo.businessplan.common.exception.CheckObjectExistException;
import com.oo.businessplan.common.exception.NullUserException;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.exception.PasswordValidException;
import com.oo.businessplan.common.net.SessionInfo;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.redis.RedisTokenManager;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.IPAdressUtil;
import com.oo.businessplan.common.util.StringUtil;

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
	   private RedisTokenManager tokenManager;
	   
	   @Autowired
	   private WebMessageService wmService;	   
	   
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
	   @RequestMapping(value="/"
	   		+ "loginAsyn.do",method=RequestMethod.POST)
	   public ResponseResult<Object> loginAsyn(HttpServletRequest request,HttpSession session,
				@ApiParam(value = "网站编号", required = true)  @RequestParam(required=true,value="code")String code,
				@ApiParam(value = "用户名", required = true)  @RequestParam(required=true,value="userName")String userName,
				@ApiParam(value = "密码", required = true)  @RequestParam(required=true,value="password")String password,
				@ApiParam(value = "mac", required = false)  @RequestParam(required=false,value="mac")String mac){
			
			ResponseResult<Object>  response = new ResponseResult<>();	
		    String target = null;
			WebMessage webMessage = wmService.selectWeb(code);	
			if (webMessage==null) {
			   return response.fail("此网站不存在");
			}	
			Admin admin = null;
			try {
				admin = adminService.getAdminByAccount(userName, webMessage);
			    if (!adminService.checkPasswordValid(admin.getPassword(), password)) {
					return response.fail("密码错误");
				}
			} catch (NullUserException e) {
				e.printStackTrace();
				try {
					admin = adminService.generalByECode(userName, webMessage);
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
			sessionInfo.setName(userName);
			String ip = IPAdressUtil.getIpAddress(request);
			sessionInfo.setIp(ip);
			sessionInfo.setMac(mac);
			sessionInfo.getResourceList().put(webMessage.getCode(), webMessage.getSignoutAddress());//signoutIp为注销的请求路径
			tokenManager.createToken(sessionInfo) ;
			
			Map<String,Object> result = new HashMap<>();
			result.put("session", sessionInfo);
			result.put("target", target);
			return response.success("ok",result);
		}
	   
	   
	   @ApiOperation(value = "获取账号信息")
	   @RequestMapping(value="/admin_main.do",method=RequestMethod.GET)
	   @IgnoreSecurity(authority = false)
	   public ResponseResult<Admin> searchAdminMain(HttpServletRequest request,
			   @ApiParam(value = "账号用户名", required = true)  @RequestParam(required=true,value="accountname")String accountname){
		    
		    ResponseResult<Admin> response = new ResponseResult<>();
		    
		    Map<String,Object> result = adminService.getAdminByAccountName( accountname);
		    if (result.get(SystemKey.ERROR_KEY)!=null) {
				response.fail(result.get(SystemKey.ERROR_KEY).toString());
			}
		    
		    return response.success((Admin)result.get("admin"));
		   
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
		    if ( adminService.removeAdmin(accountName) ) {
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
		    Map<String,String> result = adminService.alterPassword(oldPass,newPass,verificationCode,phoneNo);
		   	    
		    return response.success(null);
		   
	   }
	   
	   @ApiOperation(value = "账号信息修改-修改昵称")
	   @RequestMapping(value="/alterNikeName.do",method=RequestMethod.PATCH)
	   @IgnoreSecurity(val=false)
	   public ResponseResult<Object> alterNikeName(
			   HttpServletRequest request,
			   @RequestBody Admin admin){
		    
		    ResponseResult<Object> response = new ResponseResult<>();
            if (admin == null || admin.getId() == null || 
            		StringUtil.isEmpty(admin.getNikename())) {
				return response.error(ResultConstant.PARAMETER_ERROR);
			}
            if (adminService.checkNikenameExist(admin.getNikename())) {
				return response.fail("此昵称已存在");
			} 
            Admin param = new Admin();
            param.setId(admin.getId());
            param.setNikename(admin.getNikename());
            if (adminService.update(param) == 1) {
				return response.success();
			} 
		    return response.error("未知错误");
		   
	   }
	   
	   @ApiOperation(value = "账号信息修改-修改头像")
	   @RequestMapping(value="/alterAvatar.do",method=RequestMethod.PATCH)
	   @IgnoreSecurity(val=false)
	   public ResponseResult<Object> alterAvatar(
			   HttpServletRequest request,
			   @RequestBody Admin admin){
		    
		    ResponseResult<Object> response = new ResponseResult<>();
            if (admin == null || admin.getId() == null || 
            		StringUtil.isEmpty(admin.getNikename())) {
				return response.error(ResultConstant.PARAMETER_ERROR);
			}
            if (adminService.checkNikenameExist(admin.getNikename())) {
				return response.fail("此昵称已存在");
			} 
            Admin param = new Admin();
            param.setId(admin.getId());
            param.setNikename(admin.getNikename());
            if (adminService.update(param) == 1) {
				return response.success();
			} 
		    return response.error("未知错误");
		   
	   }

	   @ApiOperation(value = "账号列表")
	   @RequestMapping(value="/admins.r", method=RequestMethod.GET)
	   @IgnoreSecurity(val=false)
	   public ResponseResult<List<Padmin>> adminList(HttpServletRequest request,
			   @ApiParam(value = "用户名", required = false)  @RequestParam(required=false,value="accountName")String accountName,
			   @ApiParam(value = "昵称", required = false)  @RequestParam(required=false,value="nikename")String nikename,
			   @ApiParam(value = "状态  0-禁用 1-正常", required = false)  @RequestParam(required=false,value="state")Byte state,
			   @ApiParam(value = "员工名称", required = false)  @RequestParam(required=false,value="relatedName")String relatedName,
			   @ApiParam(value = "员工编号", required = false)  @RequestParam(required=false,value="relatedCode")String relatedCode,
			   @ApiParam(value = "页数", required = false)  @RequestParam(required=false,value="pageNo")Integer pageNo,
			   @ApiParam(value = "页容量", required = false)  @RequestParam(required=false,value="pageSize")Integer pageSize){
		    
		    ResponseResult<List<Padmin>> response = new ResponseResult<>();
		    
		    AdminForm adminForm = new AdminForm();
		    adminForm.setAccountname(accountName);
		    adminForm.setNikename(nikename);
		    adminForm.setState(state);
		    adminForm.setDelflag(DeleteFlag.VALID.getCode());
		    adminForm.setRelatedName(relatedName);
		    adminForm.setRelatedCode(relatedCode);
		    adminForm.setPageNum(pageNo);
		    
		    List<Padmin> admins = adminService.getAdminList(adminForm);
	    
		    return response.success(admins);
		   
	   }
	   
	   
	
	  

}
