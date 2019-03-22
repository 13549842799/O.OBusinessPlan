package com.oo.businessplan.common.pageModel;

public class PageParams {
	
	public static final int[] sizeGroup = new int[]{20,40,60,80};
		
	private Integer pageNum;
	private Integer pageSize;
    private Integer count;
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	/**
	 * 初始化页数和叶容量的参数
	 */
	public void init(){
		if (pageNum==null||pageSize==null) {
			pageNum =0;
			pageSize=sizeGroup[0];
		}
		if (pageNum!=null&&pageSize!=null) {
			pageNum = pageNum>=1?pageNum:1;
			for (int i = 0; i < sizeGroup.length; i++) {
				if (sizeGroup[i]==pageSize) {
					pageNum = (pageNum-1)*pageSize;
					return;
				}
			}
			pageSize = sizeGroup[0];
			pageNum = (pageNum-1)*pageSize;
		}
	}

}
