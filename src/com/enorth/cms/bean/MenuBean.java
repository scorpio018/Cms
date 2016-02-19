package com.enorth.cms.bean;

import android.app.Activity;

public class MenuBean {
	private int menuIconResourceId;
	
	private String menuName;
	
	private int menuLayoutId;
	
	private Activity activity;

	public int getMenuIconResourceId() {
		return menuIconResourceId;
	}

	public void setMenuIconResourceId(int menuIconResourceId) {
		this.menuIconResourceId = menuIconResourceId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getMenuLayoutId() {
		return menuLayoutId;
	}

	public void setMenuLayoutId(int menuLayoutId) {
		this.menuLayoutId = menuLayoutId;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	
}
