package com.enorth.cms.listener;

import com.enorth.cms.activity.R;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public abstract class CommonOnTouchListener implements OnTouchListener {

	protected float downY = 0;

	protected float downX = 0;

	protected float moveX = 0;

	protected float moveY = 0;

	protected boolean isClick = false;

	protected int touchSlop;
	
	/**
	 * 判断点击的时候是否要将背景变成灰色
	 * @return
	 */
	public abstract boolean isClickBackgroungColorChange();
	/**
	 * 当确定当前状态为点击事件时，在进行改变操作前进行的判断，如果返回false，则不继续进行onImgChangeDo方法的操作
	 * @return
	 */
	public abstract boolean onImgChangeBegin();

	/**
	 * 在手指抬起的最后进行的操作
	 */
	public abstract void onImgChangeEnd();
	
	/**
	 * 当onImgChangeBegin为true时执行的方法
	 */
	public abstract void onImgChangeDo();
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			downX = v.getScaleX();
			downY = v.getScaleY();
			if (isClickBackgroungColorChange()) {
				int color = ContextCompat.getColor(v.getContext(), R.color.bottom_color_pressed);
				v.setBackgroundColor(color);
			}
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
					onImgChangeDo();
				}
			}
			if (isClickBackgroungColorChange()) {
				int color = ContextCompat.getColor(v.getContext(), R.color.bottom_color_basic);
				v.setBackgroundColor(color);
			}
			onImgChangeEnd();
			break;
		default:
			Log.w("default", String.valueOf(event.getActionMasked()));
			break;
		}
		return true;
	}
}
