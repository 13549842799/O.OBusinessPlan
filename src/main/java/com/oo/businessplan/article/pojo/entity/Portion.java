package com.oo.businessplan.article.pojo.entity;

import java.io.Serializable;
import java.util.List;

import com.oo.businessplan.basic.entity.ModifierEntity;

/**
 * 分卷类 
 * @author cyz
 *
 */
public class Portion extends ModifierEntity<Integer> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5567856076152157186L;
	
	private String title;
	
	private String content;
	
	private Integer novelId;
	
	private Integer wordsNum;
	
	private Double number; //序号 ，卷号
	
	private Integer sectionNum; // 拥有的章节数
	
	private List<Section> sections;
	
	public Portion () {
	}
	
	public Portion (Integer id) {
		this.setId(id);
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

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

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public Integer getSectionNum() {
		return sectionNum;
	}

	public void setSectionNum(Integer sectionNum) {
		this.sectionNum = sectionNum;
	}
	
	

}
