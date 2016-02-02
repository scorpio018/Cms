package com.enorth.cms.adapter.news;

import java.util.List;

import com.enorth.cms.adapter.CommonViewPagerAdapter;

import android.view.View;

public class ListViewViewPagerAdapter extends CommonViewPagerAdapter {

	public ListViewViewPagerAdapter(List<View> viewList) {
		super(viewList);
	}
	
	public ListViewViewPagerAdapter(List<View> viewList, List<String> titles) {
		super(viewList, titles);
	}

}
