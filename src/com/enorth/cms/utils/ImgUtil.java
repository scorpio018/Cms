package com.enorth.cms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

public class ImgUtil {
	/**
	 * 建立HTTP请求，并获取Bitmap对象。
	 * 
	 * @param imageUrl
	 *            图片的URL地址
	 * @return 解析后的Bitmap对象
	 */
	public static Bitmap downloadBitmap(String imageUrl) {
		Bitmap bitmap = null;
		HttpURLConnection con = null;
		try {
			URL url = new URL(imageUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5 * 1000);
			con.setReadTimeout(10 * 1000);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);//不缓存  
			bitmap = BitmapFactory.decodeStream(con.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		return bitmap;
	}
	
	public static long getMaxMemory() {
		long maxMemory = Runtime.getRuntime().maxMemory() / 1024;
		return maxMemory;
	}
	
	/**
	 * 将传入的图片保存到指定的文件夹中
	 * @param loadPath 传入的图片所在的文件夹
	 * @param photoName 图片名
	 * @param savePath 保存的图片所在的文件夹
	 * @throws IOException
	 */
	public static void savePhotoToSDCard(String loadPath, String photoName, String savePath, Context context) throws IOException {
		// 判断SD卡是否有读写权限
		if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
			File dir = new File(loadPath);
			if (!dir.exists()) {
				throw new FileNotFoundException("没有找到要读取的图片");
			}
			dir = new File(savePath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			
			File saveFile = new File(savePath, photoName);
			while (saveFile.exists()) {
				photoName = TimeUtil.getDateYMHHMSsNoConnector();
				saveFile = new File(savePath, photoName);
			}
			saveFile.createNewFile();
			File loadFile = new File(loadPath, photoName);
			FileInputStream fis = new FileInputStream(loadFile);
			FileUtils.copyInputStreamToFile(fis, saveFile);
			refreshGallery(saveFile, context);
		}
	}
	
	public static void savePhotoToSDCard(String path, String photoName,
            Bitmap photoBitmap) {
		// 判断SD卡是否有读写权限
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File photoFile = new File(path, photoName); //在指定路径下创建文件
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                            fileOutputStream)) {
                        fileOutputStream.flush();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	public static void refreshGallery(File file, Context context) {
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(file);
		intent.setData(uri);
		context.sendBroadcast(intent);
		
	}
	
}
