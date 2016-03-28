package com.enorth.cms.adapter.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.enorth.cms.adapter.SearchCommonFilterAdapter;
import com.enorth.cms.adapter.news.ChannelSearchListViewAdapter.Holder;
import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.bean.news_list.NewsListImageViewBasicBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.filter.news.SearchChannelFilter;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.listener.imageview.ImageViewOnTouchListener;
import com.enorth.cms.listener.newslist.ListViewItemOnTouchListener;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.news.ChannelSearchActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchChannelFilterAdapter extends SearchCommonFilterAdapter<ChannelBean> {
	
	private ChannelSearchActivity activity;
	
//	private SearchChannelFilterAdapter thisAdapter;
	
//	private List<NewsListImageViewBasicBean> listViewItem;
	
	public long curCheckChannelId = -1L;
	
	public String curCheckChannelName = "";
	
	public long parentChannelId = -1L;
	
	private View view;
	/**
	 * 由于AutoCompleteTextView的搜索结果是以效率优先（即会回收看不到的item），不满足需求，所以需要手动保留items而不适用默认的getView
	 */
//	public List<View> items;
	
	public SearchChannelFilterAdapter(ChannelSearchActivity activity, int textViewResourceId, List<ChannelBean> objects) {
		super(activity, textViewResourceId, objects);
		this.activity = activity;
//		this.thisAdapter = this;
	}
	
	@Override
	public void doAfterFilterNewValues(List<ChannelBean> values) {
		/*listViewItem = new ArrayList<NewsListImageViewBasicBean>();
		for (ChannelBean value : values) {
			listViewItem.add(bean);
		}*/
		// 每次刷新数据时将items清空
//		items = new ArrayList<View>();
	}

	@Override
	public void initFilter(Context context, List<ChannelBean> objects, Object lock, List<ChannelBean> originalValues) {
		filter = new SearchChannelFilter(context, objects, lock, originalValues, this);
	}
	
	/**
	 * 将所有匹配结果都存入items中
	 * @param parent
	 * @param resource
	 */
	/*private void initItems(ViewGroup parent, int resource) {
		int size = objects.size();
		for (int i = 0; i < size; i++) {
			curView = inflater.inflate(resource, parent, false);
			ChannelBean object = objects.get(i);
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
	}*/
	
	@Override
	public View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
//			convertView = LayoutInflater.from(activity).inflate(this.resource, null);
			convertView = inflater.inflate(resource, null);
			holder.checkBtn = (CheckBox) convertView.findViewById(R.id.iv_check_btn);
			holder.channelNameTV = (TextView) convertView.findViewById(R.id.tv_news_title);
			holder.channelNext = (ImageView) convertView.findViewById(R.id.iv_news_next);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		ChannelBean channelBean = super.objects.get(position);
		if (channelBean.getChannelId().equals(activity.getChannelId()) || channelBean.getChannelId().equals(activity.getCurCheckChannelId())) {
			holder.checkBtn.setChecked(true);
			curCheckChannelId = channelBean.getChannelId();
			curCheckChannelName = channelBean.getChannelName();
			parentChannelId = channelBean.getParentId();
		}
		
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
	
	@Override
	public Object getItem(int position) {
		return objects.get(position);
	}

	/*private void addCheckBtnClickEvent(final NewsListImageViewBasicBean bean) {
		OnTouchListener listViewCheckBtnOnTouchListener = new ImageViewOnTouchListener(activity) {
			@Override
			public boolean onImgChangeBegin(View v) {
				checkChannel(bean);
				return true;
			}

			@Override
			public void onImgChangeEnd(View v) {
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
	}*/
	
	/**
	 * 给ListView中的item添加点击事件（点击搜索下一级）
	 * @param bean
	 */
	/*private void addListViewItemClickEvent(final NewsListImageViewBasicBean bean) {
		CommonOnTouchListener listViewItemOnTouchListener = new ListViewItemOnTouchListener(activity) {
			@Override
			public void onImgChangeDo(View v) {
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
	}*/
	
	/*public void checkChannel(NewsListImageViewBasicBean bean) {
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
	}*/
	
	/*public void channelClick(NewsListImageViewBasicBean bean) throws Exception {
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
