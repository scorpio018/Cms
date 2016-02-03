package com.enorth.cms.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public abstract class CommonViewPagerAdapter extends PagerAdapter {

	private List<View> viewList;

	private List<String> titles;

	private int pagerNum = 0;

	public CommonViewPagerAdapter(List<View> viewList) {
		this.viewList = viewList;
	}

	public CommonViewPagerAdapter(List<View> viewList, List<String> titles) {
		this.viewList = viewList;
		this.titles = titles;
	}

	public int getPagerNum() {
		return pagerNum;
	}

	@Override
	public int getCount() {
		return viewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object position) {
		return arg0 == position;
	}

	@Override
	public void destroyItem(final View container, int position, Object object) {
		final View view = viewList.get(position);
		/*Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				((ViewPager) container).removeView(view);
			}
		};*/
		if (view != null) {
			((ViewPager) container).removeView(view);
		}
	}

	@Override
	public Object instantiateItem(final View container, final int position) {
		View view = null;
		try {
			view = viewList.get(position);
			if (view.getParent() == null) {
				((ViewPager) container).addView(view, 0);
			} else {
				/**
				 * 很难理解新添加进来的view会自动绑定一个父类，由于一个儿子view不能与两个父类相关， 所以得解绑不这样做否则会产生
				 * viewpager java.lang.IllegalStateException: The specified
				 * child already has a parent. You must call removeView() on the
				 * child's parent first.
				 */
				((ViewGroup) view.getParent()).removeView(view);
				((ViewPager) container).addView(view, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pagerNum = position;
		}
		/*new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (view.getParent() == null) {
					((ViewPager) container).addView(view, 0);
				} else {
					*//**
					 * 很难理解新添加进来的view会自动绑定一个父类，由于一个儿子view不能与两个父类相关， 所以得解绑不这样做否则会产生
					 * viewpager java.lang.IllegalStateException: The specified
					 * child already has a parent. You must call removeView() on the
					 * child's parent first.
					 *//*
					((ViewGroup) view.getParent()).removeView(view);
					((ViewPager) container).addView(view, 0);
				}
			}
		}.sendEmptyMessage(0);*/
		return view;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position);
	}

	public void removeAll() {
		if (viewList != null) {
			viewList.clear();
		}
		if (titles != null) {
			titles.clear();
		}
		pagerNum = 0;
		notifyDataSetChanged();
	}
}
