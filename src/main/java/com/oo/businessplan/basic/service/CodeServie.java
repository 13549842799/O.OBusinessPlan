package com.oo.businessplan.basic.service;

import java.util.Objects;

import com.oo.businessplan.common.enumeration.DeleteFlag;
import com.oo.businessplan.common.enumeration.StatusFlag;
import com.oo.businessplan.common.util.StringUtil;

/**
 * 拥有编号的实体的业务方法接口
 * @author cyz
 *
 */
public interface CodeServie {
    
	/**
	 * 通过父编号和当前记录的id生成当前记录的编号,可以指定编号保留的长度
	 * @param parentCode
	 * @param code 当前的末端编号
	 * @param num 保留位数的长度
	 * @return
	 */
    default String generalCode(String parentCode, String code, int num) {
		String suffer = String.format("%0" + num + "d", code);
		return StringUtil.isEmpty(parentCode) ? suffer : parentCode + suffer ;
	};
	
	/**
	 * 通过父编号和当前记录的id生成当前记录的编号
	 * @param parentCode
	 * @param code
	 * @return
	 */
	default String generalCode(String parentCode, String code) {
		return generalCode(parentCode, code, 3);
	}
	
	/**
	 * 判断是否存在已经存在当前的code了
	 * @param code
	 * @param delflag
	 * @param state
	 * @return
	 */
	boolean codeExists(String code, DeleteFlag delflag);
	
	String generalCode();
}
