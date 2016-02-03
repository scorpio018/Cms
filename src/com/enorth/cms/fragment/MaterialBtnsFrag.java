package com.enorth.cms.fragment;

import java.util.Random;

import com.enorth.cms.view.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;

public class MaterialBtnsFrag extends Fragment {
	
	private View view;
	// 匀速效果
	private LinearInterpolator lir;
	// 加速效果
	private AccelerateInterpolator air;
	// 减速效果
	private DecelerateInterpolator dir;
	// 自定义效果
	private Interpolator extraIr;
	/**
	 * 初始化alpha动画（渐变透明度动画效果）
	 */
	private AlphaAnimation alphaAnimation;
	/**
	 * 初始化alpha动画（渐变透明度动画效果）
	 */
	private RotateAnimation rotateAnimation;
	/**
	 * 舒适化scale动画（渐变尺寸伸缩动画效果）
	 */
	private ScaleAnimation scaleAnimation;
	/**
	 * 初始化tanslate动画（画面转移位置移动动画效果）
	 */
	private TranslateAnimation translateAnimaction; 
	
	private ImageButton imgBtnAddPic, imgBtnChoosePic, imgBtnAddVideo, imgBtnChooseVideo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.material_btns_linearlayout, container);
		init();
		return view;
	}
	
	private void init() {
		initInterpolator();
		initAnimations();
		initBtn();
	}
	
	private void initBtn() {
		initImgBtnAddPicClickEvent();
		initImgBtnChoosePicClickEvent();
		initImgBtnAddVideoClickEvent();
		initImgBtnChooseVideoClickEvent();
	}
	
	private void initInterpolator() {
		// 匀速效果
		lir = new LinearInterpolator();
		// 加速效果
		air = new AccelerateInterpolator();
		// 减速效果
		dir = new DecelerateInterpolator();
		// 自定义效果
		extraIr = new Interpolator() {
			
			@Override
			public float getInterpolation(float input) {
				Random rd = new Random(20);
				return rd.nextFloat();
			}
		};
	}
	
	private void initAnimations() {
		// 初始化alpha动画（渐变透明度动画效果）
		alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(view.getContext(), R.anim.alpha);
		// 初始化rotate动画（画面转移旋转动画效果）
		rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate);
		// 舒适化scale动画（渐变尺寸伸缩动画效果）
		scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(view.getContext(), R.anim.scale);
		// 初始化tanslate动画（画面转移位置移动动画效果）
		translateAnimaction = (TranslateAnimation) AnimationUtils.loadAnimation(view.getContext(), R.anim.translate);
		initAnimInterpolator();
	}
	
	/**
	 * 改变运动的速度
	 */
	private void initAnimInterpolator() {
		// 将动画位移效果设置成匀速运动
		alphaAnimation.setInterpolator(lir);
		// 将动画位移效果设置成快速运动
		rotateAnimation.setInterpolator(air);
		// 将动画位移效果设置成减速运动
		scaleAnimation.setInterpolator(dir);
		translateAnimaction.setInterpolator(air);
		
		
	}
	
	private void initImgBtnAddPicClickEvent() {
		imgBtnAddPic = (ImageButton) view.findViewById(R.id.imgBtnAddPic);
		imgBtnAddPic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imgBtnAddPic.startAnimation(alphaAnimation);
			}
		});
	}
	
	private void initImgBtnChoosePicClickEvent() {
		imgBtnChoosePic = (ImageButton) view.findViewById(R.id.imgBtnChoosePic);
		imgBtnChoosePic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imgBtnChoosePic.startAnimation(rotateAnimation);
			}
		});
	}
	
	private void initImgBtnAddVideoClickEvent() {
		imgBtnAddVideo = (ImageButton) view.findViewById(R.id.imgBtnAddVideo);
		imgBtnAddVideo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imgBtnAddVideo.startAnimation(scaleAnimation);
			}
		});
	}
	
	private void initImgBtnChooseVideoClickEvent() {
		imgBtnChooseVideo = (ImageButton) view.findViewById(R.id.imgBtnChooseVideo);
		imgBtnChooseVideo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imgBtnChooseVideo.startAnimation(translateAnimaction);
			}
		});
	}
	
}
