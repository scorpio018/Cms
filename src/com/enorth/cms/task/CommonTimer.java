package com.enorth.cms.task;

import java.util.Timer;

import android.os.Handler;

public class CommonTimer {
	
	private Handler handler;
	private Timer timer;
	private CommonTask task;
	
	public CommonTimer(Handler handler) {
		this.handler = handler;
		timer = new Timer();
		task = new CommonTask(handler);
	}
	
	

}
