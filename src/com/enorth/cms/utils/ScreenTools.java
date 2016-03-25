package com.enorth.cms.utils;

import com.enorth.cms.consts.ParamConst;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.WindowManager;

public class ScreenTools {
	/**
	 * 是否已经将数据初始化
	 */
//	private static boolean isInit = false;
	/**
	 * 手机的高度
	 */
	private static int phoneHeight = -1;
	/**
	 * 手机的宽度
	 */
	private static int phoneWidth = -1;
	/**
	 * 屏幕认定滑动的最大位移
	 */
	private static int touchSlop = -1;
	/**
	 * 密度dpi
	 */
	private static float densityDpi = -1;
	/**
	 * 获取屏幕的宽度
	 * @param activity
	 * @return
	 */
	public static int getPhoneWidth(Activity activity) {
		if (phoneWidth == -1) {
			Display display = activity.getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			phoneWidth = size.x;
			phoneHeight = size.y;
		}
		return phoneWidth;
	}
	
	/**
	 * 获得屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getPhoneWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}
	/**
	 * 获得屏幕高度
	 * @param context
	 * @return
	 */
	public static int getPhoneHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}
	
	/**
	 * 获取屏幕的高度
	 * @param activity
	 * @return
	 */
	public static int getPhoneHeight(Activity activity) {
		if (phoneHeight == -1) {
			Display display = activity.getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			phoneWidth = size.x;
			phoneHeight = size.y;
		}
		return phoneHeight;
	}
	
	public static int getTouchSlop(Context context) {
		if (touchSlop == -1) {
			touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		}
		return touchSlop;
	}
	
	public static float getDesity(Context context) {
		if (densityDpi == -1) {
			// 获取屏幕密度Dpi
			DisplayMetrics dm = context.getResources().getDisplayMetrics();
			densityDpi = dm.density;
		}
		return densityDpi;
	}
	/**
	 * 将px转换成dip
	 * @param pixs
	 * @param activity
	 * @return
	 */
	public static int px2dip(int pixs, Context context) {
		getDesity(context);
		int dip = (int) ((pixs / densityDpi) + 0.5f);
		return dip;
	}
	
	/**
	 * 获取在dimens.xml中定义的值，转换成int类型，并转换成px
	 * @param dimen
	 * @param activity
	 * @return
	 */
	public static int dimenDip2px(int dimen, Context context) {
		int dimenDip = Math.round(context.getResources().getDimension(dimen));
		return dip2px(dimenDip, context);
	}
	/**
	 * 将dip转换成px
	 * @param dip
	 * @param activity
	 * @return
	 */
	public static int dip2px(int dip, Context context) {
		/*if (densityDpi == -1) {
			// 获取屏幕密度Dpi
			DisplayMetrics dm = context.getResources().getDisplayMetrics();
			densityDpi = dm.density;
		}*/
		getDesity(context);
		int pixs = (int) (dip * densityDpi + 0.5f);
		return pixs;
	}
	/**
	 * 初始化基础数据（屏幕的宽度、高度、密度）
	 * @param activity
	 */
	/*private static void initBaseData(Activity activity) {
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
			isInit = true;
		}
	}*/
	
	public static int measuredHeight(int measureSpec) {
		return measureResult(measureSpec);
	}
	
	public static int measuredWidth(int measureSpec) {
		return measureResult(measureSpec);
	}
	
	private static int measureResult(int spec) {
		int specMode = MeasureSpec.getMode(spec);
		int specSize = MeasureSpec.getSize(spec);
		int result = 500;
		if (specMode == MeasureSpec.AT_MOST) {
			result = specSize;
		} else if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		}
		return result;
	}
	
	/**
	 * 判断是否为上下滑动操作
	 * @param downX
	 * @param downY
	 * @param xMove
	 * @param yMove
	 * @return
	 */
	public static boolean isVerticalAction(float downX, float downY, float xMove, float yMove) {
		double tanValue = getAngleYTanValue(downX, downY, xMove, yMove);
		double tan = Math.tan(ParamConst.IS_REFRESH_ACTION_ANGLE * Math.PI / 180);
		Log.i("isRefreshAction", "通过两点组成的直角三角形计算以x作为对边、y作为临边算出的tan值【" + tanValue + "】，再与tan(30°)的值【" + tan + "】进行对比");
		if (tan <= tanValue) {
			Log.i("isRefreshAction", "大于tan值，说明不是下拉刷新操作");
			return false;
		} else {
			Log.i("isRefreshAction", "小于tan值，说明是下拉刷新操作");
			return true;
		}
	}
	
	/**
	 * 判断是否为左右滑动操作
	 * @param downX
	 * @param downY
	 * @param xMove
	 * @param yMove
	 * @return
	 */
	public static boolean isHorizontalScrollAction(float downX, float downY, float xMove, float yMove) {
		double tanValue = getAngleXTanValue(downX, downY, xMove, yMove);
		double tan = Math.tan(ParamConst.IS_REFRESH_ACTION_ANGLE * Math.PI / 180);
		Log.i("isRefreshAction", "通过两点组成的直角三角形计算以x作为对边、y作为临边算出的tan值【" + tanValue + "】，再与tan(30°)的值【" + tan + "】进行对比");
		if (tan <= tanValue) {
			Log.i("isRefreshAction", "大于tan值，说明不是左右滑动操作");
			return false;
		} else {
			Log.i("isRefreshAction", "小于tan值，说明是左右滑动操作");
			return true;
		}
	}
	
	/**
	 * 根据Xs、Ys的每一个点之间的计算，最终算出Y轴和斜边夹角的tan（贪帧特）的平均值
	 * @param downX
	 * @param downY
	 * @param xMove
	 * @param yMove
	 * @return
	 */
	private static double getAngleYTanValue(float downX, float downY, float xMove, float yMove) {
		// 获取两点，然后根据计算出两点的角度的平均值计算是否为向下拖动的动作
		double tmpX = Math.abs(downX - xMove);
		double tmpY = Math.abs(downY - yMove);
		return tmpX / tmpY;
	}
	
	/**
	 * 根据Xs、Ys的每一个点之间的计算，最终算出X轴和斜边夹角的tan（贪帧特）的平均值
	 * @param downX
	 * @param downY
	 * @param xMove
	 * @param yMove
	 * @return
	 */
	private static double getAngleXTanValue(float downX, float downY, float xMove, float yMove) {
		// 获取两点，然后根据计算出两点的角度的平均值计算是否为向左右滑动的动作
		double tmpX = Math.abs(downX - xMove);
		double tmpY = Math.abs(downY - yMove);
		return tmpY / tmpX;
	}
}
