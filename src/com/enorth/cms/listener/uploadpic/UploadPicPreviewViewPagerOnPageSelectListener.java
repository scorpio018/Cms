package com.enorth.cms.listener.uploadpic;

import java.util.List;
import java.util.Map;

import com.enorth.cms.utils.ImageLoader;
import com.enorth.cms.view.upload.UploadPicPreviewActivity;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;

public class UploadPicPreviewViewPagerOnPageSelectListener implements OnPageChangeListener {
	
	private UploadPicPreviewActivity activity;
	
	private List<View> items;
	
	private List<String> imgDatas;
	
	private int size;
	
	public UploadPicPreviewViewPagerOnPageSelectListener(UploadPicPreviewActivity activity/*List<View> items, List<String> imgDatas*/) {
		this.activity = activity;
		this.items = activity.getItems();
		this.imgDatas = activity.getImgDatas();
		this.size = items.size();
		/*this.items = items;
		this.imgDatas = imgDatas;
		this.size = items.size();*/
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		for (int i = 0; i < size; i++) {
			ImageView imageView = (ImageView) items.get(i);
			
			if ((position - 1) <= i && i <= (position + 1)) {
				ImageLoader.getInstance().loadImageLocal(imgDatas.get(i), imageView);
			} else {
				imageView.setImageBitmap(null);
			}
		}
		activity.setCurCheckPosition(position);
		Map<Integer, String> checkedImgsMap = activity.getCheckedImgsMap();
		activity.setNeedUseCheckBoxChangeEvent(false);
		if (checkedImgsMap.containsKey(position)) {
			activity.getDetermineCB().setChecked(true);
		} else {
			activity.getDetermineCB().setChecked(false);
		}
		activity.setNeedUseCheckBoxChangeEvent(true);
	}

}
