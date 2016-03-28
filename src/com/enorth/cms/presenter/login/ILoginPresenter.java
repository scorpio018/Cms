package com.enorth.cms.presenter.login;

import com.enorth.cms.bean.login.LoginBean;

import android.os.Handler;

public interface ILoginPresenter {
	public void login(LoginBean bean, boolean rememberUser, Handler handler) throws Exception;
}
