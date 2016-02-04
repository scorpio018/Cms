package com.enorth.cms.listener.popup;

import com.enorth.cms.listener.CommonOnTouchListener;

import android.view.View;

public abstract class PopupWindowOnTouchListener extends CommonOnTouchListener {
	
	public PopupWindowOnTouchListener(int touchSlop) {
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
	public boolean onImgChangeBegin() {
		return true;
	}

}
