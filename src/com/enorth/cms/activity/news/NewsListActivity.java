package com.enorth.cms.activity.news;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.enorth.cms.activity.R;
import com.enorth.cms.adapter.news.ListViewViewPagerAdapter;
import com.enorth.cms.adapter.news.NewsListViewAdapter;
import com.enorth.cms.bean.ButtonColorBasicBean;
import com.enorth.cms.bean.news_list.BottomMenuBasicBean;
import com.enorth.cms.bean.news_list.BottomMenuOperateDataBasicBean;
import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.bean.news_list.NewsListListViewItemBasicBean;
import com.enorth.cms.common.EnableSimpleChangeButton;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.consts.UrlConst;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.listener.imageview.ImageViewOnTouchListener;
import com.enorth.cms.listener.listview.ListViewItemOnTouchListener;
import com.enorth.cms.listener.listview.bottom_menu.BottomMenuOnTouchListener;
import com.enorth.cms.utils.HttpUtil;
import com.enorth.cms.utils.LayoutParamsUtil;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.view.listview.newslist.NewsListListView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
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

@SuppressLint("CommitPrefEdits")
public class NewsListActivity extends Activity implements OnPageChangeListener {
	/**
	 * 底部的操作按钮布局
	 */
	private LinearLayout newsOperateBtnLayout;
	/**
	 * 新闻操作类型的按钮布局（待编辑、待签发、已签发），外层需要套一个RelativeLayout以保证按钮能水平居中
	 */
	private LinearLayout newsTypeBtnLineLayout;
	/**
	 * 副标题左侧的频道名称
	 */
	private TextView newsSubTitleTV;
	/**
	 * 新闻列表的ViewPager，里面根据newsTypeBtnText的length放相应的ListView
	 */
	private ViewPager newsListViewPager;
	/**
	 * 将ViewPager中的ListView存入此集合中
	 */
	private List<View> views;
	/**
	 * 屏幕认定滑动的最大位移
	 */
	private int touchSlop;
	/**
	 * 选中的新闻总和
	 */
	private int[] selectedNewsCount = {0, 0, 0};
	/**
	 * 切换新闻列表的标头按钮
	 */
	private String[] newsTypeBtnText = {"待编辑", "待签发", "已签发"};
	/**
	 * 底部菜单的基础bean（里面包括按钮可选/不可选图标、文组描述、提示信息、颜色）
	 */
	private BottomMenuOperateDataBasicBean bottomMenuOperateBean = new BottomMenuOperateDataBasicBean();
	/**
	 * 底部菜单的默认颜色
	 */
	private int newsOperateBtnBasicColor;
	/**
	 * 白色
	 */
	private int whiteColor;
	/**
	 * 蓝色
	 */
	private int blueColor;
	/**
	 * 手机的高度
	 */
	private int phoneHeight;
	/**
	 * 手机的宽度
	 */
	private int phoneWidth;
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
	private List<BottomMenuBasicBean> bottomMenuList;
	
//	private RadioGroup newsOperateRadioGroup;
	/**
	 * 当前新闻列表联动底部菜单可以操作的状态值（默认都没选中）
	 */
	private int canEnableState = ParamConst.CAN_ENABLE_STATE_DEFAULT;
	/**
	 * 当前activity（用于在匿名内部类中获取当前activity）
	 */
	private NewsListActivity thisActivity;
	/**
	 * 频道ID
	 */
	private Long channelId;
	/**
	 * 新闻频道名称
	 */
	private String newsSubTitleText;
	/**
	 * 进行日期格式化
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	
	private Intent intent = new Intent();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 初始化底部RadioButton菜单，由于要动态添加，所以需要在setContentView之前添加
//		initNewsOperateRadioGroupBtn();
		setContentView(R.layout.activity_news_list);
		// 加载基本参数
		initBasicData();
		try {
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
//		initNewsListLayout();
		// 加载底部菜单
		initNewsOperateBtnLayout();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		channelId = SharedPreUtil.getLong(this, ParamConst.CUR_CHANNEL_ID);
		newsSubTitleText = SharedPreUtil.getString(thisActivity, ParamConst.CUR_CHANNEL_NAME);
//		Toast.makeText(thisActivity, "channelId【" + channelId + "】、channelName【" + newsSubTitleText + "】", Toast.LENGTH_SHORT).show();
		newsSubTitleTV.setText(newsSubTitleText);
	}
	
	/**
	 * 初始化所需的基本参数
	 */
	private void initBasicData() {
		// 初始化认定滑动的最大位移
		touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
		// 将当前activity存入全局变量，用于匿名内部类中实现的方法的使用
		this.thisActivity = this;
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
		// 另一种获得屏幕分辨率的方式，这里得到的像素值是设备独立像素dp
		/*DisplayMetrics metrics = new DisplayMetrics();
		Display dis = this.getWindowManager().getDefaultDisplay();
		dis.getMetrics(metrics);*/
		
		// 如果没有频道ID，则存入默认的频道ID
		channelId = SharedPreUtil.getLong(thisActivity, ParamConst.CUR_CHANNEL_ID);
		if (channelId == -1L) {
			SharedPreUtil.resetChannelIdData(thisActivity);
			newsSubTitleText = ParamConst.DEFAULT_CHANNEL_NAME;
		} else {
			newsSubTitleText = SharedPreUtil.getString(thisActivity, ParamConst.CUR_CHANNEL_NAME);
		}
	}
	
