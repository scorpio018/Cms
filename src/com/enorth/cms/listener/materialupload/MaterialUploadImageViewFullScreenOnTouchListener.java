package com.enorth.cms.listener.materialupload;

import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.view.material.MaterialUploadPicPreviewActivity;
import com.enorth.cms.view.upload.UploadPicPreviewActivity;

import android.view.View;

public abstract class MaterialUploadImageViewFullScreenOnTouchListener extends CommonOnTouchListener {
	
	private MaterialUploadPicPreviewActivity activity;
	
	public MaterialUploadImageViewFullScreenOnTouchListener(MaterialUploadPicPreviewActivity activity) {
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
