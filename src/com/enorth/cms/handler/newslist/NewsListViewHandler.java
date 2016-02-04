package com.enorth.cms.handler.newslist;

import java.util.List;

import com.enorth.cms.adapter.news.NewsListViewAdapter;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.handler.UrlRequestCommonHandler;
import com.enorth.cms.view.news.NewsCommonActivity;
import com.enorth.cms.widget.listview.newslist.NewsListListView;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListAdapter;
/**
 * 将接口中返回并组装好的List<View>加载到ListView中
 * @author yangyang
 *
 */
public class NewsListViewHandler extends UrlRequestCommonHandler {

	private NewsCommonActivity activity;
	
	private NewsListListView newsListView;
	
	private String errorHint;
	
	public NewsListViewHandler(NewsCommonActivity activity, NewsListListView newsListView, String errorHint) {
		 this.activity = activity;
		 this.newsListView = newsListView;
		 this.errorHint = errorHint;
	}
	/*@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		try {
			switch (msg.what) {
			case ParamConst.MESSAGE_WHAT_SUCCESS:
				final List<View> items = (List<View>) msg.obj;
				ListAdapter adapter = new NewsListViewAdapter(items);
				newsListView.setAdapter(adapter);
				// ViewUtil.setListViewHeightBasedOnChildren(newsListView);
				break;
			case ParamConst.MESSAGE_WHAT_NO_DATA:
				activity.initNewsListData(newsListView, false, errorHint);
				break;
			case ParamConst.MESSAGE_WHAT_ERROR:
				String errorMsg = (String) msg.obj;
				activity.initNewsListData(newsListView, false, errorMsg);
				break;
			default:
				activity.initNewsListData(newsListView, false, "未知错误");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	@Override
	public void success(Message msg) {
		final List<View> items = (List<View>) msg.obj;
		ListAdapter adapter = new NewsListViewAdapter(items);
		newsListView.setAdapter(adapter);
	}
	@Override
	public void noData(Message msg) throws Exception {
		activity.initNewsListData(newsListView, false, errorHint);
	}
	@Override
	public void error(Message msg) throws Exception {
		String errorMsg = (String) msg.obj;
		activity.initNewsListData(newsListView, false, errorMsg);
	}
	@Override
	public void resultDefault(Message msg) throws Exception {
		activity.initNewsListData(newsListView, false, "未知错误");
	}
}
