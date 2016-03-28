package com.enorth.cms.listener.popup;

import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class PopupWindowContainDelMarkOnTouchListener extends CommonOnTouchListener {

	private LinearLayout layout;

	protected Context context;

	public PopupWindowContainDelMarkOnTouchListener(Context context, LinearLayout layout) {
		super.touchSlop = ScreenTools.getTouchSlop(context);
		this.context = context;
		this.layout = layout;
	}

	@Override
	public boolean isClickBackgroungColorChange() {
		return true;
	}

	@Override
	public void touchMove(View v) {

	}

	@Override
	public boolean onImgChangeBegin(View v) {
		return true;
	}

	public abstract void checkItem(String curCheckedText);

	@Override
	public void onImgChangeDo(View v) {
		if (v instanceof ImageView) {
			// 点击删除按钮
			RelativeLayout layout = (RelativeLayout) v.getParent();
			TextView chooseTV = (TextView) layout.findViewById(R.id.chooseText);
			String chooseText = (String) chooseTV.getText();
			checkItem(chooseText);
			layout.removeViewInLayout((View) v.getParent());
		} else if (v instanceof RelativeLayout) {
			// 点击整个item
			TextView chooseTV = (TextView) v.findViewById(R.id.chooseText);
			String chooseText = (String) chooseTV.getText();
			checkItem(chooseText);
		}

	}

	@Override
	public void onTouchBegin() {

	}

	@Override
	public boolean isStopEventTransfer() {
		return true;
	}

}
