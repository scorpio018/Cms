package com.enorth.cms.listener.login;

import com.enorth.cms.fragment.login.LoginFrag;
import com.enorth.cms.listener.CommonTextWatcher;

import android.text.Editable;

public class UserNameAndPwdTextWatcher extends CommonTextWatcher {
	
	private LoginFrag frag;
	
	public UserNameAndPwdTextWatcher(LoginFrag frag) {
		this.frag = frag;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		frag.checkCanSubmitEvent();
	}

}
