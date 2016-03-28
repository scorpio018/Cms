package com.enorth.cms.listener.securitysetting;

import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.view.R;
import com.enorth.cms.view.securitysetting.ChangePwdActivity;
import com.enorth.cms.view.securitysetting.SecuritySettingActivity;

import android.content.Intent;
import android.view.View;

public class SecuritySettingTagItemOnTouchListener extends CommonOnTouchListener {
	
	private SecuritySettingActivity activity;
	
	public SecuritySettingTagItemOnTouchListener(SecuritySettingActivity activity) {
		super.touchSlop = ScreenTools.getTouchSlop(activity);
		this.activity = activity;
		changeColor(ColorUtil.getBgGrayPress(activity), ColorUtil.getBgGrayDefault(activity));
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

	@Override
	public void onImgChangeDo(View v) {
		Intent intent = new Intent();
		intent.setClass(activity, ChangePwdActivity.class);
		activity.startActivity(intent);
	}

}
