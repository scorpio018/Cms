package com.enorth.cms.fragment.materialupload;

import com.enorth.cms.view.R;
import com.enorth.cms.widget.linearlayout.MaterialUploadFragLinearLayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
/**
 * 素材上传的按钮组（拍照、照片、视频三个按钮）
 * @author yangyang
 *
 */
public class MaterialUploadBtnGroupFrag extends Fragment {
	private LinearLayout layout;
	/**
	 * 向上箭头
	 */
	private ImageView materialUploadBtnGroupScrollBtn;
	/**
	 * 用作收回/展开的自定义的linearlayout
	 */
	private MaterialUploadFragLinearLayout fragLayout;
	
	public MaterialUploadBtnGroupFrag(MaterialUploadFragLinearLayout fragLayout) {
		this.fragLayout = fragLayout;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		layout = (LinearLayout) inflater.inflate(R.layout.material_upload_btn_group, null);
		initBtnEvent();
		return layout;
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
		ImageView imgBtnAddPic = (ImageView) layout.findViewById(R.id.imgBtnAddPic);
		imgBtnAddPic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "点击了拍照按钮", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	/**
	 * 初始化照片按钮
	 */
	private void initImgBtnChoosePicEvent() {
		ImageView imgBtnChoosePic = (ImageView) layout.findViewById(R.id.imgBtnChoosePic);
		imgBtnChoosePic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "点击了照片按钮", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	/**
	 * 初始化视频按钮
	 */
	private void initImgBtnAddVideo() {
		ImageView imgBtnAddVideo = (ImageView) layout.findViewById(R.id.imgBtnAddVideo);
		imgBtnAddVideo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "点击了视频按钮", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
}
