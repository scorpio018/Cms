package com.enorth.cms.handler.newslist;

import com.enorth.cms.view.news.ChannelSearchActivity;

public class MyChannelSearchHandler extends ChannelSearchCommonHandler {

	public MyChannelSearchHandler(ChannelSearchActivity activity) {
		super(activity);
	}

	@Override
	public boolean needResetContent() {
		return false;
	}

}
