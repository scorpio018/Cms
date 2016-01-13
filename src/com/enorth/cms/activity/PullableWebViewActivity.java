package com.enorth.cms.activity;

import com.enorth.cms.listener.MyWebViewListener;
import com.enorth.cms.refreshlayout.PullToRefreshLayout;
import com.enorth.cms.webviewclient.CommonWebViewClient;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
/**
 * 
 * @author yangyang
 *
 */
public class PullableWebViewActivity extends Activity
{
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		webView = (WebView) findViewById(R.id.content_view);
		PullToRefreshLayout layout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
		layout.setOnRefreshListener(new MyWebViewListener(webView));
		webView.setWebViewClient(new CommonWebViewClient(layout));
		webView.loadUrl("http://blog.csdn.net/zhongkejingwang");
		webView.setWebChromeClient(new WebChromeClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setVerticalScrollBarEnabled(false);  
		webView.getParent().requestDisallowInterceptTouchEvent(true);

	}
}
