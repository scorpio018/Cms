package com.enorth.cms.presenter.newslist;

import android.os.Handler;

public interface IChannelSearchPresenter {
	/**
	 * 初始化频道和子频道
	 * @param channelId
	 * @param handler
	 * @throws Exception
	 */
	public void initChannelData(Long channelId, Handler handler) throws Exception;
	/**
	 * 切换到我的频道
	 * @param userId
	 * @param url
	 * @throws Exception
	 */
	public void getMyChannel(Integer userId, Handler handler) throws Exception;
	
}
