package com.enorth.cms.handler.materialupload;

import com.enorth.cms.widget.linearlayout.MaterialUploadFragLinearLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MaterialUploadFragMoveHandler extends Handler {
	
	private MaterialUploadFragLinearLayout layout;
	
	private int moveSpeed = 8;
	
	private boolean isStartAnim = false;
	
	public MaterialUploadFragMoveHandler(MaterialUploadFragLinearLayout layout) {
		this.layout = layout;
	}

	@Override
	public void handleMessage(Message msg) {
//		super.handleMessage(msg);
		move();
	}
	
	/**
	 * 进行收回/展开操作
	 * @param isOpen 当前按钮组的状态：true表示展开；false表示收回
	 */
	private void move() {
		boolean isOpen = layout.isOpen();
		if (!isStartAnim) {
			layout.getMaterialUploadBtnGroupScrollBtn().startAnimation(layout.getRotateBackAnimation());
		}
		boolean isEnd = false;
		// 如果当前状态是从展开到收回，则要将按钮组的layout的高度收回到layout.getBtnGroupEndHeight()的高度
		if (isOpen) {
			layout.setCurBtnGroupHeight(layout.getCurBtnGroupHeight() + moveSpeed);
			isEnd = layout.getCurBtnGroupHeight() > layout.getBtnGroupMoveMaxHeight();
			layout.setCurBtnGroupHeight(layout.getBtnGroupMoveMaxHeight());
		} else {
			// 如果当前状态是从收回到展开，则要将按钮组的layou的高度展开到
			layout.setCurBtnGroupHeight(layout.getCurBtnGroupHeight() - moveSpeed);
			isEnd = layout.getCurBtnGroupHeight() < 0;
			layout.setCurBtnGroupHeight(0);
		}
		if (isEnd) {
			layout.getTimer().cancel();
			layout.setOpen(!isOpen);
			layout.getMaterialUploadBtnGroupScrollBtn().clearAnimation();
		}
		Log.e("layout.getCurBtnGroupHeight()", String.valueOf(layout.getCurBtnGroupHeight()));
		layout.requestLayout();
	}
}
