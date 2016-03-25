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
	
	/**
	 * 判断字符串是否不为空
	 * @param text
	 * @return
	 */
	public static boolean isNotEmpty(String text) {
		if (text != null && !text.equals("") && !text.equals("null")) {
			return true;
		} else {
			return false;
		}
	}
}
