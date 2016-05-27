package com.enorth.cms.view.news;

import com.enorth.cms.bean.news_list.RequestSaveNewsUrlBean;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

public interface INewsEditCommonView {

	public void setNewsId(String resultString, Handler handler);
	
	public void quitNewsEditCommonActivity(String msg);
	
	public RequestSaveNewsUrlBean getRequestSaveNewsUrlBean();
	
	public Activity getActivity();
	
	public Context getContext();
}
