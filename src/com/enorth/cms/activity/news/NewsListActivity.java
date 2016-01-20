package com.enorth.cms.activity.news;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.chromium.mojo.system.Handle;

import com.enorth.cms.activity.R;
import com.enorth.cms.adapter.ListViewViewPagerAdapter;
import com.enorth.cms.adapter.NewsListViewAdapter;
import com.enorth.cms.bean.BottomMenuBasicBean;
import com.enorth.cms.bean.ButtonColorBasicBean;
import com.enorth.cms.bean.ImageViewBasicBean;
import com.enorth.cms.common.EnableSimpleChangeButton;
import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.listener.bottom_menu.BottomMenuOnTouchListener;
import com.enorth.cms.listener.imageview.ImageViewOnTouchListener;
import com.enorth.cms.utils.LayoutParamsUtil;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewsListActivity extends Activity implements OnPageChangeListener {
	
	/**
	 * 底部的操作按钮布局
	 */
	private LinearLayout newsOperateBtnLayout;
	/**
	 * 新闻操作类型的按钮布局（待编辑、待签发、已签发），外层需要套一个RelativeLayout以保证按钮能水平居中
	 */
	private LinearLayout newsTypeBtnLineLayout;
	/**
	 * 新闻列表的ViewPager，里面根据newsTypeBtnText的length放相应的ListView
	 */
	private ViewPager newsListViewPager;
	/**
	 * 将ViewPager中的ListView存入此集合中
	 */
	private List<View> views;
	/**
	 * 屏幕认定滑动的最大位移
	 */
	private int touchSlop;
	/**
	 * 选中的新闻总和
	 */
	private int selectedNewsCount = 0;
	/**
	 * 切换新闻列表的标头按钮
	 */
	private String[] newsTypeBtnText = {"待编辑", "待签发", "已签发"};
	/**
	 * 当前选中的标头按钮
	 */
	private int curFocusBtn = 0;
	/**
	 * 用于点击newsTypeBtn时进行ViewPager的切换
	 * 调用arrowScroll方法用参数1或者17就可以实现向左翻页；参数2或66就可以实现向右翻页。
	 * 注：当UI中有EditText这种获得focus的widget时，则必须用17和66，否则要报错。
	 */
	private int focusLeft = 1;
	
	private int focusRight = 2;
	/**
	 * 底部菜单选中时的图片样式（按照修改、批注、送签、删除的顺序排列）
	 */
	private int[] newsOperateBtnChecked = {R.drawable.operate_btn_xiugai_checked, 
			R.drawable.operate_btn_pizhu_checked, R.drawable.operate_btn_songqian_checked,
			R.drawable.operate_btn_shanchu_checked};
	/**
	 * 底部菜单禁止点击时的图片样式（按照修改、批注、送签、删除的顺序排列）
	 */
	private int[] newsOperateBtnDisabled = {R.drawable.operate_btn_xiugai_uncheck, 
			R.drawable.operate_btn_pizhu_uncheck, R.drawable.operate_btn_songqian_uncheck,
			R.drawable.operate_btn_shanchu_uncheck};
	/**
	 * 底部菜单的文字描述
	 */
	private String[] newsOperateBtnTextContent = {"修改", "批注", "送签", "删除"};
	/**
	 * 底部菜单禁止点击时如果点击，所提示的信息
	 */
	private String[] disableHint = {"必须选择且仅选择一个新闻时才可以进行修改", "必须至少选择一个新闻才能进行批注", "必须至少选择一个新闻才能进行送签", "必须至少选择一个新闻才能进行删除"};
	/**
	 * 底部菜单可以变为可以点击的条件
	 * 跟新闻列表的选中个数进行联动，有三种状态：
	 * 1.都没选中【ParamConst.CAN_ENABLE_STATE_DEFAULT】
	 * 2.只选中了一个【ParamConst.CAN_ENABLE_STATE_SIMPLE】
	 * 3.选中了至少一个【ParamConst.CAN_ENABLE_STATE_MORE】
	 */
	private int[] newsOperateBtnCanEnableState = {ParamConst.CAN_ENABLE_STATE_SIMPLE, ParamConst.CAN_ENABLE_STATE_MORE, ParamConst.CAN_ENABLE_STATE_MORE, ParamConst.CAN_ENABLE_STATE_MORE};
	/**
	 * 当底部菜单可以点击时，文字显示的颜色（按照修改、批注、送签、删除的顺序排列）
	 */
	private int[] newsOperateBtnColor = {R.color.bottom_text_color_blue, R.color.bottom_text_color_green, R.color.bottom_text_color_yellow, R.color.bottom_text_color_red};
	/**
	 * 底部菜单的默认颜色
	 */
	private int newsOperateBtnBasicColor;
	/**
	 * 白色
	 */
	private int whiteColor;
	/**
	 * 蓝色
	 */
	private int blueColor;
	/**
	 * 底部菜单的基本数据集合
	 */
	private List<BottomMenuBasicBean> bottomMenuList;
	
//	private RadioGroup newsOperateRadioGroup;
	/**
	 * 当前新闻列表联动底部菜单可以操作的状态值（默认都没选中）
	 */
	private int canEnableState = ParamConst.CAN_ENABLE_STATE_DEFAULT;
	/**
	 * 当前activity（用于在匿名内部类中获取当前activity）
	 */
	private NewsListActivity thisActivity;
	/**
	 * 进行日期格式化
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Handler mHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);
        }  
    };  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 初始化底部RadioButton菜单，由于要动态添加，所以需要在setContentView之前添加
//		initNewsOperateRadioGroupBtn();
		setContentView(R.layout.activity_news_list);
		// 加载基本参数
		initBasicData();
		try {
			// 初始化新闻操作类型的按钮布局（待编辑、待签发、已签发）
			initNewsTypeBtnLayout();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		initNewsListLayout();
		// 加载ViewPager
		initViewPager();
		// 加载底部菜单
		initNewsOperateBtnLayout();
		
	}
	
	/**
	 * 初始化所需的基本参数
	 */
	private void initBasicData() {
		// 初始化认定滑动的最大位移
		touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
		// 将当前activity存入全局变量，用于匿名内部类中实现的方法的使用
		this.thisActivity = this;
		newsOperateBtnBasicColor = ContextCompat.getColor(thisActivity, R.color.bottom_text_color_basic);
		whiteColor = ContextCompat.getColor(this, R.color.white);
		blueColor = ContextCompat.getColor(this, R.color.common_blue);
	}
	
	/**
	 * 初始化新闻操作类型的按钮布局（待编辑、待签发、已签发）
	 * @throws Exception 
	 */
	private void initNewsTypeBtnLayout() throws Exception {
		RelativeLayout newsTypeBtnRelaLayout = (RelativeLayout) findViewById(R.id.newsTypeBtnRelaLayout);
		newsTypeBtnLineLayout = (LinearLayout) newsTypeBtnRelaLayout.getChildAt(0);
		int length = newsTypeBtnText.length;
		// 此处初始化待编辑、待签发、已签发三个按钮的基本样式
		LayoutParams params = LayoutParamsUtil.initEnableSimpleChangeButtonLayout(getResources());
		for (int i = 0; i < length; i++) {
			EnableSimpleChangeButton btn = new EnableSimpleChangeButton(this);
			btn.setText(newsTypeBtnText[i]);
			ButtonColorBasicBean colorBasicBean = new ButtonColorBasicBean(this);
			boolean needFocused = i == 0 ? true : false;
			initNewsTypeBtnStyleByFocusedState(colorBasicBean, needFocused);
			/*if (i == 0) {
				colorBasicBean.setmBgNormalColor(blueColor);
				colorBasicBean.setmTextNormalColor(whiteColor);
			} else {
				colorBasicBean.setmBgNormalColor(whiteColor);
				colorBasicBean.setmTextNormalColor(blueColor);
			}*/
			btn.setColorBasicBean(colorBasicBean);
			final int position = i;
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 调用arrowScroll方法用参数1或者17就可以实现向左翻页；参数2或66就可以实现向右翻页。
					// 注：当UI中有EditText这种获得focus的widget时，则必须用17和66，否则要报错。
					if (position > curFocusBtn) {
						// 右
						for (int j = curFocusBtn; j <= position; j++) {
							newsListViewPager.arrowScroll(focusRight);
						}
					} else {
						// 左
						for (int j = curFocusBtn; j >= position; j++) {
							newsListViewPager.arrowScroll(focusLeft);
						}
					}
//					try {
//						changeNewsTypeBtnStyleByFocusedState(position);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
				}
			});
			newsTypeBtnLineLayout.addView(btn, params);
		}
	}
	
	/**
	 * 根据当前选中的标头按钮的位置改变需要改变样式的按钮，并清除需要清除的ListView中的数据（只要在当前ListView前后超过一个间隔，则清空）
	 * @param position
	 * @throws Exception
	 */
	private void changeNewsTypeBtnStyleByFocusedState(int position) throws Exception {
		int childCount = newsTypeBtnLineLayout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			EnableSimpleChangeButton btn = (EnableSimpleChangeButton) newsTypeBtnLineLayout.getChildAt(i);
			ButtonColorBasicBean colorBasicBean = new ButtonColorBasicBean(this);
			if (i == position) {
				initNewsTypeBtnStyleByFocusedState(colorBasicBean, true);
			} else {
				initNewsTypeBtnStyleByFocusedState(colorBasicBean, false);
				if (Math.abs(position - i) > 1) {
					ListView listView = (ListView) newsListViewPager.getChildAt(i);
					listView.removeAllViews();
				}
			}
			btn.setColorBasicBean(colorBasicBean);
		}
	}
	
	/**
	 * 通过是否需要选中的标识进行ButtonColorBasicBean的样式变更操作
	 * @param colorBasicBean
	 * @param needFocused
	 */
	private void initNewsTypeBtnStyleByFocusedState(ButtonColorBasicBean colorBasicBean, boolean needFocused) {
		if (needFocused) {
			// 需要选中
			colorBasicBean.setmBgNormalColor(blueColor);
			colorBasicBean.setmTextNormalColor(whiteColor);
		} else {
			colorBasicBean.setmBgNormalColor(whiteColor);
			colorBasicBean.setmTextNormalColor(blueColor);
		}
		
	}
	
	/**
	 * 初始化新闻列表的内容布局（注释原因：使用viewPager，展现效果有变）
	 */
	/*private void initNewsListLayout() {
		newsListView = (ListView) findViewById(R.id.newsListView);
		List<View> items = initData();
//		ArrayAdapter<View> adapter = new NewsListViewAdapter(this, R.layout.news_item, items);
		ListAdapter adapter = new NewsListViewAdapter(items);
		newsListView.setAdapter(adapter);
	}*/
	
	private void initViewPager() {
		newsListViewPager = (ViewPager) findViewById(R.id.newsListViewPager);
		views = new ArrayList<View>();
		for (int i = 0; i < 3; i++) {
			ListView newsListView = new ListView(this);
			views.add(newsListView);
			if (i == 0) {
				initNewsListData(newsListView);
			}
		}
		ListViewViewPagerAdapter adapter = new ListViewViewPagerAdapter(views);
		newsListViewPager.setAdapter(adapter);
		newsListViewPager.addOnPageChangeListener(this);
	}
	
	/**
	 * 初始化新闻列表的内容布局
	 */
	private void initNewsListData(ListView newsListView) {
		List<View> items = initData();
		ListAdapter adapter = new NewsListViewAdapter(items);
		newsListView.setAdapter(adapter);
	}
	
	/*private void initNewsOperateRadioGroupBtn() {
		// TODO 如果将activity_news_list中的newsOperateRadioGroup注释去掉，则要把此处代码注释也去掉
		newsOperateRadioGroup = (RadioGroup) findViewById(R.id.newsOperateRadioGroup);
		initNewsOperateRadioBtn();
	}*/
	
	/*private void initNewsOperateRadioBtn() {
		final List<RadioButtonBasicBean> resultList = initNewsOperateRadioBtnData();
		int childCount = newsOperateRadioGroup.getChildCount();
		for (int i = 0; i < childCount; i++) {
			RadioButtonBasicBean bean = resultList.get(i);
			RadioButton radioBtn = (RadioButton) newsOperateRadioGroup.getChildAt(i);
			radioBtn.setBackgroundResource(bean.getImageDisableResource());
			radioBtn.setSelected(false);
			radioBtn.setEnabled(bean.isEnable());
			radioBtn.setText(bean.getTextContent());
		}
	}*/
	
	/*private void initNewsOperateRadioBtn() {
		LayoutInflater inflater = LayoutInflater.from(this);
		final List<RadioButtonBasicBean> resultList = initNewsOperateRadioBtnData();
		for (RadioButtonBasicBean bean : resultList) {
			RadioButton radioBtn = (RadioButton) inflater.inflate(R.layout.news_operate_radio_btn, null);
			radioBtn.setBackgroundResource(bean.getImageDisableResource());
			radioBtn.setSelected(false);
			radioBtn.setEnabled(bean.isEnable());
			radioBtn.setText(bean.getTextContent());
			newsOperateRadioGroup.addView(radioBtn);
		}
	}*/
	
	/**
	 * 初始化底部的操作按钮布局
	 */
	private void initNewsOperateBtnLayout() {
		// TODO 如果将activity_news_list中的newsOperateBtnLayout注释去掉，则要把此处代码注释也去掉
		newsOperateBtnLayout = (LinearLayout) findViewById(R.id.newsOperateBtnLayout);
		initNewsOperateBtn();
	}
	
	private List<View> initData() {
		List<View> resultView = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(this);
		Date now = new Date();
		String nowDate = sdf.format(now);
		for (int i = 0; i < 25; i++) {
			ImageViewBasicBean bean = new ImageViewBasicBean();
			View view = inflater.inflate(R.layout.news_item, null);
			bean.setView(view);
			final ImageView checkBtn = (ImageView) view.findViewById(R.id.iv_check_btn);
			bean.setImageView(checkBtn);
			checkBtn.setImageResource(R.drawable.uncheck_btn);
			checkBtn.setSelected(false);
			addCheckBtnClickEvent(bean);
			TextView newsTitle = (TextView) view.findViewById(R.id.tv_news_title);
			newsTitle.setText("今天是" + (i + 1) + "号，我感觉好饿");
			TextView newsTime = (TextView) view.findViewById(R.id.tv_news_time);
//			newsTime.setText("2016-01-14 16:" + (55 + i) + ":18");
			newsTime.setText(nowDate);
			TextView newsAuthorName = (TextView) view.findViewById(R.id.tv_news_author_name);
			newsAuthorName.setText("杨洋");
			resultView.add(view);
		}
		return resultView;
	}
	
	private void addCheckBtnClickEvent(final ImageViewBasicBean bean) {
		
		bean.setImageCheckedResource(R.drawable.check_btn);
		bean.setImageUncheckResource(R.drawable.uncheck_btn);
		ImageViewOnTouchListener listener = new ImageViewOnTouchListener(bean, touchSlop) {
			@Override
			public boolean onImgChangeBegin() {
				return true;
			}

			@Override
			public void onImgChangeEnd() {
				if (bean.getImageView().isSelected()) {
					selectedNewsCount++;
				} else {
					selectedNewsCount--;
				}
				changeCanEnableState();
			}
		};
		bean.getView().setOnTouchListener(listener);
		bean.getImageView().setOnTouchListener(listener);
	}
	
	private void changeCanEnableState() {
		if (selectedNewsCount == 0) {
			canEnableState = ParamConst.CAN_ENABLE_STATE_DEFAULT;
		} else if (selectedNewsCount == 1) {
			canEnableState = ParamConst.CAN_ENABLE_STATE_SIMPLE;
		} else if (selectedNewsCount >= 2) {
			canEnableState = ParamConst.CAN_ENABLE_STATE_MORE;
		}
		changeBottomMenuBtnState();
	}
	
	private void changeBottomMenuBtnState() {
		int i = 0;
		for (BottomMenuBasicBean bean : bottomMenuList) {
//			ImageView iv = bean.getImageView();
			final LinearLayout layout = (LinearLayout) newsOperateBtnLayout.getChildAt(i);
			final ImageView iv = (ImageView) layout.getChildAt(0);
			final TextView tv = (TextView) layout.getChildAt(1);
			if (bean.getCanEnableState() == canEnableState) {
				iv.setImageResource(bean.getImageCheckedResource());
				iv.setSelected(true);
				iv.setEnabled(true);
				bean.setEnable(true);
				int color = ContextCompat.getColor(thisActivity, newsOperateBtnColor[i]);
				tv.setTextColor(color);
			} else {
				iv.setImageResource(bean.getImageDisableResource());
				iv.setSelected(false);
				iv.setEnabled(false);
				bean.setEnable(false);
				tv.setTextColor(newsOperateBtnBasicColor);
			}
			i++;
		}
	}
	
	/**
	 * 初始化底部菜单的每一个按钮
	 */
	private void initNewsOperateBtn() {
//		LayoutInflater inflater = LayoutInflater.from(this);
		int i = 0;
		// 获取底部的每一个按钮的基本参数
		bottomMenuList = initNewsOperateBtnData();
		for (BottomMenuBasicBean bean : bottomMenuList) {
//			final View view = inflater.inflate(R.layout.operate_btn_basic, null);
			final LinearLayout layout = (LinearLayout) newsOperateBtnLayout.getChildAt(i++);
			bean.setView(layout);
//			LayoutParams layoutParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
//			view.setLayoutParams(layoutParams);
//			final ImageView iv = (ImageView) view.findViewById(R.id.operateBtnImageView);
			final ImageView iv = (ImageView) layout.getChildAt(0);
			bean.setImageView(iv);
			if (bean.isEnable()) {
				iv.setImageResource(bean.getImageCheckedResource());
				iv.setSelected(true);
				iv.setEnabled(true);
			} else {
				iv.setImageResource(bean.getImageDisableResource());
				iv.setSelected(false);
				iv.setEnabled(false);
			}
			final String hint = bean.getDisableHint();
			BottomMenuOnTouchListener listener = new BottomMenuOnTouchListener(bean, touchSlop) {

				@Override
				public boolean onImgChangeBegin() {
					// 如果当前点击的按钮处于disable状态，则不进行任何操作
					if (iv.isEnabled()) {
						return true;
					} else {
//						changeOperateBtnState(layout, resultList);
//						changeOperateBtnState(view, resultList);
						Toast.makeText(thisActivity, hint, Toast.LENGTH_SHORT).show();
						return false;
					}
				}

				@Override
				public void onImgChangeEnd() {
					
				}

				@Override
				public void onImgChangeDo() {
					
				}
			};
			layout.setOnTouchListener(listener);
			TextView tv = (TextView) layout.getChildAt(1);
//			TextView tv = (TextView) view.findViewById(R.id.operateBtnText);
			tv.setText(bean.getTextContent());
//			newsOperateBtnLayout.addView(layout);
			
		}
	}
	
	/*private void changeOperateBtnState(View curView, List<ImageViewBasicBean> list) {
		int childCount = newsOperateBtnLayout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View view = newsOperateBtnLayout.getChildAt(i);
			if (view != curView) {
				ImageView iv = (ImageView) view.findViewById(R.id.operateBtnImageView);
				if (iv.isSelected()) {
					ImageViewBasicBean bean = list.get(i);
					iv.setImageResource(bean.getImageUncheckResource());
					iv.setSelected(false);
					bean.setSelected(false);
					break;
				}
			}
		}
	}*/
	
	private List<BottomMenuBasicBean> initNewsOperateBtnData() {
		List<BottomMenuBasicBean> resultList = new ArrayList<BottomMenuBasicBean>();
		int length = newsOperateBtnChecked.length;
		for (int i = 0; i < length; i++) {
			BottomMenuBasicBean bean = new BottomMenuBasicBean();
			bean.setImageCheckedResource(newsOperateBtnChecked[i]);
//			bean.setImageUncheckResource(newsOperateBtnUnchecked[i]);
			bean.setImageDisableResource(newsOperateBtnDisabled[i]);
			bean.setTextContent(newsOperateBtnTextContent[i]);
			bean.setDisableHint(disableHint[i]);
			bean.setCanEnableState(newsOperateBtnCanEnableState[i]);
			resultList.add(bean);
		}
		return resultList;
	}
	
	/**
	 * 在滑动状态改变的时候调用
	 * 有三种状态（0，1，2）。1表示正在滑动；2表示滑动完毕了；0表示什么都没做。
	 */
	@Override
	public void onPageScrollStateChanged(int state) {
		
	}

	/**
	 * 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法会一直得到调用。其中三个参数的含义分别为：
	 * arg0 :当前页面，及你点击滑动的页面
	 * arg1:当前页面偏移的百分比
	 * arg2:当前页面偏移的像素位置
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	/**
	 * 此方法是页面跳转完后得到调用，position是你当前选中的页面对应的值（从0开始）
	 */
	@Override
	public void onPageSelected(final int position) {
		int childCount = newsTypeBtnLineLayout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			EnableSimpleChangeButton btn = (EnableSimpleChangeButton) newsTypeBtnLineLayout.getChildAt(i);
			ButtonColorBasicBean colorBasicBean = null;
			try {
				colorBasicBean = new ButtonColorBasicBean(thisActivity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			final ButtonColorBasicBean tmpBean = colorBasicBean;
			final int tmp = i;
			Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					ListView listView = (ListView) views.get(tmp);
					if (tmp == position) {
						initNewsTypeBtnStyleByFocusedState(tmpBean, true);
						initNewsListData(listView);
					} else {
						initNewsTypeBtnStyleByFocusedState(tmpBean, false);
						if (Math.abs(position - tmp) > 1) {
//							ListAdapter adapter = listView.getAdapter();
							ListAdapter adapter = new NewsListViewAdapter(new ArrayList<View>());
							listView.setAdapter(adapter);
						}
					}
				}
			}.sendEmptyMessageDelayed(0, 2000);
			btn.setColorBasicBean(colorBasicBean);
		}
		curFocusBtn = position;
	}
	
	/*private List<RadioButtonBasicBean> initNewsOperateRadioBtnData() {
		List<RadioButtonBasicBean> resultList = new ArrayList<RadioButtonBasicBean>();
		int length = newsOperateBtnChecked.length;
		for (int i = 0; i < length; i++) {
			RadioButtonBasicBean bean = new RadioButtonBasicBean();
			bean.setImageCheckedResource(newsOperateBtnChecked[i]);
			bean.setImageUncheckResource(newsOperateBtnDisabled[i]);
			bean.setTextContent(newsOperateBtnTextContent[i]);
			bean.setCanEnableState(newsOperateRadioBtnCanEnableState[i]);
			if (i == 0) {
				bean.setSelected(true);
			}
			resultList.add(bean);
		}
		return resultList;
	}*/
}