package com.enorth.cms.presenter.newslist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.consts.UrlConst;
import com.enorth.cms.utils.HttpUtil;
import com.enorth.cms.view.news.IChannelSearchView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ChannelSearchPresenter implements IChannelSearchPresenter {
	
	private IChannelSearchView view;
	
	public ChannelSearchPresenter(IChannelSearchView view) {
		this.view = view;
	}
	
	

	@Override
	public void initChannelData(Long channelId, final Handler handler) throws Exception {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("channelId", String.valueOf(channelId)));
		Callback callback = new Callback() {
			
			@Override
			public void onResponse(Response r) {
				String resultString = null;
				try {
					resultString = HttpUtil.checkResponseIsSuccess(r);
					view.initChannelData(resultString, handler);
				} catch (Exception e) {
					Log.e("error", e.getMessage());
					HttpUtil.responseOnFailure(r, e, handler);
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFailure(Request r, IOException e) {
				HttpUtil.requestOnFailure(r, e, handler);
			}
		};
		HttpUtil.okPost(UrlConst.CHANNEL_SEARCH_POST_URL, params, callback);
	}



	@Override
	public void getMyChannel(Integer userId, final Handler handler) throws Exception {
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
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("userId", String.valueOf(userId)));
		HttpUtil.okPost(UrlConst.MY_CHANNEL, params, callback);
	}

}
