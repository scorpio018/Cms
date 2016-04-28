package com.enorth.cms.consts;

public class UrlConst {
	
	/**
	 * 登录
	 */
	public static final String LOGIN_URL = "http://10.0.251.108:8080/pub/cms_api_60/Api!commonLogin.do";
	/**
	 * 新闻列表中请求新闻的接口URL
	 */
	public static final String NEWS_LIST_POST_URL = "http://10.0.251.108:8080/pub/cms_api_60/Api!newsList.do";
	/**
	 * 我的频道
	 */
	public static final String MY_NEWS_LIST_POST_URL = "http://10.0.251.108:8080/pub/cms_api_60/Api!myNewsList.do";
	/**
	 * 新闻搜索
	 */
	public static final String SEARCH_NEWS_POST_URL = "http://10.0.251.108:8080/pub/cms_api_60/Api!searchNews.do";
	/**
	 * 搜索频道时请求频道的接口URL
	 */
	public static final String CHANNEL_SEARCH_POST_URL = "http://10.0.251.108:8080/pub/cms_api_60/Api!channelInfos.do";
	/**
	 * 获取当前频道(已经废弃)
	 */
//	public static final String GET_CUR_CHANNEL = "http://10.0.70.71:9000/SpringMVC_MyBatisDemo/android/getDefaultChannelId";
	/**
	 * 我的频道
	 */
	public static final String MY_CHANNEL = "http://10.0.251.108:8080/pub/cms_api_60/Api!myChannelInfos.do";
//	public static final String MY_CHANNEL = "http://10.0.70.71:9000/SpringMVC_MyBatisDemo/android/getMyChannel";
	
}
