package com.enorth.cms.utils;

import com.enorth.cms.bean.ButtonColorBasicBean;
import com.enorth.cms.view.R;

import android.content.Context;
import android.support.v4.content.ContextCompat;

public class ColorUtil {
	/**
	 * 白色
	 * @param context
	 * @return
	 */
	public static int getWhiteColor(Context context) {
		return ContextCompat.getColor(context, R.color.white);
	}
	
	/**
	 * 通用蓝
	 * @param context
	 * @return
	 */
	public static int getCommonBlueColor(Context context) {
		return ContextCompat.getColor(context, R.color.common_blue);
	}
	
	/**
	 * 亮蓝
	 * @param context
	 * @return
	 */
	public static int getLightBlue(Context context) {
		return ContextCompat.getColor(context, R.color.light_blue);
	}
	
	/**
	 * 灰色
	 * @param context
	 * @return
	 */
	public static int getGray(Context context) {
		return ContextCompat.getColor(context, R.color.gray);
	}
	
	/**
	 * 亮灰
	 * @param context
	 * @return
	 */
	public static int getGrayLighter(Context context) {
		return ContextCompat.getColor(context, R.color.gray_lighter);
	}
	
	/**
	 * 默认的灰色背景
	 * @param context
	 * @return
	 */
	public static int getBgGrayDefault(Context context) {
		return ContextCompat.getColor(context, R.color.bg_gray_default);
	}
	
	/**
	 * 深灰
	 * @param context
	 * @return
	 */
	public static int getDarkGray(Context context) {
		return ContextCompat.getColor(context, R.color.dark_gray);
	}
	
	/**
	 * 控件摁下时的灰色背景色
	 * @param context
	 * @return
	 */
	public static int getBgGrayPress(Context context) {
		return ContextCompat.getColor(context, R.color.bg_gray_press);
	}
	
	/**
	 * 黑色
	 * @param context
	 * @return
	 */
	public static int getBlack(Context context) {
		return ContextCompat.getColor(context, R.color.black);
	}
	
	/**
	 * 底部菜单的默认颜色
	 * @param context
	 * @return
	 */
	public static int getBottomColorBasic(Context context) {
		return ContextCompat.getColor(context, R.color.bottom_color_basic);
	}
	
	/**
	 * 底部菜单点击时的颜色
	 * @param context
	 * @return
	 */
	public static int getBottomColorPressed(Context context) {
		return ContextCompat.getColor(context, R.color.bottom_color_pressed);
	}
	
	/**
	 * 弹出框的默认背景颜色
	 * @param context
	 * @return
	 */
	public static int getChannelPopupColor(Context context) {
		return ContextCompat.getColor(context, R.color.channel_popup_color);
	}
	
	/**
	 * 底部菜单的绿色文字
	 * @param context
	 * @return
	 */
	public static int getBottomTextColorGreen(Context context) {
		return ContextCompat.getColor(context, R.color.bottom_text_color_green);
	}
	
	/**
	 * 菜单背景颜色
	 * @param context
	 * @return
	 */
	public static int getMenuBg(Context context) {
		return ContextCompat.getColor(context, R.color.menu_bg);
	}
	
	/**
	 * 橘黄色
	 * @param context
	 * @return
	 */
	public static int getOrangeColor(Context context) {
		return ContextCompat.getColor(context, R.color.orange);
	}
	
	/**
	 * 副标题的默认颜色
	 * @param context
	 * @return
	 */
	public static int getNewsSubTitleColorBasic(Context context) {
		return ContextCompat.getColor(context, R.color.news_sub_title_color_basic);
	}
	
	/**
	 * 获取带圆角的橘黄色背景、白色文字的bean
	 * @param context
	 * @return
	 */
	public static ButtonColorBasicBean getOrangeButtonColorBasicBean(Context context) {
		ButtonColorBasicBean bean = new ButtonColorBasicBean(context);
		bean.setmBgNormalColor(getOrangeColor(context));
		bean.setmTextNormalColor(getWhiteColor(context));
		return bean;
	}
}
