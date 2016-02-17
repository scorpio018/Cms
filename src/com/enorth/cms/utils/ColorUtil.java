package com.enorth.cms.utils;

import com.enorth.cms.view.R;

import android.app.Activity;
import android.support.v4.content.ContextCompat;

public class ColorUtil {
	/**
	 * 白色
	 * @param activity
	 * @return
	 */
	public static int getWhiteColor(Activity activity) {
		return ContextCompat.getColor(activity, R.color.white);
	}
	
	/**
	 * 通用蓝
	 * @param activity
	 * @return
	 */
	public static int getCommonBlueColor(Activity activity) {
		return ContextCompat.getColor(activity, R.color.common_blue);
	}
	
	/**
	 * 灰色
	 * @param activity
	 * @return
	 */
	public static int getGray(Activity activity) {
		return ContextCompat.getColor(activity, R.color.gray);
	}
	
	/**
	 * 亮灰
	 * @param activity
	 * @return
	 */
	public static int getGrayLighter(Activity activity) {
		return ContextCompat.getColor(activity, R.color.gray_lighter);
	}
	
	/**
	 * 默认的灰色背景
	 * @param activity
	 * @return
	 */
	public static int getBgGrayDefault(Activity activity) {
		return ContextCompat.getColor(activity, R.color.bg_gray_default);
	}
	
	/**
	 * 深灰
	 * @param activity
	 * @return
	 */
	public static int getDarkGray(Activity activity) {
		return ContextCompat.getColor(activity, R.color.dark_gray);
	}
	
	/**
	 * 控件摁下时的灰色背景色
	 * @param activity
	 * @return
	 */
	public static int getBgGrayPress(Activity activity) {
		return ContextCompat.getColor(activity, R.color.bg_gray_press);
	}
	
	/**
	 * 黑色
	 * @param activity
	 * @return
	 */
	public static int getBlack(Activity activity) {
		return ContextCompat.getColor(activity, R.color.black);
	}
	
	/**
	 * 底部菜单的默认颜色
	 * @param activity
	 * @return
	 */
	public static int getBottomColorBasic(Activity activity) {
		return ContextCompat.getColor(activity, R.color.bottom_color_basic);
	}
	
	/**
	 * 底部菜单点击时的颜色
	 * @param activity
	 * @return
	 */
	public static int getBottomColorPressed(Activity activity) {
		return ContextCompat.getColor(activity, R.color.bottom_color_pressed);
	}
}
