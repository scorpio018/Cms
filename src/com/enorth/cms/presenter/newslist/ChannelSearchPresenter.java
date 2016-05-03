package com.enorth.cms.presenter.newslist;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.enorth.cms.callback.CommonCallback;
import com.enorth.cms.consts.UrlConst;
import com.enorth.cms.enums.HttpBuilderType;
import com.enorth.cms.utils.HttpUtil;
import com.enorth.cms.utils.LogUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.view.news.IChannelSearchView;
import com.squareup.okhttp.Callback;

import android.os.Handler;

public class ChannelSearchPresenter implements IChannelSearchPresenter {
	
	private IChannelSearchView view;
	
	public ChannelSearchPresenter(IChannelSearchView view) {
		this.view = view;
	}
	
	

	@Override
	public void initChannelData(List<BasicNameValuePair> params, final Handler handler) {
			Callback callback = new CommonCallback(handler) {
			
			@Override
			public void initReturnJsonData(String resultString) {
				LogUtil.printLog(resultString, view.getActivity());
				view.initChannelData(resultString, handler);
			}
		};
		HttpUtil.okPost(StaticUtil.getCurScanBean(view.getActivity()).getScanUrl() + UrlConst.CHANNEL_SEARCH_POST_URL, params, callback, HttpBuilderType.REQUEST_FORM_ENCODE);
	}



	@Override
	public void getMyChannel(List<BasicNameValuePair> params, final Handler handler) {
		/*Callback callback = new Callback() {
			
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
		};*/
		Callback callback = new CommonCallback(handler) {
			
			@Override
			public void initReturnJsonData(String resultString) {
				view.getMyChannel(resultString, handler);
			}
		};
		HttpUtil.okPost(StaticUtil.getCurScanBean(view.getActivity()).getScanUrl() + UrlConst.MY_CHANNEL, params, callback, HttpBuilderType.REQUEST_FORM_ENCODE);
	}

}
