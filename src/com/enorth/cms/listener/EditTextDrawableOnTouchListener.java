package com.enorth.cms.listener;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;

public abstract class EditTextDrawableOnTouchListener implements OnTouchListener {


	public EditTextDrawableOnTouchListener() {
	}
	
	public abstract EditText getEditText();
	
	public abstract void evenDo();

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		EditText editText = getEditText();
		Drawable drawable = editText.getCompoundDrawables()[2];
		if (drawable == null)
			return false;

		if (event.getAction() != MotionEvent.ACTION_UP)
			return false;

		int intrinsicWidth = drawable.getIntrinsicWidth();
		// 触摸点位置判断
		if (event.getX() > editText.getWidth() - editText.getPaddingRight()
				- intrinsicWidth) {
//			editText.setText("");
			evenDo();
			return true;
		}

		return false;
	}

}
