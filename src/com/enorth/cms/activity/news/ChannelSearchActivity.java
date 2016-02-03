package com.enorth.cms.activity.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.enorth.cms.activity.R;
import com.enorth.cms.adapter.CommonListViewAdapter;
import com.enorth.cms.adapter.news.NewsListViewAdapter;
import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.consts.UrlConst;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.listener.imageview.ImageViewOnTouchListener;
import com.enorth.cms.listener.listview.ListViewItemOnClickListener;
import com.enorth.cms.listener.listview.ListViewItemOnTouchListener;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.HttpUtil;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.view.listview.newslist.NewsListListView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.AbsListView.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChannelSearchActivity extends Activity {
	
	private NewsListListView channelListView;
	
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
	
	private List<Long> channelIdSearchRecord;
	/**
	 * 当前选中的频道ID（左侧对勾按钮选中状态时存入，在选择新的频道时清空）
	 */
	private Long curCheckChannelId = 0L;
	/**
	 * 当前选中的频道名称（左侧对勾按钮选中状态时存入，在选择新的频道时清空）
	 */
	private String curCheckChannelName = "";
	
	private EditText editText;
	
	private TextView channelSearchCheckedText;
	
	private JSONObject jsonObject;
	
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
		initHandler();
		getCurCheckedChannelId();
	}
	
	private void initHandler() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				try {
					switch (msg.what) {
					case ParamConst.MESSAGE_WHAT_SUCCESS:
						final List<View> items = (List<View>) msg.obj;
						CommonListViewAdapter adapter = new CommonListViewAdapter(items);
						channelListView.setAdapter(adapter);
//						ViewUtil.setListViewHeightBasedOnChildren(newsListView);
					break;
					case ParamConst.MESSAGE_WHAT_NO_DATA:
						initDefaultData("没有数据");
					break;
					case ParamConst.MESSAGE_WHAT_ERROR:
						String errorMsg = (String) msg.obj;
						initDefaultData(errorMsg);
					break;
					default:
						initDefaultData("未知错误");
					break;
					}
					resetCurChannelSearchCheckedText();
					// 每次重新选择一个频道后都要进行清空，
					curCheckChannelId = 0L;
					curCheckChannelName = "";
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					AnimUtil.hideRefreshFrame(thisActivity);
				}
			}
		};
	}
	
	/**
	 * 加载默认页面
	 * @param errorHint
	 */
	private void initDefaultData(String errorHint) {
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
	
	/**
	 * 点击右上角的“完成”，即将当前选中的频道存入SharedPreferences并返回新闻列表页
	 */
	private void initConfirmEvent() {
		TextView confirm = (TextView) findViewById(R.id.channelTitleMenuConfirm);
		confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (curCheckChannelId == 0L) {
					/*SharedPreUtil.put(thisActivity, ParamConst.CUR_CHANNEL_ID, channelId);
					SharedPreUtil.put(thisActivity, ParamConst.CUR_CHANNEL_NAME, channelName);*/
					Toast.makeText(thisActivity, "请先选择一个频道", Toast.LENGTH_SHORT).show();
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
		editText = (EditText) findViewById(R.id.channelSearchEdit);
		
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
					channelId = jsonObject.getLong("parentId");
					int channelLevel = jsonObject.getInt("deptLevel");
					if (channelLevel == 0) {
						Toast.makeText(thisActivity, "当前已经是最上级", Toast.LENGTH_SHORT).show();
						return;
					}
					initChannelDefaultData();
				} catch (Exception e) {
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
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("deptId", String.valueOf(channelId)));
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
		HttpUtil.okPost(UrlConst.CHANNEL_SEARCH_POST_URL, params, callback);
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
	private void resetCurChannelSearchCheckedText() throws JSONException {
		int channelLevel = jsonObject.getInt("deptLevel");
		channelName = jsonObject.getString("deptName");
		
		if (channelLevel == 0 || channelLevel == 1) {
			channelName = substrText(channelName, newsTitleAllowLength);
			SharedPreUtil.put(thisActivity, ParamConst.ROOT_CHANNEL_NAME, channelName);
		} else if (channelLevel == 2) {
			channelName = substrText(SharedPreUtil.getString(thisActivity, ParamConst.ROOT_CHANNEL_NAME), newsTitleAllowLength / 2 - 1) + "/" + substrText(channelName, newsTitleAllowLength / 2 - 1);
//			channelName = SharedPreUtil.getString(thisActivity, ParamConst.ROOT_CHANNEL_NAME) + "/" + channelName;
		} else {
			channelName = substrText(SharedPreUtil.getString(thisActivity, ParamConst.ROOT_CHANNEL_NAME), newsTitleAllowLength / 2 - 3) + "/../" + substrText(channelName, newsTitleAllowLength / 2 - 3);
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
