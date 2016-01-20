package com.enorth.cms.listener.bottom_menu;

import com.enorth.cms.bean.BottomMenuBasicBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnTouchListener;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public abstract class BottomMenuOnTouchListener extends CommonOnTouchListener {
	
	public int canEnableState = ParamConst.CAN_ENABLE_STATE_DEFAULT;
	
	private BottomMenuBasicBean bean;
	
	public BottomMenuOnTouchListener(BottomMenuBasicBean bean, int touchSlop) {
		this.bean = bean;
		this.touchSlop = touchSlop;
	}
	
	public BottomMenuOnTouchListener(BottomMenuBasicBean bean, int touchSlop, int canEnableState) {
		this.bean = bean;
		this.touchSlop = touchSlop;
		this.canEnableState = canEnableState;
	}
	
	@Override
	public boolean isClickBackgroungColorChange() {
		return true;
	}

	/*@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			downX = v.getScaleX();
			downY = v.getScaleY();
			
			Log.w("MotionEvent.ACTION_DOWN", "【x:" + downX + "、y:" + downY + "】");
			isClick = true;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			Log.w("MotionEvent.ACTION_POINTER_DOWN", "多指放下动作记录");
		case MotionEvent.ACTION_POINTER_UP:
			Log.w("MotionEvent.ACTION_POINTER_UP", "多指抬起动作记录");
			isClick = false;
			break;
		case MotionEvent.ACTION_MOVE:
			moveX = v.getScaleX();
			moveY = v.getScaleY();
			double distinct = Math.sqrt(Math.pow(Math.abs(downX - moveX), 2) + Math.pow(Math.abs(downY - moveY), 2));
			Log.w("MotionEvent.ACTION_MOVE", "【x:" + moveX + "、y:" + moveY + "、distinct:" + distinct + "、touchSlop:" + touchSlop + "】");
			if (distinct >= touchSlop) {
				isClick = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (isClick) {
				boolean isContinue = onImgChangeBegin();
				if (isContinue) {
					
				}
			}
			onImgChangeEnd();
			break;
		default:
			Log.w("default", String.valueOf(event.getActionMasked()));
			break;
		}
		return true;
	}*/

}