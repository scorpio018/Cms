package com.enorth.cms.listener.newslist.newstypebtn;

import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.view.news.NewsCommonActivity;

import android.os.Handler;
import android.os.Message;
import android.view.View;

public class NewsTypeBtnOnClickListener extends CommonOnClickListener {
	
	private NewsCommonActivity activity;
	
	private int position;
	
	public NewsTypeBtnOnClickListener(NewsCommonActivity activity, int position) {
		this.activity = activity;
		this.position = position;
	}

	@Override
	public void onClick(View v) {
		// 根据当前选中的标头按钮的位置改变需要改变样式的按钮，并清除需要清除的ListView中的数据（只要在当前ListView前后超过一个间隔，则清空）
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				activity.newsListViewPager.setCurrentItem(position, false);
				try {
					activity.changeNewsTypeBtnStyleByFocusedState(position);
				} catch (Exception e) {
					e.printStackTrace();
				}
				activity.curPosition = position;
				activity.changeAddNewsBtnVisible();
				activity.initNewsOperateBtn();
			}
		}.sendEmptyMessage(0);
	}

}
