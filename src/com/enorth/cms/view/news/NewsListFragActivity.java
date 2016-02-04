package com.enorth.cms.view.news;

import com.enorth.cms.bean.BottomMenuOperateDataBasicBean;
import com.enorth.cms.bean.news_list.NewsListBottomMenuOperateDataBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.handler.newslist.NewsSubTitleHandler;
import com.enorth.cms.listener.newslist.subtitle.ChooseChannelOnClickListener;
import com.enorth.cms.listener.newslist.subtitle.ChooseChannelOnTouchListener;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsListFragActivity extends NewsCommonActivity {

	@Override
	public BottomMenuOperateDataBasicBean initBottomMenuOperateBean() {
		return new NewsListBottomMenuOperateDataBean();
	}

	@Override
	public void setNewsListContentView() {
		setContentView(R.layout.activity_news_list_frag);
	}

	@Override
	public Activity getCurActivity() {
		return this;
	}

	@Override
	public void initNewsTitle() {
		
	}

	@Override
	public void initNewsSubTitle() {
		// 如果没有频道ID，则存入默认的频道ID
		channelId = SharedPreUtil.getLong(thisActivity, ParamConst.CUR_CHANNEL_ID);
		newsSubTitleHandler = new NewsSubTitleHandler(this);
		try {
			presenter.requestCurChannelData(channelId, 1, newsSubTitleHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LinearLayout layout = (LinearLayout) findViewById(R.id.newsSubTitleLineLayout);
		ChooseChannelOnTouchListener listener = new ChooseChannelOnTouchListener(touchSlop) {
			@Override
			public void onImgChangeDo() {
				intent.setClass(thisActivity, ChannelSearchActivity.class);
				thisActivity.startActivity(intent);
			}
		};
		listener.changeColor(R.color.white, R.color.news_sub_title_color_basic);
		layout.setOnTouchListener(listener);
//		OnClickListener listener = new ChooseChannelOnClickListener(thisActivity);
//		layout.setOnClickListener(listener);
		newsSubTitleTV = (TextView) layout.findViewById(R.id.newsSubTitleText);
		newsSubTitleTV.setText(newsSubTitleText);
	}


	
}
