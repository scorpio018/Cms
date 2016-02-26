package com.enorth.cms.adapter.materialupload;

import java.util.List;

import com.enorth.cms.adapter.CommonListViewAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MaterialUploadFileTypeItemListViewAdapter extends ArrayAdapter<View>/*CommonListViewAdapter*/ {

	private List<View> items;
	
	public MaterialUploadFileTypeItemListViewAdapter(Context context, int resource) {
		super(context, resource);
	}
	
	public MaterialUploadFileTypeItemListViewAdapter(Context context, int resource, List<View> items) {
		super(context, resource, items);
		this.items = items;
	}

	/*public MaterialUploadFileTypeItemListViewAdapter(List<View> items) {
		super(items);
	}*/

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getItem(position);
//		return super.getView(position, convertView, parent);
	}
	
	@Override
	public View getItem(int position) {
		return items.get(position);
//		return super.getItem(position);
	}
}
