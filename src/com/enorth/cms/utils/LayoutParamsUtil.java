package com.enorth.cms.utils;

import com.enorth.cms.activity.R;

import android.app.ActionBar.LayoutParams;
import android.content.res.Resources;

public class LayoutParamsUtil {
	/**
	 * 初始化新闻列表/直播列表中的待编辑、待签发、已签发的按钮的基本样式
	 * @param resources
	 * @return
	 */
	public static LayoutParams initEnableSimpleChangeButtonLayout(Resources resources) {
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		params.leftMargin = (int) resources.getDimension(R.dimen.news_title_change_viewpager_btn_margin_left);
//		params.rightMargin = (int) resources.getDimension(R.dimen.news_title_change_viewpager_btn_margin_right);
//		params.topMargin = (int) resources.getDimension(R.dimen.news_title_change_viewpager_btn_margin_top);
//		params.bottomMargin = (int) resources.getDimension(R.dimen.news_title_change_viewpager_btn_margin_bottom);
		return params;
	}
}
