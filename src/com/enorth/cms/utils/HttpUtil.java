package com.enorth.cms.utils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.enorth.cms.consts.ParamConst;
import com.enorth.cms.consts.UrlCodeErrorConst;
import com.enorth.cms.enums.HttpBuilderType;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import okio.BufferedSink;

public class HttpUtil {
	// OkHttpClient只需要保持一个对象即可，没必要多次实例化
	private static OkHttpClient client;

//	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	public static OkHttpClient getOkHttpClient() {
		if (client == null) {
			client = new OkHttpClient();
			client.setConnectTimeout(10, TimeUnit.SECONDS);
		}
		return client;
	}
	
	/**
	 * 适用于只有url，没有需要传入的参数的同步POST请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String okPost(String url, HttpBuilderType type) throws Exception {
//		RequestBody body = RequestBody.create(JSON, "");
		RequestBody body = getRequestBodyByType(null, type);
		Request request = new Request.Builder().url(url).post(body).build();
		return executeHttp(request);
	}
	
	/**
	 * 适用于只有url，没有需要传入的参数的异步POST请求
	 * @param url
	 * @param callback
	 * @throws Exception
	 */
	public static void okPost(String url, Callback callback, HttpBuilderType type) throws Exception {
		RequestBody body = getRequestBodyByType(null, type);
		Request request = new Request.Builder().url(url).post(body).build();
		enqueue(request, callback);
	}

	/**
	 * 适用于既有url，又有参数的同步POST请求
	 * @param url
	 * @param paramsJson
	 * @return
	 * @throws Exception
	 */
	public static String okPost(String url, List<BasicNameValuePair> params, HttpBuilderType type) throws Exception {
//		RequestBody body = RequestBody.create(JSON, paramsJson);
		RequestBody body = getRequestBodyByType(params, type);
		Request request = new Request.Builder().url(url).post(body).build();
		return executeHttp(request);
	}
	
	/**
	 * 适用于既有url，又有参数的异步POST请求
	 * @param url
	 * @param paramsJson
	 * @param callback
	 * @throws Exception
	 */
	public static void okPost(String url, List<BasicNameValuePair> params, Callback callback, HttpBuilderType type) {
//		RequestBody body = RequestBody.create(JSON, paramsJson);
		RequestBody body = getRequestBodyByType(params, type);
		Request request = new Request.Builder().url(url).post(body).build();
		enqueue(request, callback);
	}
	
	private static RequestBody getRequestBodyByType(List<BasicNameValuePair> params, HttpBuilderType type) {
		if (type.equals(HttpBuilderType.REQUEST_FORM_ENCODE)) {
			// 普通表单
			return attachHttpPostParamsFormEncodingBuilder(params);
		} else if (type.equals(HttpBuilderType.REQUEST_FILE)) {
			// 文件表单
			return attachHttpPostParamsMultipartBuilder(params);
		} /*else if (type.equals(HttpBuilderType.REQUEST_STREAM)) {
			// 文件流表单
		} else if (type.equals(HttpBuilderType.REQUEST_STRING)) {
			// String类型表单
		} else if (type.equals(HttpBuilderType.REQUEST_MIX)) {
			// 混合表单
		}*/ else {
			return null;
		}
	}
	
	/**
	 * 适用于只有url，没有需要传入的参数的同步GET请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String okGet(String url) throws Exception {
		Request request = new Request.Builder().url(url).get().build();
		return executeHttp(request);
	}
	
	/**
	 * 适用于只有url，没有需要传入的参数的异步GET请求
	 * @param url
	 * @param callback
	 * @throws Exception
	 */
	public static void okGet(String url, Callback callback) throws Exception {
		Request request = new Request.Builder().url(url).get().build();
		enqueue(request, callback);
	}

	/**
	 * 适用于既有url，又有参数的同步GET请求
	 * @param url
	 * @param paramsJson
	 * @return
	 * @throws Exception
	 */
	public static String okGet(String url, List<BasicNameValuePair> params) throws Exception {
		url = attachHttpGetParams(url, params);
		Request request = new Request.Builder().url(url).get().build();
		return executeHttp(request);
	}
	
	/**
	 * 适用于既有url，又有参数的异步GET请求
	 * @param url
	 * @param paramsJson
	 * @param callback
	 * @throws Exception
	 */
	public static void okGet(String url, List<BasicNameValuePair> params, Callback callback) throws Exception {
		url = attachHttpGetParams(url, params);
		Request request = new Request.Builder().url(url).get().build();
		enqueue(request, callback);
	}

	/**
	 * 该请求不会开启异步线程
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private static String executeHttp(Request request) throws Exception {
		Response response = getOkHttpClient().newCall(request).execute();
		if (response.isSuccessful()) {
			return checkResponseIsSuccess(response);
		} else {
			throw new Exception("请求失败：" + response);
		}
	}
	
	/**
	 * 通过response的code值进行判断，确定真正正常访问并返回正确的数据
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String checkResponseIsSuccess(Response response) throws Exception {
		int code = response.code();
		switch (code) {
		case 200:
			String result = response.body().string();
			return checkResourseResult(result);
		case 404:
			throw new Exception("未找到对应页面");
		default:
			throw new Exception(response.message());
		}
	}
	
	public static String checkResponseIsSuccess(String result) throws Exception {
		return checkResourseResult(result);
	}

	private static String checkResourseResult(String result) throws Exception {
		JSONObject jo = new JSONObject(result);
		int errorCode = jo.getInt("code");
		switch (errorCode) {
		case UrlCodeErrorConst.SUCCESS:
			return jo.getString("result");
		case UrlCodeErrorConst.CHECK_TOKEN_FAILED:
			throw new Exception("用户验证失败，请重新登录[-1]");
		case UrlCodeErrorConst.CHECK_CHECK_SUM_FAILED:
			throw new Exception("用户验证失败，请重新登录[-2]");
		case UrlCodeErrorConst.CHECK_LOGIN_FAILED:
		case UrlCodeErrorConst.CUR_STATE_CANNOT_DEL_NEWS:
		case UrlCodeErrorConst.CANNOT_OPER_MULTI_TAGS_NEWS:
		case UrlCodeErrorConst.REQUEST_OBJECT_IS_NOT_EXISTS:
		case UrlCodeErrorConst.CUR_USER_NO_FIRST_LEVEL_CHANNEL_PERMISSION:
		case UrlCodeErrorConst.CREATE_LOGIN_TOKEN_FAILED:
			throw new Exception("errorCode:【" + errorCode + "】" + jo.getString("result"));
		default:
			throw new Exception(UrlCodeErrorConst.UNKNOWN_ERROR_HINT);
		}
	}
	/**
	 * 开启异步线程访问网络
	 * 
	 * @param request
	 * @param responseCallback
	 */
	public static void enqueue(Request request, Callback responseCallback) {
		getOkHttpClient().newCall(request).enqueue(responseCallback);
	}

	/**
	 * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
	 * 
	 * @param request
	 */
	public static void enqueue(Request request) {
		getOkHttpClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Response r) throws IOException {
				
			}

			@Override
			public void onFailure(Request r, IOException e) {
				
			}
		});
	}

	private static final String CHARSET_NAME = "UTF-8";

	/**
	 * 这里使用了HttpClinet的API。只是为了方便
	 * 
	 * @param params
	 * @return
	 */
	public static String formatParams(List<BasicNameValuePair> params) {
		return URLEncodedUtils.format(params, CHARSET_NAME);
	}
	
	/**
	 * 根据传入的参数进行拆分，并存入FormEncodingBuilder中，最终build成RequestBody
	 * @param params
	 * @return
	 */
	public static RequestBody attachHttpPostParamsFormEncodingBuilder(List<BasicNameValuePair> params) {
//		MultipartBuilder multipartBuilder = new MultipartBuilder();
		FormEncodingBuilder builder = new FormEncodingBuilder();
//		multipartBuilder.type(MultipartBuilder.FORM);
//		multipartBuilder.type(MultipartBuilder.)
		if (params != null) {
			for (BasicNameValuePair param : params) {
				builder.add(param.getName(), param.getValue());
			}
		} else {
			builder.add("111", "111");
		}
		return builder.build();
	}
	/**
	 * 根据传入的参数进行拆分，并存入MultipartBuilder中，最终build成RequestBody
	 * @param params
	 * @return
	 */
	public static RequestBody attachHttpPostParamsMultipartBuilder(List<BasicNameValuePair> params) {
		MultipartBuilder multipartBuilder = new MultipartBuilder();
		multipartBuilder.type(MultipartBuilder.FORM);
		if (params != null) {
			for (BasicNameValuePair param : params) {
				multipartBuilder.addFormDataPart(param.getName(), param.getValue());
			}
		} else {
			multipartBuilder.addFormDataPart("111", "111");
		}
		return multipartBuilder.build();
	}
	
	private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
	
	/*public static RequestBody attachHttpPostParamsStream(List<BasicNameValuePair> params) {
		RequestBody requestBody = new RequestBody() {
			
			@Override
			public void writeTo(BufferedSink arg0) throws IOException {
				
			}
			
			@Override
			public MediaType contentType() {
				return MEDIA_TYPE_MARKDOWN;
			}
		};
	}*/

	/**
	 * 为HttpGet 的 url 方便的添加多个name value 参数。
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String attachHttpGetParams(String url, List<BasicNameValuePair> params) {
		return url + "?" + formatParams(params);
	}

	/**
	 * 为HttpGet 的 url 方便的添加1个name value 参数。
	 * 
	 * @param url
	 * @param name
	 * @param value
	 * @return
	 */
	public static String attachHttpGetParam(String url, String name, String value) {
		return url + "?" + name + "=" + value;
	}
	
	public static void requestOnFailure(Request r, Exception e, Handler handler) {
		commonOnFailure(e, handler);
	}
	
	public static void responseOnFailure(Response r, Exception e, Handler handler) {
		commonOnFailure(e, handler);
	}
	
	public static void commonOnFailure(Exception e, Handler handler) {
		Message message = new Message();
		String errorMsg = e.toString();
		if (errorMsg == null) {
			errorMsg = "服务器异常";
		}
		Log.e("HttpUtil", errorMsg);
		message.what = ParamConst.MESSAGE_WHAT_ERROR;
		message.obj = errorMsg;
		handler.sendMessage(message);
	}
}
