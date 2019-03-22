package com.oo.businessplan.authority.service;

import java.util.List;
import java.util.Queue;

import com.oo.businessplan.authority.pojo.Resource;
import com.oo.businessplan.basic.service.BaseService;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.exception.UpdateErrorException;

public interface ResourceService extends BaseService<Resource> {
	
	public List<Resource> getResourcesForAdmin(Integer adminId,String accountName);
	
	/**
	 * 获取树形结构的资源列表,如果不存在最顶层节点，则传入null
	 * @param queue
	 * @return
	 */
	public List<Resource> getResourceTree(Queue<Resource> queue,Resource current);
	
	/**
	 * 获取树形结构的资源列表,如果不存在最顶层节点，则传入null
	 * @param all
	 * @return
	 */
	public List<Resource> getResourceTree(List<Resource> all, Resource current);
    
	
}
