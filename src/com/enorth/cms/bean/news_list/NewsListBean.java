package com.enorth.cms.bean.news_list;

import java.io.Serializable;

import com.enorth.cms.annotation.SharedPreSaveAnnotation;

/**
 * 接口返回的新闻列表bean
 * 
 * @author yangyang
 *
 */
public class NewsListBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2222368569554486589L;
	/**
	 * 新闻ID
	 */
	@SharedPreSaveAnnotation
	private Long newsId;
	/**
	 * 新闻标题
	 */
	@SharedPreSaveAnnotation
	private String title;
	/**
	 * 拟制时间
	 */
	@SharedPreSaveAnnotation
	private Long initDate;
	/**
	 * 修改时间
	 */
	@SharedPreSaveAnnotation
	private Long modDate;
	/**
	 * 新闻状态（编辑0 待签发1000 已签发2000 删除-1）
	 */
	@SharedPreSaveAnnotation
	private int state;
	/**
	 * 新闻发布时间
	 */
	@SharedPreSaveAnnotation
	private Long pubDate;
	/**
	 * 优先级
	 */
	@SharedPreSaveAnnotation
	private int listOrder;
	/**
	 * 该新闻的编辑的真实名称
	 */
	@SharedPreSaveAnnotation
	private String trueName;
	/**
	 * 批注数
	 */
	@SharedPreSaveAnnotation
	private int reviewCount;
	/**
	 * 是否是融合新闻 0否 1是
	 */
	@SharedPreSaveAnnotation
	private int conv;
	/**
	 * 是否是连接状态 0否 1是
	 */
	@SharedPreSaveAnnotation
	private int link;
	/**
	 * 是否有导读图 0否 1是
	 */
	@SharedPreSaveAnnotation
	private int hasGuideImage;
	/**
	 * 预览url
	 */
	@SharedPreSaveAnnotation
	private String pubUrl;

	public Long getNewsId() {
		return newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getInitDate() {
		return initDate;
	}

	public void setInitDate(Long initDate) {
		this.initDate = initDate;
	}

	public Long getModDate() {
		return modDate;
	}

	public void setModDate(Long modDate) {
		this.modDate = modDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Long getPubDate() {
		return pubDate;
	}

	public void setPubDate(Long pubDate) {
		this.pubDate = pubDate;
	}

	public int getListOrder() {
		return listOrder;
	}

	public void setListOrder(int listOrder) {
		this.listOrder = listOrder;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public int getConv() {
		return conv;
	}

	public void setConv(int conv) {
		this.conv = conv;
	}

	public int getLink() {
		return link;
	}

	public void setLink(int link) {
		this.link = link;
	}

	public int getHasGuideImage() {
		return hasGuideImage;
	}

	public void setHasGuideImage(int hasGuideImage) {
		this.hasGuideImage = hasGuideImage;
	}

	public String getPubUrl() {
		return pubUrl;
	}

	public void setPubUrl(String pubUrl) {
		this.pubUrl = pubUrl;
	}

}
