package com.oo.businessplan.authority.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oo.businessplan.authority.pojo.Resource;
import com.oo.businessplan.authority.service.ResourceService;
import com.oo.businessplan.basic.controller.BaseController;
import com.oo.businessplan.common.constant.ResultConstant;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.exception.AddErrorException;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.exception.UpdateErrorException;
import com.oo.businessplan.common.net.SessionInfo;
import com.oo.businessplan.common.pageModel.ResponseResult;
import com.oo.businessplan.common.security.IgnoreSecurity;
import com.oo.businessplan.common.util.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RequestMapping("/api/authority/resource")
@Api("资源相关接口")
@RestController
public class ResourceController extends BaseController{

	  @Autowired
	  private ResourceService resourceService;
	  
	  @ApiOperation(value = "获取树形格式的资源列表")
	  @RequestMapping(value="/tree.re",method=RequestMethod.GET)
	  @IgnoreSecurity()
	  public ResponseResult<Object>  treeResources(
		HttpServletRequest request,
	    @ApiParam(value = "父id", required = false)  
	    @RequestParam(required=false,value="pid")Integer pid,
	    @ApiParam(value = "名称", required = false)  
        @RequestParam(required=false,value="name")String name,
        @ApiParam(value = "类型", required = false)  
        @RequestParam(required=false,value="type")Byte type,
        @ApiParam(value = "状态", required = false)  
        @RequestParam(required=false,value="state")Byte state){
		  
		Resource params 
		  = new Resource(null, pid, name, type, state, DeleteFlag.VALID.getCode());
		  
		ResponseResult<Object> response = new ResponseResult<>();
		Queue<Resource> queue = new LinkedList<>(resourceService.getList(params));
		List<Resource> list = resourceService.getResourceTree(queue, null);
		list.forEach(o->o.createPath());
		return response.success(list);	  
	  }
	
	  @ApiOperation(value = "根据用户获取对应模块资源列表")
	  @RequestMapping(value="/resources.re",method=RequestMethod.GET)
	  @IgnoreSecurity(authority=false)
	  public ResponseResult<Object>  selectResourceForAdmin(HttpServletRequest request){
		  
		  ResponseResult<Object> response = new ResponseResult<>();
		  
		  try {
			SessionInfo info = matchSessionInfo(request);
			List<Resource> resources = resourceService.getResourcesForAdmin(null,info.getName());
			return response.success(resourceService.getResourceTree(resources, null));
		  }  catch (Exception e) {
			e.printStackTrace();
			return response.error("发生未知异常",e.getMessage());
		  }
		  
	  }

	  @ApiOperation(value = "添加资源")
	  @RequestMapping(value="/add.do",method=RequestMethod.POST)
	  @IgnoreSecurity()
	  public ResponseResult<Object>  addResource(
	    @ApiParam(value = "父id", required = false)  
	    @RequestParam(required=false,value="pid")Integer pid,
	    @ApiParam(value = "名称", required = true)  
        @RequestParam(required=true,value="name")String name,
        @ApiParam(value = "请求路径", required = false)  
        @RequestParam(required=false,value="request_url")String request_url,
        @ApiParam(value = "排序(未位置)", required = false)  
        @RequestParam(required=false,value="order", defaultValue="99")Integer order,
        @ApiParam(value = "样式", required = false)  
        @RequestParam(required=false,value="style")String style,
        @ApiParam(value = "描述", required = false)  
        @RequestParam(required=false,value="describes")String describes,
        @ApiParam(value = "类型", required = false,defaultValue="1")  
        @RequestParam(required=false,value="type",defaultValue="1")Byte type,
        @ApiParam(value = "key", required = false)  
        @RequestParam(required=false,value="key")String key,
        @ApiParam(value = "状态", required = false,defaultValue="1")  
        @RequestParam(required=false,value="state",defaultValue="1")Byte state){
		  
		ResponseResult<Object> response = new ResponseResult<>();
		
		if (StringUtil.isEmpty(request_url)) {
			return response.fail("请求路径不能为空");
		}
		Resource parent = null;
		if (pid != null) {
			parent = new Resource(pid,DeleteFlag.VALID.getCode());
			parent = resourceService.getById(parent);
			if (parent == null) {
				return response.fail(ResultConstant.NOT_EXIST_PARENT);
			}
			
			if (parent.getLocking() == 1) {
				return response.fail(ResultConstant.RESOURCE_LOCKING);
			}
		}
		
		Resource resource = new Resource
		   (null, pid, name, request_url, null, style, type, describes
				   , key, state, DeleteFlag.VALID.getCode());
		try {
			resourceService.add(resource);
			return response.success(resourceService.getById(resource));
		} catch (AddErrorException e) {
			e.printStackTrace();
			return response.fail(e.getMessage());
		}
	  }
	  
