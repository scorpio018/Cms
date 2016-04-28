package com.enorth.cms.utils;

import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LayoutParamsUtil {
	/**
	 * 初始化WRAP_CONTENT的宽和高
	 * @param resources
	 * @return
	 */
	public static AbsListView.LayoutParams initAbsListViewWrapLayout() {
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
		return params;
	}
	
	/**
	 * 初始化相对布局（RelativeLayout）的宽和高
	 * @return
	 */
	public static RelativeLayout.LayoutParams initRelaWrapLayout() {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		return params;
	}
	
	/**
	 * 初始化线性布局(LinearLayout)的宽和高
	 * @return
	 */
	public static LinearLayout.LayoutParams initLineWrapLayout() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		return params;
	}
	
	/**
	 * 初始化MATCH_CONTENT的宽和高
	 * @return
	 */
	public static AbsListView.LayoutParams initAbsListViewMatchLayout() {
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
		return params;
	}
	
	public static RelativeLayout.LayoutParams initRelaMatchLayout() {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		return params;
	}
	
	public static LinearLayout.LayoutParams initLineFillLayout() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		return params;
	}
	
	/**
	 * 初始化按照百分比分配的params
	 * @param weight
	 * @return
	 */
	public static LinearLayout.LayoutParams initLinePercentWeight(float weight) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, AbsListView.LayoutParams.MATCH_PARENT, weight);
		return params;
	}
	
	/**
	 * 初始化自定义的高度和按照百分比分配的params
	 * @param height
	 * @param weight
	 * @return
	 */
	public static LinearLayout.LayoutParams initLineHeightAndPercentWeight(int height, float weight) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, height, weight);
		return params;
	}
	
	/**
	 * 初始化自定义的宽和高
	 * @param width
	 * @param height
	 * @return
	 */
	public static AbsListView.LayoutParams initAbsListViewCustomLayout(int width, int height) {
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(width, height);
		return params;
	}
}
