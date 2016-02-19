package com.enorth.cms.adapter.news;

import java.util.List;

import com.enorth.cms.fragment.NewsListFragment;
import com.enorth.cms.view.news.NewsCommonActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

public class NewsListFragmentPagerAdapter extends FragmentPagerAdapter {
	
	private List<NewsListFragment> fragments;

	public NewsListFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public NewsListFragmentPagerAdapter(FragmentManager fm, List<NewsListFragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int pageNum) {
		return fragments.get(pageNum);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
