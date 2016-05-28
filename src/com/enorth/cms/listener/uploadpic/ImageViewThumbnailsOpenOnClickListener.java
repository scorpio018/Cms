package com.enorth.cms.listener.uploadpic;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.adapter.upload.ImageGridItemContainCheckAdapter;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.utils.ActivityJumpUtil;
import com.enorth.cms.view.upload.UploadPicPreviewActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageViewThumbnailsOpenOnClickListener extends CommonOnClickListener {
	
	private Activity activity;
	
	private ImageGridItemContainCheckAdapter adapter;
	
	private int checkPosition;
	
	private String broadcastAction;
	
	public ImageViewThumbnailsOpenOnClickListener(Activity activity, ImageGridItemContainCheckAdapter adapter, int checkPosition, String broadcastAction) {
		this.activity = activity;
		this.adapter = adapter;
		this.checkPosition = checkPosition;
		this.broadcastAction = broadcastAction;
	}

	@Override
	public void onClick(View v) {
		ArrayList<String> datas = (ArrayList<String>) adapter.getmDatas();
		ArrayList<String> checkedDatas = (ArrayList<String>) ImageGridItemContainCheckAdapter.getmSelectedImage();
		Intent intent = new Intent();
		intent.putExtra(ParamConst.BROADCAST_ACTION, broadcastAction);
		ActivityJumpUtil.sendImgDatasToActivity(datas, checkedDatas, checkPosition, activity, UploadPicPreviewActivity.class, intent);
	}

}
