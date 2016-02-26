package com.enorth.cms.listener.newslist;

import com.enorth.cms.listener.CommonOnTouchListener;

import android.view.View;

public abstract class ListViewXinxiOnTouchListener extends CommonOnTouchListener {

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
}
