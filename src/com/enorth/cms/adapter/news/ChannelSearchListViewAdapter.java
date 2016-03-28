package com.enorth.cms.adapter.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.bean.news_list.NewsListBean;
import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.common.EnableSimpleChangeTextView;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.listener.imageview.ImageViewOnTouchListener;
import com.enorth.cms.listener.newslist.ListViewItemOnTouchListener;
import com.enorth.cms.listener.newslist.news.CheckBtnOnCheckedChangeListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.TimeUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.news.ChannelSearchActivity;
import com.enorth.cms.view.news.NewsCommonActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChannelSearchListViewAdapter extends BaseAdapter {
	
	private List<ChannelBean> items;
	
	private ChannelSearchActivity activity;
	/**
	 * 如果此参数为空时，在点击选中的情况下将选中的view存入此参数中，并将选中的CheckBox的tag设为true
	 * 如果此参数不为空时，则通过findViewByTag查询是否存在选中的view
	 * 	如果为null，则说明已经回收，直接将当前选中的存入此参数中即可
	 * 	如果不为null，则说明选中的view还在，将之前选中的CheckBox设置为false，并将当前选中的存入此参数中
	 * 	
	 */
	private View view;
	
	public ChannelSearchListViewAdapter(List<ChannelBean> items, ChannelSearchActivity activity) {
		this.items = items;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public ChannelBean getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getChannelId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(activity).inflate(R.layout.channel_search_item, null);
			holder.checkBtn = (CheckBox) convertView.findViewById(R.id.iv_check_btn);
			holder.channelNameTV = (TextView) convertView.findViewById(R.id.tv_news_title);
			holder.channelNext = (ImageView) convertView.findViewById(R.id.iv_news_next);
			
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		ChannelBean channelBean = getItem(position);
		holder.channelNameTV.setText(channelBean.getChannelName());
		
		if (channelBean.getHasChildren() == ParamConst.HAS_CHILDREN_NO) {
			holder.channelNext.setVisibility(View.GONE);
		} else if (channelBean.getHasChildren() == ParamConst.HAS_CHILDREN_YES) {
			holder.channelNext.setVisibility(View.VISIBLE);
		}
		
		// 将CheckBox的状态进行初始化
		initCheckBtnState(channelBean, holder);
		// 初始化CheckBox的点击事件
		addCheckBtnClickEvent(channelBean, holder, convertView);
		// 添加ListView的item的点击事件
		addListViewItemClickEvent(channelBean, holder, convertView);
		return convertView;
	}

	public List<ChannelBean> getItems() {
		return items;
	}

	public void setItems(List<ChannelBean> items) {
		this.items = items;
	}
	
	/**
	 * 向ListView中的每一个item存值
	 * @param jsonArray
	 * @param canClick 判断是否可以显示可点击进入下一级频道的标识
	 * @return
	 * @throws JSONException
	 */
	/*private synchronized List<View> setDataToItems(JSONArray jsonArray, boolean canClick) throws JSONException {
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
//			saveShortNames(jo.getString("pinyin"), ivBean);
//			listViewItem.add(ivBean);
			views.add(layout);
		}
		return views;
	}*/
	
	/**
	 * 初始化左侧按钮的状态
	 * @param bean
	 */
	private void initCheckBtnState(ChannelBean channelBean, Holder holder) {
		// 如果是第一次进入，则说明是将当前频道ID的父ID传入接口中，所以要将当前频道ID进行勾选操作
		if (activity.isFirstEnter()) {
			if (channelBean.getChannelId().equals(activity.getChannelId())) {
//				bean.getImageView().setImageResource(bean.getImageCheckedResource());
				holder.checkBtn.setChecked(true);
				activity.setCurCheckChannelId(channelBean.getChannelId());
				activity.setCurCheckChannelName(channelBean.getChannelName());				
				activity.setParentChannelId(channelBean.getParentId());
			} else {
				holder.checkBtn.setChecked(false);
			}
		} else {
			holder.checkBtn.setChecked(false);
		}
	}
	
	/**
	 * 给每一个频道左侧的选中图标添加点击事件
	 * @param bean
	 */
	private void addCheckBtnClickEvent(final ChannelBean channelBean, final Holder holder, final View view/*final NewsListImageViewBasicBean bean*/) {
		
		OnTouchListener listViewCheckBtnOnTouchListener = new ImageViewOnTouchListener(activity) {
			@Override
			public boolean onImgChangeBegin(View v) {
				checkChannel(holder, view);
				return true;
			}

			@Override
			public void onImgChangeEnd(View v) {
				if (!holder.checkBtn.isChecked()) {
					activity.setCurCheckChannelId(-1L);
					activity.setCurCheckChannelName("");
				} else {
					activity.setCurCheckChannelId(channelBean.getChannelId());
					activity.setCurCheckChannelName(channelBean.getChannelName());
				}
			}

			@Override
			public void onTouchBegin() {
				// 将焦点定位到搜索栏中
				activity.setCurChannelListEnableView(ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY);
			}
		};
		holder.checkBtn.setOnTouchListener(listViewCheckBtnOnTouchListener);
	}
	
	/**
	 * 给ListView中的item添加点击事件（点击搜索下一级）
	 * @param bean
	 */
	private void addListViewItemClickEvent(final ChannelBean channelBean, final Holder holder, final View view) {
		CommonOnTouchListener listViewItemOnTouchListener = new ListViewItemOnTouchListener(activity) {
			@Override
			public void onImgChangeDo(View v) {
				try {
					channelClick(channelBean, holder, view);
				} catch (Exception e) {
					activity.error(e);
				}
			}
			
			@Override
			public boolean isClickBackgroungColorChange() {
				return false;
			}

			@Override
			public void onTouchBegin() {
				// 将焦点定位到搜索栏中
				activity.setCurChannelListEnableView(ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY);
				
			}
		};
		listViewItemOnTouchListener.changeColor(R.color.bg_gray_press, R.color.bg_gray_default);
		view.setOnTouchListener(listViewItemOnTouchListener);
		/*CommonOnClickListener listViewItemOnClickListener = new CommonOnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					channelClick(bean);
				} catch (Exception e) {
					error(e);
				}
			}
		};
		bean.getView().setOnClickListener(listViewItemOnClickListener);*/
	}
	
	private void channelClick(ChannelBean channelBean, Holder holder, View view/*NewsListImageViewBasicBean bean*/) throws Exception {
		if (channelBean.getHasChildren() == ParamConst.HAS_CHILDREN_YES) {
			activity.setChannelId(channelBean.getChannelId());
			activity.setChannelName(channelBean.getChannelName());
			activity.initChannelDefaultData();
		} else {
			activity.setCurCheckChannelId(channelBean.getChannelId());
			activity.setCurCheckChannelName(channelBean.getChannelName());
			checkChannel(holder, view);
		}
	}
	
	/**
	 * 选中频道
	 * @param holder
	 * @param view
	 */
	public void checkChannel(Holder holder, View view) {
		if (holder.checkBtn.isChecked()) {
			this.view = null;
			holder.checkBtn.setChecked(false);
		} else {
			holder.checkBtn.setChecked(true);
			holder.checkBtn.setTag(true);
			if (this.view != null) {
				View checkedView = this.view.findViewWithTag(true);
				if (checkedView != null) {
					CheckBox checkBtn = (CheckBox) checkedView;
					checkBtn.setChecked(false);
				}
			}
			this.view = view;
		}
	}
	
	class Holder {
		/**
		 * 新闻左侧的点击图标
		 */
		CheckBox checkBtn;
		/**
		 * 频道名称
		 */
		TextView channelNameTV;
		/**
		 * 标识是否存在子频道的图标
		 */
		ImageView channelNext;
	}
}
