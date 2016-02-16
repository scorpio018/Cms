package com.enorth.cms.handler.newslist;

import java.util.List;

import com.enorth.cms.adapter.CommonListViewAdapter;
import com.enorth.cms.adapter.news.SearchChannelFilterAdapter;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.ViewUtil;
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
				CommonListViewAdapter listViewAdapter = new CommonListViewAdapter(items);
				activity.channelListView.setAdapter(listViewAdapter);
				// 将频道的拼音连同对应的bean存入自定义的adapter中，配合AutoCompleteTextView使用
				SearchChannelFilterAdapter searchChannelFilterAdapter = new SearchChannelFilterAdapter(
						activity, R.layout.channel_search_item/*channel_search_edit_item*/, activity.shortNames);
				activity.searchChannelET.setAdapter(searchChannelFilterAdapter);
				activity.searchChannelET.setText("");
				ViewUtil.keyboardHidden(activity, activity.searchChannelET);
				// 将焦点定位到搜索栏中
				activity.setCurChannelListEnableView(ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY);
				// 搜索框focus状态改变时加载监听器，并将focus状态设置成false（注解原因：false会使搜索框无法获取焦点）
				/*activity.searchChannelET.setOnFocusChangeListener(new ChannelSearchEditOnFocusChangeListener(activity));
				if (activity.searchChannelET.isFocusable()) {
					activity.searchChannelET.setFocusable(false);
					activity.searchChannelET.setFocusableInTouchMode(false);
				}*/
				// 给搜索框弹出的ListView添加点击事件（注释原因：不满足需求，当前需求为：点击左侧勾选图标则进行勾选操作，点击非左侧则判断是否有下一级，如果有，则进入下一级频道；没有则将左侧勾选图标进行选中）
//				activity.searchChannelET.setOnItemClickListener(new ChannelSearchOnItemClickListener(activity, searchChannelFilterAdapter));
				activity.searchChannelFilterAdapter = searchChannelFilterAdapter;
				// 根据item的总高度设置ListView的高度（注释原因：无效）
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
