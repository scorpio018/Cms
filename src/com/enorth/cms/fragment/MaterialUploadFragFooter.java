package com.enorth.cms.fragment;

import com.enorth.cms.activity.R;

import android.app.Fragment;
import android.graphics.Interpolator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;

public class MaterialUploadFragFooter extends Fragment {
	
	private View view;
	// 匀速效果
	private LinearInterpolator lir;
	// 加速效果
	private AccelerateInterpolator air;
	// 减速效果
	private DecelerateInterpolator dir;
	// 自定义效果
	private Interpolator extraIr;
	
	private AlphaAnimation alphaAnimation;
	
	private RotateAnimation rotateAnimation;
	
	private ScaleAnimation scaleAnimation;
	
	private TranslateAnimation translateAnim; 
	
	private ImageButton imgBtnAddAction;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.footer_fragment, container, false);
		init();
		return view;
	}
	
	private void init() {
		initInterpolator();
		initAnimations();
		initBtn();
	}
	
	private void initBtn() {
		initImgBtnAddActionClickEvent();
	}
	
	private void initInterpolator() {
		// 匀速效果
		lir = new LinearInterpolator();
		// 加速效果
		air = new AccelerateInterpolator();
		// 减速效果
		dir = new DecelerateInterpolator();
	}
	
	private void initAnimations() {
		// 初始化alpha动画（渐变透明度动画效果）
		alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(view.getContext(), R.anim.alpha);
		// 初始化rotate动画（画面转移旋转动画效果）
		rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate);
		// 舒适化scale动画（渐变尺寸伸缩动画效果）
		scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(view.getContext(), R.anim.scale);
		// 初始化tanslate动画（画面转移位置移动动画效果）
		translateAnim = (TranslateAnimation) AnimationUtils.loadAnimation(view.getContext(), R.anim.translate);
	}
	
	private void initImgBtnAddActionClickEvent() {
		imgBtnAddAction = (ImageButton) view.findViewById(R.id.imgBtnAddAction);
		// 将动画位移效果设置成匀速运动
//		translateAnim.setInterpolator(lir);
		// 将动画位移效果设置成快速运动
//		translateAnim.setInterpolator(air);
		// 将动画位移效果设置成减速运动
		translateAnim.setInterpolator(dir);
		imgBtnAddAction.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imgBtnAddAction.startAnimation(translateAnim);
			}
		});
	}
	
}
