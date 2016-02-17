package com.enorth.cms.presenter.newslist;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;

public interface INewsListFragPresenter {
	/**
	 * 请求接口获取对应的数据
	 * @param handler
	 * @throws Exception
	 */
	public void requestListViewData(Handler handler, List<BasicNameValuePair> params) throws Exception;
	
	public void requestCurChannelData(Long channelId, int userId, Handler handler) throws Exception;
}
