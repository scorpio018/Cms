package com.enorth.cms.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.enorth.cms.bean.upload.ImageFolder;
import com.enorth.cms.filter.upload.UploadImgFileNameFilter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public abstract class GalleryUtil {

	private ProgressDialog mProgressDialog;
	/**
	 * 临时的辅助类，用于防止同一个文件夹的多次扫描
	 */
	private HashSet<String> mDirPaths = new HashSet<String>();
	
	private int totalCount = 0;
	/**
	 * 扫描拿到所有的图片文件夹
	 */
	private List<ImageFolder> mImageFolders = new ArrayList<ImageFolder>();
	
	private Map<String, ImageFolder> mImageFoldersMap = new HashMap<String, ImageFolder>();
	
	private List<Integer> mImageFolderSize = new ArrayList<Integer>();
	
	private ImageFolder firstCheckedFolder;
	/**
	 * 存储文件夹中的图片数量
	 */
	private int mPicsSize;
	/**
	 * 所有的图片
	 */
	private List<String> mImgs = new ArrayList<String>();
	/**
	 * 图片数量最多的文件夹
	 */
	private File mImgDir = new File("");
	
	private Handler handler;
	
	private UploadImgFileNameFilter uploadImgFileNameFilter;
	/**
	 * 指定的文件夹
	 */
	private String designationPath;
	
	public GalleryUtil(Activity activity, String path) {
		this.designationPath = path;
		init();
		initHandler();
		getImages(activity);
	}
	
	private void init() {
		uploadImgFileNameFilter = new UploadImgFileNameFilter();
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
				String firstImage = null;
				Cursor mCursor = PhoneFileQueryUtil.getImg(activity);
//				Log.e("TAG", mCursor.getCount() + "");
				while (mCursor.moveToNext()){
					// 获取图片的路径
					String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
//					Log.e("TAG", path);
					// 拿到第一张图片的路径
					if (firstImage == null)
						firstImage = path;
					// 获取该图片的父路径名
					File parentFile = new File(path).getParentFile();
					if (parentFile == null)
						continue;
					String dirPath = parentFile.getAbsolutePath();
					ImageFolder imageFolder = null;
					// 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
					if (mDirPaths.contains(dirPath)){
						imageFolder = mImageFoldersMap.get(dirPath);
						imageFolder.getImgs().add(path);
						continue;
					} else{
						mDirPaths.add(dirPath);
						// 初始化imageFloder
						imageFolder = new ImageFolder();
						imageFolder.setDir(dirPath);
						imageFolder.setFirstImagePath(path);
					}
					if(parentFile.list()==null) {
						continue;
					}
					int picSize = parentFile.list(uploadImgFileNameFilter).length;
					totalCount += picSize;
					List<String> imgs = new ArrayList<String>();
					imgs.add(path);
					imageFolder.setImgs(imgs);
					imageFolder.setCount(picSize);
					mImageFolders.add(imageFolder);
					mImageFoldersMap.put(dirPath, imageFolder);
					mImageFolderSize.add(picSize);
					if (GalleryUtil.this.designationPath != null) {
						if (GalleryUtil.this.designationPath.equals(parentFile.getAbsolutePath())) {
							mPicsSize = picSize;
							mImgDir = parentFile;
							mImgs = imgs;
							firstCheckedFolder = imageFolder;
						}
					} else {
						if (picSize > mPicsSize){
							mPicsSize = picSize;
							mImgDir = parentFile;
							mImgs = imgs;
							firstCheckedFolder = imageFolder;
						}
					}
					
				}
				mCursor.close();
				// 扫描完成，辅助的HashSet也就可以释放内存了
				mDirPaths = null;
				// 通知Handler扫描图片完成
				handler.sendEmptyMessage(0);
			}
		}).start();
	}
	
	public abstract void setDataView();

	public List<String> getmImgs() {
		return mImgs;
	}

	public void setmImgs(List<String> mImgs) {
		this.mImgs = mImgs;
	}

	public File getmImgDir() {
		return mImgDir;
	}

	public void setmImgDir(File mImgDir) {
		this.mImgDir = mImgDir;
	}

	public ImageFolder getFirstCheckedFolder() {
		return firstCheckedFolder;
	}

	public void setFirstCheckedFolder(ImageFolder firstCheckedFolder) {
		this.firstCheckedFolder = firstCheckedFolder;
	}

	public List<ImageFolder> getmImageFolders() {
		return mImageFolders;
	}

	public void setmImageFolders(List<ImageFolder> mImageFolders) {
		this.mImageFolders = mImageFolders;
	}

	public Map<String, ImageFolder> getmImageFoldersMap() {
		return mImageFoldersMap;
	}

	public void setmImageFoldersMap(Map<String, ImageFolder> mImageFoldersMap) {
		this.mImageFoldersMap = mImageFoldersMap;
	}

	public int getmPicsSize() {
		return mPicsSize;
	}

	public void setmPicsSize(int mPicsSize) {
		this.mPicsSize = mPicsSize;
	}

	public List<Integer> getmImageFolderSize() {
		return mImageFolderSize;
	}

	public void setmImageFolderSize(List<Integer> mImageFolderSize) {
		this.mImageFolderSize = mImageFolderSize;
	}
	
}
