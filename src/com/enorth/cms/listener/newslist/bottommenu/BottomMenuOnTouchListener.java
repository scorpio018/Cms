package com.enorth.cms.listener.newslist.bottommenu;

import com.enorth.cms.bean.news_list.BottomMenuBasicBean;
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

	@Override
	public void touchMove(View v) {
		
	}
	
	@Override
	public void onImgChangeEnd() {

	}

	@Override
	public void onImgChangeDo() {

	}

	@Override
	public void onTouchBegin() {
		
	}

}