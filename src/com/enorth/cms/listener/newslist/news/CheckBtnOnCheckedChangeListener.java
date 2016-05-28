package com.enorth.cms.listener.newslist.news;

import com.enorth.cms.listener.CommonOnCheckedChangeListener;
import com.enorth.cms.view.news.NewsCommonActivity;

import android.widget.CompoundButton;

public class CheckBtnOnCheckedChangeListener extends CommonOnCheckedChangeListener {
	
	private NewsCommonActivity activity;
	
	public CheckBtnOnCheckedChangeListener(NewsCommonActivity activity) {
		super(activity);
		this.activity = activity;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		super.onCheckedChanged(buttonView, isChecked);
		activity.checkBtnClickEvent(isChecked);
	}

}
