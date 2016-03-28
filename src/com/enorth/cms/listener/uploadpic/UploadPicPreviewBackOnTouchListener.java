package com.enorth.cms.listener.uploadpic;

import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ScreenTools;

import android.app.Activity;
import android.view.View;

public abstract class UploadPicPreviewBackOnTouchListener extends CommonOnTouchListener {
	
	private Activity activity;
	
	public UploadPicPreviewBackOnTouchListener(Activity activity) {
		super.touchSlop = ScreenTools.getTouchSlop(activity);
		this.activity = activity;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isStopEventTransfer() {
		return true;
	}

}
