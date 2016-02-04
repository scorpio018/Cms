package com.enorth.cms.handler.newslist;

import java.util.List;

import com.enorth.cms.adapter.CommonListViewAdapter;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.view.news.ChannelSearchActivity;

import android.os.Handler;
import android.os.Message;
import android.view.View;

public class ChannelSearchHandler extends Handler {
	
	private ChannelSearchActivity activity;
	
	public ChannelSearchHandler(ChannelSearchActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		try {
			switch (msg.what) {
			case ParamConst.MESSAGE_WHAT_SUCCESS:
				final List<View> items = (List<View>) msg.obj;
				CommonListViewAdapter adapter = new CommonListViewAdapter(items);
				activity.channelListView.setAdapter(adapter);
//				ViewUtil.setListViewHeightBasedOnChildren(newsListView);
			break;
			case ParamConst.MESSAGE_WHAT_NO_DATA:
				activity.initDefaultData("没有数据");
			break;
			case ParamConst.MESSAGE_WHAT_ERROR:
				String errorMsg = (String) msg.obj;
				activity.initDefaultData(errorMsg);
			break;
			default:
				activity.initDefaultData("未知错误");
			break;
			}
			activity.resetCurChannelSearchCheckedText();
			// 每次重新选择一个频道后都要进行清空，
			activity.curCheckChannelId = -1L;
			activity.curCheckChannelName = "";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AnimUtil.hideRefreshFrame(activity);
		}
	}
}
