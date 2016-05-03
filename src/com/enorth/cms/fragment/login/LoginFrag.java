package com.enorth.cms.fragment.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.enorth.cms.bean.login.LoginBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.handler.login.LoginHandler;
import com.enorth.cms.listener.EditTextDrawableOnTouchListener;
import com.enorth.cms.listener.login.UserNameAndPwdTextWatcher;
import com.enorth.cms.listener.login.UserNameDelBtnListener;
import com.enorth.cms.listener.login.UserNameHistoryOnTouchListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.PopupWindowUtil;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.StringUtil;
import com.enorth.cms.utils.UrlUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.login.ILoginView;
import com.enorth.cms.widget.popupwindow.CommonPopupWindow;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginFrag extends Fragment {
	
	private LinearLayout layout;
	
	private EditText userNameET;
	
	private EditText pwdET;
	
	private CheckBox rememberPwdCB;
	
	private Button loginSubmitBtn;
	
//	private CommonOnKeyListener listener;
	private UserNameAndPwdTextWatcher textWatcher;
	
	private ILoginView loginView;
	
	private LoginHandler handler;
	
	private PopupWindowUtil popupWindowUtil;
	
	private CommonPopupWindow popupWindow;
	/**
	 * 系统记录的登录过的用户名
	 */
	private List<String> userNames = new ArrayList<String>();
	
	private Map<String, LoginBean> usersMap = new HashMap<String, LoginBean>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		layout = (LinearLayout) inflater.inflate(R.layout.login_frag, null);
		initView();
		initHandler();
		initData();
		initEvent();
		return layout;
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		loginView = (ILoginView) context;
	}
	
	private void initView() {
		userNameET = (EditText) layout.findViewById(R.id.userNameET);
		pwdET = (EditText) layout.findViewById(R.id.pwdET);
		rememberPwdCB = (CheckBox) layout.findViewById(R.id.rememberPwdCB);
		loginSubmitBtn = (Button) layout.findViewById(R.id.loginSubmitBtn);
	}
	
	private void initHandler() {
		handler = new LoginHandler(getActivity());
	}
	
	private void initData() {
//		SharedPreUtil.remove(getContext(), ParamConst.REMEMBERED_USER, ParamConst.LOGIN_USER);
		String[] usersStr = SharedPreUtil.getStringArray(getContext(), ParamConst.REMEMBERED_USER, ParamConst.LOGIN_USER);
		
		for (String userStr : usersStr) {
			if (StringUtil.isNotEmpty(userStr)) {
				LoginBean bean = (LoginBean) SharedPreUtil.deSerializeObject(userStr);
				userNames.add(bean.getUserName());
				usersMap.put(bean.getUserName(), bean);
			}
		}
		if (usersStr.length != 0) {
			LoginBean loginUserBean = UrlUtil.getLoginUserBean(getContext());
			userNameET.setText(loginUserBean.getUserName());
			Boolean rememberUser = loginUserBean.isRememberUser();
			rememberPwdCB.setChecked(rememberUser);
			if (rememberUser) {
				pwdET.setText(loginUserBean.getPassword());
				loginSubmitBtn.setClickable(true);
				loginSubmitBtn.setEnabled(true);
				loginSubmitBtn.setBackgroundResource(R.drawable.common_submit_btn_radius_and_stroke);
			}
		}
//		initPopupWindow(userNames);
	}
	
	private void initPopupWindow() {
		if (popupWindowUtil == null) {
			popupWindowUtil = new PopupWindowUtil(getContext(), userNameET) {
				
				@Override
				public void initItems(LinearLayout layout) {
					UserNameHistoryOnTouchListener itemListener = new UserNameHistoryOnTouchListener(LoginFrag.this, layout) {
						
						@Override
						public void onImgChangeEnd(View v) {
							popupWindow.dismiss();
							popupWindow = null;
						}
					};
					UserNameDelBtnListener delBtnListener = new UserNameDelBtnListener(LoginFrag.this, layout);
					initPopupWindowItemsContainDelMark(layout, itemListener, delBtnListener, userNames);
				}
				
			};
		}
		popupWindowUtil.setWidth(userNameET.getMeasuredWidth());
		popupWindowUtil.setPopupBgColor(ColorUtil.getWhiteColor(getContext()));
		popupWindowUtil.setPopupBgTouchColor(ColorUtil.getBgGrayPress(getContext()));
		popupWindowUtil.setTextColor(ColorUtil.getBlack(getContext()));
		popupWindow = popupWindowUtil.initPopupWindow();
	}
	
	private void initEvent() {
		initListener();
		initUserNameEvent();
		initPwdEvent();
		checkCanSubmitEvent();
		initSubmitEvent();
	}
	
	private void initListener() {
		initKeyListener();
	}
	
	private void initKeyListener() {
		textWatcher = new UserNameAndPwdTextWatcher(this);
	}
	
	private void initUserNameEvent() {
		userNameET.setOnTouchListener(new EditTextDrawableOnTouchListener(getActivity()) {
			
			@Override
			public EditText getEditText() {
				return userNameET;
			}
			
			@Override
			public void eventDo() {
				initPopupWindow();
			}
		});
		userNameET.addTextChangedListener(textWatcher);
		userNameET.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_DEL) {
					pwdET.setText("");
					rememberPwdCB.setChecked(false);
					disableSubmit();
				}
				return false;
			}
		});
	}
	
	private void initPwdEvent() {
		pwdET.addTextChangedListener(textWatcher);
	}
	
	/**
	 * 判断是否可以点击提交按钮
	 * @return
	 */
	public void checkCanSubmitEvent() {
		if (StringUtil.isEmpty(userNameET.getText().toString()) || StringUtil.isEmpty(pwdET.getText().toString())) {
			disableSubmit();
		} else {
			enableSubmit();
		}
	}
	
	private void disableSubmit() {
		loginSubmitBtn.setClickable(false);
		loginSubmitBtn.setEnabled(false);
		loginSubmitBtn.setBackgroundResource(R.drawable.common_submit_disabled_btn_radius_and_stroke);
	}
	
	private void enableSubmit() {
		loginSubmitBtn.setClickable(true);
		loginSubmitBtn.setEnabled(true);
		loginSubmitBtn.setBackgroundResource(R.drawable.common_submit_btn_radius_and_stroke);
	}
	
	private void initSubmitEvent() {
		loginSubmitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (loginSubmitBtn.isClickable()) {
					if (StringUtil.isEmpty(StaticUtil.getCurScanBean(getContext()).getScanUrl())) {
						ViewUtil.showAlertDialog(getContext(), "请先扫描系统中手机发新闻功能页对应二维码再进行登录");
						return;
					}
					LoginBean bean = new LoginBean();
					bean.setUserName(userNameET.getText().toString());
					bean.setPassword(pwdET.getText().toString());
					boolean checked = rememberPwdCB.isChecked();
					if (checked) {
						loginView.getPresenter().login(bean, true, handler);
					} else {
						loginView.getPresenter().login(bean, false, handler);
					}
				}
			}
		});
	}

	public EditText getUserNameET() {
		return userNameET;
	}

	public void setUserNameET(EditText userNameET) {
		this.userNameET = userNameET;
	}

	public EditText getPwdET() {
		return pwdET;
	}

	public void setPwdET(EditText pwdET) {
		this.pwdET = pwdET;
	}

	public CheckBox getRememberPwdCB() {
		return rememberPwdCB;
	}

	public void setRememberPwdCB(CheckBox rememberPwdCB) {
		this.rememberPwdCB = rememberPwdCB;
	}

	public Button getLoginSubmitBtn() {
		return loginSubmitBtn;
	}

	public void setLoginSubmitBtn(Button loginSubmitBtn) {
		this.loginSubmitBtn = loginSubmitBtn;
	}

	public Map<String, LoginBean> getUsersMap() {
		return usersMap;
	}

	public void setUsersMap(Map<String, LoginBean> usersMap) {
		this.usersMap = usersMap;
	}
	
}
