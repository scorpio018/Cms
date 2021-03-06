package com.enorth.cms.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.enorth.cms.adapter.materialupload.MaterialUploadFileItemGridViewAdapter;
import com.enorth.cms.bean.ViewColorBasicBean;
import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.common.EnableSimpleChangeButton;
import com.enorth.cms.listener.menu.LeftMenuCommonOnClickListener;
import com.enorth.cms.listener.newslist.newstypebtn.NewsTypeBtnOnClickListener;
import com.enorth.cms.view.LeftHorizontalScrollMenu;
import com.enorth.cms.view.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.GridView;
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
	@Deprecated
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
				CheckBox checkBtn = (CheckBox) view.findViewById(R.id.iv_check_btn);
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
	public static List<EnableSimpleChangeButton> initBtnGroupLayout(Activity activity, LinearLayout layout, int checkedColor, String[] btnText, int[] btnId, float percentWeight) {
//		RelativeLayout newsTypeBtnRelaLayout = (RelativeLayout) activity.findViewById(R.id.newsTypeBtnRelaLayout);
//		newsTypeBtnLineLayout = (LinearLayout) newsTypeBtnRelaLayout.getChildAt(0);
//		int checkedColor = ColorUtil.getCommonBlueColor(activity);
		int unCheckedColor = ColorUtil.getWhiteColor(activity);
		return initBtnGroupLayout(activity, layout, btnText, btnId, percentWeight, checkedColor, unCheckedColor);
	}
	
	/**
	 * 初始化平行多个按钮的按钮布局（按照百分比定义宽度）
	 * @param activity
	 * @param layout
	 * @param btnText
	 * @param btnId
	 * @param checkedColor
	 * @param unCheckedColor
	 * @throws Exception
	 */
	public static List<EnableSimpleChangeButton> initBtnGroupLayout(Activity activity, LinearLayout layout, String[] btnText, int[] btnId, float percentWeight, int checkedColor, int unCheckedColor) {
		// 此处初始化按钮的基本样式
		LinearLayout.LayoutParams params = LayoutParamsUtil.initLineWidthPercentWeight(percentWeight);
		return initBtnGroupLayoutCommon(activity, layout, btnText, btnId, checkedColor, unCheckedColor, params);
	}
	
	/**
	 * 初始化平行多个按钮的按钮布局（自定义宽高）
	 * @param activity
	 * @param layout
	 * @param btnText
	 * @param btnId
	 * @param width
	 * @param height
	 * @param checkedColor
	 * @param unCheckedColor
	 * @return
	 */
	public static List<EnableSimpleChangeButton> initStaticWidthBtnGroupLayout(Activity activity, LinearLayout layout, String[] btnText, int[] btnId, int width, int checkedColor, int unCheckedColor) {
		// 此处初始化按钮的基本样式
		LinearLayout.LayoutParams params = LayoutParamsUtil.initLineWidth(width);
		return initBtnGroupLayoutCommon(activity, layout, btnText, btnId, checkedColor, unCheckedColor, params);
	}
	
	public static List<EnableSimpleChangeButton> initBtnGroupLayoutCommon(Activity activity, LinearLayout layout, String[] btnText, int[] btnId, int checkedColor, int unCheckedColor, LinearLayout.LayoutParams params) {
		int length = btnText.length;
		List<EnableSimpleChangeButton> btns = new ArrayList<EnableSimpleChangeButton>();
		for (int i = 0; i < length; i++) {
			EnableSimpleChangeButton btn = new EnableSimpleChangeButton(activity);
			btn.setButtonId(btnId[i]);
			if (i == 0) {
				btn.needRadiusPosition(false, false, false, true);
			} else if (i == length - 1) {
				btn.needRadiusPosition(false, true, false, false);
			} else {
				btn.needRadiusPosition(false, false, false, false);
			}
			btn.setText(btnText[i]);
			ViewColorBasicBean colorBasicBean = new ViewColorBasicBean(activity);
			boolean needFocused = i == 0 ? true : false;
			initBtnGroupStyleByFocusedState(colorBasicBean, btn, needFocused, checkedColor, unCheckedColor);
			btn.setColorBasicBean(colorBasicBean);
			int position = i;
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
	/*public static void initBtnGroupStyleByFocusedState(ViewColorBasicBean colorBasicBean, EnableSimpleChangeButton btn, int checkedColor, int unCheckedColor) {
		// 设置边框颜色
		colorBasicBean.setStrokeColor(checkedColor);
		colorBasicBean.setmBgNormalColor(unCheckedColor);
		colorBasicBean.setmBgFocusedColor(checkedColor);
		colorBasicBean.setmBgPressedColor(checkedColor);
		colorBasicBean.setmTextNormalColor(checkedColor);
		colorBasicBean.setmTextFocusedColor(unCheckedColor);
		colorBasicBean.setmTextPressedColor(unCheckedColor);
		btn.setColorBasicBean(colorBasicBean);
	}*/
	public static void initBtnGroupStyleByFocusedState(ViewColorBasicBean colorBasicBean, EnableSimpleChangeButton btn, boolean needFocused, int checkedColor, int unCheckedColor) {
		// 设置边框颜色
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
		btn.setColorBasicBean(colorBasicBean);
	}
	
	/*public static void changeBtnState(ViewColorBasicBean colorBasicBean, EnableSimpleChangeButton btn, boolean needFocused, int checkedColor, int unCheckedColor) {
		if (needFocused) {
			// 需要选中
//			initBtnGroupStyleByFocusedState(colorBasicBean, btn, checkedColor, unCheckedColor);
			btn.setPressed(true);
			btn.setChecked(true);
			btn.setFocusable(true);
		} else {
//			initBtnGroupStyleByFocusedState(colorBasicBean, btn, checkedColor, unCheckedColor);
			btn.setPressed(false);
			btn.setChecked(false);
			btn.setFocusable(false);
		}
	}*/
	
	/**
	 * 根据当前选中的标头按钮的位置改变需要改变样式的按钮
	 * 
	 * @param position
	 * @throws Exception
	 */
	public static void changeBtnGroupStyleByFocusedState(Activity activity, LinearLayout layout, int position, int checkedColor, int unCheckedColor) {
		int childCount = layout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			EnableSimpleChangeButton btn = (EnableSimpleChangeButton) layout.getChildAt(i);
//			ViewColorBasicBean colorBasicBean = new ViewColorBasicBean(activity);
			ViewColorBasicBean colorBasicBean = btn.getColorBasicBean();
			if (i == position) {
				initBtnGroupStyleByFocusedState(colorBasicBean, btn, true, checkedColor, unCheckedColor);
			} else {
				initBtnGroupStyleByFocusedState(colorBasicBean, btn, false, checkedColor, unCheckedColor);
			}
//			btn.setColorBasicBean(colorBasicBean);
		}
	}
	
	/**
	 * 在按钮组总找到当前选中的按钮并把id进行返回
	 * @param btns
	 * @return
	 * @throws Exception
	 */
	public static int getCurCheckedBtnGroupId(List<EnableSimpleChangeButton> btns) {
		int size = btns.size();
		for (int i = 0; i < size; i++) {
			EnableSimpleChangeButton btn = btns.get(i);
			if (btn.isChecked() || btn.isFocused()) {
				return btn.getButtonId();
			}
		}
		Log.e("ViewUtil.getCurCheckedBtnGroupId", "在按钮组中没有找到对应的类别ID");
		return -9999;
	}
	
	/**
	 * 将传入的控件的宽度按照百分比进行设置（没用上。。。）
	 * @param view
	 * @param percentWeight
	 */
	public static void initViewByWeight(View view, float percentWeight) {
		LinearLayout.LayoutParams params = LayoutParamsUtil.initLineWidthPercentWeight(percentWeight);
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
	
	/**
	 * Android动态设定GridView的高度，固定column，实现高度自适应
	 * @param gridView
	 */
	public static void setGridViewHeightBasedOnChildren(GridView gridView, int column) {
		// 获取listview的adapter
		MaterialUploadFileItemGridViewAdapter adapter = (MaterialUploadFileItemGridViewAdapter) gridView.getAdapter();
		if (adapter == null) {
			return;
		}
		// 固定列宽，有多少列
//		int col = 4;// listView.getNumColumns();
		int totalHeight = 0;
		// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
		// listAdapter.getCount()小于等于8时计算两次高度相加
		int count = adapter.getCount();
		for (int i = 0; i < count; i += column) {
			// 获取listview的每一个item
			View listItem = adapter.getView(i, null, gridView);
			listItem.measure(0, 0);
			// 获取item的高度和
			totalHeight += listItem.getMeasuredHeight();
		}

		// 获取listview的布局参数
		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		// 设置高度
		params.height = totalHeight;
		// 设置margin
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		// 设置参数
		gridView.setLayoutParams(params);
	}
	
	/*public static List<View> initDefaultData(Context context, String text, int height) {
		List<View> resultView = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(context);
		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.news_list_view_default_item, null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, height);
		layout.setLayoutParams(params);
		if (text != null) {
			TextView textView = (TextView) layout.getChildAt(0);
			textView.setText(text);
		}
		resultView.add(layout);
		return resultView;
	}*/
	
	/**
	 * 初始化带有左侧菜单键的标头，titleName为标头的文字
	 * @param activity
	 * @param titleName
	 */
	public static void initMenuTitle(Activity activity, String titleName) {
		// 初始化左侧菜单键
		ImageView menuBtn = (ImageView) activity.findViewById(R.id.titleLeftIV);
		menuBtn.setBackgroundResource(R.drawable.news_menu);
		initMenuEvent(activity, menuBtn);
		//　初始化标头
		if (StringUtil.isNotEmpty(titleName)) {
			TextView materialUploadTitleText = (TextView) activity.findViewById(R.id.titleMiddleTV);
			materialUploadTitleText.setText(titleName);
		}
	}
	
	/**
	 * 弹出错误信息并将要提示的信息加入此弹出框中
	 * @param context
	 * @param errorHint
	 */
	public static void showAlertDialog(Context context, String errorHint) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View alertView = inflater.inflate(R.layout.common_alert_error_msg, null);
		TextView errorHintTV = (TextView) alertView.findViewById(R.id.errorHint);
		errorHintTV.setText(errorHint);
		new AlertDialog.Builder(context).setTitle("提示").setView(alertView).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		}).show();
	}
	
	@SuppressLint("NewApi")
	public static void initClickBg(View view, int color) {
		StateListDrawable stateListDrawable = new StateListDrawable();
		stateListDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, new ColorDrawable(color));
		stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, new ColorDrawable(0));
