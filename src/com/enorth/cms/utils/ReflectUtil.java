package com.enorth.cms.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {
	public static Object reflectObject(Object ob) throws Exception {
		Class<? extends Object> obClass = ob.getClass();
		Class<?> forName = Class.forName(obClass.getPackage() + "." + obClass.getName());
		Object result = forName.newInstance();
		Field[] fields = ob.getClass().getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			Class<? extends Field> c = field.getClass();
			String commonName = name.substring(0, 1).toUpperCase() + name.substring(1);
			String setterName = "set" + commonName;
			String getterName = "get" + commonName;
			Method getterMethod = obClass.getMethod(getterName, c);
			Object getterValue = getterMethod.invoke(ob, new Object[] {});
			Method setterMethod = result.getClass().getMethod(setterName, c);
			setterMethod.invoke(result, new Object[] {getterValue});
		}
		return result;
	}
	
	
}
