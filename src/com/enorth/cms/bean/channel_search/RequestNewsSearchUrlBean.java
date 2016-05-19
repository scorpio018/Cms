package com.enorth.cms.bean.channel_search;

import java.io.Serializable;

import com.enorth.cms.annotation.UrlParamAnnotation;
import com.enorth.cms.bean.Page;

public class RequestNewsSearchUrlBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7432009132362573339L;

	/**
	 * 新闻id，如果非空则进行id的精确匹配，忽略其他搜索条件
	 */
	@UrlParamAnnotation(isCheck = true, checkSort = 1)
	private String newsId;
	
	/**
	 * 频道id，newsId不为空时此参数才可为空
	 */
	@UrlParamAnnotation(isCheck = true, checkSort = 2)
	private long channelId;
	
	/**
	 * 新闻状态，也就是哪个平台的新闻。newsId不为空时此参数才可为空
	 */
	@UrlParamAnnotation
	private int state;
	
	/**
	 * 关键词
	 */
	@UrlParamAnnotation
	private String keywords;
	
	/**
	 * 抓取状态，-2全部，1抓取，0非抓取。默认值-2
	 */
	@UrlParamAnnotation
	private int spiderState;
	
	/**
	 * 融合状态，-2全部，1融合，0非融合。默认值-2
	 */
	@UrlParamAnnotation
	private int convState;
	
	/**
	 * 新闻作者，-1表示搜索请求者的新闻
	 */
	@UrlParamAnnotation
	private int initEditor = -1;
	
	@UrlParamAnnotation
	private Page page;

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public int getSpiderState() {
		return spiderState;
	}

	public void setSpiderState(int spiderState) {
		this.spiderState = spiderState;
	}

	public int getConvState() {
		return convState;
	}

	public void setConvState(int convState) {
		this.convState = convState;
	}

	public int getInitEditor() {
		return initEditor;
	}

	public void setInitEditor(int initEditor) {
		this.initEditor = initEditor;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
}
