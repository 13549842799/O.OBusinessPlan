package com.oo.businessplan.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class XmlUtil {
	
	 /**
	  * 通过输入流将xml解析为字符串
	  * @param path
	  * @return
	  */
	 public static String readXmlAsString(String path){
		 
		 StringBuilder sb = new StringBuilder();

		 InputStream in = XmlUtil.class.getClassLoader().getResourceAsStream(path);	
		 InputStreamReader reader = new InputStreamReader(in);
		 BufferedReader br = new BufferedReader(reader);
		 String temp = null;
		 try {
			while ((temp =br.readLine())!=null) {
				sb.append(temp);			
			}
		 } catch (IOException e) {
			e.printStackTrace();
		 }				 
		 return sb.toString();
	 }

}
