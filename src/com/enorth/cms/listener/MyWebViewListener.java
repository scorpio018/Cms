package com.enorth.cms.listener;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.refreshlayout.PullToRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;

public class MyWebViewListener implements OnRefreshListener {
	
	private WebView webView;
	
	public MyWebViewListener(WebView webView) {
		this.webView = webView;
	}

	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
		webView.reload();
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		// 加载操作
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 千万别忘了告诉控件加载完毕了哦！
				pullToRefreshLayout.loadmoreFinish(ParamConst.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

}