	  /**
	   * 移动时不能移动到自级子集中
	   * @param id
	   * @param pid
	   * @param name
	   * @param request_url
	   * @param order
	   * @param style
	   * @param describes
	   * @param type
	   * @param key
	   * @param state
	   * @return
	   */
	  @ApiOperation(value = "更新资源")
	  @RequestMapping(value="/update.do",method=RequestMethod.POST)
	  @IgnoreSecurity()
	  public ResponseResult<Object>  updateResource(
	    @ApiParam(value = "id", required = true)  
		@RequestParam(required=true,value="id")Integer id,
	    @ApiParam(value = "父id", required = false)  
	    @RequestParam(required=false,value="pid")Integer pid,
	    @ApiParam(value = "名称", required = true)  
        @RequestParam(required=true,value="name")String name,
        @ApiParam(value = "请求路径", required = false)  
        @RequestParam(required=false,value="request_url")String request_url,
        @ApiParam(value = "排序(未位置)", required = false)  
        @RequestParam(required=false,value="order")Integer order,
        @ApiParam(value = "样式", required = false)  
        @RequestParam(required=false,value="style")String style,
        @ApiParam(value = "描述", required = false)  
        @RequestParam(required=false,value="describes")String describes,
        @ApiParam(value = "类型", required = false,defaultValue="1")  
        @RequestParam(required=false,value="type",defaultValue="1")Byte type,
        @ApiParam(value = "key", required = false)  
        @RequestParam(required=false,value="key")String key,
        @ApiParam(value = "状态", required = false,defaultValue="1")  
        @RequestParam(required=false,value="state",defaultValue="1")Byte state){
		  
		ResponseResult<Object> response = new ResponseResult<>();
		
		if (StringUtil.isEmpty(request_url)) {
			return response.fail("请求路径不能为空");
		}
		
		Resource old = new Resource(id,DeleteFlag.VALID.getCode());
		old = resourceService.getById(old);
		if (old == null) {
			return response.fail(ResultConstant.NOT_EXIST_RESOURCE);
		}
		
		if (old.getLocking() == 1) {
			return response.fail(ResultConstant.RESOURCE_LOCKING);
		}
		
		
		Resource parent = new Resource(pid, DeleteFlag.VALID.getCode());
		parent = resourceService.getById(parent);
		if (parent == null) {
			return response.fail(ResultConstant.NOT_EXIST_PARENT);
		}
		if (parent.getType() == 2) {
			return response.fail(ResultConstant.TYPE_ERROR_FATHER);
		}
		Resource resource = new Resource
		   (id, pid, name, request_url, parent.getPath() + "," + id, style, type
				   , describes, key, state, DeleteFlag.VALID.getCode());
		//在资源表中创建触发器，当插入时，会在角色表和权限表中插入超级管理员相关的记录
		//创建更新触发器，当resource表的key修改时，权限表的key也修改，当delflag修改时角色表和权限表相应修改
		switch (resourceService.updateFull(resource)) {
		case 0:			
			return response.fail("更新失败");
        case 1:	
        	//如果改变了父级，则维护子级的路径,并且存在子级，则返回树形结构的子级
        	if (resource.getPid() != old.getPid() ) {				
        		Resource params = new Resource();
        		params.setDelflag(DeleteFlag.VALID.getCode());
        		List<Resource> childs = resourceService.getList(params);
        		Queue<Resource> queue = new LinkedList<>(childs);
        		resource.setChilds(resourceService.getResourceTree(queue, resource));
			}
			return response.success(resource);
		default:
			return response.error("更新异常");
		}
	  }
	  
	  @ApiOperation(value = "启用/禁用 资源")
	  @RequestMapping(value="/state.do",method=RequestMethod.POST)
	  @IgnoreSecurity(authority=false)
	  public ResponseResult<Object>  stateUpdate(HttpServletRequest request,
			  @ApiParam(value = "id", required = true)  
              @RequestParam(required=true,value="id")int id){
		  
		  ResponseResult<Object> response = new ResponseResult<>();
		  
		  try {
			if (resourceService.state(id)) {
				return response.success();
			}
			return response.fail("更新失败");
		  } catch (Exception e) {
			e.printStackTrace();
			return response.error("发生未知异常",e.getMessage());
		  }	  
	  }
	  
	  @ApiOperation(value = "删除资源")
	  @RequestMapping(value="/delete.do",method=RequestMethod.POST)
	  @IgnoreSecurity(authority=false)
	  public ResponseResult<Object>  delete(HttpServletRequest request,
			  @ApiParam(value = "id", required = true)  
              @RequestParam(required=true,value="id")int id){
		  
		  ResponseResult<Object> response = new ResponseResult<>();
		  
		  try {
             return resourceService.delete(id) ? 
            		 response.success() : response.fail("");
		  } catch (Exception e) {
			e.printStackTrace();
			return response.error("发生未知异常",e.getMessage());
		  }
		  
	  }
	
}
