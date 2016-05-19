package com.enorth.cms.bean.securitysetting;

import com.enorth.cms.annotation.UrlParamAnnotation;
/**
 * 修改密码时传入的参数对应的bean
 * @author yangyang
 *
 */
public class RequestChangePwdUrlBean {
	/**
	 * 原密码
	 */
	@UrlParamAnnotation(isCheck = true, checkSort = 1)
	private String orgPwd;
	
	/**
	 * 新密码
	 */
	@UrlParamAnnotation
	private String newPwd;

	public String getOrgPwd() {
		return orgPwd;
	}

	public void setOrgPwd(String orgPwd) {
		this.orgPwd = orgPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	
	
}
