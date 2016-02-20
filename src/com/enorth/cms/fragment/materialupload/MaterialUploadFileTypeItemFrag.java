package com.enorth.cms.fragment.materialupload;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.enorth.cms.adapter.materialupload.MaterialUploadFileTypeItemListViewAdapter;
import com.enorth.cms.utils.LayoutParamsUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.widget.listview.CommonListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MaterialUploadFileTypeItemFrag extends Fragment {
	private LinearLayout layout;
	
	private LayoutInflater inflater;
	
	private CommonListView listView;
	
	private List<View> items = new ArrayList<View>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		layout = (LinearLayout) inflater.inflate(R.layout.material_upload_file_type_item_frag, null);
		this.inflater = inflater;
		initShowFileTime();
//		initMaterialUploadDetail();
		listView = (CommonListView) layout.findViewById(R.id.materialUploadFileItemListView);
		for (int i = 0; i < 6; i++) {
			initMaterialUploadDetail();
		}
		MaterialUploadFileTypeItemListViewAdapter adapter = new MaterialUploadFileTypeItemListViewAdapter(items);
		listView.setAdapter(adapter);
		return layout;
	}
	
	/**
	 * 初始化文件显示的时间区间
	 * @param view
	 */
	private void initShowFileTime() {
		// 获取当前显示文件的时间的layout
		LinearLayout materialUploadFileTypeLayout = (LinearLayout) layout.findViewById(R.id.materialUploadFileTypeLayout);
		materialUploadFileTypeLayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MaterialUploadFileTypeItemFrag.this.getContext(), "点击了最近一天", Toast.LENGTH_SHORT).show();;
			}
		});
		// 获取当前显示文件的时间的文字说明
		TextView materialUploadFileTypeText = (TextView) layout.findViewById(R.id.materialUploadFileTypeText);
		materialUploadFileTypeText.setText("最近一天");
	}
	
	private Date now = new Date();
	
	private Random rd = new Random();
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private void initMaterialUploadDetail() {
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.material_upload_file_history_item, null);
		TextView uploadTimeTV = (TextView) layout.findViewById(R.id.uploadTimeTV);
		uploadTimeTV.setText(sdf.format(now));
		now.setTime(now.getTime() - 60000);
		TextView uploadTitleTV = (TextView) layout.findViewById(R.id.uploadTitleTV);
		uploadTitleTV.setText("我上传的标题");
		GridLayout fileGridLayout = (GridLayout) layout.findViewById(R.id.fileGridLayout);
		int n = rd.nextInt(7) + 6;
		for (int i = 0; i < n; i++) {
			ImageView iv = new ImageView(this.getContext());
			LayoutParams initWrapLayout = LayoutParamsUtil.initWrapLayout();
			iv.setBackgroundResource(R.drawable.ic_launcher);
			fileGridLayout.addView(iv, initWrapLayout);
		}
		items.add(layout);
	}
	
}
