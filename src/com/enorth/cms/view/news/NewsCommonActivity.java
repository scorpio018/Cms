package com.enorth.cms.view.news;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.enorth.cms.adapter.news.NewsListFragmentPagerAdapter;
import com.enorth.cms.adapter.news.NewsListViewAdapter;
import com.enorth.cms.bean.BottomMenuOperateDataBasicBean;
import com.enorth.cms.bean.Page;
import com.enorth.cms.bean.ViewColorBasicBean;
import com.enorth.cms.bean.channel_search.RequestNewsSearchUrlBean;
import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.bean.login.LoginBean;
import com.enorth.cms.bean.news_list.BottomMenuBasicBean;
import com.enorth.cms.bean.news_list.NewsListBean;
import com.enorth.cms.bean.news_list.RequestNewsListUrlBean;
import com.enorth.cms.common.EnableSimpleChangeButton;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.consts.UrlConst;
import com.enorth.cms.fragment.NewsListFragment;
import com.enorth.cms.handler.newslist.NewsListViewHandler;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.listener.color.ChangeBGColorOnTouchListener;
import com.enorth.cms.listener.color.UnChangeBGColorOnTouchListener;
import com.enorth.cms.listener.newslist.viewpager.NewsCommonViewPagerOnPageChangeListener;
import com.enorth.cms.listener.popup.bottombtn.BottomBtnMorePopupWindowOnClickListener;
import com.enorth.cms.listener.popup.newssubtitle.NewsSubTitlePopupWindowOnTouchListener;
import com.enorth.cms.listener.popup.newstitle.NewsTitlePopupWindowOnTouchListener;
import com.enorth.cms.presenter.newslist.INewsListFragPresenter;
import com.enorth.cms.presenter.newslist.NewsListFragPresenter;
import com.enorth.cms.utils.BeanParamsUtil;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.DrawableUtil;
import com.enorth.cms.utils.JsonUtil;
import com.enorth.cms.utils.LayoutParamsUtil;
import com.enorth.cms.utils.PopupWindowUtil;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.StringUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.BaseFragmentActivity;
import com.enorth.cms.view.R;
import com.enorth.cms.widget.popupwindow.CommonPopupWindow;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class NewsCommonActivity extends BaseFragmentActivity implements INewsCommonView {
	/**
	 * 左侧菜单
	 */
	// protected LeftHorizontalScrollMenu menu;
	/**
	 * 底部的操作按钮布局
	 */
	protected LinearLayout newsOperateBtnLayout;
	/**
	 * 新闻操作类型的按钮布局（待编辑、待签发、已签发），外层需要套一个RelativeLayout以保证按钮能水平居中
	 */
	private LinearLayout newsTypeBtnLineLayout;
	/**
	 * 标题中间的标题名
	 */
	private TextView titleText;
	/**
	 * 副标题左侧的频道名称
	 */
	private TextView newsSubTitleTV;
	/**
	 * 副标题中的排序图标
	 */
	private ImageView newsSubTitleSort;
	/**
	 * 在没有新闻并且刷新的时候显示，其他情况则隐藏
	 */
	private RelativeLayout hintRelative;
	/**
	 * 新闻列表的ViewPager，里面根据newsTypeBtnText的length放相应的ListView
	 */
	private ViewPager newsListViewPager;
	/**
	 * 右下角的“+”图案
	 */
	protected ImageView addNewsBtn;
	/**
	 * 标题点击弹出的弹出框
	 */
	private CommonPopupWindow newsTitlePopupWindow;
	/**
	 * 副标题中的排序图标点击弹出的弹出框
	 */
	private CommonPopupWindow subNewsTitleSortPopupWindow;
	/**
	 * 底部菜单点击“更多”弹出的弹出框
	 */
	private CommonPopupWindow bottomMenuBtnPopupWindow;
	/**
	 * 标题弹出框工具
	 */
	private PopupWindowUtil newsTitlePopupWindowUtil;
	/**
	 * 副标题中的排序图标弹出框工具
	 */
	private PopupWindowUtil subNewsTitleSortPopupWindowUtil;
	/**
	 * 底部菜单点击“更多”弹出的弹出框工具
	 */
	private PopupWindowUtil bottomMenuBtnPopupWindowUtil;
	/**
	 * 将ViewPager中的ListView存入此集合中
	 */
	private List<NewsListFragment> views = new ArrayList<NewsListFragment>();
	/**
	 * 将ViewPager中的ListView存入此集合中
	 */
	private List<PullToRefreshListView> listViews = new ArrayList<PullToRefreshListView>();
	/**
	 * 将ListView使用到的adapter存入此集合中
	 */
	private List<NewsListViewAdapter> listViewAdapter = new ArrayList<NewsListViewAdapter>();
	/**
	 * 当前的状态，有INIT(初始状态)、REFRESHING(刷新)、LOADING(加载)三种
	 */
	private int curRefreshState = ParamConst.INIT;
	// public List<View> views = new ArrayList<View>();
	/**
	 * 选中的新闻总和
	 */
	protected int[] selectedNewsCount = { 0, 0, 0 };
	/**
	 * 标题的所有类别（普通新闻）
	 */
	private List<String> allNewsTitleName;
//	private Map<String, String> allNewsTitleName;
	/**
	 * 当前的新闻标题
	 */
	private String curNewsTitleName;
	/**
	 * 新闻排序的类别
	 */
//	private String[] allSubNewsTitleSortName = {ParamConst.NEWS_SORT_BY_LIST_ORDER, ParamConst.NEWS_SORT_BY_PUB_DATE, ParamConst.NEWS_SORT_BY_MOD_DATE};
	
//	private String curSubNewsTitleSortName = ParamConst.NEWS_SORT_BY_MOD_DATE;
	
	private Map<String, String> allSubNewsTitleSortMap;
	/**
	 * 当前新闻排序的字段
	 */
	private String curSubNewsTitleSortColumn = ParamConst.NEWS_SORT_BY_MOD_DATE_KEY;
	/**
	 * 当前请求的url地址
	 */
	private String curUrl;
	/**
	 * 标题的所有类别（普通新闻）
	 */
//	private List<NewsTitleBean> newsTitleBeans;
	/**
	 * 切换新闻列表的标头按钮
	 */
	private String[] newsTypeBtnText = { "编辑", "待签发", "已签发" };
	/**
	 * 切换新闻列表时对应的state状态
	 */
	private int[] newsStateBtnState = { ParamConst.STATE_EDIT, ParamConst.STATE_TO_PUB, ParamConst.STATE_PUB };
	/**
	 * 编辑、待签发、已签发的按钮组的集合
	 */
	private List<EnableSimpleChangeButton> newsStateBtns;
	/**
	 * 初始化新闻操作类型的按钮布局（待编辑、待签发、已签发）
	 */
	private RelativeLayout newsTypeBtnRelaLayout;
	/**
	 * 右下角的"+"在对应的新闻列表中是否显示
	 */
	public boolean[] isShowAddNewsBtn = { true, false, false };
	/**
	 * 获取搜索图标的Drawable，用于搜索新闻之后加入到对应按钮组中选中的按钮上
	 */
	public Drawable newsStateBtnRightDrawable;
	/**
	 * 底部菜单的基础bean（里面包括按钮可选/不可选图标、文组描述、提示信息、颜色）
	 */
	protected BottomMenuOperateDataBasicBean bottomMenuOperateBean;
	/**
	 * 标识当前底部菜单是否存在“更多”按钮
	 */
	private boolean hasNewsIsInMore = false;
	/**
	 * “更多”按钮对应的bean
	 */
	private BottomMenuBasicBean moreBean;
	/**
	 * “更多”按钮
	 */
	private View moreOperateMenuBtn;
	/**
	 * 底部菜单的默认颜色
	 */
	protected int newsOperateBtnBasicColor;
	/**
	 * 白色
	 */
	protected int whiteColor;
	/**
	 * 选中的颜色
	 */
	private int checkedColor;
	/**
	 * 蓝色
	 */
//	protected int blueColor;
	/**
	 * 新闻列表中的标题最多能显示多少字
	 */
	private int newsTitleAllowLength;
	/**
	 * ViewPager当前页
	 */
	private int curPosition = 0;
	/**
	 * 底部菜单的基本数据集合
	 */
	protected List<BottomMenuBasicBean> bottomMenuList;
	/**
	 * 当前新闻列表联动底部菜单可以操作的状态值（默认都没选中）
	 */
	protected int canEnableState = ParamConst.CAN_ENABLE_STATE_DEFAULT;
	/**
	 * 在onResume中用于判断副标题的第一次加载是否已经完成
	 */
	private boolean isSubTitleInitFinish = false;
	/**
	 * 频道ID
	 */
	protected Long channelId;
	/**
	 * 新闻频道名称
	 */
	private String newsSubTitleText;
	/**
	 * 用户登录时获取的当前的新闻频道bean
	 */
	protected ChannelBean channelBean;
	/**
	 * 当前登录的用户bean
	 */
	private LoginBean loginBean;
	/**
	 * 请求新闻列表时需要传入接口的参数bean
	 */
	protected RequestNewsListUrlBean newsListBean;

	protected Intent intent = new Intent();

	protected INewsListFragPresenter presenter;
	/**
	 * 副标题中的频道加载
	 */
	protected Handler newsSubTitleHandler;
	
	private int refreshType = ParamConst.REFRESH_TYPE_DEFAULT;
	/**
	 * 如果进行新闻搜索，则在修改refreshType的同时，当新结果加载完之后，需要将此参数变为true
	 */
	private boolean isNewsSearched = false;
	/**
	 * 获取搜索的新闻条件bean
	 */
	private RequestNewsSearchUrlBean requestNewsSearchUrlBean;

	/**
	 * 初始化底部菜单bean
	 * 
	 * @return
	 */
	public abstract BottomMenuOperateDataBasicBean initBottomMenuOperateBean();

	/**
	 * 在此方法中加载xml文件
	 */
	public abstract void setNewsListContentView();

	/**
	 * 将当前activity存入thisActivity中
	 * 
	 * @return
	 */
	public abstract Activity getCurActivity();

	/**
	 * 初始化标题的一系列操作
	 */
	public abstract void initNewsTitle();
	/**
	 * 初始化所有新闻标题的名称
	 * @return
	 */
	public abstract List<String> setAllNewsTitleText();
	
	/**
	 * 初始化副标题的一系列操作
	 */
	public abstract void initNewsSubTitle();
	
	public abstract int initCurNewsType();
	
	public abstract int initNewsTypeBtnColor();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNewsListContentView();
		bottomMenuOperateBean = initBottomMenuOperateBean();
		initBasicData();
		initNewsTitle();
		initNewsTitleEvent();
		initNewsSubTitle();
		initSubTitleSortImageEvent();
		// 初始化新闻操作类型的按钮布局（待编辑、待签发、已签发）
		initNewsTypeBtnLayout();
		// 加载ViewPager
		initViewPager();
		initAddNewsBtn();
		// initNewsListLayout();
		// 加载底部菜单
		initNewsOperateBtnLayout();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 从当前activity跳转到NewsSearchActivity并返回到当前activity时进入此条件
		case ParamConst.NEWS_LIST_FRAG_ACTIVITY_TO_NEWS_SEARCH_ACTIVITY_REQUEST_CODE:
			if (resultCode == ParamConst.NEWS_SEARCH_ACTIVITY_BACK_TO_NEWS_LIST_FRAG_ACTIVITY_RESULT_CODE) {
				Bundle extras = data.getExtras();
				// 获取搜索的新闻条件bean
				requestNewsSearchUrlBean = (RequestNewsSearchUrlBean) extras.getSerializable(ParamConst.NEWS_SEARCH_BEAN);
				Page page = new Page();
				requestNewsSearchUrlBean.setPage(page);
				refreshType = ParamConst.REFRESH_TYPE_NEWS_SEARCH;
				isNewsSearched = false;
				listViews.get(curPosition).setRefreshing();
			}
			break;
		case ParamConst.NEWS_LIVE_LIST_FRAG_ACTIVITY_TO_NEWS_SEARCH_ACTIVITY_REQUEST_CODE:
			if (resultCode == ParamConst.NEWS_SEARCH_ACTIVITY_BACK_TO_NEWS_LIST_FRAG_ACTIVITY_RESULT_CODE) {
				Bundle extras = data.getExtras();
				// 获取搜索的新闻条件bean
				requestNewsSearchUrlBean = (RequestNewsSearchUrlBean) extras.getSerializable(ParamConst.NEWS_SEARCH_BEAN);
				Page page = new Page();
				requestNewsSearchUrlBean.setPage(page);
				refreshType = ParamConst.REFRESH_TYPE_NEWS_SEARCH;
				isNewsSearched = false;
				listViews.get(curPosition).setRefreshing();
			}
			break;
			
		case ParamConst.NEWS_LIST_FRAG_ACTIVITY_TO_CHANNEL_SEARCH_ACTIVITY_REQUEST_CODE:
			if (resultCode == ParamConst.CHANNEL_SEARCH_ACTIVITY_BACK_TO_NEWS_LIST_FRAG_ACTIVITY_RESULT_CODE) {
				channelBean = StaticUtil.getCurChannelBean(this);
				newsListBean.setChannelId(channelBean.getChannelId());
				newsSubTitleTV.setText(channelBean.getChannelName());
				NewsListViewAdapter newsListViewAdapter = listViewAdapter.get(curPosition);
				newsListViewAdapter.getItems().clear();
				newsListViewAdapter.notifyDataSetChanged();
				listViews.get(curPosition).postDelayed(new Runnable() {
					
					@Override
					public void run() {
						listViews.get(curPosition).setRefreshing();
					}
				}, 100);
			}
		case ParamConst.NEWS_LIVE_LIST_FRAG_ACTIVITY_TO_CHANNEL_SEARCH_ACTIVITY_REQUEST_CODE:
			if (resultCode == ParamConst.CHANNEL_SEARCH_ACTIVITY_BACK_TO_NEWS_LIST_FRAG_ACTIVITY_RESULT_CODE) {
				channelBean = StaticUtil.getCurChannelBean(this);
				newsListBean.setChannelId(channelBean.getChannelId());
				newsSubTitleTV.setText(channelBean.getChannelName());
				NewsListViewAdapter newsListViewAdapter = listViewAdapter.get(curPosition);
				newsListViewAdapter.getItems().clear();
				newsListViewAdapter.notifyDataSetChanged();
				listViews.get(curPosition).postDelayed(new Runnable() {
					
					@Override
					public void run() {
						listViews.get(curPosition).setRefreshing();
					}
				}, 100);
			}
		default:
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	/**
	 * 初始化基本数据
	 * 
	 * @throws Exception
	 */
	protected void initBasicData() {
		initView();
		presenter = new NewsListFragPresenter(this);
		newsOperateBtnBasicColor = ContextCompat.getColor(NewsCommonActivity.this, R.color.bottom_text_color_basic);
		whiteColor = ColorUtil.getWhiteColor(NewsCommonActivity.this);
		checkedColor = initNewsTypeBtnColor();
		// 获取屏幕的分辨率
		newsTitleAllowLength = ScreenTools.getPhoneWidth(NewsCommonActivity.this) / ParamConst.FONT_WIDTH;
		channelBean = StaticUtil.getCurChannelBean(this);
		loginBean = StaticUtil.getCurLoginBean(this);

		initNewsListBean();
		// 将所有的新闻标题进行初始化
		allNewsTitleName = setAllNewsTitleText();
		
		// 将第一个标题存入当前标题中
		curNewsTitleName = allNewsTitleName.get(0);
		
		curUrl = StaticUtil.getCurScanBean(this).getScanUrl() + UrlConst.NEWS_LIST_POST_URL;
		
		initSortData();
		initListViewAdapter();
	}
	
	private void initView() {
		titleText = (TextView) findViewById(R.id.titleMiddleTV);
		newsTypeBtnRelaLayout = (RelativeLayout) findViewById(R.id.newsTypeBtnRelaLayout);
		newsListViewPager = (ViewPager) findViewById(R.id.newsListViewPager);
		addNewsBtn = (ImageView) findViewById(R.id.addNewsBtn);
		newsOperateBtnLayout = (LinearLayout) findViewById(R.id.newsOperateBtnLayout);
		newsSubTitleSort = (ImageView) findViewById(R.id.newsSubTitleSort);
		hintRelative = (RelativeLayout) findViewById(R.id.hintRelative);
	}
	
	/**
	 * 将ViewPager中的所有ListView对应的adapter进行初始化
	 */
	private void initListViewAdapter() {
		int length = newsTypeBtnText.length;
		for (int i = 0; i < length; i++) {
//			NewsListViewAdapter adapter = new NewsListViewAdapter(this, 0, items);
			NewsListViewAdapter adapter = new NewsListViewAdapter(new ArrayList<NewsListBean>(), this);
			listViewAdapter.add(adapter);
		}
	}
	
	/**
	 * 将可选的排序的key/value进行初始化
	 */
	private void initSortData() {
		allSubNewsTitleSortMap = new LinkedHashMap<String, String>();
		allSubNewsTitleSortMap.put(ParamConst.NEWS_SORT_BY_LIST_ORDER_KEY, ParamConst.NEWS_SORT_BY_LIST_ORDER_VALUE);
		allSubNewsTitleSortMap.put(ParamConst.NEWS_SORT_BY_PUB_DATE_KEY, ParamConst.NEWS_SORT_BY_PUB_DATE_VALUE);
		allSubNewsTitleSortMap.put(ParamConst.NEWS_SORT_BY_MOD_DATE_KEY, ParamConst.NEWS_SORT_BY_MOD_DATE_VALUE);
	}
	
	/**
	 * 标题点击弹出事件
	 */
	private void initNewsTitleEvent() {
		titleText.setCompoundDrawablesWithIntrinsicBounds(null, null, DrawableUtil.getDrawable(this, R.drawable.news_down_btn), null);
		ChangeBGColorOnTouchListener listener = new ChangeBGColorOnTouchListener(this) {
			
			@Override
			public void onImgChangeEnd(View v) {
				
			}
			
			@Override
			public void onImgChangeDo(View v) {
				initNewsTitlePopupWindow();
			}
		};
//		listener.changeColor(ColorUtil.getBgGrayPress(this), ColorUtil.getCommonBlueColor(this));
		titleText.setOnTouchListener(listener);
	}
	
	// 标题弹出框
	private void initNewsTitlePopupWindow() {
		if (newsTitlePopupWindowUtil == null) {
			newsTitlePopupWindowUtil = new PopupWindowUtil(this, titleText) {
				
				@Override
				public void initItems(LinearLayout layout) {
					NewsTitlePopupWindowOnTouchListener listener = new NewsTitlePopupWindowOnTouchListener(NewsCommonActivity.this, layout) {
						@Override
						public void onImgChangeEnd(View v) {
							newsTitlePopupWindow.dismiss();
							newsTitlePopupWindow = null;
						}
					};
					initPopupWindowItemsContainCheckMark(layout, listener, allNewsTitleName, curNewsTitleName);
				}
			};
			newsTitlePopupWindowUtil.setXoffInPixels(-newsTitlePopupWindowUtil.getWidth() / 2);
		}
		newsTitlePopupWindow = newsTitlePopupWindowUtil.initPopupWindow();
	}
	
	/**
	 * 副标题中的排序图标点击弹出事件
	 */
	private void initSubTitleSortImageEvent() {
		newsSubTitleSort.setVisibility(View.GONE);
		UnChangeBGColorOnTouchListener listener = new UnChangeBGColorOnTouchListener(this) {
			
			@Override
			public void onImgChangeEnd(View v) {
				
			}
			
			@Override
			public void onImgChangeDo(View v) {
				initSubNewsTitlePopupWindow();
			}
		};
		newsSubTitleSort.setOnTouchListener(listener);
	}
	
	/**
	 * 副标题中的排序图标的弹出框
	 */
	private void initSubNewsTitlePopupWindow() {
		if (subNewsTitleSortPopupWindowUtil == null) {
			subNewsTitleSortPopupWindowUtil = new PopupWindowUtil(this, newsSubTitleSort) {
				
				@Override
				public void initItems(LinearLayout layout) {
					NewsSubTitlePopupWindowOnTouchListener listener = new NewsSubTitlePopupWindowOnTouchListener(NewsCommonActivity.this, layout) {
						@Override
						public void onImgChangeEnd(View v) {
							subNewsTitleSortPopupWindow.dismiss();
							subNewsTitleSortPopupWindow = null;
						}
					};
					initPopupWindowItemsContainCheckMark(layout, listener, allSubNewsTitleSortMap, curSubNewsTitleSortColumn);
				}
			};
			subNewsTitleSortPopupWindowUtil.setXoffInPixels(-subNewsTitleSortPopupWindowUtil.getWidth());
		}
		subNewsTitleSortPopupWindow = subNewsTitleSortPopupWindowUtil.initPopupWindow();
	}
	
	private void initNewsListBean() {
		newsListBean = new RequestNewsListUrlBean();
		newsListBean.setChannelId(channelBean.getChannelId());
//		newsListBean.setInitEditor(loginBean.getUserId());
		newsListBean.setState(newsStateBtnState[0]);
		newsListBean.setNewsType(initCurNewsType());
		Page page = new Page();
		page.setOrderBy(curSubNewsTitleSortColumn);
		newsListBean.setPage(page);
	}

	/**
	 * 初始化新闻操作类型的按钮布局（待编辑、待签发、已签发）
	 * 
	 * @throws Exception
	 */
	private void initNewsTypeBtnLayout() {
		newsTypeBtnLineLayout = (LinearLayout) newsTypeBtnRelaLayout.getChildAt(0);
		newsStateBtns = ViewUtil.initBtnGroupLayout(NewsCommonActivity.this, newsTypeBtnLineLayout, checkedColor, newsTypeBtnText,
				newsStateBtnState, 0.9f);
		// 获取搜索图标的Drawable，用于搜索新闻之后加入到对应按钮组中选中的按钮上
		newsStateBtnRightDrawable = DrawableUtil.getDrawable(this, R.drawable.nav_search);
	}

	/**
	 * 初始化ViewPager
	 * 
	 * @throws Exception
	 */
	protected void initViewPager() {
		for (int i = 0; i < 3; i++) {
			NewsListFragment fragment = new NewsListFragment(this, i);
			views.add(fragment);
			
		}
		NewsListFragmentPagerAdapter adapter = new NewsListFragmentPagerAdapter(getSupportFragmentManager(), views);
		newsListViewPager.setAdapter(adapter);
		newsListViewPager.addOnPageChangeListener(new NewsCommonViewPagerOnPageChangeListener(this));
		initDefaultData(null);
	}
	
	protected void initDefaultData(String errorHint) {
		TextView newsListViewDefaultText = (TextView) findViewById(R.id.newsListViewDefaultText);
		if (StringUtil.isNotEmpty(errorHint)) {
			newsListViewDefaultText.setText(errorHint);
		}
	}

	/**
	 * 初始化右下角的“+”图案
	 */
	public abstract void initAddNewsBtn();

	/**
	 * 初始化底部的操作按钮布局
	 */
	protected void initNewsOperateBtnLayout() {
		initNewsOperateBtn();
	}

	/**
	 * 初始化新闻列表的内容布局
	 * 
	 * @param newsListView
	 * @param needInitData
	 *            为true时，errorHint可以为null，当请求url时出现异常，则使用errorHint提示，如果为空，
	 *            则使用默认内容
	 * @param errorHint
	 * @throws Exception
	 */
	@Override
	public void initNewsListData(PullToRefreshListView newsListView, boolean needInitData, String errorHint) {
		if (needInitData) {
			Handler newsListViewHandler = new NewsListViewHandler(this, newsListView, errorHint);
			initData(newsListViewHandler, curUrl);
		} else {
			NewsListViewAdapter adapter = listViewAdapter.get(curPosition);
//			adapter.setItems(new ArrayList<NewsListBean>());
//			adapter.notifyDataSetChanged();
			if (StringUtil.isNotEmpty(errorHint)) {
				if (adapter.getItems().size() == 0) {
					hintRelative.setVisibility(View.VISIBLE);
				} else {
					hintRelative.setVisibility(View.GONE);
				}
				ViewUtil.showAlertDialog(this, errorHint);
			}
			listViews.get(curPosition).onRefreshComplete();
		}
	}
	
	/**
	 * 在从新闻搜索页带回条件时进行的新搜索
	 * @param newsListView
	 */
	public void initNewsSearchListData(PullToRefreshListView newsListView) {
		// 将bean处理成传入接口中的格式
		List<BasicNameValuePair> initData = BeanParamsUtil.initData(requestNewsSearchUrlBean, this);
		// 初始化当前ListView对应的handler
		Handler newsListViewHandler = new NewsListViewHandler(this, listViews.get(curPosition), null);
		// 将当前搜索URL进行替换
		curUrl = StaticUtil.getCurScanBean(this).getScanUrl() + UrlConst.SEARCH_NEWS_POST_URL;
		// 在当前选中的按钮组中加入搜索图标
//		newsStateBtns.get(curPosition).setCompoundDrawablesWithIntrinsicBounds(null, null, newsStateBtnRightDrawable, null);
		int height = newsTypeBtnLineLayout.getMeasuredHeight();
		newsStateBtnRightDrawable.setBounds(0, 0, height / 2, height / 2);
		newsStateBtns.get(curPosition).setCompoundDrawables(null, null, newsStateBtnRightDrawable, null);
		presenter.requestListViewData(curUrl, newsListViewHandler, initData);
		isNewsSearched = true;
	}
	
	/**
	 * 访问新闻列表的接口，并将从中获取的数据放入对应的控件中显示
	 * 
	 * @param handler
	 * @param newsListView
	 * @throws Exception
	 */
	protected void initData(Handler handler, String url) {
		List<BasicNameValuePair> initData = BeanParamsUtil.initData(newsListBean, this);
		presenter.requestListViewData(url, handler, initData);
	}

	/**
	 * 将从接口中获取的数据放入对应的控件中显示
	 */
	@Override
	public void initReturnJsonData(String result, Handler handler) {
		List<NewsListBean> resultView = new ArrayList<NewsListBean>();
		if (StringUtil.isNotEmpty(result)) {
			JSONArray jsonArray = JsonUtil.initJsonArray(result);
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject jo = JsonUtil.getJSONObject(jsonArray, i);
//				View view = packageNewsData(jo);
				NewsListBean bean = packageNewsData(jo);
				if (bean == null) {
//					ViewUtil.showAlertDialog(this, "数据错误");
					Message msg = new Message();
					msg.what = ParamConst.MESSAGE_WHAT_ERROR;
					msg.obj = "数据错误";
					handler.sendMessage(msg);
					return;
//					throw new JSONException("数据错误");
				}
				resultView.add(bean);
			}
		}

		if (resultView.size() == 0) {
			// msg.what = ParamConst.MESSAGE_WHAT_NO_DATA;
			handler.sendEmptyMessage(ParamConst.MESSAGE_WHAT_NO_DATA);
		} else {
			Message msg = new Message();
			msg.what = ParamConst.MESSAGE_WHAT_SUCCESS;
			msg.obj = resultView;
			handler.sendMessage(msg);
		}
	}
	
	private NewsListBean packageNewsData(JSONObject jo) {
		NewsListBean newsListBean = (NewsListBean) BeanParamsUtil.saveJsonToObject(jo, NewsListBean.class);
		return newsListBean;
	}

	public void checkBtnClickEvent(boolean isChecked) {
		if (isChecked) {
			selectedNewsCount[curPosition]++;
		} else {
			selectedNewsCount[curPosition]--;
		}
		changeCanEnableState();
		changeBottomMenuBtnState();
	}

	/**
	 * 根据当前选中的标头按钮的位置改变需要改变样式的按钮，并清除需要清除的ListView中的数据
	 * 
	 * @param position
	 * @throws Exception
	 */
	public void changeNewsTypeBtnStyleByFocusedState(int position) {
		ViewUtil.changeBtnGroupStyleByFocusedState(NewsCommonActivity.this, newsTypeBtnLineLayout, position,
				checkedColor, whiteColor);
	}

	/**
	 * 初始化底部菜单的每一个按钮
	 */
	public void initNewsOperateBtn() {
		int i = 0;
		// 获取底部的每一个按钮的基本参数
		initNewsOperateBtnData();
		changeCanEnableState();
		initBottomMenuBtnState();
		for (final BottomMenuBasicBean bean : bottomMenuList) {
			LinearLayout layout = (LinearLayout) newsOperateBtnLayout.getChildAt(i++);
			final ImageView bottomIV = (ImageView) layout.getChildAt(0);
			
			final String hint = bean.getDisableHint();
			OnClickListener listener = new CommonOnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 如果当前点击的按钮处于disable状态，则不进行任何操作
					if ((Boolean) bottomIV.getTag()) {
						if ((Boolean)v.getTag()) {
							initBottomMenuBtnPopupWindow();
						} else {
							ViewUtil.showAlertDialog(NewsCommonActivity.this, bean.getTextContent());
						}
					} else {
						ViewUtil.showAlertDialog(NewsCommonActivity.this, hint);
					}
				}
			};
			layout.setOnClickListener(listener);
		}
	}

	/**
	 * 初始化底部菜单的信息（注释原因：底部菜单的展现格式有变）
	 * 
	 * @return
	 */
	protected void initNewsOperateBtnData() {
		hasNewsIsInMore = false;
		moreOperateMenuBtn = null;
		int[] newsOperateBtnChecked = bottomMenuOperateBean.getNewsOperateBtnChecked(curPosition);
		int[] newsOperateBtnDisabled = bottomMenuOperateBean.getNewsOperateBtnDisabled(curPosition);
		String[] newsOperateBtnTextContent = bottomMenuOperateBean.getNewsOperateBtnTextContent(curPosition);
		String[] disableHint = bottomMenuOperateBean.getDisableHint(curPosition);
		int[] newsOperateBtnCanEnableState = bottomMenuOperateBean.getNewsOperateBtnCanEnableState(curPosition);
		boolean[] newsIsInMore = bottomMenuOperateBean.getNewsIsInMore(curPosition);
		int length = newsOperateBtnChecked.length;
		bottomMenuList = new ArrayList<BottomMenuBasicBean>();
		moreBean = initMoreBean();
		for (int i = 0; i < length; i++) {
			BottomMenuBasicBean bean = new BottomMenuBasicBean();
			bean.setImageCheckedResource(newsOperateBtnChecked[i]);
			bean.setImageDisableResource(newsOperateBtnDisabled[i]);
			bean.setTextContent(newsOperateBtnTextContent[i]);
			bean.setDisableHint(disableHint[i]);
			bean.setCanEnableState(newsOperateBtnCanEnableState[i]);
			if (bean.getCanEnableState() > moreBean.getCanEnableState()) {
				moreBean.setCanEnableState(bean.getCanEnableState());
			}
			if (newsIsInMore[i]) {
				hasNewsIsInMore = true;
				List<BottomMenuBasicBean> items = moreBean.getItems();
				items.add(bean);
			} else {
				bottomMenuList.add(bean);
			}
		}
		if (hasNewsIsInMore) {
			bottomMenuList.add(moreBean);
		}
	}
	
	/**
	 * 初始化底部菜单的“更多”bean
	 * @return
	 */
	private BottomMenuBasicBean initMoreBean() {
		BottomMenuBasicBean moreBean = new BottomMenuBasicBean();
		moreBean.setMore(true);
		moreBean.setImageCheckedResource(R.drawable.operate_btn_more_checked);
		moreBean.setImageDisableResource(R.drawable.operate_btn_more_disable);
		moreBean.setTextContent("更多");
		moreBean.setDisableHint("请选择新闻后再进行更多操作");
		moreBean.setCanEnableState(ParamConst.CAN_ENABLE_STATE_DEFAULT);
		return moreBean;
	}

	/**
	 * 修改canEnableState状态
	 */
	protected void changeCanEnableState() {
		if (selectedNewsCount[curPosition] == 0) {
			canEnableState = ParamConst.CAN_ENABLE_STATE_DEFAULT;
		} else if (selectedNewsCount[curPosition] == 1) {
			canEnableState = ParamConst.CAN_ENABLE_STATE_SIMPLE;
		} else if (selectedNewsCount[curPosition] >= 2) {
			canEnableState = ParamConst.CAN_ENABLE_STATE_MORE;
		}
	}

	/**
	 * 先将底部菜单全部清除，再根据对应新闻类别进行数据切换
	 */
	protected void initBottomMenuBtnState() {
		// 先将所有的按钮组全部移除
		newsOperateBtnLayout.removeAllViews();
		initHorizontalBottomMenuBtn(bottomMenuList);
	}
	
	/**
	 * 根据canEnableState改变底部菜单的状态
	 */
	private void changeBottomMenuBtnState() {
		changeHorizontalBottomMenuBtn(bottomMenuList);
	}
	
	private void initBottomMenuBtnPopupWindow() {
		if (bottomMenuBtnPopupWindowUtil == null) {
			bottomMenuBtnPopupWindowUtil = new PopupWindowUtil(this, moreOperateMenuBtn) {
				@Override
				public void initItems(LinearLayout layout) {
					bottomMenuBtnPopupWindowUtil.setLayout(layout);
					BottomBtnMorePopupWindowOnClickListener listener = new BottomBtnMorePopupWindowOnClickListener(NewsCommonActivity.this, layout);
					initMoreOperateMenuBtns(layout, listener);
				}
			};
		}
		bottomMenuBtnPopupWindowUtil.setPopupWindowShowType(ParamConst.POPUP_WINDOW_SHOW_TYPE_AT_LOCATION);
		bottomMenuBtnPopupWindowUtil.setGravity(Gravity.BOTTOM|Gravity.END);
		bottomMenuBtnPopupWindowUtil.setY(newsOperateBtnLayout.getMeasuredHeight());
//		bottomMenuBtnPopupWindowUtil.setXoffInPixels(-bottomMenuBtnPopupWindowUtil.getWidth());
		bottomMenuBtnPopupWindow = bottomMenuBtnPopupWindowUtil.initPopupWindow();
	}
	
	public void initMoreOperateMenuBtns(LinearLayout layout, CommonOnClickListener listener) {
		List<BottomMenuBasicBean> items = moreBean.getItems();
		for (BottomMenuBasicBean item : items) {
			if (item.getCanEnableState() >= canEnableState && canEnableState != ParamConst.CAN_ENABLE_STATE_DEFAULT) {
				LinearLayout itemLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.popup_bottom_btn, null);
				TextView bottomBtnTV = (TextView) itemLayout.findViewById(R.id.bottomBtnTV);
				bottomBtnTV.setCompoundDrawablesWithIntrinsicBounds(DrawableUtil.getDrawable(this, item.getImageCheckedResource()), null, null, null);
				bottomBtnTV.setCompoundDrawablePadding(20);
				bottomBtnTV.setText(item.getTextContent());
				layout.addView(itemLayout);
			}
		}
	}
	
	/**
	 * 初始化底部菜单，并将所有菜单平均对称排列
	 * @param bottomMenuList
	 */
	private void initHorizontalBottomMenuBtn(List<BottomMenuBasicBean> bottomMenuList) {
		int i = 0;
		LinearLayout.LayoutParams percentLayoutParams = LayoutParamsUtil.initLineWidthPercentWeight(1f);
		LinearLayout.LayoutParams wrapLayoutParams = LayoutParamsUtil.initLineWrapLayout();
		for (BottomMenuBasicBean bean : bottomMenuList) {
			LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setGravity(Gravity.CENTER);
			// 将当前底部菜单是否为“更多”存入tag中
			layout.setTag(bean.isMore());
			newsOperateBtnLayout.addView(layout, percentLayoutParams);
			
			ImageView iv = new ImageView(this);
			TextView tv = new TextView(this);
			layout.addView(iv, wrapLayoutParams);
			layout.addView(tv, wrapLayoutParams);
			changeBottomMenuBtn(iv, tv, bean, i);
			tv.setText(bean.getTextContent());
			if (bean.isMore()) {
				moreOperateMenuBtn = layout;
			}
			i++;
		}
	}
	
	private void changeHorizontalBottomMenuBtn(List<BottomMenuBasicBean> bottomMenuList) {
		int childCount = newsOperateBtnLayout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			LinearLayout layout = (LinearLayout) newsOperateBtnLayout.getChildAt(i);
			ImageView iv = (ImageView) layout.getChildAt(0);
			TextView tv = (TextView) layout.getChildAt(1);
			changeBottomMenuBtn(iv, tv, bottomMenuList.get(i), i);
		}
	}
	
	/**
	 * 改变底部菜单的状态
	 * @param iv 底部菜单的图片
	 * @param tv 底部菜单的文字
	 * @param bean 底部菜单对应bean
	 * @param i 当前处理的底部菜单
	 */
	private void changeBottomMenuBtn(ImageView iv, TextView tv, BottomMenuBasicBean bean, int i) {
		// 将是否可以点击的按钮的状态进行初始化/变化
		if (bean.getCanEnableState() >= canEnableState && canEnableState != ParamConst.CAN_ENABLE_STATE_DEFAULT) {
			iv.setImageResource(bean.getImageCheckedResource());
			iv.setTag(true);
			int color = ColorUtil.getColor(this,
					bottomMenuOperateBean.getNewsOperateBtnColor()[curPosition][i]);
			tv.setTextColor(color);
		} else {
			iv.setImageResource(bean.getImageDisableResource());
			iv.setTag(false);
			tv.setTextColor(newsOperateBtnBasicColor);
		}
	}

	public void changeAddNewsBtnVisible() {
		if (isShowAddNewsBtn[curPosition]) {
			addNewsBtn.setVisibility(View.VISIBLE);
		} else {
			addNewsBtn.setVisibility(View.GONE);
		}
	}

	public void changeToCurPosition(ViewColorBasicBean colorBasicBean, EnableSimpleChangeButton btn,
			final PullToRefreshListView listView, int position) {
		for (EnableSimpleChangeButton newsStateBtn : newsStateBtns) {
			ViewUtil.initBtnGroupStyleByFocusedState(colorBasicBean, newsStateBtn, false, checkedColor, whiteColor);
			newsStateBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		}
		ViewUtil.initBtnGroupStyleByFocusedState(colorBasicBean, btn, true, checkedColor, whiteColor);
		NewsListViewAdapter newsListViewAdapter = listViewAdapter.get(position);
		if (newsListViewAdapter.getItems().size() == 0) {
			hintRelative.setVisibility(View.VISIBLE);
			listView.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					listView.setRefreshing();
				}
			}, 100);
		} else {
			hintRelative.setVisibility(View.GONE);
		}
		
	}

	public ViewPager getNewsListViewPager() {
		return newsListViewPager;
	}

	public TextView getTitleText() {
		return titleText;
	}

	public void setTitleText(TextView titleText) {
		this.titleText = titleText;
	}

	public void setNewsListViewPager(ViewPager newsListViewPager) {
		this.newsListViewPager = newsListViewPager;
	}

	public String getNewsSubTitleText() {
		return newsSubTitleText;
	}

	public void setNewsSubTitleText(String newsSubTitleText) {
		this.newsSubTitleText = newsSubTitleText + " ";
	}

	public TextView getNewsSubTitleTV() {
		return newsSubTitleTV;
	}

	public void setNewsSubTitleTV(TextView newsSubTitleTV) {
		this.newsSubTitleTV = newsSubTitleTV;
	}

	public boolean isSubTitleInitFinish() {
		return isSubTitleInitFinish;
	}

	public void setSubTitleInitFinish(boolean isSubTitleInitFinish) {
		this.isSubTitleInitFinish = isSubTitleInitFinish;
	}

	public List<NewsListFragment> getViews() {
		return views;
	}

	public void setViews(List<NewsListFragment> views) {
		this.views = views;
	}

	public List<PullToRefreshListView> getListViews() {
		return listViews;
	}

	public void setListViews(List<PullToRefreshListView> listViews) {
		this.listViews = listViews;
	}

	public List<NewsListViewAdapter> getListViewAdapter() {
		return listViewAdapter;
	}

	public void setListViewAdapter(List<NewsListViewAdapter> listViewAdapter) {
		this.listViewAdapter = listViewAdapter;
	}

	public int getCurRefreshState() {
		return curRefreshState;
	}

	public void setCurRefreshState(int curRefreshState) {
		this.curRefreshState = curRefreshState;
	}

	public LinearLayout getNewsTypeBtnLineLayout() {
		return newsTypeBtnLineLayout;
	}

	public void setNewsTypeBtnLineLayout(LinearLayout newsTypeBtnLineLayout) {
		this.newsTypeBtnLineLayout = newsTypeBtnLineLayout;
	}

	public int getCurPosition() {
		return curPosition;
	}

	public void setCurPosition(int curPosition) {
		this.curPosition = curPosition;
	}

	public RequestNewsListUrlBean getNewsListBean() {
		return newsListBean;
	}

	public void setNewsListBean(RequestNewsListUrlBean newsListBean) {
		this.newsListBean = newsListBean;
	}

	public int[] getNewsStateBtnState() {
		return newsStateBtnState;
	}

	public void setNewsStateBtnState(int[] newsStateBtnState) {
		this.newsStateBtnState = newsStateBtnState;
	}

	public List<String> getAllNewsTitleName() {
		return allNewsTitleName;
	}

	public void setAllNewsTitleName(List<String> allNewsTitleName) {
		this.allNewsTitleName = allNewsTitleName;
	}

	public String getCurNewsTitleName() {
		return curNewsTitleName;
	}

	public void setCurNewsTitleName(String curNewsTitleName) {
		this.curNewsTitleName = curNewsTitleName;
	}

	public String getCurUrl() {
		return curUrl;
	}

	public void setCurUrl(String curUrl) {
		this.curUrl = curUrl;
	}

	public Map<String, String> getAllSubNewsTitleSortMap() {
		return allSubNewsTitleSortMap;
	}

	public void setAllSubNewsTitleSortMap(Map<String, String> allSubNewsTitleSortMap) {
		this.allSubNewsTitleSortMap = allSubNewsTitleSortMap;
	}

	public String getCurSubNewsTitleSortColumn() {
		return curSubNewsTitleSortColumn;
	}

	public void setCurSubNewsTitleSortColumn(String curSubNewsTitleSortColumn) {
		this.curSubNewsTitleSortColumn = curSubNewsTitleSortColumn;
		newsListBean.getPage().setOrderBy(curSubNewsTitleSortColumn);
	}

	public ImageView getNewsSubTitleSort() {
		return newsSubTitleSort;
	}

	public void setNewsSubTitleSort(ImageView newsSubTitleSort) {
		this.newsSubTitleSort = newsSubTitleSort;
	}

	public int getNewsTitleAllowLength() {
		return newsTitleAllowLength;
	}

	public void setNewsTitleAllowLength(int newsTitleAllowLength) {
		this.newsTitleAllowLength = newsTitleAllowLength;
	}

	public RelativeLayout getHintRelative() {
		return hintRelative;
	}

	public void setHintRelative(RelativeLayout hintRelative) {
		this.hintRelative = hintRelative;
	}

	public List<EnableSimpleChangeButton> getNewsStateBtns() {
		return newsStateBtns;
	}

	public void setNewsStateBtns(List<EnableSimpleChangeButton> newsStateBtns) {
		this.newsStateBtns = newsStateBtns;
	}

	public int getRefreshType() {
		return refreshType;
	}

	public void setRefreshType(int refreshType) {
		this.refreshType = refreshType;
	}

	public boolean isNewsSearched() {
		return isNewsSearched;
	}

	public void setNewsSearched(boolean isNewsSearched) {
		this.isNewsSearched = isNewsSearched;
	}

	public RequestNewsSearchUrlBean getRequestNewsSearchUrlBean() {
		return requestNewsSearchUrlBean;
	}

	public void setRequestNewsSearchUrlBean(RequestNewsSearchUrlBean requestNewsSearchUrlBean) {
		this.requestNewsSearchUrlBean = requestNewsSearchUrlBean;
	}

	public CommonPopupWindow getBottomMenuBtnPopupWindow() {
		return bottomMenuBtnPopupWindow;
	}

	public void setBottomMenuBtnPopupWindow(CommonPopupWindow bottomMenuBtnPopupWindow) {
		this.bottomMenuBtnPopupWindow = bottomMenuBtnPopupWindow;
	}

}
