package com.enorth.cms.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.enorth.cms.filter.SearchCommonFilter;
import com.enorth.cms.filter.news.SearchChannelFilter;
import com.enorth.cms.view.R;

import android.content.Context;
import android.os.Handler;
import android.provider.Contacts.Intents.Insert;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public abstract class SearchCommonFilterAdapter<T> extends BaseAdapter implements Filterable {
	
	protected Context context;
	
	private List<T> originalValues;
	
	public List<T> objects;
	
	private Object lock = new Object();
	
	protected int resource;
	
	private int dropDownResource;
	
	protected SearchCommonFilter<T> filter;
	
	protected LayoutInflater inflater;
	/**
	 * 在getView时使用，放入全局变量是为了能够让继承该类的子类在getItem方法中能够获取该view
	 */
	protected View curView;
	
	public SearchCommonFilterAdapter(Context context, int textViewResourceId, List<T> objects) {
		init(context, textViewResourceId, objects);
	}
	
	private void init(Context context, int resource, List<T> objects) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.resource = dropDownResource = resource;
		this.objects = new ArrayList<T>(objects);
//		filter = new SearchCommonFilter(objects, lock, originalValues);
		/*new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
				initFilter(context, objects, lock, originalValues);
			};
		}.sendEmptyMessage(0);*/
		initFilter(context, objects, lock, originalValues);
	}
	
	public abstract void initFilter(Context context, List<T> objects, Object lock, List<T> originalValues);
	
	public abstract void doAfterFilterNewValues(List<T> values);

	public void add(T object) {
		if (originalValues != null) {
			synchronized (lock) {
				originalValues.add(object);
			}
		} else {
			objects.add(object);
		}
	}
	
	public void Insert(T object, int index) {
		if (originalValues != null) {
			synchronized (lock) {
				originalValues.add(index, object);
			}
		} else {
			objects.add(index, object);
		}
	}
	
	public void remove(T object) {
		if (originalValues != null) {
			synchronized (lock) {
				originalValues.remove(object);
			}
		} else {
			objects.remove(object);
		}
	}
	
	public void clear() {
		if (originalValues != null) {
			synchronized (lock) {
				originalValues.clear();
			}
		} else {
			objects.clear();
		}
	}
	
	public Context getContext() {
		return context;
	}
	
	@Override
	public int getCount() {
		return objects.size();
	}

	/*@Override
	public T getItem(int position) {
		return objects.get(position);
	}*/

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, resource);
	}
	
	/**
	 * 通过resourceId获取一个View（如果之前生成过，则直接拿到view即可）。目前只支持TextView，如果想做成复杂的页面，可以重构此方法
	 * @param position
	 * @param convertView
	 * @param parent
	 * @param resource
	 * @return
	 */
	public abstract View createViewFromResource(int position, View convertView, ViewGroup parent, int resource);

	@Override
	public Filter getFilter() {
		return filter;
	}
	
	public void setDropDownResouce(int resource) {
		this.dropDownResource = resource;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, dropDownResource);
	}

}
