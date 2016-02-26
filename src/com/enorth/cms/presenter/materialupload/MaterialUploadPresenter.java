package com.enorth.cms.presenter.materialupload;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.enorth.cms.view.material.IMaterialUploadView;

import android.os.Handler;
import android.view.View;

public class MaterialUploadPresenter implements IMaterialUploadPresenter {
	
	private IMaterialUploadView view;
	
	public MaterialUploadPresenter(IMaterialUploadView view) {
		this.view = view;
	}

	@Override
	public void getItemsByFileTypeAndUploadType(String fileType, String uploadType, Handler handler) {
//		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		String resultStr = "文件类型:" + fileType + ";来源类型:" + uploadType;
		view.getItemsByFileTypeAndUploadType(resultStr, handler);
//		params.add(new BasicNameValuePair("fileType", fileType));
	}

}
