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

public abstract class PopupWindowContainCheckMarkOnTouchListener extends CommonOnTouchListener {
	
	private LinearLayout layout;
	
	protected Context context;

	public PopupWindowContainCheckMarkOnTouchListener(Context context, LinearLayout layout) {
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
	
	public abstract void checkItem(String tag, String curCheckedText);

	@Override
	public void onImgChangeDo(View v) {
		int childCount = layout.getChildCount();
		Object curTag = v.getTag();
		for (int j = 0; j < childCount; j++) {
			View view = layout.getChildAt(j);
			ImageView chooseCheckStateIV = (ImageView) view.findViewById(R.id.chooseCheckStateIV);
			Object tag = view.getTag();
			if (tag.equals(curTag)) {
				if (chooseCheckStateIV.getVisibility() == View.GONE) {
					TextView chooseTV = (TextView) view.findViewById(R.id.chooseText);
					String chooseText = (String) chooseTV.getText();
					chooseCheckStateIV.setVisibility(View.VISIBLE);
					checkItem(curTag.toString(), chooseText);
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
