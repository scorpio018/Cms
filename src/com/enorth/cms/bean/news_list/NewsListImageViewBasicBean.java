package com.enorth.cms.bean.news_list;

import com.enorth.cms.bean.ImageBasicBean;

import android.widget.ImageView;

public class NewsListImageViewBasicBean extends ImageBasicBean {
	

	private boolean isSelected = false;

	private ImageView imageView;
	
	private String id;
	
	private String name;
	
	private boolean canClick;
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCanClick() {
		return canClick;
	}

	public void setCanClick(boolean canClick) {
		this.canClick = canClick;
	}

}
