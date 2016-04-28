package com.enorth.cms.listener.popup.newstitle;

import com.enorth.cms.consts.UrlConst;
import com.enorth.cms.listener.popup.PopupWindowContainCheckMarkOnTouchListener;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.news.NewsCommonActivity;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

public abstract class NewsTitlePopupWindowOnTouchListener extends PopupWindowContainCheckMarkOnTouchListener {

	private NewsCommonActivity activity;

	public NewsTitlePopupWindowOnTouchListener(NewsCommonActivity activity, LinearLayout layout) {
		super(activity, layout);
		this.activity = activity;
	}

	@Override
	public void checkItem(String tag, String curCheckedText) {
		activity.getTitleText().setText(curCheckedText);
		activity.setCurNewsTitleName(curCheckedText);
		if (curCheckedText.equals(activity.getResources().getString(R.string.normal_news_title_text))) {
			activity.setCurUrl(UrlConst.NEWS_LIST_POST_URL);
		} else if (curCheckedText.equals(activity.getResources().getString(R.string.my_news_title_text))) {
			activity.setCurUrl(UrlConst.MY_NEWS_LIST_POST_URL);
		}
//		activity.setCurUrl(tag);
//		activity.getViews().get(activity.getCurPosition()).getNewsListView().setRefreshing();
		activity.getListViews().get(activity.getCurPosition()).setRefreshing();
	}
}
