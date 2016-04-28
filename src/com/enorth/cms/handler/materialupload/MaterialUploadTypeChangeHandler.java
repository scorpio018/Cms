package com.enorth.cms.handler.materialupload;

import com.enorth.cms.view.material.MaterialUploadActivity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class MaterialUploadTypeChangeHandler extends Handler {
	
	private MaterialUploadActivity activity;

	public MaterialUploadTypeChangeHandler(MaterialUploadActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		Log.e("MaterialUploadTypeChangeHandler.msg", msg.obj.toString());
		Toast.makeText(activity, msg.obj.toString(), Toast.LENGTH_SHORT).show();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
