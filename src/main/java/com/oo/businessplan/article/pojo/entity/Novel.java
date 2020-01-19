package com.oo.businessplan.article.pojo.entity;

import java.io.Serializable;
import java.util.List;

import com.oo.businessplan.article.pojo.AbstractArticle;

/**
 * title 书名  content 简介
 * @author cyz
 *
 */
public class Novel extends AbstractArticle<Integer> implements Serializable{
	
	/**
	 * 小说的连载状态：未开始
	 */
	public static final byte UNSTART = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2200995135226600806L;

	private String cover; //封面
	
	private String labels; //标签
	
	private Integer wordsNum;// 整本书字数
	
	private List<Portion> portions; //分卷集合
	
	private Byte novelState;// 状态  0-未开始 1-连载中 2-没有更新 3-完结
	
	private Integer portionsNum;
	
	private Integer sectionsNum;
	
	private Long lastetSectionId; //最新的章节

	public Novel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Novel(Integer id, Byte delflag) {
		super(id, delflag);
		// TODO Auto-generated constructor stub
	}
	
	public Novel(Integer id, Integer creator) {
		super(id);
		this.setCreator(creator);
	}

	public Novel(Integer id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public Integer getWordsNum() {
		return wordsNum;
	}

	public void setWordsNum(Integer wordsNum) {
		this.wordsNum = wordsNum;
	}

	public List<Portion> getPortions() {
		return portions;
	}

	public void setPortions(List<Portion> portions) {
		this.portions = portions;
	}

	public Byte getNovelState() {
		return novelState;
	}

	public void setNovelState(Byte novelState) {
		this.novelState = novelState;
	}

	public Integer getPortionsNum() {
		return portionsNum;
	}

	public void setPortionsNum(Integer portionsNum) {
		this.portionsNum = portionsNum;
	}

	public Integer getSectionsNum() {
		return sectionsNum;
	}

	public void setSectionsNum(Integer sectionsNum) {
		this.sectionsNum = sectionsNum;
	}

	public Long getLastetSectionId() {
		return lastetSectionId;
	}

	public void setLastetSectionId(Long lastetSectionId) {
		this.lastetSectionId = lastetSectionId;
	}

	

	
	
	

}
