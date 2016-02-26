package com.enorth.cms.view.news;

import com.enorth.cms.bean.BottomMenuOperateDataBasicBean;
import com.enorth.cms.bean.news_list.NewsListBottomMenuOperateDataBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.handler.newslist.NewsSubTitleHandler;
import com.enorth.cms.listener.newslist.subtitle.ChooseChannelOnTouchListener;
import com.enorth.cms.listener.newslist.title.NewsSearchOnTouchListener;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
	public void initNewsTitle() {
		initMenuBtn();
		initTitleText();
		initTitleSearchBtn();
	}
	
	private void initMenuBtn() {
		ImageView menuBtn = (ImageView) findViewById(R.id.titleLeftBtn);
		menuBtn.setBackgroundResource(R.drawable.news_menu);
		ViewUtil.initMenuEvent(this, menuBtn);
	}
	
	private void initTitleText() {
		TextView newsTitleText = (TextView) findViewById(R.id.titleText);
		newsTitleText.setText(R.string.news_title_text);
	}
	
	private void initTitleSearchBtn() {
		ImageView newsTitleNewsSearch = (ImageView) findViewById(R.id.titleRightBtn);
		newsTitleNewsSearch.setBackgroundResource(R.drawable.news_search);
		NewsSearchOnTouchListener listener = new NewsSearchOnTouchListener(touchSlop) {
			
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
		channelId = SharedPreUtil.getLong(this, ParamConst.CUR_CHANNEL_ID);
		newsSubTitleHandler = new NewsSubTitleHandler(this);
		try {
			presenter.requestCurChannelData(channelId, ParamConst.USER_ID, newsSubTitleHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LinearLayout layout = (LinearLayout) findViewById(R.id.newsSubTitleLineLayout);
		ChooseChannelOnTouchListener listener = new ChooseChannelOnTouchListener(touchSlop) {
			@Override
			public void onImgChangeDo(View v) {
				intent.setClass(NewsListFragActivity.this, ChannelSearchActivity.class);
				// 将当前用户的频道传到频道搜索中
				Bundle bundle = initChannelId();
				intent.putExtras(bundle);
//				thisActivity.startActivity(intent);
				startActivityForResult(intent, ParamConst.NEWS_LIST_FRAG_ACTIVITY_TO_CHANNEL_SEARCH_ACTIVITY_REQUEST_CODE);
			}

			@Override
			public void onTouchBegin() {
				// TODO Auto-generated method stub
				
			}
		};
		listener.changeColor(R.color.white, R.color.news_sub_title_color_basic);
		layout.setOnTouchListener(listener);
//		OnClickListener listener = new ChooseChannelOnClickListener(thisActivity);
//		layout.setOnClickListener(listener);
		/*newsSubTitleTV = (TextView) layout.findViewById(R.id.newsSubTitleText);
		newsSubTitleTV.setText(newsSubTitleText);*/
	}
	
	private Bundle initChannelId() {
		Bundle bundle = new Bundle();
		long channelId = SharedPreUtil.getLong(this, ParamConst.CUR_CHANNEL_ID);
		long parentChannelId = SharedPreUtil.getLong(this, ParamConst.CUR_CHANNEL_ID_PARENT_ID);
		bundle.putLong(ParamConst.CUR_CHANNEL_ID, channelId);
		bundle.putLong(ParamConst.CUR_CHANNEL_ID_PARENT_ID, parentChannelId);
		return bundle;
	}
	
}
