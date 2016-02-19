package com.enorth.cms.fragment.materialupload;

import com.enorth.cms.view.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
/**
 * 素材上传的按钮组（拍照、照片、视频三个按钮）
 * @author yangyang
 *
 */
public class MaterialUploadBtnGroup extends Fragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.material_upload_btn_group, null);
		
		return layout;
	}
}
