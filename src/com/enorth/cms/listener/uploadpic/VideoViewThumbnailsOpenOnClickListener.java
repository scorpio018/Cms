package com.enorth.cms.listener.uploadpic;

import java.util.ArrayList;

import com.enorth.cms.adapter.upload.ImageGridItemContainCheckAdapter;
import com.enorth.cms.adapter.upload.VideoGridItemContainCheckAdapter;
import com.enorth.cms.bean.upload.VideoInfo;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.utils.ActivityJumpUtil;
import com.enorth.cms.utils.PhoneFileQueryUtil;
import com.enorth.cms.view.upload.UploadPicPreviewActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

public class VideoViewThumbnailsOpenOnClickListener extends CommonOnClickListener {
	
	private Activity activity;
	
	private VideoGridItemContainCheckAdapter adapter;
	
	private int checkPosition;
	
	public VideoViewThumbnailsOpenOnClickListener(Activity activity, VideoGridItemContainCheckAdapter adapter, int checkPosition) {
		this.activity = activity;
		this.adapter = adapter;
		this.checkPosition = checkPosition;
	}

	@Override
	public void onClick(View v) {
		VideoInfo videoInfo = adapter.getVideoInfos().get(checkPosition);
		/*Cursor cursor = PhoneFileQueryUtil.getVideoById(activity, videoInfo.getVideoId());
		if (cursor.moveToFirst()) {
			String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
			String type = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.parse(path), type);
			activity.startActivity(intent);
		}*/
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse(videoInfo.getVideoPath()), videoInfo.getMimeType());
		activity.startActivity(intent);
		/*ArrayList<String> datas = (ArrayList<String>) adapter.getmDatas();
		ArrayList<String> checkedDatas = (ArrayList<String>) VideoGridItemContainCheckAdapter.getmSelectedImage();
		ActivityJumpUtil.sendImgDatasToActivity(datas, checkedDatas, checkPosition, activity, UploadPicPreviewActivity.class);*/
		
	}

}
