package com.enorth.cms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 将使用该注解的参数存入SharedPreference中
 * name表示Editor的名字
 * value表示存入的key
 * @author Administrator
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SharedPreSaveAnnotation {
	/**
	 * Editor的名字
	 * @return
	 */
	String name() default "";
	/**
	 * 保存到Editor的key名
	 * @return
	 */
	String key() default "";
}
