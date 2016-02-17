package com.enorth.cms.presenter.newslist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.consts.UrlConst;
import com.enorth.cms.utils.HttpUtil;
import com.enorth.cms.view.news.INewsCommonView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class NewsListFragPresenter implements INewsListFragPresenter {
	
	private INewsCommonView view;
	
	public NewsListFragPresenter(INewsCommonView view) {
		this.view = view;
	}

	/**
	 * 请求新闻列表的接口，并将结果传入activity中进行处理
	 */
	@Override
	public void requestListViewData(final Handler handler, List<BasicNameValuePair> params) throws Exception {
		String url = UrlConst.NEWS_LIST_POST_URL;
		Callback callback = new Callback() {

			@Override
			public void onResponse(Response r) throws IOException {
				String resultString = null;
				try {
					resultString = HttpUtil.checkResponseIsSuccess(r);
					view.initData(resultString, handler);

				} catch (Exception e) {
					try {
						Message msg = new Message();
						msg.what = ParamConst.MESSAGE_WHAT_ERROR;
						msg.obj = "错误信息：" + e.getMessage();
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
		};
		HttpUtil.okPost(url, params, callback);
	}

	@Override
	public void requestCurChannelData(Long channelId, int userId, final Handler handler) throws Exception {
		Callback callback = new Callback() {
			
			@Override
			public void onResponse(Response r) throws IOException {
				String resultString = null;
				try {
					resultString = HttpUtil.checkResponseIsSuccess(r);
					view.initSubTitleResult(resultString, handler);
				} catch (Exception e) {
					try {
						Message msg = new Message();
						msg.what = ParamConst.MESSAGE_WHAT_ERROR;
						msg.obj = "错误信息：" + e.getMessage();
						handler.sendMessage(msg);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			
			@Override
			public void onFailure(Request r, IOException e) {
				HttpUtil.requestOnFailure(r, e, handler);
			}
		};
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("channelId", String.valueOf(channelId)));
		params.add(new BasicNameValuePair("userId", String.valueOf(userId)));
		HttpUtil.okPost(UrlConst.GET_CUR_CHANNEL, params, callback);
	}

}
