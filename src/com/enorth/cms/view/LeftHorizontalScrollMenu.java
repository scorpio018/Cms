package com.enorth.cms.view;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.adapter.CommonListViewAdapter;
import com.enorth.cms.bean.MenuBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.menu.ContentOnTouchListener;
import com.enorth.cms.listener.menu.LeftMenuItemOnTouchListener;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.view.login.LoginActivity;
import com.enorth.cms.view.material.MaterialUploadActivity;
import com.enorth.cms.view.news.NewsListFragActivity;
import com.enorth.cms.view.news.NewsLiveListFragActivity;
import com.enorth.cms.view.securitysetting.SecuritySettingActivity;
import com.nineoldandroids.view.ViewHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LeftHorizontalScrollMenu extends HorizontalScrollView {
	/**
	 * 屏幕宽度
	 */
	private int mScreenWidth;
	/**
	 * dp
	 */
	private int mMenuRightPadding;
	/**
	 * 菜单的宽度
	 */
	private int mMenuWidth;
	
	private int mHalfMenuWidth;
	
	private boolean isOpen;

	private boolean once;
	
	private boolean isInitMenuData = false;
	
	private List<MenuBean> menuBeans;
	
	private LayoutInflater inflater;

	private LinearLayout wrapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	
	private Context context;
	
	private int curActivityResourceId;
	
	private ContentOnTouchListener contentOnTouchListener;
	
	public LeftHorizontalScrollMenu(Context context) {
		this(context, null, 0);
	}
	
	public LeftHorizontalScrollMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public LeftHorizontalScrollMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		inflater = LayoutInflater.from(context);
		mScreenWidth = ScreenTools.getPhoneWidth(context);
		mMenuRightPadding = mScreenWidth / 3;
	}
	
	/**
	 * 将当前的layout、左侧菜单和右侧的内容进行初始化
	 */
	private void initHorizontalScrollMenuView() {
		wrapper = (LinearLayout) getChildAt(0);
		initMenu();
		curActivityResourceId = SharedPreUtil.getInt(context, ParamConst.CUR_ACTIVITY_RESOURCE_ID);
	}
	
	private void initMenu() {
		mMenu = (ViewGroup) wrapper.getChildAt(0);
		initUserInfo();
		List<MenuBean> menuData = getMenuData();
		ListView menuListView = (ListView) mMenu.findViewById(R.id.menuListView);
		List<View> items = new ArrayList<View>();
		for (final MenuBean menuBean : menuData) {
			LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.menu_item, null);
			ImageView menuIcon = (ImageView) layout.getChildAt(0);
			menuIcon.setImageResource(menuBean.getMenuIconResourceId());
			TextView menuNameTV = (TextView) layout.getChildAt(1);
			menuNameTV.setText(menuBean.getMenuName());
			LeftMenuItemOnTouchListener listener = new LeftMenuItemOnTouchListener(ScreenTools.getTouchSlop(context)) {
				
				@Override
				public void onImgChangeDo(View v) {
					menuClick(menuBean);
				}
			};
			listener.changeColor(ColorUtil.getLightBlue(context), ColorUtil.getMenuBg(context));
			layout.setOnTouchListener(listener);
			items.add(layout);
		}
		ListAdapter adapter = new CommonListViewAdapter(items);
		menuListView.setAdapter(adapter);
	}
	
	private void initUserInfo() {
		ImageView headImg = (ImageView) mMenu.findViewById(R.id.headImg);
		headImg.setImageResource(R.drawable.head_img);
		TextView userNameTV = (TextView) mMenu.findViewById(R.id.userNameTV);
		userNameTV.setText("杨洋");
		TextView userTypeTV = (TextView) mMenu.findViewById(R.id.userTypeTV);
		userTypeTV.setText("程序猿");
	}
	
	private void getContent(Context context) {
		if (mContent == null) {
			mContent = (ViewGroup) wrapper.getChildAt(1);
			contentOnTouchListener = new ContentOnTouchListener(ScreenTools.getTouchSlop(getContext())) {
				
				@Override
				public void onImgChangeDo(View v) {
					if (isOpen) {
						closeMenu();
					}
				}
				
				@Override
				public boolean isStopEventTransfer() {
					if (isOpen) {
						return true;
					} else {
						return false;
					}
				}

			};
		}
	}
	
	private void menuClick(MenuBean menuBean) {
		if (menuBean.getMenuLayoutId() != curActivityResourceId) {
			if (menuBean.getActivity() == null) {
				toggle();
			} else {
				curActivityResourceId = menuBean.getMenuLayoutId();
				Intent intent = new Intent();
				intent.setClass(context, menuBean.getActivity().getClass());
				context.startActivity(intent);
				((Activity)context).finish();
			}
		} else {
			toggle();
		}
	}
	
	/**
	 * 切换菜单状态
	 */
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}
	
	/**
	 * 打开菜单
	 */
	public void openMenu() {
		AnimUtil.showRefreshFrame(getContext(), mContent, contentOnTouchListener, false, "");
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu() {
		AnimUtil.hideRefreshFrame();
		this.smoothScrollTo(mMenuWidth, 0);
		isOpen = false;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/**
		 * 显示的设置一个宽度
		 */
		if (!once) {
			initHorizontalScrollMenuView();
			mMenuWidth = mScreenWidth - mMenuRightPadding;
			mHalfMenuWidth = mMenuWidth / 2;
			mMenu.getLayoutParams().width = mMenuWidth;
			getContent(context);
			mContent.getLayoutParams().width = mScreenWidth;

		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		/*case MotionEvent.ACTION_DOWN:
			Log.w("dispatchTouchEvent", "手已经摁下屏幕了");
			downY = ev.getY();
			downX = ev.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			moveY = ev.getY();
			moveX = ev.getX();
			if (isHorizontalScroll == ParamConst.IS_HORIZONTAL_SCROLL_DEFAULT) {
				boolean refreshAction = ScreenTools.isVerticalAction(downX, downY, moveX, moveY);
				if (!refreshAction) {
					isHorizontalScroll = ParamConst.IS_HORIZONTAL_SCROLL_NO;
					break;
				} else {
					isHorizontalScroll = ParamConst.IS_HORIZONTAL_SCROLL_YES;
				}
			}
			if (isHorizontalScroll == ParamConst.IS_HORIZONTAL_SCROLL_NO) {
				break;
			}
			break;*/
		// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			if (scrollX > mHalfMenuWidth) {
				closeMenu();
			} else {
				openMenu();
			}
			return false;
		}
		return super.onTouchEvent(ev);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			// 将菜单隐藏
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt)
	{
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mMenuWidth;
//		float leftScale = 1 - 0.3f * scale;
//		float rightScale = 0.8f + scale * 0.2f;
		
//		ViewHelper.setScaleX(mMenu, leftScale);
//		ViewHelper.setScaleY(mMenu, leftScale);
//		ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
		ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.7f);

