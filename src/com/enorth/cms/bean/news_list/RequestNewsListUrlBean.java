package com.enorth.cms.bean.news_list;

import java.io.Serializable;

import com.enorth.cms.annotation.UrlParamAnnotation;
import com.enorth.cms.bean.Page;
import com.enorth.cms.consts.ParamConst;

/**
 * 用于请求新闻列表时传入接口中的参数bean
 * @author yangyang
 *
 */
public class RequestNewsListUrlBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9094632091820961517L;

	@UrlParamAnnotation(isCheck = true, checkSort = 0)
	private Long channelId;
	
	@UrlParamAnnotation
	private Integer state;
	
	@UrlParamAnnotation
	private Page page;
	
	@UrlParamAnnotation
	private int newsType = ParamConst.NEWS_TYPE_ALL;
	/**
	 * 当前用户
	 */
	@UrlParamAnnotation
	private int initEditor;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public int getNewsType() {
		return newsType;
	}

	public void setNewsType(int newsType) {
		this.newsType = newsType;
	}

	public int getInitEditor() {
		return initEditor;
	}

	public void setInitEditor(int initEditor) {
		this.initEditor = initEditor;
	}
	
	
}
