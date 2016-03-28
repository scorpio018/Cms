package com.enorth.cms.filter.login;

import java.util.List;

import com.enorth.cms.adapter.SearchCommonFilterAdapter;
import com.enorth.cms.filter.SearchCommonFilter;

import android.content.Context;

public class UserNameFilter extends SearchCommonFilter<String> {
	
	public UserNameFilter(Context context, List<String> objects, Object lock, List<String> originalValues,
			SearchCommonFilterAdapter<String> adapter) {
		super(context, objects, lock, originalValues, adapter);
	}

	@Override
	public void filterTest(String value, List<String> values, CharSequence constraint) {
		if (value.equals(constraint)) {
			values.add(value);
		}
	}

}
