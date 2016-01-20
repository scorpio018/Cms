package com.enorth.cms.refreshlayout;

import org.xwalk.core.internal.XWalkViewInternal;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CrossWalkRefreshLayout extends XWalkViewInternal {
	
	public CommonRefreshLayout commonLayout;
	
	private CrossWalkRefreshLayout thisLayout;

	public CrossWalkRefreshLayout(Context context) {
		super(context);
		this.thisLayout = this;
		initCommonLayout(context);
	}
	
	public CrossWalkRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.thisLayout = this;
		initCommonLayout(context);
	}
	
	public CrossWalkRefreshLayout(Context context, Activity activity) {
		super(context, activity);
		this.thisLayout = this;
		initCommonLayout(context);
	}
	
	/*public CrossWalkRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}*/
	
	private void initCommonLayout(Context context) {
		commonLayout = new CommonRefreshLayout(context) {
			
			@Override
			public void requestLayout() {
				thisLayout.requestLayout();
			}
			
			@Override
			public int getMeasuredHeight() {
				return thisLayout.getMeasuredHeight();
			}
			
			@Override
			public View getChildAt(int num) {
				int childCount = thisLayout.getChildCount();
				
				/*for (int i = 0; i < childCount; i++) {
					View child = thisLayout.getChildAt(i);
					Resources resources = child.getResources();
					Log.i("类型：", child.getClass().getName() + resources.getClass().getName());
				}
				Log.i("childCount:", String.valueOf(childCount));*/
				return thisLayout.getChildAt(num);
			}
		};
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		commonLayout.dispatchTouchEvent(ev);
		super.dispatchTouchEvent(ev);
		return true;
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		commonLayout.layout(changed, left, top, right, bottom);
	}
}
