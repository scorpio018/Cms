package com.enorth.cms.adapter.news;

import java.util.List;

import com.enorth.cms.adapter.SearchCommonFilterAdapter;
import com.enorth.cms.adapter.news.ChannelSearchListViewAdapter.Holder;
import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.filter.news.SearchChannelFilter;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.listener.imageview.ImageViewOnTouchListener;
import com.enorth.cms.listener.newslist.ListViewItemOnTouchListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.DrawableUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.news.ChannelSearchActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SearchChannelFilterAdapter extends SearchCommonFilterAdapter<ChannelBean> {
	
	private ChannelSearchActivity activity;
	
//	private long curCheckChannelId = -1L;
	
//	private String curCheckChannelName = "";
	
//	private long parentChannelId = -1L;
	
	private View view;
	
	private ChannelBean channelBean;
	
//	private int checkedPosition = -1;
	
	private Drawable left;
	
	private Drawable right;
	
	public SearchChannelFilterAdapter(ChannelSearchActivity activity, int textViewResourceId, List<ChannelBean> objects) {
		super(activity, textViewResourceId, objects);
		this.activity = activity;
		/*for (ChannelBean item : objects) {
			if (item.getChannelId().equals(activity.getCurCheckChannelId())) {
				this.channelBean = item;
				break;
			}
		}*/
		init();
	}
	
	private void init() {
		left = DrawableUtil.getDrawable(activity, R.drawable.edit_text_search);
		right = DrawableUtil.getDrawable(activity, R.drawable.edit_text_cross);
	}

	@Override
	public void initFilter(Context context, List<ChannelBean> objects, Object lock, List<ChannelBean> originalValues) {
		filter = new SearchChannelFilter(context, objects, lock, originalValues, this);
	}
	
	@Override
	public View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(resource, null);
//			holder.checkBtn = (CheckBox) convertView.findViewById(R.id.iv_check_btn);
			holder.newsTextLayout = (RelativeLayout) convertView.findViewById(R.id.newsTextLayout);
			holder.channelNameTV = (TextView) convertView.findViewById(R.id.tv_news_title);
			holder.channelNext = (ImageView) convertView.findViewById(R.id.iv_news_next);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		ChannelBean channelBean = super.getItems().get(position);
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
		// 添加ListView的item右侧的按钮点击事件
		addChannelNextClickEvent(channelBean, holder, convertView, position);
		return convertView;
	}
	
	@Override
	public Object getItem(int position) {
		return super.getItems().get(position);
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
				activity.setCurChannelListEnableView(ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_AUTO_COMPLETE_TEXT_VIEW);
				channelClick(channelBean, holder, view, position);
			}
		};
	}
	
	private void addChannelNextClickEvent(final ChannelBean channelBean, final Holder holder, final View view, final int position) {
		new CommonOnClickListener(holder.channelNext, false, 0) {
			
			@Override
			public void onClick(View v) {
				// 将焦点定位到搜索栏中
				activity.setCurChannelListEnableView(ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_AUTO_COMPLETE_TEXT_VIEW);
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
	
	@Override
	public void initDrawable() {
		activity.getSearchChannelET().setCompoundDrawablesWithIntrinsicBounds(left, null, right, null);
	}
	
	@Override
	public void removeDrawable() {
		activity.getSearchChannelET().setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
	}
	
	public ChannelBean getChannelBean() {
		return channelBean;
	}

	public void setChannelBean(ChannelBean channelBean) {
		this.channelBean = channelBean;
	}

	class Holder {
		/**
		 * 新闻左侧的点击图标
		 */
//		CheckBox checkBtn;
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
