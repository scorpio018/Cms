package com.enorth.cms.fragment.materialupload;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.view.R;
import com.enorth.cms.view.material.IMaterialUploadView;
import com.enorth.cms.view.upload.GalleryActivity;
import com.enorth.cms.view.upload.VideoActivity;
import com.enorth.cms.widget.linearlayout.MaterialUploadFragLinearLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
/**
 * 素材上传的按钮组（拍照、照片、视频三个按钮）
 * @author yangyang
 *
 */
public class MaterialUploadBtnGroupFrag extends Fragment {
	private LinearLayout layout;
	/**
	 * 为true则说明已经获取了layout的宽度
	 */
	private boolean hasMeasured = false;
	/**
	 * 向上箭头
	 */
	private ImageView materialUploadBtnGroupScrollBtn;
	/**
	 * 用作收回/展开的自定义的linearlayout
	 */
	private MaterialUploadFragLinearLayout fragLayout;
	/**
	 * 拍照按钮
	 */
	private ImageView imgBtnAddPic;
	/**
	 * 照片按钮
	 */
	private ImageView imgBtnChoosePic;
	/**
	 * 视频按钮
	 */
	private ImageView imgBtnAddVideo;
	/**
	 * 图片的宽度
	 */
	private int picWidth;
	/**
	 * 图片的高度
	 */
	private int picHeight;
	
	private IMaterialUploadView view;
	
	public MaterialUploadBtnGroupFrag(MaterialUploadFragLinearLayout fragLayout) {
		this.fragLayout = fragLayout;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		layout = (LinearLayout) inflater.inflate(R.layout.material_upload_btn_group, null);
		initBtnEvent();
		ViewTreeObserver viewTreeObserver = layout.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				if (!hasMeasured) {
					int measuredWidth = layout.getMeasuredWidth();
					picWidth = picHeight = measuredWidth * 2 / 3 / 3;
					initImgBtnLayoutParam(imgBtnAddPic);
					initImgBtnLayoutParam(imgBtnChoosePic);
					initImgBtnLayoutParam(imgBtnAddVideo);
//					LayoutParams ivParams = LayoutParamsUtil.initLineWidthAndHeight(picWidth, picHeight);
					
				}
				return true;
			}
		});
		return layout;
	}
	
	private void initImgBtnLayoutParam(View view) {
		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		layoutParams.width = picWidth;
		layoutParams.height = picHeight;
		view.setLayoutParams(layoutParams);
	}
	
	/**
	 * 初始化按钮组
	 */
	private void initBtnEvent() {
		initImgBtnAddPicEvent();
		initImgBtnChoosePicEvent();
		initImgBtnAddVideo();
		initBottomImgEvent();
	}
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		view = (IMaterialUploadView) context;
	}
	
	/**
	 * 初始化向上箭头的点击事件（第一次点击将当前activity向上移，第二次还原，如此往复）
	 */
	private void initBottomImgEvent() {
		materialUploadBtnGroupScrollBtn = (ImageView) layout.findViewById(R.id.materialUploadBtnGroupScrollBtn);
		materialUploadBtnGroupScrollBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fragLayout.startMove();
			}
		});
	}
	
	/**
	 * 初始化拍照按钮
	 */
	private void initImgBtnAddPicEvent() {
		imgBtnAddPic = (ImageView) layout.findViewById(R.id.imgBtnAddPic);
		imgBtnAddPic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(getContext(), "点击了拍照按钮", Toast.LENGTH_SHORT).show();
				view.takePhoto();
			}
		});
	}
	
	/**
	 * 初始化照片按钮
	 */
	private void initImgBtnChoosePicEvent() {
		imgBtnChoosePic = (ImageView) layout.findViewById(R.id.imgBtnChoosePic);
		imgBtnChoosePic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(getContext(), "点击了照片按钮", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra(ParamConst.BROADCAST_ACTION, ParamConst.CLOSE_ACTIVITY);
				intent.setClass(getActivity(), GalleryActivity.class);
				startActivity(intent);
			}
		});
	}
	
	/**
	 * 初始化视频按钮
	 */
	private void initImgBtnAddVideo() {
		imgBtnAddVideo = (ImageView) layout.findViewById(R.id.imgBtnChooseVideo);
		imgBtnAddVideo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(getContext(), "点击了视频按钮", Toast.LENGTH_SHORT).show();
//				view.takePhotoGallery();
				Intent intent = new Intent();
				intent.putExtra(ParamConst.BROADCAST_ACTION, ParamConst.CLOSE_ACTIVITY);
				intent.setClass(getActivity(), VideoActivity.class);
				startActivity(intent);
				
			}
		});
	}
	
}
