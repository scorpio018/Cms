package com.enorth.cms.view.login;

import com.enorth.cms.bean.login.LoginBean;
import com.enorth.cms.presenter.login.ILoginPresenter;

import android.os.Handler;

public interface ILoginView {
	
	public LoginActivity getActivity();
	
	public ILoginPresenter getPresenter();
	
	public void login(String resultStr, LoginBean bean, Boolean rememberUser, Handler handler);
	
}
