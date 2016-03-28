package com.enorth.cms.presenter.newslist;

import java.io.IOException;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.enorth.cms.consts.UrlConst;
import com.enorth.cms.enums.HttpBuilderType;
import com.enorth.cms.utils.HttpUtil;
import com.enorth.cms.utils.LogUtil;
import com.enorth.cms.view.news.IChannelSearchView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.os.Handler;
import android.util.Log;

public class ChannelSearchPresenter implements IChannelSearchPresenter {
	
	private IChannelSearchView view;
	
	public ChannelSearchPresenter(IChannelSearchView view) {
		this.view = view;
	}
	
	

	@Override
	public void initChannelData(List<BasicNameValuePair> params, final Handler handler) throws Exception {
		Callback callback = new Callback() {
			
			@Override
			public void onResponse(Response r) {
				String resultString = null;
				try {
					resultString = HttpUtil.checkResponseIsSuccess(r);
//					LogUtil.printLog(resultString, view.getActivity());
					view.initChannelData(resultString, handler);
				} catch (Exception e) {
					Log.e("error", e.toString());
					HttpUtil.responseOnFailure(r, e, handler);
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFailure(Request r, IOException e) {
				HttpUtil.requestOnFailure(r, e, handler);
			}
		};
		HttpUtil.okPost(UrlConst.CHANNEL_SEARCH_POST_URL, params, callback, HttpBuilderType.REQUEST_FORM_ENCODE);
	}



	@Override
	public void getMyChannel(List<BasicNameValuePair> params, final Handler handler) throws Exception {
		Callback callback = new Callback() {
			
			@Override
			public void onResponse(Response r) throws IOException {
				try {
					String resultString = HttpUtil.checkResponseIsSuccess(r);
					view.getMyChannel(resultString, handler);
				} catch (Exception e) {
//					view.error(e);
					HttpUtil.responseOnFailure(r, e, handler);
				}
			}
			
			@Override
			public void onFailure(Request r, IOException e) {
				HttpUtil.requestOnFailure(r, e, handler);
			}
		};
		HttpUtil.okPost(UrlConst.MY_CHANNEL, params, callback, HttpBuilderType.REQUEST_FORM_ENCODE);
	}

}
