package com.enorth.cms.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.enorth.cms.bean.ButtonColorBasicBean;
import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.common.EnableSimpleChangeButton;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.menu.LeftMenuCommonOnClickListener;
import com.enorth.cms.listener.newslist.newstypebtn.NewsTypeBtnOnClickListener;
import com.enorth.cms.view.LeftHorizontalScrollMenu;
import com.enorth.cms.view.R;
import com.enorth.cms.view.news.NewsSearchActivity;
import com.enorth.cms.widget.listview.newslist.NewsListListView;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewUtil {
	
	public static InputMethodManager inputMethodManager;
	/**
	 * 根据ListView里面的item的高度和设置ListView的总高度
	 * @param listView
	 */
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
	 * 初始化平行多个按钮的按钮布局，并设置默认选中和未选中的颜色
	 * @param activity
	 * @param layout
	 * @param btnText
	 * @param btnId
	 * @throws Exception
	 */
	public static List<EnableSimpleChangeButton> initBtnGroupLayout(Activity activity, LinearLayout layout, String[] btnText, String[] btnId, float percentWeight) throws Exception {
//		RelativeLayout newsTypeBtnRelaLayout = (RelativeLayout) activity.findViewById(R.id.newsTypeBtnRelaLayout);
//		newsTypeBtnLineLayout = (LinearLayout) newsTypeBtnRelaLayout.getChildAt(0);
		int checkedColor = ColorUtil.getCommonBlueColor(activity);
		int unCheckedColor = ColorUtil.getWhiteColor(activity);
		return initBtnGroupLayout(activity, layout, btnText, btnId, percentWeight, checkedColor, unCheckedColor);
	}
	
	/**
	 * 初始化平行多个按钮的按钮布局
	 * @param activity
	 * @param layout
	 * @param btnText
	 * @param btnId
	 * @param checkedColor
	 * @param unCheckedColor
	 * @throws Exception
	 */
	public static List<EnableSimpleChangeButton> initBtnGroupLayout(Activity activity, LinearLayout layout, String[] btnText, String[] btnId, float percentWeight, int checkedColor, int unCheckedColor) throws Exception {
		int length = btnText.length;
		// 此处初始化按钮的基本样式
		LinearLayout.LayoutParams params = LayoutParamsUtil.initPercentWeight(percentWeight);
		List<EnableSimpleChangeButton> btns = new ArrayList<EnableSimpleChangeButton>();
		for (int i = 0; i < length; i++) {
			EnableSimpleChangeButton btn = new EnableSimpleChangeButton(activity);
			btn.setButtonId(btnId[i]);
			if (i == 0) {
				btn.needRaduisPosition(false, false, false, true);
			} else if (i == length - 1) {
				btn.needRaduisPosition(false, true, false, false);
			} else {
				btn.needRaduisPosition(false, false, false, false);
			}
			btn.setText(btnText[i]);
			ButtonColorBasicBean colorBasicBean = new ButtonColorBasicBean(activity);
			boolean needFocused = i == 0 ? true : false;
			initBtnGroupStyleByFocusedState(colorBasicBean, btn, needFocused, checkedColor, unCheckedColor);
			btn.setColorBasicBean(colorBasicBean);
			final int position = i;
			// 加点击事件，切换到相应的ListView中
			NewsTypeBtnOnClickListener listener = new NewsTypeBtnOnClickListener(activity, layout, position);
			btn.setOnClickListener(listener);
			btns.add(btn);
			layout.addView(btn, params);
		}
		return btns;
	}
	
	/**
	 * 如果是选中的话，则将背景设置为选中颜色，字体设置为未选中颜色，反之相反
	 * @param colorBasicBean
	 * @param needFocused
	 * @param checkedColor
	 * @param unCheckedColor
	 */
	public static void initBtnGroupStyleByFocusedState(ButtonColorBasicBean colorBasicBean, EnableSimpleChangeButton btn, boolean needFocused, int checkedColor, int unCheckedColor) {
		if (needFocused) {
			// 需要选中
			colorBasicBean.setmBgNormalColor(checkedColor);
			colorBasicBean.setmTextNormalColor(unCheckedColor);
			btn.setChecked(true);
		} else {
			colorBasicBean.setmBgNormalColor(unCheckedColor);
			colorBasicBean.setmTextNormalColor(checkedColor);
			btn.setChecked(false);
		}
	}
	
	/**
	 * 根据当前选中的标头按钮的位置改变需要改变样式的按钮
	 * 
	 * @param position
	 * @throws Exception
	 */
	public static void changeBtnGroupStyleByFocusedState(Activity activity, LinearLayout layout, int position, int checkedColor, int unCheckedColor) throws Exception {
		int childCount = layout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			EnableSimpleChangeButton btn = (EnableSimpleChangeButton) layout.getChildAt(i);
			ButtonColorBasicBean colorBasicBean = new ButtonColorBasicBean(activity);
			if (i == position) {
				initBtnGroupStyleByFocusedState(colorBasicBean, btn, true, checkedColor, unCheckedColor);
			} else {
				btn.setChecked(false);
//				initBtnGroupStyleByFocusedState(colorBasicBean, false, unCheckedColor, checkedColor);
			}
			btn.setColorBasicBean(colorBasicBean);
		}
	}
	
	/**
	 * 在按钮组总找到当前选中的按钮并把id进行返回
	 * @param btns
	 * @return
	 * @throws Exception
	 */
	public static String getCurCheckedBtnGroupId(List<EnableSimpleChangeButton> btns) throws Exception {
		int size = btns.size();
		for (int i = 0; i < size; i++) {
			EnableSimpleChangeButton btn = btns.get(i);
			if (btn.isChecked()) {
				return btn.getButtonId();
			}
		}
		return null;
	}
	
	/**
	 * 将所需参数返回给上一个activity
	 * @param activity
	 * @param bundle
	 */
	public static void takeParamsBackToPrevActivity(Activity activity, Bundle bundle, int resultCode) {
		Intent intent = new Intent();
		/*Bundle values = null;
		try {
			values = getValues();
		} catch (Exception e) {
			Toast.makeText(NewsSearchActivity.this, "在NewsSearchActivity进行返回时发生错误：【" + e.getMessage() + "】", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}*/
		intent.putExtras(bundle);
		activity.setResult(resultCode, intent);
		activity.finish();
	}
	
	/**
	 * 将传入的控件的宽度按照百分比进行设置（没用上。。。）
	 * @param view
	 * @param percentWeight
	 */
	public static void initViewByWeight(View view, float percentWeight) {
		LinearLayout.LayoutParams params = LayoutParamsUtil.initPercentWeight(percentWeight);
		view.setLayoutParams(params);
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
	 * 当activity需要左侧导航栏时调用此处
	 * @param activity 当前activity
	 * @param layoutId 当前activity使用的layout的xml文件
	 */
	public static void setContentViewForMenu(Activity activity, int layoutId) {
		// 首先将当前activity与带左侧导航栏的xml文件进行绑定
		activity.setContentView(R.layout.activity_main);
		// 然后将当前activity对应的真正的xml文件存入activity_main中的一个id为menuAndContentLayout的LinearLayout中
		View view = activity.getLayoutInflater().inflate(layoutId, null);
		LinearLayout menuAndContentLayout = (LinearLayout) activity.findViewById(R.id.menuAndContentLayout);
		menuAndContentLayout.addView(view);
	}
	
	/**
	 * 给传入的View添加显示菜单事件
	 * @param view
	 */
	public static void initMenuEvent(Activity activity, View view) {
		// 获取菜单控件
		LeftHorizontalScrollMenu menu = (LeftHorizontalScrollMenu) activity.findViewById(R.id.leftMenu);
//		ImageView menuBtn = (ImageView) activity.findViewById(R.id.newsTitleMenuBtn);
//		menuBtn.setBackgroundResource(R.drawable.news_menu);
		// 给传入的View添加显示/隐藏菜单事件
		LeftMenuCommonOnClickListener listener = new LeftMenuCommonOnClickListener(menu);
		view.setOnClickListener(listener);
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
