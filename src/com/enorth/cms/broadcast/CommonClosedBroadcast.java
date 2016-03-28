package com.enorth.cms.broadcast;

import com.enorth.cms.consts.ParamConst;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class CommonClosedBroadcast {
	
	public CommonClosedBroadcast(final Activity activity) {
		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				activity.unregisterReceiver(this);
				activity.finish();
			};
		};
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(ParamConst.CLOSE_ACTIVITY);
		activity.registerReceiver(receiver, filter);
	}
	
	public static void close(Activity activity) {
		Intent intent = new Intent();
		intent.setAction(ParamConst.CLOSE_ACTIVITY);
		activity.sendBroadcast(intent);
		activity.finish();
	}
}
