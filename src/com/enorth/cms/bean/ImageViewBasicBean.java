package com.enorth.cms.bean;

import android.widget.ImageView;

public class ImageViewBasicBean extends ImageBasicBean {
	

	private boolean isSelected = false;

	private ImageView imageView;

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

}
