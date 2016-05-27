package com.enorth.cms.handler;

import com.enorth.cms.consts.ParamConst;

import android.os.Handler;
import android.os.Message;
import android.widget.BaseAdapter;

public abstract class UrlRequestCommonHandler extends Handler {
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		switch (msg.what) {
		case ParamConst.MESSAGE_WHAT_SUCCESS:
			success(msg);
			// ViewUtil.setListViewHeightBasedOnChildren(newsListView);
			break;
		case ParamConst.MESSAGE_WHAT_NO_DATA:
			noData(msg);
			break;
		case ParamConst.MESSAGE_WHAT_ERROR:
			error(msg);
			break;
		default:
			resultDefault(msg);
			break;
		}
	}
	
	public abstract void success(Message msg);
	
	public abstract void noData(Message msg);
	
	public abstract void error(Message msg);
	
	public abstract void resultDefault(Message msg);
}
