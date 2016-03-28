package com.enorth.cms.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 使用的通用参数的工具类
 * @author yangyang
 *
 */
public class ParamsUtil {
	
	private static String DEVICE_ID;
	/**
	 * 获取安卓设备唯一标识码
	 * @param context
	 * @return
	 */
	public static String getDeviceID(Context context) {
		if (DEVICE_ID == null) {
			TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
			DEVICE_ID = tm.getDeviceId();
		}
		return DEVICE_ID;
	}
}
