package com.enorth.cms.utils;

import android.util.Log;

public class ExceptionUtil {
	public static void simpleExceptionCatch(String exTitle, Exception e) {
		Log.e(exTitle, e.getMessage());
		e.printStackTrace();
	}
}
