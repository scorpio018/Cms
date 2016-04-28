package com.enorth.cms.listener.newslist.viewpager;

import com.enorth.cms.bean.ViewColorBasicBean;
import com.enorth.cms.common.EnableSimpleChangeButton;
import com.enorth.cms.utils.ViewUtil;
import com.enorth.cms.view.news.NewsCommonActivity;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;

public class NewsCommonViewPagerOnPageChangeListener implements OnPageChangeListener {
	
	private NewsCommonActivity activity;
	
	public NewsCommonViewPagerOnPageChangeListener(NewsCommonActivity activity) {
		this.activity = activity;
	}
	/**
	 * 在滑动状态改变的时候调用 有三种状态（0，1，2）。1表示正在滑动；2表示滑动完毕了；0表示什么都没做。
	 */
	@Override
	public void onPageScrollStateChanged(int state) {
		
	}

	/**
	 * 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法会一直得到调用。其中三个参数的含义分别为： arg0 :当前页面，及你点击滑动的页面
	 * arg1:当前页面偏移的百分比 arg2:当前页面偏移的像素位置
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		Log.e("NewsCommonViewPagerOnPageChangeListener", "position【" + arg0 + "】");
	}

	/**
	 * 此方法是页面跳转完后得到调用，position是你当前选中的页面对应的值（从0开始）
	 */
	@Override
	public void onPageSelected(final int position) {
		// AnimUtil.showRefreshFrame(NewsCommonActivity.this);
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				// 将ViewPager切换的当前位置传入activity中，以保证能正确获取到所需的adapter
				activity.setCurPosition(position);
				activity.changeAddNewsBtnVisible();
				activity.initNewsOperateBtn();
				int childCount = activity.getNewsTypeBtnLineLayout().getChildCount();
				// 只有在已签发的时候才显示排序图标
				if (position == 2) {
					activity.getNewsSubTitleSort().setVisibility(View.VISIBLE);
				} else {
					activity.getNewsSubTitleSort().setVisibility(View.GONE);
				}
				for (int i = 0; i < childCount; i++) {
					// 初始化新的按钮组的颜色
					ViewColorBasicBean colorBasicBean = new ViewColorBasicBean(activity);
					// 获取可下拉的ListView
					PullToRefreshListView listView = (PullToRefreshListView) (activity.getViews().get(i).getNewsListView());
					// 获取按钮组的每一个按钮
					EnableSimpleChangeButton btn = (EnableSimpleChangeButton) activity.getNewsTypeBtnLineLayout().getChildAt(i);
					if (i == position) {
						// 将当前选择的新闻类型对应的newsType存入newsListBean中
						int newsState = ViewUtil.getCurCheckedBtnGroupId(activity.getNewsStateBtns());
						activity.getNewsListBean().setState(newsState);
						// 此处获取新闻类型的方式也可，不过推荐上面的获取方式
//						activity.getNewsListBean().setNewsType(activity.getNewsTypeBtnState()[position]);
						activity.changeToCurPosition(colorBasicBean, btn, listView, position);
					} else {
//						changeToOtherPosition(colorBasicBean, listView, Math.abs(position - i) > 1, i);
					}
					btn.setColorBasicBean(colorBasicBean);
				}
			}
		}.sendEmptyMessage(0);
	}
}
