package com.enorth.cms.adapter.upload;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.bean.upload.ViewTakePhoto;
import com.enorth.cms.bean.upload.ViewThumbnails;
import com.enorth.cms.enums.Type;
import com.enorth.cms.listener.uploadpic.AddPicOnTouchListener;
import com.enorth.cms.listener.uploadpic.OnPhotoSelectedListener;
import com.enorth.cms.listener.uploadpic.ImageViewThumbnailsCheckOnClickListener;
import com.enorth.cms.listener.uploadpic.ImageViewThumbnailsDelOnClickListener;
import com.enorth.cms.listener.uploadpic.ImageViewThumbnailsOpenOnClickListener;
import com.enorth.cms.utils.ImageLoader;
import com.enorth.cms.view.R;
import com.enorth.cms.view.upload.UploadPicFinishCheckActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageGridItemContainDelAdapter extends BaseAdapter {
	/**
	 * view的种类个数（氛围TYPE_1、TYPE_2两种）
	 */
	final int VIEW_TYPE = 2;
	/**
	 * 继续添加图片
	 */
	final int TYPE_1 = 0;
	/**
	 * 图片
	 */
	final int TYPE_2 = 1;

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
//	private static List<String> mSelectedImage = new ArrayList<String>();

	/**
	 * 文件夹路径
	 */
//	private String mDirPath;

	private UploadPicFinishCheckActivity activity;
	/**
	 * 所有的图片
	 */
	private List<String> mDatas = new ArrayList<String>();

	public ImageGridItemContainDelAdapter(UploadPicFinishCheckActivity activity, List<String> mDatas/*, String dirPath*/) {
		super();
		this.activity = activity;
		this.mDatas = mDatas;
//		this.mDirPath = dirPath;
	}

	public void changeData(List<String> mDatas/*, String dirPath*/) {
		this.mDatas = mDatas;
//		this.mDirPath = dirPath;
		notifyDataSetChanged();

	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public String getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == (getCount() - 1)) {
			return TYPE_1;
		} else {
			return TYPE_2;
		}
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE;
	}
	
	private ViewThumbnails viewThumbnails;
	private ViewTakePhoto viewTakePhoto;

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case TYPE_1:
				convertView = LayoutInflater.from(activity).inflate(R.layout.grid_item2, null);
				viewTakePhoto = new ViewTakePhoto();
				viewTakePhoto.setIdItemTakePhoto((LinearLayout) convertView.findViewById(R.id.id_item_image2));
				convertView.setTag(viewTakePhoto);
				break;
			case TYPE_2:
				convertView = LayoutInflater.from(activity).inflate(R.layout.grid_item, null);
				viewThumbnails = new ViewThumbnails();
				viewThumbnails.setIdItemImage((ImageView) convertView.findViewById(R.id.id_item_image));
//				viewThumbnails.getIdItemImage().setEnabled(false);
				viewThumbnails.setIdItemSelect((ImageButton) convertView.findViewById(R.id.id_item_select));
				convertView.setTag(viewThumbnails);
				break;
			}
		} else {
			switch (type) {
			case TYPE_1:
				viewTakePhoto = (ViewTakePhoto) convertView.getTag();
				break;
			case TYPE_2:
				viewThumbnails = (ViewThumbnails) convertView.getTag();
				break;

			}
		}
		switch (type) {
		case TYPE_1:
			viewTakePhoto.getIdItemTakePhoto().setBackgroundResource(R.drawable.common_add_pic);
			viewTakePhoto.getIdItemTakePhoto().setOnTouchListener(new AddPicOnTouchListener(activity) {
				
				@Override
				public void onTouchBegin() {
					viewTakePhoto.getIdItemTakePhoto().setBackgroundResource(R.drawable.common_add_pic_selected);
				}
				
				@Override
				public void onImgChangeEnd(View v) {
					viewTakePhoto.getIdItemTakePhoto().setBackgroundResource(R.drawable.common_add_pic);
				}
				
				@Override
				public void onImgChangeDo(View v) {
					activity.backEvent(false);
				}
			});
			break;
		case TYPE_2:
			viewThumbnails.getIdItemSelect().setBackgroundResource(R.drawable.common_del);
			viewThumbnails.getIdItemImage().setBackgroundResource(R.drawable.pictures_no);
			ImageLoader.getInstance(3, Type.LIFO)
					.loadImageLocal(/* mDirPath + "/" + */mDatas.get(position), viewThumbnails.getIdItemImage());
			viewThumbnails.getIdItemImage().setColorFilter(null);
			// 设置ImageView的点击事件
			viewThumbnails.getIdItemSelect().setOnClickListener(new ImageViewThumbnailsDelOnClickListener(position, activity));
			break;

		default:
			break;
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

	/*public static List<String> getmSelectedImage() {
		return mSelectedImage;
	}

	public static void setmSelectedImage(List<String> mSelectedImage) {
		ImageGridItemContainDelAdapter.mSelectedImage = mSelectedImage;
	}*/

	public List<String> getmDatas() {
		return mDatas;
	}

	public void setmDatas(List<String> mDatas) {
		this.mDatas = mDatas;
	}
}
