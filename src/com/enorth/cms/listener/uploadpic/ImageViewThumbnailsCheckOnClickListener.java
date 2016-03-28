package com.enorth.cms.listener.uploadpic;

import com.enorth.cms.adapter.upload.ImageGridItemContainCheckAdapter;
import com.enorth.cms.bean.upload.ViewThumbnails;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.view.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

public class ImageViewThumbnailsCheckOnClickListener extends CommonOnClickListener {
	private ViewThumbnails viewThumbnails;
	private int position;
	private ImageGridItemContainCheckAdapter adapter;

	private Context context;

	public ImageViewThumbnailsCheckOnClickListener(ViewThumbnails viewThumbnails, int position, ImageGridItemContainCheckAdapter adapter,
			Context context) {
		this.viewThumbnails = viewThumbnails;
		this.position = position;
		this.adapter = adapter;
		this.context = context;
	}

	@Override
	public void onClick(View v) {
		// 已经选择过该图片
		String imagePath = /*adapter.getmDirPath() + "/" + */adapter.getmDatas().get(position - 1);
		if (ImageGridItemContainCheckAdapter.getmSelectedImage().contains(imagePath)) {
			ImageGridItemContainCheckAdapter.getmSelectedImage().remove(imagePath);
			viewThumbnails.getIdItemSelect().setImageResource(R.drawable.picture_unselected);
			viewThumbnails.getIdItemImage().setColorFilter(null);
		} else {// 未选择该图片
			if (ImageGridItemContainCheckAdapter.getmSelectedImage().size() >= ParamConst.MAX_SELECT_IMAGE_COUNT) {
				Toast.makeText(context, "最多只能选" + ParamConst.MAX_SELECT_IMAGE_COUNT + "张", Toast.LENGTH_SHORT).show();
				return;
			}
			ImageGridItemContainCheckAdapter.getmSelectedImage().add(imagePath);
			viewThumbnails.getIdItemSelect().setImageResource(R.drawable.pictures_selected);
			viewThumbnails.getIdItemImage().setColorFilter(Color.parseColor("#77000000"));
		}
		adapter.getOnPhotoSelectedListener().photoClick(ImageGridItemContainCheckAdapter.getmSelectedImage());
	}
}
