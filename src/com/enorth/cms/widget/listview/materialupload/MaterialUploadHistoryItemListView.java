package com.enorth.cms.widget.listview.materialupload;

import com.enorth.cms.utils.ScreenTools;
import com.enorth.cms.widget.linearlayout.MaterialUploadFragLinearLayout;
import com.enorth.cms.widget.listview.CommonListView;

import android.content.Context;
import android.util.AttributeSet;

public class MaterialUploadHistoryItemListView extends CommonListView {
	
	private MaterialUploadFragLinearLayout fragLayout;

	public MaterialUploadHistoryItemListView(Context context) {
		super(context);
	}
	
	public MaterialUploadHistoryItemListView(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	public MaterialUploadHistoryItemListView(Context context, AttributeSet attr, int defStyleAttr) {
		super(context, attr, defStyleAttr);
	}
	
	public MaterialUploadHistoryItemListView(Context context, MaterialUploadFragLinearLayout fragLayout) {
		super(context);
		this.fragLayout = fragLayout;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (fragLayout == null) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		} else {
			int measuredHeight = ScreenTools.measuredHeight(heightMeasureSpec);
			int measuredWidth = ScreenTools.measuredWidth(widthMeasureSpec);
			int curMoveHeight = fragLayout.getCurMoveHeight();
			setMeasuredDimension(measuredWidth, measuredHeight + curMoveHeight);
		}
	}
	
}
