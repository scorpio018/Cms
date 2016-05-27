package com.enorth.cms.view.news;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.bean.BottomMenuOperateDataBasicBean;
import com.enorth.cms.bean.news_list.NewsLiveListBottomMenuOperateDataBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.handler.newslist.NewsSubTitleHandler;
import com.enorth.cms.listener.color.UnChangeBGColorOnTouchListener;
import com.enorth.cms.listener.newslist.subtitle.ChooseChannelOnTouchListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NewsLiveListFragActivity extends NewsCommonActivity {

	@Override
	public BottomMenuOperateDataBasicBean initBottomMenuOperateBean() {
		return new NewsLiveListBottomMenuOperateDataBean();
	}

	@Override
	public void setNewsListContentView() {
		// setContentView(R.layout.activity_news_live_list_frag);
		// setContentView(R.layout.activity_main);
		// addView();
		ViewUtil.setContentViewForMenu(this, R.layout.activity_news_live_list_frag);
	}

	@Override
	public Activity getCurActivity() {
		return this;
	}

	@Override
	public List<String> setAllNewsTitleText() {
		List<String> allNewsTitleText = new ArrayList<String>();
		allNewsTitleText.add(getResources().getString(R.string.news_live_title_text));
		allNewsTitleText.add(getResources().getString(R.string.my_news_title_text));
		return allNewsTitleText;
		/*
		 * Map<String, String> allNewsTitleText = new LinkedHashMap<String,
		 * String>(); allNewsTitleText.put(UrlConst.NEWS_LIST_POST_URL,
		 * getResources().getString(R.string.news_live_title_text));
		 * allNewsTitleText.put(UrlConst.MY_NEWS_LIST_POST_URL,
		 * getResources().getString(R.string.my_news_title_text)); return
		 * allNewsTitleText;
		 */
	}

	@Override
	public int initCurNewsType() {
		return ParamConst.NEWS_TYPE_PIC_TEXT_LIVE;
	}

	@Override
	public void initNewsTitle() {
		ViewUtil.initMenuTitle(this, getCurNewsTitleName());
		initTitleSearchBtn();
	}

	private void initTitleSearchBtn() {
		ImageView newsTitleNewsSearch = (ImageView) findViewById(R.id.titleRightIV);
		newsTitleNewsSearch.setBackgroundResource(R.drawable.iconfont_sousuo);
		UnChangeBGColorOnTouchListener listener = new UnChangeBGColorOnTouchListener(this) {

			@Override
			public void onImgChangeDo(View v) {
				intent.setClass(NewsLiveListFragActivity.this, NewsSearchActivity.class);
				// startActivity(intent);
				startActivityForResult(intent,
						ParamConst.NEWS_LIVE_LIST_FRAG_ACTIVITY_TO_NEWS_SEARCH_ACTIVITY_REQUEST_CODE);
			}
		};
		newsTitleNewsSearch.setOnTouchListener(listener);
	}

	@Override
	public void initNewsSubTitle() {
		// 如果没有频道ID，则存入默认的频道ID
		channelId = StaticUtil.getCurChannelBean(this).getChannelId();
		newsSubTitleHandler = new NewsSubTitleHandler(this);
		Message msg = new Message();
		msg.obj = channelBean;
		msg.what = ParamConst.MESSAGE_WHAT_SUCCESS;
		newsSubTitleHandler.sendMessage(msg);
		LinearLayout layout = (LinearLayout) findViewById(R.id.newsSubTitleLineLayout);
		ChooseChannelOnTouchListener listener = new ChooseChannelOnTouchListener(this) {
			@Override
			public void onImgChangeDo(View v) {
				intent.setClass(NewsLiveListFragActivity.this, ChannelSearchActivity.class);
				intent.putExtra(ParamConst.CHANNEL_SEARCH_IS_TEMP, ParamConst.CHANNEL_SEARCH_IS_TEMP_NO);
				// 将当前用户的频道传到频道搜索中(由于使用了全局静态变量，所以不需要这样传值了)
				// Bundle bundle = initChannelId();
				// intent.putExtras(bundle);
				// thisActivity.startActivity(intent);
				startActivityForResult(intent,
						ParamConst.NEWS_LIVE_LIST_FRAG_ACTIVITY_TO_CHANNEL_SEARCH_ACTIVITY_REQUEST_CODE);
			}

			@Override
			public void onTouchBegin() {
				// TODO Auto-generated method stub

			}
		};
		listener.changeColor(ColorUtil.getWhiteColor(this), ColorUtil.getNewsSubTitleColorBasic(this));
		layout.setOnTouchListener(listener);
		// OnClickListener listener = new ChooseChannelOnClickListener(this);
		// layout.setOnClickListener(listener);
		/*
		 * newsSubTitleTV = (TextView)
		 * layout.findViewById(R.id.newsSubTitleText);
		 * newsSubTitleTV.setText(newsSubTitleText);
		 */
	}

	@Override
	public NewsCommonActivity getActivity() {
		return this;
	}

	@Override
	public int initNewsTypeBtnColor() {
		return ColorUtil.getCommonGreenColor(this);
	}

	@Override
	public void initAddNewsBtn() {
		OnClickListener listener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(NewsLiveListFragActivity.this, "点击了“添加新闻”按钮", Toast.LENGTH_SHORT).show();
			}
		};
		addNewsBtn.setOnClickListener(listener);

	}
}
