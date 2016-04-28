package com.enorth.cms.listener;

import com.enorth.cms.view.news.ChannelSearchActivity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;

public abstract class EditTextDrawableOnTouchListener implements OnTouchListener {
	
	private Activity activity;

	public abstract EditText getEditText();

	public abstract void eventDo();
	
	public EditTextDrawableOnTouchListener(Activity activity) {
		this.activity = activity;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (activity instanceof ChannelSearchActivity) {
			ChannelSearchActivity channelSearchActivity = (ChannelSearchActivity) activity;
			channelSearchActivity.setClickET(true);
		}
		EditText editText = getEditText();
		Drawable drawable = editText.getCompoundDrawables()[2];
		if (drawable == null)
			return false;

		if (event.getAction() != MotionEvent.ACTION_UP)
			return false;

		int intrinsicWidth = drawable.getIntrinsicWidth();
		// 触摸点位置判断
		if (event.getX() > editText.getWidth() - editText.getPaddingRight() - intrinsicWidth) {
			// editText.setText("");
			eventDo();
			return true;
		}

		return false;
	}

}
