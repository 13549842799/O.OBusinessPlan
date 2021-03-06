package com.oo.businessplan.article.pojo.entity;

import java.sql.Timestamp;
import java.util.List;

import com.oo.businessplan.basic.entity.IdEntity;
import com.oo.businessplan.upload.pojo.UploadFile;

/**
 * 章节类
 * @author cyz
 *
 */
public class Section extends IdEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7158468863712830956L;
	
	private String title;
	
	private String content;

	private Integer novelId;//小说id
	
	private Integer portionId;//分卷id
	
	private String remark; //备注，也就是ps
	
	private Integer wordsNum;
	
	private Double number; //章节号
	
	private List<UploadFile> files;
	
	private String addImagesId;
	
	private String delImagesId;
	
	private Integer creator;
	
	private Timestamp createTime;
	
	private Integer modifier;
	
	private Timestamp modifierTime;
	
	private Long lastSection;
	
	private Long nextSection;
	
	
	public Section() {}
	
	public Section(Long id) {
		this.setId(id);
	}

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

	public List<UploadFile> getFiles() {
		return files;
	}

	public void setFiles(List<UploadFile> files) {
		this.files = files;
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

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getModifier() {
		return modifier;
	}

	public void setModifier(Integer modifier) {
		this.modifier = modifier;
	}

	public Timestamp getModifierTime() {
		return modifierTime;
	}

	public void setModifierTime(Timestamp modifierTime) {
		this.modifierTime = modifierTime;
	}

	public String getDelImagesId() {
		return delImagesId;
	}

	public void setDelImagesId(String delImagesId) {
		this.delImagesId = delImagesId;
	}

	public String getAddImagesId() {
		return addImagesId;
	}

	public void setAddImagesId(String addImagesId) {
		this.addImagesId = addImagesId;
	}

	public Long getLastSection() {
		return lastSection;
	}

	public void setLastSection(Long lastSection) {
		this.lastSection = lastSection;
	}

	public Long getNextSection() {
		return nextSection;
	}

	public void setNextSection(Long nextSection) {
		this.nextSection = nextSection;
	}
	

	public Integer getNovelId() {
		return novelId;
	}

	public void setNovelnId(Integer novelId) {
		this.novelId = novelId;
	}

	@Override
	public String toString() {
		return "Section [title=" + title + ", content=" + content + ", portionId=" + portionId + ", remark=" + remark
				+ ", wordsNum=" + wordsNum + ", number=" + number + ", files=" + files + ", creator=" + creator
				+ ", createTime=" + createTime + ", modifier=" + modifier + ", modifierTime=" + modifierTime + "]";
	}

	
	
	

}
