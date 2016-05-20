package com.enorth.cms.view;

import java.util.List;

import com.enorth.cms.bean.ViewColorBasicBean;
import com.enorth.cms.common.EnableSimpleChangeButton;
import com.enorth.cms.utils.LayoutParamsUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
/**
 * 通用的底部弹出按钮组
 * @author yangyang
 *
 */
public abstract class CommonAlertDialogActivity extends Activity {
	
	protected LinearLayout popLayout;
	
	protected List<View> buttons;
	/**
	 * 包裹button的layout的params
	 */
	private LayoutParams layoutParams;
	/**
	 * button的params
	 */
	private LayoutParams btnParams;
	/**
	 * 按钮的类别（0：默认样式；1：EnableSimpleChangeButton；）
	 */
	private int btnType = 0;
	
	private float textSize = 18f;
	
	public abstract void initContentView();
	/**
	 * 将想要加入layout中的按钮存入集合中返回
	 * @return
	 */
	public abstract List<View> addBtns();
	/**
	 * 是否需要初始化默认的样式
	 * @return
	 */
	public abstract boolean needInitStyle();
	/**
	 * 是否需要取消按钮
	 * @return
	 */
	public abstract boolean needCancelBtn();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.alert_dialog_vertical_no_pic);
		initContentView();
		initView();
		buttons = addBtns();
		if (needInitStyle()) {
			initStyle();
		}
		if (needCancelBtn()) {
			initCancelBtn();
		}
	}
	
	private void initView() {
		popLayout = (LinearLayout) findViewById(R.id.pop_layout);
	}
	
	private void initStyle() {
		layoutParams = LayoutParamsUtil.initMatchWidthAndWrapHeight();
		btnParams = LayoutParamsUtil.initLineWidthPercentWeight(0.9f);
		int size = buttons.size();
//		for (View btn : buttons) {
		for (int i = 0; i < size; i++) {
			View btn = buttons.get(i);
			if (btn instanceof EnableSimpleChangeButton) {
				btnType = 1;
				initEnableSimpleChangeBtnStyle((EnableSimpleChangeButton) btn, i);
			} else {
				btnType = 0;
				defaultInitBtnStyle(btn);
			}
		}
	}
	
	private void initEnableSimpleChangeBtnStyle(EnableSimpleChangeButton btn, int position) {
		LinearLayout layout = initLinearLayoutForButton();
		popLayout.addView(layout, layoutParams);
		ViewColorBasicBean colorBasicBean = new ViewColorBasicBean(this);
		if (position == 0) {
			btn.needRadiusPosition(true, true, false, true);
		} else if (position == (buttons.size() - 1)) {
			btn.needRadiusPosition(false, true, true, true);
		} else {
			btn.needRadiusPosition(false, true, false, true);
		}
		btn.setTextSize(textSize);
		btn.setColorBasicBean(colorBasicBean);
		layout.addView(btn, btnParams);
	}
	
	private void defaultInitBtnStyle(View btn) {
		LinearLayout layout = initLinearLayoutForButton();
		popLayout.addView(layout, layoutParams);
		btn.setBackgroundResource(R.drawable.btn_style_alert_dialog_button);
		layout.addView(btn, btnParams);
	}
	
	private void initCancelBtn() {
		switch (btnType) {
		// 默认样式
		case 0:
			initDefaultCancelBtn();
			break;
		// EnableSimpleChangeButton
		case 1:
			initEnableSimpleChangeButtonCancleBtn();
			break;
		default:
			break;
		}
	}
	/**
	 * 默认取消按钮
	 */
	private void initDefaultCancelBtn() {
		LinearLayout layout = initLinearLayoutForButton();
		popLayout.addView(layout, layoutParams);
		Button cancelBtn = new Button(this);
		cancelBtn.setText("取消");
		cancelBtn.setBackgroundResource(R.drawable.btn_style_alert_dialog_cancel);
		layout.addView(cancelBtn, btnParams);
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonAlertDialogActivity.this.finish();
			}
		});
	}
	/**
	 * EnableSimpleChangeButton的取消按钮
	 */
	private void initEnableSimpleChangeButtonCancleBtn() {
		LinearLayout layout = initLinearLayoutForButton();
		popLayout.addView(layout, layoutParams);
		ViewColorBasicBean colorBasicBean = new ViewColorBasicBean(this);
		EnableSimpleChangeButton cancelBtn = new EnableSimpleChangeButton(this);
		cancelBtn.setText("取消");
		cancelBtn.needRadiusPosition(true, true, true, true);
		cancelBtn.setColorBasicBean(colorBasicBean);
		cancelBtn.setTextSize(textSize);
		LayoutParams cancelBtnParams = LayoutParamsUtil.initLineWidthPercentWeight(0.9f);
		cancelBtnParams.topMargin = 40;
		layout.addView(cancelBtn, cancelBtnParams);
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonAlertDialogActivity.this.finish();
			}
		});
	}
	
	private LinearLayout initLinearLayoutForButton() {
		LinearLayout layout = new LinearLayout(this);
		layout.setWeightSum(1f);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		return layout;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
}
