package com.enorth.cms.fragment.materialupload;

import com.enorth.cms.view.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MaterialUploadFileTypeItemFrag extends Fragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.material_upload_file_type_item_frag, null);
		initShowFileTime(layout);
		return layout;
	}
	
	/**
	 * 初始化文件显示的时间区间
	 * @param view
	 */
	private void initShowFileTime(View view) {
		// 获取当前显示文件的时间的layout
		LinearLayout materialUploadFileTypeLayout = (LinearLayout) view.findViewById(R.id.materialUploadFileTypeLayout);
		materialUploadFileTypeLayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MaterialUploadFileTypeItemFrag.this.getContext(), "点击了最近一天", Toast.LENGTH_SHORT).show();;
			}
		});
		// 获取当前显示文件的时间的文字说明
		TextView materialUploadFileTypeText = (TextView) view.findViewById(R.id.materialUploadFileTypeText);
		materialUploadFileTypeText.setText("最近一天");
	}
	
}
