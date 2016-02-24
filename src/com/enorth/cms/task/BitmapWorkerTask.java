package com.enorth.cms.task;

import com.enorth.cms.adapter.materialupload.MaterialUploadFileItemGridViewAdapter;
import com.enorth.cms.utils.ImgUtil;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
	
	private MaterialUploadFileItemGridViewAdapter adapter;
	
	public BitmapWorkerTask(MaterialUploadFileItemGridViewAdapter adapter) {
		this.adapter = adapter;
	}

	/**
	 * 图片的URL地址
	 */
	private String imageUrl;

	@Override
	protected Bitmap doInBackground(String... params) {
		imageUrl = params[0];
		// 在后台开始下载图片
		Bitmap bitmap = ImgUtil.downloadBitmap(params[0]);
		if (bitmap != null) {
			// 图片下载完成后缓存到LrcCache中
			adapter.addBitmapToMemoryCache(params[0], bitmap);
		}
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		super.onPostExecute(bitmap);
		// 根据Tag找到相应的ImageView控件，将下载好的图片显示出来。
		ImageView imageView = (ImageView) adapter.getPhotoWall().findViewWithTag(imageUrl);
		if (imageView != null && bitmap != null) {
			imageView.setImageBitmap(bitmap);
		}
		adapter.getTaskCollection().remove(this);
	}

}
