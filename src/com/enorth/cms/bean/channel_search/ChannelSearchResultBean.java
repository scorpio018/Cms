package com.enorth.cms.bean.channel_search;

import android.view.View;
import android.widget.ImageView;

public class ChannelSearchResultBean {
	
	private Long channelId;
	
	private String channelName;
	
	private View view;
	
	private ImageView checkBtn;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public ImageView getCheckBtn() {
		return checkBtn;
	}

	public void setCheckBtn(ImageView checkBtn) {
		this.checkBtn = checkBtn;
	}
	
	
	
}
