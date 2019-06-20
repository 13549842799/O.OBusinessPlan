package com.oo.businessplan.common.util;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
	
	private volatile static HttpUtil httpUtil;
	
	private final static String[] agent = { "Android", "iPhone", "iPod","iPad", "Windows Phone", "MQQBrowser" }; //定义移动端请求的所有可能类型
	
	private HttpUtil () {}
	
	/**
	 * 判断请求是否属于手机端
	 * @param request
	 * @return  0-手机 1-其它
	 */
	public int checkHttpOrigin(HttpServletRequest request) {
		
		String ua = request.getHeader("User-Agent");
		
		if (!(!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;")))) {
			return 1;
		}
		if (!(!ua.contains("Windows NT") && !ua.contains("Macintosh"))) { 
			return 1;
		}
		for (String item : agent) {
			if (ua.contains(item)) {
				return 0;
			}
		}
		return 1;
	}
	
    
	
	/**
	* 判断User-Agent 是不是来自于手机
	* @param ua
	* @return
	*/
	public  boolean checkAgentIsMobile(String ua) {
		boolean flag = false;
		if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
			// 排除 苹果桌面系统
			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
				for (String item : agent) {
					if (ua.contains(item)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
	
	public static HttpUtil getInstance() {
		
		if (httpUtil == null) {
			synchronized (HttpUtil.class) {
				if (httpUtil == null) {
					httpUtil = new HttpUtil();
				}
			}
		}
			
		return httpUtil;
	}
 
}