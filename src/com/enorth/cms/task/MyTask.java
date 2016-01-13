package com.enorth.cms.task;

import java.util.TimerTask;

import android.os.Handler;

public class MyTask extends TimerTask {
	private Handler handler;

	public MyTask(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {
		handler.obtainMessage().sendToTarget();
	}

}