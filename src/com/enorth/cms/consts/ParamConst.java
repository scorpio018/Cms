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
	 * 当前手势是否为左右滑动参数的默认值
	 */
	public static final int IS_HORIZONTAL_SCROLL_DEFAULT = 0;
	/**
	 * 当前手势是左右滑动参数的默认值
	 */
	public static final int IS_HORIZONTAL_SCROLL_NO = 1;
	/**
	 * 当前手势不是左右滑动参数的默认值
	 */
	public static final int IS_HORIZONTAL_SCROLL_YES = 2;

	/**
	 * 判断是刷新操作的最小角度值
	 */
	public static final double IS_REFRESH_ACTION_ANGLE = 30.0;
	/**
	 * 判断是横向滑动操作的最小角度值
	 */
	public static final double IS_HORIZONTAL_ACTION_ANGLE = 30.0;
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
	 * 编辑新闻
	 */
	public static final int STATE_EDIT = 0;
	/**
	 * 待(送)签发
	 */
	public static final int STATE_TO_PUB = 1000;
	/**
	 * 已签发新闻
	 */
	public static final int STATE_PUB = 2000;
	/**
	 * 已删除的新闻（逻辑删除）
	 */
	public static final int STATE_DELETE = -1;
	/**
	 * 自动签发
	 */
	public static final int STATE_AUTO_PUB = 1500;
	/**
	 * 内部留言
	 */
	public static final int STATE_REPLY = 400;
	/**
	 * 内部资料
	 */
	public static final int STATE_RESOURCE = 500;
	
	/**
	 * 全部新闻
	 */
	public static final int TYPE_ALL = 0;
	/**
	 * 普通新闻
	 */
	public static final int TYPE_NORMAL = 10000;
	/**
	 * 专题新闻
	 */
	public static final int TYPE_TOPIC = 20000;
	/**
	 * 专题更多页
	 */
	public static final int TYPE_TOPIC_MORE = 30000;
	/**
	 * 多签新闻
	 */
	public static final int TYPE_LINK = 40000;
	/**
	 * 跳转新闻
	 */
	public static final int TYPE_REDIRECT = 50000;
	/**
	 * 高清图新闻
	 */
	public static final int TYPE_HD_PIC = 60000;
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
	 * 素材上转中，两个fragment的状态之已经展开
	 */
	public static final int CUR_FRAG_LAYOUT_STATE_IS_OPENED = 0;
	/**
	 * 素材上转中，两个fragment的状态之正在收回
	 */
	public static final int CUR_FRAG_LAYOUT_STATE_CLOSING = 1;
	/**
	 * 素材上转中，两个fragment的状态之已经收回
	 */
	public static final int CUR_FRAG_LAYOUT_STATE_IS_CLOSED = 2;
	/**
	 * 素材上转中，两个fragment的状态之正在展开
	 */
	public static final int CUR_FRAG_LAYOUT_STATE_OPENING = 3;
	/**
	 * 读取文件类别为读取图片
	 */
	public static final int FILE_LOAD_TYPE_IMAGE = 1;
	/**
	 * 读取文件类别为读取视频缩略图
	 */
	public static final int FILE_LOAD_TYPE_VIDEO = 2;
	/**
	 * 加载本地资源
	 */
	public static final int FILE_LOAD_LOCATION_LOCAL = 1;
	/**
	 * 加载网络url资源
	 */
	public static final int FILE_LOAD_LOCATION_URL = 2;
	
	/**
	 * 文字宽度（此处是估算出来的，以后需要用算法计算）
	 */
	public static final int FONT_WIDTH = 46;
	/**
	 * 弹出浮层的默认宽度（）
	 */
	public static final int POP_WINDOW_COMMON_WIDTH = 200;
	/**
	 * 图片最多只能选9张
	 */
	public static final int MAX_SELECT_IMAGE_COUNT = 9;
	/**
	 * 视频最多只能选一条
	 */
	public static final int MAX_SELECT_VIDEO_COUNT = 1;
	
	/**
	 * 不是融合新闻
	 */
	public static final int IS_CONV_NO = 0;
	/**
	 * 是融合新闻
	 */
	public static final int IS_CONV_YES = 1;
	/**
	 * 新闻处于断开状态
	 */
	public static final int LINK_NO = 0;
	/**
	 * 新闻处于连接状态
	 */
	public static final int LINK_YES = 1;
	/**
	 * 没有导读图
	 */
	public static final int HAS_GUIDE_IMAGE_NO = 0;
	/**
	 * 有导读图
	 */
	public static final int HAS_GUIDE_IMAGE_YES = 1;
	
	/**
	 * 弹出浮层的默认高度
	 */
