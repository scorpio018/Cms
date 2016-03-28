package com.enorth.cms.enums;

public enum HttpBuilderType {
	/**
	 * 提交表单
	 */
	REQUEST_FORM_ENCODE,
	/**
	 * 提交文件
	 */
	REQUEST_FILE,
	/**
	 * 提交流
	 */
	REQUEST_STREAM,
	/**
	 * 提交String
	 */
	REQUEST_STRING,
	/**
	 * 提交分块请求
	 * 	MultipartBuilder可以构建复杂的请求体，与HTML文件上传形式兼容。
	 * 	多块请求体中每块请求都是一个请求体，可以定义自己的请求头。这些请求头可以用来描述这块请求，例如他的Content-Disposition。
	 * 	如果Content-Length和Content-Type可用的话，他们会被自动添加到请求头中。
	 */
	REQUEST_MIX
	
}
