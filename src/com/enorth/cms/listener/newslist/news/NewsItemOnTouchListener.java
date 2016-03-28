package com.enorth.cms.listener.newslist.news;

import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.view.news.NewsCommonActivity;

import android.view.View;

public abstract class NewsItemOnTouchListener extends CommonOnTouchListener {
	
	private NewsCommonActivity activity;
	
	public NewsItemOnTouchListener(NewsCommonActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onTouchBegin() {
		
	}

	@Override
	public boolean isClickBackgroungColorChange() {
		return true;
	}

	@Override
	public void touchMove(View v) {
		
	}

	@Override
	public boolean onImgChangeBegin(View v) {
		return true;
	}

	@Override
	public void onImgChangeEnd(View v) {
		
	}

	@Override
	public boolean isStopEventTransfer() {
		return true;
	}


}
