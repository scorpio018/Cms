package com.enorth.cms.listener.materialupload;

import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.view.material.MaterialUploadActivity;

import android.util.Log;
import android.view.View;

public class MaterialUploadBottomOnClickListener extends CommonOnClickListener {
	
	private MaterialUploadActivity activity;
	
	public MaterialUploadBottomOnClickListener(MaterialUploadActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		Log.e("bottomClick", "click");
		activity.initPopupWindow();
	}

}
