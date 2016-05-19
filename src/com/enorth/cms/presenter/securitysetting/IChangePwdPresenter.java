package com.enorth.cms.presenter.securitysetting;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;

public interface IChangePwdPresenter {
	/**
	 * 修改密码
	 * @param url
	 * @param handler
	 * @param params
	 */
	public void changePwd(String url, Handler handler, List<BasicNameValuePair> params);
}
