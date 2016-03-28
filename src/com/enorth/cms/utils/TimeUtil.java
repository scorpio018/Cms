package com.enorth.cms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
	private static SimpleDateFormat sdf;
	
	public static String getDate(String template) {
		sdf = new SimpleDateFormat(template, Locale.CHINA);
		return sdf.format(new Date());
	}
	
	public static String getDateYMHHMS() {
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		return sdf.format(new Date());
	}
	
	public static String getDateYMHHM(long time) {
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
		return sdf.format(new Date(time));
	}
	
	public static String getDateMHHM(long time) {
		sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
		return sdf.format(new Date(time));
	}
	
	public static String getDateYMHHMSNoConnector() {
		sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
		return sdf.format(new Date());
	}
	
	public static String getDateYMHHMSsNoConnector() {
		sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.CHINA);
		return sdf.format(new Date());
	}
}
