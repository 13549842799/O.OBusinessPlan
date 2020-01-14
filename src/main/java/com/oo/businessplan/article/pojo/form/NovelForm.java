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
	
	private Integer label;
	
	private List<Label> labelList;
	
	private Long startTime;
	
	private Long endTime;
	
	private String classifyName;
	
	private SectionForm lastetSection;

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

	
	public String getWordsNumStr() {
		if(this.getWordsNum() == null) {
			return "0字";
		}
		if (this.getWordsNum() < 10000) {
			return this.getWordsNum() + "字";
		}
		return String.format("%.2f万字", this.getWordsNum().doubleValue()/10000);
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

	public Integer getLabel() {
		return label;
	}

	public void setLabel(Integer label) {
		this.label = label;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public SectionForm getLastetSection() {
		return lastetSection;
	}

	public void setLastetSection(SectionForm lastetSection) {
		this.lastetSection = lastetSection;
	}
	
	
	public String getStateName() {
		switch (this.getNovelState()) {
		case 1:
			return "未开始";
		case 2:
			return "连载中";
		case 3:
			return "没有更新";
		case 4:
			return "完结";
		}
		return "";
	}

}
