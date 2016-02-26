package com.enorth.cms.widget.gridview.materialupload;

import com.enorth.cms.widget.gridview.CommonGridView;

import android.content.Context;
import android.util.AttributeSet;

public class MaterialUploadHistoryGridView extends CommonGridView {
	
	private boolean hasScrollBar = true;

	public MaterialUploadHistoryGridView(Context context) {
		super(context);
	}
	
	public MaterialUploadHistoryGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public MaterialUploadHistoryGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	public MaterialUploadHistoryGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = heightMeasureSpec;
		if (hasScrollBar) {
			expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);// 注意这里,这里的意思是直接测量出GridView的高度
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	public boolean isHasScrollBar() {
		return hasScrollBar;
	}

	public void setHasScrollBar(boolean hasScrollBar) {
		this.hasScrollBar = hasScrollBar;
	}
	
	
}
