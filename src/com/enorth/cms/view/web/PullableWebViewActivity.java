package com.enorth.cms.view.web;

import com.enorth.cms.client.CommonWebViewClient;
import com.enorth.cms.listener.MyWebViewListener;
import com.enorth.cms.refreshlayout.PullToRefreshLayout;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
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
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDomStorageEnabled(true);
		webView.setVerticalScrollBarEnabled(false);  
		webView.getParent().requestDisallowInterceptTouchEvent(true);

	}
}
