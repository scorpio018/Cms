package com.enorth.cms.view.news;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.bean.ViewColorBasicBean;
import com.enorth.cms.common.SimpleImageView;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.utils.ActivityJumpUtil;
import com.enorth.cms.utils.CameraUtil;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.ImgUtil;
import com.enorth.cms.utils.LayoutParamsUtil;
import com.enorth.cms.utils.MediaUtils;
import com.enorth.cms.view.CommonAlertDialogActivity;
import com.enorth.cms.view.R;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 点击“设置导读图”时弹出，可选择“拍摄照片”和“本地照片”
 * @author Administrator
 *
 */
public class ChooseReadingPicFromActivity extends CommonAlertDialogActivity {
	/**
	 * 拍摄照片
	 */
	private View takePhoto;
	/**
	 * 本地照片
	 */
	private View localPhoto;
	/**
	 * 为true则说明已经获取了layout的宽度
	 */
	private boolean hasMeasured = false;
	
	private int ivWidth = 0;
	
	private int ivHeight = 0;
	
	@Override
	public void initContentView() {
		setContentView(R.layout.alert_dialog_horizontal_has_pic);
	}

	@Override
	public List<View> addBtns() {
		initPicWidthAndHeight();
		return null;
	}
	
	private void initPicWidthAndHeight() {
		ViewTreeObserver viewTreeObserver = popLayout.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				if (!hasMeasured) {
					int popLayoutWidth = popLayout.getMeasuredWidth();
					// 图片的宽度控制在popLayout的1/4
					ivWidth = ivHeight = popLayoutWidth * 1 / 4;
					hasMeasured = true;
					init();
				}
				return true;
			}
		});
	}
	
	private void init() {
		takePhoto = initView(R.drawable.iconfont_paizhao, "拍摄照片");
		localPhoto = initView(R.drawable.iconfont_tupian, "本地照片");
		initEvent();
	}
	
	private View initView(int resourceId, String text) {
		LinearLayout layout = new LinearLayout(this);
		layout.setWeightSum(1f);
		layout.setGravity(Gravity.CENTER);
		layout.setOrientation(LinearLayout.VERTICAL);
		LayoutParams layoutParams = LayoutParamsUtil.initLineWidthPercentWeight(0.5f);
		popLayout.addView(layout, layoutParams);
		
		LayoutParams ivParams = LayoutParamsUtil.initLineWidthAndHeight(ivWidth, ivHeight);
		LayoutParams wrapLayout = LayoutParamsUtil.initLineWrapLayout();
//		ImageView iv = new ImageView(this);
		ViewColorBasicBean colorBasicBean = new ViewColorBasicBean(this);
		colorBasicBean.setStrokeColor(ColorUtil.getWhiteColor(this));
		SimpleImageView iv = new SimpleImageView(this, colorBasicBean);
		iv.setBackgroundResource(resourceId);
		layout.addView(iv, ivParams);
		TextView tv = new TextView(this);
		tv.setText(text);
		layout.addView(tv, wrapLayout);
		return layout;
	}
	
	private void initEvent() {
		takePhoto.setOnClickListener(new CommonOnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(ChooseReadingPicFromActivity.this, "点击了“拍摄照片”按钮", Toast.LENGTH_SHORT).show();
				CameraUtil.takePhoto(ChooseReadingPicFromActivity.this, Environment.getExternalStorageDirectory() + "/DCIM");
			}
		});
		localPhoto.setOnClickListener(new CommonOnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(ChooseReadingPicFromActivity.this, "点击了“本地照片”按钮", Toast.LENGTH_SHORT).show();
				CameraUtil.takePhotoGallery(ChooseReadingPicFromActivity.this);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ArrayList<String> imgDatas = new ArrayList<String>();
		switch (requestCode) {
		case ParamConst.TAKE_CAMERA_PICTURE:
			if (CameraUtil.getNewTakePhotoFile() != null) {
				ImgUtil.refreshGallery(CameraUtil.getNewTakePhotoFile(), this);
			}
			imgDatas.add(CameraUtil.getNewTakePhotoFile().getAbsolutePath());
//			ActivityJumpUtil.sendTakePhotoToActivity(imgDatas, ChooseReadingPicFromActivity.this, UploadPicFinishCheckActivity.class, ParamConst.ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY_NO);
			break;
		case ParamConst.CAMERA_SELECT:
			// 照片的原始资源地址  
            Uri imgUri = data.getData();
            String filePath = MediaUtils.uriToFilePath(this, imgUri);
			imgDatas.add(filePath);
			break;
		default:
			break;
		}
		Bundle bundle = new Bundle();
		bundle.putStringArrayList(ParamConst.IMG_DATAS, imgDatas);
		ActivityJumpUtil.takeParamsBackToPrevActivity(this, bundle, resultCode);
	}
	
	@Override
	public boolean needInitStyle() {
		return false;
	}

	@Override
	public boolean needCancelBtn() {
		return false;
	}

}
