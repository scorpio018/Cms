package com.enorth.cms.listener.newslist.subtitle;

import com.enorth.cms.listener.CommonOnTouchListener;

import android.view.View;

public abstract class ChooseChannelTypeOnTouchListener extends CommonOnTouchListener {
	
	public ChooseChannelTypeOnTouchListener(int touchSlop) {
		super.touchSlop = touchSlop;
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
	public boolean isStopEventTransfer() {
		return true;
	}
}
