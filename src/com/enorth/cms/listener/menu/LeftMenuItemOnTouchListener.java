package com.enorth.cms.listener.menu;

import com.enorth.cms.listener.CommonOnTouchListener;

import android.view.View;

public abstract class LeftMenuItemOnTouchListener extends CommonOnTouchListener {

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
	public boolean onImgChangeBegin() {
		return true;
	}

	@Override
	public void onImgChangeEnd() {
		
	}

}
