package com.enorth.cms.thread.uploadpic;

import java.io.IOException;

import com.enorth.cms.bean.upload.ImgBeanHolder;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.utils.ImageLoader;

import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class LoadImageLocalThread implements Runnable {
	
	private ImageLoader imageLoader;
	
	private String path;
	
	private ImageView imageView;
	
	public LoadImageLocalThread(ImageLoader imageLoader, String path, ImageView imageView) {
		this.imageLoader = imageLoader;
		this.path = path;
		this.imageView = imageView;
	}
	
	@Override
	public void run() {
		ImgBeanHolder holder = new ImgBeanHolder();
		try {
			holder.setBitmap(imageLoader.getBitmapSaveLruCache(path, imageView, ParamConst.FILE_LOAD_LOCATION_LOCAL));
		} catch (IOException e) {
			Log.e("LoadImageUrlThread.run() error", e.toString());
			e.printStackTrace();
		}
		holder.setImageView(imageView);
		holder.setPath(path);
		Message message = Message.obtain();
		message.obj = holder;
		// Log.e("TAG", "mHandler.sendMessage(message);");
		imageLoader.getmHandler().sendMessage(message);
		imageLoader.getmPoolSemaphore().release();
	}
}
