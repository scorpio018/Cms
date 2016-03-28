package com.enorth.cms.utils;

import android.content.Context;
import android.util.Log;

public class LogUtil {
	/**
	 * 由于传入的内容长度过长，在LogCat中无法完全打印出来，所以进行截取分组打印
	 * @param str
	 * @param context
	 */
	public static void printLog(String str, Context context) {
		int groupCount = 300;
		int length = str.length();
		if (length > groupCount) {
			int groupNum = length / groupCount;
			for (int i = 0; i < groupNum; i++) {
				String tmp = str.substring(groupCount * i, groupCount * (i + 1));
				Log.e("LogUtil.printLog", tmp);
			}
			Log.e("LogUtil.printLog", str.substring(groupCount * groupNum));
		} else {
			Log.e("LogUtil.printLog", str);
		}
	}
}
