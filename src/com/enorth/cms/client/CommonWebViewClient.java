package com.enorth.cms.client;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.refreshlayout.PullToRefreshLayout;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CommonWebViewClient extends WebViewClient {
	
	private PullToRefreshLayout layout;
	
	private boolean isSuccess = true;
	
	public CommonWebViewClient(PullToRefreshLayout layout) {
//		layout.changeState(ParamConst.REFRESHING);
//		layout.requestLayout();
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
		isSuccess = false;
		layout.refreshFinish(ParamConst.FAIL);
		view.loadUrl(null);
	}
	
	@Override
	public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
		handler.cancel();
	}
	
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		Log.e("onPageStarted", "开始访问URL");
		super.onPageStarted(view, url, favicon);
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		if (isSuccess) {
			Log.e("onPageFinished", "刷新结束/成功");
			layout.refreshFinish(ParamConst.SUCCEED);
		} else {
			isSuccess = true;
		}
		super.onPageFinished(view, url);
	}
}
