package com.oo.businessplan.article.pojo.entity;

import com.oo.businessplan.article.pojo.AbstractArticle;

/**
 * 总结
 * @author tangdabing
 *
 */
public class FinalReport extends AbstractArticle<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 70940974183316306L;

	private String result;
	
	private String testimonials ;// 感言
	
	
	public FinalReport() {
		super();
	}

	public FinalReport(Integer id, Byte delflag) {
		super(id, delflag);
	}

	public FinalReport(Integer id) {
		super(id);
	}
	

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTestimonials() {
		return testimonials;
	}

	public void setTestimonials(String testimonials) {
		this.testimonials = testimonials;
	}
	
	

}
