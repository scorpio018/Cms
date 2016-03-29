package com.enorth.cms.adapter.news;

import java.util.List;

import com.enorth.cms.adapter.SearchCommonFilterAdapter;
import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.filter.news.SearchChannelFilter;
import com.enorth.cms.listener.CommonOnTouchListener;
import com.enorth.cms.listener.imageview.ImageViewOnTouchListener;
import com.enorth.cms.listener.newslist.ListViewItemOnTouchListener;
import com.enorth.cms.view.R;
import com.enorth.cms.view.news.ChannelSearchActivity;

import android.content.Context;
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
	
	private long curCheckChannelId = -1L;
	
	private String curCheckChannelName = "";
	
	private long parentChannelId = -1L;
	
	private View view;
	
	private ChannelBean channelBean;
	
	private int checkedPosition = -1;
	
	public SearchChannelFilterAdapter(ChannelSearchActivity activity, int textViewResourceId, List<ChannelBean> objects) {
		super(activity, textViewResourceId, objects);
		this.activity = activity;
//		this.thisAdapter = this;
	}
	
	/*@Override
	public void doAfterFilterNewValues(List<ChannelBean> values) {
		listViewItem = new ArrayList<NewsListImageViewBasicBean>();
		for (ChannelBean value : values) {
			listViewItem.add(bean);
		}
		// 每次刷新数据时将items清空
//		items = new ArrayList<View>();
	}*/

	@Override
	public void initFilter(Context context, List<ChannelBean> objects, Object lock, List<ChannelBean> originalValues) {
		filter = new SearchChannelFilter(context, objects, lock, originalValues, this);
	}
	
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
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		ChannelBean channelBean = super.objects.get(position);
		if (channelBean.getChannelId().equals(activity.getChannelId()) || channelBean.getChannelId().equals(activity.getCurCheckChannelId())) {
			holder.checkBtn.setChecked(true);
			holder.checkBtn.setTag(true);
			this.view = convertView;
			this.channelBean = channelBean;
			this.checkedPosition = position;
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
		initCheckBtnState(channelBean, holder, convertView, position);
		// 初始化CheckBox的点击事件
		addCheckBtnClickEvent(channelBean, holder, convertView, position);
		// 添加ListView的item的点击事件
		addListViewItemClickEvent(channelBean, holder, convertView, position);
		return convertView;
	}
	
	@Override
	public Object getItem(int position) {
		return objects.get(position);
	}
	
	/**
	 * 初始化左侧按钮的状态
	 * @param bean
	 */
	private void initCheckBtnState(ChannelBean channelBean, Holder holder, View view, int position) {
		if ((channelBean.getChannelId().equals(activity.getChannelId()) && this.view == null) || (checkedPosition == position)) {
			holder.checkBtn.setChecked(true);
			holder.checkBtn.setTag(true);
			this.view = view;
			this.channelBean = channelBean;
			activity.setCurCheckChannelId(channelBean.getChannelId());
			activity.setCurCheckChannelName(channelBean.getChannelName());				
			activity.setParentChannelId(channelBean.getParentId());
		} else {
			holder.checkBtn.setChecked(false);
		}
	}
	
	/**
	 * 给每一个频道左侧的选中图标添加点击事件
	 * @param bean
	 */
	private void addCheckBtnClickEvent(final ChannelBean channelBean, final Holder holder, final View view, final int position) {
		
		OnTouchListener listViewCheckBtnOnTouchListener = new ImageViewOnTouchListener(activity) {
			@Override
			public boolean onImgChangeBegin(View v) {
				checkChannel(channelBean, holder, view, position);
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
	private void addListViewItemClickEvent(final ChannelBean channelBean, final Holder holder, final View view, final int position) {
		CommonOnTouchListener listViewItemOnTouchListener = new ListViewItemOnTouchListener(activity) {
			@Override
			public void onImgChangeDo(View v) {
				try {
					channelClick(channelBean, holder, view, position);
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
	
	private void channelClick(ChannelBean channelBean, Holder holder, View view, int position) {
		if (channelBean.getHasChildren() == ParamConst.HAS_CHILDREN_YES) {
			activity.setChannelId(channelBean.getChannelId());
			activity.setChannelName(channelBean.getChannelName());
			activity.setBackToParent(false);
			activity.initChannelDefaultData();
		} else {
			activity.setCurCheckChannelId(channelBean.getChannelId());
			activity.setCurCheckChannelName(channelBean.getChannelName());
			checkChannel(channelBean, holder, view, position);
		}
	}
	
	/**
	 * 选中频道
	 * @param holder
	 * @param view
	 */
	public void checkChannel(ChannelBean channelBean, Holder holder, View view, int position) {
		activity.setCurChannelListEnableView(ParamConst.CUR_CHANNEL_LIST_ENABLE_VIEW_AUTO_COMPLETE_TEXT_VIEW);
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
			this.channelBean = channelBean;
			this.checkedPosition = position;
		}
	}
	
	public long getCurCheckChannelId() {
		return curCheckChannelId;
	}

	public void setCurCheckChannelId(long curCheckChannelId) {
		this.curCheckChannelId = curCheckChannelId;
	}

	public String getCurCheckChannelName() {
		return curCheckChannelName;
	}

	public void setCurCheckChannelName(String curCheckChannelName) {
		this.curCheckChannelName = curCheckChannelName;
	}

	public long getParentChannelId() {
		return parentChannelId;
	}

	public void setParentChannelId(long parentChannelId) {
		this.parentChannelId = parentChannelId;
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
