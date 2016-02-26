package com.enorth.cms.widget.viewpager.materialupload;

import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.widget.linearlayout.MaterialUploadFragLinearLayout;
import com.enorth.cms.widget.viewpager.CommonViewPager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class MaterialUploadHistoryViewPager extends CommonViewPager {
	
	private MaterialUploadFragLinearLayout fragLayout;

	public MaterialUploadHistoryViewPager(Context context) {
		super(context);
	}
	
	public MaterialUploadHistoryViewPager(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	public MaterialUploadHistoryViewPager(Context context, MaterialUploadFragLinearLayout fragLayout) {
		super(context);
		this.fragLayout = fragLayout;
	}
	
	public MaterialUploadHistoryViewPager(Context context, AttributeSet attr, MaterialUploadFragLinearLayout fragLayout) {
		super(context, attr);
		this.fragLayout = fragLayout;
	}
	
	/*@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (fragLayout == null) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		} else {
			int measuredHeight = ScreenTools.measuredHeight(heightMeasureSpec);
			int measuredWidth = ScreenTools.measuredWidth(widthMeasureSpec);
			int curMoveHeight = fragLayout.getCurMoveHeight();
			setMeasuredDimension(measuredWidth, measuredHeight + curMoveHeight);
		}
	}*/

}
