package com.enorth.cms.listener;

import com.enorth.cms.utils.ColorUtil;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public abstract class CommonOnCheckedChangeListener implements OnCheckedChangeListener {
	
	private Context context;
	/**
	 * 按钮选中时，文字的颜色
	 */
	private int checkedColor;
	/**
	 * 按钮文字的默认颜色
	 */
	private int normalColor;
	
	public CommonOnCheckedChangeListener(Context context) {
		this.context = context;
	}
	
	public CommonOnCheckedChangeListener(Context context, CompoundButton button) {
		this.context = context;
		this.normalColor = button.getCurrentTextColor();
		initBtnState(button);
	}
	
	public CommonOnCheckedChangeListener(Context context, CompoundButton button, int checkedColor) {
		this.context = context;
		this.normalColor = button.getCurrentTextColor();
		this.checkedColor = checkedColor;
		initBtnState(button);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			checkBtn(buttonView);
		} else {
			uncheckBtn(buttonView);
		}
	}
	
	private void initBtnState(CompoundButton button) {
		if (button.isChecked()) {
			checkBtn(button);
		}
	}
	
	private void checkBtn(CompoundButton button) {
		if (checkedColor == 0) {
			checkedColor = ColorUtil.getCommonGreenColor(context);
		}
		button.setTextColor(checkedColor);
	}
	
	private void uncheckBtn(CompoundButton button) {
		button.setTextColor(normalColor);
	}
}
