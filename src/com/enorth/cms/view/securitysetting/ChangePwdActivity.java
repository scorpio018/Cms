package com.enorth.cms.view.securitysetting;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.enorth.cms.bean.securitysetting.RequestChangePwdUrlBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.consts.UrlConst;
import com.enorth.cms.handler.securitysetting.ChangePwdHandler;
import com.enorth.cms.presenter.securitysetting.ChangePwdPresenter;
import com.enorth.cms.presenter.securitysetting.IChangePwdPresenter;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.BeanParamsUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.StringUtil;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePwdActivity extends Activity implements IChangePwdView {
	/**
	 * 退回
	 */
	private TextView titleLeftTV;
	/**
	 * “修改密码”标题
	 */
	private TextView titleMiddleTV;
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
	/**
	 * 调用修改密码接口时传入的参数bean
	 */
	private RequestChangePwdUrlBean requestChangePwdUrlBean;
	/**
	 * 修改密码
	 */
	private ChangePwdHandler changePwdHandler;
	/**
	 * 
	 */
	private IChangePwdPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);
		initView();
		initPresenter();
		initData();
		initHandler();
		initEvent();
	}

	private void initView() {
		titleLeftTV = (TextView) findViewById(R.id.titleLeftTV);
		titleMiddleTV = (TextView) findViewById(R.id.titleMiddleTV);
		userNameET = (EditText) findViewById(R.id.userNameET);
		prevPwdET = (EditText) findViewById(R.id.prevPwdET);
		newPwdET = (EditText) findViewById(R.id.newPwdET);
		newPwdConfirmET = (EditText) findViewById(R.id.newPwdConfirmET);
		changePwdSubmitBtn = (Button) findViewById(R.id.changePwdSubmitBtn);
	}

	private void initPresenter() {
		presenter = new ChangePwdPresenter(this);
	}

	private void initData() {
		titleMiddleTV.setText("修改密码");
		initUserName();
		requestChangePwdUrlBean = new RequestChangePwdUrlBean();
	}

	private void initHandler() {
		changePwdHandler = new ChangePwdHandler(this);
	}

	private void initUserName() {
		userNameET.setText("用户名：" + StaticUtil.getCurLoginBean(this).getUserName());
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
				requestChangePwdUrlBean.setOrgPwd(prevPwdET.getText().toString());
				requestChangePwdUrlBean.setNewPwd(newPwdET.getText().toString());
				List<BasicNameValuePair> initData = BeanParamsUtil.initData(requestChangePwdUrlBean,
						ChangePwdActivity.this);

				AnimUtil.showRefreshFrame(ChangePwdActivity.this, true, "正在修改密码，请稍后");
				presenter.changePwd(UrlConst.CHANGE_PWD, changePwdHandler, initData);
			}
		});
	}

	@Override
	public void initReturnJsonData(String resultString, Handler handler) {
		if (StringUtil.isEmpty(resultString)) {
			changePwdHandler.sendEmptyMessage(ParamConst.MESSAGE_WHAT_SUCCESS);
		} else {
			Message msg = new Message();
			msg.what = ParamConst.MESSAGE_WHAT_ERROR;
			msg.obj = resultString;
			changePwdHandler.sendMessage(msg);
		}
	}

	@Override
	public void backToPrevActivity() {
		onBackPressed();
	}

	@Override
	public Context getContext() {
		return this;
	}

}