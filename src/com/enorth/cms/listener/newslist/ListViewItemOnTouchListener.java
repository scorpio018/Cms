package com.enorth.cms.listener.newslist;

import com.enorth.cms.listener.CommonOnTouchListener;

import android.view.MotionEvent;
import android.view.View;

public abstract class ListViewItemOnTouchListener extends CommonOnTouchListener {

	public ListViewItemOnTouchListener(int touchSlop) {
		super.touchSlop = touchSlop;
	}
	
	@Override
	public boolean isClickBackgroungColorChange() {
		return true;
	}

	@Override
	public boolean onImgChangeBegin(View v) {
		return true;
	}

	@Override
	public void onImgChangeEnd(View v) {

	}
	
	@Override
	public void touchMove(View v) {
		
	}
	
	@Override
	public boolean isStopEventTransfer() {
		return true;
	}
}
