package com.oo.businessplan.article.pojo.form;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oo.businessplan.article.pojo.entity.Diary;
import com.oo.businessplan.article.pojo.entity.Label;
import com.oo.businessplan.article.service.DiaryService;
/**
 * 日记类的参数类，用来接收页面传递的参数
 * @author cyz
 *
 */
public class DiaryForm extends Diary{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6804908199535792314L;
	
	private List<Label> labelList;
	
	private String classifyName;
	
	private Integer pageNum;
	
	private Integer pageSize;
	
	

	public List<Label> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<Label> labelList) {
		this.labelList = labelList;
	}

	@JsonProperty(defaultValue="1")
	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	@JsonProperty(defaultValue=(DiaryService.pageSize+""))
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	
	
	

}
