package com.oo.businessplan.basic.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理结果集的接口
 * @author cyz
 *
 */
public interface UtilService<T> {
	
    String createKey(T obj);
	
    default Map<String, T> createMapFromList(List<T> list) {
    	Map<String, T> map = new HashMap<>();
    	for (int i = 0, len = list.size(); i < len; i++) {
			map.put(createKey(list.get(i)), list.get(i));
		}	
    	return map;
    }
}
