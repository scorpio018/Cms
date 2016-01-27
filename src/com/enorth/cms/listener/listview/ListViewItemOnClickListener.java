package com.enorth.cms.listener.listview;

import com.enorth.cms.bean.news_list.NewsListListViewItemBasicBean;
import com.enorth.cms.listener.CommonOnClickListener;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class ListViewItemOnClickListener extends CommonOnClickListener {
	
	private NewsListListViewItemBasicBean bean;
	
	private Context context;
	
	public ListViewItemOnClickListener(Context context, NewsListListViewItemBasicBean bean) {
		this.context = context;
		this.bean = bean;
	}
	
	@Override
	public void onClick(View v) {
		Toast.makeText(context, "点击的新闻ID为【" + bean.getId() + "】", Toast.LENGTH_SHORT).show();
	}

}
