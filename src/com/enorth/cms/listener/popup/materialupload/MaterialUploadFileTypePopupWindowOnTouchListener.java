package com.enorth.cms.listener.popup.materialupload;

import com.enorth.cms.fragment.materialupload.MaterialUploadFileTypeItemFrag;
import com.enorth.cms.listener.popup.PopupWindowOnTouchListener;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

public abstract class MaterialUploadFileTypePopupWindowOnTouchListener extends PopupWindowOnTouchListener {
	
	private MaterialUploadFileTypeItemFrag fragment;

	public MaterialUploadFileTypePopupWindowOnTouchListener(Context context, LinearLayout layout) {
		super(context, layout);
	}
	
	public MaterialUploadFileTypePopupWindowOnTouchListener(Context context, MaterialUploadFileTypeItemFrag fragment, LinearLayout layout) {
		super(context, layout);
		this.fragment = fragment;
	}

	@Override
	public void checkItem(String curCheckedText) {
		fragment.getMaterialUploadFileTypeText().setText(curCheckedText);
		fragment.setCurMaterialUploadFileTypeText(curCheckedText);
		Toast.makeText(super.context, "时间区间改为【" + curCheckedText + "】", Toast.LENGTH_SHORT).show();
		fragment.refreshData();
	}

}
