package com.enorth.cms.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpXmlClient {
//	private static Logger log = Logger.getLogger(HttpXmlClient.class);

	public static String post(String url, Map<String, String> params)
			throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;

//		log.info("create httpPost:" + url);
		HttpPost httpPost = postForm(url, params);

		body = invoke(httpclient, httpPost);

		return body;
	}

	public static String get(String url) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;

//		log.info("create httpGet:" + url);
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);

		return body;
	}

	private static String invoke(CloseableHttpClient httpclient,
			HttpUriRequest httpost) throws ParseException, IOException {

		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);

		return body;
	}

	private static String paseResponse(HttpResponse response)
			throws ParseException, IOException {
//		log.info("get response from http server..");
		HttpEntity entity = response.getEntity();

//		log.info("response status: " + response.getStatusLine());
		String charset = EntityUtils.toString(entity);
//		log.info(charset);

		return charset;
	}

	private static HttpResponse sendRequest(CloseableHttpClient httpclient,
			HttpUriRequest httpost) {
//		log.info("execute post...");
		HttpResponse response = null;

		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static HttpPost postForm(String url, Map<String, String> params) {

		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}

		try {
//			log.info("set utf-8 form entity to httppost");
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return httpPost;
	}
}
