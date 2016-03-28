package com.enorth.cms.listener.popup.newssubtitle;

import com.enorth.cms.listener.popup.PopupWindowContainCheckMarkOnTouchListener;
import com.enorth.cms.view.news.NewsCommonActivity;

import android.widget.LinearLayout;

public abstract class NewsSubTitlePopupWindowOnTouchListener extends PopupWindowContainCheckMarkOnTouchListener {

	private NewsCommonActivity activity;
	

	public NewsSubTitlePopupWindowOnTouchListener(NewsCommonActivity activity, LinearLayout layout) {
		super(activity, layout);
		this.activity = activity;
	}

	@Override
	public void checkItem(String tag, String curCheckedText) {
		activity.setCurSubNewsTitleSortColumn(tag);
		/*activity.initNewsListData(activity.getViews().get(activity.getCurPosition()).getNewsListView(), true,
				"数据请求错误");*/
		activity.getViews().get(activity.getCurPosition()).getNewsListView().setRefreshing();
		
	}
}
