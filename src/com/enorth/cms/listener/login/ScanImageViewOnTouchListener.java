package com.enorth.cms.listener.login;

import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ScreenTools;

import android.app.Activity;
import android.view.View;

public abstract class ScanImageViewOnTouchListener extends CommonOnTouchListener {
	
	private Activity activity;
	
	public ScanImageViewOnTouchListener(Activity activity) {
		super.touchSlop = ScreenTools.getTouchSlop(activity);
		this.activity = activity;
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
		return false;
	}

	@Override
	public void onImgChangeDo(View v) {
		
	}

	@Override
	public boolean isStopEventTransfer() {
		return true;
	}

}
