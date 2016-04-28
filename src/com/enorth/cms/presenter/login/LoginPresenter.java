package com.enorth.cms.presenter.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.enorth.cms.bean.login.LoginBean;
import com.enorth.cms.consts.UrlConst;
import com.enorth.cms.enums.HttpBuilderType;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.BeanParamsUtil;
import com.enorth.cms.utils.HttpUtil;
import com.enorth.cms.view.login.ILoginView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.os.Handler;
import android.util.Log;

public class LoginPresenter implements ILoginPresenter {
	
	private ILoginView view;
	
	public LoginPresenter(ILoginView view) {
		this.view = view;
	}

	@Override
	public void login(final LoginBean bean, final boolean rememberUser, final Handler handler) {
		List<BasicNameValuePair> params = BeanParamsUtil.initDataSimple(bean, view.getActivity());
		Callback callback = new Callback() {
			
			@Override
			public void onResponse(Response r) throws IOException {
				String resultString = null;
				try {
					resultString = HttpUtil.checkResponseIsSuccess(r);
					view.login(resultString, bean, rememberUser, handler);
				} catch (Exception e) {
					Log.e("LoginPresenter login error", e.toString());
					HttpUtil.responseOnFailure(r, e, handler);
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Request r, IOException e) {
				HttpUtil.requestOnFailure(r, e, handler);
			}
		};
		AnimUtil.showRefreshFrame(view.getActivity(), true, "正在登录，请稍后");
		HttpUtil.okPost(UrlConst.LOGIN_URL, params, callback, HttpBuilderType.REQUEST_FORM_ENCODE);
	}

}
