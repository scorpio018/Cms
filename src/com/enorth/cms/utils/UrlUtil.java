package com.enorth.cms.utils;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.enorth.cms.annotation.SharedPreSaveAnnotation;
import com.enorth.cms.bean.login.LoginBean;
import com.enorth.cms.consts.ParamConst;

import android.content.Context;

public class UrlUtil {

	/**
	 * 将接口中通用的参数进行统一存入
	 * @param params
	 * @param context
	 * @param ob
	 */
	public static String getCheckSum(Context context, Object... ob) {
		String seed = getSeed(context);
		StringBuffer strBuf = new StringBuffer();
		for (Object o : ob) {
			strBuf.append(o.toString());
		}
		strBuf.append(seed);
		String checkSum = MD5Util.getMD5(strBuf.toString());
		return checkSum;
	}
	
	public static StringBuffer getCheckSumParam(Object... ob) {
		StringBuffer strBuf = new StringBuffer();
		for (Object o : ob) {
			strBuf.append(o.toString());
		}
		return strBuf;
//		String checkSum = MD5Util.getMD5(strBuf.toString());
	}
	public static StringBuffer getCheckSumParam(List<Object> ob) {
		StringBuffer strBuf = new StringBuffer();
		for (Object o : ob) {
			strBuf.append(o.toString());
		}
		return strBuf;
//		String checkSum = MD5Util.getMD5(strBuf.toString());
	}
	
	/**
	 * 获取种子值
	 * @param context
	 * @return
	 */
	public static String getSeed(Context context) {
		Field[] declaredFields = LoginBean.class.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.getName().equals(ParamConst.USER_LOGIN_SEED)) {
				SharedPreSaveAnnotation annotation = field.getAnnotation(SharedPreSaveAnnotation.class);
				String name = annotation.name();
				if (name.equals("")) {
					name = LoginBean.class.getName();
				}
				String key = annotation.key();
				if (key.equals("")) {
					key = field.getName();
				}
				String seed = SharedPreUtil.getString(context, name, key, "");
				return seed;
			}
		}
		return null;
	}
	
	/**
	 * 获取当前登录的用户bean
	 * @param context
	 * @return
	 */
	public static LoginBean getLoginUserBean(Context context) {
		LoginBean loginBean = (LoginBean) BeanParamsUtil.getObject(LoginBean.class, context);
		return loginBean;
	}
}
