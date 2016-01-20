package com.enorth.cms.consts;

public class ParamConst {
	/**
	 * 判断当前APK是否为第一次访问的key值（存入SharedPreference）
	 */
	public static String ACTIVITY_IS_FIRST_ENTER = "isFirst";
	/**
	 * 下拉状态
	 */
	public static final int STATUS_PULL_TO_REFRESH = 0;

	/**
	 * 释放立即刷新状态
	 */
	public static final int STATUS_RELEASE_TO_REFRESH = 1;

	/**
	 * 正在刷新状态
	 */
	public static final int STATUS_REFRESHING = 2;

	/**
	 * 刷新完成或未刷新状态
	 */
	public static final int STATUS_REFRESH_FINISHED = 3;

	/**
	 * 下拉头部回滚的速度
	 */
	public static final int SCROLL_SPEED = -20;

	/**
	 * 一分钟的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_MINUTE = 60 * 1000;

	/**
	 * 一小时的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_HOUR = 60 * ONE_MINUTE;

	/**
	 * 一天的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_DAY = 24 * ONE_HOUR;

	/**
	 * 一月的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_MONTH = 30 * ONE_DAY;

	/**
	 * 一年的毫秒值，用于判断上次的更新时间
	 */
	public static final long ONE_YEAR = 12 * ONE_MONTH;

	/**
	 * 当前手势是否为下拉刷新的参数的默认值
	 */
	public static final int IS_REFRESH_DEFAULT = 0;

	/**
	 * 当前手势不是下拉刷新操作
	 */
	public static final int IS_REFRESH_NO = 1;

	/**
	 * 当前手势是下拉刷新操作
	 */
	public static final int IS_REFRESH_YES = 2;

	/**
	 * 判断是刷新操作的最小角度值
	 */
	public static final double IS_REFRESH_ACTION_ANGLE = 30.0;
	/**
	 * 初始状态
	 */
	public static final int INIT = 0;
	/**
	 * 释放刷新
	 */
	public static final int RELEASE_TO_REFRESH = 1;
	/**
	 * 正在刷新
	 */
	public static final int REFRESHING = 2;
	/**
	 * 释放加载
	 */
	public static final int RELEASE_TO_LOAD = 3;
	/**
	 * 正在加载
	 */
	public static final int LOADING = 4;
	/**
	 * 操作完毕
	 */
	public static final int DONE = 5;
	/**
	 * 刷新成功
	 */
	public static final int SUCCEED = 0;
	/**
	 * 刷新失败
	 */
	public static final int FAIL = 1;
	
	/**
	 * 当前设置RadioButton的enabled状态的默认值
	 */
	public static final int CAN_ENABLE_STATE_DEFAULT = 0;
	/**
	 * 当前设置RadioButton的enabled状态的单选激活状态
	 */
	public static final int CAN_ENABLE_STATE_SIMPLE = 1;
	/**
	 * 当前设置RadioButton的enabled状态的多选激活状态
	 */
	public static final int CAN_ENABLE_STATE_MORE = 2;
	
	/**
	 * 上次更新时间的字符串常量，用于作为SharedPreferences的键值
	 */
	public static final String UPDATED_AT = "updated_at";
}
