package com.enorth.cms.view.news;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.enorth.cms.adapter.news.NewsListFragmentPagerAdapter;
import com.enorth.cms.adapter.news.NewsListViewAdapter;
import com.enorth.cms.bean.BottomMenuOperateDataBasicBean;
import com.enorth.cms.bean.ButtonColorBasicBean;
import com.enorth.cms.bean.Page;
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
import com.enorth.cms.listener.color.ChangeBGColorOnTouchListener;
import com.enorth.cms.listener.color.UnChangeBGColorOnTouchListener;
import com.enorth.cms.listener.newslist.bottommenu.BottomMenuOnTouchListener;
import com.enorth.cms.listener.newslist.viewpager.NewsCommonViewPagerOnPageChangeListener;
import com.enorth.cms.listener.popup.newssubtitle.NewsSubTitlePopupWindowOnTouchListener;
import com.enorth.cms.listener.popup.newstitle.NewsTitlePopupWindowOnTouchListener;
import com.enorth.cms.presenter.newslist.INewsListFragPresenter;
import com.enorth.cms.presenter.newslist.NewsListFragPresenter;
import com.enorth.cms.utils.BeanParamsUtil;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.DimenUtil;
import com.enorth.cms.utils.DrawableUtil;
import com.enorth.cms.utils.PopupWindowUtil;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.StringUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.BaseFragmentActivity;
import com.enorth.cms.view.R;
import com.enorth.cms.widget.popupwindow.CommonPopupWindow;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
	 * 标题弹出框工具
	 */
	private PopupWindowUtil newsTitlePopupWindowUtil;
	/**
	 * 副标题中的排序图标弹出框工具
	 */
	private PopupWindowUtil subNewsTitleSortPopupWindowUtil;
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
	/**
	 * 默认显示到ListView中的Layout的高度
	 */
	private int defaultListViewHeight = 0;
	// public List<View> views = new ArrayList<View>();
	/**
	 * 选中的新闻总和
	 */
	protected int[] selectedNewsCount = { 0, 0, 0 };
	/**
	 * 标题的所有类别（普通新闻）
	 */
	private int[] allNewsTitleName;
	/**
	 * 当前的新闻标题
	 */
	private int curNewsTitleName;
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
	private int[] newsTypeBtnState = { ParamConst.STATE_EDIT, ParamConst.STATE_TO_PUB, ParamConst.STATE_PUB };
	/**
	 * 切换新闻列表的标头按钮对应的id
	 */
	private String[] newsTypeBtnId = { "waitEdit", "tobeIssued", "hasIssued" };

	/**
	 * 编辑、待签发、已签发的按钮组的集合
	 */
	private List<EnableSimpleChangeButton> newsTypeBtns;
	/**
	 * 初始化新闻操作类型的按钮布局（待编辑、待签发、已签发）
	 */
	private RelativeLayout newsTypeBtnRelaLayout;
	/**
	 * 右下角的"+"在对应的新闻列表中是否显示
	 */
	public boolean[] isShowAddNewsBtn = { true, false, false };
	/**
	 * 底部菜单的基础bean（里面包括按钮可选/不可选图标、文组描述、提示信息、颜色）
	 */
	protected BottomMenuOperateDataBasicBean bottomMenuOperateBean;
	/**
	 * 底部菜单的默认颜色
	 */
	protected int newsOperateBtnBasicColor;
	/**
	 * 白色
	 */
	protected int whiteColor;
	/**
	 * 蓝色
	 */
	protected int blueColor;
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
	public abstract int[] setAllNewsTitleText();
	
	/**
	 * 初始化副标题的一系列操作
	 */
	public abstract void initNewsSubTitle();
	
	public abstract int initCurNewsType();

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
				Set<String> keySet = extras.keySet();
				for (String key : keySet) {
					Log.e(key, extras.getString(key));
					Toast.makeText(NewsCommonActivity.this, key + ":" + extras.getString(key), Toast.LENGTH_SHORT)
							.show();
				}
			}
			break;
		case ParamConst.NEWS_LIST_FRAG_ACTIVITY_TO_CHANNEL_SEARCH_ACTIVITY_REQUEST_CODE:
			if (resultCode == ParamConst.CHANNEL_SEARCH_ACTIVITY_BACK_TO_NEWS_LIST_FRAG_ACTIVITY_RESULT_CODE) {
				Bundle extras = data.getExtras();
				if (extras.containsKey(ParamConst.CUR_CHANNEL_ID)
						&& extras.containsKey(ParamConst.CUR_CHANNEL_ID_PARENT_ID)
						&& extras.containsKey(ParamConst.CUR_CHANNEL_NAME)) {
					channelId = extras.getLong(ParamConst.CUR_CHANNEL_ID);
					Long parentId = extras.getLong(ParamConst.CUR_CHANNEL_ID_PARENT_ID);
					newsSubTitleText = extras.getString(ParamConst.CUR_CHANNEL_NAME);
					newsSubTitleTV.setText(newsSubTitleText);
					SharedPreUtil.put(NewsCommonActivity.this, ParamConst.CUR_CHANNEL_ID, channelId);
					SharedPreUtil.put(NewsCommonActivity.this, ParamConst.CUR_CHANNEL_ID_PARENT_ID, parentId);
					SharedPreUtil.put(NewsCommonActivity.this, ParamConst.CUR_CHANNEL_NAME, newsSubTitleText);
				}
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
		blueColor = ColorUtil.getCommonBlueColor(NewsCommonActivity.this);
		// 获取屏幕的分辨率

		newsTitleAllowLength = ScreenTools.getPhoneWidth(NewsCommonActivity.this) / ParamConst.FONT_WIDTH;
		channelBean = (ChannelBean) BeanParamsUtil.getObject(ChannelBean.class, this);
		loginBean = (LoginBean) BeanParamsUtil.getObject(LoginBean.class, this);

		initNewsListBean();
		// 将所有的新闻标题进行初始化
		allNewsTitleName = setAllNewsTitleText();
//		newsTitleBeans = setAllNewsTitleText();
		
		// 将第一个标题存入当前标题中
//		curNewsTitleName = newsTitleBeans.get(0).getNewsTitleName();
		curNewsTitleName = allNewsTitleName[0];
		curUrl = UrlConst.NEWS_LIST_POST_URL;
		
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
		float titleHeight = DimenUtil.getDimension(this, R.dimen.news_title_height);
		float subTitleHeight = DimenUtil.getDimension(this, R.dimen.news_sub_title_height);
		float operateBtnHeight = DimenUtil.getDimension(this, R.dimen.news_operate_btn_layout_height);
		defaultListViewHeight = (int) ((ScreenTools.getPhoneHeight(NewsCommonActivity.this) - titleHeight - subTitleHeight
				- operateBtnHeight) / 2 + titleHeight + subTitleHeight);
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
		titleText.setCompoundDrawablesWithIntrinsicBounds(null, null, DrawableUtil.getDrawable(this, R.drawable.news_sub_title_channel_iv), null);
		ChangeBGColorOnTouchListener listener = new ChangeBGColorOnTouchListener(this) {
			
			@Override
			public void onImgChangeEnd(View v) {
				
			}
			
			@Override
			public void onImgChangeDo(View v) {
				initNewsTitlePopupWindow();
			}
		};
		listener.changeColor(ColorUtil.getBgGrayPress(this), ColorUtil.getCommonBlueColor(this));
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
			/*popupWindowUtil.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
			int newsTitleHeightPx = ScreenTools.dimenDip2px(R.dimen.news_title_height, this);
			popupWindowUtil.setY(newsTitleHeightPx);*/
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
		newsListBean.setInitEditor(loginBean.getUserId());
		newsListBean.setState(newsTypeBtnState[0]);
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
		newsTypeBtns = ViewUtil.initBtnGroupLayout(NewsCommonActivity.this, newsTypeBtnLineLayout, newsTypeBtnText,
				newsTypeBtnId, 0.9f);
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
	protected void initAddNewsBtn() {
		OnClickListener listener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(NewsCommonActivity.this, "点击了“添加新闻”按钮", Toast.LENGTH_SHORT).show();
			}
		};
		addNewsBtn.setOnClickListener(listener);
	}

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
			adapter.setItems(new ArrayList<NewsListBean>());
			adapter.notifyDataSetChanged();
			if (StringUtil.isNotEmpty(errorHint)) {
				hintRelative.setVisibility(View.VISIBLE);
				ViewUtil.showAlertDialog(this, errorHint);
			}
		}
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
		try {
			JSONArray jsonArray = new JSONArray(result);
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				JSONObject jo = jsonArray.getJSONObject(i);
//				View view = packageNewsData(jo);
				NewsListBean bean = packageNewsData(jo);
				if (bean == null) {
					throw new JSONException("数据错误");
				}
				resultView.add(bean);
			}
		} catch (JSONException e) {
			Log.e("NewsCommonActivity.initData()Json转换成view时发生错误", e.toString());
			e.printStackTrace();
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
	}
	
	/**
	 * 给新闻列表的每一条新闻添加点击事件
	 * 
	 * @param bean
	 */
	/*public CommonOnTouchListener initListViewItemTouchEvent(final NewsListListViewItemBasicBean bean) {
		CommonOnTouchListener listViewItemOnTouchListener = new ListViewItemOnTouchListener(this) {
			@Override
			public void onImgChangeDo(View v) {
				Toast.makeText(NewsCommonActivity.this, "点击的新闻ID为【" + bean.getId() + "】", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onTouchBegin() {

			}
		};
		listViewItemOnTouchListener.changeColor(ColorUtil.getBgGrayPress(this), ColorUtil.getBgGrayDefault(this));
		bean.getView().findViewById(R.id.newsTextLayout).setOnTouchListener(listViewItemOnTouchListener);
	}*/

	/**
	 * 根据当前选中的标头按钮的位置改变需要改变样式的按钮，并清除需要清除的ListView中的数据（只要在当前ListView前后超过一个间隔，则清空）
	 * 
	 * @param position
	 * @throws Exception
	 */
	public void changeNewsTypeBtnStyleByFocusedState(int position) throws Exception {
		ViewUtil.changeBtnGroupStyleByFocusedState(NewsCommonActivity.this, newsTypeBtnLineLayout, position,
				ColorUtil.getCommonBlueColor(NewsCommonActivity.this),
				ColorUtil.getWhiteColor(NewsCommonActivity.this));
	}

	/**
	 * 初始化底部菜单的每一个按钮
	 */
	public void initNewsOperateBtn() {
		int i = 0;
		// 获取底部的每一个按钮的基本参数
		bottomMenuList = initNewsOperateBtnData();
		changeCanEnableState();
		for (final BottomMenuBasicBean bean : bottomMenuList) {
			// final View view = inflater.inflate(R.layout.operate_btn_basic,
			// null);
			final LinearLayout layout = (LinearLayout) newsOperateBtnLayout.getChildAt(i++);
			if (bean.getImageCheckedResource() == 0) {
				layout.setVisibility(View.GONE);
			} else {
				layout.setVisibility(View.VISIBLE);
			}
			bean.setView(layout);
			final ImageView iv = (ImageView) layout.getChildAt(0);
			bean.setImageView(iv);
			if (bean.isEnable()) {
				iv.setImageResource(bean.getImageCheckedResource());
				iv.setSelected(true);
				iv.setEnabled(true);
			} else {
				iv.setImageResource(bean.getImageDisableResource());
				iv.setSelected(false);
				iv.setEnabled(false);
			}
			final String hint = bean.getDisableHint();
			BottomMenuOnTouchListener listener = new BottomMenuOnTouchListener(bean, this) {

				@Override
				public boolean onImgChangeBegin(View v) {
					// 如果当前点击的按钮处于disable状态，则不进行任何操作
					if (iv.isEnabled()) {
						Toast.makeText(NewsCommonActivity.this, bean.getTextContent(), Toast.LENGTH_SHORT).show();
						return true;
					} else {
						Toast.makeText(NewsCommonActivity.this, hint, Toast.LENGTH_SHORT).show();
						return false;
					}
				}
			};
			layout.setOnTouchListener(listener);
			TextView tv = (TextView) layout.getChildAt(1);
			tv.setText(bean.getTextContent());

		}
	}

	/**
	 * 初始化底部菜单的信息
	 * 
	 * @return
	 */
	protected List<BottomMenuBasicBean> initNewsOperateBtnData() {
		List<BottomMenuBasicBean> resultList = new ArrayList<BottomMenuBasicBean>();
		int[] newsOperateBtnChecked = bottomMenuOperateBean.getNewsOperateBtnChecked(curPosition);
		int[] newsOperateBtnDisabled = bottomMenuOperateBean.getNewsOperateBtnDisabled(curPosition);
		String[] newsOperateBtnTextContent = bottomMenuOperateBean.getNewsOperateBtnTextContent(curPosition);
		String[] disableHint = bottomMenuOperateBean.getDisableHint(curPosition);
		int[] newsOperateBtnCanEnableState = bottomMenuOperateBean.getNewsOperateBtnCanEnableState(curPosition);
		int length = newsOperateBtnChecked.length;
		for (int i = 0; i < length; i++) {
			BottomMenuBasicBean bean = new BottomMenuBasicBean();
			bean.setImageCheckedResource(newsOperateBtnChecked[i]);
			// bean.setImageUncheckResource(newsOperateBtnUnchecked[i]);
			bean.setImageDisableResource(newsOperateBtnDisabled[i]);
			bean.setTextContent(newsOperateBtnTextContent[i]);
			bean.setDisableHint(disableHint[i]);
			bean.setCanEnableState(newsOperateBtnCanEnableState[i]);
			resultList.add(bean);
		}
		return resultList;
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
		changeBottomMenuBtnState();
	}

	/**
	 * 根据canEnableState改变底部菜单的状态
	 */
	protected void changeBottomMenuBtnState() {
		int i = 0;
		for (BottomMenuBasicBean bean : bottomMenuList) {
			// ImageView iv = bean.getImageView();
			final LinearLayout layout = (LinearLayout) newsOperateBtnLayout.getChildAt(i);
			final ImageView iv = (ImageView) layout.getChildAt(0);
			final TextView tv = (TextView) layout.getChildAt(1);
			if (bean.getCanEnableState() >= canEnableState && canEnableState != ParamConst.CAN_ENABLE_STATE_DEFAULT) {
				iv.setImageResource(bean.getImageCheckedResource());
				iv.setSelected(true);
				iv.setEnabled(true);
				bean.setEnable(true);
				int color = ContextCompat.getColor(NewsCommonActivity.this,
						bottomMenuOperateBean.getNewsOperateBtnColor()[i]);
				tv.setTextColor(color);
			} else {
				iv.setImageResource(bean.getImageDisableResource());
				iv.setSelected(false);
				iv.setEnabled(false);
				bean.setEnable(false);
				tv.setTextColor(newsOperateBtnBasicColor);
			}
			i++;
		}
	}

	public void changeAddNewsBtnVisible() {
		if (isShowAddNewsBtn[curPosition]) {
			addNewsBtn.setVisibility(View.VISIBLE);
		} else {
			addNewsBtn.setVisibility(View.GONE);
		}
	}

	public void changeToCurPosition(ButtonColorBasicBean colorBasicBean, EnableSimpleChangeButton btn,
			final PullToRefreshListView listView, int position) {
		ViewUtil.initBtnGroupStyleByFocusedState(colorBasicBean, btn, true, blueColor, whiteColor);
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
		this.newsSubTitleText = newsSubTitleText;
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

	public int[] getNewsTypeBtnState() {
		return newsTypeBtnState;
	}

	public void setNewsTypeBtnState(int[] newsTypeBtnState) {
		this.newsTypeBtnState = newsTypeBtnState;
	}

	public int[] getAllNewsTitleName() {
		return allNewsTitleName;
	}

	public void setAllNewsTitleName(int[] allNewsTitleName) {
		this.allNewsTitleName = allNewsTitleName;
	}

	public int getCurNewsTitleName() {
		return curNewsTitleName;
	}

	public void setCurNewsTitleName(int curNewsTitleName) {
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

}
