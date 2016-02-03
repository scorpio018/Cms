package com.enorth.cms.dialog;


import com.enorth.cms.view.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager.LayoutParams;

public class MenuDialog extends Dialog {

	public MenuDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public MenuDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public MenuDialog(Context context) {

		// dialog的视图风格
		super(context, R.style.Theme_Transparent);
		// 设置布局文件
		setContentView(R.layout.menu_dialog);
		// setTitle("Custom Dialog");

		// 单击dialog之外的地方，可以dismiss掉dialog。
		setCanceledOnTouchOutside(true);
		// 设置window属性
		// LayoutParams a = getWindow().getAttributes();
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		// a.gravity = Gravity.TOP;
		// a.dimAmount = 1.0f; // 添加背景遮盖
		// getWindow().setAttributes(a);

		// 在下面这种情况下，后台的activity不会被遮盖，也就是只会遮盖此dialog大小的部分
		LayoutParams a = getWindow().getAttributes();
		a.gravity = Gravity.TOP;
		a.dimAmount = 0.0f; // 去背景遮盖
		getWindow().setAttributes(a);

		// 为你的对话框初始化数据
//		initMenu();
	}

}
