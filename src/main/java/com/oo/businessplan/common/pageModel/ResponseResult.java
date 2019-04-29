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
	/**
	 * 请求成功
	 */
	public static final int RESPONSE_SUCCESS =200;
	/**
	 * 请求失败(业务逻辑上)
	 */
	public static final int RESPONSE_FAIL =100;
	/**
	 * 发生未知异常(报错)
	 */
	public static final int RESPONSE_ERROR=300;
	/**
	 * 登录失效，重新登录
	 */
	public static final int RESPONSE_RELOGIN=400;
	
	private int status;// 200 成功   100  失败   300异常  400
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
	
	private ResponseResult<T> setValue(int status, String message, T data) {
		this.message = message;
		this.data = data;
		this.status = status;
		return this;
	}
	
	public  ResponseResult<T> success(String mess,T t){
		return setValue(RESPONSE_SUCCESS, mess, t);
	}
	
	public ResponseResult<T> success(){
		return success(null,null);
	}
	
	public ResponseResult<T> success(T t){
		return success(null,t);
	}
	
	public  ResponseResult<T> fail(String mess,T t){
		return setValue(RESPONSE_FAIL, mess, t);
	}
	
	public ResponseResult<T> fail(String mess){
		return fail(mess,null);
	}
	
	public  ResponseResult<T> error(String mess,T t){
		return setValue(RESPONSE_ERROR, mess, t);
	}
	
	public  ResponseResult<T> error(String mess){
		return error(mess,null);
	}
	
	/**
	 * 重新登录提示的返回信息
	 * @return
	 */
	public ResponseResult<T> relogin() {
		return setValue(RESPONSE_RELOGIN, "登录已过期, 请重新登录", null);
	}
	
	/**
	 * 执行更新操作后返回前端的制式信息
	 * @param result
	 * @return
	 */
	public ResponseResult<T> updateResult(int result) {
		switch (result) {
		case 0:
			return fail("更新失败，当前更新数为0");
		case 1:
			return success();
		default:
			return error("更新失败，当前更新涉及多条数据");
		}
	}
	
	/**
	 * 执行删除操作后返回前端的制式信息
	 * @param result
	 * @return
	 */
	public ResponseResult<T> deleteResult(boolean result) {
		return result ? success() : fail("删除失败");
	}
	
	

}
