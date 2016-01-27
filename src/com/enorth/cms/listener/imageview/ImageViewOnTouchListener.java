package com.enorth.cms.listener.imageview;

import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.listener.CommonOnTouchListener;

import android.view.MotionEvent;
import android.view.View;

public abstract class ImageViewOnTouchListener extends CommonOnTouchListener {
	
	private NewsListImageViewBasicBean bean;
	
	public ImageViewOnTouchListener(NewsListImageViewBasicBean bean, int touchSlop) {
		this.bean = bean;
		super.touchSlop = touchSlop;
	}
	
	@Override
	public boolean isClickBackgroungColorChange() {
		return false;
	}
	
	/**
	 * 在已经确定当前操作为点击操作时所作的操作
	 */
	@Override
	public void onImgChangeDo() {
		if (bean.isSelected()) {
			bean.getImageView().setImageResource(bean.getImageUncheckResource());
			bean.getImageView().setSelected(false);
			bean.setSelected(false);
		} else {
//			checkBtn.setImageResource(R.drawable.check_btn);
//			checkBtn.setSelected(true);
			bean.getImageView().setImageResource(bean.getImageCheckedResource());
			bean.getImageView().setSelected(true);
			bean.setSelected(true);
		}
	}
	
	@Override
	public void touchMove(View v) {
		
	}
}
