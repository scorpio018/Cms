package com.enorth.cms.bean;

import com.enorth.cms.annotation.UrlParamAnnotation;

public class Page {

	/**
	 * 当前第几页
	 */
	@UrlParamAnnotation
	private Integer pageNo = 1;
	/**
	 * 一页显示多少条
	 */
	@UrlParamAnnotation
	private Integer pageSize = 20;
	/**
	 * 
	 */
	@UrlParamAnnotation
	private String orderBy = "mod_date desc";

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy + " desc";
	}

}
