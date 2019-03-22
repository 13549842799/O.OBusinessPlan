package com.oo.businessplan.common.pageModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

public class ResponseResult<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4185740114021603198L;
	public static final int RESPONSE_SUCCESS =200;
	public static final int RESPONSE_FAIL =100;
	public static final int RESPONSE_ERROR=300;
	
	private int status;// 200 成功   100  失败   300异常
	private String message;
	private T data;
	
	
	public ResponseResult() {
		super();
	}
	
	public ResponseResult(int status, String message) {
		this();
		this.status = status;
		this.message = message;
	}
	
	public ResponseResult(int status, String message, T data) {
		this(status, message);
		this.data = data;
	}
	
	/**
	 * 通过response返回json结果
	 * @param response
	 * @throws IOException
	 */
	public void responseMessage(HttpServletResponse response) throws IOException {
	    response.setContentType("application/json");
		PrintWriter out = response.getWriter();
	    out.println(JSONObject.toJSON(this));
	}
	
	public void responseFailMessage(HttpServletResponse response,String message) throws IOException {
		this.status = RESPONSE_FAIL;
		this.message = message;
		responseMessage(response);
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public  ResponseResult<T> success(String mess,T t){
		return new ResponseResult<T>(RESPONSE_SUCCESS,mess,t);
	}
	
	public ResponseResult<T> success(){
		return success(null,null);
	}
	
	public ResponseResult<T> success(T t){
		return success(null,t);
	}
	
	public  ResponseResult<T> fail(String mess,T t){
		return new ResponseResult<T>(RESPONSE_FAIL,mess,t);
	}
	
	public ResponseResult<T> fail(String mess){
		return fail(mess,null);
	}
	
	public  ResponseResult<T> error(String mess,T t){
		return new ResponseResult<T>(RESPONSE_ERROR,mess,t);
	}
	
	public  ResponseResult<T> error(String mess){
		return error(mess,null);
	}
	
	

}
