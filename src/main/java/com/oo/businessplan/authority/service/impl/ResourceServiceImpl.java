package com.oo.businessplan.authority.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oo.businessplan.authority.mapper.ResourceMapper;
import com.oo.businessplan.authority.pojo.Resource;
import com.oo.businessplan.authority.service.ResourceService;
import com.oo.businessplan.basic.service.RedisCacheService;
import com.oo.businessplan.basic.service.impl.BaseServiceImpl;
import com.oo.businessplan.common.constant.EntityConstants;
import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.enumeration.StatusFlag;
import com.oo.businessplan.common.exception.ObjectNotExistException;
import com.oo.businessplan.common.exception.UpdateErrorException;
import com.oo.businessplan.common.util.StringUtil;

@Service("resourceService")
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceService, RedisCacheService<Resource> {

	@Autowired
	ResourceMapper resourceMapper;
	
	@Override
	public List<Resource> getResourcesForAdmin(Integer adminId,String accountName) {
		
		List<Resource> list = resourceMapper.getModuleResourcesList(adminId,accountName,1,DeleteFlag.VALID.getCode(),StatusFlag.ENABLE.getCode());
		
		return list;
	}
	
	
	/**
     * 递归获取树形结构的资源tree对象
     * 算法：递归查询，循环编列队列，当遍历的资源是当前的资源的子级资源，
     *       则添加进当前资源的子集列表中。同时开启下一轮递归
     *       当队列不存在对象或者循环的次数大于当前队列的长度时，停止循环。
     *       如果有子集资源需要添加时，重置计数器，避免计数器跳过资源提早结束循环
     */
	@Override
	public List<Resource> getResourceTree(Queue<Resource> queue, Resource current) {
        List<Resource> childs = new ArrayList<>(5);
	    int count = 0;//计数器
	    Resource node = null;
	    while (!queue.isEmpty() && queue.size() >= count) {
	        count ++;
	        node = queue.poll();//从队列中获取并移除头部对象  
		    if ((current == null && node.getPid() == null) 
		    		|| (current != null && Objects.equals(current.getId(), node.getPid()))) {
		        count = 0;
		    	childs.add(node);
		    	node.setChilds(getResourceTree(queue, node));
			} else {
			  queue.add(node);//如果不是子级资源，则重新回归队列,防止遗漏资源
			}
		  }
		return childs;
	}

	@Override
	public List<Resource> getResourceTree(List<Resource> all, Resource current) {
		
		List<Resource> childs = new ArrayList<>();
		Resource node = null;
		for (int i = 0, len = all.size(); i < len; i++) {
			node = all.get(i);
			if ((current == null && node.getPid() == null)
					|| (current != null && Objects.equals(current.getId(), node.getPid()))) {
				childs.add(node);
				node.setChilds(getResourceTree(all, node));			
			}
		}
		
		return childs;
	}



	@Override
	public Resource getObject(String key, int expired, int timeUnit) {
		return super.getObject(resourceMapper, key,EntityConstants.REDIS_RESOURCE_NAME, expired, timeUnit);
	}

	@Override
	public List<Resource> getListObject(String key, int expired, int timeUnit) {
		return super.getListObject(resourceMapper, key, EntityConstants.REDIS_RESOURCE_NAME, expired, timeUnit);
	}
	
}
