package com.enorth.cms.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity {
	
	private LeftHorizontalScrollMenu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		menu = (LeftHorizontalScrollMenu) findViewById(R.id.leftMenu);
	}
	
	public void toggleMenu(View view) {
		menu.toggle();
	}
	
}
