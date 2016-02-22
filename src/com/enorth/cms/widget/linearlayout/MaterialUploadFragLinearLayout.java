package com.enorth.cms.widget.linearlayout;

import com.enorth.cms.handler.materialupload.MaterialUploadFragMoveHandler;
import com.enorth.cms.task.MyTask;
import com.enorth.cms.task.MyTimer;
import com.enorth.cms.view.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MaterialUploadFragLinearLayout extends LinearLayout {
	
	private boolean once = true;
	/**
	 * 按钮组的fragment
	 */
	private View btnGroupFrag;
	/**
	 * 按钮组的fragment的高度
	 */
	private int btnGroupHeight;
	/**
	 * 按钮组的fragment的宽度
	 */
	private int btnGroupWidth;
	/**
	 * 按钮组的缩回的最终高度
	 */
	private int btnGroupEndHeight;
	/**
	 * 按钮组能变化的最大高度
	 */
	private int btnGroupMoveMaxHeight;
	/**
	 * 当前按钮组变化的高度
	 */
	private int curBtnGroupHeight = 0;
	/**
	 * 进行180°旋转的向上按钮图片
	 */
	private ImageView materialUploadBtnGroupScrollBtn;
	/**
	 * 180°旋转的动画
	 */
	private RotateAnimation rotateBackAnimation;
	/**
	 * 附件历史的fragment
	 */
	private View historyFrag;
	/**
	 * 附件历史的fragment的高度
	 */
	private int historyHeight;
	/**
	 * 附件里是的fragment的宽度
	 */
	private int historyWidth;
	/**
	 * 持续进行按钮组的收回/展开操作用到的定时器
	 */
	private MyTimer timer;
	/**
	 * fragment收回/展开的handler
	 */
	private MaterialUploadFragMoveHandler handler;
	
	private boolean isOpen = true;

	public MaterialUploadFragLinearLayout(Context context) {
		super(context);
	}
	
	public MaterialUploadFragLinearLayout(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	public MaterialUploadFragLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	public MaterialUploadFragLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	public void startMove() {
		if (once) {
			btnGroupFrag = getChildAt(1);
			LinearLayout materialUploadBtnGroupScrollLayout = (LinearLayout) btnGroupFrag.findViewById(R.id.materialUploadBtnGroupScrollLayout);
			materialUploadBtnGroupScrollBtn = (ImageView) btnGroupFrag.findViewById(R.id.materialUploadBtnGroupScrollBtn);
			rotateBackAnimation = (RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.reverse_back_anim);
			btnGroupHeight = btnGroupFrag.getMeasuredHeight();
			btnGroupWidth = btnGroupFrag.getMeasuredWidth();
			btnGroupEndHeight = materialUploadBtnGroupScrollLayout.getMeasuredHeight();
			btnGroupMoveMaxHeight = btnGroupHeight - btnGroupEndHeight;
			historyFrag = getChildAt(2);
			historyHeight = historyFrag.getMeasuredWidth();
			historyWidth = historyFrag.getMeasuredHeight();
			handler = new MaterialUploadFragMoveHandler(this);
			once = false;
		}
		timer = new MyTimer(handler);
		timer.schedule(5);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (once) {
			super.onLayout(changed, l, t, r, b);
		} else {
			btnGroupFrag.layout(0, btnGroupHeight - curBtnGroupHeight, btnGroupWidth, curBtnGroupHeight);
			historyFrag.layout(0, curBtnGroupHeight, historyWidth, historyHeight - curBtnGroupHeight);
		}
	}

	public View getBtnGroupFrag() {
		return btnGroupFrag;
	}

	public void setBtnGroupFrag(View btnGroupFrag) {
		this.btnGroupFrag = btnGroupFrag;
	}

	public int getBtnGroupHeight() {
		return btnGroupHeight;
	}

	public void setBtnGroupHeight(int btnGroupHeight) {
		this.btnGroupHeight = btnGroupHeight;
	}

	public int getBtnGroupEndHeight() {
		return btnGroupEndHeight;
	}

	public void setBtnGroupEndHeight(int btnGroupEndHeight) {
		this.btnGroupEndHeight = btnGroupEndHeight;
	}

	public int getBtnGroupMoveMaxHeight() {
		return btnGroupMoveMaxHeight;
	}

	public void setBtnGroupMoveMaxHeight(int btnGroupMoveMaxHeight) {
		this.btnGroupMoveMaxHeight = btnGroupMoveMaxHeight;
	}

	public int getCurBtnGroupHeight() {
		return curBtnGroupHeight;
	}

	public void setCurBtnGroupHeight(int curBtnGroupHeight) {
		this.curBtnGroupHeight = curBtnGroupHeight;
	}

	public ImageView getMaterialUploadBtnGroupScrollBtn() {
		return materialUploadBtnGroupScrollBtn;
	}

	public void setMaterialUploadBtnGroupScrollBtn(ImageView materialUploadBtnGroupScrollBtn) {
		this.materialUploadBtnGroupScrollBtn = materialUploadBtnGroupScrollBtn;
	}

	public RotateAnimation getRotateBackAnimation() {
		return rotateBackAnimation;
	}

	public void setRotateBackAnimation(RotateAnimation rotateBackAnimation) {
		this.rotateBackAnimation = rotateBackAnimation;
	}

	public View getHistoryFrag() {
		return historyFrag;
	}

	public void setHistoryFrag(View historyFrag) {
		this.historyFrag = historyFrag;
	}

	public int getHistoryHeight() {
		return historyHeight;
	}

	public void setHistoryHeight(int historyHeight) {
		this.historyHeight = historyHeight;
	}

	public MyTimer getTimer() {
		return timer;
	}

	public void setTimer(MyTimer timer) {
		this.timer = timer;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

}
