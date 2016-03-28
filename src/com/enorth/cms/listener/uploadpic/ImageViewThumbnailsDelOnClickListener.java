package com.enorth.cms.listener.uploadpic;

import com.enorth.cms.adapter.upload.ImageGridItemContainDelAdapter;
import com.enorth.cms.bean.upload.ViewThumbnails;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.view.upload.UploadPicFinishCheckActivity;

import android.view.View;

public class ImageViewThumbnailsDelOnClickListener extends CommonOnClickListener {

	
	private int position;
	

	private UploadPicFinishCheckActivity activity;

	public ImageViewThumbnailsDelOnClickListener(int position, UploadPicFinishCheckActivity activity) {
		this.position = position;
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		activity.removePic(position);
	}
}
