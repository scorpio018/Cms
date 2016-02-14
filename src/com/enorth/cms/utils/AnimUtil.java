package com.enorth.cms.utils;

import com.enorth.cms.view.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;

@SuppressLint("InflateParams")
public class AnimUtil {
	
	// 匀速效果
	private static LinearInterpolator lir;
	// 加速效果
	private static AccelerateInterpolator air;
	// 减速效果
	private static DecelerateInterpolator dir;
	/**
	 * 初始化alpha动画（渐变透明度动画效果）
	 */
	private static AlphaAnimation alphaAnimation;
	/**
	 * 初始化alpha动画（渐变透明度动画效果）
	 */
	private static RotateAnimation rotateAnimation;
	/**
	 * 舒适化scale动画（渐变尺寸伸缩动画效果）
	 */
	private static ScaleAnimation scaleAnimation;
	/**
	 * 初始化tanslate动画（画面转移位置移动动画效果）
	 */
	private static TranslateAnimation translateAnimaction;
	/**
	 * 刷新frame
	 */
	private static FrameLayout refreshLayout;
	
	static {
		initAnimInterpolator();
	}
	
	/**
	 * 将刷新页面加入当前activity中并覆盖
	 * @param activity
	 */
	public static void showRefreshFrame(Activity activity) {
		// 初始化tanslate动画（画面转移位置移动动画效果）
		initAmin(activity);
		View frameView = activity.findViewById(R.id.refreshLayoutCommon);
		if (frameView == null) {
			LayoutInflater inflater = LayoutInflater.from(activity);
			refreshLayout = (FrameLayout) inflater.inflate(R.layout.refresh_layout_common, null);
			int color = ContextCompat.getColor(activity, R.color.dark_gray);
			refreshLayout.setBackgroundColor(color);
			refreshLayout.getBackground().setAlpha(120);
			final ImageView refreshIV = (ImageView) refreshLayout.getChildAt(0);
			rotateAnimation.setInterpolator(lir);
			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					refreshIV.startAnimation(rotateAnimation);
				}
			}.sendEmptyMessage(0);
			LayoutParams initMatchLayout = LayoutParamsUtil.initMatchLayout();
			refreshLayout.setVisibility(View.VISIBLE);
			activity.addContentView(refreshLayout, initMatchLayout);
		} else {
			refreshLayout = (FrameLayout) frameView;
			refreshLayout.setVisibility(View.VISIBLE);
		}
		
	}
	
	public static void hideRefreshFrame(Activity activity) {
		refreshLayout.clearAnimation();
		refreshLayout.setVisibility(View.GONE);
		
	}
	
	private static void initAmin(Context context) {
		// 初始化tanslate动画（画面转移位置移动动画效果）
		if (translateAnimaction == null) {
			translateAnimaction = (TranslateAnimation) AnimationUtils.loadAnimation(context, R.anim.translate);
		}
		// 初始化alpha动画（渐变透明度动画效果）
		if (alphaAnimation == null) {
			alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(context, R.anim.alpha);
		}
		// 初始化rotate动画（画面转移旋转动画效果）
		if (rotateAnimation == null) {
			rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.rotate);
		}
		// 舒适化scale动画（渐变尺寸伸缩动画效果）
		if (scaleAnimation == null) {
			scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(context, R.anim.scale);
		}
	}
	
	private static void initAnimInterpolator() {
		// 匀速效果
		lir = new LinearInterpolator();
		// 加速效果
		air = new AccelerateInterpolator();
		// 减速效果
		dir = new DecelerateInterpolator();
		/*// 将动画位移效果设置成匀速运动
		alphaAnimation.setInterpolator(lir);
		// 将动画位移效果设置成快速运动
		rotateAnimation.setInterpolator(air);
		// 将动画位移效果设置成减速运动
		scaleAnimation.setInterpolator(dir);*/
	}
}
