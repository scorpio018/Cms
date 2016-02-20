package com.enorth.cms.view.material;

import com.enorth.cms.fragment.materialupload.MaterialUploadBtnGroupFrag;
import com.enorth.cms.fragment.materialupload.MaterialUploadHistoryFrag;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MaterialUploadActivity extends FragmentActivity {
	
	private FragmentManager fragmentManager;
	
	private FragmentTransaction fragmentTransaction;
	/**
	 * 底部菜单（标识当前的附件内容来源类别）
	 */
	private TextView materialFromTypeTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtil.setContentViewForMenu(this, R.layout.activity_material_upload);
		try {
			initNewsTitle();
			initFrag();
			initBottom();
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
	
	/**
	 * 初始化按钮组和附件内容
	 * @throws Exception
	 */
	private void initFrag() throws Exception {
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		initBtnGroupFrag();
		initFileHistoryFrag();
		fragmentTransaction.commit();
	}
	
	private void initBottom() throws Exception {
		materialFromTypeTV = (TextView) findViewById(R.id.materialFromTypeTV);
		materialFromTypeTV.setText("来自app");
	}
	
	/**
	 * 初始化左上角的菜单按钮
	 * @throws Exception
	 */
	private void initMenuBtn() throws Exception {
		ImageView menuBtn = (ImageView) findViewById(R.id.titleLeftBtn);
		menuBtn.setBackgroundResource(R.drawable.news_menu);
		ViewUtil.initMenuEvent(this, menuBtn);
	}
	
	/**
	 * 初始化标题
	 * @throws Exception
	 */
	private void initTitleText() throws Exception {
		TextView materialUploadTitleText = (TextView) findViewById(R.id.titleText);
		materialUploadTitleText.setText(R.string.material_upload_title_text);
	}
	
	/**
	 * 初始化右上角的搜索按钮
	 * @throws Exception
	 */
	private void initTitleSearchBtn() throws Exception {
		ImageView materialEditBtn = (ImageView) findViewById(R.id.titleRightBtn);
		materialEditBtn.setBackgroundResource(R.drawable.news_search);
	}
	
	/**
	 * 初始化初始化按钮组fragment
	 * @throws Exception
	 */
	private void initBtnGroupFrag() throws Exception {
		MaterialUploadBtnGroupFrag frag = new MaterialUploadBtnGroupFrag();
		fragmentTransaction.add(R.id.materialUploadBtnGroupFrag, frag);
	}
	
	/**
	 * 初始化附件内容fragment
	 * @throws Exception
	 */
	private void initFileHistoryFrag() throws Exception {
		MaterialUploadHistoryFrag frag = new MaterialUploadHistoryFrag();
		fragmentTransaction.add(R.id.materialUploadHistoryFrag, frag);
	}
	
}
