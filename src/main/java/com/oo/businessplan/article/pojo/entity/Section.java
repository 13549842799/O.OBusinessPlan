package com.oo.businessplan.article.pojo.entity;

import com.oo.businessplan.article.pojo.AbstractArticle;

/**
 * 章节类
 * @author cyz
 *
 */
public class Section extends AbstractArticle<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7158468863712830956L;

	private Integer portionId;//分卷id
	
	private String remark; //备注，也就是ps
	
	private Integer wordsNum;
	
	private String[] pictruePath; //图片路径数组

	public Integer getPortionId() {
		return portionId;
	}

	public void setPortionId(Integer portionId) {
		this.portionId = portionId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getWordsNum() {
		return wordsNum;
	}

	public void setWordsNum(Integer wordsNum) {
		this.wordsNum = wordsNum;
	}

	public String[] getPictruePath() {
		return pictruePath;
	}

	public void setPictruePath(String[] pictruePath) {
		this.pictruePath = pictruePath;
	}
	
	

}
