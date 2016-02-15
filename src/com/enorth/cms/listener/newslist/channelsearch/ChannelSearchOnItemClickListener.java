package com.enorth.cms.listener.newslist.channelsearch;

import java.util.List;
import java.util.Map;

import com.enorth.cms.adapter.news.SearchChannelFilterAdapter;
import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.listener.CommonOnItemClickListener;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.news.ChannelSearchActivity;

import android.view.View;
import android.widget.AdapterView;

public class ChannelSearchOnItemClickListener extends CommonOnItemClickListener {
	
	private ChannelSearchActivity activity;
	
	private SearchChannelFilterAdapter adapter;
	
	public ChannelSearchOnItemClickListener(ChannelSearchActivity activity, SearchChannelFilterAdapter adapter) {
		this.activity = activity;
		this.adapter = adapter;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Map<NewsListImageViewBasicBean, List<String>> values = adapter.objects.get(position);
		NewsListImageViewBasicBean bean = ViewUtil.getNewsListImageViewBasicBean(values, activity);
		if (bean == null) {
			return;
		}
		
	}

}
