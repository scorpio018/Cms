package com.enorth.cms.listener.newslist.news;

import com.enorth.cms.fragment.NewsListFragment;
import com.enorth.cms.listener.CommonOnScrollListener;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class NewsListListViewOnScrollListener extends CommonOnScrollListener {
	
	private NewsListFragment newsListFragment;
	
	public NewsListListViewOnScrollListener(NewsListFragment newsListFragment) {
		this.newsListFragment = newsListFragment;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			newsListFragment.getNewsListView().setCurFirstShowItemPosition(view.getFirstVisiblePosition());
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		
	}
}
