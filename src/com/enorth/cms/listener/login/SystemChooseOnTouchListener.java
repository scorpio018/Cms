package com.enorth.cms.listener.login;

import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ScreenTools;

import android.content.Context;
import android.view.View;

public abstract class SystemChooseOnTouchListener extends CommonOnTouchListener {
	
	public SystemChooseOnTouchListener(Context context) {
		super(context);
		super.touchSlop = ScreenTools.getTouchSlop(context);
	}

	@Override
	public void onTouchBegin() {
		
	}

	@Override
	public boolean isClickBackgroungColorChange() {
		return false;
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