//		ViewHelper.setPivotX(mContent, 0);
//		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
//		ViewHelper.setScaleX(mContent, rightScale);
//		ViewHelper.setScaleY(mContent, rightScale);

	}
	
	private List<MenuBean> getMenuData() {
		if (!isInitMenuData) {
			menuBeans = new ArrayList<MenuBean>();
			MenuBean bean1 = new MenuBean();
			bean1.setMenuIconResourceId(R.drawable.menu_news_edit);
			bean1.setMenuName("普通新闻");
			bean1.setActivity(new NewsListFragActivity());
			bean1.setMenuLayoutId(R.layout.activity_news_list_frag);
			menuBeans.add(bean1);
			
			MenuBean bean2 = new MenuBean();
			bean2.setMenuIconResourceId(R.drawable.menu_news_live);
			bean2.setMenuName("图文直播");
			bean2.setActivity(new NewsLiveListFragActivity());
			bean2.setMenuLayoutId(R.layout.activity_news_live_list_frag);
			menuBeans.add(bean2);
			
			MenuBean bean3 = new MenuBean();
			bean3.setMenuIconResourceId(R.drawable.menu_video_live);
			bean3.setMenuName("视频直播");
			menuBeans.add(bean3);
			
			MenuBean bean4 = new MenuBean();
			bean4.setMenuIconResourceId(R.drawable.menu_material_upload);
			bean4.setMenuName("素材上传");
			bean4.setActivity(new MaterialUploadActivity());
			bean4.setMenuLayoutId(R.layout.activity_material_upload);
			menuBeans.add(bean4);
			
			MenuBean bean5 = new MenuBean();
			bean5.setMenuIconResourceId(R.drawable.menu_safety_setting);
			bean5.setMenuName("安全设置");
			bean5.setActivity(new SecuritySettingActivity());
			bean5.setMenuLayoutId(R.layout.activity_security_setting);
			menuBeans.add(bean5);
			
			MenuBean bean6 = new MenuBean();
			bean6.setMenuIconResourceId(R.drawable.menu_about);
			bean6.setMenuName("关于");
			menuBeans.add(bean6);
			
			MenuBean bean7 = new MenuBean();
			bean7.setMenuIconResourceId(R.drawable.menu_exit);
			bean7.setMenuName("退出");
			bean7.setActivity(new LoginActivity());
			bean7.setMenuLayoutId(R.layout.activity_login);
			menuBeans.add(bean7);
		}
		return menuBeans;
	}
	
}
