package com.enorth.cms.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.enorth.cms.view.material.MaterialBtnActivity;
import com.enorth.cms.view.material.MaterialUploadActivity;
import com.enorth.cms.view.news.NewsListFragActivity;
import com.enorth.cms.view.web.PullableWebViewActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityBak extends BaseActivity {
	
	private Button btnToMaterialUploadActivity, btnToMaterialBtnActivity, 
		btnToPullableWebViewActivity, btnToCrossWalkActivity, btnToNewsListActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_bak);
		init();
	}

	private void init() {
		initCurTime();
		initBtnToMaterialBtnActivity();
		initBtnToPullableWebViewActivity();
//		initBtnToCrossWalkActivity();
		initBtnToNewsListActivity();
		initBtnToMaterialUploadActivity();
	}
	
	private void initCurTime() {
		String curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		TextView curTimeTv = (TextView) findViewById(R.id.curTime);
		curTimeTv.setText(curTime);
	}
	
	/*private void initBtnToMaterialUploadActivity() {
		btnToMaterialUploadActivity = (Button) findViewById(R.id.btnToMaterialUploadActivity);
		btnToMaterialUploadActivity.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent.setClass(MainActivity.this, MaterialUploadActivity.class);
				startActivity(intent);
			}
		});
	}*/
	
	private void initBtnToMaterialBtnActivity() {
		btnToMaterialBtnActivity = (Button) findViewById(R.id.btnToMaterialBtnActivity);
		btnToMaterialBtnActivity.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent.setClass(MainActivityBak.this, MaterialBtnActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void initBtnToPullableWebViewActivity() {
		btnToPullableWebViewActivity = (Button) findViewById(R.id.btnToPullableWebViewActivity);
		btnToPullableWebViewActivity.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent.setClass(MainActivityBak.this, PullableWebViewActivity.class);
				startActivity(intent);
			}
		});
	}
	
	/*private void initBtnToCrossWalkActivity() {
		btnToCrossWalkActivity = (Button) findViewById(R.id.btnToCrossWalkActivity);
		btnToCrossWalkActivity.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent.setClass(MainActivity.this, CrossWalkActivity.class);
				startActivity(intent);
			}
		});
	}*/
	
	private void initBtnToNewsListActivity() {
		btnToNewsListActivity = (Button) findViewById(R.id.btnToNewsListActivity);
		btnToNewsListActivity.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				intent.setClass(MainActivity.this, NewsListActivity.class);
//				intent.setClass(MainActivity.this, NewsListFixActivity.class);
				intent.setClass(MainActivityBak.this, NewsListFragActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void initBtnToMaterialUploadActivity() {
		btnToMaterialUploadActivity = (Button) findViewById(R.id.btnToMaterialUploadActivity);
		btnToMaterialUploadActivity.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent.setClass(MainActivityBak.this, MaterialUploadActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public void exitClick() {
		Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT);
	}
}
