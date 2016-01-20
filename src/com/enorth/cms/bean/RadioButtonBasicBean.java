package com.enorth.cms.bean;

import com.enorth.cms.consts.ParamConst;

import android.widget.RadioButton;

public class RadioButtonBasicBean extends ImageBasicBean {
	private RadioButton radioBtn;
	
	private boolean isSelected = false;
	
	private boolean isEnable = false;
	
	private int canEnableState = ParamConst.CAN_ENABLE_STATE_DEFAULT;

	public RadioButton getRadioBtn() {
		return radioBtn;
	}

	public void setRadioBtn(RadioButton radioBtn) {
		this.radioBtn = radioBtn;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isEnable() {
		return isEnable;
	}
	
	public boolean isEnable(int canEnableState) {
		if (this.canEnableState == canEnableState) {
			setEnable(true);
		} else {
			setEnable(false);
		}
		return isEnable();
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
	
}
