package com.enorth.cms.refreshlayout;

import com.enorth.cms.activity.R;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.OnRefreshListener;
import com.enorth.cms.pullableview.Pullable;
import com.enorth.cms.task.AutoRefreshAndLoadTask;
import com.enorth.cms.task.MyTimer;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 自定义的布局，用来管理三个子控件，其中一个是下拉头，一个是包含内容的pullableView（可以是实现Pullable接口的的任何View），
 * 还有一个上拉头，更多详解见博客http://blog.csdn.net/zhongkejingwang/article/details/38868463
 * 
 * @author yangyang
 */
public class PullToRefreshLayout extends FrameLayout {
//	public static final String TAG = "PullToRefreshLayout";
	/**
	 * 当前状态
	 */
	private int state = ParamConst.INIT;
	/**
	 * 刷新回调接口
	 */
	public OnRefreshListener mListener;
	/**
	 * 按下Y坐标，移动时的最新的Y坐标，上一个事件点Y坐标
	 */
	private float downY, moveY, lastY;
	/**
	 * 按下X坐标，移动时的最新的X坐标
	 */
	private float downX, moveX;
	/**
	 * 下拉的距离。注意：pullDownY和pullUpY不可能同时不为0
	 */
	public float pullDownY = 0;
	/**
	 * 上拉的距离
	 */
	private float pullUpY = 0;

	/**
	 * 释放刷新的距离
	 */
	public float refreshDist = 200;
	/**
	 * 释放加载的距离
	 */
	private float loadmoreDist = 200;

	private MyTimer timer;
	/**
	 * 回滚速度
	 */
	public float MOVE_SPEED = 8;
	/**
	 * 第一次执行布局
	 */
	private boolean isLayout = false;
	/**
	 * 在刷新过程中滑动操作
	 */
	private boolean isTouch = false;
	/**
	 * 手指滑动距离与下拉头的滑动距离比，中间会随正切函数变化
	 */
	private float radio = 2;

	/**
	 * 下拉箭头的转180°动画
	 */
	private RotateAnimation rotateAnimation;
	/**
	 * 下拉箭头的回转180°动画
	 */
	private RotateAnimation rotateBackAnimation;
	/**
	 * 均匀旋转动画
	 */
	private RotateAnimation refreshingAnimation;

	/**
	 * 下拉头
	 */
	private View refreshView;
	/**
	 * 下拉的箭头
	 */
	private View pullView;
	/**
	 * 正在刷新的图标
	 */
	private View refreshingView;
	/**
	 * 刷新结果图标
	 */
	private View refreshStateImageView;
	/**
	 * 刷新结果：成功或失败
	 */
	private TextView refreshStateTextView;

	/**
	 * 上拉头
	 */
	private View loadmoreView;
	/**
	 * 上拉的箭头
	 */
	private View pullUpView;
	/**
	 * 正在加载的图标
	 */
	private View loadingView;
	/**
	 * 加载结果图标
	 */
	private View loadStateImageView;
	/**
	 * 加载结果：成功或失败
	 */
	private TextView loadStateTextView;

	/**
	 * 实现了Pullable接口的View
	 */
	private View pullableView;
	/**
	 * 过滤多点触碰
	 */
	private int mEvents;
	/**
	 * 这两个变量用来控制pull的方向，如果不加控制，当情况满足可上拉又可下拉时没法下拉
	 */
	private boolean canPullDown = true;
	/**
	 * 这两个变量用来控制pull的方向，如果不加控制，当情况满足可上拉又可下拉时没法下拉
	 */
	private boolean canPullUp = true;

	/**
	 * 表示当前是否可以进行刷新/加载更多的操作
	 */
	private int isFresh = ParamConst.IS_REFRESH_DEFAULT;
	
