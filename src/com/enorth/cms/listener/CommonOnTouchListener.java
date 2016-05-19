package com.enorth.cms.listener;

import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.view.R;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public abstract class CommonOnTouchListener implements OnTouchListener {

	protected float downX = 0;

	protected float downY = 0;

	protected float moveX = 0;

	protected float moveY = 0;
	
	protected float touchStartX = 0;
	
	protected float touchStartY = 0;
	
	protected float touchCurrentX = 0;
	
	protected float touchCurrentY = 0;
	
	protected float startX = 0;
	
	protected float startY = 0;

	protected boolean isClick = false;

	protected int touchSlop = 0;
	
	protected int pressColor = R.color.gray_lighter;
	
	protected int normalColor = R.color.bottom_color_basic;
	
	public CommonOnTouchListener() {
	}
	
	public CommonOnTouchListener(Context context) {
		touchSlop = ScreenTools.getTouchSlop(context);
	}
	
	public void changeColor(int pressColor, int normalColor) {
		this.pressColor = pressColor;
		this.normalColor = normalColor;
	}
	/**
	 * 在手指刚刚触摸时触发该方法（用于获取焦点时提供的方法）
	 */
	public abstract void onTouchBegin();
	/**
	 * 判断点击的时候是否要将背景变成灰色
	 * @return
	 */
	public abstract boolean isClickBackgroungColorChange();
	/**
	 * 当进行拖动时触发该方法
	 */
	public abstract void touchMove(View v);
	/**
	 * 当确定当前状态为点击事件时，在进行改变操作前进行的判断，如果返回false，则不继续进行onImgChangeDo方法的操作
	 * @return
	 */
	public abstract boolean onImgChangeBegin(View v);

	/**
	 * 在手指抬起的最后进行的操作
	 */
	public abstract void onImgChangeEnd(View v);
	
	/**
	 * 当onImgChangeBegin为true时执行的方法
	 */
	public abstract void onImgChangeDo(View v);
	/**
	 * true表示事件不向下传递；false相反
	 * @return
	 */
	public abstract boolean isStopEventTransfer();
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			Log.e("onTouch", "ACTION_DOWN");
			onTouchBegin();
			downX = v.getScaleX();
			downY = v.getScaleY();
			touchStartX = event.getRawX();
			touchStartY = event.getRawY();
			startX = event.getX();
			startY = event.getY();
			if (isClickBackgroungColorChange()) {
//				int color = ContextCompat.getColor(v.getContext(), pressColor);
				v.setBackgroundColor(pressColor);
				Log.e("onTouch", "ClickBackgroungColorChangePressColor");
			}
//			Log.w("MotionEvent.ACTION_DOWN", "【x:" + downX + "、y:" + downY + "】");
			isClick = true;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			Log.e("MotionEvent.ACTION_POINTER_DOWN", "多指放下动作记录");
		case MotionEvent.ACTION_POINTER_UP:
			Log.e("MotionEvent.ACTION_POINTER_UP", "多指抬起动作记录");
			isClick = false;
			break;
		/*case MotionEvent.ACTION_CANCEL:
			Log.e("onTouch", "ACTION_CANCEL");
			break;
		case MotionEvent.ACTION_OUTSIDE:
			Log.e("onTouch", "ACTION_OUTSIDE");
			break;
		case MotionEvent.ACTION_HOVER_MOVE:
			Log.e("onTouch", "ACTION_HOVER_MOVE");
			break;
		case MotionEvent.ACTION_SCROLL:
			Log.e("onTouch", "ACTION_SCROLL");
			break;
		case MotionEvent.ACTION_HOVER_ENTER:
			Log.e("onTouch", "ACTION_HOVER_ENTER");
			break;
		case MotionEvent.ACTION_HOVER_EXIT:
			Log.e("onTouch", "ACTION_HOVER_ENTER");
			break;
		case MotionEvent.ACTION_BUTTON_PRESS:
			Log.e("onTouch", "ACTION_BUTTON_PRESS");
			break;
		case MotionEvent.ACTION_BUTTON_RELEASE:
			Log.e("onTouch", "ACTION_BUTTON_RELEASE");
			break;
		case MotionEvent.ACTION_POINTER_INDEX_MASK:
			Log.e("onTouch", "ACTION_BUTTON_RELEASE");
			break;*/
			
		case MotionEvent.ACTION_MOVE:
			Log.e("onTouch", "ACTION_MOVE");
			moveX = v.getScaleX();
			moveY = v.getScaleY();
			double distinct = Math.sqrt(Math.pow(Math.abs(downX - moveX), 2) + Math.pow(Math.abs(downY - moveY), 2));
			Log.e("MotionEvent.ACTION_MOVE", "【x:" + moveX + "、y:" + moveY + "、distinct:" + distinct + "、touchSlop:" + touchSlop + "】");
			if (distinct >= touchSlop) {
				isClick = false;
			}
			touchCurrentX = event.getRawX();
			touchCurrentY = event.getRawY();
			touchMove(v);
			touchStartX = touchCurrentX;
			touchStartY = touchCurrentY;
			break;
//		case MotionEvent.ACTION_CANCEL:
//			Log.e("onTouch", "ACTION_CANCEL");
		case MotionEvent.ACTION_UP:
			Log.e("onTouch", "ACTION_UP");
			if (isClickBackgroungColorChange()) {
//				int color = ContextCompat.getColor(v.getContext(), normalColor);
				v.setBackgroundColor(normalColor);
				Log.e("onTouch", "ClickBackgroungColorChangeNormalColor");
			}
			if (isClick) {
				Log.e("onTouch", "isClick");
				boolean isContinue = onImgChangeBegin(v);
				Log.e("onTouch", "onImgChangeBegin");
				if (isContinue) {
					onImgChangeDo(v);
					Log.e("onTouch", "onImgChangeDo");
				}
			}
			onImgChangeEnd(v);
			Log.e("onTouch", "onImgChangeEnd");
			break;
		default:
			if (isClickBackgroungColorChange()) {
//				int color = ContextCompat.getColor(v.getContext(), normalColor);
				v.setBackgroundColor(normalColor);
			}
			Log.w("default", String.valueOf(event.getActionMasked()));
			break;
		}
		return isStopEventTransfer();
	}
}
