package com.enorth.cms.client;

import org.xwalk.core.XWalkJavascriptResult;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

import com.enorth.cms.refreshlayout.CrossWalkRefreshLayout;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ValueCallback;

public class CommonXWalkClient extends XWalkUIClient {
	
//	private XWalkView view;
	private CrossWalkRefreshLayout layout;

	public CommonXWalkClient(XWalkView view) {
		super(view);
//		this.view = view;
	}
	
	public CommonXWalkClient(XWalkView view, CrossWalkRefreshLayout layout) {
		super(view);
		this.layout = layout;
	}
	
	@Override
	public boolean shouldOverrideKeyEvent(XWalkView view, KeyEvent event) {
		Log.e("shouldOverrideKeyEvent", "shouldOverrideKeyEvent");
		view.load(view.getUrl(), null);
//		return super.shouldOverrideKeyEvent(view, event);
		return true;
	}
	
	@Override
	public boolean onConsoleMessage(XWalkView view, String message, int lineNumber, String sourceId,
			ConsoleMessageType messageType) {
		Log.e("onConsoleMessage", "onConsoleMessage");
		return super.onConsoleMessage(view, message, lineNumber, sourceId, messageType);
	}
	
	@Override
	public boolean onCreateWindowRequested(XWalkView view, InitiateBy initiator, ValueCallback<XWalkView> callback) {
		Log.e("onCreateWindowRequested", "onCreateWindowRequested");
		return super.onCreateWindowRequested(view, initiator, callback);
	}
	
	@Override
	public void onFullscreenToggled(XWalkView view, boolean enterFullscreen) {
		Log.e("onFullscreenToggled", "onFullscreenToggled");
		super.onFullscreenToggled(view, enterFullscreen);
	}
	
	@Override
	public void onIconAvailable(XWalkView view, String url, Message startDownload) {
		Log.e("onIconAvailable", "onIconAvailable");
		super.onIconAvailable(view, url, startDownload);
	}
	
	@Override
	public void onJavascriptCloseWindow(XWalkView view) {
		Log.e("onJavascriptCloseWindow", "onJavascriptCloseWindow");
		super.onJavascriptCloseWindow(view);
	}
	
	@Override
	public boolean onJavascriptModalDialog(XWalkView view, JavascriptMessageType type, String url, String message,
			String defaultValue, XWalkJavascriptResult result) {
		Log.e("onJavascriptModalDialog", "onJavascriptModalDialog");
		return super.onJavascriptModalDialog(view, type, url, message, defaultValue, result);
	}
	
	@Override
	public void onPageLoadStarted(XWalkView view, String url) {
		Log.e("onPageLoadStarted", "onPageLoadStarted");
		view.load(url, null);
		super.onPageLoadStarted(view, url);
	}
	
	@Override
	public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {
		Log.e("onPageLoadStopped", "onPageLoadStopped");
		super.onPageLoadStopped(view, url, status);
	}
	
	@Override
	public void onReceivedIcon(XWalkView view, String url, Bitmap icon) {
		Log.e("onReceivedIcon", "onReceivedIcon");
		super.onReceivedIcon(view, url, icon);
	}
	
	@Override
	public void onReceivedTitle(XWalkView view, String title) {
		Log.e("onReceivedTitle", "onReceivedTitle");
		super.onReceivedTitle(view, title);
	}
	
	@Override
	public void onRequestFocus(XWalkView view) {
		Log.e("onRequestFocus", "onRequestFocus");
		super.onRequestFocus(view);
	}
	
	@Override
	public void onScaleChanged(XWalkView view, float oldScale, float newScale) {
		Log.e("onScaleChanged", "onScaleChanged");
		super.onScaleChanged(view, oldScale, newScale);
	}
	
	@Override
	public void onUnhandledKeyEvent(XWalkView view, KeyEvent event) {
		Log.e("onUnhandledKeyEvent", "onUnhandledKeyEvent");
		super.onUnhandledKeyEvent(view, event);
	}
	
	@Override
	public void openFileChooser(XWalkView view, ValueCallback<Uri> uploadFile, String acceptType, String capture) {
		Log.e("openFileChooser", "openFileChooser");
		super.openFileChooser(view, uploadFile, acceptType, capture);
	}
}
