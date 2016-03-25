package com.enorth.cms.listener.newslist.subtitle;

import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ScreenTools;

import android.content.Context;
import android.view.View;

public abstract class ChooseChannelOnTouchListener extends CommonOnTouchListener {
	
	public ChooseChannelOnTouchListener(Context context) {
		super.touchSlop = ScreenTools.getTouchSlop(context);
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
