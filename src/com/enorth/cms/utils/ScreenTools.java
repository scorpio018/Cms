package com.enorth.cms.utils;

import android.app.Activity;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewConfiguration;

public class ScreenTools {
	/**
	 * 是否已经将数据初始化
	 */
	private static boolean isInit = false;
	/**
	 * 手机的高度
	 */
	private static int phoneHeight;
	/**
	 * 手机的宽度
	 */
	private static int phoneWidth;
	/**
	 * 屏幕认定滑动的最大位移
	 */
	private static int touchSlop;
	/**
	 * 密度dpi
	 */
	private static float densityDpi = 0;
	/**
	 * 获取屏幕的宽度
	 * @param activity
	 * @return
	 */
	public static int getPhoneWidth(Activity activity) {
		initBaseData(activity);
		return phoneWidth;
	}
	/**
	 * 获取屏幕的高度
	 * @param activity
	 * @return
	 */
	public static int getPhoneHeight(Activity activity) {
		initBaseData(activity);
		return phoneHeight;
	}
	
	public static int getTouchSlop(Activity activity) {
		initBaseData(activity);
		return touchSlop;
	}
	/**
	 * 将px转换成dip
	 * @param pixs
	 * @param activity
	 * @return
	 */
	public static int px2dip(int pixs, Activity activity) {
		initBaseData(activity);
		int dip = (int) ((pixs / densityDpi) + 0.5f);
		return dip;
	}
	/**
	 * 将dip转换成px
	 * @param dip
	 * @param activity
	 * @return
	 */
	public static int dip2px(int dip, Activity activity) {
		initBaseData(activity);
		int pixs = (int) (dip * densityDpi + 0.5f);
		return pixs;
	}
	/**
	 * 初始化基础数据（屏幕的宽度、高度、密度）
	 * @param activity
	 */
	private static void initBaseData(Activity activity) {
		if (!isInit) {
			// 获取屏幕的分辨率
			Display display = activity.getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			phoneWidth = size.x;
			phoneHeight = size.y;
			// 获取屏幕密度Dpi
			DisplayMetrics dm = activity.getResources().getDisplayMetrics();
			densityDpi = dm.density;
			touchSlop = ViewConfiguration.get(activity).getScaledTouchSlop();
			isInit = true;
		}
	}
}
