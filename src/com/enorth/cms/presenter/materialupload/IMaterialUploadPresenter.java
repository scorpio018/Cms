package com.enorth.cms.presenter.materialupload;

import android.os.Handler;

public interface IMaterialUploadPresenter {
	
	public void getItemsByFileTypeAndUploadType(String fileType, String uploadType, Handler handler);
}
