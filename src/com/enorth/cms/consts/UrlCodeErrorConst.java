package com.enorth.cms.consts;
/**
 * 接口返回的code值和对应的含义
 * @author yangyang
 *
 */
public class UrlCodeErrorConst {
	
	/**
	 * 成功
	 */
	public static final int SUCCESS = 1;
	/**
	 * token验证失败
	 */
	public static final int CHECK_TOKEN_FAILED = -1;
	/**
	 * check_sum校验失败
	 */
	public static final int CHECK_CHECK_SUM_FAILED = -2;
	/**
	 * 登录验证失败
	 */
	public static final int CHECK_LOGIN_FAILED = -3;
	/**
	 * 删除新闻操作失败，当前状态下新闻不允许删除
	 */
	public static final int CUR_STATE_CANNOT_DEL_NEWS = -4;
	/**
	 * 不允许对多签新闻进行当前操作
	 */
	public static final int CANNOT_OPER_MULTI_TAGS_NEWS = -5;
	/**
	 * 请求的对象不存在
	 */
	public static final int REQUEST_OBJECT_IS_NOT_EXISTS = -6;
	/**
	 * 当前用户对所有一级频道都没有查看权限(无法登录)
	 */
	public static final int CUR_USER_NO_FIRST_LEVEL_CHANNEL_PERMISSION = -7;
	/**
	 * 创建登录令牌出错
	 */
	public static final int CREATE_LOGIN_TOKEN_FAILED = -8;
	/**
	 * 登录验证失败提示
	 */
	public static final String CHECK_LOGIN_FAILED_HINT = "登录验证失败";
	/**
	 * 未知错误
	 */
	public static final String UNKNOWN_ERROR_HINT = "未知错误";
	
}
