package com.enorth.cms.listener.imageview;

import com.enorth.cms.bean.ImageViewBasicBean;
import com.enorth.cms.listener.CommonOnTouchListener;

public abstract class ImageViewOnTouchListener extends CommonOnTouchListener {
	
	private ImageViewBasicBean bean;
	
	public ImageViewOnTouchListener(ImageViewBasicBean bean, int touchSlop) {
		this.bean = bean;
		this.touchSlop = touchSlop;
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

	/*@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			downX = v.getScaleX();
			downY = v.getScaleY();
			Log.w("MotionEvent.ACTION_DOWN", "【x:" + downX + "、y:" + downY + "】");
			isClick = true;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			Log.w("MotionEvent.ACTION_POINTER_DOWN", "多指放下动作记录");
		case MotionEvent.ACTION_POINTER_UP:
			Log.w("MotionEvent.ACTION_POINTER_UP", "多指抬起动作记录");
			isClick = false;
			break;
		case MotionEvent.ACTION_MOVE:
			moveX = v.getScaleX();
			moveY = v.getScaleY();
			double distinct = Math.sqrt(Math.pow(Math.abs(downX - moveX), 2) + Math.pow(Math.abs(downY - moveY), 2));
			Log.w("MotionEvent.ACTION_MOVE", "【x:" + moveX + "、y:" + moveY + "、distinct:" + distinct + "、touchSlop:" + touchSlop + "】");
			if (distinct >= touchSlop) {
				isClick = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (isClick) {
				boolean isContinue = onImgChangeBegin();
				if (isContinue) {
					if (bean.isSelected()) {
						bean.getImageView().setImageResource(bean.getImageUncheckResource());
						bean.getImageView().setSelected(false);
						bean.setSelected(false);
					} else {
//						checkBtn.setImageResource(R.drawable.check_btn);
//						checkBtn.setSelected(true);
						bean.getImageView().setImageResource(bean.getImageCheckedResource());
						bean.getImageView().setSelected(true);
						bean.setSelected(true);
					}
				}
			}
			onImgChangeEnd();
			Log.w("MotionEvent.ACTION_UP", "isClick:" + isClick + "，选中状态为【" + bean.isSelected() + "】");
			break;
		default:
			Log.w("default", String.valueOf(event.getActionMasked()));
			break;
		}
		return true;
	}*/

}
