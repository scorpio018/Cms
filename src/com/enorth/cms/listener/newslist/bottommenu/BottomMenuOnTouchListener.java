package com.enorth.cms.listener.newslist.bottommenu;

import com.enorth.cms.bean.news_list.BottomMenuBasicBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ScreenTools;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public abstract class BottomMenuOnTouchListener extends CommonOnTouchListener {
	
	public int canEnableState = ParamConst.CAN_ENABLE_STATE_DEFAULT;
	
	private BottomMenuBasicBean bean;
	
	public BottomMenuOnTouchListener(BottomMenuBasicBean bean, Context context) {
		this.bean = bean;
		this.touchSlop = ScreenTools.getTouchSlop(context);
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

	@Override
	public void touchMove(View v) {
		
	}
	
	@Override
	public void onImgChangeEnd(View v) {

	}

	@Override
	public void onImgChangeDo(View v) {

	}

	@Override
	public void onTouchBegin() {
		
	}
	
	@Override
	public boolean isStopEventTransfer() {
		return true;
	}

}