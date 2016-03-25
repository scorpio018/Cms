package com.enorth.cms.utils;

import java.util.List;

import com.enorth.cms.bean.ImageBasicBean;
import com.enorth.cms.enums.DrawablePosition;
import com.enorth.cms.view.R.id;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;

public class DrawableUtil {
	public StateListDrawable createDrawableSelector(Context context, ImageBasicBean bean) {
		Drawable checked = ContextCompat.getDrawable(context, bean.getImageCheckedResource());
		Drawable unchecked = ContextCompat.getDrawable(context, bean.getImageUncheckResource());
		Drawable disabled = ContextCompat.getDrawable(context, bean.getImageDisableResource());
        StateListDrawable stateList = new StateListDrawable();
        int statePressed = android.R.attr.state_pressed;
        int stateChecked = android.R.attr.state_checked;
        int stateFocused = android.R.attr.state_focused;
        int stateEnabled = android.R.attr.state_enabled;
        stateList.addState(new int[] {-stateEnabled}, disabled);
        stateList.addState(new int[] {stateChecked}, checked);
        stateList.addState(new int[] {statePressed}, checked);
        stateList.addState(new int[] {stateFocused}, checked);
        stateList.addState(new int[] {}, unchecked);
        return stateList;
    }
	
	/**
	 * 获取drawable
	 * @param context
	 * @param drawable
	 * @return
	 */
	public static Drawable getDrawable(Context context, int drawable) {
		return ContextCompat.getDrawable(context, drawable);
	}
	
}
