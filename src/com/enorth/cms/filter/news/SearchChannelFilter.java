package com.enorth.cms.filter.news;

import java.util.List;
import java.util.Map;

import com.enorth.cms.adapter.SearchCommonFilterAdapter;
import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.filter.SearchCommonFilter;
import com.enorth.cms.utils.ViewUtil;

import android.content.Context;

public class SearchChannelFilter extends SearchCommonFilter<Map<NewsListImageViewBasicBean, List<String>>> {

	public SearchChannelFilter(Context context, List<Map<NewsListImageViewBasicBean, List<String>>> objects, Object lock, List<Map<NewsListImageViewBasicBean, List<String>>> originalValues,
			SearchCommonFilterAdapter<Map<NewsListImageViewBasicBean, List<String>>> adapter) {
		super(context, objects, lock, originalValues, adapter);
	}

	@Override
	public void filterTest(Map<NewsListImageViewBasicBean, List<String>> value, List<Map<NewsListImageViewBasicBean, List<String>>> values, CharSequence constraint) {
		if (constraint == null) {
			return;
		}
		NewsListImageViewBasicBean bean = ViewUtil.getNewsListImageViewBasicBean(value, context);
		if (bean == null) {
			return;
		}
		List<String> shortNames = value.get(bean);
		for (String shortName : shortNames) {
			if (shortName.contains(constraint)) {
				values.add(value);
				break;
			}
		}
		if (bean.getName().contains(constraint)) {
			values.add(value);
		}
	}

}
