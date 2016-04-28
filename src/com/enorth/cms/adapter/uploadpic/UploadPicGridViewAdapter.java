package com.enorth.cms.adapter.uploadpic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.enorth.cms.listener.uploadpic.UploadPicGridViewOnScrollListener;
import com.enorth.cms.task.LocalPicWorkerTask;
import com.enorth.cms.utils.MemoryCacheUtil;
import com.enorth.cms.view.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class UploadPicGridViewAdapter extends ArrayAdapter<Object> {
	
	private Object[] imgs;
	
	private GridView photoWall;
	/**
	 * 第一张可见图片的下标
	 */
	private int mFirstVisibleItem;
	/**
	 * 一屏有多少张图片可见
	 */
	private int mVisibleItemCount;
	
	private MemoryCacheUtil memoryCacheUtil;
	
	private Set<LocalPicWorkerTask> tasks = new HashSet<LocalPicWorkerTask>();
	
	private Map<String, ImageView> picImageViewMap = new HashMap<String, ImageView>();

	private boolean isFirstEnter = true;
	
	public UploadPicGridViewAdapter(Context context, int resource) {
		super(context, resource);
	}
	
	public UploadPicGridViewAdapter(Context context, int textViewResourceId, Object[] objects,
			GridView photoWall) {
		super(context, textViewResourceId, objects);
		this.imgs = objects;
		this.photoWall = photoWall;
		memoryCacheUtil = new MemoryCacheUtil();
		UploadPicGridViewOnScrollListener listener = new UploadPicGridViewOnScrollListener(this);
		photoWall.setOnScrollListener(listener);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final String url = getItem(position);
		View view;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(R.layout.photo_layout, null);
		} else {
			view = convertView;
		}
		ImageView photo = (ImageView) view.findViewById(R.id.materialUploadFileItemImageView);
		// 给ImageView设置一个Tag，保证异步加载图片时不会乱序
//		photo.setTag(position);
		setImageView(url, photo);
		return view;
	}
	
	@Override
	public String getItem(int position) {
		return imgs[position].toString();
	}
	
	private void setImageView(String url, ImageView imageView) {
		Bitmap bitmap = memoryCacheUtil.getBitmapFromMemoryCache(url);
		if (bitmap == null) {
			imageView.setImageResource(R.drawable.empty_photo);
		} else {
			imageView.setImageBitmap(bitmap);
		}
		picImageViewMap.put(url, imageView);
		
	}
	
	public void loadAllTasks(int firstVisibleItem, int visibleItemCount) {
		for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
			String imageUrl = imgs[i].toString();
			Bitmap bitmap = memoryCacheUtil.getBitmapFromMemoryCache(imageUrl);
			ImageView imageView = picImageViewMap.get(imageUrl);
			if (bitmap == null) {
				LocalPicWorkerTask task = new LocalPicWorkerTask(this, imageView);
				tasks.add(task);
				task.execute(imageUrl, String.valueOf(i));
			} else {
//				ImageView imageView = (ImageView) photoWall.findViewWithTag(i);
				if (imageView != null && bitmap != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}
	}
	
	public void cancelAllTasks() {
		if (tasks != null) {
			for (LocalPicWorkerTask task : tasks) {
				task.cancel(false);
			}
		}
	}

	public GridView getPhotoWall() {
		return photoWall;
	}

	public void setPhotoWall(GridView photoWall) {
		this.photoWall = photoWall;
	}

	public int getmFirstVisibleItem() {
		return mFirstVisibleItem;
	}

	public void setmFirstVisibleItem(int mFirstVisibleItem) {
		this.mFirstVisibleItem = mFirstVisibleItem;
	}

	public int getmVisibleItemCount() {
		return mVisibleItemCount;
	}

	public void setmVisibleItemCount(int mVisibleItemCount) {
		this.mVisibleItemCount = mVisibleItemCount;
	}

	public Set<LocalPicWorkerTask> getTasks() {
		return tasks;
	}

	public void setTasks(Set<LocalPicWorkerTask> tasks) {
		this.tasks = tasks;
	}

	public boolean isFirstEnter() {
		return isFirstEnter;
	}

	public void setFirstEnter(boolean isFirstEnter) {
		this.isFirstEnter = isFirstEnter;
	}
}
