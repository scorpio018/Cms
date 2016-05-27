package com.enorth.cms.adapter.news;

import java.util.List;

import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.listener.CommonOnItemClickListener;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.listener.imageview.ImageViewOnTouchListener;
import com.enorth.cms.listener.newslist.ListViewItemOnTouchListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.DrawableUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.news.ChannelSearchActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

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
	
	private ChannelBean channelBean;
	
	/**
	 * 在点击搜索框时会出现ListView中的item找不到父类的情况，所以当needRefresh为true是，要调用notifyDataSetChanged()方法
	 */
//	private boolean needRefresh = false;
	
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
			holder.newsTextLayout = (RelativeLayout) convertView.findViewById(R.id.newsTextLayout);
			holder.channelNameTV = (TextView) convertView.findViewById(R.id.tv_news_title);
			holder.channelNext = (ImageView) convertView.findViewById(R.id.iv_news_next);
			
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		ChannelBean channelBean = getItem(position);
		if (channelBean.isChecked()) {
			convertView.setBackgroundColor(ColorUtil.getBlueBgColor(activity));
			holder.channelNameTV.setTextColor(ColorUtil.getCommonBlueColor(activity));
		} else {
			convertView.setBackgroundColor(ColorUtil.getWhiteColor(activity));
			holder.channelNameTV.setTextColor(ColorUtil.getBlack(activity));
		}
		holder.channelNameTV.setText(channelBean.getChannelName());
		
		if (channelBean.getHasChildren() == ParamConst.HAS_CHILDREN_NO) {
			holder.channelNext.setVisibility(View.GONE);
		} else if (channelBean.getHasChildren() == ParamConst.HAS_CHILDREN_YES) {
			holder.channelNext.setVisibility(View.VISIBLE);
		}
		
		// 添加ListView的item的点击事件
		addListViewItemClickEvent(channelBean, holder, convertView, position);
		// 添加ListView的item右侧箭头的点击事件
		addChannelNextClickEvent(channelBean, holder, convertView, position);
		return convertView;
	}
	
	/**
	 * 给ListView中的item添加点击事件（点击搜索下一级）
	 * @param bean
	 */
	private void addListViewItemClickEvent(final ChannelBean channelBean, final Holder holder, final View view, final int position) {
		new CommonOnClickListener(holder.newsTextLayout, false, 0) {
			
			@Override
			public void onClick(View v) {
				// 将焦点定位到搜索栏中
				activity.setCurChannelListEnableView(ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY);
				channelClick(channelBean, holder, view, position);
			}
		};
	}
	
	private void addChannelNextClickEvent(final ChannelBean channelBean, final Holder holder, final View view, final int position) {
		new CommonOnClickListener(holder.channelNext, false, 0) {
			
			@Override
			public void onClick(View v) {
				// 将焦点定位到搜索栏中
				activity.setCurChannelListEnableView(ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_CHANNEL_SEARCH_ACTIVITY);
				activity.setChannelId(channelBean.getChannelId());
				activity.setChannelName(channelBean.getChannelName());
				activity.setBackToParent(false);
				activity.initChannelDefaultData();
			}
		};
	}
	
	private void channelClick(ChannelBean channelBean, Holder holder, View view, int position) {
		setChannelBean(channelBean);
		activity.confirmChannel(channelBean.getChannelId(), channelBean.getChannelName(), channelBean.getParentId());
	}
	
	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public ChannelBean getChannelBean() {
		return channelBean;
	}

	public void setChannelBean(ChannelBean channelBean) {
		this.channelBean = channelBean;
	}
	
	public List<ChannelBean> getItems() {
		return items;
	}

	public void setItems(List<ChannelBean> items) {
		this.items = items;
		this.view = null;
		this.channelBean = null;
	}

	class Holder {
		/**
		 * item
		 */
		RelativeLayout newsTextLayout;
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
