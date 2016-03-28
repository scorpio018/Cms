package com.enorth.cms.bean.login;

import java.io.Serializable;

import com.enorth.cms.annotation.SharedPreSaveAnnotation;

/**
 * 规定对应频道能够上传的图片的最大/最小像素
 * 
 * @author yangyang
 *
 */
public class SeniorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -338016523053913922L;
	/**
	 * 图片的最大像素
	 */
	@SharedPreSaveAnnotation
	private int picMax;
	/**
	 * 图片的最小像素
	 */
	@SharedPreSaveAnnotation
	private int picMin;

	public int getPicMax() {
		return picMax;
	}

	public void setPicMax(int picMax) {
		this.picMax = picMax;
	}

	public int getPicMin() {
		return picMin;
	}

	public void setPicMin(int picMin) {
		this.picMin = picMin;
	}

}
