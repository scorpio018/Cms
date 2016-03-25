package com.enorth.cms.widget.listview.newslist;

import com.enorth.cms.listener.newslist.news.NewsListListViewOnScrollListener;
import com.enorth.cms.widget.listview.CommonListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class NewsListListView extends CommonListView {

	public NewsListListView(Context context) {
		super(context);
	}
	
	public NewsListListView(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	public NewsListListView(Context context, AttributeSet attr, int defStyleAttr) {
		super(context, attr, defStyleAttr);
	}
	
	public NewsListListView(Context context, View view) {
		super(context);
	}
	
	/*@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}*/

	/*@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
	@Override
	public void onViewAdded(View child) {
		super.onViewAdded(child);
		int height = ViewUtil.refreshListViewItemHeight(child);
		child.measure(child.getMeasuredWidth(), height);
	}*/
}