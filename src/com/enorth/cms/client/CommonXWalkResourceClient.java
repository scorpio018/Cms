package com.enorth.cms.client;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.refreshlayout.CrossWalkRefreshLayout;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebView;

public class CommonXWalkResourceClient extends XWalkResourceClient {
	
	private CrossWalkRefreshLayout layout;
	
	private boolean isSuccess = true;

	public CommonXWalkResourceClient(XWalkView view) {
		super(view);
	}
	
	public CommonXWalkResourceClient(XWalkView view, CrossWalkRefreshLayout layout) {
		super(view);
		this.layout = layout;
	}
	
	/*@Override
	public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
		view.load(url, null);
		return true;
	}*/
	
	@Override
	public void onReceivedLoadError(XWalkView view, int errorCode, String description, String failingUrl) {
		Log.e("onReceivedError", "刷新失败，errorCode【" + errorCode + "】，description【" + description + "】");
		isSuccess = false;
		if (layout != null) {
			layout.commonLayout.refreshFinish(ParamConst.FAIL);
		}
		super.onReceivedLoadError(view, errorCode, description, failingUrl);
	}
	
	@Override
	public void onReceivedSslError(XWalkView view, ValueCallback<Boolean> callback, SslError error) {
		Log.e("onReceivedSslError", "error【" + error.toString() + "】");
		super.onReceivedSslError(view, callback, error);
	}
	
	@Override
	public void onLoadStarted(XWalkView view, String url) {
		Log.e("onPageStarted", "开始访问URL【" + url + "】");
		super.onLoadStarted(view, url);
	}
	
	@Override
	public void onLoadFinished(XWalkView view, String url) {
		if (isSuccess) {
			Log.e("onPageFinished", "刷新结束/成功");
			if (layout != null) {
				layout.commonLayout.refreshFinish(ParamConst.SUCCEED);
			}
		} else {
			isSuccess = true;
		}
		super.onLoadFinished(view, url);
	}
}
