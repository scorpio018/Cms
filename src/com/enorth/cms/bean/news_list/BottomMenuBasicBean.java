package com.enorth.cms.bean.news_list;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.bean.ImageBasicBean;
import com.enorth.cms.consts.ParamConst;

import android.widget.ImageView;

public class BottomMenuBasicBean extends ImageBasicBean {
	
	private boolean isEnable = false;

	private int canEnableState = ParamConst.CAN_ENABLE_STATE_DEFAULT;
	
	private boolean isMore = false;
	
	private List<BottomMenuBasicBean> items = new ArrayList<BottomMenuBasicBean>();
	
	private ImageView imageView;
	
	private String disableHint;

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public int getCanEnableState() {
		return canEnableState;
	}

	public void setCanEnableState(int canEnableState) {
		this.canEnableState = canEnableState;
	}

	public boolean isMore() {
		return isMore;
	}

	public void setMore(boolean isMore) {
		this.isMore = isMore;
	}

	public List<BottomMenuBasicBean> getItems() {
		return items;
	}

	public void setItems(List<BottomMenuBasicBean> items) {
		this.items = items;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public String getDisableHint() {
		return disableHint;
	}

	public void setDisableHint(String disableHint) {
		this.disableHint = disableHint;
	}

}
