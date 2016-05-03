package com.enorth.cms.view.news;

import android.app.Activity;
import android.os.Handler;

public interface IChannelSearchView {
	/**
	 * 获取当前的频道和子频道
	 * @param result
	 * @param handler
	 * @throws Exception
	 */
	public void initChannelData(String result, Handler handler);
	/**
	 * 获取我的频道
	 * @param result
	 * @param handler
	 * @throws Exception
	 */
	public void getMyChannel(String result, Handler handler);
	
	public ChannelSearchActivity getActivity();
	
	public void error(Exception e);
	
	public void finallyExec();
	
	public void onFailure(Exception e);
}
