package com.enorth.cms.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.view.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class ViewUtil {
	
	public static InputMethodManager inputMethodManager;
	
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
	
	/**
	 * 在频道搜索页进行频道搜索时使用，获取item对应的bean
	 * @param value
	 * @param context
	 * @return
	 */
	public static NewsListImageViewBasicBean getNewsListImageViewBasicBean(Map<NewsListImageViewBasicBean, List<String>> value, Context context) {
		Set<NewsListImageViewBasicBean> key = value.keySet();
		if (key.size() > 1) {
			Toast.makeText(context, "搜索数据有误，请联系管理员", Toast.LENGTH_SHORT).show();
			return null;
		}
		Object[] array = key.toArray();
		return (NewsListImageViewBasicBean) array[0];
	}
	
	/**
	 * 软键盘的显示和隐藏
	 * @param context
	 * @param v
	 * @param hasFocus
	 */
	public static void keyboardDo(Context context, View v, boolean hasFocus) {
        if (hasFocus) {//如果有焦点就显示软件盘
        	initInputMethodManager(context);
        } else {//否则就隐藏
    	   keyboardHidden(context, v);
       }
	}
	
	/**
	 * 初始化软键盘管理类
	 * @param context
	 */
	private static void initInputMethodManager(Context context) {
		if (inputMethodManager == null) {
			inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		}
	}
	
	/**
	 * 显示软键盘
	 * @param context
	 * @param v
	 */
	public static void keyboardShow(Context context, View v) {
		initInputMethodManager(context);
		inputMethodManager.showSoftInputFromInputMethod(v.getWindowToken() , 0);
	}
	/**
	 * 隐藏软键盘
	 * @param context
	 * @param v
	 */
	public static void keyboardHidden(Context context, View v) {
		initInputMethodManager(context);
		inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
}
