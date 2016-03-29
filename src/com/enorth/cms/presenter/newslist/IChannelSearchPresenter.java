package com.enorth.cms.presenter.newslist;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;

public interface IChannelSearchPresenter {
	/**
	 * 初始化频道和子频道
	 * @param channelId
	 * @param handler
	 * @throws Exception
	 */
	public void initChannelData(List<BasicNameValuePair> params, Handler handler);
	/**
	 * 切换到我的频道
	 * @param userId
	 * @param url
	 * @throws Exception
	 */
	public void getMyChannel(List<BasicNameValuePair> params, Handler handler);
	
}
