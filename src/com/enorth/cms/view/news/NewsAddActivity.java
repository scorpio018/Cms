package com.enorth.cms.view.news;

import java.util.List;

import com.enorth.cms.bean.UrlInitDataBean;
import com.enorth.cms.bean.news_list.RequestSaveNewsUrlBean;
import com.enorth.cms.bean.news_list.TemplateSearchBean;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.consts.UrlConst;
import com.enorth.cms.handler.newslist.NewsAddHandler;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.listener.newslist.newsedit.ConvergentNewsOnCheckedChangeListener;
import com.enorth.cms.presenter.newslist.INewsAddPresenter;
import com.enorth.cms.presenter.newslist.NewsAddPresenter;
import com.enorth.cms.utils.BeanParamsUtil;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.SharedPreUtil;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.StringUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 普通新闻添加
 * 
 * @author yangyang
 *
 */
public class NewsAddActivity extends NewsEditCommonActivity implements INewsAddView {
	/**
	 * 送签
	 */
	private TextView songqianTV;
	/**
	 * 保存
	 */
	private TextView saveTV;
	/**
	 * 预览
	 */
	private TextView previewTV;
	/**
	 * 存退
	 */
	private TextView saveAndQuitTV;
	/**
	 * 移动标题的layout
	 */
	private LinearLayout mobileTitleVisibleLayout;
	/**
	 * 移动标题
	 */
	private EditText mobileTitleET;
	/**
	 * 稿源layout
	 */
	private LinearLayout manuscriptsLayout;
	/**
	 * 稿源
	 */
	private TextView manuscripts;
	/**
	 * 关键词layout
	 */
//	private LinearLayout keywordLayout;
//	private LinearLayout keywordItem;
	/**
	 * 关键词
	 */
	private EditText keywordET;
	/**
	 * 左下角的“融合新闻”复选框
	 */
	private CheckBox convergentNewsCB;
	/**
	 * 右下角的附件个数标识
	 */
	private TextView enclosureCount;
	/**
	 * “附件”按钮
	 */
	private TextView enclosure;
	/**
	 * 4g模板最外层的layout
	 */
	private LinearLayout templateSearch4gVisibleLayout;
	/**
	 * 需要添加点击事件的4g模板layout
	 */
	private LinearLayout templateSearch4gLayout;
	/**
	 * 显示选择的4g模板的TextView
	 */
	private TextView templateSearch4gTV;

	private TemplateSearchBean templateSearchBean;
	
	private INewsAddPresenter presenter;
	
	private NewsAddHandler handler;
	
	@Override
	public int setContentView() {
		return R.layout.activity_news_add;
	}
	
