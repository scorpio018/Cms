package com.enorth.cms.bean;

import com.enorth.cms.consts.ParamConst;

import android.widget.ImageView;

public class BottomMenuBasicBean extends ImageBasicBean {
	
	private boolean isEnable = false;

	private int canEnableState = ParamConst.CAN_ENABLE_STATE_DEFAULT;
	
	private ImageView imageView;
	
	private String disableHint;

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public int getCanEnableState() {
		return canEnableState;
	}

	public void setCanEnableState(int canEnableState) {
		this.canEnableState = canEnableState;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public String getDisableHint() {
		return disableHint;
	}

	public void setDisableHint(String disableHint) {
		this.disableHint = disableHint;
	}

}
