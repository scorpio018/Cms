package com.enorth.cms.presenter.securitysetting;

import java.io.IOException;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.enorth.cms.callback.CommonCallback;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.enums.HttpBuilderType;
import com.enorth.cms.utils.HttpUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.view.securitysetting.IChangePwdView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.os.Handler;
import android.os.Message;

public class ChangePwdPresenter implements IChangePwdPresenter {
	
	private IChangePwdView view;
	
	public ChangePwdPresenter(IChangePwdView view) {
		this.view = view;
	}

	@Override
	public void changePwd(String url, final Handler handler, List<BasicNameValuePair> params) {
		/*final Callback callback = new Callback() {

			@Override
			public void onResponse(Response r) throws IOException {
				String resultString = null;
				try {
					resultString = HttpUtil.checkResponseIsSuccess(r);
					view.initReturnJsonData(resultString, handler);
				} catch (Exception e) {
					try {
						Message msg = new Message();
						msg.what = ParamConst.MESSAGE_WHAT_ERROR;
						msg.obj = e.toString();
						handler.sendMessage(msg);
					} catch (Exception e1) {
						HttpUtil.responseOnFailure(r, e, handler);
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void onFailure(Request r, IOException e) {
				HttpUtil.requestOnFailure(r, e, handler);
			}
		};*/
		Callback callback = new CommonCallback(handler) {
			
			@Override
			public void initReturnJsonData(String resultString) {
				view.initReturnJsonData(resultString, handler);
			}
		};
		HttpUtil.okPost(StaticUtil.getCurScanBean(view.getContext()).getScanUrl() + url, params, callback, HttpBuilderType.REQUEST_FORM_ENCODE);
	}

}
