package com.enorth.cms.listener.newslist.newstypebtn;

import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.news.NewsCommonActivity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NewsTypeBtnOnClickListener extends CommonOnClickListener {
	
	private Activity activity;
	
	private int position;
	/**
	 * 按钮组的layout
	 */
	private LinearLayout layout;
	
	public NewsTypeBtnOnClickListener(Activity activity, LinearLayout layout, int position) {
		this.activity = activity;
		this.position = position;
		this.layout = layout;
	}

	@Override
	public void onClick(View v) {
		// 根据当前选中的标头按钮的位置改变需要改变样式的按钮，并清除需要清除的ListView中的数据（只要在当前ListView前后超过一个间隔，则清空）
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				// 如果是NewsCommonActivity，则会有后续的ViewPager操作
				if (activity instanceof NewsCommonActivity) {
					NewsCommonActivity newsCommonActivity = (NewsCommonActivity) activity;
					newsCommonActivity.getNewsListViewPager().setCurrentItem(position, false);
					try {
						newsCommonActivity.changeNewsTypeBtnStyleByFocusedState(position);
					} catch (Exception e) {
						e.printStackTrace();
					}
					newsCommonActivity.setCurPosition(position);
					newsCommonActivity.changeAddNewsBtnVisible();
					newsCommonActivity.initNewsOperateBtn();
				} else {
					try {
						ViewUtil.changeBtnGroupStyleByFocusedState(activity, layout, position, ColorUtil.getCommonBlueColor(activity), ColorUtil.getWhiteColor(activity));
					} catch (Exception e) {
						Toast.makeText(activity, "按钮组状态切换时出现错误:【" + e.getMessage() + "】", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
				}
				
			}
		}.sendEmptyMessage(0);
	}

}
