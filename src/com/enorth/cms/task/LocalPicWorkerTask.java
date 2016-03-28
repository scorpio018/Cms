package com.enorth.cms.task;

import com.enorth.cms.adapter.uploadpic.UploadPicGridViewAdapter;
import com.enorth.cms.utils.CameraUtil;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.ImageView;

public class LocalPicWorkerTask extends AsyncTask<String, Void, Bitmap> {
	
	private UploadPicGridViewAdapter adapter;
	
	private String img;
	
	private ImageView imageView;
	
	private int position;
	
	public LocalPicWorkerTask(UploadPicGridViewAdapter adapter, ImageView imageView) {
		this.adapter = adapter;
		this.imageView = imageView;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		img = params[0];
		position = Integer.parseInt(params[1]);
		Bitmap loacalBitmap = CameraUtil.getLoacalBitmap(params[0]);
		return loacalBitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
//		ImageView imageView = (ImageView) adapter.getPhotoWall().findViewWithTag(position);
		imageView.setImageBitmap(result);
		adapter.getTasks().remove(this);
	}

}
