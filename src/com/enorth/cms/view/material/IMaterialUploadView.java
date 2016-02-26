package com.enorth.cms.view.material;

import android.os.Handler;

public interface IMaterialUploadView {
	
	public void getItemsByFileTypeAndUploadType(String resultStr, Handler handler);
	
	public void changeFileType(String fileType);
}
