package com.enorth.cms.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.enorth.cms.bean.upload.VideoInfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Media;
import android.provider.MediaStore.Video.Thumbnails;
import android.widget.Toast;

public abstract class VideoUtil {
	private ProgressDialog mProgressDialog;
	
	private List<VideoInfo> videoInfos = new ArrayList<VideoInfo>();
	
	private List<String> thumbnailDatas = new ArrayList<String>();
	
	private Handler handler;
	
	public VideoUtil(Activity activity) {
		initHandler();
		getImages(activity);
	}
	
	private void initHandler() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				mProgressDialog.dismiss();
				setDataView();
			}
		};
	}
	
	/**
	 * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
	 */
	public void getImages(final Activity activity){
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			Toast.makeText(activity, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}
		// 显示进度条
		mProgressDialog = ProgressDialog.show(activity, null, "正在加载...");
		new Thread(new Runnable(){
			@Override
			public void run(){
//				Cursor cursor = PhoneFileQueryUtil.getVideoThumbnail(activity);
				Cursor cursor = PhoneFileQueryUtil.getVideo(activity);
//				Log.e("TAG", mCursor.getCount() + "");
				while (cursor.moveToNext()){
					VideoInfo info = new VideoInfo();
					// 获取视频的路径
					info.setVideoPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
					info.setMimeType(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE)));
					info.setVideoId(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID)));
					Cursor thumbCursor = PhoneFileQueryUtil.getVideoThumbnailById(activity, info.getVideoId());
					if (thumbCursor.moveToFirst()) {
						info.setThumbPath(thumbCursor.getString(thumbCursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA)));
						thumbnailDatas.add(info.getThumbPath());
					}
					
					
					// 此处是获取缩略图时需要存入的值
//					info.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Thumbnails._ID)));
//					info.setVideoId(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Thumbnails.VIDEO_ID)));
//					info.setThumbPath(cursor.getString(cursor.getColumnIndex(Thumbnails.DATA)));
//					thumbnailDatas.add(cursor.getString(cursor.getColumnIndex(Thumbnails.DATA)));
					
					/*Cursor videoThumb = PhoneFileQueryUtil.getVideoThumb(activity, videoId);
					if (videoThumb.moveToFirst()) {
						info.setThumbPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA)));
					}*/
//					videoInfosMap.put(info.getThumbPath(), info);
					videoInfos.add(info);
				}
				cursor.close();
				// 通知Handler扫描图片完成
				handler.sendEmptyMessage(0);
			}
		}).start();
	}
	
	public abstract void setDataView();

	public List<VideoInfo> getVideoInfos() {
		return videoInfos;
	}

	public void setVideoInfos(List<VideoInfo> videoInfos) {
		this.videoInfos = videoInfos;
	}

	public List<String> getThumbnailDatas() {
		return thumbnailDatas;
	}

	public void setThumbnailDatas(List<String> thumbnailDatas) {
		this.thumbnailDatas = thumbnailDatas;
	}
	
}
