package com.enorth.cms.view.news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.enorth.cms.adapter.CommonListViewAdapter;
import com.enorth.cms.adapter.news.NewsListViewAdapter;
import com.enorth.cms.adapter.news.SearchChannelFilterAdapter;
import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.handler.newslist.AllChannelSearchHandler;
import com.enorth.cms.handler.newslist.MyChannelSearchHandler;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.listener.imageview.ImageViewOnTouchListener;
import com.enorth.cms.listener.newslist.ListViewItemOnTouchListener;
import com.enorth.cms.listener.newslist.channelsearch.ChannelSearchEditOnTouchListener;
import com.enorth.cms.listener.newslist.channelsearch.ChannelSearchOnItemClickListener;
import com.enorth.cms.listener.newslist.subtitle.ChooseChannelTypeOnTouchListener;
import com.enorth.cms.listener.popup.PopupWindowOnTouchListener;
import com.enorth.cms.presenter.newslist.ChannelSearchPresenter;
import com.enorth.cms.presenter.newslist.IChannelSearchPresenter;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.ExceptionUtil;
import com.enorth.cms.utils.ReflectUtil;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.StringUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.widget.listview.newslist.NewsListListView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ChannelSearchActivity extends Activity implements IChannelSearchView {
	
	public NewsListListView channelListView;
	
	private ChannelSearchActivity thisActivity;
	
	/**
	 * 全部频道的handler，可以进行下一级频道和上一级的跳转，显示当前目录，并且提供搜索
	 */
	public Handler allChannelHandler;
	/**
	 * 我的频道的handler，不提供下一级频道和上一级的跳转，不显示当前目录，不提供搜索
	 */
	public Handler myChannelHandler;
	/**
	 * 新闻列表中的标题最多能显示多少字
	 */
	private int newsTitleAllowLength;
	/**
	 * 当前已选择的频道ID
	 */
	public Long channelId = 0L;
	/**
	 * 当前已选择的频道名称
	 */
	public String channelName;
	/**
	 * 当前进入该acitivity时频道ID对应的父ID
	 */
	public Long parentChannelId = 0L;
	
//	private List<Long> channelIdSearchRecord;
	/**
	 * 当前选中的频道ID（左侧对勾按钮选中状态时存入，在选择新的频道时清空）
	 */
	public Long curCheckChannelId = -1L;
	/**
	 * 当前选中的频道名称（左侧对勾按钮选中状态时存入，在选择新的频道时清空）
	 */
	public String curCheckChannelName = "";
	/**
	 * 包裹搜索框的相对布局
	 */
	private RelativeLayout channelSearchEditLayout;
	/**
	 * 搜索框
	 */
	public AutoCompleteTextView searchChannelET;
	/**
	 * 搜索频道时使用的适配器
	 */
	public SearchChannelFilterAdapter searchChannelFilterAdapter;
	/**
	 * 此参数默认表示用户当前是在该activity中进行频道的操作，如果进行搜索时
	 * 则此参数会变成ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_AUTO_COMPLETE_TEXT_VIEW
	 * 表示当前用户正在搜索频道的ListView中进行操作
	 */
	private int curChannelListEnableView = ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY;
	/**
	 * 包裹当前频道的目录和返回上一级按钮的layout
	 */
	private RelativeLayout ContentAndBackToPrevLayout;
	/**
	 * “返回上一级”按钮
	 */
	private ImageView back;
	/**
	 * 标题中间的频道类型layout
	 */
	private LinearLayout channelSearchTitleLayout;
	/**
	 * 频道目录
	 */
	public TextView curChooseChannelTV;
	/**
	 * 挡圈选择的标题频道类别
	 */
	public String curChooseChannelType;
	/**
	 * 当前选中的频道目录
	 */
	private TextView channelSearchCheckedText;
	/**
	 * 所有可选的频道（目前只包括全部频道、我的频道两种）
	 */
	public List<String> allChooseChannelName;
	/**
	 * 标头的全部频道选择弹出
	 */
	public PopupWindow popupWindow;
	/**
	 * 接口返回的json数据
	 */
	private JSONObject jsonObject;
	/**
	 * 频道名称的缩写集合
	 */
	public List<Map<NewsListImageViewBasicBean, List<String>>> shortNames;
	/**
	 * 正在加载时的浮层的颜色
	 */
	private int channelPopupColor;
	/**
	 * 判断是否为第一次进入页面（如果是第一次进入页面，需要传入接口的不是频道ID，而是父ID，然后将当前传入的频道进行勾选）
	 */
	public boolean isFirstEnter = true;
	
	public IChannelSearchPresenter presenter;
	
	public List<NewsListImageViewBasicBean> listViewItem = new ArrayList<NewsListImageViewBasicBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_search);
		initBaseData();
		initTitleEvent();
		try {
			initChannelDefaultData();
		} catch (Exception e) {
			error(e);
		}
	}
	
	private void initBaseData() {
		thisActivity = this;
		presenter = new ChannelSearchPresenter(this);
		channelSearchEditLayout = (RelativeLayout) findViewById(R.id.channelSearchEditLayout);
		back = (ImageView) findViewById(R.id.channelSearchTitleBackBtn);
		channelListView = (NewsListListView) findViewById(R.id.childChannelListView);
		channelSearchCheckedText = (TextView) findViewById(R.id.channelSearchCheckedText);
		ContentAndBackToPrevLayout = (RelativeLayout) findViewById(R.id.ContentAndBackToPrevLayout);
		
		View backView = findViewById(R.id.channelSearchTitle);
		// 此处要减去该按钮的宽度，防止频道覆盖按钮
		newsTitleAllowLength = (ScreenTools.getPhoneWidth(thisActivity) - backView.getMeasuredWidth()) / ParamConst.FONT_WIDTH;
		initChooseChannelName();
		channelPopupColor = ContextCompat.getColor(thisActivity, R.color.channel_popup_color);
		initHandler();
		getCurCheckedChannelId();
	}
	
	/**
	 * 将全部频道、我的频道存入数组中，用于标头点击时弹出popupWindow时显示
	 */
	private void initChooseChannelName() {
		allChooseChannelName = new ArrayList<String>();
		allChooseChannelName.add(ParamConst.ALL_CHANNEL);
		allChooseChannelName.add(ParamConst.MY_CHANNEL);
	}
	
	private void initHandler() {
		allChannelHandler = new AllChannelSearchHandler(this);
		myChannelHandler = new MyChannelSearchHandler(this);
	}
	
	/**
	 * 加载默认页面
	 * @param errorHint
	 */
	public void initDefaultData(String errorHint) {
		Resources resources = getResources();
		float titleHeight = resources.getDimension(R.dimen.news_title_height);
		float subTitleHeight = resources.getDimension(R.dimen.news_sub_title_height);
		float operateBtnHeight = resources.getDimension(R.dimen.news_operate_btn_layout_height);
		int height = (int) ((ScreenTools.getPhoneHeight(thisActivity) - titleHeight - subTitleHeight - operateBtnHeight) / 2 + titleHeight + subTitleHeight);
		List<View> items = initDefaultData(errorHint, height);
		ListAdapter adapter = new NewsListViewAdapter(items);
		channelListView.setAdapter(adapter);
	}
	
	/**
	 * 加载默认页面到固定位置上
	 * @param text
	 * @param height
	 * @return
	 */
	private List<View> initDefaultData(String text, int height) {
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
	 * 初始化头部的事件
	 */
	private void initTitleEvent() {
		initBackEvent();
		initChooseChannelEvent();
		initConfirmEvent();
		initEditTextEvent();
		initBackToParentEvent();
	}
	
	/**
	 * 初始化返回键事件
	 */
	private void initBackEvent() {
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				thisActivity.onBackPressed();
			}
		});
	}
	/**
	 * 初始化频道类型选择事件
	 */
	private void initChooseChannelEvent() {
		channelSearchTitleLayout = (LinearLayout) findViewById(R.id.channelSearchTitleLayout);
		curChooseChannelTV = (TextView) findViewById(R.id.channelSearchTitleText);
		curChooseChannelType = SharedPreUtil.getString(thisActivity, ParamConst.CUR_CHOOSE_CHANNEL_TYPE);
		if (StringUtil.isEmpty(curChooseChannelType)) {
			SharedPreUtil.put(thisActivity, ParamConst.CUR_CHOOSE_CHANNEL_TYPE, ParamConst.ALL_CHANNEL);
			curChooseChannelType = ParamConst.ALL_CHANNEL;
		}
		curChooseChannelTV.setText(curChooseChannelType);
		ChooseChannelTypeOnTouchListener listener = new ChooseChannelTypeOnTouchListener(ScreenTools.getTouchSlop(thisActivity)) {
			
			@Override
			public void onImgChangeEnd() {
				
			}
			
			@Override
			public void onImgChangeDo() {
				getChooseChannelPopupWindow(curChooseChannelType);
			}

			@Override
			public void onTouchBegin() {
				curChannelListEnableView = ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY;
			}
		};
		listener.changeColor(R.color.bottom_text_color_green, R.color.common_blue);
		channelSearchTitleLayout.setOnTouchListener(listener);
	}
	
	/**
	 * 如果弹出框处于激活状态，则将弹出框销毁，反之则实例化弹出页面
	 * @param curChooseChannelName
	 */
	private void getChooseChannelPopupWindow(String curChooseChannelName) {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			popupWindow = null;
		} else {
			initChooseChannelPopupWindow(curChooseChannelName);
		}
	}
	
	/**
	 * 实例化频道选择弹出页面
	 * @param curChooseChannelName
	 */
	private void initChooseChannelPopupWindow(final String curChooseChannelName) {
		final LinearLayout layout = new LinearLayout(thisActivity);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundColor(channelPopupColor);
		// 设置半透明
		layout.getBackground().setAlpha(200);
//		LayoutInflater inflater = LayoutInflater.from(thisActivity);
		int size = allChooseChannelName.size();
		int touchSlop = ScreenTools.getTouchSlop(thisActivity);
		for (int i = 0; i < size; i++) {
			new PopupWindowOnTouchListener(thisActivity, curChooseChannelName, i, layout, touchSlop);
		}
		popupWindow = new PopupWindow(layout, ParamConst.POP_WINDOW_COMMON_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		// 为了让popupWindow能够做到点击其他位置可以消失，需要加入如下代码
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		
		popupWindow.setAnimationStyle(R.style.AnimationFade);
		int titleHeight = (int)Math.round(getResources().getDimension(R.dimen.news_title_height));
		int xoffInPixels = ParamConst.POP_WINDOW_COMMON_WIDTH / 2;
	    // 将pixels转为dip
		int xoffInDip = ScreenTools.px2dip(xoffInPixels, thisActivity);
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			popupWindow.showAtLocation(channelSearchTitleLayout, Gravity.TOP, 0, titleHeight);
		} else {
			popupWindow.showAsDropDown(channelSearchTitleLayout, -xoffInDip, 0);
		}
		popupWindow.update();
	}
	
	/**
	 * 点击右上角的“完成”，即将当前选中的频道存入SharedPreferences并返回新闻列表页
	 */
	private void initConfirmEvent() {
		TextView confirm = (TextView) findViewById(R.id.channelTitleMenuConfirm);
		confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean isNotSelectChannel = false;
				try {
					switch (curChannelListEnableView) {
					case ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY:
						isNotSelectChannel = curCheckChannelId == -1L;
						confirmClickCommonEvent(isNotSelectChannel, curCheckChannelId, curCheckChannelName, parentChannelId);
						break;
					case ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_AUTO_COMPLETE_TEXT_VIEW:
						isNotSelectChannel = searchChannelFilterAdapter != null && searchChannelFilterAdapter.curCheckChannelId == -1L;
						confirmClickCommonEvent(isNotSelectChannel, searchChannelFilterAdapter.curCheckChannelId, searchChannelFilterAdapter.curCheckChannelName, searchChannelFilterAdapter.parentChannelId);
						break;
					default:
						ExceptionUtil.simpleExceptionCatch("点击完成时发生错误", new Exception("curChannelListEnableView【" + curChannelListEnableView + "】未知"));
						break;
					}
				} catch (JSONException e) {
					ExceptionUtil.simpleExceptionCatch("点击完成时发生错误", e);
				}
				
			}
		});
	}
	
	private void confirmClickCommonEvent(boolean isNotSelectChannel, long curCheckChannelId, String curCheckChannelName, long parentChannelId) throws JSONException {
		if (curCheckChannelId == -1L) {
			Toast.makeText(thisActivity, "请先选择一个频道", Toast.LENGTH_SHORT).show();
			return;
		} else {
			/*SharedPreUtil.put(thisActivity, ParamConst.CUR_CHANNEL_ID, curCheckChannelId);
			SharedPreUtil.put(thisActivity, ParamConst.CUR_CHANNEL_NAME, curCheckChannelName);
			SharedPreUtil.put(thisActivity, ParamConst.CUR_CHANNEL_ID_PARENT_ID, parentChannelId);*/
			Bundle bundle = initChannelIdForPrevActivity();
			ViewUtil.takeParamsBackToPrevActivity(thisActivity, bundle, ParamConst.CHANNEL_SEARCH_ACTIVITY_BACK_TO_NEWS_LIST_FRAG_ACTIVITY_RESULT_CODE);
		}
	}
	
	private Bundle initChannelIdForPrevActivity() throws JSONException {
		Bundle bundle = new Bundle();
		bundle.putLong(ParamConst.CUR_CHANNEL_ID, curCheckChannelId);
		bundle.putString(ParamConst.CUR_CHANNEL_NAME, curCheckChannelName);
		bundle.putLong(ParamConst.CUR_CHANNEL_ID_PARENT_ID, parentChannelId);
		String channelContent = getChannelContent(true);
		bundle.putString(ParamConst.CUR_CHANNEL_CONTENT, channelContent);
		return bundle;
	}
	
	private void initEditTextEvent() {
		searchChannelET = (AutoCompleteTextView) findViewById(R.id.channelSearchEdit);
		searchChannelET.setOnTouchListener(new ChannelSearchEditOnTouchListener(thisActivity));
	}
	
	/**
	 * 给“上一级”添加点击事件
	 */
	private void initBackToParentEvent() {
		View view = findViewById(R.id.channelBackToPrevSearchLayout);
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					JSONObject curChannel = jsonObject.getJSONObject("curChannel");
					int channelLevel = curChannel.getInt("deptLevel");
					if (channelLevel == 0) {
						Toast.makeText(thisActivity, "当前已经是最上级", Toast.LENGTH_SHORT).show();
						return;
					}
					channelId = curChannel.getLong("parentId");
					initChannelDefaultData();
				} catch (Exception e) {
					onFailure(e);
				}
			}
		});
	}
	
	/**
	 * 将刚刚进入页面需要加载的频道列表和当前的频道的数据进行加载
	 * @throws Exception
	 */
	public void initChannelDefaultData() throws Exception {
		AnimUtil.showRefreshFrame(thisActivity);
		String curChooseChannelType = SharedPreUtil.getString(thisActivity, ParamConst.CUR_CHOOSE_CHANNEL_TYPE);
		if (curChooseChannelType.equals(ParamConst.ALL_CHANNEL)) {
			getAllChannel();
		} else if (curChooseChannelType.equals(ParamConst.MY_CHANNEL)) {
			getMyChannel();
		}
	}
	/**
	 * 先将搜索框、目录和返回上一级进行显示，再获取所有频道的数据
	 * @throws Exception
	 */
	public void getAllChannel() throws Exception {
		channelSearchEditLayout.setVisibility(View.VISIBLE);
		ContentAndBackToPrevLayout.setVisibility(View.VISIBLE);
		removeAllListData();
		channelSearchCheckedText.setText("");
		if (isFirstEnter) {
			presenter.initChannelData(parentChannelId, allChannelHandler);
		} else {
			presenter.initChannelData(channelId, allChannelHandler);
		}
		
	}
	/**
	 * 先将搜索框、目录和返回上一级进行隐藏，再获得我的频道的数据
	 * @throws Exception
	 */
	public void getMyChannel() throws Exception {
		channelSearchEditLayout.setVisibility(View.GONE);
		ContentAndBackToPrevLayout.setVisibility(View.GONE);
		removeAllListData();
		presenter.getMyChannel(ParamConst.USER_ID, myChannelHandler);
	}
	/**
	 * 将ListView中的数据全部删除
	 */
	private void removeAllListData() {
		List<View> items = new ArrayList<View>();
		CommonListViewAdapter adapter = new CommonListViewAdapter(items);
		channelListView.setAdapter(adapter);
	}
	
	@Override
	public void initChannelData(String result, Handler handler) throws Exception {
		jsonObject = new JSONObject(result);
		initChannelDataListView(jsonObject, handler);
	}
	
	@Override
	public void getMyChannel(String result, Handler handler) throws Exception {
		JSONArray ja = new JSONArray(result);
		initMyChannelDataListView(ja, handler);
	}
	
	@Override
	public void error(Exception e) {
		Log.e("进入error()方法", e.getMessage());
		e.printStackTrace();
	}
	
	@Override
	public void finallyExec() {
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				AnimUtil.hideRefreshFrame(thisActivity);
			}
		}.sendEmptyMessage(0);
	}
	
	@Override
	public void onFailure(Exception e) {
		Log.e("服务器异常", e.getMessage());
		Message message = new Message();
		String errorMsg = e.getMessage();
		if (errorMsg == null) {
			errorMsg = "服务器异常";
		}
		message.what = ParamConst.MESSAGE_WHAT_ERROR;
		message.obj = errorMsg;
		allChannelHandler.sendMessage(message);
	}
	
	/**
	 * 将接口返回的数据存入到ListView中
	 * @param jsonObject
	 * @throws JSONException
	 */
	private void initChannelDataListView(JSONObject jsonObject, Handler handler) throws JSONException {
		JSONArray jsonArray = jsonObject.getJSONArray("children");
		initDataListView(jsonArray, handler, true);
	}
	
	private void initMyChannelDataListView(JSONArray jsonArray, Handler handler) throws JSONException {
		initDataListView(jsonArray, handler, false);
	}
	
	private void initDataListView(JSONArray jsonArray, Handler handler, boolean canClick) throws JSONException {
		List<View> items = setDataToItems(jsonArray, canClick);
		if (items.size() == 0) {
			handler.sendEmptyMessage(ParamConst.MESSAGE_WHAT_NO_DATA);
		} else {
			Message msg = new Message();
			msg.what = ParamConst.MESSAGE_WHAT_SUCCESS;
			msg.obj = items;
			handler.sendMessage(msg);
		}
	}
	
	/**
	 * 向ListView中的每一个item存值
	 * @param jsonArray
	 * @param canClick 判断是否可以显示可点击进入下一级频道的标识
	 * @return
	 * @throws JSONException
	 */
	private synchronized List<View> setDataToItems(JSONArray jsonArray, boolean canClick) throws JSONException {
		List<View> views = new ArrayList<View>();
		listViewItem = new ArrayList<NewsListImageViewBasicBean>();
		shortNames = new ArrayList<Map<NewsListImageViewBasicBean,List<String>>>();
 		LayoutInflater inflater = LayoutInflater.from(this);
		int length = jsonArray.length();
		for (int i = 0; i < length; i++) {
			// 将重要数据封装到bean中
			NewsListImageViewBasicBean ivBean = new NewsListImageViewBasicBean();
			LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.channel_search_item, null);
			JSONObject jo = jsonArray.getJSONObject(i);
			Long channelId = jo.getLong("deptId");
			String channelName = jo.getString("deptName");
			ivBean.setId(String.valueOf(channelId));
			ivBean.setName(channelName);
			ivBean.setParentId(jo.getString("parentId"));
			boolean isHasChild = jo.getBoolean("hasChild");
			ivBean = initChannelBeanCommon(ivBean, layout, canClick, isHasChild);

			initCheckBtnState(ivBean);
			addCheckBtnClickEvent(ivBean);
			addListViewItemClickEvent(ivBean);
			saveShortNames(jo.getString("pinyin"), ivBean);
			listViewItem.add(ivBean);
			views.add(layout);
		}
		return views;
	}
	
	/**
	 * 在频道显示和频道搜索时可以共用的bean中参数存入的方法
	 * @param bean
	 * @param view 每一个item对应的view
	 * @param canClick 判断是否可以进入下一级频道的标识，在“我的频道”中，不允许进入下级频道；在频道搜索中，允许进入下级频道
	 * @param isHasChild 当前频道是否有子频道
	 * @return
	 */
	public NewsListImageViewBasicBean initChannelBeanCommon(NewsListImageViewBasicBean bean, View view, boolean canClick, boolean isHasChild) {
		TextView channelNameTv = (TextView) view.findViewById(R.id.tv_news_title);
		channelNameTv.setText(bean.getName());
		bean.setView(view);
		ImageView imageView = (ImageView) view.findViewById(R.id.iv_check_btn);
		bean.setImageView(imageView);
		// 判断是否可以进入下一级频道的标识，在“我的频道”中，不允许进入下级频道；在频道搜索中，允许进入下级频道
		if (canClick) {
			ImageView next = (ImageView) view.findViewById(R.id.iv_news_next);
			bean.setHasChild(isHasChild);
			if (isHasChild) {
				next.setVisibility(View.VISIBLE);
				bean.setCanClick(true);
			} else {
				next.setVisibility(View.GONE);
				bean.setCanClick(false);
			}
		}
		bean.setImageCheckedResource(R.drawable.check_btn);
		bean.setImageUncheckResource(R.drawable.uncheck_btn);
//		addCheckBtnClickEvent(bean);
//		addListViewItemClickEvent(bean);
		return bean;
	}
	
	/**
	 * 初始化左侧按钮的状态
	 * @param bean
	 */
	private void initCheckBtnState(final NewsListImageViewBasicBean bean) {
		// 如果是第一次进入，则说明是将当前频道ID的父ID传入接口中，所以要将当前频道ID进行勾选操作
		if (isFirstEnter) {
			if (bean.getId().equals(String.valueOf(channelId))) {
				bean.getImageView().setImageResource(bean.getImageCheckedResource());
				bean.setSelected(true);
				bean.getImageView().setSelected(true);
				curCheckChannelId = Long.parseLong(bean.getId());
				curCheckChannelName = bean.getName();
				parentChannelId = Long.parseLong(bean.getParentId());
			} else {
				bean.getImageView().setImageResource(bean.getImageUncheckResource());
				bean.getImageView().setSelected(false);
			}
		} else {
			bean.getImageView().setImageResource(bean.getImageUncheckResource());
		}
	}
	
	/**
	 * 给每一个频道左侧的选中图标添加点击事件
	 * @param bean
	 */
	private void addCheckBtnClickEvent(final NewsListImageViewBasicBean bean) {
		
		OnTouchListener listViewCheckBtnOnTouchListener = new ImageViewOnTouchListener(bean, ScreenTools.getTouchSlop(thisActivity)) {
			@Override
			public boolean onImgChangeBegin() {
				checkChannel(bean);
				return true;
			}

			@Override
			public void onImgChangeEnd() {
				if (!bean.getImageView().isSelected()) {
					curCheckChannelId = -1L;
					curCheckChannelName = "";
				} else {
					curCheckChannelId = Long.parseLong(bean.getId());
					curCheckChannelName = bean.getName();
				}
			}

			@Override
			public void onTouchBegin() {
				// 将焦点定位到搜索栏中
				curChannelListEnableView = ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY;
			}
		};
		bean.setOnTouchListener(listViewCheckBtnOnTouchListener);
		bean.getImageView().setOnTouchListener(listViewCheckBtnOnTouchListener);
	}
	
	public void checkChannel(NewsListImageViewBasicBean bean) {
		if (!bean.getImageView().isSelected()) {
			for (NewsListImageViewBasicBean b : listViewItem) {
				if (b.getImageView().isSelected()) {
					b.getImageView().setImageResource(bean.getImageUncheckResource());
					b.getImageView().setSelected(false);
					b.setSelected(false);
					break;
				}
			}
		}
	}
	
	/**
	 * 给ListView中的item添加点击事件（点击搜索下一级）
	 * @param bean
	 */
	private void addListViewItemClickEvent(final NewsListImageViewBasicBean bean) {
		CommonOnTouchListener listViewItemOnTouchListener = new ListViewItemOnTouchListener(ScreenTools.getTouchSlop(thisActivity)) {
			@Override
			public void onImgChangeDo() {
				try {
					channelClick(bean);
				} catch (Exception e) {
					error(e);
				}
			}
			
			@Override
			public boolean isClickBackgroungColorChange() {
				return false;
			}

			@Override
			public void onTouchBegin() {
				// 将焦点定位到搜索栏中
				curChannelListEnableView = ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY;
				
			}
		};
//		listViewItemOnTouchListener.changeColor(R.color.bg_gray_press, R.color.bg_gray_default);
		bean.getView().setOnTouchListener(listViewItemOnTouchListener);
		CommonOnClickListener listViewItemOnClickListener = new CommonOnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					channelClick(bean);
				} catch (Exception e) {
					error(e);
				}
			}
		};
		bean.getView().setOnClickListener(listViewItemOnClickListener);
	}
	
	private void saveShortNames(String shortName, NewsListImageViewBasicBean bean) {
		Map<NewsListImageViewBasicBean, List<String>> resultMap = new HashMap<NewsListImageViewBasicBean, List<String>>();
		String[] splitShortNames = shortName.split(",");
		List<String> resultNames = new ArrayList<String>();
		for (String str : splitShortNames) {
			resultNames.add(str);
		}
		NewsListImageViewBasicBean resultBean = copyBeanSimple(bean);
		resultMap.put(resultBean, resultNames);
		shortNames.add(resultMap);
	}
	
	private void channelClick(NewsListImageViewBasicBean bean) throws Exception {
		if (bean.isCanClick()) {
			channelId = Long.parseLong(bean.getId());
			channelName = bean.getName();
			initChannelDefaultData();
		} else {
			curCheckChannelId = Long.parseLong(bean.getId());
			curCheckChannelName = bean.getName();
			checkChannel(bean);
			if (bean.isSelected()) {
				bean.getImageView().setImageResource(bean.getImageUncheckResource());
				bean.getImageView().setSelected(false);
				bean.setSelected(false);
			} else {
//				checkBtn.setImageResource(R.drawable.check_btn);
//				checkBtn.setSelected(true);
				bean.getImageView().setImageResource(bean.getImageCheckedResource());
				bean.getImageView().setSelected(true);
				bean.setSelected(true);
			}
		}
	}
	
	/**
	 * 获取传入该activity的新闻ID和对应的父ID
	 */
	private void getCurCheckedChannelId() {
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras.containsKey(ParamConst.CUR_CHANNEL_ID) && extras.containsKey(ParamConst.CUR_CHANNEL_ID_PARENT_ID)) {
			channelId = extras.getLong(ParamConst.CUR_CHANNEL_ID);
			parentChannelId = extras.getLong(ParamConst.CUR_CHANNEL_ID_PARENT_ID);
		}
	}
	
	/**
	 * 获取存入SharedPreferences中的当前选中的频道信息(注解原因：由于有很多activity都会进入这个搜索频道页，为了适配，此方法不适用)
	 */
	/*private void getCurCheckedChannelId() {
		channelId = SharedPreUtil.getLong(thisActivity, ParamConst.CUR_CHANNEL_ID);
		if (channelId == -1L) {
			SharedPreUtil.resetChannelIdData(thisActivity);
			channelName = ParamConst.DEFAULT_CHANNEL_NAME;
		} else {
			channelName = SharedPreUtil.getString(thisActivity, ParamConst.CUR_CHANNEL_NAME);
			parentChannelId = SharedPreUtil.getLong(thisActivity, ParamConst.CUR_CHANNEL_ID_PARENT_ID);
		}
	}*/
	
	/**
	 * 重置频道目录
	 * @param useCurChannelName 判断是否在目录的最后用的当前频道的频道名
	 * @throws JSONException
	 */
	public void resetCurChannelSearchCheckedText(boolean useCurChannelName) throws JSONException {
		channelName = getChannelContent(useCurChannelName);
		channelSearchCheckedText.setText(channelName);
		JSONObject jo = jsonObject.getJSONObject("curChannel");
		parentChannelId = jo.getLong("deptId");
		AnimUtil.hideRefreshFrame(thisActivity);
	}
	
	/**
	 * 将接口返回的频道数据进行处理，最后生成频道目录
	 * 目录的最后一个频道根据传入的boolean值进行变化：true表示使用当前选中的频道名（当为true的时候，channelName不能为空）；false表示使用父频道的频道名
	 * @param useCurChannelName
	 * @return
	 * @throws JSONException
	 */
	private String getChannelContent(boolean useCurChannelName) throws JSONException {
		JSONArray ja = jsonObject.getJSONArray("channelContent");
		JSONObject jo = jsonObject.getJSONObject("curChannel");
		int length = ja.length();
		
		JSONObject firstDept = ja.getJSONObject(0);
//		JSONObject lastDept = ja.getJSONObject(length - 1);
		String lastDeptName = null;
		if (useCurChannelName) {
			lastDeptName = channelName;
		} else {
			lastDeptName = jo.getString("deptName");;
		}
		if (length == 1) {
			channelName = substrText(channelName, newsTitleAllowLength);
		} else if (length == 2) {
			channelName = substrText(firstDept.getString("deptName"), newsTitleAllowLength / 2 - 1) + "/" + substrText(lastDeptName, newsTitleAllowLength / 2 - 1);
		} else {
			channelName = substrText(firstDept.getString("deptName"), newsTitleAllowLength / 2 - 3) + "/../" + substrText(lastDeptName, newsTitleAllowLength / 2 - 3);
		}
		return channelName;
	}
	
	private String substrText(String text, int length) {
		if (text.length() > length) {
			return text.substring(0, length) + "..";
		} else {
			return text;
		}
	}
	
	/**
	 * 用于在搜索频道时需要通过当前显示的频道进行搜索，所以需要重新实例化一个新的bean，并将基本信息存入新的bean中，用于adapter存入新的控件
	 * @param bean
	 * @return
	 */
	private NewsListImageViewBasicBean copyBeanSimple(NewsListImageViewBasicBean bean) {
		NewsListImageViewBasicBean resultBean = new NewsListImageViewBasicBean();
		resultBean.setId(bean.getId());
		resultBean.setName(bean.getName());
		resultBean.setParentId(bean.getParentId());
		resultBean.setHasChild(bean.isHasChild());
		resultBean.setCanClick(bean.isCanClick());
		return resultBean;
	}

	public int getCurChannelListEnableView() {
		return curChannelListEnableView;
	}

	public void setCurChannelListEnableView(int curChannelListEnableView) {
		this.curChannelListEnableView = curChannelListEnableView;
	}
	
	
}
