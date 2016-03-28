package com.enorth.cms.listener.uploadpic;

import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.view.upload.UploadPicPreviewActivity;

import android.view.View;

public abstract class ImageViewFullScreenOnTouchListener extends CommonOnTouchListener {
	
	private UploadPicPreviewActivity activity;
	
	public ImageViewFullScreenOnTouchListener(UploadPicPreviewActivity activity) {
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
		
	}

	@Override
	public boolean isStopEventTransfer() {
		return true;
	}

}
