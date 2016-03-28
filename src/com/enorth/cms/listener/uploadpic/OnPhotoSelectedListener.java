package com.enorth.cms.listener.uploadpic;

import java.util.List;

public interface OnPhotoSelectedListener {
	public void photoClick(List<String> number);
	public void takePhoto();
}