//		view.setBackground(stateListDrawable);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			view.setBackground(stateListDrawable);
		} else {
			view.setBackgroundDrawable(stateListDrawable);
		}
	}
	
	/**
	 * 如果弹出框处于激活状态，则将弹出框销毁，反之则实例化弹出页面
	 * @param curChooseChannelName
	 */
	/*public static void getChooseChannelPopupWindow(Activity activity, PopupWindow popupWindow, String curChooseChannelName) {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			popupWindow = null;
		} else {
			initChooseChannelPopupWindow(curChooseChannelName);
		}
	}
	
	*//**
	 * 实例化频道选择弹出页面
	 * @param curChooseChannelName
	 *//*
	private static void initChooseChannelPopupWindow(Activity activity, PopupWindow popupWindow, PopupWindowBean bean, String curChooseChannelName) {
		final LinearLayout layout = new LinearLayout(activity);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundColor(bean.getPopupColor());
		// 设置半透明
		layout.getBackground().setAlpha(200);
//		LayoutInflater inflater = LayoutInflater.from(thisActivity);
//		int size = allChooseChannelName.size();
		int size = bean.getItems().size();
		int touchSlop = ScreenTools.getTouchSlop(activity);
		for (int i = 0; i < size; i++) {
			new PopupWindowOnTouchListener(activity, curChooseChannelName, i, layout, ScreenTools.getTouchSlop(activity));
		}
		popupWindow = new PopupWindow(layout, ParamConst.POP_WINDOW_COMMON_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		// 为了让popupWindow能够做到点击其他位置可以消失，需要加入如下代码
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		
		popupWindow.setAnimationStyle(R.style.AnimationFade);
		int titleHeight = (int)Math.round(activity.getResources().getDimension(R.dimen.news_title_height));
		int xoffInPixels = ParamConst.POP_WINDOW_COMMON_WIDTH / 2;
	    // 将pixels转为dip
		int xoffInDip = ScreenTools.px2dip(xoffInPixels, activity);
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			popupWindow.showAtLocation(channelSearchTitleLayout, Gravity.TOP, 0, titleHeight);
		} else {
			popupWindow.showAsDropDown(channelSearchTitleLayout, -xoffInDip, 0);
		}
		popupWindow.update();
	}*/
}
