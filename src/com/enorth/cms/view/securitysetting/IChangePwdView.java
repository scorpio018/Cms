package com.enorth.cms.view.securitysetting;

import android.os.Handler;

public interface IChangePwdView {
	
	/**
	 * 将返回的json结果进行初始化
	 * @param resultString
	 * @param handler
	 */
	public void initReturnJsonData(String resultString, Handler handler);
}
