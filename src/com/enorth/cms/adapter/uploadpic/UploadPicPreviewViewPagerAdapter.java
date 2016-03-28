package com.enorth.cms.adapter.uploadpic;

import java.util.List;

import com.enorth.cms.adapter.CommonViewPagerAdapter;

import android.view.View;
import android.widget.ImageView;

public class UploadPicPreviewViewPagerAdapter extends CommonViewPagerAdapter {
	
	private List<String> imgDatas;

	public UploadPicPreviewViewPagerAdapter(List<View> viewList) {
		super(viewList);
	}
	
	public UploadPicPreviewViewPagerAdapter(List<View> viewList, List<String> imgDatas) {
		super(viewList);
		this.imgDatas = imgDatas;
	}
	
	

}
