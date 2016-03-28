package com.enorth.cms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 在进行接口请求的时候，需要传入接口的参数标识
 * @author Administrator
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UrlParamAnnotation {
	
	String key() default "";
	
	boolean isCheck() default false;
	
	int checkSort() default 0;
	
}
