package com.enorth.cms.adapter.news;

import com.enorth.cms.fragment.NewsListFragment;
import com.enorth.cms.view.news.NewsCommonActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NewsListFragmentPagerAdapter extends FragmentPagerAdapter {
	
	private NewsCommonActivity activity;
	
	private NewsListFragment fragment;

	public NewsListFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public NewsListFragmentPagerAdapter(FragmentManager fm, NewsCommonActivity activity) {
		super(fm);
		this.activity = activity;
	}
	
	public NewsListFragmentPagerAdapter(FragmentManager fm, NewsCommonActivity activity, NewsListFragment fragment) {
		super(fm);
		this.activity = activity;
		this.fragment = fragment;
	}

	@Override
	public Fragment getItem(int pageNum) {
		return activity.views.get(pageNum);
	}

	@Override
	public int getCount() {
		return activity.views.size();
	}

}
