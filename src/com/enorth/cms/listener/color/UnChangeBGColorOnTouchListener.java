package com.enorth.cms.listener.color;

import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ScreenTools;

import android.app.Activity;
import android.view.View;

public abstract class UnChangeBGColorOnTouchListener extends CommonOnTouchListener {
	
	public UnChangeBGColorOnTouchListener(Activity activity) {
		super.touchSlop = ScreenTools.getTouchSlop(activity);
	}

	@Override
	public boolean isClickBackgroungColorChange() {
		return false;
	}
	
	@Override
	public void onTouchBegin() {
		
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
