package com.enorth.cms.bean.login;

import java.io.Serializable;

import com.enorth.cms.annotation.SharedPreSaveAnnotation;

public class ScanBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 扫描二维码得到的系统的ID
	 */
	@SharedPreSaveAnnotation(name = "scan")
	private String scanId;
	/**
	 * 扫描二维码得到的系统的base_url
	 */
	@SharedPreSaveAnnotation(name = "scan")
	private String scanUrl;

	/**
	 * 扫描二维码得到的系统的版本
	 */
	@SharedPreSaveAnnotation(name = "scan")
	private String scanVersion;
	/**
	 * 扫描二维码得到的系统的名字
	 */
	@SharedPreSaveAnnotation(name = "scan")
	private String scanName;

	public String getScanId() {
		return scanId;
	}

	public void setScanId(String scanId) {
		this.scanId = scanId;
	}

	public String getScanUrl() {
		return scanUrl;
	}

	public void setScanUrl(String scanUrl) {
		this.scanUrl = scanUrl;
	}

	public String getScanVersion() {
		return scanVersion;
	}

	public void setScanVersion(String scanVersion) {
		this.scanVersion = scanVersion;
	}

	public String getScanName() {
		return scanName;
	}

	public void setScanName(String scanName) {
		this.scanName = scanName;
	}

}
