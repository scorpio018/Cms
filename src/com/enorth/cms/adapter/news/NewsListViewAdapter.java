package com.enorth.cms.adapter.news;

import java.util.List;

import com.enorth.cms.bean.news_list.NewsListBean;
import com.enorth.cms.common.EnableSimpleChangeTextView;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.newslist.news.CheckBtnOnCheckedChangeListener;
import com.enorth.cms.listener.newslist.news.NewsItemOnTouchListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.TimeUtil;
import com.enorth.cms.view.R;
import com.enorth.cms.view.news.NewsCommonActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsListViewAdapter extends BaseAdapter {
	
	private List<NewsListBean> items;
	
	private NewsCommonActivity activity;

	public NewsListViewAdapter(List<NewsListBean> items, NewsCommonActivity activity) {
		this.items = items;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public NewsListBean getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getNewsId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(activity).inflate(R.layout.news_item, null);
			holder.checkBtn = (CheckBox) convertView.findViewById(R.id.iv_check_btn);
			holder.newsTitle = (TextView) convertView.findViewById(R.id.tv_news_title);
			holder.newsTime = (TextView) convertView.findViewById(R.id.tv_news_time);
			holder.newsAuthorName = (TextView) convertView.findViewById(R.id.tv_news_author_name);
			// 是否是融合新闻 0否 1是
			holder.linkIV = (ImageView) convertView.findViewById(R.id.iv_link);
			// 是否有导读图 0否 1是
			holder.guideImageIV = (ImageView) convertView.findViewById(R.id.iv_guide_image);
			// 批注
			holder.reviewCountTV = (EnableSimpleChangeTextView) convertView.findViewById(R.id.iv_news_review_count);
			
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final NewsListBean item = getItem(position);
		// 由于PullToRefreshListView的onItemClickListener失效，不得已出此下策，望轻喷
		NewsItemOnTouchListener listItemOnTouchListener = new NewsItemOnTouchListener(activity) {
			
			@Override
			public void onImgChangeDo(View v) {
				Toast.makeText(activity, "点击的新闻ID为【" + item.getNewsId() + "】", Toast.LENGTH_SHORT).show();
			}
		};
		listItemOnTouchListener.changeColor(ColorUtil.getBgGrayPress(activity), ColorUtil.getBgGrayDefault(activity));
		convertView.setOnTouchListener(listItemOnTouchListener);
		
		// 左侧菜单的点击事件
		holder.checkBtn.setOnCheckedChangeListener(new CheckBtnOnCheckedChangeListener(activity));
		
		// 新闻标题的字数控制
		String title = item.getTitle();
		if (title.length() > activity.getNewsTitleAllowLength()) {
			title = title.substring(0, activity.getNewsTitleAllowLength()) + "...";
		}
		holder.newsTitle.setText(title);
		// 新闻的修改时间
		String modDate = TimeUtil.getDateMHHM(item.getModDate());
		holder.newsTime.setText(modDate);
		// 新闻作者
		holder.newsAuthorName.setText(item.getTrueName());
		
		// 是否是融合新闻 0否 1是
		int conv = item.getConv();
		if (conv == ParamConst.IS_CONV_YES) {
			holder.linkIV.setVisibility(View.VISIBLE);
			int link = item.getLink();
			if (link == ParamConst.LINK_YES) {
				holder.linkIV.setBackgroundResource(R.drawable.icon_link);
			} else if (link == ParamConst.LINK_NO) {
				holder.linkIV.setBackgroundResource(R.drawable.icon_unlink);
			} else {
				Log.e("NewsListViewAdapter", "新闻参数有误【link：" + link + "】");
				return null;
			}
		} else if (conv == ParamConst.IS_CONV_NO) {
			holder.linkIV.setVisibility(View.GONE);
		} else {
			Log.e("NewsListViewAdapter", "新闻参数有误【is_conv：" + conv + "】");
			return null;
		}
		
		// 是否有导读图 0否 1是
		int guideImage = item.getHasGuideImage();
		if (guideImage == ParamConst.HAS_GUIDE_IMAGE_YES) {
			holder.guideImageIV.setVisibility(View.VISIBLE);
		} else if (guideImage == ParamConst.HAS_GUIDE_IMAGE_NO) {
			holder.guideImageIV.setVisibility(View.GONE);
		} else {
			Log.e("NewsListViewAdapter", "新闻参数有误【hasGuideImage：" + guideImage + "】");
			return null;
		}
		
		// 批注
		holder.reviewCountTV.setColorBasicBean(ColorUtil.getOrangeButtonColorBasicBean(activity));
		holder.reviewCountTV.setRadius(8);
		int reviewCount = item.getReviewCount();
		if (reviewCount > 0) {
			holder.reviewCountTV.setVisibility(View.VISIBLE);
			holder.reviewCountTV.setText(String.valueOf(reviewCount));
		} else {
			holder.reviewCountTV.setVisibility(View.GONE);
		}
		
		return convertView;
	}

	public List<NewsListBean> getItems() {
		return items;
	}

	public void setItems(List<NewsListBean> items) {
		this.items = items;
	}

	class Holder {
		/**
		 * 新闻左侧的点击图标
		 */
		CheckBox checkBtn;
		/**
		 * 新闻标题
		 */
		TextView newsTitle;
		/**
		 * 每一个新闻的修改时间
		 */
		TextView newsTime;
		/**
		 * 新闻作者
		 */
		TextView newsAuthorName;
		/**
		 * 是否是融合新闻的图标
		 */
		ImageView linkIV;
		/**
		 * 是否有导读图的图标
		 */
		ImageView guideImageIV;
		/**
		 * 批注
		 */
		EnableSimpleChangeTextView reviewCountTV;
	}
}
