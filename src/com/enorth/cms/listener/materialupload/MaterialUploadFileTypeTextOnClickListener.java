package com.enorth.cms.listener.materialupload;

import java.util.List;

import com.enorth.cms.listener.CommonOnClickListener;

import android.view.View;
import android.widget.TextView;

public class MaterialUploadFileTypeTextOnClickListener extends CommonOnClickListener {
	
	private TextView textView;
	
	private List<TextView> materialUploadFileTypeTVs;
	
	public MaterialUploadFileTypeTextOnClickListener(TextView textView) {
		this.textView = textView;
	}

	@Override
	public void onClick(View v) {
		
	}

}
