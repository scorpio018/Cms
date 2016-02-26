package com.enorth.cms.listener.newslist.title;

import com.enorth.cms.listener.CommonOnTouchListener;

import android.view.View;

public abstract class NewsSearchOnTouchListener extends CommonOnTouchListener {
	
	public NewsSearchOnTouchListener(int touchSlop) {
		super.touchSlop = touchSlop;
	}

	@Override
	public void onTouchBegin() {
		
	}

	@Override
	public boolean isClickBackgroungColorChange() {
		return false;
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
