package com.enorth.cms.adapter.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.enorth.cms.adapter.SearchCommonFilterAdapter;
import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.filter.news.SearchChannelFilter;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.listener.imageview.ImageViewOnTouchListener;
import com.enorth.cms.listener.newslist.ListViewItemOnTouchListener;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.news.ChannelSearchActivity;

import android.content.Context;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SearchChannelFilterAdapter extends SearchCommonFilterAdapter<Map<NewsListImageViewBasicBean, List<String>>> {
	
	private ChannelSearchActivity activity;
	
//	private SearchChannelFilterAdapter thisAdapter;
	
	private List<NewsListImageViewBasicBean> listViewItem;
	
	public long curCheckChannelId = -1L;
	
	public String curCheckChannelName = "";
	
	public long parentChannelId = -1L;
	/**
	 * 由于AutoCompleteTextView的搜索结果是以效率优先（即会回收看不到的item），不满足需求，所以需要手动保留items而不适用默认的getView
	 */
	public List<View> items;
	
	public SearchChannelFilterAdapter(ChannelSearchActivity activity, int textViewResourceId, List<Map<NewsListImageViewBasicBean, List<String>>> objects) {
		super(activity, textViewResourceId, objects);
		this.activity = activity;
//		this.thisAdapter = this;
	}
	
	@Override
	public void doAfterFilterNewValues(List<Map<NewsListImageViewBasicBean, List<String>>> values) {
		listViewItem = new ArrayList<NewsListImageViewBasicBean>();
		for (Map<NewsListImageViewBasicBean, List<String>> map : values) {
			NewsListImageViewBasicBean bean = ViewUtil.getNewsListImageViewBasicBean(map, context);
			listViewItem.add(bean);
		}
		// 每次刷新数据时将items清空
		items = new ArrayList<View>();
	}

	@Override
	public void initFilter(Context context, List<Map<NewsListImageViewBasicBean, List<String>>> objects, Object lock, List<Map<NewsListImageViewBasicBean, List<String>>> originalValues) {
		filter = new SearchChannelFilter(context, objects, lock, originalValues, this);
	}
	
	/**
	 * 将所有匹配结果都存入items中
	 * @param parent
	 * @param resource
	 */
	private void initItems(ViewGroup parent, int resource) {
		int size = objects.size();
		for (int i = 0; i < size; i++) {
			curView = inflater.inflate(resource, parent, false);
			Map<NewsListImageViewBasicBean, List<String>> object = objects.get(i);
			NewsListImageViewBasicBean bean = ViewUtil.getNewsListImageViewBasicBean(object, context);
			if (bean == null) {
				return;
			}
			
			activity.initChannelBeanCommon(bean, curView, bean.isCanClick(), bean.isHasChild());
			ImageView imageView = bean.getImageView();
			if (bean.getId().equals(String.valueOf(activity.channelId)) || bean.getId().equals(String.valueOf(activity.curCheckChannelId))) {
				imageView.setImageResource(bean.getImageCheckedResource());
				imageView.setSelected(true);
				bean.setSelected(true);
				curCheckChannelId = Long.parseLong(bean.getId());
				curCheckChannelName = bean.getName();
				parentChannelId = Long.parseLong(bean.getParentId());
			} else {
				imageView.setImageResource(bean.getImageUncheckResource());
				imageView.setSelected(false);
			}
			addCheckBtnClickEvent(bean);
			addListViewItemClickEvent(bean);
			items.add(curView);
		}
	}
	
	@Override
	public View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
		if (items == null) {
			items = new ArrayList<View>();
		}
		if (items.size() == 0) {
			initItems(parent, resource);
		}
		View view = items.get(position);
		return view;
	}
	
	@Override
	public Object getItem(int position) {
		return objects.get(position);
	}

	private void addCheckBtnClickEvent(final NewsListImageViewBasicBean bean) {
		OnTouchListener listViewCheckBtnOnTouchListener = new ImageViewOnTouchListener(bean, ScreenTools.getTouchSlop(activity)) {
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
					parentChannelId = -1L;
				} else {
					curCheckChannelId = Long.parseLong(bean.getId());
					curCheckChannelName = bean.getName();
					parentChannelId = Long.parseLong(bean.getParentId());
				}
			}

			@Override
			public void onTouchBegin() {
				// 将焦点定位到搜索栏中
				activity.setCurChannelListEnableView(ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_AUTO_COMPLETE_TEXT_VIEW);
			}
		};
		bean.getImageView().setOnTouchListener(listViewCheckBtnOnTouchListener);
	}
	
	/**
	 * 给ListView中的item添加点击事件（点击搜索下一级）
	 * @param bean
	 */
	private void addListViewItemClickEvent(final NewsListImageViewBasicBean bean) {
		CommonOnTouchListener listViewItemOnTouchListener = new ListViewItemOnTouchListener(ScreenTools.getTouchSlop(activity)) {
			@Override
			public void onImgChangeDo() {
				try {
					channelClick(bean);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public boolean isClickBackgroungColorChange() {
				return false;
			}

			@Override
			public void onTouchBegin() {
				// 将焦点定位到搜索栏中
				activity.setCurChannelListEnableView(ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_AUTO_COMPLETE_TEXT_VIEW);
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
					activity.error(e);
				}
			}
		};
		bean.getView().setOnClickListener(listViewItemOnClickListener);
	}
	
	public void checkChannel(NewsListImageViewBasicBean bean) {
		ImageView checkBtn = bean.getImageView();
		if (!checkBtn.isSelected()) {
			for (NewsListImageViewBasicBean b : listViewItem) {
				ImageView subImageView = b.getImageView();
				if (subImageView.isSelected()) {
					subImageView.setImageResource(bean.getImageUncheckResource());
					subImageView.setSelected(false);
					b.setSelected(false);
					break;
				}
			}
		}
	}
	
	public void channelClick(NewsListImageViewBasicBean bean) throws Exception {
		if (bean.isCanClick()) {
			activity.searchChannelET.dismissDropDown();
			activity.channelId = Long.parseLong(bean.getId());
			activity.channelName = bean.getName();
			activity.initChannelDefaultData();
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
}