	/**
	 * 执行自动回滚的handler
	 */
	Handler updateHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// 回弹速度随下拉距离moveDeltaY增大而增大
//			Log.e("updateHandler.handleMessage", "回弹速度随下拉距离moveDeltaY增大而增大");
			// 获取当前view的高度(getHeight()在view的高度没有超过屏幕高度的时候与此参数的值相同，而如果view超出，则getHeight()的值就是屏幕的高度值)
			int measuredHeight = getMeasuredHeight();
//			Log.w("updateHandler.handleMessage", "getMeasuredHeight()【" + measuredHeight + "】");
//			Log.w("updateHandler.handleMessage", "pullDownY【" + pullDownY + "】");
//			Log.w("updateHandler.handleMessage", "pullUpY【" + pullUpY + "】");
			double result = Math.PI / 2 / getMeasuredHeight() * (pullDownY + Math.abs(pullUpY));
//			Log.w("updateHandler.handleMessage",
//					"Math.PI / 2 / getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))【" + result + "】");
			MOVE_SPEED = (float) (8 + 5 * Math.tan(result));
//			Log.w("updateHandler.handleMessage",
//					"MOVE_SPEED = 8 * 5 * Math.tan(Math.PI / 2 / getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))))【"
//							+ MOVE_SPEED + "】");
			if (!isTouch) {
//				Log.w("updateHandler.handleMessage", "手已经离开屏幕");
				// 正在刷新，且没有往上推的话则悬停，显示"正在刷新..."
				if (state == ParamConst.REFRESHING && pullDownY <= refreshDist) {
					Log.w("updateHandler.handleMessage", "state为【正在刷新】且下拉的距离≤释放刷新的距离，将释放刷新的距离赋值给下拉距离并取消timer");
					pullDownY = refreshDist;
					timer.cancel();
				} else if (state == ParamConst.LOADING && -pullUpY <= loadmoreDist) {
					Log.w("updateHandler.handleMessage", "state为【正在加载】且上拉的距离≤释放加载的距离，将释放加载的距离赋值给上拉距离并取消timer");
					pullUpY = -loadmoreDist;
					timer.cancel();
				}

			}
//			Log.w("updateHandler.handleMessage", "开始回弹");
			if (pullDownY > 0)
				pullDownY -= MOVE_SPEED;
			else if (pullUpY < 0)
				pullUpY += MOVE_SPEED;
			if (pullDownY < 0 || pullUpY > 0) {
				if (pullDownY < 0) {
					// 已完成回弹
					pullDownY = 0;
				} else {
					// 已完成回弹
					pullUpY = 0;
				}
				pullView.clearAnimation();
				// 隐藏下拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
				if (state != ParamConst.REFRESHING && state != ParamConst.LOADING)
					changeState(ParamConst.INIT);
				timer.cancel();
				requestLayout();
			}
			// 刷新布局,会自动调用onLayout
//			Log.w("updateHandler.handleMessage", "刷新布局,会自动调用onLayout");
			requestLayout();
			// 没有拖拉或者回弹完成
			if (pullDownY + Math.abs(pullUpY) == 0)
				timer.cancel();
		}

	};

	public void setOnRefreshListener(OnRefreshListener listener) {
		mListener = listener;
	}

	public PullToRefreshLayout(Context context) {
		super(context);
		initView(context);
	}

	public PullToRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	private void initView(Context context) {
		Log.e("initView", "初始化view");
		Log.w("initView", "将handler传入到MyTimer的构造方法中");
		timer = new MyTimer(updateHandler);
		Log.w("initView", "给当前传入的view添加动画");
		rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.reverse_anim);
		rotateBackAnimation = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.reverse_back_anim);
		refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.rotating);
		// 添加匀速转动动画
		LinearInterpolator lir = new LinearInterpolator();
		rotateAnimation.setInterpolator(lir);
		rotateBackAnimation.setInterpolator(lir);
		refreshingAnimation.setInterpolator(lir);
	}

	public void hide() {
		Log.e("hide", "调用hide方法");
		timer.schedule(5);
	}

	/**
	 * 完成刷新操作，显示刷新结果。注意：刷新完成后一定要调用这个方法
	 */
	/**
	 * @param refreshResult
	 *            PullToRefreshLayout.SUCCEED代表成功，PullToRefreshLayout.FAIL代表失败
	 */
	public void refreshFinish(int refreshResult) {
		Log.e("refreshFinish", "完成刷新操作");
		refreshingView.clearAnimation();
		refreshingView.setVisibility(View.GONE);
		switch (refreshResult) {
		case ParamConst.SUCCEED:
			// 刷新成功
			Log.w("refreshFinish", "刷新结果为【刷新成功】");
			refreshStateImageView.setVisibility(View.VISIBLE);
			refreshStateTextView.setText(R.string.refresh_succeed);
			refreshStateImageView.setBackgroundResource(R.drawable.refresh_succeed);
			break;
		case ParamConst.FAIL:
		default:
			// 刷新失败
			Log.w("refreshFinish", "刷新结果为【刷新失败】");
			refreshStateImageView.setVisibility(View.VISIBLE);
			refreshStateTextView.setText(R.string.refresh_fail);
			refreshStateImageView.setBackgroundResource(R.drawable.refresh_failed);
			break;
		}
		if (pullDownY > 0) {
			// 刷新结果停留1秒
			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					Log.w("refreshFinish", "刷新的view还没有完全回到最初状态");
					changeState(ParamConst.DONE);
					hide();
				}
			}.sendEmptyMessageDelayed(0, 1000);
		} else {
			Log.w("refreshFinish", "刷新的view已经回到最初状态");
			changeState(ParamConst.DONE);
			hide();
		}
	}

	/**
	 * 加载完毕，显示加载结果。注意：加载完成后一定要调用这个方法
	 * 
	 * @param refreshResult
	 *            PullToRefreshLayout.SUCCEED代表成功，PullToRefreshLayout.FAIL代表失败
	 */
	public void loadmoreFinish(int refreshResult) {
		loadingView.clearAnimation();
		loadingView.setVisibility(View.GONE);
		switch (refreshResult) {
		case ParamConst.SUCCEED:
			// 加载成功
			loadStateImageView.setVisibility(View.VISIBLE);
			loadStateTextView.setText(R.string.load_succeed);
			loadStateImageView.setBackgroundResource(R.drawable.load_succeed);
			break;
		case ParamConst.FAIL:
		default:
			// 加载失败
			loadStateImageView.setVisibility(View.VISIBLE);
			loadStateTextView.setText(R.string.load_fail);
			loadStateImageView.setBackgroundResource(R.drawable.load_failed);
			break;
		}
		if (pullUpY < 0) {
			// 刷新结果停留1秒
			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					changeState(ParamConst.DONE);
					hide();
				}
			}.sendEmptyMessageDelayed(0, 1000);
		} else {
			changeState(ParamConst.DONE);
			hide();
		}
	}

	public void changeState(int to) {
		Log.e("changeState", "改变state的状态");
		firstInit();
		state = to;
		switch (state) {
		case ParamConst.INIT:
			// 下拉布局初始状态
			Log.w("changeState", "状态变为【初始状态】");
			refreshStateImageView.setVisibility(View.GONE);
			refreshStateTextView.setText(R.string.pull_to_refresh);
			pullView.startAnimation(rotateBackAnimation);
//			pullView.clearAnimation();
			pullView.setVisibility(View.VISIBLE);
			// 上拉布局初始状态
			loadStateImageView.setVisibility(View.GONE);
			loadStateTextView.setText(R.string.pullup_to_load);
//			pullUpView.clearAnimation();
			pullUpView.startAnimation(rotateBackAnimation);
			pullUpView.setVisibility(View.VISIBLE);
			break;
		case ParamConst.RELEASE_TO_REFRESH:
			// 释放刷新状态
			Log.w("changeState", "状态变为【释放刷新状态】");
			refreshStateTextView.setText(R.string.release_to_refresh);
			pullView.startAnimation(rotateAnimation);
			break;
		case ParamConst.REFRESHING:
			// 正在刷新状态
			Log.w("changeState", "状态变为【正在刷新状态】");
			pullView.clearAnimation();
			refreshingView.setVisibility(View.VISIBLE);
			pullView.setVisibility(View.INVISIBLE);
			refreshingView.startAnimation(refreshingAnimation);
			refreshStateTextView.setText(R.string.refreshing);
			break;
		case ParamConst.RELEASE_TO_LOAD:
			// 释放加载状态
			Log.w("changeState", "状态变为【释放加载状态】");
			loadStateTextView.setText(R.string.release_to_load);
			pullUpView.startAnimation(rotateAnimation);
			break;
		case ParamConst.LOADING:
			// 正在加载状态
			Log.w("changeState", "状态变为【正在加载状态】");
			pullUpView.clearAnimation();
			loadingView.setVisibility(View.VISIBLE);
			pullUpView.setVisibility(View.INVISIBLE);
			loadingView.startAnimation(refreshingAnimation);
			loadStateTextView.setText(R.string.loading);
			break;
		case ParamConst.DONE:
			// 刷新或加载完毕，啥都不做
			Log.w("changeState", "状态变为【刷新或加载完毕】");
			break;
		}
	}

	/**
	 * 不限制上拉或下拉
	 */
	private void releasePull() {
		Log.e("releasePull", "不限制上拉或下拉");
		canPullDown = true;
		canPullUp = true;
	}

	/*
	 * （非 Javadoc）由父控件决定是否分发事件，防止事件冲突
	 * 
	 * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.e("dispatchTouchEvent", "获取屏幕事件");
		switch (ev.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			Log.w("dispatchTouchEvent", "手已经摁下屏幕了");
			downY = ev.getY();
			downX = ev.getX();
			lastY = downY;
			timer.cancel();
			mEvents = 0;
			releasePull();
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
		case MotionEvent.ACTION_POINTER_UP:
			// 过滤多点触碰
			Log.w("dispatchTouchEvent", "过滤多点触碰");
			mEvents = -1;
			break;
		case MotionEvent.ACTION_MOVE:
			Log.w("dispatchTouchEvent", "手在滑动");
			if (mEvents == 0) {
				if (pullDownY > 0 || (((Pullable) pullableView).canPullDown() && canPullDown && state != ParamConst.LOADING)) {
					Log.w("dispatchTouchEvent", "当前位置可以下拉");
					// 如果isFresh变成true，则说明isRefreshAction方法已经断定当前操作是刷新操作
					moveY = ev.getY();
					moveX = ev.getX();
					if (isFresh == ParamConst.IS_REFRESH_DEFAULT) {
						if (!isRefreshAction(moveX, moveY)) {
							break;
						}
					}
					if (isFresh == ParamConst.IS_REFRESH_NO) {
						break;
					}
					Log.w("dispatchTouchEvent", "当前下滑的角度被识别成下拉动作，开始下拉动作");
					// 可以下拉，正在加载时不能下拉
					// 对实际滑动距离做缩小，造成用力拉的感觉
					pullDownY = pullDownY + (ev.getY() - lastY) / radio;
					if (pullDownY < 0) {
						pullDownY = 0;
						canPullDown = false;
						canPullUp = true;
					}
					if (pullDownY > getMeasuredHeight())
						pullDownY = getMeasuredHeight();
					if (state == ParamConst.REFRESHING) {
						// 正在刷新的时候触摸移动
						Log.w("dispatchTouchEvent", "当前状态为【正在刷新】，将isTouch改为true");
						isTouch = true;
					}
				} else if (pullUpY < 0 || (((Pullable) pullableView).canPullUp() && canPullUp && state != ParamConst.REFRESHING)) {
					// 可以上拉，正在刷新时不能上拉
					Log.w("dispatchTouchEvent", "当前位置可以上拉");
					pullUpY = pullUpY + (ev.getY() - lastY) / radio;
					if (pullUpY > 0) {
						pullUpY = 0;
						canPullDown = true;
						canPullUp = false;
					}
					if (pullUpY < -getMeasuredHeight())
						pullUpY = -getMeasuredHeight();
					if (state == ParamConst.LOADING) {
						// 正在加载的时候触摸移动
						Log.e("dispatchTouchEvent", "当前状态为【正在加载】，将isTouch改为true");
						isTouch = true;
					}
				} else {
					Log.w("dispatchTouchEvent", "当前位置既不能刷新也不能加载更多，不限制上拉或下拉");
				}
				releasePull();
			} else {
				mEvents = 0;
			}
			lastY = ev.getY();
			// 根据下拉距离改变比例
			radio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))));
			Log.w("dispatchTouchEvent", "radio:【" + radio + "】");
			if (pullDownY > 0 || pullUpY < 0) {
				Log.w("dispatchTouchEvent", "由于pullDownY【" + pullDownY + "】/pullUpY【" + pullUpY + "】不为0，所以需要刷新当前的布局");
				requestLayout();
			}
			if (pullDownY > 0) {
				Log.w("dispatchTouchEvent", "pullDownY【" + pullDownY + "】＞0");
				if (pullDownY <= refreshDist && (state == ParamConst.RELEASE_TO_REFRESH || state == ParamConst.DONE)) {
					// 如果下拉距离没达到刷新的距离且当前状态是释放刷新，改变状态为下拉刷新
					Log.w("dispatchTouchEvent", "下拉距离没达到刷新的距离且当前状态是释放刷新，改变状态为下拉刷新");
					changeState(ParamConst.INIT);
				}
				if (pullDownY >= refreshDist && state == ParamConst.INIT) {
					// 如果下拉距离达到刷新的距离且当前状态是初始状态刷新，改变状态为释放刷新
					Log.w("dispatchTouchEvent", "下拉距离达到刷新的距离且当前状态是初始状态刷新，改变状态为释放刷新");
					changeState(ParamConst.RELEASE_TO_REFRESH);
				}
			} else if (pullUpY < 0) {
				Log.w("dispatchTouchEvent", "pullUpY【" + pullUpY + "】＜0");
				// 下面是判断上拉加载的，同上，注意pullUpY是负值
				if (-pullUpY <= loadmoreDist && (state == ParamConst.RELEASE_TO_LOAD || state == ParamConst.DONE)) {
					Log.w("dispatchTouchEvent", "上拉距离没达到加载的距离且当前状态是释放加载，改变状态为初始状态");
					changeState(ParamConst.INIT);
				}
				// 上拉操作
				if (-pullUpY >= loadmoreDist && state == ParamConst.INIT) {
					Log.w("dispatchTouchEvent", "上拉距离达到加载的距离且当前状态是初始状态刷新，改变状态为释放加载");
					changeState(ParamConst.RELEASE_TO_LOAD);
				}
			}
			// 因为刷新和加载操作不能同时进行，所以pullDownY和pullUpY不会同时不为0，因此这里用(pullDownY +
			// Math.abs(pullUpY))就可以不对当前状态作区分了
			if ((pullDownY + Math.abs(pullUpY)) > 8) {
				// 防止下拉过程中误触发长按事件和点击事件
				Log.w("dispatchTouchEvent", "防止下拉过程中误触发长按事件和点击事件");
				ev.setAction(MotionEvent.ACTION_CANCEL);
			}
			break;
		case MotionEvent.ACTION_UP:
			isFresh = ParamConst.IS_REFRESH_DEFAULT;
			if (pullDownY > refreshDist || -pullUpY > loadmoreDist) {
				// 正在刷新时往下拉（正在加载时往上拉），释放后下拉头（上拉头）不隐藏
				Log.w("dispatchTouchEvent", "当前移动的距离＞刷新/加载的距离");
				isTouch = false;
			}
			if (state == ParamConst.RELEASE_TO_REFRESH) {
				Log.w("dispatchTouchEvent", "将状态改为【正在刷新】");
				changeState(ParamConst.REFRESHING);
				// 刷新操作
				if (mListener != null)
					mListener.onRefresh(this);
			} else if (state == ParamConst.RELEASE_TO_LOAD) {
				changeState(ParamConst.LOADING);
				// 加载操作
				if (mListener != null)
					mListener.onLoadMore(this);
			}
			hide();
		default:
			break;
		}
		// 事件分发交给父类
		super.dispatchTouchEvent(ev);
		return true;
	}

	/**
	 * 判断当前动作是否为刷新操作的方法（按照每个操作点的角度）
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isRefreshAction(float xMove, float yMove) {
		double tanValue = getTanValue(xMove, yMove);
		double tan = Math.tan(ParamConst.IS_REFRESH_ACTION_ANGLE * Math.PI / 180);
		Log.i("isRefreshAction", "通过两点组成的直角三角形计算以x作为对边、y作为临边算出的tan值【" + tanValue + "】，再与tan(30°)的值【" + tan + "】进行对比");
		if (tan <= tanValue) {
			Log.i("isRefreshAction", "大于tan值，说明不是下拉刷新操作");
			isFresh = ParamConst.IS_REFRESH_NO;
			return false;
		} else {
			Log.i("isRefreshAction", "小于tan值，说明是下拉刷新操作");
			isFresh = ParamConst.IS_REFRESH_YES;
			return true;
		}

	}

	/**
	 * 根据Xs、Ys的每一个点之间的计算，最终算出tan（贪帧特）的平均值
	 * 
	 * @return
	 */
	private double getTanValue(float xMove, float yMove) {
		// 获取两点，然后根据计算出两点的角度的平均值计算是否为向下拖动的动作
		double tmpX = Math.abs(downX - xMove);
		double tmpY = Math.abs(downY - yMove);
		return tmpX / tmpY;
	}

	/**
	 * 自动刷新
	 */
	public void autoRefresh() {
		AutoRefreshAndLoadTask task = new AutoRefreshAndLoadTask(this);
		task.execute(20);
	}

	/**
	 * 自动加载
	 */
	public void autoLoad() {
		pullUpY = -loadmoreDist;
		requestLayout();
		changeState(ParamConst.LOADING);
		// 加载操作
		if (mListener != null)
			mListener.onLoadMore(this);
	}

	private void initView() {
		// 初始化下拉布局
		pullView = refreshView.findViewById(R.id.pull_icon);
		refreshStateTextView = (TextView) refreshView.findViewById(R.id.state_tv);
		refreshingView = refreshView.findViewById(R.id.refreshing_icon);
		refreshStateImageView = refreshView.findViewById(R.id.state_iv);
		// 初始化上拉布局
		pullUpView = loadmoreView.findViewById(R.id.pullup_icon);
		loadStateTextView = (TextView) loadmoreView.findViewById(R.id.loadstate_tv);
		loadingView = loadmoreView.findViewById(R.id.loading_icon);
		loadStateImageView = loadmoreView.findViewById(R.id.loadstate_iv);
	}
	
	private void firstInit() {
		if (!isLayout) {
			// 这里是第一次进来的时候做一些初始化
			refreshView = getChildAt(0);
			pullableView = getChildAt(1);
			loadmoreView = getChildAt(2);
			isLayout = true;
			initView();
			refreshDist = ((ViewGroup) refreshView).getChildAt(0).getMeasuredHeight();
			loadmoreDist = ((ViewGroup) loadmoreView).getChildAt(0).getMeasuredHeight();
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.d("Test", "Test");
		firstInit();
		// 改变子控件的布局，这里直接用(pullDownY + pullUpY)作为偏移量，这样就可以不对当前状态作区分
		refreshView.layout(0, (int) (pullDownY + pullUpY) - refreshView.getMeasuredHeight(),
				refreshView.getMeasuredWidth(), (int) (pullDownY + pullUpY));
		pullableView.layout(0, (int) (pullDownY + pullUpY), pullableView.getMeasuredWidth(),
				(int) (pullDownY + pullUpY) + pullableView.getMeasuredHeight());
		loadmoreView.layout(0, (int) (pullDownY + pullUpY) + pullableView.getMeasuredHeight(),
				loadmoreView.getMeasuredWidth(),
				(int) (pullDownY + pullUpY) + pullableView.getMeasuredHeight() + loadmoreView.getMeasuredHeight());
	}

}
