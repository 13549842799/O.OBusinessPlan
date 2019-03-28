package com.oo.businessplan.article.pojo.entity;

import java.io.Serializable;

import com.oo.businessplan.article.pojo.AbstractArticle;

/**
 * 分卷类 
 * @author cyz
 *
 */
public class Portion extends AbstractArticle<Integer> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5567856076152157186L;
	
	private Integer novelId;
	
	private Integer wordsNum;
	

	public Integer getNovelId() {
		return novelId;
	}

	public void setNovelId(Integer novelId) {
		this.novelId = novelId;
	}

	public Integer getWordsNum() {
		return wordsNum;
	}

	public void setWordsNum(Integer wordsNum) {
		this.wordsNum = wordsNum;
	}
	
	

}
