package com.enorth.cms.popup;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

public class MaterialBtnsPopupWindow extends PopupWindow {
	
	public MaterialBtnsPopupWindow(Context context) {
		super(context);
	}
	
	public MaterialBtnsPopupWindow(View contentView) {
		super(contentView);
	}
	
	public MaterialBtnsPopupWindow(View contentView, int width, int height) {
		super(contentView, width, height);
	}
	
	public MaterialBtnsPopupWindow(View contentView, int width, int height, boolean focusable) {
		super(contentView, width, height, focusable);
	}
	
}
