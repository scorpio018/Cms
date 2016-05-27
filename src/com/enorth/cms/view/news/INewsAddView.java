package com.enorth.cms.view.news;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

/**
 * 添加新闻
 * @author yangyang
 *
 */
public interface INewsAddView {
	/**
	 * 是否为融合新闻
	 * true：显示“移动标题”和“4g模板”
	 * false：隐藏“移动标题”和“4g模板”
	 * @param isChecked
	 */
	public void isConvergentNews(boolean isChecked);
	
	
	public void afterSaveNews(String resultString, Handler handler);
	
	public Activity getActivity();
	
	public Context getContext();
	
}
