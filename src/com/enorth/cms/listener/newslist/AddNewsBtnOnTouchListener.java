package com.enorth.cms.listener.newslist;

import com.enorth.cms.listener.CommonOnTouchListener;

import android.view.View;

public abstract class AddNewsBtnOnTouchListener extends CommonOnTouchListener {
	
	@Override
	public boolean isClickBackgroungColorChange() {
		return false;
	}

	@Override
	public boolean onImgChangeBegin(View v) {
		return true;
	}

	@Override
	public void onImgChangeEnd(View v) {
		
	}

}
