package com.oo.businessplan.common.constant;

/**
 * 接口返回结果的通用文字结果静态字符串
 * @author cyz
 *
 */
public interface ResultConstant {
	
	
	public static final String PARAMETER_ERROR = "参数错误";
	
	public static final String PARAMETER_REQUIRE_NULL = "必要参数为空";
	
	/**
	 * 不存在此对象
	 */
	public static final String SELECT_OBJECT = "请选择对象";
	
	public static final String NOT_EXIST_PARENT ="当前上级不存在";
	
	public static final String NOT_EXIST_EMPLOYEE ="不存在此职员";
	
	public static final String NOT_EXIST_ADMIN = "不存在此账号";
	
	public static final String NOT_EXIST_RESOURCE = "不存在此资源";
	
	public static final String NOT_EXIST_ROLE = "不存在此角色";
	
	public static final String OBJECT_IS_DELETE = "该目标已被删除";
	
	public static final String NOT_EXIST_AUTH = "不存在此权限";
	
	
	/**
	 * 对象重复存在
	 */
	public static final String EMPLOYEE_EXIST_ADMIN ="此职员已经存在账号";
	
	/**
	 * 编号问题
	 */
	public static final String CODE_EXIST = "编号已经存在";
	
	public static final String CODE_NOT_NULL = "编号不能为空";
	
	/**
	 * 发生预料外的异常
	 */
	public static final String ACCOUNTING_RESPONSE = "发生未知异常，请联系管理员";
	
	public static final String AVATAR_UPLOAD_ERROR="图片上传异常";
	
	
	/**
	 * 格式错误信息
	 */
	public static final String PATTERN_IDCARD_ERROR = "个人身份证格式错误";
	
	public static final String PATTERN_EMAIL_ERROR = "邮箱格式错误";
	
	public static final String PATTERN_SEX_ERROR="性别格式错误";
	
	public static final String PATTERN_PHONE_LENGTH_ERROR="手机号位数不是11位";
	
	public static final String PATTERN_PHONE__ERROR="手机号含有非法字符";
	
	/**
	 * 业务错误信息
	 */
	public static final String RESOURCE_LOCKING = "当前资源已被锁定";
	
	//类型错误
	public static final String TYPE_ERROR_FATHER = "父级类型错误";

}
