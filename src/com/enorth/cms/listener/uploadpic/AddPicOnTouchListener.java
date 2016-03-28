package com.enorth.cms.listener.uploadpic;

import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ScreenTools;

import android.content.Context;
import android.view.View;

public abstract class AddPicOnTouchListener extends CommonOnTouchListener {
	
	public AddPicOnTouchListener(Context context) {
		super.touchSlop = ScreenTools.getTouchSlop(context);
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
		return true;
	}

	@Override
	public boolean isStopEventTransfer() {
		return true;
	}

}