//	public static final int POP_WINDOW_COMMON_HEIGHT = 200;
	/**
	 * 测试使用：用户ID
	 */
	@Deprecated
	public static final int USER_ID = 1;
	// 由于频道搜索页中有频道列表的ListView和搜索频道的ListView，所以需要考虑到用户是选择哪个上面的频道后进行跳转到下一级或选择频道返回新闻列表中
	/**
	 * 当前用户点击的频道item是在ChannelSearchActivity中
	 */
	public static final int CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY = 1;
	/**
	 * 当前用户点击的频道item时在AutoCompleteTextView中
	 */
	public static final int CUR_CHANNEL_LIST_ENABLE_VIEW_AUTO_COMPLETE_TEXT_VIEW = 2;
	// ---------------------两个activity之间需要传值时定义的code值start-----------------------------//
	/**
	 * 从新闻列表跳转到搜索新闻页面的REQUEST_CODE值
	 */
	public static final int NEWS_LIST_FRAG_ACTIVITY_TO_NEWS_SEARCH_ACTIVITY_REQUEST_CODE = 1;
	/**
	 * 从新闻列表跳转到频道搜索页面的REQUEST_CODE值
	 */
	public static final int NEWS_LIST_FRAG_ACTIVITY_TO_CHANNEL_SEARCH_ACTIVITY_REQUEST_CODE = 2;
	/**
	 * 从图文直播列表跳转到搜索新闻页面的REQUEST_CODE值
	 */
	public static final int NEWS_LIVE_LIST_FRAG_ACTIVITY_TO_NEWS_SEARCH_ACTIVITY_REQUEST_CODE = 3;
	/**
	 * 从图文直播列表跳转到频道搜索页面的REQUEST_CODE值
	 */
	public static final int NEWS_LIVE_LIST_FRAG_ACTIVITY_TO_CHANNEL_SEARCH_ACTIVITY_REQUEST_CODE = 4;
	/**
	 * 从相册页面跳转到图片预览页面的REQUEST_CODE值
	 */
	public static final int GALLERY_ACTIVITY_TO_UPLOAD_PIC_PREVIEW_ACTIVITY_REQUEST_CODE = 5;
	/**
	 * 从任意activity进入单纯的显示图片的MaterialUploadPicPreviewActivity页面的REQUEST_CODE值
	 */
	public static final int ACTIVITY_TO_MATERIAL_UPLOAD_PIC_PREVIEW_ACTIVITY_REQUEST_CODE = 6;
	/**
	 * 跳转到扫一扫页面的REQUEST_CODE值
	 */
	public static final int SCANNING_REQUEST_CODE = 1;
	
	/**
	 * 从搜索新闻页面返回到新闻列表的RESULT_CODE值
	 */
	public static final int NEWS_SEARCH_ACTIVITY_BACK_TO_NEWS_LIST_FRAG_ACTIVITY_RESULT_CODE = 1;
	/**
	 * 从频道搜索页面返回到新闻列表的RESULT_CODE值
	 */
	public static final int CHANNEL_SEARCH_ACTIVITY_BACK_TO_NEWS_LIST_FRAG_ACTIVITY_RESULT_CODE = 2;
	/**
	 * 从预览页面返回到相册页面的RESULT_CODE值
	 */
	public static final int UPLOAD_PIC_PREVIEW_ACTIVITY_BACK_TO_GALLERY_ACTIVITY_RESULT_CODE = 3;
	
	/**
	 * 调用系统相机拍照、系统相册选择照片并显示
	 */
    public static final int CAMERA_TAKE = 1;  
    public static final int CAMERA_SELECT = 2;
    
    /**
	 * 进入拍照模块的值
	 */
	public static final int TAKE_CAMERA_PICTURE = 1000;
	
	// ---------------------两个activity之间需要传值时定义的code值end-----------------------------//
	
	
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
	 * 新闻排序中的“优先级”key值
	 */
	public static final String NEWS_SORT_BY_LIST_ORDER_KEY = "list_order";
	/**
	 * 新闻排序中的“优先级”value值
	 */
	public static final String NEWS_SORT_BY_LIST_ORDER_VALUE = "优先级";
	/**
	 * 新闻排序中的“发布时间”key值
	 */
	public static final String NEWS_SORT_BY_PUB_DATE_KEY = "pub_date";
	/**
	 * 新闻排序中的“发布时间”value值
	 */
	public static final String NEWS_SORT_BY_PUB_DATE_VALUE = "发布时间";
	/**
	 * 新闻排序中的“修改时间”key值
	 */
	public static final String NEWS_SORT_BY_MOD_DATE_KEY = "mod_date";
	/**
	 * 新闻排序中的“修改时间”value值
	 */
	public static final String NEWS_SORT_BY_MOD_DATE_VALUE = "修改时间";
	/**
	 * 频道搜索中的全部频道
	 */
	public static final String ALL_CHANNEL = "全部频道";
	/**
	 * 频道搜索中的我的频道
	 */
	public static final String MY_CHANNEL = "我的频道";
	/**
	 * 附件上传中的图片
	 */
	public static final String MATERIAL_UPLOAD_FILE_TYPE_TEXT_PIC = "图片";
	/**
	 * 附件上传中的视频
	 */
	public static final String MATERIAL_UPLOAD_FILE_TYPE_TEXT_VIDEO = "视频";
	/**
	 * 附件上传中的手机上传
	 */
	public static final String MATERIAL_UPLOAD_TYPE_FROM_PHONE = "手机上传";
	/**
	 * 附件上传中所有历史
	 */
	public static final String MATERIAL_UPLOAD_TYPE_FROM_ALL = "所有历史";
	/**
	 * 通过最近日期进行数据查询
	 */
	public static final String MATERIAL_UPLOAD_TYPE_TEXT_1 = "最近一天";
	
	public static final String MATERIAL_UPLOAD_TYPE_TEXT_2 = "最近两天";
	
	public static final String MATERIAL_UPLOAD_TYPE_TEXT_3 = "最近三天";
	
	/**
	 * PopupWindow显示时调用showAtLocation方法
	 */
	public static final int POPUP_WINDOW_SHOW_TYPE_AT_LOCATION = 1;
	/**
	 * PopupWindow显示时调用showAsDropDown方法
	 */
	public static final int POPUP_WINDOW_SHOW_TYPE_AS_DROPDOWN = 2;
	
	/*---------------------接口中返回的key一览 start-----------------------------------*/
	/**
	 * 登录成功时获取到的用户对象
	 */
	public static String LOGIN_USER = "user";
	/**
	 * 登录成功时返回的频道对象
	 */
	public static String CHANNEL = "channel";
	/**
	 * 频道ID
	 */
	public static String CHANNEL_ID = "channelId";
	/**
	 * 频道名称
	 */
	public static String CHANNEL_NAME = "channelName";
	/**
	 * 频道等级
	 */
	public static String CHANNEL_LEVEL = "channelLevel";
	/**
	 * 按照频道名的层级存成一个数组
	 */
	public static String CHANNEL_NAME_CONTENT = "channelNameContent";
	
	/*---------------------接口中返回的key一览 end-------------------------------------*/
	
	/*---------------------SharedPreferences中的存值及其含义 start---------------------*/
	/**
	 * 用户ID
	 */
	public static String LOGIN_USER_ID = "userId";
	/**
	 * 真实姓名
	 */
	public static String LOGIN_TRUE_NAME = "trueName";
	/**
	 * 用户名
	 */
	public static String LOGIN_USER_NAME = "userName";
	/**
	 * 密码
	 */
	public static String LOGIN_PASSWORD = "password";
	/**
	 * 用户登录的token
	 */
	public static String LOGIN_TOKEN = "token";
	/**
	 * 用户登录之后从接口返回的种子（随机码）
	 */
	public static String USER_LOGIN_SEED = "seed";
	/**
	 * 标识是否已经登录
	 */
	public static String IS_LOGIN = "isLogin";
	/**
	 * 当前保存的登录过的用户
	 */
	public static String REMEMBERED_USER = "rememberedUser";
	/**
	 * 判断当前APK是否为第一次访问的key值（存入SharedPreference）
	 */
	public static String ACTIVITY_IS_ENTERED = "isEntered";
	/**
	 * 上次更新时间的字符串常量，用于作为SharedPreferences的键值
	 */
	public static final String UPDATED_AT = "updated_at";
	/**
	 * 当前用户最后进入的activity页
	 */
	public static final String CUR_ACTIVITY_RESOURCE_ID = "curActivityResourceId";
	/**
	 * 当前的频道ID
	 */
	public static final String CUR_CHANNEL_ID = "channelId";
	/**
	 * 当前的频道名称
	 */
	public static final String CUR_CHANNEL_NAME = "channelName";
	/**
	 * 当前的频道ID对应的父ID
	 */
	public static final String CUR_CHANNEL_ID_PARENT_ID = "parentChannelId";
	/**
	 * 当前频道对应的目录结构
	 */
	public static final String CUR_CHANNEL_CONTENT = "channelContent";
	/**
	 * 显示当前已选择的频道时，需要将第一级的和当前选择的频道进行显示，中间的用“...”代替，其中此字段存的即为第一级
	 */
	public static final String ROOT_CHANNEL_NAME = "rootChannelName";
	/**
	 * 当前选择的频道类型（分为全部频道、我的频道两种）
	 */
	public static final String CUR_CHOOSE_CHANNEL_TYPE = "curChooseChannelType";
	/**
	 * 当前选择的附件上传类型
	 */
	public static final String CUR_MATERIAL_UPLOAD_TYPE = "curMaterialUploadType";
	
	/*---------------------SharedPreferences中的存值及其含义 end-----------------------*/
	
	/**
	 * 在n张图片传入另一个activity时，将这n张图片地址存入的List以该key名存入bundle中
	 */
	public static final String IMG_DATAS = "imgDatas";
	/**
	 * 在n张图片传入另一个activity时，可能会有一些图片已经选中了，所以讲选中的图片地址的List以该key名存入bundle中
	 */
	public static final String CHECKED_IMG_DATAS = "checkedImgDatas";
	/**
	 * 在点击相册中的某一张图片时，将点击的图片的position以该key名存入bundle中传入另一个activity
	 */
	public static final String CHECK_POSITION = "checkPosition";
	/**
	 * 点击添加图片时，是不是返回到上一个activity
	 */
	public static final String ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY = "addPicIsJUmpToPrevActivity";
	/**
	 * 点击添加图片时，不是返回到上一个activity
	 */
	public static final Integer ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY_NO = 1;
	/**
	 * 点击添加图片时，是返回到上一个activity
	 */
	public static final Integer ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY_YES = 2;
	
	/*---------------------广播注册时的action及其对应的功能 start--------------------------------*/
	/**
	 * 关闭activity的广播标识
	 */
	public static final String CLOSE_ACTIVITY = "closeActivity";
	/*---------------------广播注册时的action及其对应的功能 end--------------------------------*/

}
