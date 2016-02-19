package com.enorth.cms.adapter.materialupload;

import java.util.List;

import com.enorth.cms.fragment.NewsListFragment;
import com.enorth.cms.fragment.materialupload.MaterialUploadFileTypeItemFrag;
import com.enorth.cms.view.news.NewsCommonActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

public class MaterialUploadFileTypeItemFragmentPagerAdapter extends FragmentPagerAdapter {
	
	private List<MaterialUploadFileTypeItemFrag> fragments;

	public MaterialUploadFileTypeItemFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public MaterialUploadFileTypeItemFragmentPagerAdapter(FragmentManager fm, List<MaterialUploadFileTypeItemFrag> fragments) {
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