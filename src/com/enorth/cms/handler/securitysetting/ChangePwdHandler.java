package com.enorth.cms.handler.securitysetting;

import com.enorth.cms.handler.UrlRequestCommonHandler;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.securitysetting.IChangePwdView;

import android.R.anim;
import android.os.Message;
import android.widget.Toast;

public class ChangePwdHandler extends UrlRequestCommonHandler {
	
	private IChangePwdView view;
	
	public ChangePwdHandler(IChangePwdView view) {
		this.view = view;
	}

	@Override
	public void success(Message msg) {
		AnimUtil.hideRefreshFrame();
		Toast.makeText(view.getContext(), "修改密码成功", Toast.LENGTH_SHORT).show();
		view.backToPrevActivity();
	}

	@Override
	public void noData(Message msg) {
		
	}

	@Override
	public void error(Message msg) {
		Object obj = msg.obj;
		ViewUtil.showAlertDialog(view.getContext(), obj != null ? obj.toString() : "未知错误");
		AnimUtil.hideRefreshFrame();
	}

	@Override
	public void resultDefault(Message msg) {
		
	}

}
