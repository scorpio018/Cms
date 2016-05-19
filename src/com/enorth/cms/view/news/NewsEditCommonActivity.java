package com.enorth.cms.view.news;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.enorth.cms.bean.ViewColorBasicBean;
import com.enorth.cms.common.EnableSimpleChangeButton;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.CommonOnClickListener;
import com.enorth.cms.utils.ColorUtil;
import com.enorth.cms.utils.ImageLoader;
import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.utils.StaticUtil;
import com.enorth.cms.utils.StringUtil;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.R;
import com.squareup.okhttp.RequestBody;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	
	private RequestBody readingPicPath;
	/**
	 * 普通新闻和图文直播不同的layout
	 */
	protected LinearLayout differentLayout;
	/**
	 * 用于显示当前选中的新闻频道
	 */
	private TextView channelSearchTV;
	/**
	 * 关键词layout
	 */
	protected LinearLayout keywordLayout;
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
	protected LinearLayout enclosureLayout;
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
	private String[] contentAndAbstractString;
	/**
	 * 正文/摘要的内容
	 */
	private EditText contentAndAbstractET;
	/**
	 * 正文/摘要的EditText对应的提示信息
	 */
	private String[] contentAndAbstractETHint;
	/**
	 * 当前点击的按钮
	 */
	private int curPosition;
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
	public abstract void addKeyword();
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
			case ParamConst.ADD_READING_PIC_ACTIVITY_BACK_TO_NEWS_EIDT_COMMON_ACTIVITY_DEL_READING_PIC_RESULT_CODE:
				readingPicIV.setImageBitmap(null);
				break;
			case ParamConst.ADD_READING_PIC_ACTIVITY_BACK_TO_NEWS_EIDT_COMMON_ACTIVITY_HAS_IMG_DATAS_RESULT_CODE:
				Bundle bundle = data.getExtras();
				ArrayList<String> imgDatas = bundle.getStringArrayList(ParamConst.IMG_DATAS);
				if (imgDatas.size() != 1) {
					ViewUtil.showAlertDialog(this, "只能选择一张做导读图");
				}
				
				ImageLoader.getInstance().initBitmapByLocalPath(readingPicIV, imgDatas.get(0));
				readingPicPath = RequestBody.create(ParamConst.MEDIA_TYPE_PNG, new File(imgDatas.get(0)));
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * 初始化基本数据
	 */
	private void initBasicData() {
		// 正文/摘要基本数据初始化
		btnText = new String[]{"正文", "摘要"};
		btnId = new int[]{ParamConst.EDIT_NEWS_BTN_CONTENT, ParamConst.EDIT_NEWS_BTN_ABSTRACT};
		checkedColor = ColorUtil.getCommonBlueColor(this);
		unCheckedColor = ColorUtil.getWhiteColor(this);
		contentAndAbstractETHint = new String[]{ParamConst.EDIT_NEWS_CONTENT_EDITTEXT_HINT, ParamConst.EDIT_NEWS_ABSTRACT_EDITTEXT_HINT};
		curPosition = 0;
		contentAndAbstractString = new String[contentAndAbstractETHint.length];
	}

	private void initView() {
		initTitle();
		initReadingPic();
		initDifferentLayout();
		initChannelSearch();
		initKeyword();
		initContentAndAbstractBtns();
		initContentAndAbstractET();
		initEnclosure();
	}

	private void initEvent() {
		initTitleEvent();
		initReadingPicEvent();
		initContentAndAbstractBtnEvent();
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
		differentLayout = (LinearLayout) findViewById(R.id.differentLayout);
		addDifferentLayout();
	}

	/**
	 * 初始化新闻频道
	 */
	private void initChannelSearch() {
		allowLength = (ScreenTools.getPhoneWidth(this) * 9 / 10) / ParamConst.FONT_WIDTH;
		channelSearchTV = (TextView) findViewById(R.id.channelSearchTV);
		String[] channelNamesTree = StaticUtil.getChannelNamesTree(this);
		String channelContent = StaticUtil.getChannelContent(allowLength, channelNamesTree);
		channelSearchTV.setText(channelContent);
	}
	
	/**
	 * 初始化导读图模块
	 */
	private void initReadingPic() {
		readingPicLayout = (RelativeLayout) findViewById(R.id.readingPicLayout);
		readingPicIV = (ImageView) findViewById(R.id.readingPicIV);
	}
	/**
	 * 初始化关键词
	 */
	private void initKeyword() {
		keywordLayout = (LinearLayout) findViewById(R.id.keywordLayout);
		addKeyword();
	}
	
	private void initContentAndAbstractBtns() {
		contentAndAbstractBtnsLayout = (LinearLayout) findViewById(R.id.contentAndAbstractBtnsLayout);
		contentAndAbstractBtns = ViewUtil.initBtnGroupLayout(this, contentAndAbstractBtnsLayout, btnText, btnId, 0.2f, checkedColor, unCheckedColor);
		
	}
	
	private void initContentAndAbstractET() {
		contentAndAbstractET = (EditText) findViewById(R.id.contentAndAbstractET);
		contentAndAbstractET.setHint(contentAndAbstractETHint[curPosition]);
	}
	
	public void changeToCurPosition(ViewColorBasicBean colorBasicBean, EnableSimpleChangeButton btn) {
		ViewUtil.changeBtnGroupStyleByFocusedState(this, contentAndAbstractBtnsLayout, curPosition, checkedColor, unCheckedColor);
		String curString = contentAndAbstractString[curPosition];
		if (StringUtil.isEmpty(curString)) {
			contentAndAbstractET.setText(null);
			contentAndAbstractET.setHint(contentAndAbstractETHint[curPosition]);
		} else {
			contentAndAbstractET.setText(contentAndAbstractString[curPosition]);
		}
	}
	
	/**
	 * 初始化附件
	 */
	private void initEnclosure() {
		newsLayout = (LinearLayout) findViewById(R.id.newsLayout);
		enclosureLayout = (LinearLayout) findViewById(R.id.enclosureLayout);
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
	
	private void initContentAndAbstractBtnEvent() {
		for (EnableSimpleChangeButton btn : contentAndAbstractBtns) {
			btn.setOnClickListener(new CommonOnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 将当前选中的正文/摘要中的内容存入内存中
					contentAndAbstractString[curPosition] = contentAndAbstractET.getText().toString();
					EnableSimpleChangeButton curBtn = (EnableSimpleChangeButton) v;
					curPosition = curBtn.getButtonId();
					ViewColorBasicBean colorBasicBean = new ViewColorBasicBean(NewsEditCommonActivity.this);
					changeToCurPosition(colorBasicBean, curBtn);
				}
			});
		}
	}
}
