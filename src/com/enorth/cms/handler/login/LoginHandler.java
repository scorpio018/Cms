package com.enorth.cms.handler.login;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.consts.UrlCodeErrorConst;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.view.news.NewsListFragActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class LoginHandler extends Handler {
	
	private Activity activity;
	
	public LoginHandler(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		AnimUtil.hideRefreshFrame();
		Toast.makeText(activity, "登录成功！", Toast.LENGTH_SHORT).show();
		int what = msg.what;
		switch (what) {
		case ParamConst.MESSAGE_WHAT_SUCCESS:
			Intent intent = new Intent();
			intent.setClass(activity, NewsListFragActivity.class);
			activity.startActivity(intent);
			activity.finish();
			break;
		case ParamConst.MESSAGE_WHAT_ERROR:
			Object obj = msg.obj;
			Toast.makeText(activity, obj.toString(), Toast.LENGTH_SHORT).show();
			break;
		default:
			Toast.makeText(activity, UrlCodeErrorConst.UNKNOWN_ERROR_HINT, Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
