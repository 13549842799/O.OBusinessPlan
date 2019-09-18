package com.oo.businessplan.common.pageModel;

public class PageParams<T> {
	
	private Integer pageNum;
	
	private Integer pageSize;
	
	private T params;
	
	public PageParams() {}
	
	public PageParams(T params) {
		this.params = params;
	}
	
	public PageParams(T params, int pageNum, int pageSize) {
		this(params);
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}
	
	public PageParams(T params, Integer pageNum, Integer pageSize) {
		this(params);
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public T getParams() {
		return params;
	}

	public void setParams(T params) {
		this.params = params;
	}
	
	

}
