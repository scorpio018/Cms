package com.enorth.cms.listener.popup;

import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class PopupWindowOnTouchListener extends CommonOnTouchListener {
	
	private LinearLayout layout;
	
	protected Context context;

	public PopupWindowOnTouchListener(Context context, LinearLayout layout) {
		super.touchSlop = ScreenTools.getTouchSlop(context);
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
		int childCount = layout.getChildCount();
		for (int j = 0; j < childCount; j++) {
			View view = layout.getChildAt(j);
			ImageView chooseCheckStateIV = (ImageView) view.findViewById(R.id.chooseCheckStateIV);

			if (view.getTag().equals(v.getTag())) {
				if (chooseCheckStateIV.getVisibility() == View.GONE) {
					TextView chooseTV = (TextView) view.findViewById(R.id.chooseText);
					String chooseText = (String) chooseTV.getText();
					chooseCheckStateIV.setVisibility(View.VISIBLE);
					checkItem(chooseText);
				}
			} else {
				if (chooseCheckStateIV.getVisibility() == View.VISIBLE) {
					chooseCheckStateIV.setVisibility(View.GONE);
				}
			}
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
