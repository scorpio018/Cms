package com.enorth.cms.listener.login;

import com.enorth.cms.fragment.login.ScanFrag;
import com.enorth.cms.listener.popup.PopupWindowContainDelMarkOnTouchListener;
import com.enorth.cms.utils.ScreenTools;

import android.widget.LinearLayout;

public abstract class SystemChooseTextViewOnTouchListener extends PopupWindowContainDelMarkOnTouchListener {
	
	private ScanFrag frag;

	public SystemChooseTextViewOnTouchListener(ScanFrag frag, LinearLayout layout) {
		super(frag.getContext(), layout);
		super.touchSlop = ScreenTools.getTouchSlop(context);
		this.frag = frag;
	}

	@Override
	public void checkItem(String curCheckedText) {
		frag.getSystemChooseTV().setText(curCheckedText);
	}
	
}
