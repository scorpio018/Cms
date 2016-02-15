package com.enorth.cms.view.news;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.enorth.cms.adapter.news.NewsListFragmentPagerAdapter;
import com.enorth.cms.adapter.news.NewsListViewAdapter;
import com.enorth.cms.bean.BottomMenuOperateDataBasicBean;
import com.enorth.cms.bean.ButtonColorBasicBean;
import com.enorth.cms.bean.news_list.BottomMenuBasicBean;
import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.bean.news_list.NewsListListViewItemBasicBean;
import com.enorth.cms.common.EnableSimpleChangeButton;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.fragment.NewsListFragment;
import com.enorth.cms.handler.newslist.NewsListViewHandler;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.listener.imageview.ImageViewOnTouchListener;
import com.enorth.cms.listener.newslist.ListViewItemOnTouchListener;
import com.enorth.cms.listener.newslist.bottommenu.BottomMenuOnTouchListener;
import com.enorth.cms.listener.newslist.newstypebtn.NewsTypeBtnOnClickListener;
import com.enorth.cms.presenter.newslist.INewsListFragPresenter;
import com.enorth.cms.presenter.newslist.NewsListFragPresenter;
import com.enorth.cms.utils.LayoutParamsUtil;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.widget.listview.newslist.NewsListListView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public abstract class NewsCommonActivity extends FragmentActivity implements INewsCommonView, OnPageChangeListener {
	/**
	 * 底部的操作按钮布局
	 */
	protected LinearLayout newsOperateBtnLayout;
	/**
	 * 新闻操作类型的按钮布局（待编辑、待签发、已签发），外层需要套一个RelativeLayout以保证按钮能水平居中
	 */
	protected LinearLayout newsTypeBtnLineLayout;
	/**
	 * 副标题左侧的频道名称
	 */
	public TextView newsSubTitleTV;
	/**
	 * 新闻列表的ViewPager，里面根据newsTypeBtnText的length放相应的ListView
	 */
	public ViewPager newsListViewPager;
	/**
	 * 右下角的“+”图案
	 */
	protected ImageView addNewsBtn;
	/**
	 * 将ViewPager中的ListView存入此集合中
	 */
	public List<NewsListFragment> views = new ArrayList<NewsListFragment>();
//	public List<View> views = new ArrayList<View>();
	/**
	 * 屏幕认定滑动的最大位移
	 */
	protected int touchSlop;
	/**
	 * 选中的新闻总和
	 */
	protected int[] selectedNewsCount = { 0, 0, 0 };
	/**
	 * 切换新闻列表的标头按钮
	 */
	public String[] newsTypeBtnText = { "待编辑", "待签发", "已签发" };
	/**
	 * 右下角的"+"在对应的新闻列表中是否显示
	 */
	public boolean[] isShowAddNewsBtn = {true, false, false};
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
	 * 手机的高度
	 */
	protected int phoneHeight;
	/**
	 * 手机的宽度
	 */
	protected int phoneWidth;
	/**
	 * 新闻列表中的标题最多能显示多少字
	 */
	public int newsTitleAllowLength;
	/**
	 * ViewPager当前页
	 */
	public int curPosition = 0;
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
	public boolean isSubTitleInitFinish = false;
	/**
	 * 当前activity（用于在匿名内部类中获取当前activity）
	 */
	protected Activity thisActivity;
	/**
	 * 频道ID
	 */
	protected Long channelId;
	/**
	 * 新闻频道名称
	 */
	public String newsSubTitleText;
	/**
	 * 进行日期格式化
	 */
	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

	protected Intent intent = new Intent();
	
	protected INewsListFragPresenter presenter;
	/**
	 * 副标题中的频道加载
	 */
	protected Handler newsSubTitleHandler;
	/**
	 * 初始化底部菜单bean
	 * @return
	 */
	public abstract BottomMenuOperateDataBasicBean initBottomMenuOperateBean();
	/**
	 * 在此方法中加载xml文件
	 */
	public abstract void setNewsListContentView();
	/**
	 * 将当前activity存入thisActivity中
	 * @return
	 */
	public abstract Activity getCurActivity();
	/**
	 * 初始化标题的一系列操作
	 */
	public abstract void initNewsTitle();
	/**
	 * 初始化副标题的一系列操作
	 */
	public abstract void initNewsSubTitle();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNewsListContentView();
		bottomMenuOperateBean = initBottomMenuOperateBean();
		try {
			initBasicData();
			initNewsTitle();
			initNewsSubTitle();
			// 初始化新闻操作类型的按钮布局（待编辑、待签发、已签发）
			initNewsTypeBtnLayout();
			// 加载ViewPager
			initViewPager();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initAddNewsBtn();
		// initNewsListLayout();
		// 加载底部菜单
		initNewsOperateBtnLayout();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (isSubTitleInitFinish) {
			channelId = SharedPreUtil.getLong(this, ParamConst.CUR_CHANNEL_ID);
			Long parentId = SharedPreUtil.getLong(this, ParamConst.CUR_CHANNEL_ID_PARENT_ID);
			newsSubTitleText = SharedPreUtil.getString(thisActivity, ParamConst.CUR_CHANNEL_NAME);
//			Toast.makeText(thisActivity, "channelId【" + channelId + "】、channelName【" + newsSubTitleText + "】", Toast.LENGTH_SHORT).show();
			newsSubTitleTV.setText(newsSubTitleText);
		}
	}
	
	/**
	 * 初始化基本数据
	 * @throws Exception 
	 */
	protected void initBasicData() throws Exception {
		// 初始化认定滑动的最大位移
		touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
		// 将当前activity存入全局变量，用于匿名内部类中实现的方法的使用
		this.thisActivity = getCurActivity();
		presenter = new NewsListFragPresenter(this);
		newsOperateBtnBasicColor = ContextCompat.getColor(thisActivity, R.color.bottom_text_color_basic);
		whiteColor = ContextCompat.getColor(this, R.color.white);
		blueColor = ContextCompat.getColor(this, R.color.common_blue);
		// 获取屏幕的分辨率
		Display display = thisActivity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		phoneWidth = size.x;
		newsTitleAllowLength = phoneWidth / ParamConst.FONT_WIDTH;
		phoneHeight = size.y;
		/*if (channelId == -1L) {
			SharedPreUtil.resetChannelIdData(thisActivity);
			newsSubTitleText = ParamConst.DEFAULT_CHANNEL_NAME;
		} else {
			newsSubTitleText = SharedPreUtil.getString(thisActivity, ParamConst.CUR_CHANNEL_NAME);
		}*/
	}
	
	/**
	 * 将获取的频道传入Handler中进行处理（放到指定位置显示）
	 */
	@Override
	public void initSubTitleResult(String result, Handler handler) throws Exception {
		JSONObject jo = new JSONObject(result);
		Message msg = new Message();
		msg.what = ParamConst.MESSAGE_WHAT_SUCCESS;
		msg.obj = jo;
		handler.sendMessage(msg);
	}
	
	/**
	 * 初始化新闻操作类型的按钮布局（待编辑、待签发、已签发）
	 * 
	 * @throws Exception
	 */
	protected void initNewsTypeBtnLayout() throws Exception {
		RelativeLayout newsTypeBtnRelaLayout = (RelativeLayout) findViewById(R.id.newsTypeBtnRelaLayout);
		newsTypeBtnLineLayout = (LinearLayout) newsTypeBtnRelaLayout.getChildAt(0);
		int length = newsTypeBtnText.length;
		// 此处初始化待编辑、待签发、已签发三个按钮的基本样式
		LinearLayout.LayoutParams params = LayoutParamsUtil.initPercentWeight(0.9f);
		for (int i = 0; i < length; i++) {
			EnableSimpleChangeButton btn = new EnableSimpleChangeButton(this);
			if (i == 0) {
				btn.needRaduisPosition(false, false, false, true);
			} else if (i == length - 1) {
				btn.needRaduisPosition(false, true, false, false);
			} else {
				btn.needRaduisPosition(false, false, false, false);
			}
			btn.setText(newsTypeBtnText[i]);
			ButtonColorBasicBean colorBasicBean = new ButtonColorBasicBean(this);
			boolean needFocused = i == 0 ? true : false;
			initNewsTypeBtnStyleByFocusedState(colorBasicBean, needFocused);
			btn.setColorBasicBean(colorBasicBean);
			final int position = i;
			// 加点击事件，切换到相应的ListView中
			NewsTypeBtnOnClickListener listener = new NewsTypeBtnOnClickListener(this, position);
			btn.setOnClickListener(listener);
			newsTypeBtnLineLayout.addView(btn, params);
		}
	}
	
	/**
	 * 初始化ViewPager
	 * @throws Exception
	 */
	protected void initViewPager() throws Exception {
		// AnimUtil.showRefreshFrame(thisActivity);
		newsListViewPager = (ViewPager) findViewById(R.id.newsListViewPager);
		for (int i = 0; i < 3; i++) {
			NewsListFragment fragment = new NewsListFragment(this, i);
			
			views.add(fragment);
			NewsListFragmentPagerAdapter adapter = new NewsListFragmentPagerAdapter(getSupportFragmentManager(), this);
			newsListViewPager.setAdapter(adapter);
		}
		newsListViewPager.addOnPageChangeListener(this);
	}
	
	/**
	 * 初始化右下角的“+”图案
	 */
	protected void initAddNewsBtn() {
		addNewsBtn = (ImageView) findViewById(R.id.addNewsBtn);
		OnClickListener listener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(thisActivity, "点击了“添加新闻”按钮", Toast.LENGTH_SHORT).show();
			}
		};
		addNewsBtn.setOnClickListener(listener);
	}
	
	/**
	 * 初始化底部的操作按钮布局
	 */
	protected void initNewsOperateBtnLayout() {
		// TODO 如果将activity_news_list中的newsOperateBtnLayout注释去掉，则要把此处代码注释也去掉
		newsOperateBtnLayout = (LinearLayout) findViewById(R.id.newsOperateBtnLayout);
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
	public void initNewsListData(final NewsListListView newsListView, boolean needInitData, final String errorHint)
			throws Exception {
		if (needInitData) {
			Handler newsListViewHandler = new NewsListViewHandler(this, newsListView, errorHint);
			initData(newsListViewHandler);
		} else {
			Resources resources = getResources();
			float titleHeight = resources.getDimension(R.dimen.news_title_height);
			float subTitleHeight = resources.getDimension(R.dimen.news_sub_title_height);
			float operateBtnHeight = resources.getDimension(R.dimen.news_operate_btn_layout_height);
			int height = (int) ((phoneHeight - titleHeight - subTitleHeight - operateBtnHeight) / 2 + titleHeight
					+ subTitleHeight);
			List<View> items = initDefaultData(errorHint, height);
			ListAdapter adapter = new NewsListViewAdapter(items);
			newsListView.setAdapter(adapter);
			// AnimUtil.hideRefreshFrame(thisActivity);
		}
	}
	
	/**
	 * 访问新闻列表的接口，并将从中获取的数据放入对应的控件中显示
	 * 
	 * @param handler
	 * @param newsListView
	 * @throws Exception
	 */
	protected void initData(final Handler handler) throws Exception {
		presenter.requestListViewData(handler);
	}
	
	/**
	 * 将从接口中获取的数据放入对应的控件中显示
	 */
	@Override
	public void initData(String result, Handler handler) throws Exception {
		JSONArray jsonArray = new JSONArray(result);
		List<View> resultView = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(thisActivity);
		int length = jsonArray.length();
		for (int i = 0; i < length; i++) {
			JSONObject jo = jsonArray.getJSONObject(i);
			View view = packageNewsData(jo, inflater);
			resultView.add(view);
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
	
	/**
	 * 将接口中获取的json数据进行拆解，存入新闻列表UI中
	 * @param jo
	 * @param inflater
	 * @return
	 * @throws JSONException
	 */
	private View packageNewsData(JSONObject jo, LayoutInflater inflater) throws JSONException {
		// 将新闻列表中的左侧的选中图片和对应的新闻item进行封装，存入NewsListImageViewBasicBean中
		NewsListImageViewBasicBean ivBean = new NewsListImageViewBasicBean();
		// 使用news_item.xml中的布局，存入ListView中
		View view = inflater.inflate(R.layout.news_item, null);
		ivBean.setView(view);
		final ImageView checkBtn = (ImageView) view.findViewById(R.id.iv_check_btn);
		ivBean.setImageView(checkBtn);
		// 默认左侧不选中
		checkBtn.setImageResource(R.drawable.uncheck_btn);
		checkBtn.setSelected(false);
		addCheckBtnClickEvent(ivBean);

		// 将新闻列表中的每一个item进行封装，并将传入的参数存入到itemBean中
		NewsListListViewItemBasicBean itemBean = new NewsListListViewItemBasicBean();
		itemBean.setView(view);
		itemBean.setId(jo.getString("id"));
		addListViewItemTouchEvent(itemBean);

		TextView newsTitle = (TextView) view.findViewById(R.id.tv_news_title);
		String title = jo.getString("newsTitle");
		if (title.length() > newsTitleAllowLength) {
			title = title.substring(0, newsTitleAllowLength) + "...";
		}
		newsTitle.setText(title);
		TextView newsTime = (TextView) view.findViewById(R.id.tv_news_time);
		newsTime.setText(jo.getString("newsTime"));
		TextView newsAuthorName = (TextView) view.findViewById(R.id.tv_news_author_name);
		newsAuthorName.setText(jo.getString("newsAuthorName"));
		ImageView xinxi = (ImageView) view.findViewById(R.id.iv_news_xinxi);
		boolean hasXinxi = jo.getBoolean("hasXinxi");
		if (hasXinxi) {
			xinxi.setVisibility(View.VISIBLE);
		} else {
			xinxi.setVisibility(View.GONE);
		}
		return view;
	}
	
	/**
	 * 初始化新闻列表中的初始数据
	 * 
	 * @param text
	 * @param height
	 * @return
	 */
	protected List<View> initDefaultData(String text, int height) {
		List<View> resultView = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(this);
		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.news_list_view_default_item, null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, height);
		layout.setLayoutParams(params);
		if (text != null) {
			TextView textView = (TextView) layout.getChildAt(0);
			textView.setText(text);
		}
		resultView.add(layout);
		return resultView;
	}
	
	/**
	 * 给每一个新闻左侧的选中图标添加点击事件
	 * 
	 * @param bean
	 */
	public void addCheckBtnClickEvent(final NewsListImageViewBasicBean bean) {

		bean.setImageCheckedResource(R.drawable.check_btn);
		bean.setImageUncheckResource(R.drawable.uncheck_btn);
		OnTouchListener listViewCheckBtnOnTouchListener = new ImageViewOnTouchListener(bean, touchSlop) {
			@Override
			public boolean onImgChangeBegin() {
				return true;
			}

			@Override
			public void onImgChangeEnd() {
				if (bean.getImageView().isSelected()) {
					selectedNewsCount[curPosition]++;
				} else {
					selectedNewsCount[curPosition]--;
				}
				changeCanEnableState();
			}
		};
		bean.getImageView().setOnTouchListener(listViewCheckBtnOnTouchListener);
	}
	
	/**
	 * 给新闻列表的每一条新闻添加点击事件
	 * 
	 * @param bean
	 */
	public void addListViewItemTouchEvent(final NewsListListViewItemBasicBean bean) {
		CommonOnTouchListener listViewItemOnTouchListener = new ListViewItemOnTouchListener(touchSlop) {
			@Override
			public void onImgChangeDo() {
				Toast.makeText(thisActivity, "点击的新闻ID为【" + bean.getId() + "】", Toast.LENGTH_SHORT).show();
			}
		};
		listViewItemOnTouchListener.changeColor(R.color.bg_gray_press, R.color.bg_gray_default);
		bean.getView().findViewById(R.id.newsTextLayout).setOnTouchListener(listViewItemOnTouchListener);
	}
	
	/**
	 * 通过是否需要选中的标识进行ButtonColorBasicBean的样式变更操作
	 * 
	 * @param colorBasicBean
	 * @param needFocused
	 */
	protected void initNewsTypeBtnStyleByFocusedState(ButtonColorBasicBean colorBasicBean, boolean needFocused) {
		if (needFocused) {
			// 需要选中
			colorBasicBean.setmBgNormalColor(blueColor);
			colorBasicBean.setmTextNormalColor(whiteColor);
		} else {
			colorBasicBean.setmBgNormalColor(whiteColor);
			colorBasicBean.setmTextNormalColor(blueColor);
		}

	}
	
	/**
	 * 根据当前选中的标头按钮的位置改变需要改变样式的按钮，并清除需要清除的ListView中的数据（只要在当前ListView前后超过一个间隔，则清空）
	 * 
	 * @param position
	 * @throws Exception
	 */
	public void changeNewsTypeBtnStyleByFocusedState(int position) throws Exception {
		int childCount = newsTypeBtnLineLayout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			EnableSimpleChangeButton btn = (EnableSimpleChangeButton) newsTypeBtnLineLayout.getChildAt(i);
			ButtonColorBasicBean colorBasicBean = new ButtonColorBasicBean(this);
			if (i == position) {
				initNewsTypeBtnStyleByFocusedState(colorBasicBean, true);
			} else {
				initNewsTypeBtnStyleByFocusedState(colorBasicBean, false);
				if (Math.abs(position - i) > 1) {
					NewsListListView listView = (NewsListListView) newsListViewPager.getChildAt(i);
					listView.removeAllViews();
					selectedNewsCount[i] = 0;
				}
			}
			btn.setColorBasicBean(colorBasicBean);
		}
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
			BottomMenuOnTouchListener listener = new BottomMenuOnTouchListener(bean, touchSlop) {

				@Override
				public boolean onImgChangeBegin() {
					// 如果当前点击的按钮处于disable状态，则不进行任何操作
					if (iv.isEnabled()) {
						Toast.makeText(thisActivity, bean.getTextContent(), Toast.LENGTH_SHORT).show();
						return true;
					} else {
						Toast.makeText(thisActivity, hint, Toast.LENGTH_SHORT).show();
						return false;
					}
				}

				@Override
				public void onImgChangeEnd() {

				}

				@Override
				public void onImgChangeDo() {

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
				int color = ContextCompat.getColor(thisActivity, bottomMenuOperateBean.getNewsOperateBtnColor()[i]);
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
	
	/**
	 * 在滑动状态改变的时候调用 有三种状态（0，1，2）。1表示正在滑动；2表示滑动完毕了；0表示什么都没做。
	 */
	@Override
	public void onPageScrollStateChanged(int state) {

	}

	/**
	 * 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法会一直得到调用。其中三个参数的含义分别为： arg0 :当前页面，及你点击滑动的页面
	 * arg1:当前页面偏移的百分比 arg2:当前页面偏移的像素位置
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	/**
	 * 此方法是页面跳转完后得到调用，position是你当前选中的页面对应的值（从0开始）
	 */
	@Override
	public void onPageSelected(final int position) {
		// AnimUtil.showRefreshFrame(thisActivity);
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				int childCount = newsTypeBtnLineLayout.getChildCount();
				for (int i = 0; i < childCount; i++) {

					ButtonColorBasicBean colorBasicBean = null;
					try {
						colorBasicBean = new ButtonColorBasicBean(thisActivity);
					} catch (Exception e) {
						e.printStackTrace();
					}
					NewsListListView listView = (NewsListListView) (views.get(i).newsListView);
					try {
						if (i == position) {
							changeToCurPosition(colorBasicBean, listView);
						} else {
//							changeToOtherPosition(colorBasicBean, listView, Math.abs(position - i) > 1, i);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					Log.e("改变按钮的样式【开始】", sdf.format(new Date()));
					EnableSimpleChangeButton btn = (EnableSimpleChangeButton) newsTypeBtnLineLayout.getChildAt(i);
					btn.setColorBasicBean(colorBasicBean);
					Log.e("改变按钮的样式【结束】", sdf.format(new Date()));
				}
				curPosition = position;
				changeAddNewsBtnVisible();
				initNewsOperateBtn();
				Log.e("【结束】", sdf.format(new Date()));
				// AnimUtil.hideRefreshFrame(thisActivity);
			}
		}.sendEmptyMessage(0);
	}
	
	public void changeAddNewsBtnVisible() {
		if (isShowAddNewsBtn[curPosition]) {
			addNewsBtn.setVisibility(View.VISIBLE);
		} else {
			addNewsBtn.setVisibility(View.GONE);
		}
	}

	protected void changeToCurPosition(ButtonColorBasicBean colorBasicBean, NewsListListView listView) throws Exception {
		initNewsTypeBtnStyleByFocusedState(colorBasicBean, true);
		NewsListViewAdapter adapter = (NewsListViewAdapter) listView.getAdapter();
		// ListAdapter adapter = listView.getAdapter();
		int count = adapter.getCount();
		if (count == 1) {
			View view = adapter.getView(0, null, null);
			if (view instanceof RelativeLayout) {
				initNewsListData(listView, true, "数据请求错误");
			}
		}

	}

	protected void changeToOtherPosition(ButtonColorBasicBean colorBasicBean, NewsListListView listView,
			boolean needCleanListView, int myPosision) throws Exception {
		initNewsTypeBtnStyleByFocusedState(colorBasicBean, false);
		if (needCleanListView) {
			selectedNewsCount[myPosision] = 0;
			initNewsListData(listView, false, null);
		}
	}
}