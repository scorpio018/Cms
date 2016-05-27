package com.enorth.cms.view.news;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.enorth.cms.bean.ViewColorBasicBean;
import com.enorth.cms.bean.login.ChannelBean;
import com.enorth.cms.bean.news_list.GenerateNewsIdBean;
import com.enorth.cms.bean.news_list.RequestGenerateNewsIdUrlBean;
import com.enorth.cms.bean.news_list.RequestSaveNewsUrlBean;
import com.enorth.cms.bean.news_list.TemplateSearchBean;
import com.enorth.cms.common.EnableSimpleChangeButton;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.consts.UrlConst;
import com.enorth.cms.handler.newslist.NewsEditCommonHandler;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.presenter.newslist.INewsEditCommonPresenter;
import com.enorth.cms.presenter.newslist.NewsEditCommonPresenter;
import com.enorth.cms.utils.AnimUtil;
import com.enorth.cms.utils.BeanParamsUtil;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.ImageLoader;
import com.enorth.cms.utils.JsonUtil;
import com.enorth.cms.utils.ReflectUtil;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.StringUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;
import com.squareup.okhttp.RequestBody;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public abstract class NewsEditCommonActivity extends Activity implements INewsEditCommonView {
	/**
	 * 标题左侧菜单
	 */
	private TextView titleLeftTV;
	/**
	 * 标题右侧的按钮组
	 */
//	protected RelativeLayout titleBtnsLayout;
	protected LinearLayout titleLayout;
	/**
	 * 显示导读图片
	 */
	private ImageView readingPicIV;
	/**
	 * 导读图layout
	 */
	protected RelativeLayout readingPicLayout;
	/**
	 * 新闻标题
	 */
	protected TextView addReadingPicTV;
	
//	private RequestBody readingPicPath;
	/**
	 * 普通新闻和图文直播不同的layout
	 */
//	protected LinearLayout differentLayout;
	/**
	 * 选择频道的layout
	 */
	private LinearLayout channelSearchLayout;
	/**
	 * 用于显示当前选中的新闻频道
	 */
	private TextView channelSearchTV;
	/**
	 * 选择模版的layout
	 */
	private LinearLayout templateSearchLayout;
	/**
	 * 用于显示当前选中的新闻模版
	 */
	private TextView templateSearchTV;
	/**
	 * 关键词layout
	 */
//	protected LinearLayout keywordLayout;
	/**
	 * 正文和摘要按钮的layout
	 */
	private LinearLayout contentAndAbstractBtnsLayout;
	/**
	 * 正文/摘要按钮组
	 */
	private List<EnableSimpleChangeButton> contentAndAbstractBtns;
	/**
	 * 除了标题layout和附件layout之外的所有layout
	 */
	protected LinearLayout newsLayout;
	/**
	 * 附件layout
	 */
	protected RelativeLayout enclosureLayout;
	/**
	 * 进行url请求
	 */
	private INewsEditCommonPresenter presenter;
	/**
	 * url请求后的处理操作
	 */
	private NewsEditCommonHandler handler;
	/**
	 * 请求生成新的新闻ID的URL时所用的bean
	 */
	private RequestGenerateNewsIdUrlBean requestInitNewsIdUrlBean;
	/**
	 * 获取新闻ID后将其保存下来的bean
	 */
	protected GenerateNewsIdBean generateNewsIdBean;
	/**
	 * 请求保存新闻的URL时所用的bean
	 */
	private RequestSaveNewsUrlBean requestSaveNewsUrlBean;
	
	/**
	 * 按照9/10比例的控件允许的最大显示（新闻频道、新闻模版）
	 */
	private int allowLength = 0;
	/**
	 * 正文/摘要按钮上显示的内容
	 */
	private String[] btnText;
	/**
	 * 正文/摘要按钮对应的id
	 */
	private int[] btnId;
	/**
	 * 正文/摘要按钮选中时的颜色
	 */
	private int checkedColor;
	/**
	 * 正文/摘要未选中时的颜色
	 */
	private int unCheckedColor;
	/**
	 * 正文/摘要里面的内容
	 */
	protected String[] contentAndAbstractString;
	/**
	 * “重置”按钮
	 */
	private TextView resetTV;
	/**
	 * “排版”按钮
	 */
	private TextView composingTV;
	/**
	 * 正文/摘要的内容
	 */
	protected EditText contentAndAbstractET;
	/**
	 * 正文/摘要的EditText对应的提示信息
	 */
	private String[] contentAndAbstractETHint;
	/**
	 * 当前选中的新闻频道bean
	 */
	private ChannelBean channelBean;
	/**
	 * 当前选中的新闻模版bean
	 */
	private TemplateSearchBean templateSearchBean;
	/**
	 * 当前点击的按钮
	 */
	protected int curPosition;
	/**
	 * 设置当前activity使用的xml
	 * 
	 * @return
	 */
	public abstract int setContentView();
	/**
	 * 初始化标头的按钮组
	 */
	public abstract void initTitleBtns();
	/**
	 * 给标头的按钮组加上点击事件
	 */
	public abstract void initTitleBtnsEvent();
	/**
	 * 添加普通新闻和图文直播中不同的layout
	 */
	public abstract void addDifferentLayout();
	/**
	 * 添加关键词（如果没有，在实现此方法的方法体里面可以制空）
	 */
//	public abstract void addKeyword();
	/**
	 * 是否有附件
	 * @return
	 */
	public abstract boolean hasEnclosure();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(setContentView());
		initBasicData();
		initView();
		initEvent();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case ParamConst.NEWS_EDIT_COMMON_ACTIVITY_TO_ADD_READING_PIC_ACTIVITY_REQUEST_CODE:
			switch (resultCode) {
			case ParamConst.ADD_READING_PIC_ACTIVITY_BACK_TO_NEWS_EDIT_COMMON_ACTIVITY_DEL_READING_PIC_RESULT_CODE:
				readingPicIV.setImageBitmap(null);
				break;
			case ParamConst.ADD_READING_PIC_ACTIVITY_BACK_TO_NEWS_EDIT_COMMON_ACTIVITY_HAS_IMG_DATAS_RESULT_CODE:
				Bundle bundle = data.getExtras();
				ArrayList<String> imgDatas = bundle.getStringArrayList(ParamConst.IMG_DATAS);
				if (imgDatas.size() != 1) {
					ViewUtil.showAlertDialog(this, "只能选择一张做导读图");
				}
				
				ImageLoader.getInstance().initBitmapByLocalPath(readingPicIV, imgDatas.get(0));
				requestSaveNewsUrlBean.setUnpload(new File(imgDatas.get(0)));
//				readingPicPath = RequestBody.create(ParamConst.MEDIA_TYPE_PNG, new File(imgDatas.get(0)));
				break;
			default:
				break;
			}
			break;
		case ParamConst.NEWS_EDIT_COMMON_ACTIVITY_TO_CHANNEL_SEARCH_ACTIVITY_REQUEST_CODE:
			switch (resultCode) {
			case ParamConst.CHANNEL_SEARCH_ACTIVITY_BACK_TO_NEWS_EDIT_ACTIVITY_RESULT_CODE:
				// 获取当前应用选中的新闻频道，并将新闻频道树显示在channelSearchTV中
				String[] channelNamesTree = StaticUtil.getTmpChannelNamesTree(this, false);
				String channelContent = StaticUtil.getChannelContent(allowLength, channelNamesTree);
				channelSearchTV.setText(channelContent);
				channelBean = StaticUtil.getTmpChannelBean(this, false);
				requestSaveNewsUrlBean.setChannelId(channelBean.getChannelId());
				break;

			default:
				break;
			}
		case ParamConst.NEWS_EDIT_COMMON_ACTIVITY_TO_TEMPLATE_SEARCH_ACTIVITY_REQUEST_CODE:
			switch (resultCode) {
			case ParamConst.TEMPLATE_SEARCH_ACTIVITY_BACK_TO_NEWS_EDIT_COMMON_ACTIVITY_RESULT_CODE:
				if (data != null) {
					Bundle bundle = data.getExtras();
					if (bundle != null) {
						String str = bundle.getString(ParamConst.CUR_TEMPLATE_FILE, "");
						if (StringUtil.isNotEmpty(str)) {
							templateSearchBean = (TemplateSearchBean) SharedPreUtil.deSerializeObject(str);
						}
					}
				}
				if (templateSearchBean != null) {
					templateSearchTV.setText(templateSearchBean.getTemplateName());
					requestSaveNewsUrlBean.setTemplateId(templateSearchBean.getTemplateId());
				}
				break;

			default:
				break;
			}
		default:
			break;
		}
	}
	
	/**
	 * 初始化基本数据
	 */
	private void initBasicData() {
		initPresenter();
		initHandler();
		// 正文/摘要基本数据初始化
		btnText = new String[]{"正文", "摘要"};
		btnId = new int[]{ParamConst.EDIT_NEWS_BTN_CONTENT, ParamConst.EDIT_NEWS_BTN_ABSTRACT};
		checkedColor = ColorUtil.getCommonBlueColor(this);
		unCheckedColor = ColorUtil.getWhiteColor(this);
		// 初始化正文/摘要文本框的提示信息
		contentAndAbstractETHint = new String[]{ParamConst.EDIT_NEWS_CONTENT_EDITTEXT_HINT, ParamConst.EDIT_NEWS_ABSTRACT_EDITTEXT_HINT};
		// 定义当前选中的是正文
		curPosition = 0;
		// 保存正文/摘要内容的数组
		contentAndAbstractString = new String[contentAndAbstractETHint.length];
		// 在每一项中最多可以显示的字数
		allowLength = (ScreenTools.getPhoneWidth(this) * 9 / 10) / ParamConst.FONT_WIDTH;
		// 获取临时的频道bean
		channelBean = StaticUtil.getTmpChannelBean(this, true);
		// 实例化保存新闻所需的bean
		requestSaveNewsUrlBean = new RequestSaveNewsUrlBean();
		requestSaveNewsUrlBean.setChannelId(channelBean.getChannelId());
		initNewsId();
	}
	
	private void initPresenter() {
		presenter = new NewsEditCommonPresenter(this);
	}
	
	private void initHandler() {
		handler = new NewsEditCommonHandler(this);
	}
	
	private void initNewsId() {
		requestInitNewsIdUrlBean = new RequestGenerateNewsIdUrlBean();
		requestInitNewsIdUrlBean.setChannelId(channelBean.getChannelId());
		String url = StaticUtil.getCurScanBean(this).getScanUrl() + UrlConst.GENERATE_NEWS_ID;
		List<BasicNameValuePair> initData = BeanParamsUtil.initData(requestInitNewsIdUrlBean, this);
		presenter.generateNewsId(url, initData, handler);
	}
	
	@Override
	public void setNewsId(String resultString, Handler handler) {
		if (StringUtil.isNotEmpty(resultString)) {
			JSONObject jsonObject = JsonUtil.initJsonObject(resultString);
			generateNewsIdBean = packageBean(jsonObject);
			Message message = new Message();
			message.what = ParamConst.MESSAGE_WHAT_SUCCESS;
			message.obj = generateNewsIdBean;
			handler.sendMessage(message);
		} else {
			handler.sendEmptyMessage(0);
		}
	}
	
	private GenerateNewsIdBean packageBean(JSONObject jsonObject) {
		GenerateNewsIdBean generateNewsIdBean = (GenerateNewsIdBean) BeanParamsUtil.saveJsonToObject(jsonObject, GenerateNewsIdBean.class);
		return generateNewsIdBean;
	}

	private void initView() {
		initTitle();
		initReadingPic();
		initDifferentLayout();
		initChannelSearch();
		initTemplateSearch();
//		initKeyword();
		initContentAndAbstractBtns();
		initContentAndAbstractWidgets();
		initContentAndAbstractET();
		initEnclosure();
	}

	private void initEvent() {
		initTitleEvent();
		initReadingPicEvent();
		initChannelSearchEvent();
		initTemplateSearchEvent();
		initContentAndAbstractBtnEvent();
		initContentAndAbstractWidgetsEvent();
	}

	/**
	 * 初始化标头
	 */
	private void initTitle() {
		titleLeftTV = (TextView) findViewById(R.id.titleLeftTV);
		titleLayout = (LinearLayout) findViewById(R.id.titleLayout);
		initTitleBtns();
	}
	
	/**
	 * 初始化普通新闻和图文直播不同的layout
	 */
	private void initDifferentLayout() {
//		differentLayout = (LinearLayout) findViewById(R.id.differentLayout);
		addDifferentLayout();
	}

	/**
	 * 初始化新闻频道
	 */
	private void initChannelSearch() {
		// 初始化包裹“选择频道”的layout
		channelSearchLayout = (LinearLayout) findViewById(R.id.channelSearchLayout);
		// 初始化显示当前新闻频道的TextView
		channelSearchTV = (TextView) findViewById(R.id.channelSearchTV);
		// 获取当前应用选中的新闻频道，并将新闻频道树显示在channelSearchTV中
		String[] channelNamesTree = StaticUtil.getChannelNamesTree(this);
		String channelContent = StaticUtil.getChannelContent(allowLength, channelNamesTree);
		channelSearchTV.setText(channelContent);
		
	}
	
	private void initTemplateSearch() {
		templateSearchLayout = (LinearLayout) findViewById(R.id.templateSearchLayout);
		templateSearchTV = (TextView) findViewById(R.id.templateSearchTV);
	}
	
	/**
	 * 初始化导读图模块
	 */
	private void initReadingPic() {
		readingPicLayout = (RelativeLayout) findViewById(R.id.readingPicLayout);
		readingPicIV = (ImageView) findViewById(R.id.readingPicIV);
		addReadingPicTV = (TextView) findViewById(R.id.addReadingPicTV);
	}
	
	/**
	 * 初始化正文和摘要按钮组
	 */
	private void initContentAndAbstractBtns() {
		contentAndAbstractBtnsLayout = (LinearLayout) findViewById(R.id.contentAndAbstractBtnsLayout);
		contentAndAbstractBtns = ViewUtil.initBtnGroupLayout(this, contentAndAbstractBtnsLayout, btnText, btnId, 0.2f, checkedColor, unCheckedColor);
		
	}
	
	private void initContentAndAbstractWidgets() {
		resetTV = (TextView) findViewById(R.id.resetTV);
		composingTV = (TextView) findViewById(R.id.composingTV);
	}
	
	/**
	 * 初始化正文和摘要对应的输入文本域
	 */
	private void initContentAndAbstractET() {
		contentAndAbstractET = (EditText) findViewById(R.id.contentAndAbstractET);
		contentAndAbstractET.setHint(contentAndAbstractETHint[curPosition]);
	}
	
	public void changeToCurPosition(ViewColorBasicBean colorBasicBean, EnableSimpleChangeButton btn) {
		ViewUtil.changeBtnGroupStyleByFocusedState(this, contentAndAbstractBtnsLayout, curPosition, checkedColor, unCheckedColor);
		/*String curString = null;
		switch (curPosition) {
		case 0:
			// 正文
			curString = requestSaveNewsUrlBean.getContent();
			break;
		case 1:
			// 概要
			curString = requestSaveNewsUrlBean.getAbs();
			break;
		default:
			break;
		}*/
		String curString = contentAndAbstractString[curPosition];
		if (StringUtil.isEmpty(curString)) {
			contentAndAbstractET.setText(null);
			contentAndAbstractET.setHint(contentAndAbstractETHint[curPosition]);
		} else {
			contentAndAbstractET.setText(curString);
		}
	}
	
	/**
	 * 初始化附件
	 */
	private void initEnclosure() {
		newsLayout = (LinearLayout) findViewById(R.id.newsLayout);
		enclosureLayout = (RelativeLayout) findViewById(R.id.enclosureLayout);
		if (!hasEnclosure()) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) newsLayout.getLayoutParams();
			layoutParams.bottomMargin = 0;
			newsLayout.setLayoutParams(layoutParams);
			enclosureLayout.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 标头左侧的“返回”按钮添加点击事件
	 */
	private void initTitleEvent() {
		new CommonOnClickListener(titleLeftTV, true, ColorUtil.getLightBlue(this)) {

			@Override
			public void onClick(View v) {
				NewsEditCommonActivity.this.onBackPressed();
			}
		};
		initTitleBtnsEvent();
	}
	
	/**
	 * 导读图片添加点击事件
	 */
	private void initReadingPicEvent() {
		new CommonOnClickListener(readingPicLayout, false, 0) {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(NewsEditCommonActivity.this, "点击添加导读图片按钮", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(NewsEditCommonActivity.this, AddReadingPicActivity.class);
				NewsEditCommonActivity.this.startActivityForResult(intent, ParamConst.NEWS_EDIT_COMMON_ACTIVITY_TO_ADD_READING_PIC_ACTIVITY_REQUEST_CODE);
			}
		};
	}
	
	/**
	 * 选择频道添加点击事件
	 */
	private void initChannelSearchEvent() {
		channelSearchLayout.setOnClickListener(new CommonOnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewsEditCommonActivity.this, ChannelSearchActivity.class);
				intent.putExtra(ParamConst.CHANNEL_SEARCH_IS_TEMP, ParamConst.CHANNEL_SEARCH_IS_TEMP_YES);
				NewsEditCommonActivity.this.startActivityForResult(intent, ParamConst.NEWS_EDIT_COMMON_ACTIVITY_TO_CHANNEL_SEARCH_ACTIVITY_REQUEST_CODE);
			}
		});
	}
	
	/**
	 * 选择模版添加点击事件
	 */
	private void initTemplateSearchEvent() {
		new CommonOnClickListener(templateSearchLayout, false, 0) {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewsEditCommonActivity.this, TemplateSearchActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(ParamConst.TEMPLATE_TYPE, ParamConst.TEMPLATE_TYPE_NORMAL);
				String serializeObject = null;
				if (templateSearchBean != null) {
					serializeObject = SharedPreUtil.serializeObject(templateSearchBean);
				} else {
					serializeObject = "";
				}
				bundle.putString(ParamConst.CUR_TEMPLATE_FILE, serializeObject);
				bundle.putInt(ParamConst.LOAD_TEMPLATE_FILE_TYPE, ParamConst.LOAD_TEMPLATE_FILE_TYPE_NORMAL);
				intent.putExtras(bundle);
//				intent.putExtra(ParamConst.TEMPLATE_TYPE, ParamConst.TEMPLATE_TYPE_NORMAL);
				NewsEditCommonActivity.this.startActivityForResult(intent, ParamConst.NEWS_EDIT_COMMON_ACTIVITY_TO_TEMPLATE_SEARCH_ACTIVITY_REQUEST_CODE);
			}
		};
	}
	
	private void initContentAndAbstractBtnEvent() {
		for (EnableSimpleChangeButton btn : contentAndAbstractBtns) {
			btn.setOnClickListener(new CommonOnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 将当前选中的正文/摘要中的内容存入内存中
					/*String str = contentAndAbstractET.getText().toString();
					switch (curPosition) {
					case 0:
						// 正文
						requestSaveNewsUrlBean.setContent(str);
						break;
					case 1:
						// 摘要
						requestSaveNewsUrlBean.setAbs(str);
						break;
					default:
						break;
					}*/
					contentAndAbstractString[curPosition] = contentAndAbstractET.getText().toString();
					EnableSimpleChangeButton curBtn = (EnableSimpleChangeButton) v;
					curPosition = curBtn.getButtonId();
					ViewColorBasicBean colorBasicBean = new ViewColorBasicBean(NewsEditCommonActivity.this);
					changeToCurPosition(colorBasicBean, curBtn);
				}
			});
		}
	}
	
	private void initContentAndAbstractWidgetsEvent() {
		// 点击“重置”按钮
		new CommonOnClickListener(resetTV, true, ColorUtil.getBgGrayPress(this)) {
			
			@Override
			public void onClick(View v) {
				// 将正文进行重置
				contentAndAbstractString[0] = requestSaveNewsUrlBean.getContent();
				// 将摘要进行重置
				contentAndAbstractString[1] = requestSaveNewsUrlBean.getAbs();
				contentAndAbstractET.setText(contentAndAbstractString[curPosition]);
			}
		};
	}
	
	@Override
	public void quitNewsEditCommonActivity(String msg) {
		AnimUtil.hideRefreshFrame();
		ViewUtil.showAlertDialog(this, msg);
		this.onBackPressed();
	}
	
	@Override
	public Activity getActivity() {
		return this;
	}
	
	@Override
	public Context getContext() {
		return this;
	}
	
	@Override
	public RequestSaveNewsUrlBean getRequestSaveNewsUrlBean() {
		return requestSaveNewsUrlBean;
	}
	
	
}
