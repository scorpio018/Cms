package com.enorth.cms.broadcast;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.view.news.NewsAddActivity;
import com.enorth.cms.view.news.NewsAddChooseEnclosureActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class CommonBroadcast {
	
	public CommonBroadcast(final Activity activity, String intentFilterAction) {
		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals(ParamConst.CLOSE_ACTIVITY)) {
					activity.unregisterReceiver(this);
					activity.finish();
				} else if (action.equals(ParamConst.UPLOAD_NEWS_ATT)) {
					if (activity instanceof NewsAddActivity) {
						String newsAttHtmlCode = intent.getStringExtra(ParamConst.NEWS_ATT_HTML_CODE);
						NewsAddActivity newsAddActivity = (NewsAddActivity) activity;
						newsAddActivity.addCmsBlock(newsAttHtmlCode);
					}
				}
			};
		};
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(intentFilterAction);
		activity.registerReceiver(receiver, filter);
	}
	
	public void close(Activity activity) {
		Intent intent = new Intent();
		intent.setAction(ParamConst.CLOSE_ACTIVITY);
		activity.sendBroadcast(intent);
		activity.finish();
	}
	
	public void uploadNewsAtt(Activity activity, Intent intent) {
		intent.setAction(ParamConst.UPLOAD_NEWS_ATT);
		activity.sendBroadcast(intent);
	}
	
}
