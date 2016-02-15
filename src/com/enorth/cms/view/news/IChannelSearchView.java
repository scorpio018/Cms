package com.enorth.cms.view.news;

import android.app.DownloadManager.Request;
import android.os.Handler;

public interface IChannelSearchView {
	/**
	 * 获取当前的频道和子频道
	 * @param result
	 * @param handler
	 * @throws Exception
	 */
	public void initChannelData(String result, Handler handler) throws Exception;
	/**
	 * 获取我的频道
	 * @param result
	 * @param handler
	 * @throws Exception
	 */
	public void getMyChannel(String result, Handler handler) throws Exception;
	
	public void error(Exception e);
	
	public void finallyExec();
	
	public void onFailure(Exception e);
}
