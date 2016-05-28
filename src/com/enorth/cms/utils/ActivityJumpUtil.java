package com.enorth.cms.utils;

import java.util.ArrayList;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.view.material.MaterialUploadPicPreviewActivity;
import com.enorth.cms.view.upload.UploadPicPreviewActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
/**
 * 用于一些可以共用的activity跳转的方法的工具类
 * @author yangyang
 *
 */
public class ActivityJumpUtil {
	/**
	 * 跳转到图片预览activity
	 * @param imgDatas
	 * @param checkedImgDatas
	 * @param checkPosition
	 * @param activity
	 */
	public static void sendImgDatasToActivity(ArrayList<String> imgDatas, ArrayList<String> checkedImgDatas, int checkPosition, Activity activity, Class toActivityClass, Intent intent) {
		
		Bundle bundle = new Bundle();
		bundle.putStringArrayList(ParamConst.IMG_DATAS, imgDatas);
		bundle.putStringArrayList(ParamConst.CHECKED_IMG_DATAS, checkedImgDatas);
		bundle.putInt(ParamConst.CHECK_POSITION, checkPosition);
		intent.setClass(activity, toActivityClass);
		intent.putExtras(bundle);
		activity.startActivityForResult(intent, ParamConst.GALLERY_ACTIVITY_TO_UPLOAD_PIC_PREVIEW_ACTIVITY_REQUEST_CODE);
	}
	
	/**
	 * 从任意activity进入单纯的显示图片的MaterialUploadPicPreviewActivity页面并将图片集合进行传入
	 * @param imgDatas
	 * @param activity
	 */
	public static void sendImgDatasToSimpleShowImageActivity(ArrayList<String> imgDatas, Activity activity) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putStringArrayList(ParamConst.IMG_DATAS, imgDatas);
		intent.setClass(activity, MaterialUploadPicPreviewActivity.class);
		intent.putExtras(bundle);
		activity.startActivityForResult(intent, ParamConst.ACTIVITY_TO_MATERIAL_UPLOAD_PIC_PREVIEW_ACTIVITY_REQUEST_CODE);
	}
	
	/**
	 * 将图片集合传入到指定的activity中
	 * 根据ParamConst.ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY的值判断：
	 * ParamConst.ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY_NO：素材上传中照相之后进入最终上传页面，然后点击"+"进入相册
	 * ParamConst.ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY_YES：在相册模块中进入最终上传页面，然后点击"+"返回相册
	 * @param imgDatas
	 * @param activity
	 * @param toActivityClass
	 * @param addPicIsJumpToPrevActivity
	 */
	public static void sendTakePhotoToActivity(ArrayList<String> imgDatas, Activity activity, Class toActivityClass, int addPicIsJumpToPrevActivity, Intent intent) {
		Bundle bundle = new Bundle();
		bundle.putStringArrayList(ParamConst.IMG_DATAS, imgDatas);
		bundle.putInt(ParamConst.ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY, addPicIsJumpToPrevActivity);
		intent.setClass(activity, toActivityClass);
		intent.putExtras(bundle);
		activity.startActivityForResult(intent, ParamConst.GALLERY_ACTIVITY_TO_UPLOAD_PIC_PREVIEW_ACTIVITY_REQUEST_CODE);
	}
	
	/**
	 * 将所需参数返回给上一个activity
	 * @param activity
	 * @param bundle
	 */
	public static void takeParamsBackToPrevActivity(Activity activity, Bundle bundle, int resultCode) {
		Intent intent = new Intent();
		intent.putExtras(bundle);
		activity.setResult(resultCode, intent);
		activity.finish();
	}
}
