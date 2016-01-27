package com.enorth.cms.listener.listview.subtitle;

import com.enorth.cms.listener.CommonOnTouchListener;

import android.view.View;

public abstract class ChannelOnTouchListener extends CommonOnTouchListener {

	@Override
	public boolean isClickBackgroungColorChange() {
		return true;
	}

	@Override
	public void touchMove(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onImgChangeBegin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onImgChangeEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onImgChangeDo() {
		// TODO Auto-generated method stub
		
	}

}
