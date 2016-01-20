package com.enorth.cms.bean;

import com.enorth.cms.activity.R;

import android.content.Context;
import android.support.v4.content.ContextCompat;

public class ButtonColorBasicBean {
	
	private Context context;
	
	private int mBgNormalColor = 0;
	
	private int mTextNormalColor = 0;
	
	private int mBgPressedColor = 0;
	
	private int mTextPressedColor = 0;
	
	private int mBgFocusedColor = 0;
	
	private int mTextFocusedColor = 0;
	
	private int mBgUnabledColor = 0;
	
	private int mTextUnabledColor = 0;
	
	public ButtonColorBasicBean(Context context) throws Exception {
		if (context == null) {
			throw new Exception("实例化此bean时需要传入一个非空的context");
		}
		this.context = context;
	}

	public int getmBgNormalColor() {
		if (mBgNormalColor == 0) {
			mBgNormalColor = ContextCompat.getColor(context, R.color.white);
		}
		return mBgNormalColor;
	}

	public void setmBgNormalColor(int mBgNormalColor) {
		this.mBgNormalColor = mBgNormalColor;
	}
	
	public int getmTextNormalColor() {
		if (mTextNormalColor == 0) {
			mTextNormalColor = ContextCompat.getColor(context, R.color.common_blue);
		}
		return mTextNormalColor;
	}

	public void setmTextNormalColor(int mTextNormalColor) {
		this.mTextNormalColor = mTextNormalColor;
	}

	public int getmBgPressedColor() {
		if (mBgPressedColor == 0) {
			mBgPressedColor = ContextCompat.getColor(context, R.color.dark_gray);
		}
		return mBgPressedColor;
	}

	public void setmBgPressedColor(int mBgPressedColor) {
		this.mBgPressedColor = mBgPressedColor;
	}

	public int getmTextPressedColor() {
		if (mTextPressedColor == 0) {
			mTextPressedColor = ContextCompat.getColor(context, R.color.yellow);
		}
		return mTextPressedColor;
	}

	public void setmTextPressedColor(int mTextPressedColor) {
		this.mTextPressedColor = mTextPressedColor;
	}
	
	public int getmBgFocusedColor() {
		if (mBgFocusedColor == 0) {
			mBgFocusedColor = ContextCompat.getColor(context, R.color.common_blue);
		}
		return mBgFocusedColor;
	}

	public void setmBgFocusedColor(int mBgFocusedColor) {
		this.mBgFocusedColor = mBgFocusedColor;
	}
	
	public int getmTextFocusedColor() {
		if (mTextFocusedColor == 0) {
			mTextFocusedColor = ContextCompat.getColor(context, R.color.white);
		}
		return mTextFocusedColor;
	}

	public void setmTextFocusedColor(int mTextFocusedColor) {
		this.mTextFocusedColor = mTextFocusedColor;
	}

	public int getmBgUnabledColor() {
		if (mBgUnabledColor == 0) {
			mBgUnabledColor = ContextCompat.getColor(context, R.color.gray);
		}
		return mBgUnabledColor;
	}

	public void setmBgUnabledColor(int mBgUnabledColor) {
		this.mBgUnabledColor = mBgUnabledColor;
	}

	public int getmTextUnabledColor() {
		if (mTextUnabledColor == 0) {
			mTextUnabledColor = ContextCompat.getColor(context, R.color.dark_gray);
		}
		return mTextUnabledColor;
	}

	public void setmTextUnabledColor(int mTextUnabledColor) {
		this.mTextUnabledColor = mTextUnabledColor;
	}
}