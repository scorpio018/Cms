package com.enorth.cms.adapter.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.enorth.cms.adapter.SearchCommonFilterAdapter;
import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.filter.news.SearchChannelFilter;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.listener.imageview.ImageViewOnTouchListener;
import com.enorth.cms.listener.newslist.ListViewItemOnTouchListener;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.news.ChannelSearchActivity;

import android.content.Context;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchChannelFilterAdapter extends SearchCommonFilterAdapter<Map<NewsListImageViewBasicBean, List<String>>> {
	
	private ChannelSearchActivity activity;
	
	private SearchChannelFilterAdapter thisAdapter;
	
	private List<NewsListImageViewBasicBean> listViewItem;
	
	private long curCheckChannelId = -1L;
	
	private String curCheckChannelName = "";
	
	public SearchChannelFilterAdapter(ChannelSearchActivity activity, int textViewResourceId, List<Map<NewsListImageViewBasicBean, List<String>>> objects) {
		super(activity, textViewResourceId, objects);
		this.activity = activity;
		this.thisAdapter = this;
	}
	
	@Override
	public void doAfterFilterNewValues(List<Map<NewsListImageViewBasicBean, List<String>>> values) {
		listViewItem = new ArrayList<NewsListImageViewBasicBean>();
		for (Map<NewsListImageViewBasicBean, List<String>> map : values) {
			NewsListImageViewBasicBean bean = ViewUtil.getNewsListImageViewBasicBean(map, context);
			listViewItem.add(bean);
		}
	}

	@Override
	public void initFilter(Context context, List<Map<NewsListImageViewBasicBean, List<String>>> objects, Object lock, List<Map<NewsListImageViewBasicBean, List<String>>> originalValues) {
		filter = new SearchChannelFilter(context, objects, lock, originalValues, this);
		/*new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
				thisAdapter.filter = new SearchChannelFilter(context, objects, lock, originalValues, thisAdapter);
			};
		}.sendEmptyMessage(0);*/
	}

	@Override
	public Object getItem(int position) {
		TextView text = null;
		try {
//			text = (TextView) view.findViewById(R.id.channel_search_item_tv);
			text = (TextView) curView.findViewById(R.id.tv_news_title);
		} catch (ClassCastException e) {
			throw new IllegalStateException("公共搜索适配器无法将该resourceId[" + resource + "]转换成TextView", e);
		}
		Map<NewsListImageViewBasicBean, List<String>> object = objects.get(position);
		NewsListImageViewBasicBean bean = ViewUtil.getNewsListImageViewBasicBean(object, context);
		if (bean == null) {
			return null;
		}
		ImageView next = (ImageView) curView.findViewById(R.id.iv_news_next);
		if (bean.isHasChild()) {
			next.setVisibility(View.VISIBLE);
		} else {
			next.setVisibility(View.GONE);
		}
		text.setText(bean.getName());
		ImageView checkBtn = (ImageView) curView.findViewById(R.id.iv_check_btn);
		if (bean.getId().equals(String.valueOf(activity.channelId)) || bean.getId().equals(String.valueOf(activity.curCheckChannelId))) {
			checkBtn.setImageResource(bean.getImageCheckedResource());
			checkBtn.setSelected(true);
		} else {
			checkBtn.setImageResource(bean.getImageUncheckResource());
		}
		addCheckBtnClickEvent(bean, checkBtn);
		return null;
	}
	
	private void addCheckBtnClickEvent(final NewsListImageViewBasicBean bean, final ImageView checkBtn) {
		OnTouchListener listViewCheckBtnOnTouchListener = new ImageViewOnTouchListener(bean, ScreenTools.getTouchSlop(activity)) {
			@Override
			public boolean onImgChangeBegin() {
				checkChannel(bean, checkBtn);
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
		};
		checkBtn.setOnTouchListener(listViewCheckBtnOnTouchListener);
	}
	
	/**
	 * 给ListView中的item添加点击事件（点击搜索下一级）
	 * @param bean
	 */
	private void addListViewItemClickEvent(final NewsListImageViewBasicBean bean, final ImageView checkBtn) {
		CommonOnTouchListener listViewItemOnTouchListener = new ListViewItemOnTouchListener(ScreenTools.getTouchSlop(activity)) {
			@Override
			public void onImgChangeDo() {
				channelClick(bean, checkBtn);
			}
		};
		listViewItemOnTouchListener.changeColor(R.color.bg_gray_press, R.color.bg_gray_default);
		bean.getView().setOnTouchListener(listViewItemOnTouchListener);
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
	
	public void checkChannel(NewsListImageViewBasicBean bean, ImageView checkBtn) {
		if (!bean.getImageView().isSelected()) {
			for (NewsListImageViewBasicBean b : listViewItem) {
				if (b.getImageView().isSelected()) {
					checkBtn.setImageResource(bean.getImageUncheckResource());
					checkBtn.setSelected(false);
					break;
				}
			}
		}
	}
	
	private void channelClick(NewsListImageViewBasicBean bean, ImageView checkBtn) {
		if (bean.isCanClick()) {
			activity.channelId = Long.parseLong(bean.getId());
			activity.channelName = bean.getName();
			activity.initChannelDefaultData();
		} else {
			curCheckChannelId = Long.parseLong(bean.getId());
			curCheckChannelName = bean.getName();
			checkChannel(bean, checkBtn);
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
}
