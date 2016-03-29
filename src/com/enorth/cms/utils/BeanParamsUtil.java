package com.enorth.cms.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.enorth.cms.annotation.SharedPreSaveAnnotation;
import com.enorth.cms.annotation.UrlParamAnnotation;
import com.enorth.cms.bean.login.LoginBean;
import com.enorth.cms.interutil.MapComparator;

import android.content.Context;
import android.util.Log;

public class BeanParamsUtil {
	
	/**
	 * 将传入的object以变量为key，变量对应的值为value存入List<BasicNameValuePair>中
	 * @param ob
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static List<BasicNameValuePair> initData(Object ob, Context context) {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		Map<Integer, Object> isCheckObMap = new TreeMap<Integer, Object>(new MapComparator());
		// 将ob中需要提取出来传入url的参数进行封装，并将需要进行MD5拼值的参数按照规定的顺序(key)存入isCheckObMap中(value)
		initDataCommon(ob, context, params, isCheckObMap);
		// 将要拼MD5的参数存入list中
		List<Object> list = new ArrayList<Object>(isCheckObMap.values());
		// 将集合传入getCheckSumParam方法中
		StringBuffer checkSumParams = UrlUtil.getCheckSumParam(list);
		
//		String seed = UrlUtil.getSeed(context);
		// 获取当前登录的用户bean
		LoginBean loginUserBean = UrlUtil.getLoginUserBean(context);
		// 获取种子值
		String seed = loginUserBean.getSeed();
		// 将种子值拼入参数后面
		checkSumParams.append(seed);
		// 进行MD5
		String checkSum = MD5Util.getMD5(checkSumParams.toString());
		// 将MD5后的结果以check_sum为key存入params中
		params.add(new BasicNameValuePair("check_sum", checkSum));
		// token值
		String token = loginUserBean.getToken();
		params.add(new BasicNameValuePair("api_token", token));
		return params;
//		params = UrlUtil.addUrlCommonParams(params, context, ob);
	}
	
	/**
	 * 简单的只进行传入url的param值的封装，不包括token和check_sum
	 * @param ob
	 * @param context
	 * @return
	 */
	public static List<BasicNameValuePair> initDataSimple(Object ob, Context context) {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		Map<Integer, Object> isCheckObMap = new TreeMap<Integer, Object>(new MapComparator());
		initDataCommon(ob, context, params, isCheckObMap);
		return params;
	}
	
	private static List<BasicNameValuePair> initDataCommon(Object ob, Context context, List<BasicNameValuePair> params, Map<Integer, Object> isCheckObMap) {
		// 当ob为空，则说明没有需要和api_token进行拼接的值
		if (ob != null) {
			initData(ob, params, isCheckObMap);
		}
		params.add(new BasicNameValuePair("devId", ParamsUtil.getDeviceID(context)));
		return params;
	}
	
