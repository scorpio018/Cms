package com.enorth.cms.adapter.news;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ChannelSearchArrayAdapter extends ArrayAdapter<List<String>> {
	
	private List<List<String>> result;

	public ChannelSearchArrayAdapter(Context context, int resource) {
		super(context, resource);
	}
	
	public ChannelSearchArrayAdapter(Context context, int resource, List<List<String>> result) {
		super(context, resource);
		this.result = result;
	}

	@Override
	public List<String> getItem(int position) {
		
		return super.getItem(position);
	}
}
