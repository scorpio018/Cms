package com.enorth.cms.listener.popup.bottombtn;

import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.view.news.NewsCommonActivity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
/**
 * 底部菜单点击更多时弹出的按钮的点击事件
 * @author Administrator
 *
 */
public class BottomBtnMorePopupWindowOnClickListener extends CommonOnClickListener {

	private NewsCommonActivity activity;

	public BottomBtnMorePopupWindowOnClickListener(NewsCommonActivity activity, LinearLayout layout) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(activity, "点击了", Toast.LENGTH_SHORT).show();
		activity.getBottomMenuBtnPopupWindow().dismiss();
		activity.setBottomMenuBtnPopupWindow(null);
	}
}
