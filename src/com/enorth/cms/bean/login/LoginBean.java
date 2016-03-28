package com.enorth.cms.bean.login;

import java.io.Serializable;

import com.enorth.cms.annotation.SharedPreSaveAnnotation;
import com.enorth.cms.annotation.UrlParamAnnotation;

public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7656349434892258260L;

	@SharedPreSaveAnnotation(name = "user")
	private int userId;

	@UrlParamAnnotation
	@SharedPreSaveAnnotation(name = "user")
	private String userName;

	@UrlParamAnnotation
	@SharedPreSaveAnnotation(name = "user")
	private String password;

	@SharedPreSaveAnnotation(name = "user")
	private String trueName;

	@SharedPreSaveAnnotation(name = "user")
	private String token;
	
	@SharedPreSaveAnnotation(name = "user")
	private Boolean rememberUser;
	
	@SharedPreSaveAnnotation(name = "user")
	private String seed;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean isRememberUser() {
		return rememberUser;
	}

	public void setRememberUser(Boolean rememberUser) {
		this.rememberUser = rememberUser;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	public Boolean getRememberUser() {
		return rememberUser;
	}

}
