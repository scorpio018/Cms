package com.enorth.cms.handler.newslist;

import java.util.List;

import com.enorth.cms.adapter.news.NewsListViewAdapter;
import com.enorth.cms.bean.news_list.NewsListBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.handler.UrlRequestCommonHandler;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.view.news.NewsCommonActivity;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.os.Message;
import android.util.Log;
import android.view.View;
/**
 * 将接口中返回并组装好的List<View>加载到ListView中
 * @author yangyang
 *
 */
public class NewsListViewHandler extends UrlRequestCommonHandler {

	private NewsCommonActivity activity;
	
	private PullToRefreshListView newsListView;
	
	private String errorHint;
	
	public NewsListViewHandler(NewsCommonActivity activity, PullToRefreshListView newsListView, String errorHint) {
		 this.activity = activity;
		 this.newsListView = newsListView;
		 this.errorHint = errorHint;
	}
	
	@Override
	public void success(Message msg) {
		final List<NewsListBean> items = (List<NewsListBean>) msg.obj;
//		ListAdapter adapter = new NewsListViewAdapter(activity, 0, items);
		NewsListViewAdapter adapter = activity.getListViewAdapter().get(activity.getCurPosition());
		if (activity.getCurRefreshState() == ParamConst.REFRESHING) {
			adapter.setItems(items);
		} else if (activity.getCurRefreshState() == ParamConst.LOADING) {
			if (items.size() != 0) {
				adapter.getItems().addAll(items);
			} else {
				// 由于这一页没有数据，因此将当前页退回到上一页
				activity.getNewsListBean().getPage().setPageNo(activity.getNewsListBean().getPage().getPageNo() - 1);
			}
		} else {
			Log.e("NewsListViewHandler success", "activity.getCurRefreshState()【" + activity.getCurRefreshState() + "】");
		}
		activity.setCurRefreshState(ParamConst.INIT);
		newsListView.onRefreshComplete();
		newsListView.getRefreshableView().setSelection(newsListView.getCurFirstShowItemPosition());
		adapter.notifyDataSetChanged();
		if (adapter.getItems().size() == 0) {
			activity.getHintRelative().setVisibility(View.VISIBLE);
		} else {
			activity.getHintRelative().setVisibility(View.GONE);
		}
//		AnimUtil.hideRefreshFrame();
	}
	@Override
	public void noData(Message msg) throws Exception {
		activity.initNewsListData(newsListView, false, "没有找到数据 ");
		AnimUtil.hideRefreshFrame();
	}
	@Override
	public void error(Message msg) throws Exception {
		String errorMsg = (String) msg.obj;
		activity.initNewsListData(newsListView, false, errorMsg);
		AnimUtil.hideRefreshFrame();
	}
	@Override
	public void resultDefault(Message msg) throws Exception {
		activity.initNewsListData(newsListView, false, "未知错误");
		AnimUtil.hideRefreshFrame();
	}
}
