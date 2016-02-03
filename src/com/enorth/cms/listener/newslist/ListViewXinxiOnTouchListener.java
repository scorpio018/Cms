package com.enorth.cms.listener.newslist;

import com.enorth.cms.listener.CommonOnTouchListener;

public abstract class ListViewXinxiOnTouchListener extends CommonOnTouchListener {

	@Override
	public boolean isClickBackgroungColorChange() {
		return true;
	}

	@Override
	public boolean onImgChangeBegin() {
		return true;
	}

	@Override
	public void onImgChangeEnd() {

	}
}
