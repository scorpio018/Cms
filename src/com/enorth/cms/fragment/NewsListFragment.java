package com.enorth.cms.fragment;

import com.enorth.cms.adapter.news.NewsListViewAdapter;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.newslist.news.NewsItemOnTouchListener;
import com.enorth.cms.listener.newslist.news.NewsListListViewOnScrollListener;
import com.enorth.cms.view.R;
import com.enorth.cms.view.news.NewsCommonActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

@SuppressLint("InflateParams")
public class NewsListFragment extends ListFragment {
	
	private int curPageNum;
	
	private NewsCommonActivity activity;
	
	private PullToRefreshListView newsListView;
	
	private int curFirstShowItemPosition = 0;
	
	public NewsListFragment(NewsCommonActivity activity, int curPageNum) {
		this.activity = activity;
		this.curPageNum = curPageNum;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_ptr_list, null);
		newsListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		NewsListViewAdapter adapter = activity.getListViewAdapter().get(curPageNum);
		newsListView.setAdapter(adapter);
		// 将新闻列表ListView存入ListViews集合中
		activity.getListViews().add(newsListView);
		// 既可以下拉刷新，又可以上拉加载
		newsListView.setMode(Mode.BOTH);
		// 刷新/加载事件触发时调用的事件
		newsListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				/*String label = DateUtils.formatDateTime(activity.getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);*/
				if (curPageNum == 2) {
					activity.getNewsListBean().getPage().setOrderBy(activity.getCurSubNewsTitleSortColumn());
				} else {
					activity.getNewsListBean().getPage().setOrderBy(ParamConst.NEWS_SORT_BY_MOD_DATE_KEY);
				}
				Mode currentMode = refreshView.getCurrentMode();
				if (currentMode == Mode.PULL_FROM_START) {
					// 下拉刷新操作
					activity.setCurRefreshState(ParamConst.REFRESHING);
				} else if (currentMode == Mode.PULL_FROM_END) {
					// 上拉加载更多操作
					activity.setCurRefreshState(ParamConst.LOADING);
					activity.getNewsListBean().getPage().setPageNo(activity.getNewsListBean().getPage().getPageNo() + 1);
				} else {
					// 其他
					activity.setCurRefreshState(ParamConst.REFRESHING);
				}
				activity.initNewsListData(newsListView, true, "数据请求错误");
			}
		});
		
		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(activity);
		soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
		soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
		soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
		newsListView.setOnPullEventListener(soundListener);
		
		ListView refreshableView = newsListView.getRefreshableView();
		refreshableView.setOnScrollListener(new NewsListListViewOnScrollListener(this));
		// 不知为何，无效
//		refreshableView.setOnItemClickListener(new NewsListItemOnClickListener(activity));
		
		if (curPageNum == 0) {
			newsListView.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					newsListView.setRefreshing();
				}
			}, 100);
		}
		return view;
	}

	public PullToRefreshListView getNewsListView() {
		return newsListView;
	}

	public void setNewsListView(PullToRefreshListView newsListView) {
		this.newsListView = newsListView;
	}

	public int getCurFirstShowItemPosition() {
		return curFirstShowItemPosition;
	}

	public void setCurFirstShowItemPosition(int curFirstShowItemPosition) {
		this.curFirstShowItemPosition = curFirstShowItemPosition;
	}

}
