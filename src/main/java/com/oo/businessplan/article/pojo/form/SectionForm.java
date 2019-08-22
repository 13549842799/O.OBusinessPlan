package com.oo.businessplan.article.pojo.form;

import com.oo.businessplan.article.pojo.entity.Section;

public class SectionForm extends Section {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3088312806085564839L;

	private String novelName;
	
	private String portionName;

	public String getNovelName() {
		return novelName;
	}

	public void setNovelName(String novelName) {
		this.novelName = novelName;
	}

	public String getPortionName() {
		return portionName;
	}

	public void setPortionName(String portionName) {
		this.portionName = portionName;
	}
	
	

}
