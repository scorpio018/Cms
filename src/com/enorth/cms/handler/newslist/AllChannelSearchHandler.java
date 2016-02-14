package com.enorth.cms.handler.newslist;

import com.enorth.cms.view.news.ChannelSearchActivity;

public class AllChannelSearchHandler extends ChannelSearchCommonHandler {

	public AllChannelSearchHandler(ChannelSearchActivity activity) {
		super(activity);
	}

	@Override
	public boolean needResetContent() {
		return true;
	}

}
