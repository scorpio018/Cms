package com.enorth.cms.bean;

import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.R.color;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class ViewColorBasicBean {
	
	private Context context;
	
	private int mBgNormalColor = 0;
	
	private int mTextNormalColor = 0;
	
	private int mBgPressedColor = 0;
	
	private int mTextPressedColor = 0;
	
	private int mBgFocusedColor = 0;
	
	private int mTextFocusedColor = 0;
	
	private int mBgUnabledColor = 0;
	
	private int mTextUnabledColor = 0;
	
	private int strokeColor = 0;
	
	public ViewColorBasicBean(Context context) {
		if (context == null) {
			Log.e("ButtonColorBasicBean() error", "实例化此bean时需要传入一个非空的context");
		}
		this.context = context;
	}

	public int getmBgNormalColor() {
		if (mBgNormalColor == 0) {
//			mBgNormalColor = ContextCompat.getColor(context, R.color.white);
			mBgNormalColor = ColorUtil.getWhiteColor(context);
		}
		return mBgNormalColor;
	}

	public void setmBgNormalColor(int mBgNormalColor) {
		this.mBgNormalColor = mBgNormalColor;
	}
	
	public int getmTextNormalColor() {
		if (mTextNormalColor == 0) {
//			mTextNormalColor = ContextCompat.getColor(context, R.color.common_blue);
			mTextNormalColor = ColorUtil.getCommonBlueColor(context);
		}
		return mTextNormalColor;
	}

	public void setmTextNormalColor(int mTextNormalColor) {
		this.mTextNormalColor = mTextNormalColor;
	}

	public int getmBgPressedColor() {
		if (mBgPressedColor == 0) {
//			mBgPressedColor = ContextCompat.getColor(context, R.color.gray_lighter);
			mBgPressedColor = ColorUtil.getGrayLighter(context);
		}
		return mBgPressedColor;
	}

	public void setmBgPressedColor(int mBgPressedColor) {
		this.mBgPressedColor = mBgPressedColor;
	}

	public int getmTextPressedColor() {
		if (mTextPressedColor == 0) {
//			mTextPressedColor = ContextCompat.getColor(context, R.color.black);
			mTextPressedColor = ColorUtil.getBlack(context);
		}
		return mTextPressedColor;
	}

	public void setmTextPressedColor(int mTextPressedColor) {
		this.mTextPressedColor = mTextPressedColor;
	}
	
	public int getmBgFocusedColor() {
		if (mBgFocusedColor == 0) {
//			mBgFocusedColor = ContextCompat.getColor(context, R.color.common_blue);
			mBgFocusedColor = ColorUtil.getCommonBlueColor(context);
		}
		return mBgFocusedColor;
	}

	public void setmBgFocusedColor(int mBgFocusedColor) {
		this.mBgFocusedColor = mBgFocusedColor;
	}
	
	public int getmTextFocusedColor() {
		if (mTextFocusedColor == 0) {
//			mTextFocusedColor = ContextCompat.getColor(context, R.color.white);
			mTextFocusedColor = ColorUtil.getWhiteColor(context);
		}
		return mTextFocusedColor;
	}

	public void setmTextFocusedColor(int mTextFocusedColor) {
		this.mTextFocusedColor = mTextFocusedColor;
	}

	public int getmBgUnabledColor() {
		if (mBgUnabledColor == 0) {
//			mBgUnabledColor = ContextCompat.getColor(context, R.color.gray);
			mBgUnabledColor = ColorUtil.getGray(context);
		}
		return mBgUnabledColor;
	}

	public void setmBgUnabledColor(int mBgUnabledColor) {
		this.mBgUnabledColor = mBgUnabledColor;
	}

	public int getmTextUnabledColor() {
		if (mTextUnabledColor == 0) {
//			mTextUnabledColor = ContextCompat.getColor(context, R.color.dark_gray);
			mTextUnabledColor = ColorUtil.getDarkGray(context);
		}
		return mTextUnabledColor;
	}

	public void setmTextUnabledColor(int mTextUnabledColor) {
		this.mTextUnabledColor = mTextUnabledColor;
	}

	public int getStrokeColor() {
		if (strokeColor == 0) {
			strokeColor = ColorUtil.getCommonBlueColor(context);
		}
		return strokeColor;
	}

	public void setStrokeColor(int strokeColor) {
		this.strokeColor = strokeColor;
	}
	
}