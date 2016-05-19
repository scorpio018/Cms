package com.enorth.cms.listener.popup;

import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 此处点击事件为PopupWindow弹出的item点击后将对应的item删除
 * @author yangyang
 *
 */
public abstract class PopupWindowContainDelMarkOnClickListener extends CommonOnClickListener {

	private LinearLayout layout;

	protected Context context;

	public PopupWindowContainDelMarkOnClickListener(Context context, LinearLayout layout) {
		this.context = context;
		this.layout = layout;
	}

	public abstract void checkItem(String curCheckedText);

	@Override
	public void onClick(View v) {
		if (v instanceof ImageView) {
			// 点击删除按钮
//			RelativeLayout layout = (RelativeLayout) v.getParent();
			View layout = (View) v.getParent();
			TextView chooseTV = (TextView) layout.findViewById(R.id.chooseText);
			String chooseText = (String) chooseTV.getText();
			checkItem(chooseText);
//			this.layout.removeView((View) v.getParent());
//			((ViewGroup) v.getParent().getParent()).removeView((View) v.getParent());
//			layout.requestLayout();
//			layout.removeAllViews();
			/*int childCount = layout.getChildCount() - 1;
			for (int i = childCount; i >= 0; i--) {
				View child = layout.getChildAt(i);
				layout.removeView(child);
			}*/
			this.layout.removeView(layout);
			this.layout.removeViewInLayout(layout);
//			((LinearLayout) layout.getParent()).removeView(layout);
//			this.layout.removeView(layout);
//			layout.removeView((View) v.getParent());
//			layout.removeViewInLayout((View) v.getParent());
		} else if (v instanceof RelativeLayout) {
			// 点击整个item
			TextView chooseTV = (TextView) v.findViewById(R.id.chooseText);
			String chooseText = (String) chooseTV.getText();
			checkItem(chooseText);
		}
	}
}
