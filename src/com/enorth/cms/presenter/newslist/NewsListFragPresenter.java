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
	public void requestListViewData(final Handler handler) throws Exception {
		String url = UrlConst.NEWS_LIST_POST_URL;
		final int start = 1;
		final int end = 10;
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("start", String.valueOf(start)));
		params.add(new BasicNameValuePair("end", String.valueOf(end)));
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
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void onFailure(Request r, IOException e) {
				commonOnFailure(r, e, handler);
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
				commonOnFailure(r, e, handler);
			}
		};
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("channelId", String.valueOf(channelId)));
		params.add(new BasicNameValuePair("userId", String.valueOf(userId)));
		HttpUtil.okPost(UrlConst.GET_CUR_CHANNEL, params, callback);
	}

	public void commonOnFailure(Request r, IOException e, Handler handler) {
		Message message = new Message();
		String errorMsg = e.getMessage();
		if (errorMsg == null) {
			errorMsg = "服务器异常";
		}
		Log.e("错误信息", errorMsg);
		message.what = ParamConst.MESSAGE_WHAT_ERROR;
		message.obj = errorMsg;
		handler.sendMessage(message);
	}
}
