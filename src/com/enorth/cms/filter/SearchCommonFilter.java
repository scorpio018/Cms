package com.enorth.cms.filter;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.adapter.SearchCommonFilterAdapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Filter;

public abstract class SearchCommonFilter<T> extends Filter {
	
	private List<T> objects;
	
	private Object lock;
	
	private List<T> originalValues;
	
	private SearchCommonFilterAdapter<T> adapter;
	
	protected Context context;
	
	public SearchCommonFilter(Context context, List<T> objects, Object lock, List<T> originalValues, SearchCommonFilterAdapter<T> adapter) {
		this.context = context;
		this.objects = objects;
		this.lock = lock;
		this.originalValues = originalValues;
		this.adapter = adapter;
	}
	
	/**
	 * 将用户输入的字符进行对比，并判断value是否匹配，如果匹配，则将value存入values中
	 * @param value
	 * @param values
	 */
	public abstract void filterTest(T value, List<T> values, CharSequence constraint);
	
	@Override
	protected FilterResults performFiltering(CharSequence constraint) {
		FilterResults results = new FilterResults();
		if (originalValues == null) {
			synchronized (lock) {
				originalValues = new ArrayList<T>(objects);
			}
		}
		int count = originalValues.size();
		List<T> values = new ArrayList<T>();
		for (int i = 0; i < count; i++) {
			T value = originalValues.get(i);
			filterTest(value, values, constraint);
		}
		results.values = values;
		results.count = count;
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
//		adapter.objects.clear();
		adapter.objects = (List<T>) results.values;
		adapter.doAfterFilterNewValues(adapter.objects);
		if (results.count > 0) {
			adapter.notifyDataSetChanged();
		} else {
			adapter.notifyDataSetInvalidated();
		}
	}

}
