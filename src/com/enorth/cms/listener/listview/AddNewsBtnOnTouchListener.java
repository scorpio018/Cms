package com.enorth.cms.listener.listview;

import com.enorth.cms.listener.CommonOnTouchListener;

public abstract class AddNewsBtnOnTouchListener extends CommonOnTouchListener {
	
	@Override
	public boolean isClickBackgroungColorChange() {
		return false;
	}

	@Override
	public boolean onImgChangeBegin() {
		return true;
	}

	@Override
	public void onImgChangeEnd() {
		
	}

}
