package com.enorth.cms.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.enorth.cms.bean.upload.ImageSize;
import com.enorth.cms.bean.upload.ImgBeanHolder;
import com.enorth.cms.bean.upload.VideoInfo;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.enums.Type;
import com.enorth.cms.thread.uploadpic.LoadImageLocalThread;
import com.enorth.cms.thread.uploadpic.LoadImageUrlThread;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

@SuppressLint("NewApi")
public class ImageLoader {
	/**
	 * 图片缓存的核心类
	 */
	private LruCache<String, Bitmap> mLruCache;
	/**
	 * 线程池
	 */
	private ExecutorService mThreadPool;
	/**
	 * 线程池的线程数量，默认为1
	 */
	private int mThreadCount = 1;
	/**
	 * 队列的调度方式
	 */
	private Type mType = Type.LIFO;
	/**
	 * 任务队列
	 */
	private LinkedList<Runnable> mTasks;
	/**
	 * 轮询的线程
	 */
	private Thread mPoolThread;
	private Handler mPoolThreadHander;

	/**
	 * 运行在UI线程的handler，用于给ImageView设置图片
	 */
	private Handler mHandler;

	/**
	 * 引入一个值为1的信号量，防止mPoolThreadHander未初始化完成
	 */
	private volatile Semaphore mSemaphore = new Semaphore(0);

	/**
	 * 引入一个值为1的信号量，由于线程池内部也有一个阻塞线程，防止加入任务的速度过快，使LIFO效果不明显
	 */
	private volatile Semaphore mPoolSemaphore;

	private static ImageLoader mInstance;

	/**
	 * 单例获得该实例对象
	 * 
	 * @return
	 */
	public static ImageLoader getInstance() {

		if (mInstance == null) {
			synchronized (ImageLoader.class) {
				if (mInstance == null) {
					mInstance = new ImageLoader(1, Type.LIFO);
				}
			}
		}
		return mInstance;
	}

	private ImageLoader(int threadCount, Type type) {
		init(threadCount, type);
	}

