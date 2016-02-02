package com.enorth.cms.utils;

import android.widget.AbsListView.LayoutParams;
import android.widget.LinearLayout;

public class LayoutParamsUtil {
	/**
	 * 初始化WRAP_CONTENT的宽和高
	 * @param resources
	 * @return
	 */
	public static LayoutParams initWrapLayout() {
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		return params;
	}
	
	/**
	 * 初始化MATCH_CONTENT的宽和高
	 * @return
	 */
	public static LayoutParams initMatchLayout() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		return params;
	}
	
	/**
	 * 初始化按照百分比分配的params
	 * @param weight
	 * @return
	 */
	public static LinearLayout.LayoutParams initPercentWeight(float weight) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, weight);
		return params;
	}
	
	/**
	 * 初始化自定义的宽和高
	 * @param width
	 * @param height
	 * @return
	 */
	public static LayoutParams initCustomLayout(int width, int height) {
		LayoutParams params = new LayoutParams(width, height);
		return params;
	}
}
