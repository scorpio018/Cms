package com.enorth.cms.activity;

import com.enorth.cms.consts.ParamConst;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

public class SplashActivity extends BaseActivity {
	
	private Intent intent = new Intent();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		SharedPreferences pre = getSharedPreferences(ParamConst.ACTIVITY_IS_FIRST_ENTER, 0);
		boolean isFirst = pre.getBoolean(ParamConst.ACTIVITY_IS_FIRST_ENTER, true);
		
		if (isFirst) {
			toWelcomeActivity();
		} else {
			toMainActivity();
		}
		finish();
	}
	
	private void toWelcomeActivity() {
		intent.setClass(SplashActivity.this, WelcomeActivity.class);
		startActivity(intent);
	}
	
	private void toMainActivity() {
		intent.setClass(SplashActivity.this, MainActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void exitClick() {
		Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT);
	}
}
