package com.enorth.cms.bean.channel_search;

import java.io.Serializable;

import com.enorth.cms.annotation.UrlParamAnnotation;
/**
 * 进行频道查询时传入的参数bean
 * @author yangyang
 *
 */
public class RequestChannelSearchUrlBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@UrlParamAnnotation(isCheck = true)
	private long channelId;

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}
	
	
}
