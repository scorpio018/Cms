package com.enorth.cms.view.material;

import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MaterialUploadActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtil.setContentViewForMenu(this, R.layout.activity_material_upload);
		try {
			initNewsTitle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化标头
	 */
	private void initNewsTitle() throws Exception {
		initMenuBtn();
		initTitleText();
		initTitleSearchBtn();
	}
	
	private void initMenuBtn() throws Exception {
		ImageView menuBtn = (ImageView) findViewById(R.id.titleLeftBtn);
		menuBtn.setBackgroundResource(R.drawable.news_menu);
		ViewUtil.initMenuEvent(this, menuBtn);
	}
	
	private void initTitleText() throws Exception {
		TextView materialUploadTitleText = (TextView) findViewById(R.id.titleText);
		materialUploadTitleText.setText(R.string.material_upload_title_text);
	}
	
	private void initTitleSearchBtn() throws Exception {
		ImageView materialEditBtn = (ImageView) findViewById(R.id.titleRightBtn);
		materialEditBtn.setBackgroundResource(R.drawable.news_search);
	}
	
}
