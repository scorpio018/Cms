package com.enorth.cms.widget.linearlayout;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.handler.materialupload.MaterialUploadFragMoveHandler;
import com.enorth.cms.task.MyTimer;
import com.enorth.cms.view.R;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;

public class MaterialUploadFragLinearLayout extends LinearLayout {
	
	private boolean once = true;
	
	private LinearLayout parentLayout;
	
	private int parentLayoutHeight;
	
	private View titleView;
	/**
	 * 标头的高度
	 */
	private int titleViewHeight;
	/**
	 * 按钮组的fragment
	 */
	private View btnGroupFrag;
	/**
	 * 按钮组的fragment的top值
	 */
	private int btnGroupFragTop;
	/**
	 * 按钮组的fragment的bottom值
	 */
	private int btnGroupFragBottom;
	/**
	 * 按钮组的fragment的高度
	 */
	private int btnGroupFragHeight;
	/**
	 * 按钮组的fragment的宽度
	 */
	private int btnGroupFragWidth;
	/**
	 * 按钮组（拍照、图片、视频）的layout
	 */
	private LinearLayout btnGroupLayout;
	/**
	 * 按钮组的fragment的高度
	 */
	private int btnGroupHeight;
	/**
	 * 按钮组的fragment的宽度
	 */
	private int btnGroupWidth;
	/**
	 * 进行180°旋转的向上按钮图片
	 */
	private ImageView materialUploadBtnGroupScrollBtn;
	/**
	 * 进行180°旋转的向上按钮的高度
	 */
	private int btnGroupScrollHeight;
	/**
	 * 进行180°旋转的向上按钮的宽度
	 */
	private int btnGroupScrollWidth;
	/**
	 * 用按钮组的fragment的高度减去进行180°旋转的向上按钮的高度得到的最终值
	 */
	private int btnGroupEndHeight;
	/**
	 * 180°旋转的动画
	 */
	private RotateAnimation rotateAnimation;
	/**
	 * 回转180°的动画
	 */
	private RotateAnimation rotateBackAnimation;
	/**
	 * 附件历史的fragment
	 */
	private View historyFrag;
	/**
	 * 附件历史的fragment的top值
	 */
	private int historyTop;
	/**
	 * 附件历史的fragment的bottom值
	 */
	private int historyBottom;
	/**
	 * 附件历史的fragment的高度
	 */
	private int historyHeight;
	/**
	 * 附件里是的fragment的宽度
	 */
	private int historyWidth;
	/**
	 * 当前移动的高度（在收回时，是从btnGroupScrollHeight到0；在展开时，是从0到btnGroupScrollHeight）
	 */
	private int curMoveHeight;
	/**
	 * 持续进行按钮组的收回/展开操作用到的定时器
	 */
	private MyTimer timer;
	/**
	 * fragment收回/展开的handler
	 */
	private MaterialUploadFragMoveHandler handler;
	/**
	 * 当前状态是否为收回状态：true表示收回，false表示展开
	 */
	private boolean isOpen = true;
	/**
	 * 是否已经收回/展开完毕
	 */
	private boolean isEnd = false;
	/**
	 * 两个fragment的状态之已经展开
	 */
	private int curFragLayoutState = ParamConst.CUR_FRAG_LAYOUT_STATE_IS_OPENED;
	
	private Context context;
	
	public MaterialUploadFragLinearLayout(Context context) {
		super(context);
		this.context = context;
	}
	
	public MaterialUploadFragLinearLayout(Context context, AttributeSet attr) {
		super(context, attr);
		this.context = context;
	}
	
