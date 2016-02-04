package com.enorth.cms.view.news;

import android.os.Handler;

public interface INewsCommonView {
	public void initData(String result, Handler handler) throws Exception;
	
	public void initSubTitleResult(String result, Handler handler) throws Exception;
}
