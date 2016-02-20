package com.enorth.cms.view;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.adapter.CommonListViewAdapter;
import com.enorth.cms.bean.MenuBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.menu.LeftMenuItemOnTouchListener;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.view.material.MaterialUploadActivity;
import com.enorth.cms.view.news.NewsListFragActivity;
import com.enorth.cms.view.news.NewsLiveListFragActivity;
import com.nineoldandroids.view.ViewHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
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
//		createNewLayout(context);
//		initHorizontalScrollMenuView();
		mScreenWidth = ScreenTools.getScreenWidth(context);
//		mMenuRightPadding = 150;
		mMenuRightPadding = mScreenWidth / 3;
		/*TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
			case R.styleable.SlidingMenu_rightPadding:
				// 默认50
				mMenuRightPadding = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50f,
								getResources().getDisplayMetrics()));// 默认为10DP
				break;
			}
		}
		a.recycle();*/
	}
	
	/**
	 * 将当前的layout、左侧菜单和右侧的内容进行初始化
	 */
	private void initHorizontalScrollMenuView() {
		wrapper = (LinearLayout) getChildAt(0);
		initMenu();
		SharedPreUtil.remove(context, ParamConst.CUR_ACTIVITY_RESOURCE_ID);
		curActivityResourceId = SharedPreUtil.getInt(context, ParamConst.CUR_ACTIVITY_RESOURCE_ID);
		
		if (curActivityResourceId == -1) {
			curActivityResourceId = R.layout.activity_news_list_frag;
//			SharedPreUtil.put(context, ParamConst.CUR_ACTIVITY_RESOURCE_ID, curActivityResourceId);
		}
//		createNewLayout(context, curActivityResourceId);
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
			layout.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					menuClick(menuBean);
				}
			});
			/*LeftMenuItemOnTouchListener listener = new LeftMenuItemOnTouchListener() {
				
				@Override
				public void onImgChangeDo() {
					menuClick(menuBean);
				}
			};
			listener.changeColor(R.color.gray_lighter, R.color.menu_bg);
			layout.setOnTouchListener(listener);*/
			items.add(layout);
		}
		ListAdapter adapter = new CommonListViewAdapter(items);
		menuListView.setAdapter(adapter);
	}
	
	private void menuClick(MenuBean menuBean) {
		if (menuBean.getMenuLayoutId() != curActivityResourceId) {
			if (menuBean.getActivity() == null) {
				toggle();
			} else {
				Intent intent = new Intent();
				intent.setClass(context, menuBean.getActivity().getClass());
				context.startActivity(intent);
//				SharedPreUtil.put(context, ParamConst.CUR_ACTIVITY_RESOURCE_ID, menuBean.getMenuLayoutId());
				((Activity)context).finish();
			}
		} else {
			toggle();
		}
	}
	
	private void initUserInfo() {
		ImageView headImg = (ImageView) mMenu.findViewById(R.id.headImg);
		headImg.setImageResource(R.drawable.head_img);
		TextView userNameTV = (TextView) mMenu.findViewById(R.id.userNameTV);
		userNameTV.setText("杨洋");
		TextView userTypeTV = (TextView) mMenu.findViewById(R.id.userTypeTV);
		userTypeTV.setText("程序猿");
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
			menuBeans.add(bean5);
			
			MenuBean bean6 = new MenuBean();
			bean6.setMenuIconResourceId(R.drawable.menu_about);
			bean6.setMenuName("关于");
			menuBeans.add(bean6);
			
			MenuBean bean7 = new MenuBean();
			bean7.setMenuIconResourceId(R.drawable.menu_exit);
			bean7.setMenuName("退出");
			menuBeans.add(bean7);
		}
		return menuBeans;
	}
	
	/**
	 * 新建一个内容并加到当前的layout中
	 * @param context
	 * @param layout
	 * @return
	 */
	/*private void createNewLayout(Context context, int layoutResourceId) {
		
		mContent = (ViewGroup) wrapper.getChildAt(1);
		if (mContent != null) {
			wrapper.removeViewAt(1);
		}
		
		mContent = (ViewGroup) inflater.inflate(layoutResourceId, null);
		LinearLayout.LayoutParams initFillLayout = LayoutParamsUtil.initFillLayout();
		wrapper.addView(mContent, initFillLayout);
	}*/
	
	private void getContent(Context context) {
		mContent = (ViewGroup) wrapper.getChildAt(1);
	}
	
	private Activity getActivityByLayoutId(int layoutId) {
		List<MenuBean> menuData = getMenuData();
		for (MenuBean menuBean : menuData) {
			if (menuBean.getMenuLayoutId() == layoutId) {
				return menuBean.getActivity();
			}
		}
		return null;
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/**
		 * 显示的设置一个宽度
		 */
		if (!once)
		{
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
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		if (changed)
		{
			// 将菜单隐藏
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		int action = ev.getAction();
		switch (action)
		{
		// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			if (scrollX > mHalfMenuWidth)
			{
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;
			} else
			{
				this.smoothScrollTo(0, 0);
				isOpen = true;
			}
			return false;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 打开菜单
	 */
	public void openMenu()
	{
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu()
	{
		if (isOpen)
		{
			this.smoothScrollTo(mMenuWidth, 0);
			isOpen = false;
		}
	}

	/**
	 * 切换菜单状态
	 */
	public void toggle()
	{
		if (isOpen)
		{
			closeMenu();
		} else
		{
			openMenu();
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

}
