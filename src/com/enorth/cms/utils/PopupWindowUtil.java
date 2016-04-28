package com.enorth.cms.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.enorth.cms.bean.newstitle.NewsTitleBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.view.R;
import com.enorth.cms.widget.popupwindow.CommonPopupWindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class PopupWindowUtil {
	
	private CommonPopupWindow popupWindow;
	
	private Context context;
	
	private LinearLayout layout;
	/**
	 * 弹出框的背景颜色（初始化时赋值）
	 */
	private int popupBgColor;
	/**
	 * 弹出框点击时的颜色
	 */
	private int popupBgTouchColor;
	/**
	 * 文字颜色
	 */
	private int textColor;
	
//	private int animationStyle;
	
	private int width;
	/**
	 * 使用showAsDropDown时的x坐标值
	 */
	private int xoffInPixels;
	/**
	 * 使用showAsDropDown时的x坐标值
	 */
	private int yoffInPixels;
	
	private int gravity;
	
	private int y;
	/**
	 * 表示已此view为原点进行popupWindow的弹出
	 */
	private View view;
	
	private int popupWindowShowType = ParamConst.POPUP_WINDOW_SHOW_TYPE_AS_DROPDOWN;
	
	public abstract void initItems(LinearLayout layout);
	
	public PopupWindowUtil(Context context, View view) {
		this.context = context;
		this.view = view;
		init();
	}
	
	private void init() {
//		popupBgColor = R.color.channel_popup_color;
		popupBgColor = ColorUtil.getChannelPopupColor(context);
//		popupBgTouchColor = R.color.bottom_text_color_green;
		popupBgTouchColor = ColorUtil.getBottomTextColorGreen(context);
		textColor = ColorUtil.getWhiteColor(context);
//		popupBgColor = ContextCompat.getColor(context, R.color.channel_popup_color);
//		animationStyle = R.style.AnimationFadeUpToBottom;
		width = ParamConst.POP_WINDOW_COMMON_WIDTH;
		gravity = Gravity.TOP|Gravity.CENTER_HORIZONTAL;
		y = 0;
	}
	
	public CommonPopupWindow initPopupWindow() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			popupWindow = null;
		} else {
			switch(popupWindowShowType) {
			case ParamConst.POPUP_WINDOW_SHOW_TYPE_AT_LOCATION:
				initPopupWindowShowAtLocation();
				break;
			case ParamConst.POPUP_WINDOW_SHOW_TYPE_AS_DROPDOWN:
				initPopupWindowShowAsDropDown();
				break;
			}
		}
		return popupWindow;
	}
	
	private void initPopupWindowCommon() {
		layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundColor(popupBgColor);
		// 设置半透明
		layout.getBackground().setAlpha(200);
		initItems(layout);
		popupWindow = new CommonPopupWindow(layout, width, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		// 为了让popupWindow能够做到点击其他位置可以消失，需要加入如下代码
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
	}
	
	/**
	 * 实例化频道选择弹出页面
	 * @param curChooseChannelName
	 */
	private void initPopupWindowShowAtLocation() {
		initPopupWindowCommon();
		
//		popupWindow.setAnimationStyle(animationStyle);
//		int xoffInPixels = width / 2;
	    // 将pixels转为dip
//		int xoffInDip = ScreenTools.px2dip(xoffInPixels, activity);
//		int sdk = android.os.Build.VERSION.SDK_INT;
		popupWindow.showAtLocation(view, gravity, 0, y);
		/*if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			popupWindow.showAtLocation(view, gravity, 0, y);
		} else {
			popupWindow.showAsDropDown(view, -xoffInDip, 0);
		}*/
		popupWindow.update();
	}
	
	private void initPopupWindowShowAsDropDown() {
		initPopupWindowCommon();
	    // 将pixels转为dip
		int xoffInDip = ScreenTools.px2dip(xoffInPixels, context);
		int yoffInDip = 0;
		if (yoffInPixels != 0) {
			layout.measure(MeasureSpec.makeMeasureSpec(context.getResources()
					.getDisplayMetrics().widthPixels, MeasureSpec.AT_MOST),
					MeasureSpec.makeMeasureSpec(context.getResources()
							.getDisplayMetrics().heightPixels,
							MeasureSpec.AT_MOST));
			int layoutHeight = layout.getMeasuredHeight();
			yoffInDip = ScreenTools.px2dip(yoffInPixels, context) + layoutHeight;
		}
		popupWindow.showAsDropDown(view, xoffInDip, -yoffInDip);
		popupWindow.update();
	}
	
	/**
	 * 初始化弹出框里面的选项（以对勾+选项名为显示样式）
	 * @param layout
	 * @param listener
	 * @param allNames
	 * @param curName
	 */
	public void initPopupWindowItemsContainCheckMark(LinearLayout layout, CommonOnTouchListener listener, List<String> allNames, String curCheckedName) {
		LayoutInflater inflater = LayoutInflater.from(context);
		int size = allNames.size();
		for (int i = 0; i < size; i++) {
			RelativeLayout chooseItem = (RelativeLayout) inflater.inflate(R.layout.popup_contain_check_mark, null);
			TextView chooseName = (TextView) chooseItem.findViewById(R.id.chooseText);
			chooseName.setTextColor(textColor);
			String curName = allNames.get(i);
			chooseName.setText(curName);
			ImageView checkedIV = (ImageView) chooseItem.getChildAt(0);
			if (curName.equals(curCheckedName)) {
				checkedIV.setVisibility(View.VISIBLE);
			} else {
				checkedIV.setVisibility(View.GONE);
			}
			
			listener.changeColor(popupBgTouchColor, popupBgColor);
			chooseItem.setOnTouchListener(listener);
			chooseItem.setTag(curName);
			layout.addView(chooseItem);
		}
	}
	
	/**
	 * 将所有要在弹出框中加入的内容按照key/value存入Map中（key表示对应的ID值之类的，value为显示给用户的内容），再传入当前要选中的key值
	 * @param layout
	 * @param listener
	 * @param allNames
	 * @param curKey
	 */
	public void initPopupWindowItemsContainCheckMark(LinearLayout layout, CommonOnTouchListener listener, Map<String, String> allNames, String curKey) {
		LayoutInflater inflater = LayoutInflater.from(context);
		Set<String> keySet = allNames.keySet();
		for (String key : keySet) {
			RelativeLayout chooseItem = (RelativeLayout) inflater.inflate(R.layout.popup_contain_check_mark, null);
			TextView chooseName = (TextView) chooseItem.findViewById(R.id.chooseText);
			chooseName.setTextColor(textColor);
			String curName = allNames.get(key);
			chooseName.setText(curName);
			ImageView checkedIV = (ImageView) chooseItem.getChildAt(0);
			if (curKey == key) {
				checkedIV.setVisibility(View.VISIBLE);
			} else {
				checkedIV.setVisibility(View.GONE);
			}
			
			listener.changeColor(popupBgTouchColor, popupBgColor);
			chooseItem.setOnTouchListener(listener);
			chooseItem.setTag(key);
			layout.addView(chooseItem);
		}
	}
	
	/**
	 * 初始化弹出框里面的选项（以选项名+右侧删除图标为显示样式）
	 * @param layout
	 * @param listener
	 * @param allNames
	 * @param curName
	 */
	public void initPopupWindowItemsContainDelMark(LinearLayout layout, CommonOnTouchListener itemListener, CommonOnTouchListener delBtnListener, List<String> allNames) {
		LayoutInflater inflater = LayoutInflater.from(context);
		int size = allNames.size();
		for (int i = 0; i < size; i++) {
			RelativeLayout chooseItem = (RelativeLayout) inflater.inflate(R.layout.popup_contain_del_mark, null);
			TextView chooseName = (TextView) chooseItem.findViewById(R.id.chooseText);
			chooseName.setTextColor(textColor);
			String curName = allNames.get(i);
			chooseName.setText(curName);
			ImageView checkedIV = (ImageView) chooseItem.findViewById(R.id.chooseDelIV);
			itemListener.changeColor(popupBgTouchColor, popupBgColor);
			chooseItem.setOnTouchListener(itemListener);
			checkedIV.setOnTouchListener(delBtnListener);
			chooseItem.setTag(curName);
			layout.addView(chooseItem);
		}
	}
	
	public PopupWindow getPopupWindow() {
		return popupWindow;
	}

	public void setPopupWindow(CommonPopupWindow popupWindow) {
		this.popupWindow = popupWindow;
	}

	public void setPopupBgColor(int popupBgColor) {
		this.popupBgColor = popupBgColor;
	}

	public int getPopupBgTouchColor() {
		return popupBgTouchColor;
	}

	public void setPopupBgTouchColor(int popupBgTouchColor) {
		this.popupBgTouchColor = popupBgTouchColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public int getPopupBgColor() {
		return popupBgColor;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getXoffInPixels() {
		return xoffInPixels;
	}

	public void setXoffInPixels(int xoffInPixels) {
		this.xoffInPixels = xoffInPixels;
	}

	public int getYoffInPixels() {
		return yoffInPixels;
	}

	public void setYoffInPixels(int yoffInPixels) {
		this.yoffInPixels = yoffInPixels;
	}

	public int getGravity() {
		return gravity;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPopupWindowShowType() {
		return popupWindowShowType;
	}

	public void setPopupWindowShowType(int popupWindowShowType) {
		this.popupWindowShowType = popupWindowShowType;
	}
	
}
