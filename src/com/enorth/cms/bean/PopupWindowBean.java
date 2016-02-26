package com.enorth.cms.bean;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.consts.ParamConst;

import android.view.View;
import android.widget.PopupWindow;

public class PopupWindowBean {
	/**
	 * 弹出框的背景颜色
	 */
	private int popupBgColor;
	/**
	 * 弹出框中的每一个item
	 */
	private List<View> items = new ArrayList<View>();
	/**
	 * 以此view为原点进行显示
	 */
	private View view;
	/**
	 * 显示的位置（根据Gravity.获取--例：Gravity.TOP）
	 */
	private int gravity;
	/**
	 * 弹出浮层的宽度
	 */
	private int width = ParamConst.POP_WINDOW_COMMON_WIDTH;
	/**
	 * 弹出浮层的高度（注释原因：弹出浮层没有竖着显示的）
	 */
//	private int height = ParamConst.POP_WINDOW_COMMON_HEIGHT;
	/**
	 * x坐标
	 */
//	private int x;
	/**
	 * y坐标
	 */
	private int y;
	
	private PopupWindow popupWindow;

	public int getPopupBgColor() {
		return popupBgColor;
	}

	public void setPopupBgColor(int popupBgColor) {
		this.popupBgColor = popupBgColor;
	}

	public List<View> getItems() {
		return items;
	}

	public void setItems(List<View> items) {
		this.items = items;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public int getGravity() {
		return gravity;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public PopupWindow getPopupWindow() {
		return popupWindow;
	}

	public void setPopupWindow(PopupWindow popupWindow) {
		this.popupWindow = popupWindow;
	}

}
