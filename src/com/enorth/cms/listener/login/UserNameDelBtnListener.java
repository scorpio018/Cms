package com.enorth.cms.listener.login;

import com.enorth.cms.bean.login.LoginBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.fragment.login.LoginFrag;
import com.enorth.cms.listener.popup.PopupWindowContainDelMarkOnTouchListener;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.StringUtil;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class UserNameDelBtnListener extends PopupWindowContainDelMarkOnTouchListener {
	
//	private Context context;
	private LoginFrag loginFrag;
	
	public UserNameDelBtnListener(LoginFrag loginFrag, LinearLayout layout) {
		super(loginFrag.getContext(), layout);
		this.loginFrag = loginFrag;
		
	}

	@Override
	public void checkItem(String curCheckedText) {
		String[] usersStr = SharedPreUtil.getStringArray(context, ParamConst.REMEMBERED_USER, ParamConst.LOGIN_USER);
		String[] newUsersStr = new String[usersStr.length - 1];
		int i = 0;
		for (String userStr : usersStr) {
			if (StringUtil.isNotEmpty(userStr)) {
				LoginBean savedUser = (LoginBean) SharedPreUtil.deSerializeObject(userStr);
				// 将不需要删除的用户存入新的数组中
				if (savedUser.getUserName().equals(curCheckedText)) {
					loginFrag.getUserNames().remove(curCheckedText);
				} else {
					newUsersStr[i++] = userStr;
				}
			}
		}
		if (newUsersStr.length == 1 && StringUtil.isEmpty(newUsersStr[0])) {
			StaticUtil.removeCurLoginBean(context);
		}
		SharedPreUtil.put(context, ParamConst.REMEMBERED_USER, ParamConst.LOGIN_USER, newUsersStr);
	}

	@Override
	public void onImgChangeEnd(View v) {
		
	}

}