	private static void initData(Object ob, List<BasicNameValuePair> params, Map<Integer, Object> isCheckObMap) {
		Class<?> c = ob.getClass();
		Field[] fields = c.getDeclaredFields();
		Method[] declaredMethods = c.getDeclaredMethods();
		int methodLength = declaredMethods.length;
		List<String> methodNames = new ArrayList<String>();
		for (int i = 0; i < methodLength; i++) {
			methodNames.add(declaredMethods[i].getName());
		}
		for (Field field : fields) {
			// 将有@UrlParamAnnotation注释的变量取出并存入params中
			if (field.isAnnotationPresent(UrlParamAnnotation.class)) {
				UrlParamAnnotation annotation = field.getAnnotation(UrlParamAnnotation.class);
				String key = annotation.key();
				if (key.equals("")) {
					key = field.getName();
				}
				Class<?> type = field.getType();
				String getterName = "get" + key.substring(0, 1).toUpperCase(Locale.CHINA) + key.substring(1);
				if (!methodNames.contains(getterName)) {
					continue;
				}
				Method method = null;
				try {
					method = ob.getClass().getMethod(getterName, new Class[]{});
				} catch (NoSuchMethodException e) {
					Log.e("BeanParamsUtil.initData error", e.toString());
					e.printStackTrace();
				}
				Object value = null;
				try {
					value = method.invoke(ob);
				} catch (IllegalAccessException e) {
					Log.e("BeanParamsUtil.initData error", e.toString());
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					Log.e("BeanParamsUtil.initData error", e.toString());
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					Log.e("BeanParamsUtil.initData error", e.toString());
					e.printStackTrace();
				}
				// 用于check_sum的参数拼接，如果为true，则需要存入isCheckOb中，用于最终和种子进行拼接，再MD5成check_sum
				boolean isCheck = annotation.isCheck();
				if (isCheck) {
					int checkSort = annotation.checkSort();
					isCheckObMap.put(checkSort, value);
				}
				if (checkIsString(type)) {
					params.add(new BasicNameValuePair(key, value.toString()));
				} else if (checkIsInteger(type)) {
					params.add(new BasicNameValuePair(key, value.toString()));
				} else if (checkIsLong(type)) {
					params.add(new BasicNameValuePair(key, value.toString()));
				} else if (checkIsDouble(type)) {
					params.add(new BasicNameValuePair(key, value.toString()));
				} else if (checkIsBoolean(type)) {
					params.add(new BasicNameValuePair(key, value.toString()));
				} else {
					if (checkIsList(type)) {
						Log.e("BeanParamsUtil.initData error", "目前尚未支持Map方法~");
					} else if (checkIsMap(type)) {
						Log.e("BeanParamsUtil.initData error", "目前尚未支持Map方法~");
					} else if (checkIsSet(type)) {
						Log.e("BeanParamsUtil.initData error", "目前尚未支持Set方法~");
					} else if (type.isArray()) {
						Log.e("BeanParamsUtil.initData error", "目前尚未支持数组方法~");
					} else {
						initData(value, params, isCheckObMap);
					}
				}
			}
		}
	}
	
