package com.enorth.cms.bean.login;

import java.io.Serializable;

import com.enorth.cms.annotation.SharedPreSaveAnnotation;

/**
 * 用于保存新闻频道对象
 * 
 * @author yangyang
 *
 */
public class ChannelBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8136705582426463832L;
	/**
	 * 新闻频道ID
	 */
	@SharedPreSaveAnnotation
	private Long channelId;
	/**
	 * 新闻频道名
	 */
	@SharedPreSaveAnnotation
	private String channelName;
	/**
	 * 新闻频道的层级
	 */
	@SharedPreSaveAnnotation
	private Integer channelLevel;
	/**
	 * 父ID
	 */
	@SharedPreSaveAnnotation
	private Long parentId;
	
	/**
	 * 是否有子频道 0否 1是
	 */
	private int hasChildren;
	
	/**
	 * 频道首字母大写
	 */
	private String simpleName;
	/**
	 * 规定对应频道能够上传的图片的最大/最小像素
	 */
	@SharedPreSaveAnnotation
	private SeniorBean senior;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public int getChannelLevel() {
		return channelLevel;
	}

	public void setChannelLevel(Integer channelLevel) {
		this.channelLevel = channelLevel;
	}

	public SeniorBean getSenior() {
		return senior;
	}

	public void setSenior(SeniorBean senior) {
		this.senior = senior;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public int getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(int hasChildren) {
		this.hasChildren = hasChildren;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

}
