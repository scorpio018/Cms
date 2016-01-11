package com.enorth.cms.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Intent intent = new Intent();
	
	private Button btnToMaterialUploadActivity, btnToMaterialBtnActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		initBtnToMaterialUploadActivity();
		initBtnToMaterialBtnActivity();
	}
	
	private void initBtnToMaterialUploadActivity() {
		btnToMaterialUploadActivity = (Button) findViewById(R.id.btnToMaterialUploadActivity);
		btnToMaterialUploadActivity.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent.setClass(MainActivity.this, MaterialUploadActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void initBtnToMaterialBtnActivity() {
		btnToMaterialBtnActivity = (Button) findViewById(R.id.btnToMaterialBtnActivity);
		btnToMaterialBtnActivity.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent.setClass(MainActivity.this, MaterialBtnActivity.class);
				startActivity(intent);
			}
		});
	}
}