	private void init(int threadCount, Type type) {
		// loop thread
		mPoolThread = new Thread() {
			@Override
			public void run() {
				Looper.prepare();

				mPoolThreadHander = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						mThreadPool.execute(getTask());
						try {
							mPoolSemaphore.acquire();
						} catch (InterruptedException e) {
							Log.e("mPoolSemaphore.acquire() Exception", e.toString());
						}
					}
				};
				// 释放一个信号量
				mSemaphore.release();
				Looper.loop();
			}
		};
		mPoolThread.start();

		// 获取应用程序最大可用内存
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;
		mLruCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			};
		};

		mThreadPool = Executors.newFixedThreadPool(threadCount);
		mPoolSemaphore = new Semaphore(threadCount);
		mTasks = new LinkedList<Runnable>();
		mType = type == null ? Type.LIFO : type;

	}

	/**
	 * 加载图片
	 * 
	 * @param path
	 * @param imageView
	 */
	public void loadImageLocal(String path, ImageView imageView) {
		loadImage(path, imageView, new LoadImageLocalThread(this, path, imageView));
	}
	
	public void loadImageUrl(String path, ImageView imageView) {
		loadImage(path, imageView, new LoadImageUrlThread(this, path, imageView));
	}
	
	private void loadImage(String path, ImageView imageView, Runnable runnable) {
		// set tag
		imageView.setTag(path);
		// UI线程
		if (mHandler == null) {
			mHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
					ImageView imageView = holder.getImageView();
					Bitmap bm = holder.getBitmap();
					String path = holder.getPath();
					if (imageView.getTag().toString().equals(path)) {
						imageView.setImageBitmap(bm);
//												imageView.setEnabled(true);
					}
				}
			};
		}

		Bitmap bm = getBitmapFromLruCache(path);
		if (bm != null) {
			ImgBeanHolder holder = new ImgBeanHolder();
			holder.setBitmap(bm);;
			holder.setImageView(imageView);
			holder.setPath(path);
			Message message = Message.obtain();
			message.obj = holder;
			mHandler.sendMessage(message);
		} else {
			addTask(runnable);
		}
	}
	
	/**
	 * 通过传入的图片路径和ImageView控件获取bitmap，并存入LruCache中
	 * @param path
	 * @param imageView
	 * @return
	 * @throws IOException 
	 */
	public Bitmap getBitmapSaveLruCache(String path, ImageView imageView, int loadLocation) throws IOException {
		Bitmap bm = getBitmap(path, imageView, loadLocation);
		addBitmapToLruCache(path, bm);
		return getBitmapFromLruCache(path);
	}
	
	/**
	 * 通过传入的图片路径和ImageView控件
	 * @param path
	 * @param imageView
	 * @return
	 * @throws IOException 
	 */
	public Bitmap getBitmap(String path, ImageView imageView, int loadLocation) throws IOException {
		ImageSize imageSize = getImageViewWidth(imageView);

		int reqWidth = imageSize.getWidth();
		int reqHeight = imageSize.getHeight();

		return decodeSampledBitmapFromResource(path, reqWidth, reqHeight, loadLocation);
	}
	/**
	 * 将imageView加载bitmap（如果图片是翻转过的，则将图片翻转回来）
	 * @param imageView
	 * @param localPath
	 */
	public void initBitmapByLocalPath(ImageView imageView, String localPath) {
		Bitmap bitmapRotaDegree = MediaUtils.getBitmapRotaDegree(localPath);
		imageView.setImageBitmap(bitmapRotaDegree);
	}

	/**
	 * 添加一个任务
	 * 
	 * @param runnable
	 */
	private synchronized void addTask(Runnable runnable) {
		try {
			// 请求信号量，防止mPoolThreadHander为null
			if (mPoolThreadHander == null)
				mSemaphore.acquire();
		} catch (InterruptedException e) {
		}
		mTasks.add(runnable);

		mPoolThreadHander.sendEmptyMessage(0x110);
	}

	/**
	 * 取出一个任务
	 * 
	 * @return
	 */
	private synchronized Runnable getTask() {
		if (mType == Type.FIFO) {
			return mTasks.removeFirst();
		} else if (mType == Type.LIFO) {
			return mTasks.removeLast();
		}
		return null;
	}

	/**
	 * 单例获得该实例对象
	 * 
	 * @return
	 */
	public static ImageLoader getInstance(int threadCount, Type type) {

		if (mInstance == null) {
			synchronized (ImageLoader.class) {
				if (mInstance == null) {
					mInstance = new ImageLoader(threadCount, type);
				}
			}
		}
		return mInstance;
	}

	/**
	 * 根据ImageView获得适当的压缩的宽和高
	 * 
	 * @param imageView
	 * @return
	 */
	private ImageSize getImageViewWidth(ImageView imageView) {
		ImageSize imageSize = new ImageSize();
		final DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
		final LayoutParams params = imageView.getLayoutParams();

		// Get actual image width
		int width = params.width == LayoutParams.WRAP_CONTENT ? 0 : imageView.getWidth(); 
		if (width <= 0) {
			// Get layout width parameter
			width = params.width;
		}
		if (width <= 0) {
			// Check maxWidth parameter
			width = getImageViewFieldValue(imageView, "mMaxWidth"); 
		}
		if (width <= 0) {
			width = displayMetrics.widthPixels;
		}
		int height = params.height == LayoutParams.WRAP_CONTENT ? 0 : imageView.getHeight(); // Get
																								// actual
																								// image
																								// height
		if (height <= 0)
			height = params.height; // Get layout height parameter
		if (height <= 0)
			height = getImageViewFieldValue(imageView, "mMaxHeight"); // Check maxHeight parameter
		if (height <= 0)
			height = displayMetrics.heightPixels;
		imageSize.setWidth(width);
		imageSize.setHeight(height);
		return imageSize;
	}

	/**
	 * 从LruCache中获取一张图片，如果不存在就返回null。
	 */
	private Bitmap getBitmapFromLruCache(String key) {
		return mLruCache.get(key);
	}

	/**
	 * 往LruCache中添加一张图片
	 * 
	 * @param key
	 * @param bitmap
	 */
	private void addBitmapToLruCache(String key, Bitmap bitmap) {
		if (getBitmapFromLruCache(key) == null) {
			if (bitmap != null)
				mLruCache.put(key, bitmap);
		}
	}

	/**
	 * 计算inSampleSize，用于压缩图片
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// 源图片的宽度
		int width = options.outWidth;
		int height = options.outHeight;
		int inSampleSize = 1;

		if (width > reqWidth && height > reqHeight) {
			// 计算出实际宽度和目标宽度的比率
			int widthRatio = Math.round((float) width / (float) reqWidth);
			int heightRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = Math.max(widthRatio, heightRatio);
		}
		return inSampleSize;
	}

	/**
	 * 根据计算的inSampleSize，得到压缩后图片
	 * 
	 * @param pathName
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 * @throws IOException 
	 */
	private Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth, int reqHeight, int loadLocation) throws IOException {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		Bitmap bitmap = null;
		switch(loadLocation) {
		case ParamConst.FILE_LOAD_LOCATION_LOCAL:
			bitmap = BitmapFactory.decodeFile(pathName, options);
			break;
		case ParamConst.FILE_LOAD_LOCATION_URL:
			HttpURLConnection resourceConn = getResourceConn(pathName);
			bitmap = BitmapFactory.decodeStream(resourceConn.getInputStream(), null, options);
			break;
		}
//		return bitmap;
		
		int degree = MediaUtils.readPictureDegree(pathName);
        if(degree == 0){
            return bitmap;
        }
		Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap tempBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return tempBitmap;
		
	}
	
	/**
	 * 获取网络资源的HttpUrlConnection
	 * @param imageUrl
	 * @return
	 */
	private HttpURLConnection getResourceConn(String imageUrl) {
		HttpURLConnection con = null;
		try {
			URL url = new URL(imageUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5 * 1000);
			con.setReadTimeout(10 * 1000);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);//不缓存  
		} catch (Exception e) {
			Log.e("ImageLoader.getResourceConn() error", e.toString());
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		return con;
	}

	/**
	 * 反射获得ImageView设置的最大宽度和高度
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	private static int getImageViewFieldValue(Object object, String fieldName) {
		int value = 0;
		try {
			Field field = ImageView.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			int fieldValue = (Integer) field.get(object);
			if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
				value = fieldValue;

				Log.e("TAG", value + "");
			}
		} catch (Exception e) {
		}
		return value;
	}

	public Handler getmHandler() {
		return mHandler;
	}

	public void setmHandler(Handler mHandler) {
		this.mHandler = mHandler;
	}

	public Semaphore getmPoolSemaphore() {
		return mPoolSemaphore;
	}

	public void setmPoolSemaphore(Semaphore mPoolSemaphore) {
		this.mPoolSemaphore = mPoolSemaphore;
	}
	
	
}
