package com.enorth.cms.view.news;

import com.enorth.cms.bean.BottomMenuOperateDataBasicBean;
import com.enorth.cms.bean.news_list.NewsListBottomMenuOperateDataBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.handler.newslist.NewsSubTitleHandler;
import com.enorth.cms.listener.color.UnChangeBGColorOnTouchListener;
import com.enorth.cms.listener.newslist.subtitle.ChooseChannelOnTouchListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class NewsListFragActivity extends NewsCommonActivity {
	
	@Override
	public BottomMenuOperateDataBasicBean initBottomMenuOperateBean() {
		return new NewsListBottomMenuOperateDataBean();
	}

	@Override
	public void setNewsListContentView() {
//		setContentView(R.layout.activity_main);
		ViewUtil.setContentViewForMenu(this, R.layout.activity_news_list_frag);
	}
	
	@Override
	public Activity getCurActivity() {
		return this;
	}
	
	@Override
	public int[] setAllNewsTitleText() {
		return new int[]{R.string.normal_news_title_text, R.string.my_news_title_text};
	}
	
	@Override
	public int initCurNewsType() {
		return ParamConst.TYPE_NORMAL;
	}

	@Override
	public void initNewsTitle() {
		ViewUtil.initMenuTitle(this, getCurNewsTitleName());
		initTitleSearchBtn();
	}
	
	private void initTitleSearchBtn() {
		ImageView newsTitleNewsSearch = (ImageView) findViewById(R.id.titleRightIV);
		newsTitleNewsSearch.setBackgroundResource(R.drawable.news_search);
		UnChangeBGColorOnTouchListener listener = new UnChangeBGColorOnTouchListener(this) {
			
			@Override
			public void onImgChangeDo(View v) {
				intent.setClass(NewsListFragActivity.this, NewsSearchActivity.class);
//				startActivity(intent);
				startActivityForResult(intent, ParamConst.NEWS_LIST_FRAG_ACTIVITY_TO_NEWS_SEARCH_ACTIVITY_REQUEST_CODE);
			}
		};
		newsTitleNewsSearch.setOnTouchListener(listener);
	}

	@Override
	public void initNewsSubTitle() {
		// 如果没有频道ID，则存入默认的频道ID
//		channelId = SharedPreUtil.getLong(this, ParamConst.CUR_CHANNEL_ID);
		channelId = StaticUtil.getCurChannelBean(this).getChannelId();
		newsSubTitleHandler = new NewsSubTitleHandler(this);
		try {
//			presenter.requestCurChannelData(channelId, ParamConst.USER_ID, newsSubTitleHandler);
//			newsSubTitleHandler.sendEmptyMessage(0);
			Message msg = new Message();
			msg.obj = channelBean;
			msg.what = ParamConst.MESSAGE_WHAT_SUCCESS;
			newsSubTitleHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LinearLayout layout = (LinearLayout) findViewById(R.id.newsSubTitleLineLayout);
		ChooseChannelOnTouchListener listener = new ChooseChannelOnTouchListener(this) {
			@Override
			public void onImgChangeDo(View v) {
				intent.setClass(NewsListFragActivity.this, ChannelSearchActivity.class);
				// 将当前用户的频道传到频道搜索中(由于使用了全局静态变量，所以不需要这样传值了)
//				Bundle bundle = initChannelId();
//				intent.putExtras(bundle);
//				thisActivity.startActivity(intent);
				startActivityForResult(intent, ParamConst.NEWS_LIST_FRAG_ACTIVITY_TO_CHANNEL_SEARCH_ACTIVITY_REQUEST_CODE);
			}

			@Override
			public void onTouchBegin() {
				
			}
		};
		listener.changeColor(ColorUtil.getWhiteColor(this), ColorUtil.getNewsSubTitleColorBasic(this));
		layout.setOnTouchListener(listener);
	}
	
	private Bundle initChannelId() {
		Bundle bundle = new Bundle();
		long channelId = SharedPreUtil.getLong(this, ParamConst.CUR_CHANNEL_ID);
		long parentChannelId = SharedPreUtil.getLong(this, ParamConst.CUR_CHANNEL_ID_PARENT_ID);
		bundle.putLong(ParamConst.CUR_CHANNEL_ID, channelId);
		bundle.putLong(ParamConst.CUR_CHANNEL_ID_PARENT_ID, parentChannelId);
		return bundle;
	}

	@Override
	public NewsCommonActivity getActivity() {
		return this;
	}

}
