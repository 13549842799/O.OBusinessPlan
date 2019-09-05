package com.oo.businessplan.basic.service;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oo.businessplan.common.pageModel.PageParams;

public interface PageService<T> {
	
	final int[] sizeGroup = new int[]{20,40,60,80};
	
    List<T> getList(T t);
    
	default PageInfo<T> getPage(PageParams<T> params) {
    	PageHelper.startPage(params.getPageNum(), params.getPageSize());
    	List<T> list = this.getList(params.getParams());
    	PageInfo<T> page = new PageInfo<>(list);
    	return page;
    }

}
