package com.enorth.cms.listener.uploadpic;

import com.enorth.cms.adapter.upload.VideoGridItemContainCheckAdapter;
import com.enorth.cms.adapter.upload.VideoGridItemContainCheckAdapter;
import com.enorth.cms.bean.upload.ViewThumbnails;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.view.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

public class VideoViewThumbnailsCheckOnClickListener extends CommonOnClickListener {
	private ViewThumbnails viewThumbnails;
	private int position;
	private VideoGridItemContainCheckAdapter adapter;

	private Context context;

	public VideoViewThumbnailsCheckOnClickListener(ViewThumbnails viewThumbnails, int position, VideoGridItemContainCheckAdapter adapter,
			Context context) {
		this.viewThumbnails = viewThumbnails;
		this.position = position;
		this.adapter = adapter;
		this.context = context;
	}

	@Override
	public void onClick(View v) {
		// 已经选择过该图片
		String imagePath = adapter.getmDatas().get(position);
		if (VideoGridItemContainCheckAdapter.getmSelectedImage().contains(imagePath)) {
			VideoGridItemContainCheckAdapter.getmSelectedImage().remove(imagePath);
			viewThumbnails.getIdItemSelect().setImageResource(R.drawable.picture_unselected);
			viewThumbnails.getIdItemImage().setColorFilter(null);
		} else {// 未选择该图片
			if (VideoGridItemContainCheckAdapter.getmSelectedImage().size() >= ParamConst.MAX_SELECT_VIDEO_COUNT) {
				Toast.makeText(context, "最多只能选" + ParamConst.MAX_SELECT_VIDEO_COUNT + "条视频", Toast.LENGTH_SHORT).show();
				return;
			}
			VideoGridItemContainCheckAdapter.getmSelectedImage().add(imagePath);
			viewThumbnails.getIdItemSelect().setImageResource(R.drawable.pictures_selected);
			viewThumbnails.getIdItemImage().setColorFilter(Color.parseColor("#77000000"));
		}
	}
}
