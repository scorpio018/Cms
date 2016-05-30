package com.enorth.cms.bean.upload;

import android.graphics.Bitmap;

public class VideoInfo {

	private int videoId;

	private String mimeType;

	private String thumbPath;

	private Bitmap thumbBitmap;

	private String videoPath;

	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Bitmap getThumbBitmap() {
		return thumbBitmap;
	}

	public void setThumbBitmap(Bitmap thumbBitmap) {
		this.thumbBitmap = thumbBitmap;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

}
