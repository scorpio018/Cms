package com.enorth.cms.bean.upload;

import com.enorth.cms.bean.ButtonColorBasicBean;
import com.enorth.cms.view.R;

import android.content.Context;
import android.support.v4.content.ContextCompat;

public class UploadBottomButtonColorBasicBean extends ButtonColorBasicBean {
	
	private Context context;

	public UploadBottomButtonColorBasicBean(Context context) throws Exception {
		super(context);
		this.context = context;
		init();
	}
	
	private void init() {
		setmBgNormalColor(ContextCompat.getColor(context, R.color.common_blue));
		setmBgPressedColor(ContextCompat.getColor(context, R.color.bottom_text_color_blue));
		setmTextNormalColor(ContextCompat.getColor(context, R.color.white));
		setmTextPressedColor(ContextCompat.getColor(context, R.color.white));
	}

}
