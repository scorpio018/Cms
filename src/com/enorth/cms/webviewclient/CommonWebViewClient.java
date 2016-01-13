package com.enorth.cms.webviewclient;

import com.enorth.cms.refreshlayout.PullToRefreshLayout;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CommonWebViewClient extends WebViewClient {
	
	private PullToRefreshLayout layout;
	
	public CommonWebViewClient(PullToRefreshLayout layout) {
		this.layout = layout;
	}
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return true;
	}
	
	@Override
	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		Log.e("onReceivedError", "刷新失败，errorCode【" + errorCode + "】，description【" + description + "】");
		layout.refreshFinish(PullToRefreshLayout.FAIL);
	}
	
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		Log.e("onPageStarted", "开始访问URL");
		super.onPageStarted(view, url, favicon);
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		Log.e("onPageFinished", "刷新结束/成功");
		layout.refreshFinish(PullToRefreshLayout.SUCCEED);
		super.onPageFinished(view, url);
	}
}
