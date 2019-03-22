package com.oo.businessplan.additional.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.oo.businessplan.additional.pojo.WebMessage;

public interface WebMessageMapper {
	
	/**
	 * 返回网站信息类
	 * @param code 网站编码
	 * @return
	 */
	WebMessage selectWeb(String code);
	/**
	 * 返回网站的集合
	 * @return
	 */
	@Select("select name,homepage,code from webmapper")
	List<WebMessage> findWebs();

}
