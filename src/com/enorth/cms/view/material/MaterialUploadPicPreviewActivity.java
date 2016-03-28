package com.enorth.cms.view.material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.enorth.cms.adapter.upload.ImageGridItemContainCheckAdapter;
import com.enorth.cms.adapter.uploadpic.UploadPicPreviewViewPagerAdapter;
import com.enorth.cms.broadcast.CommonClosedBroadcast;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.materialupload.MaterialUploadImageViewFullScreenOnTouchListener;
import com.enorth.cms.listener.materialupload.MaterialUploadPicPreviewViewPagerOnPageSelectListener;
import com.enorth.cms.listener.uploadpic.DetermineCheckBoxOnCheckedChangeListener;
import com.enorth.cms.listener.uploadpic.ImageViewFullScreenOnTouchListener;
import com.enorth.cms.listener.uploadpic.UploadPicPreviewBackOnTouchListener;
import com.enorth.cms.listener.uploadpic.UploadPicPreviewViewPagerOnPageSelectListener;
import com.enorth.cms.utils.ActivityJumpUtil;
import com.enorth.cms.utils.ImageLoader;
import com.enorth.cms.utils.LayoutParamsUtil;
import com.enorth.cms.view.BaseActivity;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MaterialUploadPicPreviewActivity extends Activity implements IMaterialUploadPicPreviewView {

	private ViewPager uploadPicPreviewViewPager;
	
	private RelativeLayout uploadPicPreviewTitleLayout;
	
	private TextView backTV;
	
	private TextView picPositionTV;
	
	private TextView picComplete;
	
	private RelativeLayout uploadPicPreviewBottomLayout;
	
	private CheckBox determineCB;
	
	private List<View> items = new ArrayList<View>();
	
	private List<String> imgDatas;
	
//	private List<String> checkedImgDatas;
	
	private int curCheckPosition;
	
	private boolean featureTitleIsShow = true;
	
//	private List<String> checkedImgs = new ArrayList<String>();
	
	private Map<Integer, String> checkedImgsMap = new LinkedHashMap<Integer, String>();
	
	private UploadPicPreviewViewPagerAdapter adapter;
	
	private boolean isNeedUseCheckBoxChangeEvent = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_material_upload_pic_preview);
		initImgDatas();
		initViewPager();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("888888", "-------------1-------------------");
		switch (requestCode) {
		case ParamConst.GALLERY_ACTIVITY_TO_UPLOAD_PIC_PREVIEW_ACTIVITY_REQUEST_CODE:
			if (resultCode == ParamConst.UPLOAD_PIC_PREVIEW_ACTIVITY_BACK_TO_GALLERY_ACTIVITY_RESULT_CODE) {
				Bundle bundle = data.getExtras();
//				ArrayList<String> checkedImgDatas = bundle.getStringArrayList(ParamConst.CHECKED_IMG_DATAS);
//				GridItemContainCheckAdapter.setmSelectedImage(checkedImgDatas);
//				this.checkedImgDatas = checkedImgDatas;
				refreshCheckedImgsMap();
				initImageView();
				adapter = new UploadPicPreviewViewPagerAdapter(items);
				uploadPicPreviewViewPager.setAdapter(adapter);
				uploadPicPreviewViewPager.setCurrentItem(curCheckPosition, false);
				uploadPicPreviewViewPager.addOnPageChangeListener(new MaterialUploadPicPreviewViewPagerOnPageSelectListener(this));
			}
			break;
		}
	}
	
	private void refreshCheckedImgsMap() {
		Set<Integer> keySet = checkedImgsMap.keySet();
		List<Integer> removeKey = new ArrayList<Integer>();
		for (Integer imgData : keySet) {
			String imgDataValue = checkedImgsMap.get(imgData);
			if (!ImageGridItemContainCheckAdapter.getmSelectedImage().contains(imgDataValue)) {
				removeKey.add(imgData);
			}
		}
		for (Integer key : removeKey) {
			checkedImgsMap.remove(key);
		}
	}
	
	private void initBackEvent() {
		backTV.setText("返回");
		backTV.setOnTouchListener(new UploadPicPreviewBackOnTouchListener(this) {
			
			@Override
			public void onImgChangeEnd(View v) {
				backEvent();
			}
		});
	}
	
	private void backEvent() {
//		ArrayList<String> checkedImgs = new ArrayList<String>(checkedImgsMap.values());
		Bundle bundle = new Bundle();
//		bundle.putStringArrayList(ParamConst.CHECKED_IMG_DATAS, checkedImgs);
//		ActivityJumpUtil.takeParamsBackToPrevActivity(MaterialUploadPicPreviewActivity.this, bundle, ParamConst.UPLOAD_PIC_PREVIEW_ACTIVITY_BACK_TO_GALLERY_ACTIVITY_RESULT_CODE);
		onBackPressed();
	}
	
	public void initPicPosition() {
		picPositionTV.setText((curCheckPosition + 1) + "/" + items.size());
	}
	
	public void initPicComplete() {
		picComplete.setText("完成(" + ImageGridItemContainCheckAdapter.getmSelectedImage().size() + "/" + ParamConst.MAX_SELECT_IMAGE_COUNT + ")");
	}
	
	private void initImgDatas() {
		Bundle bundle = getIntent().getExtras();
		imgDatas = bundle.getStringArrayList(ParamConst.IMG_DATAS);
//		checkedImgDatas = bundle.getStringArrayList(ParamConst.CHECKED_IMG_DATAS);
		curCheckPosition = bundle.getInt(ParamConst.CHECK_POSITION);
	}
	
	
	private void initViewPager() {
		uploadPicPreviewViewPager = (ViewPager) findViewById(R.id.uploadPicPreviewViewPager);
		initImageView();
		adapter = new UploadPicPreviewViewPagerAdapter(items);
		uploadPicPreviewViewPager.setAdapter(adapter);
		uploadPicPreviewViewPager.setCurrentItem(curCheckPosition, false);
		uploadPicPreviewViewPager.addOnPageChangeListener(new MaterialUploadPicPreviewViewPagerOnPageSelectListener(this));
	}
	
	private void initImageView() {
		int size = imgDatas.size();
		for (int i = 0; i < size; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(LayoutParamsUtil.initAbsListViewMatchLayout());
			String imgData = imgDatas.get(i);
			if ((curCheckPosition - 1) <= i && i <= (curCheckPosition + 1)) {
				ImageLoader.getInstance().loadImageLocal(imgData, imageView);
			} else {
				imageView.setImageBitmap(null);
			}
			if (ImageGridItemContainCheckAdapter.getmSelectedImage().contains(imgData)) {
				if (curCheckPosition == i) {
					isNeedUseCheckBoxChangeEvent = false;
					determineCB.setChecked(true);
					isNeedUseCheckBoxChangeEvent = true;
				}
				checkedImgsMap.put(i, imgData);
			}
			imageView.setOnTouchListener(new MaterialUploadImageViewFullScreenOnTouchListener(this) {
				
				@Override
				public void onImgChangeEnd(View v) {
					backEvent();
				}
			});
			items.add(imageView);
		}
		initPicPosition();
		initPicComplete();
	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK) {  
        	backEvent();
        }  
        return false;  
    }

	public List<View> getItems() {
		return items;
	}

	public void setItems(List<View> items) {
		this.items = items;
	}

	public List<String> getImgDatas() {
		return imgDatas;
	}

	public void setImgDatas(List<String> imgDatas) {
		this.imgDatas = imgDatas;
	}

	public int getCurCheckPosition() {
		return curCheckPosition;
	}

	public void setCurCheckPosition(int curCheckPosition) {
		this.curCheckPosition = curCheckPosition;
		initPicPosition();
	}

	public Map<Integer, String> getCheckedImgsMap() {
		return checkedImgsMap;
	}

	public void setCheckedImgsMap(Map<Integer, String> checkedImgsMap) {
		this.checkedImgsMap = checkedImgsMap;
	}

	public CheckBox getDetermineCB() {
		return determineCB;
	}

	public void setDetermineCB(CheckBox determineCB) {
		this.determineCB = determineCB;
	}

	public boolean isNeedUseCheckBoxChangeEvent() {
		return isNeedUseCheckBoxChangeEvent;
	}

	public void setNeedUseCheckBoxChangeEvent(boolean isNeedUseCheckBoxChangeEvent) {
		this.isNeedUseCheckBoxChangeEvent = isNeedUseCheckBoxChangeEvent;
	}
	
}
