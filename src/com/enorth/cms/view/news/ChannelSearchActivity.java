package com.enorth.cms.view.news;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.enorth.cms.adapter.news.NewsListViewAdapter;
import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.handler.newslist.ChannelSearchHandler;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.listener.imageview.ImageViewOnTouchListener;
import com.enorth.cms.listener.newslist.ListViewItemOnTouchListener;
import com.enorth.cms.listener.newslist.subtitle.ChooseChannelTypeOnTouchListener;
import com.enorth.cms.listener.popup.PopupWindowOnTouchListener;
import com.enorth.cms.presenter.newslist.ChannelSearchPresenter;
import com.enorth.cms.presenter.newslist.IChannelSearchPresenter;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.StringUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.widget.listview.newslist.NewsListListView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.EditText;
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
	 * 屏幕认定滑动的最大位移
	 */
	private int touchSlop;
	
	private Handler handler;
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
	 * 当前已选择的频道ID
	 */
	private Long channelId = 0L;
	/**
	 * 当前已选择的频道名称
	 */
	private String channelName;
	
//	private List<Long> channelIdSearchRecord;
	/**
	 * 当前选中的频道ID（左侧对勾按钮选中状态时存入，在选择新的频道时清空）
	 */
	public Long curCheckChannelId = -1L;
	/**
	 * 当前选中的频道名称（左侧对勾按钮选中状态时存入，在选择新的频道时清空）
	 */
	public String curCheckChannelName = "";
	
	private EditText searchChannelET;
	/**
	 * 标题中间的频道类型layout
	 */
	private LinearLayout channelSearchTitleLayout;
	
	private TextView curChooseChannelTV;
	/**
	 * 挡圈选择的标题频道类别
	 */
	private String curChooseChannelName;
	
	private TextView channelSearchCheckedText;
	/**
	 * 所有可选的频道（目前只包括全部频道、我的频道两种）
	 */
	private List<String> allChooseChannelName;
	/**
	 * 标头的全部频道选择弹出
	 */
	private PopupWindow popupWindow;
	
	private JSONObject jsonObject;
	
	private int channelPopupColor;
	
	private IChannelSearchPresenter presenter;
	
	private List<NewsListImageViewBasicBean> listViewItem = new ArrayList<NewsListImageViewBasicBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_search);
		initBaseData();
		initTitleEvent();
		try {
			initChannelDefaultData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initBaseData() {
		thisActivity = this;
		presenter = new ChannelSearchPresenter(this);
		touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
		channelListView = (NewsListListView) findViewById(R.id.childChannelListView);
		channelSearchCheckedText = (TextView) findViewById(R.id.channelSearchCheckedText);
		// 获取屏幕的分辨率
		Display display = thisActivity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		phoneWidth = size.x;
		phoneHeight = size.y;
		View backView = findViewById(R.id.channelSearchTitle);
		View channelSearchNoChangeText1 = findViewById(R.id.channelSearchNoChangeText1);
		// 此处要减去该按钮的宽度，防止频道覆盖按钮
		newsTitleAllowLength = (phoneWidth - channelSearchNoChangeText1.getMeasuredWidth() - backView.getMeasuredWidth()) / ParamConst.FONT_WIDTH;
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
		handler = new ChannelSearchHandler(this);
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
		int height = (int) ((phoneHeight - titleHeight - subTitleHeight - operateBtnHeight) / 2 + titleHeight + subTitleHeight);
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
		ImageView back = (ImageView) findViewById(R.id.channelSearchTitleBackBtn);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				thisActivity.onBackPressed();
			}
		});
	}
	
	private void initChooseChannelEvent() {
		channelSearchTitleLayout = (LinearLayout) findViewById(R.id.channelSearchTitleLayout);
		curChooseChannelTV = (TextView) findViewById(R.id.channelSearchTitleText);
		curChooseChannelName = SharedPreUtil.getString(thisActivity, ParamConst.CUR_CHOOSE_CHANNEL_TYPE);
		if (StringUtil.isEmpty(curChooseChannelName)) {
			SharedPreUtil.put(thisActivity, ParamConst.CUR_CHOOSE_CHANNEL_TYPE, ParamConst.ALL_CHANNEL);
			curChooseChannelName = ParamConst.ALL_CHANNEL;
		}
		curChooseChannelTV.setText(curChooseChannelName);
		ChooseChannelTypeOnTouchListener listener = new ChooseChannelTypeOnTouchListener(touchSlop) {
			
			@Override
			public void onImgChangeEnd() {
				
			}
			
			@Override
			public void onImgChangeDo() {
				getChooseChannelPopupWindow(curChooseChannelName);
			}
		};
		listener.changeColor(R.color.bottom_text_color_green, R.color.common_blue);
		channelSearchTitleLayout.setOnTouchListener(listener);
		/*curChooseChannelTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getChooseChannelPopupWindow(curChooseChannelName);
				int titleHeight = (int)getResources().getDimension(R.dimen.news_title_height);
				int xPos = phoneWidth / 2 - popupWindow.getWidth() / 2;
				int sdk = android.os.Build.VERSION.SDK_INT;
				if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
					popupWindow.showAtLocation(v, Gravity.TOP, xPos, titleHeight);
//					popupWindow.showAtLocation(channelSearchTitleLayout, Gravity.TOP, 0, titleHeight);
				} else {
					popupWindow.showAsDropDown(v, xPos, 0);
				}
			}
		});*/
	}
	
	/**
	 * 如果弹出框处于激活状态，则将弹出框销毁，泛指则实例化弹出页面
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
		LayoutInflater inflater = LayoutInflater.from(thisActivity);
		int size = allChooseChannelName.size();
		for (int i = 0; i < size; i++) {
//			LinearLayout chooseChannelItem = (LinearLayout) inflater.inflate(R.layout.choose_channel_popup, null);
			RelativeLayout chooseChannelItem = (RelativeLayout) inflater.inflate(R.layout.choose_channel_popup, null);
			TextView chooseChannelName = (TextView) chooseChannelItem.findViewById(R.id.chooseChannelName);
			final String curChannelName = allChooseChannelName.get(i);
			chooseChannelName.setText(curChannelName);
			final ImageView checkedIV = (ImageView) chooseChannelItem.getChildAt(0);
			if (curChooseChannelName.equals(curChannelName)) {
				checkedIV.setVisibility(View.VISIBLE);
			} else {
				checkedIV.setVisibility(View.GONE);
			}
			final int curPosition = i;
			PopupWindowOnTouchListener listener = new PopupWindowOnTouchListener(touchSlop) {
				
				@Override
				public void onImgChangeDo() {
//					checkedIV.setVisibility(View.VISIBLE);
					int childCount = layout.getChildCount();
					for (int j = 0; j < childCount; j++) {
						if (curPosition == j) {
							checkedIV.setVisibility(View.VISIBLE);
							curChooseChannelTV.setText(curChannelName);
							SharedPreUtil.put(thisActivity, ParamConst.CUR_CHOOSE_CHANNEL_TYPE, curChannelName);
							thisActivity.curChooseChannelName = curChannelName;
						} else {
							checkedIV.setVisibility(View.GONE);
						}
					}
				}
				
				@Override
				public void onImgChangeEnd() {
					popupWindow.dismiss();
					popupWindow = null;
				}
			};
			listener.changeColor(R.color.bottom_text_color_green, R.color.channel_popup_color);
			chooseChannelItem.setOnTouchListener(listener);
			layout.addView(chooseChannelItem);
		}
		popupWindow = new PopupWindow(layout, 200, ViewGroup.LayoutParams.WRAP_CONTENT);
		// 为了让popupWindow能够做到点击其他位置可以消失，需要加入如下代码
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		
		popupWindow.setAnimationStyle(R.style.AnimationFade);
		int titleHeight = (int)Math.round(getResources().getDimension(R.dimen.news_title_height));
		
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			popupWindow.showAtLocation(channelSearchTitleLayout, Gravity.TOP, 0, titleHeight);
		} else {
			popupWindow.showAsDropDown(channelSearchTitleLayout, 0, 0);
		}
	}
	
	/**
	 * 点击右上角的“完成”，即将当前选中的频道存入SharedPreferences并返回新闻列表页
	 */
	private void initConfirmEvent() {
		TextView confirm = (TextView) findViewById(R.id.channelTitleMenuConfirm);
		confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (curCheckChannelId == -1L) {
//					SharedPreUtil.put(thisActivity, ParamConst.CUR_CHANNEL_ID, channelId);
//					SharedPreUtil.put(thisActivity, ParamConst.CUR_CHANNEL_NAME, channelName);
					Toast.makeText(thisActivity, "请先选择一个频道", Toast.LENGTH_SHORT).show();
					return;
				} else {
					/*SharedPreferences.Editor edit = preCurChannel.edit();
					edit.putLong(ParamConst.CUR_CHANNEL_ID, curCheckChannelId);
					edit.putString(ParamConst.CUR_CHANNEL_NAME, curCheckChannelName);
					edit.commit();*/
					SharedPreUtil.put(thisActivity, ParamConst.CUR_CHANNEL_ID, curCheckChannelId);
					SharedPreUtil.put(thisActivity, ParamConst.CUR_CHANNEL_NAME, curCheckChannelName);
				}
				thisActivity.onBackPressed();
			}
		});
	}
	
	private void initEditTextEvent() {
		searchChannelET = (EditText) findViewById(R.id.channelSearchEdit);
		
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
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 将刚刚进入页面需要加载的频道列表和当前的频道的数据进行加载
	 * @throws Exception
	 */
	private void initChannelDefaultData() throws Exception {
//		initDefaultData(null);
		AnimUtil.showRefreshFrame(thisActivity);
		presenter.initChannelData(channelId, handler);
		/*List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("channelId", String.valueOf(channelId)));
		// 保存搜索记录
//		saveSearchData(channelId);
		Callback callback = new Callback() {
			
			@Override
			public void onResponse(Response r) {
				String resultString = null;
				try {
					resultString = HttpUtil.checkResponseIsSuccess(r);
					jsonObject = new JSONObject(resultString);
					initChannelDataListView(jsonObject, handler);
				} catch (Exception e) {
					Log.e("error", e.getMessage());
					AnimUtil.hideRefreshFrame(thisActivity);
					e.printStackTrace();
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
				} finally {
					AnimUtil.hideRefreshFrame(thisActivity);
				}
			}
		};
		HttpUtil.okPost(UrlConst.CHANNEL_SEARCH_POST_URL, params, callback);*/
	}
	
	@Override
	public void initChannelData(String result, Handler handler) throws Exception {
		jsonObject = new JSONObject(result);
		initChannelDataListView(jsonObject, handler);
	}
	
	@Override
	public void getMyChannel(String result, Handler handler) throws Exception {
		
	}
	
	@Override
	public void error(Exception e) {
		
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
		Message message = new Message();
		String errorMsg = e.getMessage();
		if (errorMsg == null) {
			errorMsg = "服务器异常";
		}
		message.what = ParamConst.MESSAGE_WHAT_ERROR;
		message.obj = errorMsg;
		handler.sendMessage(message);
	}
	
	/**
	 * 将接口返回的数据存入到ListView中
	 * @param jsonObject
	 * @throws JSONException
	 */
	private void initChannelDataListView(JSONObject jsonObject, Handler handler) throws JSONException {
		JSONArray jsonArray = jsonObject.getJSONArray("children");
		List<View> items = setDataToItems(jsonArray);
		if (items.size() == 0) {
//			msg.what = ParamConst.MESSAGE_WHAT_NO_DATA;
			handler.sendEmptyMessage(ParamConst.MESSAGE_WHAT_NO_DATA);
		} else {
			Message msg = new Message();
			msg.what = ParamConst.MESSAGE_WHAT_SUCCESS;
			msg.obj = items;
			handler.sendMessage(msg);
		}
//		CommonListViewAdapter adapter = new CommonListViewAdapter(items);
//		channelListView.setAdapter(adapter);
	}
	
	/*private void saveSearchData(Long deptId) {
		if (channelIdSearchRecord == null) {
			channelIdSearchRecord = new ArrayList<Long>();
		}
		channelIdSearchRecord.add(deptId);
	}*/
	
	/**
	 * 向ListView中的每一个item存值
	 * @param jsonArray
	 * @return
	 * @throws JSONException
	 */
	private List<View> setDataToItems(JSONArray jsonArray) throws JSONException {
		List<View> views = new ArrayList<View>();
 		LayoutInflater inflater = LayoutInflater.from(this);
		int length = jsonArray.length();
		for (int i = 0; i < length; i++) {
			// 将重要数据封装到bean中
			NewsListImageViewBasicBean ivBean = new NewsListImageViewBasicBean();
			LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.channel_search_item, null);
			TextView channelNameTv = (TextView) layout.findViewById(R.id.tv_news_title);
			JSONObject jo = jsonArray.getJSONObject(i);
			Long channelId = jo.getLong("deptId");
			String channelName = jsonArray.getJSONObject(i).getString("deptName");
			ivBean.setId(String.valueOf(channelId));
			ivBean.setName(channelName);
			channelNameTv.setText(channelName);
			ivBean.setView(layout);
			ImageView checkBtn = (ImageView) layout.findViewById(R.id.iv_check_btn);
			ivBean.setImageView(checkBtn);
			ImageView next = (ImageView) layout.findViewById(R.id.iv_news_next);
			boolean isHasChild = jo.getBoolean("hasChild");
			if (isHasChild) {
				next.setVisibility(View.VISIBLE);
				ivBean.setCanClick(true);
			} else {
				next.setVisibility(View.GONE);
				ivBean.setCanClick(false);
			}
			addCheckBtnClickEvent(ivBean);
			addListViewItemClickEvent(ivBean);
			listViewItem.add(ivBean);
			views.add(layout);
		}
		return views;
	}
	
	/**
	 * 给每一个频道左侧的选中图标添加点击事件
	 * @param bean
	 */
	private void addCheckBtnClickEvent(final NewsListImageViewBasicBean bean) {
		
		bean.setImageCheckedResource(R.drawable.check_btn);
		bean.setImageUncheckResource(R.drawable.uncheck_btn);
		bean.getImageView().setImageResource(bean.getImageUncheckResource());
		OnTouchListener listViewCheckBtnOnTouchListener = new ImageViewOnTouchListener(bean, touchSlop) {
			@Override
			public boolean onImgChangeBegin() {
				checkChannel(bean);
				return true;
			}

			@Override
			public void onImgChangeEnd() {
				curCheckChannelId = Long.parseLong(bean.getId());
				curCheckChannelName = bean.getName();
			}
		};
		bean.setOnTouchListener(listViewCheckBtnOnTouchListener);
		bean.getImageView().setOnTouchListener(listViewCheckBtnOnTouchListener);
	}
	
	private void checkChannel(NewsListImageViewBasicBean bean) {
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
		CommonOnTouchListener listViewItemOnTouchListener = new ListViewItemOnTouchListener(touchSlop) {
			@Override
			public void onImgChangeDo() {
//				Toast.makeText(thisActivity, "点击的新闻ID为【" + bean.getId() + "】", Toast.LENGTH_SHORT).show();
				try {
					channelClick(bean);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		listViewItemOnTouchListener.changeColor(R.color.bg_gray_press, R.color.bg_gray_default);
		bean.getView().setOnTouchListener(listViewItemOnTouchListener);
		CommonOnClickListener listViewItemOnClickListener = new CommonOnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					channelClick(bean);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		bean.getView().setOnClickListener(listViewItemOnClickListener);
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
		}
	}
	
	/**
	 * 获取存入SharedPreferences中的当前选中的频道信息
	 */
	private void getCurCheckedChannelId() {
		channelId = SharedPreUtil.getLong(thisActivity, ParamConst.CUR_CHANNEL_ID);
		if (channelId == -1L) {
			SharedPreUtil.resetChannelIdData(thisActivity);
			channelName = ParamConst.DEFAULT_CHANNEL_NAME;
		} else {
			channelName = SharedPreUtil.getString(thisActivity, ParamConst.CUR_CHANNEL_NAME);
		}
		channelSearchCheckedText.setText(channelName);
//		channelSearchCheckedText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
	}
	
	/**
	 * 重置“已选择”的频道名称
	 */
	public void resetCurChannelSearchCheckedText() throws JSONException {
		JSONArray ja = jsonObject.getJSONArray("channelContent");
		JSONObject jo = jsonObject.getJSONObject("curChannel");
		int length = ja.length();
//		int channelLevel = ja.getInt("deptLevel");
		channelName = jo.getString("deptName");
		JSONObject firstDept = ja.getJSONObject(0);
		JSONObject lastDept = ja.getJSONObject(length - 1);
		if (length == 1) {
			channelName = substrText(channelName, newsTitleAllowLength);
//			SharedPreUtil.put(thisActivity, ParamConst.ROOT_CHANNEL_NAME, channelName);
		} else if (length == 2) {
			channelName = substrText(firstDept.getString("deptName"), newsTitleAllowLength / 2 - 1) + "/" + substrText(lastDept.getString("deptName"), newsTitleAllowLength / 2 - 1);
//			channelName = SharedPreUtil.getString(thisActivity, ParamConst.ROOT_CHANNEL_NAME) + "/" + channelName;
		} else {
			channelName = substrText(firstDept.getString("deptName"), newsTitleAllowLength / 2 - 3) + "/../" + substrText(lastDept.getString("deptName"), newsTitleAllowLength / 2 - 3);
//			channelName = SharedPreUtil.getString(thisActivity, ParamConst.ROOT_CHANNEL_NAME, "") + "/.../" + channelName;
		}
		channelSearchCheckedText.setText(channelName);
		AnimUtil.hideRefreshFrame(thisActivity);
	}
	
	private String substrText(String text, int length) {
		if (text.length() > length) {
			return text.substring(0, length) + "..";
		} else {
			return text;
		}
	}
}
