package com.enorth.cms.consts;

public class ParamConst {
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
	
	public static final int MESSAGE_WHAT_SUCCESS = 0;
	public static final int MESSAGE_WHAT_ERROR = 1;
	public static final int MESSAGE_WHAT_NO_DATA = 2;
	/**
	 * 文字宽度（此处是估算出来的，以后需要用算法计算）
	 */
	public static final int FONT_WIDTH = 46;
	/**
	 * 默认的频道ID
	 */
	public static final Long DEFAULT_CHANNEL_ID = 0L;
	/**
	 * 默认的频道名称
	 */
	public static final String DEFAULT_CHANNEL_NAME = "新闻中心";
	/**
	 * 新闻列表中的Fragment的标识
	 */
	public static final String NEWS_FRAG_PAGE = "page_num";
	/**
	 * 全部频道
	 */
	public static final String ALL_CHANNEL = "全部频道";
	/**
	 * 我的频道
	 */
	public static final String MY_CHANNEL = "我的频道";
	
	/*---------------------SharedPreferences中的存值及其含义 start---------------------*/
	/**
	 * 判断当前APK是否为第一次访问的key值（存入SharedPreference）
	 */
	public static String ACTIVITY_IS_FIRST_ENTER = "isFirst";
	/**
	 * 上次更新时间的字符串常量，用于作为SharedPreferences的键值
	 */
	public static final String UPDATED_AT = "updated_at";
	/**
	 * 当前的频道ID
	 */
	public static final String CUR_CHANNEL_ID = "channelId";
	/**
	 * 当前的频道名称
	 */
	public static final String CUR_CHANNEL_NAME = "channelName";
	/**
	 * 显示当前已选择的频道时，需要将第一级的和当前选择的频道进行显示，中间的用“...”代替，其中此字段存的即为第一级
	 */
	public static final String ROOT_CHANNEL_NAME = "rootChannelName";
	/**
	 * 当前选择的频道类型（分为全部频道、我的频道两种）
	 */
	public static final String CUR_CHOOSE_CHANNEL_TYPE = "curChooseChannelType";
	
	/*---------------------SharedPreferences中的存值及其含义 end-----------------------*/
}
