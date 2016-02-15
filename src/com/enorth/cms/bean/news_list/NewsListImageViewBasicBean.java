package com.enorth.cms.bean.news_list;

import com.enorth.cms.bean.ImageBasicBean;

import android.widget.ImageView;

public class NewsListImageViewBasicBean extends ImageBasicBean {
	

	private boolean isSelected = false;

	private ImageView imageView;
	
	private String id;
	
	private String name;
	
	private String parentId;
	
	private boolean canClick;
	
	private boolean hasChild;
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean isCanClick() {
		return canClick;
	}

	public void setCanClick(boolean canClick) {
		this.canClick = canClick;
	}
	
	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		NewsListImageViewBasicBean bean = (NewsListImageViewBasicBean) o;
		if (id != null) {
			if (!id.equals(bean.getId())) {
				return false;
			}
		} else {
			if (bean.getId() != null) {
				return false;
			}
		}
		return true;
	}

}
