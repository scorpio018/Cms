package com.enorth.cms.listener;

import com.enorth.cms.utils.ViewUtil;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class CommonOnClickListener implements OnClickListener {
	
	public CommonOnClickListener() {}
	
	/**
	 * 方便点击事件添加背景颜色点击效果
	 * @param view
	 * @param hasClickColor
	 * @param color
	 */
	public CommonOnClickListener(View view, boolean hasClickColor, int color) {
		view.setOnClickListener(this);
		if (hasClickColor) {
			ViewUtil.initClickBg(view, color);
		}
	}

}
