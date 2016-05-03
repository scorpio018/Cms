package com.enorth.cms.view.news;

import java.util.List;

import com.enorth.cms.bean.channel_search.RequestNewsSearchUrlBean;
import com.enorth.cms.common.EnableSimpleChangeButton;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.newslist.newssearch.NewsChannelSubmitOnTouchListener;
import com.enorth.cms.utils.ActivityJumpUtil;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.StringUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewsSearchActivity extends Activity implements INewsSearchView {
	/**
	 * 抓取类型的文字描述
	 */
	private String[] spiderText = {"全部", "抓取", "非抓取"};
	/**
	 * 抓取类型的文字描述对应的ID
	 */
	private int[] spiderId = {ParamConst.SPIDER_STATE_ALL, ParamConst.SPIDER_STATE_YES, ParamConst.SPIDER_STATE_NO};
	/**
	 * 融合类型的文字描述
	 */
	private String[] convText = {"全部", "融合", "非融合"};
	/**
	 * 融合类型的文字描述对应的ID
	 */
	private int[] convId = {ParamConst.CONV_STATE_ALL, ParamConst.CONV_STATE_YES, ParamConst.CONV_STATE_NO};
	/**
	 * 抓取选择按钮组集合
	 */
	private List<EnableSimpleChangeButton> spiderBtns;
	/**
	 * 融合选择按钮组集合
	 */
	private List<EnableSimpleChangeButton> convBtns;
	/**
	 * “返回上一级”按钮
	 */
	private ImageView back;
	/**
	 * 标题
	 */
	private TextView titleText;
	/**
	 * 重置按钮
	 */
	private TextView titleRightBtn;
	/**
	 * 包裹抓取的按钮组的Layout
	 */
	private LinearLayout crawlBtnLineLayout;
	/**
	 * 包裹融合的按钮组的Layout
	 */
	private LinearLayout mergeBtnLineLayout;
	/**
	 * 新闻ID文本框
	 */
	private EditText newsSearchNewsIdET;
	/**
	 * 关键词文本框
	 */
	private EditText newsSearchKeywordET;
	/**
	 * 搜索新闻的确定按钮
	 */
	private Button newsSearchSubmitBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_search);
		
		initView();
		initTitleEvent();
		initBtnGroup();
		initSubmmitBtn();
	}
	
	private void initView() {
		// 标头左侧的返回按钮
		back = (ImageView) findViewById(R.id.titleLeftIV);
		// 标题
		titleText = (TextView) findViewById(R.id.titleMiddleTV);
		// 标头右侧的重置按钮
		titleRightBtn = (TextView) findViewById(R.id.titleRightTV);
		
		// 获取包裹抓取按钮组的layout
		crawlBtnLineLayout = (LinearLayout) findViewById(R.id.crawlLineLayout);
//		RelativeLayout crawlBtnRelaLayout = (RelativeLayout) findViewById(R.id.crawlBtnRelaLayout);
//		crawlBtnLineLayout = (LinearLayout) crawlBtnRelaLayout.getChildAt(0);
		
		// 获取包裹融合按钮组的layout
		mergeBtnLineLayout = (LinearLayout) findViewById(R.id.mergeLineLayout);
//		RelativeLayout mergeBtnRelaLayout = (RelativeLayout) findViewById(R.id.mergeBtnRelaLayout);
//		mergeBtnLineLayout = (LinearLayout) mergeBtnRelaLayout.getChildAt(0);
		
		// 新闻ID文本框
		newsSearchNewsIdET = (EditText) findViewById(R.id.newsSearchNewsIdET);
		// 关键词文本框
		newsSearchKeywordET = (EditText) findViewById(R.id.newsSearchKeywordET);
		
		initViewBaseData();
	}
	
	private void initViewBaseData() {
		back.setImageResource(R.drawable.common_back);
		titleText.setText("搜索");
		titleRightBtn.setText("重置");
		titleRightBtn.setTextColor(ColorUtil.getWhiteColor(this));
	}
	
	private void initTitleEvent() {
		initBackEvent();
		initResetEvent();
	}
	/**
	 * 初始化返回键事件
	 */
	private void initBackEvent() {
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NewsSearchActivity.this.onBackPressed();
			}
		});
	}
	
	private void initResetEvent() {
		titleRightBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 将抓取按钮组进行重置
				ViewUtil.changeBtnGroupStyleByFocusedState(NewsSearchActivity.this, crawlBtnLineLayout, 0, ColorUtil.getCommonBlueColor(NewsSearchActivity.this), ColorUtil.getWhiteColor(NewsSearchActivity.this));
				// 将融合按钮组进行重置
				ViewUtil.changeBtnGroupStyleByFocusedState(NewsSearchActivity.this, mergeBtnLineLayout, 0, ColorUtil.getCommonBlueColor(NewsSearchActivity.this), ColorUtil.getWhiteColor(NewsSearchActivity.this));
				// 将新闻ID文本框进行清空
				newsSearchNewsIdET.setText("");
				// 将关键词文本框进行清空
				newsSearchKeywordET.setText("");
			}
		});
	}
	
	/**
	 * 将抓取和融合的按钮组进行初始化
	 * @throws Exception
	 */
	private void initBtnGroup() {
		initspiderLayout();
		initconvLayout();
	}
	
	/**
	 * 初始化抓取的选择按钮组
	 * @throws Exception
	 */
	private void initspiderLayout() {
		spiderBtns = ViewUtil.initBtnGroupLayout(this, crawlBtnLineLayout, spiderText, spiderId, 0.9f);
	}
	/**
	 * 初始化融合的选择按钮组
	 * @throws Exception
	 */
	private void initconvLayout() {
		convBtns = ViewUtil.initBtnGroupLayout(this, mergeBtnLineLayout, convText, convId, 0.9f);
	}
	
	/**
	 * 初始化确定按钮，并将输入的搜索条件存入Bundle中返回给上一个activity
	 * @throws Exception
	 */
	private void initSubmmitBtn() {
		newsSearchSubmitBtn = (Button) findViewById(R.id.newsSearchSubmitBtn);
		NewsChannelSubmitOnTouchListener listener = new NewsChannelSubmitOnTouchListener(ScreenTools.getTouchSlop(this)) {
			@Override
			public void onImgChangeDo(View v) {
				takeParamsBackToPrevActivity();
			}
		};
		listener.changeColor(ColorUtil.getGrayLighter(this), ColorUtil.getCommonBlueColor(this));
		newsSearchSubmitBtn.setOnTouchListener(listener);
	}
	
	/*@Override
	public void onBackPressed() {
		takeParamsBackToPrevActivity();
	}*/
	
	private void takeParamsBackToPrevActivity() {
		Bundle values = getValues();
		ActivityJumpUtil.takeParamsBackToPrevActivity(this, values, ParamConst.NEWS_SEARCH_ACTIVITY_BACK_TO_NEWS_LIST_FRAG_ACTIVITY_RESULT_CODE);
	}
	
	private Bundle getValues() {
		int curSpiderId = ViewUtil.getCurCheckedBtnGroupId(spiderBtns);
		int curConvState = ViewUtil.getCurCheckedBtnGroupId(convBtns);
		String newsIdText = newsSearchNewsIdET.getText().toString();
		String keywordText = newsSearchKeywordET.getText().toString();
		RequestNewsSearchUrlBean bean = new RequestNewsSearchUrlBean();
		bean.setSpiderState(curSpiderId);
		bean.setConvState(curConvState);
		if (StringUtil.isNotEmpty(newsIdText)) {
			bean.setNewsId(newsIdText);
		} else {
			bean.setChannelId(StaticUtil.getCurChannelBean(this).getChannelId());
			bean.setKeywords(keywordText);
		}
		Bundle bundle = new Bundle();
		bundle.putSerializable(ParamConst.NEWS_SEARCH_BEAN, bean);
		/*bundle.putInt("curspiderId", curspiderId);
		bundle.putInt("curconvId", curconvId);
		bundle.putString("newsIdText", newsIdText);
		bundle.putString("keywordText", keywordText);*/
		return bundle;
	}
	
}
