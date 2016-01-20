package com.enorth.cms.bean;

import android.view.View;

public class ImageBasicBean {
	private int imageCheckedResource;

	private int imageUncheckResource;
	
	private int imageDisableResource;

	private String textContent;
	
	private View view;

	public int getImageCheckedResource() {
		return imageCheckedResource;
	}

	public void setImageCheckedResource(int imageCheckedResource) {
		this.imageCheckedResource = imageCheckedResource;
	}

	public int getImageUncheckResource() {
		return imageUncheckResource;
	}

	public void setImageUncheckResource(int imageUncheckResource) {
		this.imageUncheckResource = imageUncheckResource;
	}

	public int getImageDisableResource() {
		return imageDisableResource;
	}

	public void setImageDisableResource(int imageDisableResource) {
		this.imageDisableResource = imageDisableResource;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

}
