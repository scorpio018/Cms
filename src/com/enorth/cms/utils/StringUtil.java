package com.enorth.cms.utils;

public class StringUtil {
	
	/**
	 * 判断字符串是否为空
	 * @param text
	 * @return
	 */
	public static boolean isEmpty(String text) {
		if (text == null) {
			return true;
		}
		if (text.equals("")) {
			return true;
		}
		return false;
	}
}
