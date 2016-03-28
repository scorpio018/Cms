package com.enorth.cms.view.upload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.enorth.cms.adapter.upload.ImageGridItemContainCheckAdapter;
import com.enorth.cms.adapter.upload.ImageGridItemContainDelAdapter;
import com.enorth.cms.broadcast.CommonClosedBroadcast;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.utils.ActivityJumpUtil;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.ImgUtil;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class UploadPicFinishCheckActivity extends Activity {
	
	private TextView backTV;
	
	private TextView toUploadTV;
	
	private EditText uploadPicNameTV;
	
	private GridView photoGrid;
	
	private List<String> imgDatas;
	
	private ImageGridItemContainDelAdapter gridItemAdapter;
	
	private int addPicIsJumpToPrevActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_pic_finish_check);
		initView();
		initData();
		initTitleEvent();
	}
	
	private void initView() {
		backTV = (TextView) findViewById(R.id.titleLeftTV);
		toUploadTV = (TextView) findViewById(R.id.titleRightTV);
		uploadPicNameTV = (EditText) findViewById(R.id.uploadPicNameTV);
		photoGrid = (GridView) findViewById(R.id.commonGridView);
	}
	
	private void initData() {
		new CommonClosedBroadcast(this);
		initImgDatas();
	}
	
	private void initImgDatas() {
		Bundle bundle = getIntent().getExtras();
		addPicIsJumpToPrevActivity = bundle.getInt(ParamConst.ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY);
		if (addPicIsJumpToPrevActivity == ParamConst.ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY_NO) {
			imgDatas = bundle.getStringArrayList(ParamConst.IMG_DATAS);
		} else if (addPicIsJumpToPrevActivity == ParamConst.ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY_YES) {
			imgDatas = ImageGridItemContainCheckAdapter.getmSelectedImage();
		}
		
		// 最后一个是为了能显示“添加图片”按钮
		imgDatas.add("");
		getImg();
	}
	
	private void getImg() {
		gridItemAdapter = new ImageGridItemContainDelAdapter(UploadPicFinishCheckActivity.this, imgDatas/*, getmImgDir().getAbsolutePath()*/);
		photoGrid.setAdapter(gridItemAdapter);
	}
	
	private void initTitleEvent() {
		initBackEvent();
		initCompleteEvent();
		initToUploadEvent();
	}
	
	private void initBackEvent() {
		backTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				backEvent(true);
			}
		});
	} 
	
	private void initCompleteEvent() {
		toUploadTV.setText("上传");
		toUploadTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AnimUtil.showRefreshFrame(UploadPicFinishCheckActivity.this, true, "正在上传中");
				new Handler() {
					@Override
					public void handleMessage(Message msg) {
						AnimUtil.hideRefreshFrame();
						CommonClosedBroadcast.close(UploadPicFinishCheckActivity.this);
//						UploadPicFinishCheckActivity.this.finish();
					};
				}.sendMessageDelayed(new Message(), 2000);
			}
		});
	}
	
	public void removePic(int location) {
		imgDatas.remove(location);
		if (addPicIsJumpToPrevActivity == ParamConst.ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY_YES) {
			ImageGridItemContainCheckAdapter.getmSelectedImage().remove(location);
		}
		gridItemAdapter.changeData(imgDatas);
	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK) {  
        	backEvent(true);
        }  
        return false;  
    }
	
	/**
	 * 通过传入的isBack来判断是点击的"+"还是点击的返回按钮
	 * 如果是"+"，则进行两种判断：
	 * 	1、点击"+"返回上一个activity；
	 * 	2、点击"+"进入相册activity
	 * 如果是返回键，则返回到上一个activity中
	 * @param isBack
	 */
	public void backEvent(boolean isBack) {
		Bundle bundle = new Bundle();
		if (isBack) {
			ImageGridItemContainCheckAdapter.getmSelectedImage().remove(ImageGridItemContainCheckAdapter.getmSelectedImage().size() - 1);
			ActivityJumpUtil.takeParamsBackToPrevActivity(UploadPicFinishCheckActivity.this, bundle, ParamConst.UPLOAD_PIC_PREVIEW_ACTIVITY_BACK_TO_GALLERY_ACTIVITY_RESULT_CODE);
		} else {
			if (addPicIsJumpToPrevActivity == ParamConst.ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY_NO) {
				Intent intent = new Intent();
				// 由于只有照相的时候跳转到相册页面时需要将当前的照片传入，其他情况都是直接从ImageGridItemContainCheckAdapter.getmSelectedImage()中进行数据操作，所以只有此处传值给相册activity
				imgDatas.remove(imgDatas.size() - 1);
				bundle.putStringArrayList(ParamConst.CHECKED_IMG_DATAS, (ArrayList<String>) imgDatas);
				intent.putStringArrayListExtra(ParamConst.CHECKED_IMG_DATAS, (ArrayList<String>) imgDatas);
				intent.setClass(UploadPicFinishCheckActivity.this, GalleryActivity.class);
				startActivity(intent);
				finish();
			} else if (addPicIsJumpToPrevActivity == ParamConst.ADD_PIC_IS_JUMP_TO_PREV_ACTIVITY_YES) {
				ImageGridItemContainCheckAdapter.getmSelectedImage().remove(ImageGridItemContainCheckAdapter.getmSelectedImage().size() - 1);
				ActivityJumpUtil.takeParamsBackToPrevActivity(UploadPicFinishCheckActivity.this, bundle, ParamConst.UPLOAD_PIC_PREVIEW_ACTIVITY_BACK_TO_GALLERY_ACTIVITY_RESULT_CODE);
			}
		}
		
	}
	
	private void initToUploadEvent() {
		
	}
}
