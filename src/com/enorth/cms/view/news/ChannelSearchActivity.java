package com.enorth.cms.view.news;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.enorth.cms.adapter.news.ChannelSearchListViewAdapter;
import com.enorth.cms.adapter.news.SearchChannelFilterAdapter;
import com.enorth.cms.bean.channel_search.RequestChannelSearchUrlBean;
import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.handler.newslist.AllChannelSearchHandler;
import com.enorth.cms.handler.newslist.MyChannelSearchHandler;
import com.enorth.cms.listener.EditTextDrawableOnTouchListener;
import com.enorth.cms.listener.newslist.subtitle.ChooseChannelTypeOnTouchListener;
import com.enorth.cms.listener.popup.channelsearch.ChannelSearchPopupWindowOnTouchListener;
import com.enorth.cms.presenter.newslist.ChannelSearchPresenter;
import com.enorth.cms.presenter.newslist.IChannelSearchPresenter;
import com.enorth.cms.utils.ActivityJumpUtil;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.BeanParamsUtil;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.DrawableUtil;
import com.enorth.cms.utils.ExceptionUtil;
import com.enorth.cms.utils.JsonUtil;
import com.enorth.cms.utils.PopupWindowUtil;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.StringUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.widget.listview.newslist.NewsListListView;
import com.enorth.cms.widget.popupwindow.CommonPopupWindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ChannelSearchActivity extends Activity implements IChannelSearchView {
	
	private NewsListListView channelListView;
	
	private ChannelSearchListViewAdapter channelSearchListViewAdapter;
	
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
	private Long channelId = 0L;
	/**
	 * 当前已选择的频道名称
	 */
	private String channelName;
	/**
	 * 当前进入该acitivity时频道ID对应的父ID
	 */
	private Long parentChannelId = 0L;
	
//	private List<Long> channelIdSearchRecord;
	/**
	 * 当前选中的频道ID（左侧对勾按钮选中状态时存入，在选择新的频道时清空）
	 */
	private Long curCheckChannelId = -1L;
	/**
	 * 当前选中的频道名称（左侧对勾按钮选中状态时存入，在选择新的频道时清空）
	 */
	private String curCheckChannelName = "";
	/**
	 * 包裹搜索框的相对布局
	 */
	private RelativeLayout channelSearchEditLayout;
	/**
	 * “上一级”按钮外面包裹的LinearLayout
	 */
	private LinearLayout channelBackToPrevSearchLayout;
	/**
	 * 搜索框
	 */
	private AutoCompleteTextView searchChannelET;
	/**
	 * 搜索频道时使用的适配器
	 */
	private SearchChannelFilterAdapter searchChannelFilterAdapter;
	/**
	 * 此参数默认表示用户当前是在该activity中进行频道的操作，如果进行搜索时
	 * 则此参数会变成ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_AUTO_COMPLETE_TEXT_VIEW
	 * 表示当前用户正在搜索频道的ListView中进行操作
	 */
	private int curChannelListEnableView = ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY;
	/**
	 * 包裹当前频道的目录和返回上一级按钮的layout
	 */
	private RelativeLayout contentAndBackToPrevLayout;
	/**
	 * 左上角的返回按钮
	 */
	private ImageView back;
	/**
	 * “全部频道”标题
	 */
	private TextView curChooseChannelTV;
	/**
	 * 标题右上角的“完成”按钮
	 */
	private TextView confirm;
	/**
	 * 背景提示信息
	 */
//	private RelativeLayout hintRelative;
	/**
	 * 挡圈选择的标题频道类别
	 */
	private String curChooseChannelType;
	/**
	 * 当前选中的频道目录
	 */
	private TextView channelSearchCheckedText;
	/**
	 * 所有可选的频道（目前只包括全部频道、我的频道两种）
	 */
	private List<String> allChooseChannelName;
	/**
	 * 标头的全部频道选择弹出
	 */
	private CommonPopupWindow popupWindow;
	/**
	 * 接口返回的json数据
	 */
	private JSONObject jsonObject;
	/**
	 * 接口返回的当前频道
	 */
	private JSONObject self;
	/**
	 * 频道名称的缩写集合
	 */
//	public List<Map<NewsListImageViewBasicBean, List<String>>> shortNames;
	/**
	 * 正在加载时的浮层的颜色
	 */
//	private int channelPopupColor;
	/**
	 * 判断是否为第一次进入页面（如果是第一次进入页面，需要传入接口的不是频道ID，而是父ID，然后将当前传入的频道进行勾选）
	 */
	private boolean isFirstEnter = true;
	
	private IChannelSearchPresenter presenter;
	
	/**
	 * 弹出框工具
	 */
	private PopupWindowUtil popupWindowUtil;
	/**
	 * 用于请求接口的bean
	 */
	private RequestChannelSearchUrlBean requestChannelSearchUrlBean;
	/**
	 * 当前选中的频道bean
	 */
	private ChannelBean channelBean;
	/**
	 * 表示是否可以通过点击查询子频道（全部频道为true,我的频道为false）
	 */
	private boolean canClickToChild = true;
	/**
	 * 点击的事件是否为返回上一级频道（true表示是返回上一级频道，false表示是点击频道进入子频道）
	 */
	private boolean isBackToParent;
	/**
	 * 按照树形结构排列的频道名称
	 */
	private List<String> channelNamesTree;
	/**
	 * 是否点击搜索框
	 * 	由于在点击搜索框之后，ListView中之前选中保存的view会消失父亲，所以在点击搜索框时，要将此参数改为true
	 * 	在adapter中调用notifyDataChanged，然后再改回false，是为了保证效率）
	 */
	private boolean isClickET = false;
	/**
	 * 弹出框所需的数据
	 */
//	private PopupWindowBean bean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_search);
		initView();
		initBaseData();
		initTitleEvent();
		initChannelDefaultData();
	}
	
	private void initView() {
		// 标题左侧的返回按钮
		back = (ImageView) findViewById(R.id.titleLeftIV);
		// 标题
		curChooseChannelTV = (TextView) findViewById(R.id.titleMiddleTV);
		
		confirm = (TextView) findViewById(R.id.titleRightTV);
		
		// 搜索框包裹的Layout
		channelSearchEditLayout = (RelativeLayout) findViewById(R.id.channelSearchEditLayout);
		// 显示当前频道目录
		channelSearchCheckedText = (TextView) findViewById(R.id.channelSearchCheckedText);
		// 返回上一级频道
		channelBackToPrevSearchLayout = (LinearLayout) findViewById(R.id.channelBackToPrevSearchLayout);
		// 频道ListView
		channelListView = (NewsListListView) findViewById(R.id.childChannelListView);
		// 包裹当前频道目录和返回上一级频道的Layout
		contentAndBackToPrevLayout = (RelativeLayout) findViewById(R.id.contentAndBackToPrevLayout);
		// 搜索框
		searchChannelET = (AutoCompleteTextView) findViewById(R.id.channelSearchEdit);
		// 背景提示信息
//		hintRelative = (RelativeLayout) findViewById(R.id.hintRelative);
		initViewBaseData();
	}
	/**
	 * 初始化控件的基本数据
	 */
	private void initViewBaseData() {
		// 添加返回图标
		back.setBackgroundResource(R.drawable.common_back);
		// 给标题的右侧添加一个向下的箭头
		curChooseChannelTV.setCompoundDrawablesWithIntrinsicBounds(null, null, DrawableUtil.getDrawable(this, R.drawable.news_down_btn), null);
		// 完成
		confirm.setText(R.string.channel_search_title_confirm);
		
	}
	
	private void initBaseData() {
		// 获取当前选中的频道bean
//		channelBean = (ChannelBean) BeanParamsUtil.getObject(ChannelBean.class, this);
		channelBean = StaticUtil.getCurChannelBean(this);
		// 将当前的频道ID进行储存
		initRequestChannelSearchUrlBean();
		
		presenter = new ChannelSearchPresenter(this);
		// 此处要减去该按钮的宽度，防止频道覆盖按钮
//		channelSearchCheckedText
		Rect bounds = new Rect();
		// 目前默认的内容是“北方网”，所以算出的宽度就是“北方网”的宽度，再除以3即为一个字的宽度
		channelSearchCheckedText.getPaint().getTextBounds(channelSearchCheckedText.getText().toString(), 0, channelSearchCheckedText.getText().length(), bounds);
		int fontWidth = bounds.width();
		fontWidth = ScreenTools.px2dip(fontWidth, this);
		int phoneWidth = ScreenTools.getPhoneWidth(this);
		int measuredWidth = channelBackToPrevSearchLayout.getMeasuredWidth();
		newsTitleAllowLength = (phoneWidth - measuredWidth) / fontWidth;
		initChooseChannelName();
//		channelPopupColor = ContextCompat.getColor(this, R.color.channel_popup_color);
		initHandler();
		getCurCheckedChannelId();
		initAdapter();
	}
	
	private void initRequestChannelSearchUrlBean() {
		requestChannelSearchUrlBean = new RequestChannelSearchUrlBean();
		requestChannelSearchUrlBean.setChannelId(channelBean.getChannelId());
		
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
	
	private void initAdapter() {
		initListViewAdapter();
//		initAutoCompleteTextViewAdapter();
	}
	
	private void initListViewAdapter() {
		channelSearchListViewAdapter = new ChannelSearchListViewAdapter(new ArrayList<ChannelBean>(), this);
		channelListView.setAdapter(channelSearchListViewAdapter);
	}
	
	/*private void initAutoCompleteTextViewAdapter() {
		searchChannelFilterAdapter = new SearchChannelFilterAdapter(
				this, R.layout.channel_search_item, new ArrayList<ChannelBean>());
		searchChannelET.setAdapter(searchChannelFilterAdapter);
	}*/
	
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
				ChannelSearchActivity.this.onBackPressed();
			}
		});
	}
	/**
	 * 初始化频道类型选择事件
	 */
	private void initChooseChannelEvent() {
		curChooseChannelType = SharedPreUtil.getString(this, ParamConst.CUR_CHOOSE_CHANNEL_TYPE, "");
		if (StringUtil.isEmpty(curChooseChannelType)) {
			SharedPreUtil.put(this, ParamConst.CUR_CHOOSE_CHANNEL_TYPE, ParamConst.ALL_CHANNEL);
			curChooseChannelType = ParamConst.ALL_CHANNEL;
		}
		curChooseChannelTV.setText(curChooseChannelType);
		ChooseChannelTypeOnTouchListener listener = new ChooseChannelTypeOnTouchListener(ScreenTools.getTouchSlop(this)) {
			
			@Override
			public void onImgChangeEnd(View v) {
				
			}
			
			@Override
			public void onImgChangeDo(View v) {
				initPopupWindow();
			}

			@Override
			public void onTouchBegin() {
				curChannelListEnableView = ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY;
			}
		};
		listener.changeColor(ColorUtil.getBottomTextColorGreen(this), ColorUtil.getCommonBlueColor(this));
		curChooseChannelTV.setOnTouchListener(listener);
	}
	
	/**
	 * 实例化频道选择弹出页面。如果弹出框处于激活状态，则将弹出框销毁，反之则实例化弹出页面
	 * @param curChooseChannelName
	 */
	private void initPopupWindow() {
		if (popupWindowUtil == null) {
			popupWindowUtil = new PopupWindowUtil(this, curChooseChannelTV) {
				
				@Override
				public void initItems(LinearLayout layout) {
					ChannelSearchPopupWindowOnTouchListener listener = new ChannelSearchPopupWindowOnTouchListener(ChannelSearchActivity.this, layout) {
						@Override
						public void onImgChangeEnd(View v) {
							popupWindow.dismiss();
							popupWindow = null;
						}
					};
					initPopupWindowItemsContainCheckMark(layout, listener, allChooseChannelName, curChooseChannelType);
				}
			};
			popupWindowUtil.setXoffInPixels(-popupWindowUtil.getWidth() / 2);
		}
		popupWindow = popupWindowUtil.initPopupWindow();
	}
	
	/**
	 * 点击右上角的“完成”，即将当前选中的频道存入SharedPreferences并返回新闻列表页
	 */
	private void initConfirmEvent() {
		
		confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean isNotSelectChannel = false;
				switch (curChannelListEnableView) {
				case ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY:
					isNotSelectChannel = (curCheckChannelId == null || curCheckChannelId == -1L);
					confirmClickCommonEvent(isNotSelectChannel, curCheckChannelId, curCheckChannelName, parentChannelId);
					break;
				case ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_AUTO_COMPLETE_TEXT_VIEW:
					isNotSelectChannel = (searchChannelFilterAdapter != null && searchChannelFilterAdapter.getCurCheckChannelId() == -1L);
					confirmClickCommonEvent(isNotSelectChannel, searchChannelFilterAdapter.getCurCheckChannelId(), searchChannelFilterAdapter.getCurCheckChannelName(), searchChannelFilterAdapter.getParentChannelId());
					break;
				default:
					ExceptionUtil.simpleExceptionCatch("点击完成时发生错误", new Exception("curChannelListEnableView【" + curChannelListEnableView + "】未知"));
					break;
				}
				
			}
		});
	}
	
	private void confirmClickCommonEvent(boolean isNotSelectChannel, long curCheckChannelId, String curCheckChannelName, long parentChannelId) {
		if (isNotSelectChannel || curCheckChannelId == -1L) {
			Toast.makeText(this, "请先选择一个频道", Toast.LENGTH_SHORT).show();
			return;
		} else {
			Bundle bundle = initChannelIdForPrevActivity();
			if (bundle != null) {
				ActivityJumpUtil.takeParamsBackToPrevActivity(this, bundle, ParamConst.CHANNEL_SEARCH_ACTIVITY_BACK_TO_NEWS_LIST_FRAG_ACTIVITY_RESULT_CODE);
			} else {
				finish();
			}
		}
	}
	
	private Bundle initChannelIdForPrevActivity() {
		JSONArray jsonArray = JsonUtil.getJSONArray(jsonObject, ParamConst.CHANNEL_PARENTS);
		ChannelBean cb = null;
		switch (curChannelListEnableView) {
		case ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY:
			cb = channelSearchListViewAdapter.getChannelBean();
			break;
		case ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_AUTO_COMPLETE_TEXT_VIEW:
			cb = searchChannelFilterAdapter.getChannelBean();
			break;
		default:
			ExceptionUtil.simpleExceptionCatch("点击完成时发生错误", new Exception("curChannelListEnableView【" + curChannelListEnableView + "】未知"));
			break;
		}
		// cb为空的情况只有进入搜索频道页面没有进行任何选中操作，并且带入选中的item还没有初始化
		if (cb == null) {
			ChannelSearchActivity.this.onBackPressed();
			return null;
		}
		JSONObject channelSelf = BeanParamsUtil.saveObjectToJson(cb, this);
		jsonArray.put(channelSelf);
		StaticUtil.saveChannel(jsonArray, this);
		Bundle bundle = new Bundle();
		return bundle;
	}
	
	private void initEditTextEvent() {
		searchChannelET.setOnTouchListener(new EditTextDrawableOnTouchListener(this) {
			
			@Override
			public EditText getEditText() {
				return searchChannelET;
			}
			
			@Override
			public void eventDo() {
				searchChannelET.setText("");
			}
		});
	}
	
	/**
	 * 给“上一级”添加点击事件
	 */
	private void initBackToParentEvent() {
		View view = findViewById(R.id.channelBackToPrevSearchLayout);
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				JSONObject curChannel = JsonUtil.getJSONObject(jsonObject, ParamConst.CHANNEL_SELF);
				int channelLevel = JsonUtil.getInt(curChannel, ParamConst.CHANNEL_LEVEL);
				if (channelLevel == 0) {
					Toast.makeText(ChannelSearchActivity.this, "当前已经是最上级", Toast.LENGTH_SHORT).show();
					return;
				}
				channelId = JsonUtil.getLong(curChannel, ParamConst.CHANNEL_PARENT_ID);
				isBackToParent = true;
				initChannelDefaultData();
			}
		});
	}
	
	/**
	 * 将刚刚进入页面需要加载的频道列表和当前的频道的数据进行加载
	 * @throws Exception
	 */
	public void initChannelDefaultData() {
		AnimUtil.showRefreshFrame(this, true, "正在加载，请稍后");
		String curChooseChannelType = SharedPreUtil.getString(this, ParamConst.CUR_CHOOSE_CHANNEL_TYPE, "");
		if (curChooseChannelType.equals(ParamConst.ALL_CHANNEL)) {
			getAllChannel();
		} else if (curChooseChannelType.equals(ParamConst.MY_CHANNEL)) {
			getMyChannel();
		} else {
			SharedPreUtil.remove(this, ParamConst.CUR_CHOOSE_CHANNEL_TYPE);
		}
	}
	/**
	 * 先将搜索框、目录和返回上一级进行显示，再获取所有频道的数据
	 * @throws Exception
	 */
	public void getAllChannel() {
		channelSearchEditLayout.setVisibility(View.VISIBLE);
		contentAndBackToPrevLayout.setVisibility(View.VISIBLE);
		removeAllListData();
		channelSearchCheckedText.setText("");
		if (isFirstEnter) {
			requestChannelSearchUrlBean.setChannelId(parentChannelId);
		} else {
			requestChannelSearchUrlBean.setChannelId(channelId);
		}
		List<BasicNameValuePair> params = BeanParamsUtil.initData(requestChannelSearchUrlBean, this);
		presenter.initChannelData(params, allChannelHandler);
		/*if (isFirstEnter) {
			List<BasicNameValuePair> params = BeanParamsUtil.initData(requestChannelSearchUrlBean, this);
			presenter.initChannelData(params, allChannelHandler);
		} else {
			List<BasicNameValuePair> params = BeanParamsUtil.initData(requestChannelSearchUrlBean, this);
			presenter.initChannelData(params, allChannelHandler);
		}*/
		
	}
	/**
	 * 先将搜索框、目录和返回上一级进行隐藏，再获得我的频道的数据
	 * @throws Exception
	 */
	public void getMyChannel() {
		channelSearchEditLayout.setVisibility(View.GONE);
		contentAndBackToPrevLayout.setVisibility(View.GONE);
		removeAllListData();
		List<BasicNameValuePair> params = BeanParamsUtil.initData(null, this);
		presenter.getMyChannel(params, myChannelHandler);
	}
	/**
	 * 将ListView中的数据全部删除
	 */
	private void removeAllListData() {
//		List<ChannelBean> items = new ArrayList<ChannelBean>();
//		CommonListViewAdapter adapter = new CommonListViewAdapter(items);
//		adapter.setItems(items);
		channelSearchListViewAdapter.getItems().clear();
		channelSearchListViewAdapter.notifyDataSetChanged();
//		channelListView.setAdapter(adapter);
	}
	
	@Override
	public void initChannelData(String result, Handler handler) {
//		jsonObject = new JSONObject(result);
		jsonObject = JsonUtil.initJsonObject(result);
		initChannelDataListView(jsonObject, handler);
	}
	
	@Override
	public void getMyChannel(String result, Handler handler) {
		JSONArray ja = JsonUtil.initJsonArray(result);
		initMyChannelDataListView(ja, handler);
//		JSONArray ja = new JSONArray(result);
//		initMyChannelDataListView(ja, handler);
	}
	
	/**
	 * 将接口返回的数据存入到ListView中
	 * @param jsonObject
	 * @throws JSONException
	 */
	private void initChannelDataListView(JSONObject jsonObject, Handler handler) {
//		JSONArray jsonArray = jsonObject.getJSONArray("children");
		JSONArray jsonArray = JsonUtil.getJSONArray(jsonObject, ParamConst.CHANNEL_CHILDREN);
		canClickToChild = true;
		initDataListView(jsonArray, handler);
	}
	
	private void initMyChannelDataListView(JSONArray jsonArray, Handler handler) {
		canClickToChild = false;
		initDataListView(jsonArray, handler);
	}
	
	private void initDataListView(JSONArray jsonArray, Handler handler) {
		List<ChannelBean> items = setDataToItems(jsonArray);
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
	 * 获取所有的子频道集合
	 * @param jsonArray
	 * @return
	 */
	private List<ChannelBean> setDataToItems(JSONArray jsonArray) {
		int length = jsonArray.length();
		List<ChannelBean> channelBeans = new ArrayList<ChannelBean>();
		for (int i = 0; i < length; i++) {
//			JSONObject jo = jsonArray.getJSONObject(i);
			JSONObject jo = JsonUtil.getJSONObject(jsonArray, i);
			ChannelBean channelBean = (ChannelBean) BeanParamsUtil.saveJsonToObject(jo, ChannelBean.class);
			channelBeans.add(channelBean);
		}
		return channelBeans;
	}
	
	/**
	 * 获取当前登录用户对应的频道值（原逻辑为：获取传入该activity的新闻ID和对应的父ID）
	 */
	private void getCurCheckedChannelId() {
		curCheckChannelId = channelId = channelBean.getChannelId();
		curCheckChannelName = channelName = channelBean.getChannelName();
		parentChannelId = channelBean.getParentId();
		
		/*Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras.containsKey(ParamConst.CUR_CHANNEL_ID) && extras.containsKey(ParamConst.CUR_CHANNEL_ID_PARENT_ID)) {
			channelId = extras.getLong(ParamConst.CUR_CHANNEL_ID);
			parentChannelId = extras.getLong(ParamConst.CUR_CHANNEL_ID_PARENT_ID);
		}*/
	}
	
	/**
	 * 重置频道目录
	 * @param useCurChannelName 判断是否在目录的最后用的当前频道的频道名
	 * @throws JSONException
	 */
	public void resetCurChannelSearchCheckedText(boolean useCurChannelName) {
		self = JsonUtil.getJSONObject(jsonObject, ParamConst.CHANNEL_SELF);
		channelName = getChannelContent();
		channelSearchCheckedText.setText(channelName);
		parentChannelId = JsonUtil.getLong(self, ParamConst.CHANNEL_PARENT_ID);
	}
	
	/**
	 * 将接口返回的频道数据进行处理，最后生成频道目录
	 * 目录的最后一个频道根据传入的boolean值进行变化：true表示使用当前选中的频道名（当为true的时候，channelName不能为空）；false表示使用父频道的频道名
	 * @param useCurChannelName
	 * @return
	 * @throws JSONException
	 */
	private String getChannelContent() {
		if (channelNamesTree == null) {
//			channelNamesTree = Arrays.asList(StaticUtil.getChannelNamesTree(this));
			String[] names = StaticUtil.getChannelNamesTree(this);
			int length = names.length - 1;
			
			channelNamesTree = new ArrayList<String>();
			for (int i = 0; i < length; i++) {
				channelNamesTree.add(names[i]);
			}
//			channelNamesTree.remove(channelNamesTree.size() - 1);
		} else {
			if (isBackToParent) {
				channelNamesTree.remove(channelNamesTree.size() - 1);
			} else {
				channelNamesTree.add(JsonUtil.getString(self, ParamConst.CHANNEL_NAME));
			}
		}
		int length = channelNamesTree.size();
		String lastDeptName = channelNamesTree.get(length - 1);
		if (length == 1) {
			channelName = substrText(channelNamesTree.get(0), newsTitleAllowLength);
		} else if (length == 2) {
			channelName = substrText(channelNamesTree.get(0), newsTitleAllowLength / 2 - 1) + "/" + substrText(lastDeptName, newsTitleAllowLength / 2 - 1);
		} else {
			channelName = substrText(channelNamesTree.get(0), newsTitleAllowLength / 2 - 3) + "/../" + substrText(lastDeptName, newsTitleAllowLength / 2 - 3);
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
	
	@Override
	public ChannelSearchActivity getActivity() {
		return this;
	}
	
	@Override
	public void error(Exception e) {
		Log.e("进入error()方法", e.toString());
		e.printStackTrace();
	}
	
	@Override
	public void finallyExec() {
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				AnimUtil.hideRefreshFrame();
			}
		}.sendEmptyMessage(0);
	}
	
	@Override
	public void onFailure(Exception e) {
		Log.e("ChannelSearchActivity onFailure", "服务器异常:" + e.toString());
		Message message = new Message();
		String errorMsg = e.toString();
		if (errorMsg == null) {
			errorMsg = "服务器异常";
		}
		message.what = ParamConst.MESSAGE_WHAT_ERROR;
		message.obj = errorMsg;
		allChannelHandler.sendMessage(message);
	}
	
	public NewsListListView getChannelListView() {
		return channelListView;
	}

	public void setChannelListView(NewsListListView channelListView) {
		this.channelListView = channelListView;
	}

	public List<String> getAllChooseChannelName() {
		return allChooseChannelName;
	}

	public void setAllChooseChannelName(List<String> allChooseChannelName) {
		this.allChooseChannelName = allChooseChannelName;
	}

	public int getCurChannelListEnableView() {
		return curChannelListEnableView;
	}

	public void setCurChannelListEnableView(int curChannelListEnableView) {
		this.curChannelListEnableView = curChannelListEnableView;
	}

	public TextView getCurChooseChannelTV() {
		return curChooseChannelTV;
	}

	public void setCurChooseChannelTV(TextView curChooseChannelTV) {
		this.curChooseChannelTV = curChooseChannelTV;
	}

	public String getCurChooseChannelType() {
		return curChooseChannelType;
	}

	public void setCurChooseChannelType(String curChooseChannelType) {
		this.curChooseChannelType = curChooseChannelType;
	}

	public CommonPopupWindow getPopupWindow() {
		return popupWindow;
	}

	public void setPopupWindow(CommonPopupWindow popupWindow) {
		this.popupWindow = popupWindow;
	}

	public Long getCurCheckChannelId() {
		return curCheckChannelId;
	}

	public void setCurCheckChannelId(Long curCheckChannelId) {
		this.curCheckChannelId = curCheckChannelId;
	}

	public String getCurCheckChannelName() {
		return curCheckChannelName;
	}

	public void setCurCheckChannelName(String curCheckChannelName) {
		this.curCheckChannelName = curCheckChannelName;
	}

	public boolean isFirstEnter() {
		return isFirstEnter;
	}

	public void setFirstEnter(boolean isFirstEnter) {
		this.isFirstEnter = isFirstEnter;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Long getParentChannelId() {
		return parentChannelId;
	}

	public void setParentChannelId(Long parentChannelId) {
		this.parentChannelId = parentChannelId;
	}

	public boolean isCanClickToChild() {
		return canClickToChild;
	}

	public void setCanClickToChild(boolean canClickToChild) {
		this.canClickToChild = canClickToChild;
	}

	public ChannelSearchListViewAdapter getChannelSearchListViewAdapter() {
		return channelSearchListViewAdapter;
	}

	public void setChannelSearchListViewAdapter(ChannelSearchListViewAdapter channelSearchListViewAdapter) {
		this.channelSearchListViewAdapter = channelSearchListViewAdapter;
	}

	public SearchChannelFilterAdapter getSearchChannelFilterAdapter() {
		return searchChannelFilterAdapter;
	}

	public void setSearchChannelFilterAdapter(SearchChannelFilterAdapter searchChannelFilterAdapter) {
		this.searchChannelFilterAdapter = searchChannelFilterAdapter;
	}

	public List<String> getChannelNamesTree() {
		return channelNamesTree;
	}

	public void setChannelNamesTree(List<String> channelNamesTree) {
		this.channelNamesTree = channelNamesTree;
	}

	public boolean isBackToParent() {
		return isBackToParent;
	}

	public void setBackToParent(boolean isBackToParent) {
		this.isBackToParent = isBackToParent;
	}

	public AutoCompleteTextView getSearchChannelET() {
		return searchChannelET;
	}

	public void setSearchChannelET(AutoCompleteTextView searchChannelET) {
		this.searchChannelET = searchChannelET;
	}

	public boolean isClickET() {
		return isClickET;
	}

	public void setClickET(boolean isClickET) {
		this.isClickET = isClickET;
	}
	
	
}