	public MaterialUploadFragLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
	}
	
	public MaterialUploadFragLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		this.context = context;
	}
	
	public void startMove() {
		Log.e("startMove()", "调用了此方法");
		if (once) {
			// 获取标头，用于动画改变时，能不覆盖标头
			parentLayout = (LinearLayout) getParent();
			parentLayoutHeight = parentLayout.getMeasuredHeight();
			titleView = parentLayout.getChildAt(0);
			titleViewHeight = titleView.getMeasuredHeight();
			// 获取按钮组fragment
			btnGroupFrag = getChildAt(0);
			btnGroupFragTop = btnGroupFrag.getTop();
			btnGroupFragBottom = btnGroupFrag.getBottom();
			btnGroupFragHeight = btnGroupFrag.getMeasuredHeight();
			btnGroupFragWidth = btnGroupFrag.getMeasuredWidth();
			btnGroupLayout = (LinearLayout) btnGroupFrag.findViewById(R.id.materialUploadBtnGroupLayout);
			materialUploadBtnGroupScrollBtn = (ImageView) btnGroupFrag.findViewById(R.id.materialUploadBtnGroupScrollBtn);
			rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.reverse_anim);
			rotateBackAnimation = (RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.reverse_back_anim);
			btnGroupHeight = btnGroupLayout.getMeasuredHeight();
			btnGroupWidth = btnGroupLayout.getMeasuredWidth();
			btnGroupScrollHeight = materialUploadBtnGroupScrollBtn.getMeasuredHeight();
			btnGroupScrollWidth = materialUploadBtnGroupScrollBtn.getMeasuredWidth();
			btnGroupEndHeight = btnGroupFragHeight - btnGroupScrollHeight;
			// 获取附件历史fragment
			historyFrag = getChildAt(1);
			historyTop = historyFrag.getTop();
			historyBottom = historyFrag.getBottom();
			historyHeight = historyFrag.getMeasuredWidth();
			historyWidth = historyFrag.getMeasuredHeight();
			handler = new MaterialUploadFragMoveHandler(this);
			once = false;
		}
		switch (curFragLayoutState) {
		case ParamConst.CUR_FRAG_LAYOUT_STATE_IS_OPENED:
		case ParamConst.CUR_FRAG_LAYOUT_STATE_IS_CLOSED:
			isEnd = false;
			handler.setFinished(false);
			timer = new MyTimer(handler);
			timer.schedule(5);
			break;
		case ParamConst.CUR_FRAG_LAYOUT_STATE_CLOSING:
		case ParamConst.CUR_FRAG_LAYOUT_STATE_OPENING:
		default:
			break;
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		/*if (!once) {
			historyFrag.measure(widthMeasureSpec, historyHeight);
		}*/
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (once) {
			super.onLayout(changed, l, t, r, b);
		} else {
			// curMoveHeight是从与btnGroupHeight相同开始的，当收起的时候，curMoveHeight将会最终变为0，所以top坐标为-(btnGroupHeight - curMoveHeight)
//			btnGroupLayout.layout(0, titleViewHeight - (btnGroupHeight - curMoveHeight), btnGroupWidth, titleViewHeight + curMoveHeight);
			// top坐标curMoveHeight是从curMoveHeight是从与btnGroupHeight相同开始，到0结束或反过来
//			materialUploadBtnGroupScrollBtn.layout(0, titleViewHeight + curMoveHeight, btnGroupScrollWidth, curMoveHeight + btnGroupScrollHeight + titleViewHeight);
			btnGroupFrag.layout(0, btnGroupFragTop - curMoveHeight, btnGroupFragWidth, btnGroupFragBottom - curMoveHeight);
			historyFrag.layout(0, historyTop - curMoveHeight, historyWidth, historyBottom);
//			historyFrag.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
	}

	public LinearLayout getParentLayout() {
		return parentLayout;
	}

	public void setParentLayout(LinearLayout parentLayout) {
		this.parentLayout = parentLayout;
	}

	public int getParentLayoutHeight() {
		return parentLayoutHeight;
	}

	public void setParentLayoutHeight(int parentLayoutHeight) {
		this.parentLayoutHeight = parentLayoutHeight;
	}

	public View getTitleView() {
		return titleView;
	}

	public void setTitleView(View titleView) {
		this.titleView = titleView;
	}

	public int getTitleViewHeight() {
		return titleViewHeight;
	}

	public void setTitleViewHeight(int titleViewHeight) {
		this.titleViewHeight = titleViewHeight;
	}

	public View getBtnGroupFrag() {
		return btnGroupFrag;
	}

	public void setBtnGroupFrag(View btnGroupFrag) {
		this.btnGroupFrag = btnGroupFrag;
	}

	public int getBtnGroupFragTop() {
		return btnGroupFragTop;
	}

	public void setBtnGroupFragTop(int btnGroupFragTop) {
		this.btnGroupFragTop = btnGroupFragTop;
	}

	public int getBtnGroupFragBottom() {
		return btnGroupFragBottom;
	}

	public void setBtnGroupFragBottom(int btnGroupFragBottom) {
		this.btnGroupFragBottom = btnGroupFragBottom;
	}

	public int getBtnGroupFragHeight() {
		return btnGroupFragHeight;
	}

	public void setBtnGroupFragHeight(int btnGroupFragHeight) {
		this.btnGroupFragHeight = btnGroupFragHeight;
	}

	public int getBtnGroupFragWidth() {
		return btnGroupFragWidth;
	}

	public void setBtnGroupFragWidth(int btnGroupFragWidth) {
		this.btnGroupFragWidth = btnGroupFragWidth;
	}

	public LinearLayout getBtnGroupLayout() {
		return btnGroupLayout;
	}

	public void setBtnGroupLayout(LinearLayout btnGroupLayout) {
		this.btnGroupLayout = btnGroupLayout;
	}

	public int getBtnGroupHeight() {
		return btnGroupHeight;
	}

	public void setBtnGroupHeight(int btnGroupHeight) {
		this.btnGroupHeight = btnGroupHeight;
	}

	public int getBtnGroupWidth() {
		return btnGroupWidth;
	}

	public void setBtnGroupWidth(int btnGroupWidth) {
		this.btnGroupWidth = btnGroupWidth;
	}

	public ImageView getMaterialUploadBtnGroupScrollBtn() {
		return materialUploadBtnGroupScrollBtn;
	}

	public void setMaterialUploadBtnGroupScrollBtn(ImageView materialUploadBtnGroupScrollBtn) {
		this.materialUploadBtnGroupScrollBtn = materialUploadBtnGroupScrollBtn;
	}

	public int getBtnGroupScrollHeight() {
		return btnGroupScrollHeight;
	}

	public void setBtnGroupScrollHeight(int btnGroupScrollHeight) {
		this.btnGroupScrollHeight = btnGroupScrollHeight;
	}

	public int getBtnGroupScrollWidth() {
		return btnGroupScrollWidth;
	}

	public void setBtnGroupScrollWidth(int btnGroupScrollWidth) {
		this.btnGroupScrollWidth = btnGroupScrollWidth;
	}

	public int getBtnGroupEndHeight() {
		return btnGroupEndHeight;
	}

	public void setBtnGroupEndHeight(int btnGroupEndHeight) {
		this.btnGroupEndHeight = btnGroupEndHeight;
	}

	public RotateAnimation getRotateBackAnimation() {
		return rotateBackAnimation;
	}

	public void setRotateBackAnimation(RotateAnimation rotateBackAnimation) {
		this.rotateBackAnimation = rotateBackAnimation;
	}

	public RotateAnimation getRotateAnimation() {
		return rotateAnimation;
	}

	public void setRotateAnimation(RotateAnimation rotateAnimation) {
		this.rotateAnimation = rotateAnimation;
	}

	public View getHistoryFrag() {
		return historyFrag;
	}

	public void setHistoryFrag(View historyFrag) {
		this.historyFrag = historyFrag;
	}

	public int getHistoryTop() {
		return historyTop;
	}

	public void setHistoryTop(int historyTop) {
		this.historyTop = historyTop;
	}

	public int getHistoryBottom() {
		return historyBottom;
	}

	public void setHistoryBottom(int historyBottom) {
		this.historyBottom = historyBottom;
	}

	public int getHistoryHeight() {
		return historyHeight;
	}

	public void setHistoryHeight(int historyHeight) {
		this.historyHeight = historyHeight;
	}

	public int getHistoryWidth() {
		return historyWidth;
	}

	public void setHistoryWidth(int historyWidth) {
		this.historyWidth = historyWidth;
	}

	public int getCurMoveHeight() {
		return curMoveHeight;
	}

	public void setCurMoveHeight(int curMoveHeight) {
		this.curMoveHeight = curMoveHeight;
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

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public int getCurFragLayoutState() {
		return curFragLayoutState;
	}

	public void setCurFragLayoutState(int curFragLayoutState) {
		this.curFragLayoutState = curFragLayoutState;
	}

}
