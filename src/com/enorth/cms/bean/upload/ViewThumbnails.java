package com.enorth.cms.bean.upload;

import android.widget.ImageButton;
import android.widget.ImageView;
/**
 * 缩略图控件
 * @author yangyang
 *
 */
public class ViewThumbnails {
	/**
	 * 缩略图
	 */
	private ImageView idItemImage;
	/**
	 * 缩略图右上角的图标
	 */
	private ImageButton idItemSelect;

	public ImageView getIdItemImage() {
		return idItemImage;
	}

	public void setIdItemImage(ImageView idItemImage) {
		this.idItemImage = idItemImage;
	}

	public ImageButton getIdItemSelect() {
		return idItemSelect;
	}

	public void setIdItemSelect(ImageButton idItemSelect) {
		this.idItemSelect = idItemSelect;
	}

}
