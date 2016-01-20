package com.enorth.cms.adapter;

import java.util.List;

import android.view.View;

public class WelcomeViewPagerAdapter extends CommonViewPagerAdapter {

	public WelcomeViewPagerAdapter(List<View> viewList) {
		super(viewList);
	}
	
	public WelcomeViewPagerAdapter(List<View> viewList, List<String> titles) {
		super(viewList, titles);
	}
	
}
