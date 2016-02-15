package com.enorth.cms.view;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.view.news.NewsListFragActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashActivity extends Activity {
	
	private Intent intent = new Intent();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/**
		 * 有四种模式，分别为：
		 * Context.MODE_PRIVATE = 0
		 * Context.MODE_APPEND = 32768
		 * Context.MODE_WORLD_READABLE = 1
		 * Context.MODE_WORLD_WRITEABLE = 2
		 * Context.MODE_PRIVATE：为默认操作模式，代表该文件是私有数据，只能被应用本身访问，
		 * 	在该模式下，写入的内容会覆盖原文件的内容，如果想把新写入的内容追加到原文件中。可以使用Context.MODE_APPEND
		 * Context.MODE_APPEND：模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件。
		 * Context.MODE_WORLD_READABLE和Context.MODE_WORLD_WRITEABLE用来控制其他应用是否有权限读写该文件。
		 * MODE_WORLD_READABLE：表示当前文件可以被其他应用读取；MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入。
		 * 另外Activity还提供了另一个getPreferences(mode)方法操作SharedPreferences，这个方法默认使用当前类不带包名的类名作为文件的名称。
		 */
		SharedPreferences pre = getSharedPreferences(ParamConst.ACTIVITY_IS_FIRST_ENTER, Context.MODE_PRIVATE);
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
//		intent.setClass(SplashActivity.this, MainActivity.class);
		intent.setClass(SplashActivity.this, NewsListFragActivity.class);
		startActivity(intent);
	}
	
}
