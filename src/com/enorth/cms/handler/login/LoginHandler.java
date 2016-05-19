package com.enorth.cms.handler.login;

import com.enorth.cms.consts.UrlCodeErrorConst;
import com.enorth.cms.handler.UrlRequestCommonHandler;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.news.NewsListFragActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.widget.Toast;

public class LoginHandler extends UrlRequestCommonHandler {
	
	private Activity activity;
	
	public LoginHandler(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void success(Message msg) {
		AnimUtil.hideRefreshFrame();
		Toast.makeText(activity, "登录成功！", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.setClass(activity, NewsListFragActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}

	@Override
	public void noData(Message msg) {
		AnimUtil.hideRefreshFrame();
		Object obj = msg.obj;
		ViewUtil.showAlertDialog(activity, obj.toString());
	}

	@Override
	public void error(Message msg) {
		AnimUtil.hideRefreshFrame();
		Object obj = msg.obj;
		ViewUtil.showAlertDialog(activity, obj.toString());
	}

	@Override
	public void resultDefault(Message msg) {
		AnimUtil.hideRefreshFrame();
		Toast.makeText(activity, UrlCodeErrorConst.UNKNOWN_ERROR_HINT, Toast.LENGTH_SHORT).show();
	}

}
