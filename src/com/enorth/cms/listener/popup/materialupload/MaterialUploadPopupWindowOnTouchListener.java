package com.enorth.cms.listener.popup.materialupload;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.popup.PopupWindowOnTouchListener;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.view.material.MaterialUploadActivity;

import android.widget.LinearLayout;

public abstract class MaterialUploadPopupWindowOnTouchListener extends PopupWindowOnTouchListener {
	
	private MaterialUploadActivity activity;

	public MaterialUploadPopupWindowOnTouchListener(MaterialUploadActivity activity, LinearLayout layout) {
		super(activity, layout);
		this.activity = activity;
	}

	@Override
	public void checkItem(String curCheckedText) {
		activity.getMaterialFromTypeTV().setText(curCheckedText);
		SharedPreUtil.put(activity, ParamConst.CUR_CHOOSE_CHANNEL_TYPE, curCheckedText);
		activity.setCurMaterialUploadType(curCheckedText);
		if (curCheckedText.equals(ParamConst.MATERIAL_UPLOAD_TYPE_FROM_PHONE)) {
			activity.getPresenter().getItemsByFileTypeAndUploadType(activity.getCurFileType(), ParamConst.MATERIAL_UPLOAD_TYPE_FROM_PHONE, activity.getMaterialUploadTypeChangeHandler());
//			Toast.makeText(activity, "点击了" + ParamConst.MATERIAL_UPLOAD_TYPE_FROM_PHONE, Toast.LENGTH_SHORT).show();
		} else if (curCheckedText.equals(ParamConst.MATERIAL_UPLOAD_TYPE_FROM_ALL)) {
			activity.getPresenter().getItemsByFileTypeAndUploadType(activity.getCurFileType(), ParamConst.MATERIAL_UPLOAD_TYPE_FROM_ALL, activity.getMaterialUploadTypeChangeHandler());
//			Toast.makeText(activity, "点击了" + ParamConst.MATERIAL_UPLOAD_TYPE_FROM_ALL, Toast.LENGTH_SHORT).show();
		}
	}

}
