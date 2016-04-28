package com.enorth.cms.filter.news;

import java.util.List;
import java.util.Locale;

import com.enorth.cms.adapter.SearchCommonFilterAdapter;
import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.filter.SearchCommonFilter;

import android.content.Context;

public class SearchChannelFilter extends SearchCommonFilter<ChannelBean> {

	public SearchChannelFilter(Context context, List<ChannelBean> objects, Object lock, List<ChannelBean> originalValues,
			SearchCommonFilterAdapter<ChannelBean> adapter) {
		super(context, objects, lock, originalValues, adapter);
	}

	@Override
	public void filterTest(ChannelBean value, List<ChannelBean> values, CharSequence constraint) {
		if (value.getChannelName().contains(constraint) || value.getSimpleName().toUpperCase(Locale.CHINA).contains(constraint.toString().toUpperCase(Locale.CHINA))) {
			values.add(value);
		}
		/*NewsListImageViewBasicBean bean = ViewUtil.getNewsListImageViewBasicBean(value, context);
		if (bean == null) {
			return;
		}*/
		/*List<String> shortNames = value.get(bean);
		for (String shortName : shortNames) {
			if (shortName.contains(constraint)) {
				values.add(value);
				break;
			}
		}
		if (bean.getName().contains(constraint)) {
			values.add(value);
		}*/
	}

}
