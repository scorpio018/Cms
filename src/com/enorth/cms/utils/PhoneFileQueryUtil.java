package com.enorth.cms.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.Log;

public class PhoneFileQueryUtil {

	private static String takePhotoPath;

	/**
	 * 获取手机里的所有图片（只查询jpeg和png）
	 * 
	 * @param activity
	 * @return
	 */
	public static Cursor getImg(Activity activity) {
		Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		ContentResolver mContentResolver = activity.getContentResolver();
		// 只查询jpeg和png的图片
		Cursor mCursor = mContentResolver.query(mImageUri, null,
				MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
				new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED + " desc");
		return mCursor;
	}

	/**
	 * 获取所有的视频信息
	 * @param activity
	 * @return
	 */
	public static Cursor getVideo(Activity activity) {
		updateMediaDataBase(activity, Environment.getExternalStorageDirectory().getAbsolutePath());
		Uri mVideoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		String[] mediaColumns = new String[] { MediaStore.Video.Media.DATA, MediaStore.Video.Media._ID,
				MediaStore.Video.Media.MIME_TYPE };
		// 首先检索SDcard上所有的video
		ContentResolver mContentResolver = activity.getContentResolver();
		return mContentResolver.query(mVideoUri, mediaColumns, null, null, null);
	}
	
	/**
	 * 由于进行视频录像之后，不会实时刷新，所以需要调用这个方法进行刷新
	 * @param activity
	 * @param filename
	 */
	private static void updateMediaDataBase(Activity activity, String filename) {
		int currentApiVersion = android.os.Build.VERSION.SDK_INT;// 获得当前sdk版本
		if (currentApiVersion < 19) {
//			activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + filename)));
			activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, 
					Uri.parse("file://" + Environment.getExternalStorageDirectory())));
		} else {
			MediaScannerConnection.scanFile(activity, new String[] { filename }, null,
			new MediaScannerConnection.OnScanCompletedListener() {
				public void onScanCompleted(String path, Uri uri) {
					Log.i("ExternalStorage", "Scanned " + path + ":");
					Log.i("ExternalStorage", "-> uri=" + uri);
				}
			});
		}
	}
	
	/**
	 * 获取视频的缩略图信息
	 * @param activity
	 * @return
	 */
	public static Cursor getVideoThumbnail(Activity activity) {
		/*Uri mVideoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		String[] mediaColumns = new String[] { MediaStore.Video.Media.DATA, MediaStore.Video.Media._ID,
				MediaStore.Video.Media.TITLE, MediaStore.Video.Media.MIME_TYPE };*/
		Uri mVideoThumbnailUri = Thumbnails.EXTERNAL_CONTENT_URI;
		String[] mediaColumns = new String[] {Thumbnails._ID, Thumbnails.VIDEO_ID, Thumbnails.DATA};

		// 首先检索SDcard上所有的video
		ContentResolver mContentResolver = activity.getContentResolver();
		return mContentResolver.query(mVideoThumbnailUri, mediaColumns, null, null, null);
		// cursor = activity.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
		// mediaColumns, null, null, null);
	}
	
	/**
	 * 通过视频ID获取对应的缩略图
	 * @param activity
	 * @param videoId
	 * @return
	 */
	public static Cursor getVideoThumbnailById(Activity activity, int videoId) {
		Uri mVideoThumbnailUri = Thumbnails.EXTERNAL_CONTENT_URI;
		String[] mediaColumns = new String[] {Thumbnails._ID, Thumbnails.VIDEO_ID, Thumbnails.DATA};
		ContentResolver mContentResolver = activity.getContentResolver();
		return mContentResolver.query(mVideoThumbnailUri, mediaColumns, MediaStore.Video.Thumbnails.VIDEO_ID + "=?", new String[]{String.valueOf(videoId)}, null);
	}
	
	/**
	 * 获取视频的缩略图
	 * @return
	 */
	public static Cursor getVideoById(Activity activity, int videoId) {
		Uri mVideoThumbUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		String[] mediaColumns = new String[] { MediaStore.Video.Media.DATA, MediaStore.Video.Media._ID,
				MediaStore.Video.Media.TITLE, MediaStore.Video.Media.MIME_TYPE };
		ContentResolver mContentResolver = activity.getContentResolver();
		String selection = MediaStore.Video.Media._ID +"=?";  
        String[] selectionArgs = new String[]{String.valueOf(videoId)};
		return mContentResolver.query(mVideoThumbUri, mediaColumns, selection, selectionArgs, null);
	}

	/**
	 * 通过查询的图片，将图片最多的文件夹当作默认保存新拍的照片的文件夹
	 * 
	 * @param mCursor
	 * @return
	 */
	public static String getTakePhotoPath(Activity activity) {
		if (takePhotoPath == null) {
			takePhotoPath = activity.getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath();
		}
		return takePhotoPath;
	}
}
