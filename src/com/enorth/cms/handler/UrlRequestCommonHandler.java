package com.enorth.cms.handler;

import com.enorth.cms.consts.ParamConst;

import android.os.Handler;
import android.os.Message;

public abstract class UrlRequestCommonHandler extends Handler {
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract void success(Message msg) throws Exception;
	
	public abstract void noData(Message msg) throws Exception;
	
	public abstract void error(Message msg) throws Exception;
	
	public abstract void resultDefault(Message msg) throws Exception;
}
