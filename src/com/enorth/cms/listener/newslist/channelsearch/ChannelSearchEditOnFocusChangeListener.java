package com.enorth.cms.listener.newslist.channelsearch;

import com.enorth.cms.listener.CommonOnFocusChangeListener;
import com.enorth.cms.utils.ViewUtil;

import android.content.Context;
import android.view.View;

public class ChannelSearchEditOnFocusChangeListener extends CommonOnFocusChangeListener {
	
	private Context context;
	
	public ChannelSearchEditOnFocusChangeListener(Context context) {
		this.context = context;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		ViewUtil.keyboardDo(context, v, hasFocus);
	}

}
