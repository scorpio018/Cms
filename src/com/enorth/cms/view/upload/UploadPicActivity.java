package com.enorth.cms.view.upload;

import java.util.List;

import com.enorth.cms.adapter.uploadpic.UploadPicGridViewAdapter;
import com.enorth.cms.utils.CameraUtil;
import com.enorth.cms.view.BaseActivity;
import com.enorth.cms.view.R;
import com.enorth.cms.widget.gridview.materialupload.MaterialUploadHistoryGridView;

import android.app.Activity;
import android.os.Bundle;

public class UploadPicActivity extends BaseActivity implements IUploadPicView {
	
	private MaterialUploadHistoryGridView gallery;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		initGridView();
	}
	
	private void initGridView() {
		/*gallery = (MaterialUploadHistoryGridView) findViewById(R.id.galleryGridView);
		List<String> photoList = CameraUtil.getPhotoList(this);
		UploadPicGridViewAdapter adapter = new UploadPicGridViewAdapter(this, 0, photoList.toArray(), gallery);
		gallery.setAdapter(adapter);*/
	}
}
