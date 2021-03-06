package com.enorth.cms.listener.imageview;

import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ScreenTools;

import android.content.Context;
import android.view.View;

public abstract class ImageViewOnTouchListener extends CommonOnTouchListener {
	
//	private NewsListImageViewBasicBean bean;
	
	public ImageViewOnTouchListener(/*NewsListImageViewBasicBean bean, */Context context) {
//		this.bean = bean;
		super.touchSlop = ScreenTools.getTouchSlop(context);
	}
	
	@Override
	public boolean isClickBackgroungColorChange() {
		return false;
	}
	
	/**
	 * 在已经确定当前操作为点击操作时所作的操作
	 */
	@Override
	public void onImgChangeDo(View v) {
		/*if (bean.isSelected()) {
			bean.getImageView().setImageResource(bean.getImageUncheckResource());
			bean.getImageView().setSelected(false);
			bean.setSelected(false);
		} else {
//			checkBtn.setImageResource(R.drawable.check_btn);
//			checkBtn.setSelected(true);
			bean.getImageView().setImageResource(bean.getImageCheckedResource());
			bean.getImageView().setSelected(true);
			bean.setSelected(true);
		}*/
	}
	
	@Override
	public void touchMove(View v) {
		
	}
	
	@Override
	public boolean isStopEventTransfer() {
		return true;
	}
}