	private void initNewsTitle() {
		
	}
	
	/**
	 * 副标题：包括频道名称、排序按钮
	 */
	private void initNewsSubTitle() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.newsSubTitleLineLayout);
		OnClickListener listener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent.setClass(NewsListActivity.this, ChannelSearchActivity.class);
				startActivity(intent);
			}
		};
		layout.setOnClickListener(listener);
		newsSubTitleTV = (TextView) layout.findViewById(R.id.newsSubTitleText);
		newsSubTitleTV.setText(newsSubTitleText);
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		return super.onMenuOpened(featureId, menu);
	}
	
	/**
	 * 初始化新闻操作类型的按钮布局（待编辑、待签发、已签发）
	 * @throws Exception 
	 */
	private void initNewsTypeBtnLayout() throws Exception {
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
			// 根据当前选中的标头按钮的位置改变需要改变样式的按钮，并清除需要清除的ListView中的数据（只要在当前ListView前后超过一个间隔，则清空）
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					AnimUtil.showRefreshFrame(thisActivity);
					new Handler() {
						@Override
						public void handleMessage(Message msg) {
							super.handleMessage(msg);
							newsListViewPager.setCurrentItem(position, false);
							try {
								changeNewsTypeBtnStyleByFocusedState(position);
							} catch (Exception e) {
								e.printStackTrace();
							}
							curPosition = position;
							initNewsOperateBtn();
//							AnimUtil.hideRefreshFrame(thisActivity);
						}
					}.sendEmptyMessage(0);
					
					// 调用arrowScroll方法用参数1或者17就可以实现向左翻页；参数2或66就可以实现向右翻页。
					// 注：当UI中有EditText这种获得focus的widget时，则必须用17和66，否则要报错。
					/*if (position > curFocusBtn) {
						// TODO 跳跃性切换ViewPager
						// 右
						for (int j = curFocusBtn; j < position; j++) {
							newsListViewPager.arrowScroll(focusRight);
						}
					} else {
						// 左
						for (int j = curFocusBtn; j > position; j++) {
							newsListViewPager.arrowScroll(focusLeft);
						}
					}*/
					
				}
			});
			newsTypeBtnLineLayout.addView(btn, params);
		}
	}
	
	/**
	 * 根据当前选中的标头按钮的位置改变需要改变样式的按钮，并清除需要清除的ListView中的数据（只要在当前ListView前后超过一个间隔，则清空）
	 * @param position
	 * @throws Exception
	 */
	private void changeNewsTypeBtnStyleByFocusedState(int position) throws Exception {
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
	 * 通过是否需要选中的标识进行ButtonColorBasicBean的样式变更操作
	 * @param colorBasicBean
	 * @param needFocused
	 */
	private void initNewsTypeBtnStyleByFocusedState(ButtonColorBasicBean colorBasicBean, boolean needFocused) {
		if (needFocused) {
			// 需要选中
			colorBasicBean.setmBgNormalColor(blueColor);
			colorBasicBean.setmTextNormalColor(whiteColor);
		} else {
			colorBasicBean.setmBgNormalColor(whiteColor);
			colorBasicBean.setmTextNormalColor(blueColor);
		}
		
	}
	
	private void initViewPager() throws Exception {
//		AnimUtil.showRefreshFrame(thisActivity);
		newsListViewPager = (ViewPager) findViewById(R.id.newsListViewPager);
		LayoutParams layoutParams = LayoutParamsUtil.initCustomLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		views = new ArrayList<View>();
		for (int i = 0; i < 3; i++) {
			NewsListListView newsListView = new NewsListListView(this);
			newsListView.setLayoutParams(layoutParams);
			views.add(newsListView);
			initNewsListData(newsListView, false, null);
			if (i == 0) {
				initNewsListData(newsListView, true, "数据请求错误");
			}
		}
		ListViewViewPagerAdapter adapter = new ListViewViewPagerAdapter(views);
		newsListViewPager.setAdapter(adapter);
//		AnimUtil.hideRefreshFrame(thisActivity);
		newsListViewPager.addOnPageChangeListener(this);
	}
	
	private void initAddNewsBtn() {
		ImageView addNewsBtn = (ImageView) findViewById(R.id.addNewsBtn);
		OnClickListener listener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(thisActivity, "点击了“添加新闻”按钮", Toast.LENGTH_SHORT).show();
			}
		};
		addNewsBtn.setOnClickListener(listener);
	}
	
	/**
	 * 初始化新闻列表的内容布局
	 * @param newsListView
	 * @param needInitData 为true时，errorHint可以为null，当请求url时出现异常，则使用errorHint提示，如果为空，则使用默认内容
	 * @param errorHint
	 * @throws Exception
	 */
	private void initNewsListData(final NewsListListView newsListView, boolean needInitData, final String errorHint) throws Exception {
		if (needInitData) {
			Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					try {
						switch (msg.what) {
						case ParamConst.MESSAGE_WHAT_SUCCESS:
							final List<View> items = (List<View>) msg.obj;
							ListAdapter adapter = new NewsListViewAdapter(items);
							newsListView.setAdapter(adapter);
//							AnimUtil.hideRefreshFrame(thisActivity);
//							ViewUtil.setListViewHeightBasedOnChildren(newsListView);
						break;
						case ParamConst.MESSAGE_WHAT_NO_DATA:
							initNewsListData(newsListView, false, errorHint);
						break;
						case ParamConst.MESSAGE_WHAT_ERROR:
							String errorMsg = (String) msg.obj;
							initNewsListData(newsListView, false, errorMsg);
						break;
						default:
							initNewsListData(newsListView, false, "未知错误");
						break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			initData(handler/*, newsListView*/);
		} else {
			Resources resources = getResources();
			float titleHeight = resources.getDimension(R.dimen.news_title_height);
			float subTitleHeight = resources.getDimension(R.dimen.news_sub_title_height);
			float operateBtnHeight = resources.getDimension(R.dimen.news_operate_btn_layout_height);
			int height = (int) ((phoneHeight - titleHeight - subTitleHeight - operateBtnHeight) / 2 + titleHeight + subTitleHeight);
			List<View> items = initDefaultData(errorHint, height);
			ListAdapter adapter = new NewsListViewAdapter(items);
			newsListView.setAdapter(adapter);
//			AnimUtil.hideRefreshFrame(thisActivity);
		}
	}
	
	/**
	 * 初始化底部的操作按钮布局
	 */
	private void initNewsOperateBtnLayout() {
		// TODO 如果将activity_news_list中的newsOperateBtnLayout注释去掉，则要把此处代码注释也去掉
		newsOperateBtnLayout = (LinearLayout) findViewById(R.id.newsOperateBtnLayout);
		initNewsOperateBtn();
	}
	
	/**
	 * 将从接口中获取的数据放入对应的控件中显示
	 * @param handler
	 * @param newsListView
	 * @throws Exception
	 */
	private void initData(final Handler handler/*, final NewsListListView newsListView*/) throws Exception {
		String url = UrlConst.NEWS_LIST_POST_URL;
		final int start = 1;
		final int end = 25;
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("start", String.valueOf(start)));
		params.add(new BasicNameValuePair("end", String.valueOf(end)));
		Callback callback = new Callback() {
			
			@Override
			public void onResponse(Response r) throws IOException {
				String resultString = null;
				try {
					resultString = HttpUtil.checkResponseIsSuccess(r);
					JSONArray jsonArray = new JSONArray(resultString);
					List<View> resultView = new ArrayList<View>();
					LayoutInflater inflater = LayoutInflater.from(thisActivity);
					int length = jsonArray.length();
					for (int i = 0; i < length; i++) {
						JSONObject jo = jsonArray.getJSONObject(i);
						// 将新闻列表中的左侧的选中图片和对应的新闻item进行封装，存入NewsListImageViewBasicBean中
						NewsListImageViewBasicBean ivBean = new NewsListImageViewBasicBean();
						// 使用news_item.xml中的布局，存入ListView中
						View view = inflater.inflate(R.layout.news_item, null);
						ivBean.setView(view);
						final ImageView checkBtn = (ImageView) view.findViewById(R.id.iv_check_btn);
						ivBean.setImageView(checkBtn);
						//　默认左侧不选中
						checkBtn.setImageResource(R.drawable.uncheck_btn);
						checkBtn.setSelected(false);
						addCheckBtnClickEvent(ivBean);
						
						// 将新闻列表中的每一个item进行封装，并将传入的参数存入到itemBean中
//						RelativeLayout newsTextLayout = (RelativeLayout) view.findViewById(R.id.newsTextLayout);
						NewsListListViewItemBasicBean itemBean = new NewsListListViewItemBasicBean();
						itemBean.setView(view);
						itemBean.setId(jo.getString("id"));
						addListViewItemTouchEvent(itemBean);
//						addXinxiClickEvent(itemBean);
//						addXinxiTouchEvent(itemBean);
						
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
						resultView.add(view);
					}
					
					if (resultView.size() == 0) {
//						msg.what = ParamConst.MESSAGE_WHAT_NO_DATA;
						handler.sendEmptyMessage(ParamConst.MESSAGE_WHAT_NO_DATA);
					} else {
						Message msg = new Message();
						msg.what = ParamConst.MESSAGE_WHAT_SUCCESS;
						msg.obj = resultView;
						handler.sendMessage(msg);
					}
				
				} catch (Exception e) {
					try {
//						initNewsListData(newsListView, false, "错误信息：" + e.getMessage());
						Message msg = new Message();
						msg.what = ParamConst.MESSAGE_WHAT_ERROR;
						msg.obj = "错误信息：" + e.getMessage();
						handler.sendMessage(msg);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			
			@Override
			public void onFailure(Request r, IOException e) {
				Message message = new Message();
				String errorMsg = e.getMessage();
				if (errorMsg == null) {
					errorMsg = "服务器异常";
				}
				Log.e("错误信息", errorMsg);
				message.what = ParamConst.MESSAGE_WHAT_ERROR;
				message.obj = errorMsg;
				handler.sendMessage(message);
//				List<View> view = initDefaultData("错误信息：" + e.getMessage());
				try {
//					initNewsListData(newsListView, false, "错误信息：" + message);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		HttpUtil.okPost(url, params, callback);
	}
	
	/**
	 * 初始化新闻列表中的初始数据
	 * @param text
	 * @param height
	 * @return
	 */
	private List<View> initDefaultData(String text, int height) {
		List<View> resultView = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(this);
		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.news_list_view_default_item, null);
//		LayoutParams initCustomLayout = LayoutParamsUtil.initCustomLayout(LayoutParams.MATCH_PARENT, height / 2);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, height);
		layout.setLayoutParams(params);
		if (text != null) {
//			TextView textView = (TextView) layout.findViewById(R.id.newsListViewDefaultText);
			TextView textView = (TextView) layout.getChildAt(0);
			textView.setText(text);
		}
		resultView.add(layout);
		return resultView;
	}
	/**
	 * 给每一个新闻左侧的选中图标添加点击事件
	 * @param bean
	 */
	private void addCheckBtnClickEvent(final NewsListImageViewBasicBean bean) {
		
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
	 * 给新闻列表的每一条新闻添加点击事件（注释原因：没有按下时的颜色变化，用户体验不佳）
	 * @param bean
	 */
	/*private void addListViewItemClickEvent(final NewsListListViewItemBasicBean bean) {
		OnClickListener listViewItemOnClickListener = new ListViewItemOnClickListener(thisActivity, bean);
		bean.getView().setOnClickListener(listViewItemOnClickListener);
	}*/
	/**
	 * 给新闻列表的每一条新闻添加点击事件
	 * @param bean
	 */
	private void addListViewItemTouchEvent(final NewsListListViewItemBasicBean bean) {
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
	 * 信息列表中的item右侧的“送签”按钮的点击事件
	 * @param bean
	 */
	/*private void addXinxiClickEvent(final NewsListListViewItemBasicBean bean) {
		OnClickListener listener = new ListViewXinxiOnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(thisActivity, "点击了新闻ID为【" + bean.getId() + "】的报送按钮", Toast.LENGTH_SHORT).show();
			}
		};
		*//**
		 * 此处为右侧“送签”按钮由一个LinearLayout包裹起来，然后设置固定宽度和满高，只要点击右侧区域，就能出发“送签”操作。
		 * 注解原因：“送签”图标过小，操作感觉很怪，不过如果改变“送签”图标的话，这个功能还是可以使用的
		 *//*
//		LinearLayout xinxiLayout = (LinearLayout) bean.getView().findViewById(R.id.iv_news_xinxi_layout);
//		xinxiLayout.setOnClickListener(listener);
		ImageView xinxi = (ImageView) bean.getView().findViewById(R.id.iv_news_xinxi);
		xinxi.setOnClickListener(listener);
	}*/
	
	/**
	 * 信息列表中的item右侧的“送签”按钮的点击事件
	 * 注释原因：不好看
	 */
	/*private void addXinxiTouchEvent(final NewsListListViewItemBasicBean bean) {
		CommonOnTouchListener listener = new ListViewXinxiOnTouchListener() {
			
			@Override
			public void onImgChangeDo() {
				Toast.makeText(thisActivity, "点击了新闻ID为【" + bean.getId() + "】的报送按钮", Toast.LENGTH_SHORT).show();
			}
		};
		listener.changeColor(R.color.bg_gray_press, R.color.bg_gray_default);
		bean.getView().findViewById(R.id.iv_news_xinxi_layout).setOnTouchListener(listener);
	}*/
	
	/**
	 * 修改canEnableState状态
	 */
	private void changeCanEnableState() {
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
	private void changeBottomMenuBtnState() {
		int i = 0;
		for (BottomMenuBasicBean bean : bottomMenuList) {
//			ImageView iv = bean.getImageView();
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
	 * 初始化底部菜单的每一个按钮
	 */
	private void initNewsOperateBtn() {
//		LayoutInflater inflater = LayoutInflater.from(this);
		int i = 0;
		// 获取底部的每一个按钮的基本参数
		bottomMenuList = initNewsOperateBtnData();
		changeCanEnableState();
		for (final BottomMenuBasicBean bean : bottomMenuList) {
//			final View view = inflater.inflate(R.layout.operate_btn_basic, null);
			final LinearLayout layout = (LinearLayout) newsOperateBtnLayout.getChildAt(i++);
			if (bean.getImageCheckedResource() == 0) {
				layout.setVisibility(View.GONE);
			} else {
				layout.setVisibility(View.VISIBLE);
			}
			bean.setView(layout);
//			LayoutParams layoutParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
//			view.setLayoutParams(layoutParams);
//			final ImageView iv = (ImageView) view.findViewById(R.id.operateBtnImageView);
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
//						changeOperateBtnState(layout, resultList);
//						changeOperateBtnState(view, resultList);
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
//			TextView tv = (TextView) view.findViewById(R.id.operateBtnText);
			tv.setText(bean.getTextContent());
//			newsOperateBtnLayout.addView(layout);
			
		}
	}
	
	/**
	 * 初始化底部菜单的信息
	 * @return
	 */
	private List<BottomMenuBasicBean> initNewsOperateBtnData() {
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
//			bean.setImageUncheckResource(newsOperateBtnUnchecked[i]);
			bean.setImageDisableResource(newsOperateBtnDisabled[i]);
			bean.setTextContent(newsOperateBtnTextContent[i]);
			bean.setDisableHint(disableHint[i]);
			bean.setCanEnableState(newsOperateBtnCanEnableState[i]);
			resultList.add(bean);
		}
		return resultList;
	}
	
	/**
	 * 在滑动状态改变的时候调用
	 * 有三种状态（0，1，2）。1表示正在滑动；2表示滑动完毕了；0表示什么都没做。
	 */
	@Override
	public void onPageScrollStateChanged(int state) {
		
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
	public void onPageSelected(final int position) {
//		AnimUtil.showRefreshFrame(thisActivity);
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
					NewsListListView listView = (NewsListListView) views.get(i);
					try {
						if (i== position) {
							changeToCurPosition(colorBasicBean, listView);
						} else {
							changeToOtherPosition(colorBasicBean, listView, Math.abs(position - i) > 1, i);
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
				initNewsOperateBtn();
				Log.e("【结束】", sdf.format(new Date()));
//				AnimUtil.hideRefreshFrame(thisActivity);
			}
		}.sendEmptyMessage(0);
		/*int childCount = newsTypeBtnLineLayout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			
			ButtonColorBasicBean colorBasicBean = null;
			try {
				colorBasicBean = new ButtonColorBasicBean(thisActivity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			NewsListListView listView = (NewsListListView) views.get(i);
			changeToCurPosition(colorBasicBean, listView);
			if (i == position) {
				initNewsTypeBtnStyleByFocusedState(colorBasicBean, true);
				NewsListViewAdapter adapter = (NewsListViewAdapter) listView.getAdapter();
//				ListAdapter adapter = listView.getAdapter();
				int count = adapter.getCount();
				if (count == 1) {
					View view = adapter.getView(0, null, null);
					if (view instanceof RelativeLayout) {
						Message message = new Message();
						message.obj = listView;
						message.what = ParamConst.MESSAGE_WHAT_SUCCESS;
						Log.e("向handler中发送一个message刷新数据【开始】", sdf.format(new Date()));
						handler.sendMessage(message);
						Log.e("向handler中发送一个message刷新数据【结束】", sdf.format(new Date()));
					}
				}
			} else {
				initNewsTypeBtnStyleByFocusedState(colorBasicBean, false);
				if (Math.abs(position - i) > 1) {
					selectedNewsCount[i] = 0;
					Message message = new Message();
					message.obj = listView;
					message.what = ParamConst.MESSAGE_WHAT_NO_DATA;
					Log.e("向handler中发送一个message清除数据【开始】", sdf.format(new Date()));
					handler.sendMessage(message);
					Log.e("向handler中发送一个message清除数据【结束】", sdf.format(new Date()));
				}
			}
			Log.e("改变按钮的样式【开始】", sdf.format(new Date()));
			EnableSimpleChangeButton btn = (EnableSimpleChangeButton) newsTypeBtnLineLayout.getChildAt(i);
			btn.setColorBasicBean(colorBasicBean);
			Log.e("改变按钮的样式【结束】", sdf.format(new Date()));
		}
		curPosition = position;
		initNewsOperateBtn();
		Log.e("【结束】", sdf.format(new Date()));*/
	}
	
	/*private Handler onPageSelectedHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				switch (msg.what) {
				case ParamConst.MESSAGE_WHAT_SUCCESS:
					NewsListListView listView = (NewsListListView) msg.obj;
					initNewsListData(listView, true, "数据请求错误");
					break;
				case ParamConst.MESSAGE_WHAT_NO_DATA:
					listView = (NewsListListView) msg.obj;
					initNewsListData(listView, false, null);
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};*/
	
	private void changeToCurPosition(ButtonColorBasicBean colorBasicBean, NewsListListView listView) throws Exception {
		initNewsTypeBtnStyleByFocusedState(colorBasicBean, true);
		NewsListViewAdapter adapter = (NewsListViewAdapter) listView.getAdapter();
//		ListAdapter adapter = listView.getAdapter();
		int count = adapter.getCount();
		if (count == 1) {
			View view = adapter.getView(0, null, null);
			if (view instanceof RelativeLayout) {
				initNewsListData(listView, true, "数据请求错误");
			}
		}
		
	}
	
	
	private void changeToOtherPosition(ButtonColorBasicBean colorBasicBean, NewsListListView listView, boolean needCleanListView, int myPosision) throws Exception {
		initNewsTypeBtnStyleByFocusedState(colorBasicBean, false);
		if (needCleanListView) {
			selectedNewsCount[myPosision] = 0;
			initNewsListData(listView, false, null);
		}
	}
	/**
	 * 初始化“添加新闻”按钮，并且可以拖动
	 * @return
	 */
	/*private void initAddNewsBtn() {
		ImageView addNewsBtn = new ImageView(this);
		addNewsBtn.setImageResource(R.drawable.news_add);
		final WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		final android.view.WindowManager.LayoutParams params = getParams();
		// 悬浮窗默认显示以左上角为起始坐标
		params.gravity = Gravity.RIGHT| Gravity.BOTTOM;
        //悬浮窗的开始位置，因为设置的是从右下角开始，所以屏幕左上角是x=0;y=0        
		params.x = 150;
		params.y = 150;
		
		// 给悬浮的添加新闻按钮加拖拽、点击事件
		AddNewsBtnOnTouchListener listener = new AddNewsBtnOnTouchListener(){
			@Override
			public void touchMove(View v) {
				params.x += touchStartX - touchCurrentX;
				params.y += touchStartY - touchCurrentY;
				wm.updateViewLayout(v, params);
			}

			@Override
			public void onImgChangeDo() {
				Toast.makeText(thisActivity, "点击了“添加新闻”按钮", Toast.LENGTH_SHORT).show();
			}
		};
		
		addNewsBtn.setOnTouchListener(listener);
		wm.addView(addNewsBtn, params);
	}
	
	public WindowManager.LayoutParams getParams(){
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        //设置window type 下面变量2002是在屏幕区域显示，2003则可以显示在状态栏之上
        //wmParams.type = LayoutParams.TYPE_PHONE; 
        //wmParams.type = LayoutParams.TYPE_SYSTEM_ALERT; 
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR; 
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888; 
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
       //wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE; 
        //设置可以显示在状态栏上
        wmParams.flags =  WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN| WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR|
        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        
        //设置悬浮窗口长宽数据  
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        return wmParams;
    }*/
	
	/*private void initNewsOperateRadioGroupBtn() {
		// TODO 如果将activity_news_list中的newsOperateRadioGroup注释去掉，则要把此处代码注释也去掉
		newsOperateRadioGroup = (RadioGroup) findViewById(R.id.newsOperateRadioGroup);
		initNewsOperateRadioBtn();
	}*/
	
	/*private void initNewsOperateRadioBtn() {
		final List<RadioButtonBasicBean> resultList = initNewsOperateRadioBtnData();
		int childCount = newsOperateRadioGroup.getChildCount();
		for (int i = 0; i < childCount; i++) {
			RadioButtonBasicBean bean = resultList.get(i);
			RadioButton radioBtn = (RadioButton) newsOperateRadioGroup.getChildAt(i);
			radioBtn.setBackgroundResource(bean.getImageDisableResource());
			radioBtn.setSelected(false);
			radioBtn.setEnabled(bean.isEnable());
			radioBtn.setText(bean.getTextContent());
		}
	}*/
	
	/*private void initNewsOperateRadioBtn() {
		LayoutInflater inflater = LayoutInflater.from(this);
		final List<RadioButtonBasicBean> resultList = initNewsOperateRadioBtnData();
		for (RadioButtonBasicBean bean : resultList) {
			RadioButton radioBtn = (RadioButton) inflater.inflate(R.layout.news_operate_radio_btn, null);
			radioBtn.setBackgroundResource(bean.getImageDisableResource());
			radioBtn.setSelected(false);
			radioBtn.setEnabled(bean.isEnable());
			radioBtn.setText(bean.getTextContent());
			newsOperateRadioGroup.addView(radioBtn);
		}
	}*/
	
	/*private List<View> initData(Handler handler) {
		List<View> resultView = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(thisActivity);
		Date now = new Date();
		String nowDate = sdf.format(now);
		for (int i = 0; i < 25; i++) {
			ImageViewBasicBean bean = new ImageViewBasicBean();
			View view = inflater.inflate(R.layout.news_item, null);
			bean.setView(view);
			final ImageView checkBtn = (ImageView) view.findViewById(R.id.iv_check_btn);
			bean.setImageView(checkBtn);
			checkBtn.setImageResource(R.drawable.uncheck_btn);
			checkBtn.setSelected(false);
			addCheckBtnClickEvent(bean);
			TextView newsTitle = (TextView) view.findViewById(R.id.tv_news_title);
			newsTitle.setText("今天是" + (i + 1) + "号，我感觉好饿");
			TextView newsTime = (TextView) view.findViewById(R.id.tv_news_time);
	//		newsTime.setText("2016-01-14 16:" + (55 + i) + ":18");
			newsTime.setText(nowDate);
			TextView newsAuthorName = (TextView) view.findViewById(R.id.tv_news_author_name);
			newsAuthorName.setText("杨洋");
			resultView.add(view);
		}
		return resultView;
	}*/
	
	/*private void changeOperateBtnState(View curView, List<ImageViewBasicBean> list) {
		int childCount = newsOperateBtnLayout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = newsOperateBtnLayout.getChildAt(i);
			if (view != curView) {
				ImageView iv = (ImageView) view.findViewById(R.id.operateBtnImageView);
				if (iv.isSelected()) {
					ImageViewBasicBean bean = list.get(i);
					iv.setImageResource(bean.getImageUncheckResource());
					iv.setSelected(false);
					bean.setSelected(false);
					break;
				}
			}
		}
	}*/
	
	/*private List<RadioButtonBasicBean> initNewsOperateRadioBtnData() {
		List<RadioButtonBasicBean> resultList = new ArrayList<RadioButtonBasicBean>();
		int length = newsOperateBtnChecked.length;
		for (int i = 0; i < length; i++) {
			RadioButtonBasicBean bean = new RadioButtonBasicBean();
			bean.setImageCheckedResource(newsOperateBtnChecked[i]);
			bean.setImageUncheckResource(newsOperateBtnDisabled[i]);
			bean.setTextContent(newsOperateBtnTextContent[i]);
			bean.setCanEnableState(newsOperateRadioBtnCanEnableState[i]);
			if (i == 0) {
				bean.setSelected(true);
			}
			resultList.add(bean);
		}
		return resultList;
	}*/
}