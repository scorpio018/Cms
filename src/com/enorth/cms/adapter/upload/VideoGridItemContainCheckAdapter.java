package com.enorth.cms.adapter.upload;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.enorth.cms.bean.upload.VideoInfo;
import com.enorth.cms.bean.upload.ViewThumbnails;
import com.enorth.cms.enums.Type;
import com.enorth.cms.listener.uploadpic.OnPhotoSelectedListener;
import com.enorth.cms.listener.uploadpic.VideoViewThumbnailsCheckOnClickListener;
import com.enorth.cms.listener.uploadpic.VideoViewThumbnailsOpenOnClickListener;
import com.enorth.cms.utils.ImageLoader;
import com.enorth.cms.view.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class VideoGridItemContainCheckAdapter extends CommonGridItemContainCheckAdapter {

	private Activity activity;
	
	private List<VideoInfo> videoInfos;
	
	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	private static List<String> mSelectedImage = new ArrayList<String>();
	
	public VideoGridItemContainCheckAdapter(Activity activity, List<String> thumbnailDatas, List<VideoInfo> videoInfos) {
		super(activity, thumbnailDatas);
		this.activity = activity;
		super.mDatas = thumbnailDatas;
		this.videoInfos = videoInfos;
	}

	public void changeData(List<String> mDatas) {
		this.mDatas = mDatas;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewThumbnails viewThumbnails = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(R.layout.grid_item, null);
			viewThumbnails = new ViewThumbnails();
			viewThumbnails.setIdItemImage((ImageView) convertView.findViewById(R.id.id_item_image));
//				viewThumbnails.getIdItemImage().setEnabled(false);
			viewThumbnails.setIdItemSelect((ImageButton) convertView.findViewById(R.id.id_item_select));
			convertView.setTag(viewThumbnails);
		} else {
			viewThumbnails = (ViewThumbnails) convertView.getTag();
		}
		viewThumbnails.getIdItemSelect().setBackgroundResource(R.drawable.picture_unselected);
		viewThumbnails.getIdItemImage().setBackgroundResource(R.drawable.pictures_no);
		ImageLoader.getInstance(3, Type.LIFO).loadImageLocal(mDatas.get(position), viewThumbnails.getIdItemImage());
		viewThumbnails.getIdItemImage().setColorFilter(null);
		// 设置ImageView的点击事件
		viewThumbnails.getIdItemSelect().setOnClickListener(
				new VideoViewThumbnailsCheckOnClickListener(viewThumbnails, position, this, activity));
		viewThumbnails.getIdItemImage().setOnClickListener(new VideoViewThumbnailsOpenOnClickListener(activity, this, position));
		/**
		 * 已经选择过的图片，显示出选择过的效果
		 */
		if (getmSelectedImage().contains(mDatas.get(position))) {
			viewThumbnails.getIdItemSelect().setImageResource(R.drawable.pictures_selected);
			viewThumbnails.getIdItemImage().setColorFilter(Color.parseColor("#77000000"));
		} else {
			viewThumbnails.getIdItemSelect().setImageResource(R.drawable.picture_unselected);
			viewThumbnails.getIdItemImage().setColorFilter(Color.parseColor("#00000000"));
		}
		return convertView;
	}

	private OnPhotoSelectedListener onPhotoSelectedListener;

	public void setOnPhotoSelectedListener(OnPhotoSelectedListener onPhotoSelectedListener) {
		this.onPhotoSelectedListener = onPhotoSelectedListener;
	}

	public OnPhotoSelectedListener getOnPhotoSelectedListener() {
		return onPhotoSelectedListener;
	}

	public List<String> getmDatas() {
		return mDatas;
	}

	public void setmDatas(List<String> mDatas) {
		this.mDatas = mDatas;
	}

	public List<VideoInfo> getVideoInfos() {
		return videoInfos;
	}

	public void setVideoInfos(List<VideoInfo> videoInfos) {
		this.videoInfos = videoInfos;
	}

	public static List<String> getmSelectedImage() {
		return mSelectedImage;
	}

	public static void setmSelectedImage(List<String> mSelectedImage) {
		VideoGridItemContainCheckAdapter.mSelectedImage = mSelectedImage;
	}

}
