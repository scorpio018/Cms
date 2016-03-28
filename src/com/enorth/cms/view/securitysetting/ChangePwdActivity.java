package com.enorth.cms.view.securitysetting;

import com.enorth.cms.view.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ChangePwdActivity extends Activity implements IChangePwdView {
	/**
	 * 退回
	 */
	private ImageView titleLeftTV;
	/**
	 * 用户名
	 */
	private EditText userNameET;
	/**
	 * 原密码
	 */
	private EditText prevPwdET;
	/**
	 * 新密码
	 */
	private EditText newPwdET;
	/**
	 * 密码确认
	 */
	private EditText newPwdConfirmET;
	/**
	 * 确认按钮
	 */
	private Button changePwdSubmitBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);
		initView();
		initData();
		initEvent();
	}
	
	private void initView() {
		titleLeftTV = (ImageView) findViewById(R.id.titleLeftTV);
		userNameET = (EditText) findViewById(R.id.userNameET);
		prevPwdET = (EditText) findViewById(R.id.prevPwdET);
		newPwdET = (EditText) findViewById(R.id.newPwdET);
		newPwdConfirmET = (EditText) findViewById(R.id.newPwdConfirmET);
		changePwdSubmitBtn = (Button) findViewById(R.id.changePwdSubmitBtn);
	}
	
	private void initData() {
		initUserName();
	}
	
	private void initUserName() {
		userNameET.setText("用户名：改密码");
	}
	
	private void initEvent() {
		initBackEvent();
		initSubmitEvent();
	}
	
	private void initBackEvent() {
		titleLeftTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ChangePwdActivity.this.onBackPressed();
			}
		});
	}
	
	private void initSubmitEvent() {
		changePwdSubmitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!newPwdConfirmET.getText().toString().equals(newPwdET.getText().toString())) {
					Toast.makeText(ChangePwdActivity.this, "新密码和确认密码不一致，请重输！", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
	}
}