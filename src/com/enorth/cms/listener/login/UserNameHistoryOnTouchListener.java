package com.enorth.cms.listener.login;

import com.enorth.cms.bean.login.LoginBean;
import com.enorth.cms.fragment.login.LoginFrag;
import com.enorth.cms.listener.popup.PopupWindowContainDelMarkOnTouchListener;
import com.enorth.cms.utils.ScreenTools;

import android.widget.LinearLayout;

public abstract class UserNameHistoryOnTouchListener extends PopupWindowContainDelMarkOnTouchListener {
	
	private LoginFrag frag;

	public UserNameHistoryOnTouchListener(LoginFrag frag, LinearLayout layout) {
		super(frag.getContext(), layout);
		super.touchSlop = ScreenTools.getTouchSlop(context);
		this.frag = frag;
	}
	
	@Override
	public void checkItem(String curCheckedText) {
		frag.getUserNameET().setText(curCheckedText);
		LoginBean bean = frag.getUsersMap().get(curCheckedText);
		Boolean rememberUser = bean.isRememberUser();
		if (rememberUser) {
			frag.getPwdET().setText(bean.getPassword());
		}
		frag.getRememberPwdCB().setChecked(rememberUser);
		frag.getLoginSubmitBtn().setClickable(rememberUser);
		frag.checkCanSubmitEvent();
	}

}
