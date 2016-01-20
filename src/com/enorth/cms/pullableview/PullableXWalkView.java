package com.enorth.cms.pullableview;

import org.xwalk.core.XWalkView;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

public class PullableXWalkView extends XWalkView implements Pullable {

	public PullableXWalkView(Context context) {
		super(context);
	}
	
	public PullableXWalkView(Context context, Activity activity) {
		super(context, activity);
	}
	
	public PullableXWalkView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean canPullDown() {
		if (getScrollY() == 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean canPullUp() {
		if (getScrollY() >= getHeight() * getResources().getDisplayMetrics().density - getMeasuredHeight())
			return true;
		else
			return false;
	}

}