	@Override
	public void initTitleBtns() {
		RelativeLayout btnsLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.edit_news_title_edit_btns,
				null);
		songqianTV = (TextView) btnsLayout.findViewById(R.id.songqianTV);
		saveTV = (TextView) btnsLayout.findViewById(R.id.saveTV);
		previewTV = (TextView) btnsLayout.findViewById(R.id.previewTV);
		saveAndQuitTV = (TextView) btnsLayout.findViewById(R.id.saveAndQuitTV);
		titleLayout.addView(btnsLayout);
	}

	@Override
	public void initTitleBtnsEvent() {
		// 送签点击事件
		new CommonOnClickListener(songqianTV, true, ColorUtil.getLightBlue(this)) {

			@Override
			public void onClick(View v) {
				Toast.makeText(NewsAddActivity.this, "点击了送签按钮", Toast.LENGTH_SHORT).show();
			}
		};

		// 保存点击事件
		new CommonOnClickListener(saveTV, true, ColorUtil.getLightBlue(this)) {

			@Override
			public void onClick(View v) {
				Toast.makeText(NewsAddActivity.this, "点击了保存按钮", Toast.LENGTH_SHORT).show();
				saveNews();
			}
		};

		// 预览点击事件
		new CommonOnClickListener(previewTV, true, ColorUtil.getLightBlue(this)) {

			@Override
			public void onClick(View v) {
				Toast.makeText(NewsAddActivity.this, "点击了预览按钮", Toast.LENGTH_SHORT).show();
			}
		};
		
		// 存退点击事件
		new CommonOnClickListener(saveAndQuitTV, true, ColorUtil.getLightBlue(this)) {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(NewsAddActivity.this, "点击了存退按钮", Toast.LENGTH_SHORT).show();
			}
		};
	}
	
	private void saveNews() {
		boolean checkCanSave = checkCanSave();
		if (checkCanSave) {
			RequestSaveNewsUrlBean requestSaveNewsUrlBean = getRequestSaveNewsUrlBean();
			// 保存新闻标题
			requestSaveNewsUrlBean.setTitle(addReadingPicTV.getText().toString());
			// 保存关键词
			requestSaveNewsUrlBean.setKeywords(keywordET.getText().toString());
			// 保存正文和摘要
			contentAndAbstractString[curPosition] = contentAndAbstractET.getText().toString();
			requestSaveNewsUrlBean.setContent(contentAndAbstractString[0]);
			requestSaveNewsUrlBean.setAbs(contentAndAbstractString[1]);
			// 在添加新闻时，将orgState设置成-100
			requestSaveNewsUrlBean.setOrgState(ParamConst.STATE_ADD);
			requestSaveNewsUrlBean.setNewState(ParamConst.STATE_EDIT);
//			List<BasicNameValuePair> initData = BeanParamsUtil.initData(requestSaveNewsUrlBean, this);
			List<UrlInitDataBean> initData = BeanParamsUtil.initMultiData(requestSaveNewsUrlBean, this);
			presenter = new NewsAddPresenter(this);
			handler = new NewsAddHandler(this);
			String url = StaticUtil.getCurScanBean(this).getScanUrl() + UrlConst.SAVE_NEWS;
//			String url = UrlConst.TEST_URL;
			presenter.saveNews(url, initData, handler);
		}
	}
	
	private boolean checkCanSave() {
		RequestSaveNewsUrlBean requestSaveNewsUrlBean = getRequestSaveNewsUrlBean();
		String title = addReadingPicTV.getText().toString();
		if (StringUtil.isEmpty(title)) {
			ViewUtil.showAlertDialog(this, "请输入标题");
			return false;
		}
		long channelId = requestSaveNewsUrlBean.getChannelId();
		if (channelId == -1L) {
			ViewUtil.showAlertDialog(this, "请选择新闻频道");
			return false;
		}
		int templateId = requestSaveNewsUrlBean.getTemplateId();
		if (templateId == -1) {
			ViewUtil.showAlertDialog(this, "请选择新闻模版");
			return false;
		}
		return true;
	}

	@Override
	public void addDifferentLayout() {
		addMobileTitle();
		addManuscripts();
		addKeyword();
		addTemplateSearch4g();
	}
	
	/**
	 * 添加移动标题
	 */
	private void addMobileTitle() {
		mobileTitleVisibleLayout = (LinearLayout) findViewById(R.id.mobileTitleVisibleLayout);
		mobileTitleET = (EditText) mobileTitleVisibleLayout.findViewById(R.id.mobileTitleET);
	}
	/**
	 * 添加4g模板
	 */
	private void addTemplateSearch4g() {
		templateSearch4gVisibleLayout = (LinearLayout) findViewById(R.id.templateSearch4gVisibleLayout);
		templateSearch4gLayout = (LinearLayout) findViewById(R.id.templateSearch4gLayout);
		templateSearch4gTV = (TextView) findViewById(R.id.templateSearch4gTV);
		new CommonOnClickListener(templateSearch4gLayout, false, 0) {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewsAddActivity.this, TemplateSearchActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(ParamConst.TEMPLATE_TYPE, ParamConst.TEMPLATE_TYPE_NORMAL);
				String serializeObject = null;
				if (templateSearchBean != null) {
					serializeObject = SharedPreUtil.serializeObject(templateSearchBean);
				} else {
					serializeObject = "";
				}
				
				bundle.putString(ParamConst.CUR_TEMPLATE_FILE, serializeObject);
				bundle.putInt(ParamConst.LOAD_TEMPLATE_FILE_TYPE, ParamConst.LOAD_TEMPLATE_FILE_TYPE_4G);
				intent.putExtras(bundle);
//				intent.putExtra(ParamConst.TEMPLATE_TYPE, ParamConst.TEMPLATE_TYPE_NORMAL);
				NewsAddActivity.this.startActivityForResult(intent, ParamConst.NEWS_ADD_ACTIVITY_TO_TEMPLATE_SEARCH_ACTIVITY_REQUEST_CODE);
			}
		};
	}
	
	/**
	 * 添加稿源
	 */
	private void addManuscripts() {
//		differentItem = (LinearLayout) getLayoutInflater().inflate(R.layout.normal_edit_news_diff_item, null);
		manuscriptsLayout = (LinearLayout) findViewById(R.id.manuscriptsLayout);
		manuscripts = (TextView) findViewById(R.id.manuscripts);
//		differentLayout.addView(differentItem);
		manuscriptsLayout.setOnClickListener(new CommonOnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewsAddActivity.this, AddManuscriptsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(ParamConst.MANUSCRIPTS, NewsAddActivity.this.manuscripts.getText().toString());
				intent.putExtras(bundle);
				NewsAddActivity.this.startActivityForResult(intent, ParamConst.NEWS_ADD_ACTIVITY_TO_ADD_MANUSCRIPTS_ACTIVITY_REQUEST_CODE);
			}
		});
	}
	
	/**
	 * 初始化关键词
	 */
	private void addKeyword() {
		keywordET = (EditText) findViewById(R.id.addKeywordET);
//		keywordLayout = (LinearLayout) findViewById(R.id.keywordLayout);
//		keywordItem = (LinearLayout) getLayoutInflater().inflate(R.layout.normal_edit_news_keyword, null);
//		keywordLayout = (LinearLayout) findViewById(R.id.keywordLayout);
//		keywordTV = (TextView) findViewById(R.id.keywordTV);
		/*keywordLayout.setOnClickListener(new CommonOnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewsAddActivity.this, AddKeywordActivity.class);
				NewsAddActivity.this.startActivityForResult(intent, ParamConst.NEWS_ADD_ACTIVITY_TO_ADD_KEYWORD_ACTIVITY_REQUEST_CODE);
			}
		});*/
//		keywordLayout.addView(keywordItem);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case ParamConst.NEWS_ADD_ACTIVITY_TO_ADD_MANUSCRIPTS_ACTIVITY_REQUEST_CODE:
			switch (resultCode) {
			case ParamConst.ADD_MANUSCRIPTS_ACTIVITY_BACK_TO_NEWS_ADD_ACTIVITY_RESULT_CODE:
				Bundle bundle = data.getExtras();
				if (bundle != null) {
					String manuscripts = bundle.getString(ParamConst.MANUSCRIPTS);
					NewsAddActivity.this.manuscripts.setText(manuscripts);
					// 添加稿源
					NewsAddActivity.super.getRequestSaveNewsUrlBean().setSourceName(manuscripts);
				}
				break;

			default:
				break;
			}
		case ParamConst.NEWS_ADD_ACTIVITY_TO_TEMPLATE_SEARCH_ACTIVITY_REQUEST_CODE:
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
					templateSearch4gTV.setText(templateSearchBean.getTemplateName());
//					requestSaveNewsUrlBean.setTemplateId(templateSearchBean.getTemplateId());
					super.getRequestSaveNewsUrlBean().setTemplateId4g(templateSearchBean.getTemplateId());
				}
				break;
				default:
					break;
			}
		default:
			break;
		}
	}

	@Override
	public boolean hasEnclosure() {
		initEnclosureLayout();
		return true;
	}
	
	private void initEnclosureLayout() {
		initConvergentNewsCB();
		initEnclosure();
	}

	private void initConvergentNewsCB() {
		convergentNewsCB = (CheckBox) findViewById(R.id.convergentNewsCB);
		if (generateNewsIdBean != null) {
			if (generateNewsIdBean.getShowConv() == ParamConst.IS_CONV_YES) {
				convergentNewsCB.setVisibility(View.VISIBLE);
			}
		}
		convergentNewsCB.setOnCheckedChangeListener(new ConvergentNewsOnCheckedChangeListener(this));
		boolean checked = convergentNewsCB.isChecked();
		changeVisibleByConvergentNewsCB(checked);
		
	}
	
	private void initEnclosure() {
		enclosureCount = (TextView) findViewById(R.id.enclosureCount);
		enclosureCount.setText("6");
		enclosure = (TextView) findViewById(R.id.enclosure);
		new CommonOnClickListener(enclosure, false, 0) {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewsAddActivity.this, NewsAddChooseEnclosureActivity.class);
				intent.putExtra(ParamConst.BROADCAST_ACTION, ParamConst.UPLOAD_NEWS_ATT);
				NewsAddActivity.this.startActivity(intent);
			}
		};
	}

	@Override
	public void isConvergentNews(boolean isChecked) {
		changeVisibleByConvergentNewsCB(isChecked);
	}
	
	private void changeVisibleByConvergentNewsCB(boolean isChecked) {
		if (isChecked) {
			// 显示“移动标题”和“4g模板”
			mobileTitleVisibleLayout.setVisibility(View.VISIBLE);
			templateSearch4gVisibleLayout.setVisibility(View.VISIBLE);
			super.getRequestSaveNewsUrlBean().setConv(ParamConst.IS_CONV_YES);
		} else {
			// 隐藏“移动标题”和“4g模板”
			mobileTitleVisibleLayout.setVisibility(View.GONE);
			templateSearch4gVisibleLayout.setVisibility(View.GONE);
			super.getRequestSaveNewsUrlBean().setConv(ParamConst.IS_CONV_NO);
		}
	}

	@Override
	public void afterSaveNews(String resultString, Handler handler) {
		String htmlUrl = resultString;
		Message msg = new Message();
		msg.what = ParamConst.MESSAGE_WHAT_SUCCESS;
		msg.obj = htmlUrl;
		handler.sendMessage(msg);
	}
	
	public void addCmsBlock(String cmsBlockHtmlCode) {
		String str = contentAndAbstractET.getText().toString();
		contentAndAbstractET.setText(cmsBlockHtmlCode + "\n" + str);
	}
}
