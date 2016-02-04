package com.enorth.cms.listener.newslist.subtitle;

import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.view.news.ChannelSearchActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class ChooseChannelOnClickListener extends CommonOnClickListener {
	
	private Activity activity;
	
	private Intent intent = new Intent(); 
	
	public ChooseChannelOnClickListener(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		intent.setClass(activity, ChannelSearchActivity.class);
		activity.startActivity(intent);
	}

}
