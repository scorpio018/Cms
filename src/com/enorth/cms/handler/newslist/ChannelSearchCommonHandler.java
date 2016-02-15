package com.enorth.cms.handler.newslist;

import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.enorth.cms.adapter.CommonListViewAdapter;
import com.enorth.cms.adapter.SearchCommonFilterAdapter;
import com.enorth.cms.adapter.news.SearchChannelFilterAdapter;
import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.news.ChannelSearchActivity;

import android.os.Handler;
import android.os.Message;
import android.view.View;

public abstract class ChannelSearchCommonHandler extends Handler {
	
	protected ChannelSearchActivity activity;
	
	public ChannelSearchCommonHandler(ChannelSearchActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		try {
			switch (msg.what) {
			case ParamConst.MESSAGE_WHAT_SUCCESS:
				final List<View> items = (List<View>) msg.obj;
				CommonListViewAdapter adapter = new CommonListViewAdapter(items);
				activity.channelListView.setAdapter(adapter);
				// 将频道的拼音连同对应的bean存入自定义的adapter中，配合AutoCompleteTextView使用
				SearchCommonFilterAdapter<Map<NewsListImageViewBasicBean, List<String>>> channelSearchFilterAdapter = new SearchChannelFilterAdapter(
						activity, R.layout.channel_search_item/*channel_search_edit_item*/, activity.shortNames);
				activity.searchChannelET.setAdapter(channelSearchFilterAdapter);
//				ViewUtil.setListViewHeightBasedOnChildren(newsListView);
			break;
			case ParamConst.MESSAGE_WHAT_NO_DATA:
				activity.initDefaultData("没有数据");
			break;
			case ParamConst.MESSAGE_WHAT_ERROR:
				String errorMsg = (String) msg.obj;
				activity.initDefaultData(errorMsg);
			break;
			default:
				activity.initDefaultData("未知错误");
			break;
			}
			if (needResetContent()) {
				activity.resetCurChannelSearchCheckedText();
			}
			// 每次重新选择一个频道后都要进行清空，除非是第一次进入（第一次进入，需要传入接口的是父ID，然后将当前频道进行选中。并将当前频道进行存入）
			if (activity.isFirstEnter) {
				String curChooseChannelType = SharedPreUtil.getString(activity, ParamConst.CUR_CHOOSE_CHANNEL_TYPE);
				if (curChooseChannelType.equals(ParamConst.ALL_CHANNEL)) {
					activity.isFirstEnter = false;
				}
			} else {
				activity.curCheckChannelId = -1L;
				activity.curCheckChannelName = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			AnimUtil.hideRefreshFrame(activity);
		}
	}
	
	public abstract boolean needResetContent();
}
