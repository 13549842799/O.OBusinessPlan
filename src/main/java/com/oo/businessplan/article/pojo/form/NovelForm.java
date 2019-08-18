package com.oo.businessplan.article.pojo.form;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oo.businessplan.article.pojo.entity.Label;
import com.oo.businessplan.article.pojo.entity.Novel;

public class NovelForm extends Novel {
	
	private static final long serialVersionUID = 6046630104519973869L;

	private Integer pageNum;
	
	private Integer pageSize;
	
	private String createNikeName;
	
	private List<Label> labelList;
	
	private Long startTime;
	
	private Long endTime;

	@JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
	@Override
	public Timestamp getModifierTime() {
		return super.getModifierTime() == null ? this.getCreateTime() : super.getModifierTime();
	}

	@JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
	@Override
	public Timestamp getCreateTime() {
		return super.getCreateTime();
	}	

	@JsonProperty(defaultValue="1")
	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	@JsonProperty(defaultValue="10")
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getCreateNikeName() {
		return createNikeName;
	}

	public void setCreateNikeName(String createNikeName) {
		this.createNikeName = createNikeName;
	}

	public List<Label> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<Label> labelList) {
		this.labelList = labelList;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	
	
	

}
