package com.enorth.cms.listener.menu;

import com.enorth.cms.listener.CommonOnTouchListener;

import android.view.View;

public abstract class LeftMenuItemOnTouchListener extends CommonOnTouchListener {
	
	public LeftMenuItemOnTouchListener(int touchSlop) {
		super.touchSlop = touchSlop;
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
