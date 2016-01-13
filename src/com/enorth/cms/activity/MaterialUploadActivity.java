package com.enorth.cms.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MaterialUploadActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_material_upload);
	}
	
	@Override
	public void exitClick() {
		Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT);
	}
}
