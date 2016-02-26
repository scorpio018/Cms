package com.enorth.cms.listener.newslist.newssearch;

import com.enorth.cms.listener.CommonOnTouchListener;

import android.view.View;
/**
 * 用于搜索新闻中的提交按钮的点击事件，之所以使用onTouch事件是为了追求点击特效
 * @author yangyang
 *
 */
public abstract class NewsChannelSubmitOnTouchListener extends CommonOnTouchListener {
	
	public NewsChannelSubmitOnTouchListener(int touchSlop) {
		super.touchSlop = touchSlop;
	}

	@Override
	public void onTouchBegin() {
		
	}

	@Override
	public boolean isClickBackgroungColorChange() {
		return true;
	}

	@Override
	public void touchMove(View v) {
		
	}

	@Override
	public boolean onImgChangeBegin(View v) {
		return true;
	}

	@Override
	public void onImgChangeEnd(View v) {
		
	}
	
	@Override
	public boolean isStopEventTransfer() {
		return true;
	}

}
