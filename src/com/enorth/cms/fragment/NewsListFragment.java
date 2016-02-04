package com.enorth.cms.fragment;

import com.enorth.cms.view.news.NewsCommonActivity;
import com.enorth.cms.widget.listview.newslist.NewsListListView;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewsListFragment extends ListFragment {
	
	private int curPageNum;
	
	private NewsCommonActivity activity;
	
	public static NewsListListView newsListView;
	
	public NewsListFragment(NewsCommonActivity activity, int curPageNum) {
		this.activity = activity;
		this.curPageNum = curPageNum;
	}
	
	/*public static Fragment create(int pageNum, NewsCommonActivity activity) {
		NewsListFragment fragment = new NewsListFragment(activity);
		Bundle bundle = new Bundle();
		bundle.putInt(ParamConst.NEWS_FRAG_PAGE, pageNum);
		fragment.setArguments(bundle);
		return fragment;
	}*/
	
	/*@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}*/
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		newsListView = new NewsListListView(activity);
		try {
			activity.initNewsListData(newsListView, false, null);
			if (curPageNum == 0) {
				activity.initNewsListData(newsListView, true, "数据请求错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsListView;
	}
	
}
