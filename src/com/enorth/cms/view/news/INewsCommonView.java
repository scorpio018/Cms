package com.enorth.cms.view.news;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.os.Handler;

public interface INewsCommonView {
	/**
	 * 访问接口去获取对应的新闻列表
	 * @param newsListView
	 * @param needInitData
	 * @param errorHint
	 * @param position
	 */
	public void initNewsListData(PullToRefreshListView newsListView, boolean needInitData, String errorHint);
	
	/**
	 * 将返回的json值存入对应的控件中
	 * @param result
	 * @param handler
	 */
	public void initReturnJsonData(String result, Handler handler);
	
	public NewsCommonActivity getActivity();
}
