package com.enorth.cms.consts;

public class UrlConst {
	
	/**
	 * 登录
	 */
	public static final String LOGIN_URL = "cms_api_60/Api!commonLogin.do";
	/**
	 * 新闻列表中请求新闻的接口URL
	 */
	public static final String NEWS_LIST_POST_URL = "cms_api_60/Api!newsList.do";
	/**
	 * 我的频道
	 */
	public static final String MY_NEWS_LIST_POST_URL = "cms_api_60/Api!myNewsList.do";
	/**
	 * 新闻搜索
	 */
	public static final String SEARCH_NEWS_POST_URL = "cms_api_60/Api!searchNews.do";
	/**
	 * 搜索频道时请求频道的接口URL
	 */
	public static final String CHANNEL_SEARCH_POST_URL = "cms_api_60/Api!channelInfos.do";
	/**
	 * 获取当前频道(已经废弃)
	 */
//	public static final String GET_CUR_CHANNEL = "http://10.0.70.71:9000/SpringMVC_MyBatisDemo/android/getDefaultChannelId";
	/**
	 * 我的频道
	 */
	public static final String MY_CHANNEL = "cms_api_60/Api!myChannelInfos.do";
//	public static final String MY_CHANNEL = "http://10.0.70.71:9000/SpringMVC_MyBatisDemo/android/getMyChannel";
	/**
	 * 修改密码
	 */
	public static final String CHANGE_PWD = "cms_api_60/Api!changePassword.do";
	/**
	 * 生成新的newsId
	 */
	public static final String GENERATE_NEWS_ID = "cms_api_60/Api!generateNewsId.do";
	/**
	 * 可选稿源
	 */
	public static final String SELECTABLE_MANUSCRIPTS = "cms_api_60/Api!sourceNames.do";
	/**
	 * 新闻模版
	 */
	public static final String TEMPLATE_FILES = "cms_api_60/Api!templateFiles.do";
	/**
	 * 4G模板
	 */
	public static final String TEMPLATE_FILES_4G = "cms_api_60/Api!templateFiles4g.do";
	/**
	 * 保存新闻&图文直播新闻
	 */
	public static final String SAVE_NEWS = "cms_api_60/Api!saveNews.do";
	
	public static final String TEST_URL = "http://10.0.70.71:9000/SpringMVC_MyBatisDemo/android/getForm";
}
