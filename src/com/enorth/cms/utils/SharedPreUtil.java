package com.enorth.cms.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import com.enorth.cms.consts.ParamConst;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * 使用SharedPreferences存储数据
 */
public class SharedPreUtil {

	/**
	 * 获取默认名字的SharedPreferences对象
	 * 
	 * @param context
	 * @return
	 */
	private static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(SharedPreUtil.class.getSimpleName(), Activity.MODE_PRIVATE);
	}

	/**
	 * 获取自定义名字的SharedPreferences对象
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	private static SharedPreferences getSharedPreferences(Context context, String name) {
		return context.getSharedPreferences(name, Activity.MODE_PRIVATE);
	}

	/**
	 * 获取默认名字的Editor对象
	 * 
	 * @param context
	 * @return
	 */
	private static SharedPreferences.Editor getEdit(Context context) {
		return getSharedPreferences(context).edit();
	}

	/**
	 * 获取自定义名字的Editor对象
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	private static SharedPreferences.Editor getEdit(Context context, String name) {
		return getSharedPreferences(context, name).edit();
	}

	/**
	 * 将key/value存入默认名字的Editor对象中
	 * 
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void put(Context context, String key, Object object) {
		SharedPreferences.Editor editor = getEdit(context);
		putCommon(editor, key, object);
	}

	/**
	 * 将key/value存入自定义名字的Editor对象中
	 * 
	 * @param context
	 * @param name
	 * @param key
	 * @param object
	 */
	public static void put(Context context, String name, String key, Object object) {
		SharedPreferences.Editor editor = getEdit(context, name);
		putCommon(editor, key, object);
	}

	private static void putCommon(SharedPreferences.Editor editor, String key, Object object) {
		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else if (object instanceof String[]) {
			StringBuilder datas = new StringBuilder();
			String[] data = (String[]) object;

			for (int i = 0; i < data.length; ++i) {
				if (i != 0) {
					datas.append(":");
				}

				datas.append(data[i]);
			}
			editor.putString(key, datas.toString());
		}

		editor.commit();
	}

	public static String getString(Context context, String key, String defaultObject) {
		return getSharedPreferences(context).getString(key, defaultObject);
	}

	public static int getInt(Context context, String key) {
		return getSharedPreferences(context).getInt(key, -1);
	}

	public static boolean getBoolean(Context context, String key) {
		return getSharedPreferences(context).getBoolean(key, false);
	}

	public static float getFloat(Context context, String key) {
		return getSharedPreferences(context).getFloat(key, -1f);
	}

	public static long getLong(Context context, String key) {
		return getSharedPreferences(context).getLong(key, -1l);
	}

	public static String[] getStringArray(Context context, String key) {
		return getString(context, key, "").split(":");
	}

	/**/
	public static String getString(Context context, String name, String key, String defaultObject) {
		return getSharedPreferences(context, name).getString(key, defaultObject);
	}

	public static int getInt(Context context, String name, String key) {
		return getSharedPreferences(context, name).getInt(key, -1);
	}

	public static boolean getBoolean(Context context, String name, String key) {
		return getSharedPreferences(context, name).getBoolean(key, false);
	}

	public static float getFloat(Context context, String name, String key) {
		return getSharedPreferences(context, name).getFloat(key, -1f);
	}

	public static long getLong(Context context, String name, String key) {
		return getSharedPreferences(context, name).getLong(key, -1l);
	}

	public static String[] getStringArray(Context context, String name, String key) {
		return getString(context, name, key, "").split(":");
	}
	
	/**
	 * 序列化对象
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static String serializeObject(Object object) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(object);
		String serStr = byteArrayOutputStream.toString("ISO-8859-1");
		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
		objectOutputStream.close();
		byteArrayOutputStream.close();
		return serStr;
	}
	
	/**
	 * 反序列化对象
	 * @param str
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deSerializeObject(String str) {
		Object object = null;
		try {
			String redStr = java.net.URLDecoder.decode(str, "UTF-8");
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			object = objectInputStream.readObject();
			objectInputStream.close();
			byteArrayInputStream.close();
		} catch (IOException e) {
			Log.e("SharedPreUtil.deSerializeObject IOException", e.toString());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			Log.e("SharedPreUtil.deSerializeObject ClassNotFoundException", e.toString());
			e.printStackTrace();
		}
		return object;
	}
	
	public static void remove(Context context, String key) {
		SharedPreferences.Editor editor = getEdit(context);
		editor.remove(key);
		editor.commit();
	}
	
	public static void remove(Context context, String name, String key) {
		SharedPreferences.Editor editor = getEdit(context, name);
		editor.remove(key);
		editor.commit();
	}

	public static void clear(Context context) {
		SharedPreferences.Editor editor = getEdit(context);
		editor.clear();
		editor.commit();
	}
	
	public static void clear(Context context, String name) {
		SharedPreferences.Editor editor = getEdit(context, name);
		editor.clear();
		editor.commit();
	}

	public static boolean contains(Context context, String key) {
		return getSharedPreferences(context).contains(key);
	}

	public static Map<String, ?> getAll(Context context) {
		return getSharedPreferences(context).getAll();
	}

	/**
	 * 重置频道信息（包括频道ID、频道名称）
	 * 
	 * @param pre
	 */
	public static void resetChannelIdData(Context context) {
		SharedPreferences.Editor mEditor = getEdit(context);
		mEditor.remove(ParamConst.CUR_CHANNEL_ID);
		mEditor.putLong(ParamConst.CUR_CHANNEL_ID, ParamConst.DEFAULT_CHANNEL_ID);
		mEditor.putString(ParamConst.CUR_CHANNEL_NAME, ParamConst.DEFAULT_CHANNEL_NAME);
		mEditor.commit();
	}

}
