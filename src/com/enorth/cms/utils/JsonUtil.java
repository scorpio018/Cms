package com.enorth.cms.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import com.enorth.cms.consts.ExceptionConst;

import android.util.Log;

public class JsonUtil {

	/**
	 * 将传入的String转化成jsonArray
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static JSONArray initJsonArray(String str) throws Exception {
		try {
			JSONArray jsonArray = new JSONArray(str);
			return jsonArray;
		} catch (Exception e) {
			Log.e("初始化JsonArray时发生异常", "【" + str + "】无法转换成JsonArray");
			interException();
			return null;
		}
	}
	
	/**
	 * 将传入的String转化成jsonObject
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static JSONObject initJsonObject(String str) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject(str);
			return jsonObject;
		} catch (Exception e) {
			Log.e("初始化JsonObject时发生异常", "【" + str + "】无法转换成JsonObject");
			interException();
			return null;
		}
	}
	
	/**
	 * 从JsonObject中获得传入的key对应的int类型的数据
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static int getInt(JSONObject jsonObject, String key) throws Exception {
		try {
			return jsonObject.getInt(key);
		} catch (Exception e) {
			Log.e("从JsonObject中获取int值时发生异常", "【" + jsonObject + "】中没有key为【" + key + "】的值");
			interException();
			return -1;
		}
	}
	
	/**
	 * 从JsonObject中获得传入的key对应的Long类型的数据
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static long getLong(JSONObject jsonObject, String key) throws Exception {
		try {
			return jsonObject.getLong(key);
		} catch (Exception e) {
			Log.e("从JsonObject中获取long值时发生异常", "【" + jsonObject + "】中没有key为【" + key + "】的值");
			interException();
			return -1;
		}
	}
	
	/**
	 * 从JsonObject中获得传入的key对应的boolean类型的数据
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static boolean getBoolean(JSONObject jsonObject, String key) throws Exception {
		try {
			return jsonObject.getBoolean(key);
		} catch (Exception e) {
			Log.e("从JsonObject中获取boolean值时发生异常", "【" + jsonObject + "】中没有key为【" + key + "】的值");
			interException();
			return false;
		}
	}
	
	/**
	 * 从JsonObject中获得传入的key对应的double类型的数据
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static double getDouble(JSONObject jsonObject, String key) throws Exception {
		try {
			return jsonObject.getDouble(key);
		} catch (Exception e) {
			Log.e("从JsonObject中获取double值时发生异常", "【" + jsonObject + "】中没有key为【" + key + "】的值");
			interException();
			return -1;
		}
	}
	
	/**
	 * 从JsonObject中获得传入的key对应的String类型的数据
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getString(JSONObject jsonObject, String key) throws Exception {
		try {
			return jsonObject.getString(key);
		} catch (Exception e) {
			Log.e("从JsonObject中获取String值时发生异常", "【" + jsonObject + "】中没有key为【" + key + "】的值");
			interException();
			return null;
		}
	}
	
	/**
	 * 从JsonObject中获得传入的key对应的JSONObject类型的数据
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getJSONObject(JSONObject jsonObject, String key) throws Exception {
		try {
			return jsonObject.getJSONObject(key);
		} catch (Exception e) {
			Log.e("从JsonObject中获取JSONObject值时发生异常", "【" + jsonObject + "】中没有key为【" + key + "】的值");
			interException();
			return null;
		}
	}
	
	/**
	 * 从JsonObject中获得传入的key对应的JSONArray类型的数据
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getJSONArray(JSONObject jsonObject, String key) throws Exception {
		try {
			return jsonObject.getJSONArray(key);
		} catch (Exception e) {
			Log.e("从JsonObject中获取JSONArray值时发生异常", "【" + jsonObject + "】中没有key为【" + key + "】的值");
			interException();
			return null;
		}
	}
	
	/**
	 * 从JsonObject中获得传入的key对应的int类型的数据
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static int getInt(JSONArray jsonArray, int position) throws Exception {
		try {
			return jsonArray.getInt(position);
		} catch (Exception e) {
			Log.e("从JSONArray中获取int值时发生异常", "【" + jsonArray + "】中的第【" + position + "】个对象不是int类型");
			interException();
			return -1;
		}
	}
	
	/**
	 * 从JsonObject中获得传入的key对应的Long类型的数据
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static long getLong(JSONArray jsonArray, int position) throws Exception {
		try {
			return jsonArray.getLong(position);
		} catch (Exception e) {
			Log.e("从JSONArray中获取long值时发生异常", "【" + jsonArray + "】中的第【" + position + "】个对象不是long类型");
			interException();
			return -1;
		}
	}
	
	/**
	 * 从JsonObject中获得传入的key对应的boolean类型的数据
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static boolean getBoolean(JSONArray jsonArray, int position) throws Exception {
		try {
			return jsonArray.getBoolean(position);
		} catch (Exception e) {
			Log.e("从JSONArray中获取boolean值时发生异常", "【" + jsonArray + "】中的第【" + position + "】个对象不是boolean类型");
			interException();
			return false;
		}
	}
	
	/**
	 * 从JsonObject中获得传入的key对应的double类型的数据
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static double getDouble(JSONArray jsonArray, int position) throws Exception {
		try {
			return jsonArray.getDouble(position);
		} catch (Exception e) {
			Log.e("从JSONArray中获取double值时发生异常", "【" + jsonArray + "】中的第【" + position + "】个对象不是double类型");
			interException();
			return -1;
		}
	}
	
	/**
	 * 从JsonObject中获得传入的key对应的String类型的数据
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getString(JSONArray jsonArray, int position) throws Exception {
		try {
			return jsonArray.getString(position);
		} catch (Exception e) {
			Log.e("从JSONArray中获取String值时发生异常", "【" + jsonArray + "】中的第【" + position + "】个对象不是String类型");
			interException();
			return null;
		}
	}
	
	/**
	 * 从JsonObject中获得传入的key对应的JSONObject类型的数据
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getJSONObject(JSONArray jsonArray, int position) throws Exception {
		try {
			return jsonArray.getJSONObject(position);
		} catch (Exception e) {
			Log.e("从JSONArray中获取JSONObject值时发生异常", "【" + jsonArray + "】中的第【" + position + "】个对象不是JSONObject类型");
			interException();
			return null;
		}
	}
	
	/**
	 * 从JsonObject中获得传入的key对应的JSONArray类型的数据
	 * @param jsonObject
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getJSONArray(JSONArray jsonArray, int position) throws Exception {
		try {
			return jsonArray.getJSONArray(position);
		} catch (Exception e) {
			Log.e("从JSONArray中获取JSONArray值时发生异常", "【" + jsonArray + "】中的第【" + position + "】个对象不是JSONArray类型");
			interException();
			return null;
		}
	}
	
	/**
	 * 接口返回的数据进行处理时出现的错误处理
	 * @throws Exception
	 */
	public static void interException() throws Exception {
		throw new Exception(ExceptionConst.INTER_ERROR_EXCEPTION);
	}
}
