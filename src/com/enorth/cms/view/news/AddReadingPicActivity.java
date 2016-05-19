package com.enorth.cms.view.news;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.bean.ViewColorBasicBean;
import com.enorth.cms.common.EnableSimpleChangeButton;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.utils.ActivityJumpUtil;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.view.CommonAlertDialogActivity;
import com.enorth.cms.view.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddReadingPicActivity extends CommonAlertDialogActivity {
	/**
	 * “设置导读图”按钮
	 */
//	private Button setReadingPicBtn;
	private EnableSimpleChangeButton setReadingPicBtn;
	/**
	 * “删除导读图”按钮
	 */
//	private Button delReadingPicBtn;
	private EnableSimpleChangeButton delReadingPicBtn;

	@Override
	public List<View> addBtns() {
		List<View> btns = new ArrayList<View>();
		setReadingPicBtn = new EnableSimpleChangeButton(this);
		setReadingPicBtn.setText("设置导读图");
		delReadingPicBtn = new EnableSimpleChangeButton(this);
		delReadingPicBtn.setText("删除导读图");
		btns.add(setReadingPicBtn);
		btns.add(delReadingPicBtn);
		initEvent();
		return btns;
	}
	
	private void initEvent() {
		setReadingPicBtn.setOnClickListener(new CommonOnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(AddReadingPicActivity.this, "点击了“设置导读图”按钮", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(AddReadingPicActivity.this, ChooseReadingPicFromActivity.class);
//				AddReadingPicActivity.this.startActivity(intent);
				AddReadingPicActivity.this.startActivityForResult(intent, ParamConst.ADD_READING_PIC_ACTIVITY_TO_CHOOSE_READING_PIC_FROM_ACTIVITY_REQUEST_CODE);
//				AddReadingPicActivity.this.finish();
			}
		});
		delReadingPicBtn.setOnClickListener(new CommonOnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(AddReadingPicActivity.this, "点击了“删除导读图”按钮", Toast.LENGTH_SHORT).show();
				Bundle bundle = new Bundle();
				ActivityJumpUtil.takeParamsBackToPrevActivity(AddReadingPicActivity.this, bundle, ParamConst.ADD_READING_PIC_ACTIVITY_BACK_TO_NEWS_EIDT_COMMON_ACTIVITY_DEL_READING_PIC_RESULT_CODE);
			}
		});
	}
	
	@Override
	public void initContentView() {
		setContentView(R.layout.alert_dialog_vertical_no_pic);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case ParamConst.ADD_READING_PIC_ACTIVITY_TO_CHOOSE_READING_PIC_FROM_ACTIVITY_REQUEST_CODE:
			if (data == null) {
				Bundle bundle = new Bundle();
				ActivityJumpUtil.takeParamsBackToPrevActivity(this, bundle, resultCode);
			} else {
				Bundle bundle = data.getExtras();
				if (bundle == null) {
					bundle = new Bundle();
					ActivityJumpUtil.takeParamsBackToPrevActivity(this, bundle, resultCode);
				} else {
					ActivityJumpUtil.takeParamsBackToPrevActivity(this, bundle, ParamConst.ADD_READING_PIC_ACTIVITY_BACK_TO_NEWS_EIDT_COMMON_ACTIVITY_HAS_IMG_DATAS_RESULT_CODE);
				}
			}
			break;
		default:
			finish();
			break;
		}
	}
	
	@Override
	public boolean needInitStyle() {
		return true;
	}

	@Override
	public boolean needCancelBtn() {
		return true;
	}

}
