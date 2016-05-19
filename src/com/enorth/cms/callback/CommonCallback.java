package com.enorth.cms.callback;

import java.io.IOException;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.utils.HttpUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.os.Handler;
import android.os.Message;

public abstract class CommonCallback implements Callback {

	private Handler handler;
	
	public CommonCallback(Handler handler) {
		this.handler = handler;
	}
	
	public abstract void initReturnJsonData(String resultString);
	
	@Override
	public void onResponse(Response r) throws IOException {
		String resultString = null;
		try {
			resultString = HttpUtil.checkResponseIsSuccess(r);
			initReturnJsonData(resultString);
		} catch (Exception e) {
			try {
				Message msg = new Message();
				msg.what = ParamConst.MESSAGE_WHAT_ERROR;
				msg.obj = e.getMessage();
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

}
