package com.enorth.cms.view;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.adapter.WelcomeViewPagerAdapter;
import com.enorth.cms.consts.ParamConst;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends BaseActivity implements OnPageChangeListener {
	
	private ViewPager viewPager;
	/**
	 * 储存点的容器
	 */
	private LinearLayout layout4Dot;
	/**
	 * 当前的坐标圆点
	 */
	private ImageView curDot;
	/**
	 * 引导图的最后一个view中显示的进入主页面的图片按钮
	 */
	private ImageView imageViewStart;
	/**
	 * 获取点图片的宽度，点的移动动画时用
	 */
	private int offset;
	/**
	 * 记录当前的位置
	 */
	private int currentPos = 0;
	/**
	 * 当view轮播到最后一个，显示“进入”按钮
	 */
	private Handler handler;
	/**
	 * 该参数表示当前轮播的view已经是最后一个了
	 */
	private int isLast = 1;
	/**
	 * 改参数表示当前轮播的view不是最后一个
	 */
	private int isNotLast = 0;
	/**
	 * 初始化alpha动画（渐变透明度动画效果）
	 */
	private AlphaAnimation alphaAnimation;
	/**
	 * 轮播的图片
	 */
	private int[] resources = { R.drawable.g1, R.drawable.g2, R.drawable.g3, R.drawable.g4, R.drawable.guide1,
			R.drawable.guide2, R.drawable.guide3 };

	private List<View> viewList = new ArrayList<View>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_paper);
		initWelcomePager();
	}

	private void initWelcomePager() {
		initViewPager();
		initDot();
		initImageViewStart();
		initHandler();
	}

	private void initViewPager() {
		viewPager = (ViewPager) findViewById(R.id.welcomeViewPager);
		LayoutInflater lf = LayoutInflater.from(this);
		final int colorWhite = ContextCompat.getColor(this, R.color.white);
		final int colorBlack = ContextCompat.getColor(this, R.color.black);
		for (int resource : resources) {
			// 初始化每一个页面
			final View view = lf.inflate(R.layout.view_pager_welcome, null);
			ImageView pic = (ImageView) view.findViewById(R.id.viewPagerImageView);
			pic.setImageResource(resource);
			LinearLayout layout = (LinearLayout) view.findViewById(R.id.welcomePagerLinearLayout);
//			ImageView skip = (ImageView) view.findViewById(R.id.welcomePagerSkip);
			layout.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					final TextView textView = (TextView) view.findViewById(R.id.welcomePagerSkipText);
					textView.setTextColor(colorWhite);
					Handler h = new Handler();
					h.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							textView.setTextColor(colorBlack);
						}
					}, 100);
					toMainActivity();
				}
			});
			/*ImageView v = new ImageView(this);
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			v.setLayoutParams(params);
			v.setImageResource(resource);
			v.setScaleType(ScaleType.FIT_XY);*/
			viewList.add(view);
		}
		WelcomeViewPagerAdapter adapter = new WelcomeViewPagerAdapter(viewList);
		viewPager.setAdapter(adapter);
		viewPager.addOnPageChangeListener(this);
	}
	
	/**
	 * 初始化引导图中的圆点，并在
	 * @return
	 */
	private boolean initDot() {
		layout4Dot = (LinearLayout) findViewById(R.id.dot_contain);
		int length = resources.length;
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				ImageView dot = new ImageView(this);
				dot.setImageResource(R.drawable.dot1_w);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
				dot.setLayoutParams(params);
				layout4Dot.addView(dot);
			}
			initCurDot();
			return true;
		} else {
			return false;
		}
	}
	
	private void initCurDot() {
		curDot = (ImageView) findViewById(R.id.cur_dot);
		// 当iv_currentDot的所在的树形层次将要被绘出时,此方法被调用
		curDot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				// 获取点图片的宽度，点的移动动画时用
				offset = curDot.getWidth();
				return true;
			}
		});
	}
	
	/**
	 * 滑动时 坐标点点移动动画
	 * 
	 * @param position
	 */
	private void moveCursorTo(int position) {
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation = new TranslateAnimation(offset
				* currentPos, offset * position, 0, 0);
		currentPos = position;
		animationSet.addAnimation(translateAnimation);
		animationSet.setDuration(300);
		animationSet.setFillAfter(true);
		curDot.startAnimation(animationSet);
	}
	
	private void initImageViewStart() {
		imageViewStart = (ImageView) findViewById(R.id.open);
		initImageViewStartAnim();
		imageViewStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toMainActivity();
			}
		});
	}
	
	private void initImageViewStartAnim() {
		alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.alpha);
		// 匀速效果
//		LinearInterpolator lir = new LinearInterpolator();
		// 加速效果
		AccelerateInterpolator air = new AccelerateInterpolator();
//		alphaAnimation.setInterpolator(lir);
		alphaAnimation.setInterpolator(air);
	}

	private void toMainActivity() {
		intent.setClass(WelcomeActivity.this, MainActivity.class);
		startActivity(intent);
		// TODO 正式使用时要取消下面注解的方法（将ParamConst.ACTIVITY_IS_FIRST_ENTER的参数变为false，表示该APP已经看过一次引导图了，下次进入不需要再看）
		saveTag();
		finish();
	}

	private void saveTag() {
		SharedPreferences pre = getSharedPreferences(ParamConst.ACTIVITY_IS_FIRST_ENTER, Context.MODE_PRIVATE);
		Editor editor = pre.edit();
		editor.putBoolean(ParamConst.ACTIVITY_IS_FIRST_ENTER, false);
		editor.commit();
	}

	/**
	 * 在滑动状态改变的时候调用
	 * 有三种状态（0，1，2）。1表示正在滑动；2表示滑动完毕了；0表示什么都没做。
	 */
	@Override
	public void onPageScrollStateChanged(int state) {
		if (state == 1) {
			if (currentPos != resources.length - 1) {
				imageViewStart.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法会一直得到调用。其中三个参数的含义分别为：
	 * arg0 :当前页面，及你点击滑动的页面
	 * arg1:当前页面偏移的百分比
	 * arg2:当前页面偏移的像素位置
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	/**
	 * 此方法是页面跳转完后得到调用，position是你当前选中的页面对应的值（从0开始）
	 */
	@Override
	public void onPageSelected(int position) {
		moveCursorTo(position);
		if (position == resources.length - 1) {
			handler.sendEmptyMessage(isLast);
		} else {
			handler.sendEmptyMessage(isNotLast);
		}
	}
	
	private void initHandler() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == isLast) {
					imageViewStart.startAnimation(alphaAnimation);
					imageViewStart.setVisibility(View.VISIBLE);
				} else {
					imageViewStart.setVisibility(View.GONE);
				}
			}
		};
	}
	
	@Override
	public void exitClick() {
		Toast.makeText(WelcomeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT);
	}
}
