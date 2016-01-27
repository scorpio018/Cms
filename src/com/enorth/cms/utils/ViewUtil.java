package com.enorth.cms.utils;

import com.enorth.cms.activity.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ViewUtil {
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}
	
	/**
	 * 获取ListView中当前传入的item的实际高度
	 * @param view
	 * @return
	 */
	public static int refreshListViewItemHeight(View view) {
		int heightMeasureSpec = 0;
		if (view != null) {
			if (view instanceof LinearLayout) {
				// 获取左侧的选中图标
				ImageView checkBtn = (ImageView) view.findViewById(R.id.iv_check_btn);
				int checkBtnHeight = checkBtn.getMeasuredHeight();
				TextView title = (TextView) view.findViewById(R.id.tv_news_title);
				int titleHeight = title.getMeasuredHeight();
				TextView authorName = (TextView) view.findViewById(R.id.tv_news_time);
				int authorNameHeight = authorName.getMeasuredHeight();
				if (checkBtnHeight > titleHeight + authorNameHeight) {
					heightMeasureSpec = checkBtnHeight;
				} else {
					heightMeasureSpec = titleHeight + authorNameHeight;
				}
			}
		}
		return heightMeasureSpec;
	}
}
