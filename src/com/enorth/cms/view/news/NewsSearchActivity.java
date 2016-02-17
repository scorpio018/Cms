package com.enorth.cms.view.news;

import java.util.List;

import com.enorth.cms.common.EnableSimpleChangeButton;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.newslist.newssearch.NewsChannelSubmitOnTouchListener;
import com.enorth.cms.listener.newslist.title.NewsSearchOnTouchListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class NewsSearchActivity extends Activity implements INewsSearchView {
	/**
	 * 抓取类型的文字描述
	 */
	private String[] crawlTypeText = {"全部", "抓取", "非抓取"};
	/**
	 * 抓取类型的文字描述对应的ID
	 */
	private String[] crawlTypeId = {"all", "crawl", "notCrawl"};
	/**
	 * 融合类型的文字描述
	 */
	private String[] mergeTypeText = {"全部", "融合", "非融合"};
	/**
	 * 融合类型的文字描述对应的ID
	 */
	private String[] mergeTypeId = {"all", "merge", "notMerge"};
	/**
	 * 抓取选择按钮组集合
	 */
	private List<EnableSimpleChangeButton> crawlTypeBtns;
	/**
	 * 融合选择按钮组集合
	 */
	private List<EnableSimpleChangeButton> mergeTypeBtns;
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
		
		try {
			initBtnGroup();
			initEditText();
			initSubmmitBtn();
		} catch (Exception e) {
			Log.e("error", e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 将抓取和融合的按钮组进行初始化
	 * @throws Exception
	 */
	private void initBtnGroup() throws Exception {
		initCrawlTypeLayout();
		initMergeTypeLayout();
	}
	
	/**
	 * 初始化抓取的选择按钮组
	 * @throws Exception
	 */
	private void initCrawlTypeLayout() throws Exception {
		RelativeLayout crawlBtnRelaLayout = (RelativeLayout) findViewById(R.id.crawlBtnRelaLayout);
		LinearLayout crawlBtnLineLayout = (LinearLayout) crawlBtnRelaLayout.getChildAt(0);
		crawlTypeBtns = ViewUtil.initBtnGroupLayout(this, crawlBtnLineLayout, crawlTypeText, crawlTypeId, 0.9f);
	}
	/**
	 * 初始化融合的选择按钮组
	 * @throws Exception
	 */
	private void initMergeTypeLayout() throws Exception {
		RelativeLayout mergeBtnRelaLayout = (RelativeLayout) findViewById(R.id.mergeBtnRelaLayout);
		LinearLayout mergeBtnLineLayout = (LinearLayout) mergeBtnRelaLayout.getChildAt(0);
		mergeTypeBtns = ViewUtil.initBtnGroupLayout(this, mergeBtnLineLayout, mergeTypeText, mergeTypeId, 0.9f);
	}
	
	/**
	 * 初始化文本框，并设置百分比宽度
	 * @throws Exception
	 */
	private void initEditText() throws Exception {
		initNewsIdET();
		initKeywordET();
	}
	
	/**
	 * 初始化搜索频道ID的文本框，并设置百分比宽度
	 * @throws Exception
	 */
	private void initNewsIdET() throws Exception {
		newsSearchNewsIdET = (EditText) findViewById(R.id.newsSearchNewsIdET);
		/*newsSearchNewsIdET = new EditText(this);
		ViewUtil.initViewByWeight(newsSearchNewsIdET, 2.7f);
		newsSearchNewsIdET.setHint(R.string.news_search_et_news_id_hint);*/
	}
	
	/**
	 * 初始化关键词的文本框，并设置百分比宽度
	 * @throws Exception
	 */
	private void initKeywordET() throws Exception {
		newsSearchKeywordET = (EditText) findViewById(R.id.newsSearchKeywordET);
		/*newsSearchKeywordET = new EditText(this);
		ViewUtil.initViewByWeight(newsSearchKeywordET, 2.7f);
		newsSearchKeywordET.setHint(R.string.news_search_et_keyword_hint);*/
	}
	
	/**
	 * 初始化确定按钮，并将输入的搜索条件存入Bundle中返回给上一个activity
	 * @throws Exception
	 */
	private void initSubmmitBtn() throws Exception {
		newsSearchSubmitBtn = (Button) findViewById(R.id.newsSearchSubmitBtn);
		NewsChannelSubmitOnTouchListener listener = new NewsChannelSubmitOnTouchListener(ScreenTools.getTouchSlop(this)) {
			@Override
			public void onImgChangeDo() {
				takeParamsBackToPrevActivity();
			}
		};
		listener.changeColor(R.color.gray_lighter, R.color.common_blue);
		newsSearchSubmitBtn.setOnTouchListener(listener);
		/*newsSearchSubmitBtn = new Button(this);
		ViewUtil.initViewByWeight(newsSearchSubmitBtn, 2.7f);
		newsSearchSubmitBtn.setBackgroundColor(ColorUtil.getCommonBlueColor(this));
		newsSearchSubmitBtn.setText(R.string.submit_btn_common);
		newsSearchSubmitBtn.setTextColor(ColorUtil.getWhiteColor(this));*/
	}
	
	@Override
	public void onBackPressed() {
		takeParamsBackToPrevActivity();
	}
	
	private void takeParamsBackToPrevActivity() {
		Bundle values = null;
		try {
			values = getValues();
		} catch (Exception e) {
			Toast.makeText(NewsSearchActivity.this, "在NewsSearchActivity进行返回时发生错误：【" + e.getMessage() + "】", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		ViewUtil.takeParamsBackToPrevActivity(this, values);
	}
	
	private Bundle getValues() throws Exception {
		String curCrawlTypeId = ViewUtil.getCurCheckedBtnGroupId(crawlTypeBtns);
		String curMergeTypeId = ViewUtil.getCurCheckedBtnGroupId(mergeTypeBtns);
		String newsIdText = newsSearchNewsIdET.getText().toString();
		String keywordText = newsSearchKeywordET.getText().toString();
		Bundle bundle = new Bundle();
		bundle.putString("curCrawlTypeId", curCrawlTypeId);
		bundle.putString("curMergeTypeId", curMergeTypeId);
		bundle.putString("newsIdText", newsIdText);
		bundle.putString("keywordText", keywordText);
		return bundle;
	}
	
}
