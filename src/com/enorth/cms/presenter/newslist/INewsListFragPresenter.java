package com.enorth.cms.presenter.newslist;

import android.os.Handler;

public interface INewsListFragPresenter {
	/**
	 * 请求接口获取对应的数据
	 * @param handler
	 * @throws Exception
	 */
	public void requestListViewData(Handler handler) throws Exception;
	
	public void requestCurChannelData(Long channelId, int userId, Handler handler) throws Exception;
}
