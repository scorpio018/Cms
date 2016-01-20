package com.enorth.cms.listener;

import org.xwalk.core.XWalkView;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.refreshlayout.CommonRefreshLayout;

import android.os.Handler;
import android.os.Message;

public class MyCrossWalkListener implements CommonOnRefreshListener {
	
	private XWalkView xWalkView;
	
	public MyCrossWalkListener(XWalkView xWalkView) {
		this.xWalkView = xWalkView;
	}

	@Override
	public void onRefresh(final CommonRefreshLayout layout) {
		xWalkView.reload(XWalkView.RELOAD_NORMAL);
	}

	@Override
	public void onLoadMore(final CommonRefreshLayout layout) {
		// 加载操作
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 千万别忘了告诉控件加载完毕了哦！
				layout.loadmoreFinish(ParamConst.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

}
