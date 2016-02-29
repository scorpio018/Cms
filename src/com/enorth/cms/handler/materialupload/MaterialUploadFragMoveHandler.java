package com.enorth.cms.handler.materialupload;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.widget.linearlayout.MaterialUploadFragLinearLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MaterialUploadFragMoveHandler extends Handler {
	
	private MaterialUploadFragLinearLayout layout;
	
	private int moveSpeed = 8;
	
	private boolean isStartAnim = false;
	
	private boolean isFinished = false;
	
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
	private synchronized void move() {
		if (isFinished) {
			return;
		}
		int curFragLayoutState = layout.getCurFragLayoutState();
		switch (curFragLayoutState) {
		case ParamConst.CUR_FRAG_LAYOUT_STATE_IS_CLOSED:
			layout.setCurFragLayoutState(ParamConst.CUR_FRAG_LAYOUT_STATE_OPENING);
			break;
		case ParamConst.CUR_FRAG_LAYOUT_STATE_IS_OPENED:
			layout.setCurFragLayoutState(ParamConst.CUR_FRAG_LAYOUT_STATE_CLOSING);
			break;
		case ParamConst.CUR_FRAG_LAYOUT_STATE_CLOSING:
		case ParamConst.CUR_FRAG_LAYOUT_STATE_OPENING:
			break;
		default:
			break;
		}
		boolean isOpen = layout.isOpen();
		boolean isEnd = layout.isEnd();
		if (isEnd) {
			switch (curFragLayoutState) {
			case ParamConst.CUR_FRAG_LAYOUT_STATE_CLOSING:
				layout.setCurFragLayoutState(ParamConst.CUR_FRAG_LAYOUT_STATE_IS_CLOSED);
				isFinished = true;
				break;
			case ParamConst.CUR_FRAG_LAYOUT_STATE_OPENING:
				layout.setCurFragLayoutState(ParamConst.CUR_FRAG_LAYOUT_STATE_IS_OPENED);
				isFinished = true;
				break;
			case ParamConst.CUR_FRAG_LAYOUT_STATE_IS_CLOSED:
			case ParamConst.CUR_FRAG_LAYOUT_STATE_IS_OPENED:
				return;
			default:
				return;
			}
			layout.getTimer().cancel();
//			Log.e("layout.getCurBtnGroupHeight()", String.valueOf(layout.getCurMoveHeight()));
			layout.requestLayout();
			return;
		}
		// 如果当前状态是从展开到收回，则要将按钮组的layout的高度收回到layout.getBtnGroupEndHeight()的高度
		if (isOpen) {
			if (!isStartAnim) {
				layout.getMaterialUploadBtnGroupScrollBtn().startAnimation(layout.getRotateAnimation());
			}
			layout.setCurMoveHeight(layout.getCurMoveHeight() + moveSpeed);
			layout.setHistoryBottom(layout.getHistoryBottom() + moveSpeed);
			if (layout.getCurMoveHeight() > layout.getBtnGroupEndHeight() - moveSpeed) {
				isEnd(layout.getBtnGroupEndHeight(), !isOpen);
			}
		} else {
			// 如果当前状态是从收回到展开，则要将按钮组的layou的高度展开到
			if (!isStartAnim) {
				layout.getMaterialUploadBtnGroupScrollBtn().startAnimation(layout.getRotateBackAnimation());
			}
			layout.setCurMoveHeight(layout.getCurMoveHeight() - moveSpeed);
			layout.setHistoryBottom(layout.getHistoryBottom() - moveSpeed);
			if (layout.getCurMoveHeight() < moveSpeed) {
				isEnd(0, !isOpen);
			}
		}
//		Log.e("layout.getCurBtnGroupHeight()", String.valueOf(layout.getCurMoveHeight()));
		layout.requestLayout();
	}
	
	private void isEnd(int curMoveHeight, boolean changeOpenState) {
		layout.setEnd(true);
		layout.setCurMoveHeight(curMoveHeight);
		layout.setOpen(changeOpenState);
		int height = layout.getParentLayoutHeight() - layout.getTitleViewHeight() - layout.getBtnGroupScrollHeight() - layout.getBtnGroupHeight() + layout.getCurMoveHeight();
		layout.getHistoryFrag().setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
//		layout.getMaterialUploadBtnGroupScrollBtn().clearAnimation();
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	
	
}
