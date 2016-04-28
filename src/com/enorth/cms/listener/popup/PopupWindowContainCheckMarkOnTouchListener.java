package com.enorth.cms.listener.popup;

import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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
		Log.e("check state", "VISIBLE:" + View.VISIBLE + ";GONE:" + View.GONE);
		for (int j = 0; j < childCount; j++) {
			View view = layout.getChildAt(j);
			ImageView chooseCheckStateIV = (ImageView) view.findViewById(R.id.chooseCheckStateIV);
			Object tag = view.getTag();
			if (tag.equals(curTag)) {
				TextView chooseTV = (TextView) view.findViewById(R.id.chooseText);
				String chooseText = (String) chooseTV.getText();
				Log.e("curIV check state before", chooseText + ":" + String.valueOf(chooseCheckStateIV.getVisibility()));
				chooseCheckStateIV.setVisibility(View.VISIBLE);
				Log.e("curIV check state after", chooseText + ":" + String.valueOf(chooseCheckStateIV.getVisibility()));
				checkItem(curTag.toString(), chooseText);
			} else {
				TextView chooseTV = (TextView) view.findViewById(R.id.chooseText);
				String chooseText = (String) chooseTV.getText();
				Log.e("curIV check state before", chooseText + ":" + String.valueOf(chooseCheckStateIV.getVisibility()));
				chooseCheckStateIV.setVisibility(View.GONE);
				Log.e("curIV check state after", chooseText + ":" + String.valueOf(chooseCheckStateIV.getVisibility()));
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
