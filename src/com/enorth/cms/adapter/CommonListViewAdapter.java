package com.enorth.cms.adapter;

import java.util.List;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CommonListViewAdapter extends BaseAdapter {

	private List<View> items;
	
	public CommonListViewAdapter(List<View> items) {
		this.items = items;
	}
	

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public View getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getId();
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = items.get(position);
//		int height = ViewUtil.refreshListViewItemHeight(view);
//		view.measure(view.getMeasuredWidth(), height);
		return view;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	/**
	 * 表示当前有几种样式
	 */
	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return items == null ? true : items.size() == 0 ? true : false;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return items.get(position).isEnabled();
	}

	public List<View> getItems() {
		return items;
	}

	public void setItems(List<View> items) {
		this.items = items;
	}
	
	
}
