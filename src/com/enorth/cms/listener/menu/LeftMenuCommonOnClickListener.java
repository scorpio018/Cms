package com.enorth.cms.listener.menu;

import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.view.LeftHorizontalScrollMenu;

import android.view.View;

public class LeftMenuCommonOnClickListener extends CommonOnClickListener {
	
	private LeftHorizontalScrollMenu menu;
	
	public LeftMenuCommonOnClickListener(LeftHorizontalScrollMenu menu) {
		this.menu = menu;
	}

	@Override
	public void onClick(View v) {
		menu.toggle();
	}

}
