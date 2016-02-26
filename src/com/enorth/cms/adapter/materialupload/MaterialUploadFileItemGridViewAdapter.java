package com.enorth.cms.adapter.materialupload;

import java.util.HashSet;
import java.util.Set;

import com.enorth.cms.listener.materialupload.MaterialUploadItemOnScrollListener;
import com.enorth.cms.task.BitmapWorkerTask;
import com.enorth.cms.view.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MaterialUploadFileItemGridViewAdapter extends ArrayAdapter<String> {
	/**
	 * 记录所有正在下载或等待下载的任务。
	 */
	private Set<BitmapWorkerTask> taskCollection;

	/**
	 * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
	 */
	private LruCache<String, Bitmap> mMemoryCache;
	/**
	 * GridView实例
	 */
	private GridView photoWall;
	/**
	 * 第一张可见图片的下标
	 */
	private int mFirstVisibleItem;

	/**
	 * 一屏有多少张图片可见
	 */
	private int mVisibleItemCount;

	/**
	 * 记录是否刚打开程序，用于解决进入程序不滚动屏幕，不会下载图片的问题。
	 */
	private boolean isFirstEnter = true;
	/**
	 * 图片的地址数组
	 */
	private String[] imgUrl;
	
	public MaterialUploadFileItemGridViewAdapter(Context context, int resource) {
		super(context, resource);
	}
	
	public MaterialUploadFileItemGridViewAdapter(Context context, int textViewResourceId, String[] objects,
			GridView photoWall) {
		super(context, textViewResourceId, objects);
		this.photoWall = photoWall;
		this.imgUrl = objects;
		taskCollection = new HashSet<BitmapWorkerTask>();
		// 获取应用程序最大可用内存
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;
		// 设置图片缓存大小为程序最大可用内存的1/8
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
		MaterialUploadItemOnScrollListener listener = new MaterialUploadItemOnScrollListener(this);
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
		final ImageView photo = (ImageView) view.findViewById(R.id.photo);
		// 给ImageView设置一个Tag，保证异步加载图片时不会乱序
		photo.setTag(url);
		setImageView(url, photo);
		return view;
	}
	
	@Override
	public String getItem(int position) {
		return imgUrl[position];
	}
	
	/**
	 * 给ImageView设置图片。首先从LruCache中取出图片的缓存，设置到ImageView上。如果LruCache中没有该图片的缓存，
	 * 就给ImageView设置一张默认图片。
	 * 
	 * @param imageUrl
	 *            图片的URL地址，用于作为LruCache的键。
	 * @param imageView
	 *            用于显示图片的控件。
	 */
	public void setImageView(String imageUrl, ImageView imageView) {
		Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			imageView.setImageResource(R.drawable.empty_photo);
		}
	}

	/**
	 * 将一张图片存储到LruCache中。
	 * 
	 * @param key
	 *            LruCache的键，这里传入图片的URL地址。
	 * @param bitmap
	 *            LruCache的键，这里传入从网络上下载的Bitmap对象。
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemoryCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	/**
	 * 从LruCache中获取一张图片，如果不存在就返回null。
	 * 
	 * @param key
	 *            LruCache的键，这里传入图片的URL地址。
	 * @return 对应传入键的Bitmap对象，或者null。
	 */
	public Bitmap getBitmapFromMemoryCache(String key) {
		return mMemoryCache.get(key);
	}
	
	public void loadBitmaps(int firstVisibleItem, int visibleItemCount) {
		try {
			for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
				String imageUrl = imgUrl[i];
				if (imageUrl == null) {
					System.out.println(1);
				}
				Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
				if (bitmap == null) {
					BitmapWorkerTask task = new BitmapWorkerTask(this);
					taskCollection.add(task);
					task.execute(imageUrl);
				} else {
					ImageView imageView = (ImageView) photoWall.findViewWithTag(imageUrl);
					if (imageView != null && bitmap != null) {
						imageView.setImageBitmap(bitmap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取消所有正在下载或等待下载的任务。
	 */
	public void cancelAllTasks() {
		if (taskCollection != null) {
			for (BitmapWorkerTask task : taskCollection) {
				task.cancel(false);
			}
		}
	}

	public Set<BitmapWorkerTask> getTaskCollection() {
		return taskCollection;
	}

	public void setTaskCollection(Set<BitmapWorkerTask> taskCollection) {
		this.taskCollection = taskCollection;
	}

	public LruCache<String, Bitmap> getmMemoryCache() {
		return mMemoryCache;
	}

	public void setmMemoryCache(LruCache<String, Bitmap> mMemoryCache) {
		this.mMemoryCache = mMemoryCache;
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

	public boolean isFirstEnter() {
		return isFirstEnter;
	}

	public void setFirstEnter(boolean isFirstEnter) {
		this.isFirstEnter = isFirstEnter;
	}
	
}
