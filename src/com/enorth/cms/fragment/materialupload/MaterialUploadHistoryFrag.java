package com.enorth.cms.fragment.materialupload;

import com.enorth.cms.view.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 素材上传下面的历史信息（图片、视频历史信息）
 * @author yangyang
 *
 */
public class MaterialUploadHistoryFrag extends Fragment {

	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.material_upload_history_frag, null);
		view = layout;
		return layout;
	}
	
	private void initViewPager() {
		ViewPager materialUploadFileViewPager = (ViewPager) view.findViewById(R.id.materialUploadFileViewPager);
				
	}
	
}
