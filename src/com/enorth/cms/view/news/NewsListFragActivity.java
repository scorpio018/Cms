package com.enorth.cms.view.news;

import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.bean.BottomMenuOperateDataBasicBean;
import com.enorth.cms.bean.news_list.NewsListBottomMenuOperateDataBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.handler.newslist.NewsSubTitleHandler;
import com.enorth.cms.listener.color.UnChangeBGColorOnTouchListener;
import com.enorth.cms.listener.newslist.subtitle.ChooseChannelOnTouchListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
	public List<String> setAllNewsTitleText() {
		List<String> allNewsTitleText = new ArrayList<String>();
		allNewsTitleText.add(getResources().getString(R.string.normal_news_title_text));
		allNewsTitleText.add(getResources().getString(R.string.my_news_title_text));
		return allNewsTitleText;
		/*Map<String, String> allNewsTitleText = new LinkedHashMap<String, String>();
		allNewsTitleText.put(UrlConst.NEWS_LIST_POST_URL, getResources().getString(R.string.normal_news_title_text));
		allNewsTitleText.put(UrlConst.MY_NEWS_LIST_POST_URL, getResources().getString(R.string.my_news_title_text));
		return allNewsTitleText;*/
	}
	
	@Override
	public int initCurNewsType() {
		return ParamConst.NEWS_TYPE_NORMAL;
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

	@Override
	public NewsCommonActivity getActivity() {
		return this;
	}

	@Override
	public int initNewsTypeBtnColor() {
		return ColorUtil.getCommonBlueColor(this);
	}

	@Override
	public void initAddNewsBtn() {
		OnClickListener listener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(NewsListFragActivity.this, NewsAddActivity.class);
				startActivity(intent);
			}
		};
		addNewsBtn.setOnClickListener(listener);
	}

}