	/**
	 * 将json和传入的对象类进行数据的一一对应储存，以对象类中的属性名为key值
	 * @param jo
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public static Object saveJsonToObject(JSONObject jo, Class c) {
		Object ob = null;
		try {
			ob = c.newInstance();
		} catch (InstantiationException e) {
			Log.e("BeanParamsUtil.saveJsonToObject error", e.toString());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.e("BeanParamsUtil.saveJsonToObject error", e.toString());
			e.printStackTrace();
		}
		Field[] fields = c.getDeclaredFields();
		Method[] declaredMethods = c.getDeclaredMethods();
		int methodLength = declaredMethods.length;
		List<String> methodNames = new ArrayList<String>();
		for (int i = 0; i < methodLength; i++) {
			methodNames.add(declaredMethods[i].getName());
		}
		for (Field field : fields) {
			String name = field.getName();
			Class<?> type = field.getType();
			String setterName = "set" + name.substring(0, 1).toUpperCase(Locale.CHINA) + name.substring(1);
			if (!methodNames.contains(setterName)) {
				continue;
			}
			Method method = null;
			try {
				method = ob.getClass().getMethod(setterName, type);
			} catch (NoSuchMethodException e) {
				Log.e("BeanParamsUtil.saveJsonToObject error", e.toString());
				e.printStackTrace();
			}
			Object object = null;
			try {
				if (jo.has(name)) {
					object = jo.get(name);
				} else {
					continue;
				}
			} catch (JSONException e) {
				Log.e("BeanParamsUtil.saveJsonToObject error", e.toString());
				e.printStackTrace();
			}
			if (object == null) {
				continue;
			}
			try {
				if (checkIsString(type)) {
					method.invoke(ob, jo.getString(name));
				} else if (checkIsInteger(type)) {
					method.invoke(ob, jo.getInt(name));
				} else if (checkIsLong(type)) {
					method.invoke(ob, jo.getLong(name));
				} else if (checkIsDouble(type)) {
					method.invoke(ob, jo.getDouble(name));
				} else if (checkIsBoolean(type)) {
					method.invoke(ob, jo.getBoolean(name));
				} else if (!type.isPrimitive()) {
					if (checkIsList(type)) {
						JSONArray ja = jo.getJSONArray(name);
						int length = ja.length();
						// 获得List集合中泛型的对象
						Class genericClazz = null;
						Type genericType = field.getGenericType();
						if (genericType instanceof ParameterizedType) {
							ParameterizedType pt = (ParameterizedType) genericType;
							genericClazz = (Class) pt.getActualTypeArguments()[0];
						} else {
							throw new JSONException("没有找到集合中的泛型对象");
						}
						List list = new ArrayList();
						for (int i = 0; i < length; i++) {
							Object subOb = saveJsonToObject(ja.getJSONObject(i), genericClazz);
							list.add(subOb);
						}
						method.invoke(ob, list);
					} else if (checkIsMap(type)) {
						Log.e("BeanParamsUtil.saveJsonToObject error", "目前尚未支持Map方法~");
						/*Type genericType = field.getGenericType();
						if (genericType instanceof ParameterizedType) {
							ParameterizedType pt = (ParameterizedType) genericType;
							Class genericClazzKey = (Class) pt.getActualTypeArguments()[0];
							System.out.println(genericClazzKey);
							Class genericClazzValue = (Class) pt.getActualTypeArguments()[1];
							System.out.println(genericClazzValue);
						}*/
					} else if (checkIsSet(type)) {
						Log.e("BeanParamsUtil.saveJsonToObject error", "目前尚未支持Set方法~");
					} else if (type.isArray()) {
						Log.e("BeanParamsUtil.saveJsonToObject error", "目前尚未支持数组方法~");
					} else {
						Object subOb = saveJsonToObject(jo.getJSONObject(name), type);
						method.invoke(ob, subOb);
					}
				} else {
					throw new JSONException("未知的Class类型");
				}
			} catch (IllegalAccessException e) {
				Log.e("BeanParamsUtil.saveJsonToObject error", e.toString());
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				Log.e("BeanParamsUtil.saveJsonToObject error", e.toString());
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				Log.e("BeanParamsUtil.saveJsonToObject error", e.toString());
				e.printStackTrace();
			} catch (JSONException e) {
				Log.e("BeanParamsUtil.saveJsonToObject error", e.toString());
				e.printStackTrace();
			}
		}
		return ob;
	}
	
	public static JSONObject saveObjectToJson(Object ob, Context context) {
		JSONObject jo = new JSONObject();
		Class<?> c = ob.getClass();
		String clazzName = c.getName();
		Field[] fields = c.getDeclaredFields();
		Method[] declaredMethods = c.getDeclaredMethods();
		int methodLength = declaredMethods.length;
		List<String> methodNames = new ArrayList<String>();
		for (int i = 0; i < methodLength; i++) {
			methodNames.add(declaredMethods[i].getName());
		}
		for (Field field : fields) {
			if (field.isAnnotationPresent(SharedPreSaveAnnotation.class)) {
				SharedPreSaveAnnotation annotation = field.getAnnotation(SharedPreSaveAnnotation.class);
				String name = annotation.name();
				if (name.equals("")) {
					name = clazzName;
				}
				String fieldName = field.getName();
				String key = annotation.key();
				if (key.equals("")) {
					key = fieldName;
				}
				
				Class<?> type = field.getType();
				String getterName = "get" + fieldName.substring(0, 1).toUpperCase(Locale.CHINA) + fieldName.substring(1);
				if (!methodNames.contains(getterName)) {
					continue;
				}
				Method method = null;
				Object value = null;
				try {
					method = ob.getClass().getMethod(getterName);
					value = method.invoke(ob);
				} catch (NoSuchMethodException e) {
					Log.e("BeanParamsUtil.saveObject error", e.toString());
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					Log.e("BeanParamsUtil.saveObject error", e.toString());
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					Log.e("BeanParamsUtil.saveObject error", e.toString());
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					Log.e("BeanParamsUtil.saveObject error", e.toString());
					e.printStackTrace();
				}
				
				if (checkIsString(type) || checkIsInteger(type) || checkIsLong(type) || checkIsDouble(type) || checkIsBoolean(type)) {
//					SharedPreUtil.put(context, name, key, value);
					try {
						jo.put(key, value);
					} catch (JSONException e) {
						Log.e("BeanParamsUtil.saveObject error", "json存值时出现错误");
						e.printStackTrace();
					}
				} else {
					if (checkIsList(type)) {
						Log.e("BeanParamsUtil.saveObject error", "目前尚未支持List方法~");
						/*List list = (List) value;
						int length = list.size();
						// 获得List集合中泛型的对象
						for (int i = 0; i < length; i++) {
							saveObject(list.get(i), context);
						}*/
					} else if (checkIsMap(type)) {
						Log.e("BeanParamsUtil.saveObject error", "目前尚未支持Map方法~");
					} else if (checkIsSet(type)) {
						Log.e("BeanParamsUtil.saveObject error", "目前尚未支持Set方法~");
					} else if (type.isArray()) {
						Log.e("BeanParamsUtil.saveObject error", "目前尚未支持数组方法~");
					} else {
						JSONObject subJo = saveObjectToJson(value, context);
						try {
							jo.put(key, subJo);
						} catch (JSONException e) {
							Log.e("BeanParamsUtil.saveObject error", "子对象进行json存值时出现错误");
							e.printStackTrace();
						}
					}
				}
			}
		}
		return jo;
	}
	
	public static void saveObject(Object ob, Context context) {
		Class<?> c = ob.getClass();
		String clazzName = c.getName();
		Field[] fields = c.getDeclaredFields();
		Method[] declaredMethods = c.getDeclaredMethods();
		int methodLength = declaredMethods.length;
		List<String> methodNames = new ArrayList<String>();
		for (int i = 0; i < methodLength; i++) {
			methodNames.add(declaredMethods[i].getName());
		}
		for (Field field : fields) {
			if (field.isAnnotationPresent(SharedPreSaveAnnotation.class)) {
				SharedPreSaveAnnotation annotation = field.getAnnotation(SharedPreSaveAnnotation.class);
				String name = annotation.name();
				if (name.equals("")) {
					name = clazzName;
				}
				String fieldName = field.getName();
				String key = annotation.key();
				if (key.equals("")) {
					key = fieldName;
				}
				
				Class<?> type = field.getType();
				String getterName = "get" + fieldName.substring(0, 1).toUpperCase(Locale.CHINA) + fieldName.substring(1);
				if (!methodNames.contains(getterName)) {
					continue;
				}
				Method method = null;
				Object value = null;
				try {
					method = ob.getClass().getMethod(getterName);
					value = method.invoke(ob);
				} catch (NoSuchMethodException e) {
					Log.e("BeanParamsUtil.saveObject error", e.toString());
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					Log.e("BeanParamsUtil.saveObject error", e.toString());
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					Log.e("BeanParamsUtil.saveObject error", e.toString());
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					Log.e("BeanParamsUtil.saveObject error", e.toString());
					e.printStackTrace();
				}
				
				if (checkIsString(type)) {
					SharedPreUtil.put(context, name, key, value);
				} else if (checkIsInteger(type)) {
					SharedPreUtil.put(context, name, key, value);
				} else if (checkIsLong(type)) {
					SharedPreUtil.put(context, name, key, value);
				} else if (checkIsDouble(type)) {
					SharedPreUtil.put(context, name, key, value);
				} else if (checkIsBoolean(type)) {
					SharedPreUtil.put(context, name, key, value);
				} else {
					if (checkIsList(type)) {
						Log.e("BeanParamsUtil.saveObject error", "目前尚未支持List方法~");
						/*List list = (List) value;
						int length = list.size();
						// 获得List集合中泛型的对象
						for (int i = 0; i < length; i++) {
							saveObject(list.get(i), context);
						}*/
					} else if (checkIsMap(type)) {
						Log.e("BeanParamsUtil.saveObject error", "目前尚未支持Map方法~");
					} else if (checkIsSet(type)) {
						Log.e("BeanParamsUtil.saveObject error", "目前尚未支持Set方法~");
					} else if (type.isArray()) {
						Log.e("BeanParamsUtil.saveObject error", "目前尚未支持数组方法~");
					} else {
						saveObject(value, context);
//						Object subOb = saveJsonToObject(jo.getJSONObject(name), type);
						
					}
				}
			}
		}
	}
	
	public static Object getObject(Class<?> c, Context context) {
		Field[] fields = c.getDeclaredFields();
		Object ob = null;
		try {
			ob = c.newInstance();
		} catch (InstantiationException e) {
			Log.e("BeanParamsUtil.getObject error", e.toString());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.e("BeanParamsUtil.getObject error", e.toString());
			e.printStackTrace();
		}
		String clazzName = c.getName();
		for (Field field : fields) {
			if (field.isAnnotationPresent(SharedPreSaveAnnotation.class)) {
				Class<?> type = field.getType();
				String fieldName = field.getName();
				String setterName = "set" + fieldName.substring(0, 1).toUpperCase(Locale.CHINA) + fieldName.substring(1);
				Method method = null;
				try {
					method = ob.getClass().getMethod(setterName, type);
				} catch (NoSuchMethodException e) {
					Log.e("BeanParamsUtil.getObject error", e.toString());
					e.printStackTrace();
				}
				SharedPreSaveAnnotation annotation = field.getAnnotation(SharedPreSaveAnnotation.class);
				String name = annotation.name();
				if (name.equals("")) {
					name = clazzName;
				}
				String key = annotation.key();
				if (key.equals("")) {
					key = fieldName;
				}
				Object value = null;
				if (checkIsString(type)) {
					value = SharedPreUtil.getString(context, name, key, "");
				} else if (checkIsInteger(type)) {
					value = SharedPreUtil.getInt(context, name, key);
				} else if (checkIsLong(type)) {
					value = SharedPreUtil.getLong(context, name, key);
				} else if (checkIsDouble(type)) {
					value = SharedPreUtil.getFloat(context, name, key);
				} else if (checkIsBoolean(type)) {
					value = SharedPreUtil.getBoolean(context, name, key);
				} else if (!type.isPrimitive()) {
					if (checkIsList(type)) {
						Log.e("BeanParamsUtil.getObject error", "目前尚未支持List方法~");
					} else if (checkIsMap(type)) {
						Log.e("BeanParamsUtil.getObject error", "目前尚未支持Map方法~");
					} else if (checkIsSet(type)) {
						Log.e("BeanParamsUtil.getObject error", "目前尚未支持Set方法~");
					} else if (type.isArray()) {
						Log.e("BeanParamsUtil.getObject error", "目前尚未支持数组方法~");
					} else {
						value = getObject(type, context);
					}
				}
				try {
					method.invoke(ob, value);
				} catch (IllegalAccessException e) {
					Log.e("BeanParamsUtil.getObject error", e.toString());
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					Log.e("BeanParamsUtil.getObject error", e.toString());
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					Log.e("BeanParamsUtil.getObject error", e.toString());
					e.printStackTrace();
				}
			}
		}
		return ob;
	}
	
	private static boolean checkIsString(Class<?> type) {
		if (type == String.class) {
			return true;
		} else if (type == CharSequence.class || type == char.class) {
			return true;
		} else if (type == Byte.class || type == byte.class) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean checkIsInteger(Class<?> type) {
		if (type == Integer.class || type == int.class) {
			return true;
		} else if (type == Short.class) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean checkIsLong(Class<?> type) {
		if (type == Long.class || type == long.class) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean checkIsDouble(Class<?> type) {
		if (type == Double.class || type == double.class) {
			return true;
		} else if (type == Float.class || type == float.class) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean checkIsBoolean(Class<?> type) {
		if (type == Boolean.class || type == boolean.class) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean checkIsList(Class<?> type) {
		if (type == List.class) {
			return true;
		} else if (type == ArrayList.class) {
			return true;
		} else if (type == LinkedList.class) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean checkIsMap(Class<?> type) {
		if (type == Map.class) {
			return true;
		} else if (type == HashMap.class) {
			return true;
		} else if (type == LinkedHashMap.class) {
			return true;
		} else if (type == ConcurrentHashMap.class) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean checkIsSet(Class<?> type) {
		if (type == Set.class) {
			return true;
		} else if (type == HashSet.class) {
			return true;
		} else if (type == LinkedHashSet.class) {
			return true;
		} else if (type == ConcurrentSkipListSet.class) {
			return true;
		} else {
			return false;
		}
	}
}
