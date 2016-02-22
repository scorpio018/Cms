package com.enorth.cms.task;

import java.util.TimerTask;

import android.os.Handler;

public class CommonTask extends TimerTask {
	
	private Handler handler;
	
	public CommonTask(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {
		handler.sendEmptyMessage(0);
	}

}
