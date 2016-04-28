package com.enorth.cms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.view.upload.GalleryActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class CameraUtil {
	
	/*private static SimpleDateFormat sdf = null;
	
	private static String path;*/
	
	/*static {
		sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
		path = Environment.getExternalStorageDirectory() + "/DCIM";
	}*/
	
	public static void takePhotoGallery(Activity activity) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		activity.startActivityForResult(intent, ParamConst.CAMERA_SELECT);
	}
	
	public static List<String> getPhotoList(Activity activity) {
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		ContentResolver contentResolver = activity.getContentResolver();
		Cursor c = contentResolver.query(uri,
				new String[] { MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME,
						MediaStore.Images.Media.DATA },
				MediaStore.Images.Media.MIME_TYPE + "=? OR " + MediaStore.Images.Media.MIME_TYPE + "=?",
				new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED + " desc");
//		mImageFolders.clear();
		c.moveToFirst();
		List<String> paths = new ArrayList<String>();
		while (c.moveToNext()) {
			String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
			paths.add(path);
			// File parentFile = new File(path).getParentFile();
			// if (parentFile != null) {
			// String name = parentFile.getAbsolutePath();
			// ImageFolder folder = getImageFolder(name);
			// if (folder == null) {
			// folder = new ImageFolder(name);
			// mImageFolders.add(folder);
			// }
			// folder.addImage(path);
			// }
		}
		c.close();
		return paths;
	}
	
	private static String imagename;
	
	private static File newTakePhotoFile;
	
	public static void takePhoto(Activity activity, String path) {
		imagename = TimeUtil.getDateYMHHMSsNoConnector() + ".jpg";
		String state = Environment.getExternalStorageState();  
		if (state.equals(Environment.MEDIA_MOUNTED)) {  
			Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
			newTakePhotoFile = new File(path, imagename); 
			Uri u = Uri.fromFile(newTakePhotoFile); 
			intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0); 
			intent.putExtra(MediaStore.EXTRA_OUTPUT, u); 
			activity.startActivityForResult(intent, ParamConst.TAKE_CAMERA_PICTURE); 
			Log.e("888888", "-------------0-------------------");
		} else {  
			Log.e("888888", "------------请插入SD卡-------------------");
			Toast.makeText(activity, "请插入SD卡", Toast.LENGTH_SHORT).show();
		}
	}
	
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getImagename() {
		return imagename;
	}

	public static File getNewTakePhotoFile() {
		return newTakePhotoFile;
	}
	
	
}
