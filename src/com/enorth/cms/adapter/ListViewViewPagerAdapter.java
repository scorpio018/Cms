package com.enorth.cms.adapter;

import java.util.List;

import android.view.View;

public class ListViewViewPagerAdapter extends CommonViewPagerAdapter {

	public ListViewViewPagerAdapter(List<View> viewList) {
		super(viewList);
	}
	
	public ListViewViewPagerAdapter(List<View> viewList, List<String> titles) {
		super(viewList, titles);
	}

}
