package com.enorth.cms.listener.uploadpic;

import java.util.Map;

import com.enorth.cms.adapter.upload.ImageGridItemContainCheckAdapter;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnCheckedChangeListener;
import com.enorth.cms.view.upload.UploadPicPreviewActivity;

import android.widget.CompoundButton;
import android.widget.Toast;

public class DetermineCheckBoxOnCheckedChangeListener extends CommonOnCheckedChangeListener {
	
	private UploadPicPreviewActivity activity;
	
	public DetermineCheckBoxOnCheckedChangeListener(UploadPicPreviewActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// 当刚进入选中图片的位置，或者滚动到选中的图片的位置，或者当前图片已经选中的时候，不进入check方法
		if (activity.isNeedUseCheckBoxChangeEvent()) {
			check(buttonView, isChecked);
		}
	}
	
	private void check(CompoundButton buttonView, boolean flag) {
		int curCheckPosition = activity.getCurCheckPosition();
		Map<Integer, String> checkedImgsMap = activity.getCheckedImgsMap();
		if (flag) {
			if (ImageGridItemContainCheckAdapter.getmSelectedImage().size() >= ParamConst.MAX_SELECT_IMAGE_COUNT) {
				Toast.makeText(activity, "最多只能选" + ParamConst.MAX_SELECT_IMAGE_COUNT + "张", Toast.LENGTH_SHORT).show();
				buttonView.setChecked(false);
				return;
			}
			// 选中
			if (!checkedImgsMap.containsKey(curCheckPosition)) {
				checkedImgsMap.put(curCheckPosition, activity.getImgDatas().get(curCheckPosition));
				ImageGridItemContainCheckAdapter.getmSelectedImage().add(activity.getImgDatas().get(curCheckPosition));
			}
		} else {
			// 未选中
			if (checkedImgsMap.containsKey(curCheckPosition)) {
				ImageGridItemContainCheckAdapter.getmSelectedImage().remove(checkedImgsMap.get(curCheckPosition));
				checkedImgsMap.remove(curCheckPosition);
			}
		}
		activity.initPicComplete();
	}

}
